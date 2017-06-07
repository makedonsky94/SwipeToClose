package com.makedonsky.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "right");
                startActivity(intent);
            }
        });

        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "left");
                startActivity(intent);
            }
        });

        Button horizontal = (Button) findViewById(R.id.horizontal);
        horizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "horizontal");
                startActivity(intent);
            }
        });

        Button bottom = (Button) findViewById(R.id.bottom);
        bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "bottom");
                startActivity(intent);
            }
        });

        Button top = (Button) findViewById(R.id.top);
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "top");
                startActivity(intent);
            }
        });

        Button vertical = (Button) findViewById(R.id.vertical);
        vertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ClosingActivity.class);
                intent.putExtra("swipe", "vertical");
                startActivity(intent);
            }
        });

        Button viewPager = (Button) findViewById(R.id.view_pager);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerActivity.class);
                startActivity(intent);
            }
        });

        Button detailImage = (Button) findViewById(R.id.detail_image);
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailImageActivity.class);
                startActivity(intent);
            }
        });

        Button detailImageViewPager = (Button) findViewById(R.id.detail_image_view_pager);
        detailImageViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerImageActivity.class);
                startActivity(intent);
            }
        });
    }
}
