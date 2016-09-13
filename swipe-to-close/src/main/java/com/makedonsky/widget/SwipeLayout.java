/**
 *  Copyright (C) 2015 Nevyantsev Alexandr
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.makedonsky.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


public class SwipeLayout extends RelativeLayout {
    private static final float START_ALPHA = 0.8f;
    private static final float END_ALPHA = 0.0f;

    private static final float SENSITIVITY = 0.2f;

    private ViewDragHelper mViewDragHelper;

    private int mDisplayWidth;

    private OnCloseListener mOnCloseListener;

    private LinearLayout mFadeLayout;

    public SwipeLayout(Context context) {
        super(context);
        init(true, android.R.color.black);
    }

    public SwipeLayout(Context context, boolean shadow, int shadowColor) {
        super(context);
        init(shadow, shadowColor);
    }

    public SwipeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true, android.R.color.black);
    }

    private void init(boolean shadow, @ColorRes int shadowColor) {
        mViewDragHelper = ViewDragHelper.create(this, SENSITIVITY, new ViewDragHelperCallback());
        mDisplayWidth = getResources().getDisplayMetrics().widthPixels;

        if(!shadow) {
            return;
        }

        mFadeLayout = new LinearLayout(getContext());

        LinearLayout.LayoutParams layoutParams
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFadeLayout.setLayoutParams(layoutParams);

        mFadeLayout.setLayoutParams(layoutParams);
        mFadeLayout.setBackgroundColor(ContextCompat.getColor(getContext(), shadowColor));
        mFadeLayout.setAlpha(START_ALPHA);

        addView(mFadeLayout, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        mOnCloseListener = onCloseListener;
    }

    public interface OnCloseListener {
        /**
         * Called before calling {@link OnCloseListener#onClose()}
         *
         * @return true, if calling {@link OnCloseListener#onClose()} needed
         */
        boolean onCloseStart();

        /**
         * Called when layout is closed
         */
        void onClose();

        /**
         * Called when {@link OnCloseListener#onCloseStart()} returns false
         */
        void onCancel();
    }

    /**
     * Состояние свайпа
     */
    private static class State {
        static final int NONE = 0;
        static final int VERTICAL = 1;
        static final int HORIZONTAL = 2;

        int mDirection;
        int mLeft;
        boolean mClose;
        int mDx;

        void setDirection(int direction) {
            if (mDirection == NONE) {
                mDirection = direction;
            }
        }

        public void reset() {
            mDirection = NONE;
            mLeft = 0;
            mClose = false;
        }
    }

    private class ViewDragHelperCallback extends ViewDragHelper.Callback {
        State mState = new State();

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child != mFadeLayout;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            mState.setDirection(State.VERTICAL);
            return super.clampViewPositionVertical(child, top, dy);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            mState.setDirection(State.HORIZONTAL);
            if (mState.mDirection == State.HORIZONTAL) {
                mState.mLeft = left >= 0 ? left : 0;
                mState.mDx = dx;
            }
            return mState.mLeft;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return mDisplayWidth;
        }

        @Override
        public void onViewReleased(View releasedChild, float xVel, float yVel) {
            super.onViewReleased(releasedChild, xVel, yVel);
            int leftPosition = 0;
            mState.mClose = mState.mLeft > 200 && mState.mDx > 0;
            if (mState.mClose && (mOnCloseListener == null || mOnCloseListener.onCloseStart())) {
                leftPosition = mDisplayWidth;
            }
            mViewDragHelper.settleCapturedViewAt(leftPosition, 0);
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            if (state != ViewDragHelper.STATE_IDLE || mOnCloseListener == null) {
                return;
            }

            if (mState.mClose && mOnCloseListener.onCloseStart()) {
                mOnCloseListener.onClose();
            } else if (mState.mClose && !mOnCloseListener.onCloseStart()) {
                mOnCloseListener.onCancel();
            }
            mState.reset();
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (mFadeLayout != null) {
                float percent = 1f - ((float) Math.abs(left) / (float) mDisplayWidth);
                float alpha = (percent * (START_ALPHA - END_ALPHA)) + END_ALPHA;
                mFadeLayout.setAlpha(alpha);
            }
        }
    }
}
