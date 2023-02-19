package com.example.seniorcustomview.customview16;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/18 10:53
 *
 * 嵌套滑动
 **/
public class MainActivitySixteen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.custom_view_sixteen_nested_scroll_view);

        setContentView(R.layout.custom_view_sixteen_nested_scalable_image_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
