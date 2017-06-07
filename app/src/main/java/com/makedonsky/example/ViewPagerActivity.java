package com.makedonsky.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makedonsky.widget.SwipeToClose;


public class ViewPagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            float x = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        return false;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        return event.getX() - x > 0 && viewPager.getCurrentItem() == 0;
                    default:
                        return false;
                }
            }
        });
        viewPager.setAdapter(new ColorPagerAdapter());

        SwipeToClose
                .with(this)
                .withSensitivity(0.1f)
                .bind();
    }

    private class ColorPagerAdapter extends PagerAdapter {
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
}
