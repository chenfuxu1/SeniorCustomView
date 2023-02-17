package com.example.seniorcustomview.customview15;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/12 23:03
 *
 * 自定义触摸算法之拖拽
 **/
public class MainActivityFifteen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.custom_view_fifteen_drag_helper_grid_view);

        // setContentView(R.layout.custom_view_fifteen_drag_listener_grid_view);

        // setContentView(R.layout.custom_view_fifteen_drag_to_collect);

        setContentView(R.layout.custom_view_fifteen_drag_up_down);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
