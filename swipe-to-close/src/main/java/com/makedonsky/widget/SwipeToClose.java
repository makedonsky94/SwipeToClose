/**
 *  Copyright (C) 2016 Nevyantsev Alexandr
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


import android.app.Activity;
import android.support.annotation.ColorRes;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class SwipeToClose {
    private WeakReference<Activity> mActivity;
    private boolean mShadow = true;
    private int mShadowColor = android.R.color.black;
    private int mDirection = SwipeLayout.DIRECTION_RIGHT;
    private float mSensitivity = SwipeLayout.SENSITIVITY;
    private float mStartAlpha = SwipeLayout.START_ALPHA;
    private float mEndAlpha = SwipeLayout.END_ALPHA;

    private SwipeLayout.OnCloseListener mOnCloseListener = new SwipeLayout.OnCloseListener() {
        @Override
        public boolean onCloseStart() {
            return true;
        }

        @Override
        public void onClose() {
            Activity activity = mActivity.get();
            if (activity != null) {
                activity.finish();
            }
        }

        @Override
        public void onCancel() {

        }
    };

    public static SwipeToClose with(Activity activity) {
        final SwipeToClose swipeToClose = new SwipeToClose();
        swipeToClose.mActivity = new WeakReference<>(activity);
        return swipeToClose;
    }

    /**
     * Overrides listener
     */
    public SwipeToClose withListener(SwipeLayout.OnCloseListener onCloseListener) {
        mOnCloseListener = onCloseListener;
        return this;
    }

    /**
     * If {@code widthShadow} is true, {@link SwipeLayout} will create shadow effect on closing activity
     * Default value: true
     */
    public SwipeToClose withShadow(boolean withShadow) {
        mShadow = withShadow;
        return this;
    }

    /**
     * Sets color of the shadow effect.
     * Default value: black
     */
    public SwipeToClose withShadowColor(@ColorRes int color) {
        mShadowColor = color;
        return this;
    }

    /**
     * Sets alpha to the shadow view.
     * Default value: {@code startAlpha == 0.8f}, {@code endAlpha == 0.0f}
     */
    public SwipeToClose withShadowAlpha(float startAlpha, float endAlpha) {
        mStartAlpha = startAlpha;
        mEndAlpha = endAlpha;
        return this;
    }

    /**
     * Sets direction of the swipe.
     * Available values:
     * {@link SwipeLayout#DIRECTION_LEFT},
     * {@link SwipeLayout#DIRECTION_RIGHT},
     * {@link SwipeLayout#DIRECTION_BOTTOM},
     * {@link SwipeLayout#DIRECTION_TOP}
     * {@link SwipeLayout#DIRECTION_VERTICAL}
     * {@link SwipeLayout#DIRECTION_HORIZONTAL}
     */
    public SwipeToClose withDirection(int direction) {
        mDirection = direction;
        return this;
    }

    /**
     * Sets sensitivity of the swipe.
     * Default value: 0.2f
     */
    public SwipeToClose withSensitivity(float sensitivity) {
        mSensitivity = sensitivity;
        return this;
    }

    /**
     * Sets {@link SwipeLayout} as a root view
     */
    public void bind() {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }

        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        View child = content.getChildAt(0);

        SwipeLayout.LayoutParams params =
                new SwipeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        SwipeLayout swipeLayout =
                new SwipeLayout(activity, mShadow, mShadowColor, mStartAlpha, mEndAlpha, mDirection, mSensitivity);
        swipeLayout.setLayoutParams(params);
        swipeLayout.setOnCloseListener(mOnCloseListener);

        content.removeView(child);
        swipeLayout.addView(child);
        content.addView(swipeLayout);
    }
}
