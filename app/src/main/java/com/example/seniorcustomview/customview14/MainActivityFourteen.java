package com.example.seniorcustomview.customview14;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/11 11:47
 *
 * ViewGroup 的触摸反馈
 **/
public class MainActivityFourteen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_fourteen_touch_view_group);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
