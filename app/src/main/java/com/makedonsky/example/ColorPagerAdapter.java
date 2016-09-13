package com.makedonsky.example;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ColorPagerAdapter extends PagerAdapter {
    int[] colors = {
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
    };

    @Override
    public int getCount() {
        return colors.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        ImageView itemView = (ImageView) LayoutInflater
                .from(context)
                .inflate(R.layout.image, container, false);
        itemView.setImageResource(colors[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
