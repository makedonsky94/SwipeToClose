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


import android.app.Activity;
import android.support.annotation.ColorRes;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class SwipeToClose {
    private WeakReference<Activity> mActivity;
    private boolean mShadow = true;
    private int mShadowColor = android.R.color.black;
    private int mDirection = SwipeLayout.DIRECTION_RIGHT;

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
     */
    public SwipeToClose withShadow(boolean withShadow) {
        mShadow = withShadow;
        return this;
    }

    /**
     * Sets color of shadow effect. Default color is black
     */
    public SwipeToClose withShadowColor(@ColorRes int color) {
        mShadowColor = color;
        return this;
    }

    /**
     * Sets direction of swipe.
     * Available values: {@link SwipeLayout#DIRECTION_LEFT}, {@link SwipeLayout#DIRECTION_RIGHT}
     */
    public SwipeToClose withDirection(int direction) {
        mDirection = direction;
        return this;
    }

    /**
     * Sets {@link SwipeLayout} as root view
     */
    public void bind() {
        Activity activity = mActivity.get();
        if (activity == null) {
            return;
        }

        ViewGroup content = (ViewGroup) activity.findViewById(android.R.id.content);
        ViewGroup child = (ViewGroup) content.getChildAt(0);

        SwipeLayout.LayoutParams params =
                new SwipeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        SwipeLayout swipeLayout =
                new SwipeLayout(activity, mShadow, mShadowColor, mDirection);
        swipeLayout.setLayoutParams(params);
        swipeLayout.setOnCloseListener(mOnCloseListener);

        content.removeView(child);
        swipeLayout.addView(child);
        content.addView(swipeLayout);
    }
}
