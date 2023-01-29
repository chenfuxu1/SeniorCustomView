package com.example.seniorcustomview.customview12;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:19
 *
 * 双向滑动的 ScalableImageView
 **/
public class MainActivityTwelve extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_twelve_scalable_view);
        initView();
    }


    private void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
