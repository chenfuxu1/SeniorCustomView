package com.example.seniorcustomview.customview13;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:19
 *
 * 多点触控
 **/
public class MainActivityThirteen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_thirteen_multi_point_touch_view);
        initView();
    }


    private void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
