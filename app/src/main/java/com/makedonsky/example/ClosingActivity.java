package com.makedonsky.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.makedonsky.widget.SwipeLayout;
import com.makedonsky.widget.SwipeToClose;


public class ClosingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();

        String direction = bundle.getString("swipe", "right");

        int directionIndex = 0;
        String header = "";

        switch (direction) {
            case "right":
                directionIndex = SwipeLayout.DIRECTION_RIGHT;
                header = getString(R.string.info_action_right);
                break;
            case "left":
                directionIndex = SwipeLayout.DIRECTION_LEFT;
                header = getString(R.string.info_action_left);
                break;
            case "top":
                directionIndex = SwipeLayout.DIRECTION_TOP;
                header = getString(R.string.info_action_top);
                break;
            case "bottom":
                directionIndex = SwipeLayout.DIRECTION_BOTTOM;
                header = getString(R.string.info_action_bottom);
                break;
        }

        setTitle(header);

        SwipeToClose
                .with(this)
                .withShadowColor(android.R.color.black)
                .withDirection(directionIndex)
                .bind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
