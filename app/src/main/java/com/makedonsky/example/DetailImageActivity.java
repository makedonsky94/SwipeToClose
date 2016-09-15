package com.makedonsky.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.makedonsky.widget.SwipeLayout;
import com.makedonsky.widget.SwipeToClose;


public class DetailImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_image);

        SwipeToClose
                .with(this)
                .withShadowAlpha(1.0f, 0.0f)
                .withDirection(SwipeLayout.DIRECTION_VERTICAL)
                .bind();
    }
}
