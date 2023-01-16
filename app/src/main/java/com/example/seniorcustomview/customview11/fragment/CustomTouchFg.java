package com.example.seniorcustomview.customview11.fragment;

import android.view.View;
import android.widget.Toast;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;
import com.example.seniorcustomview.customview11.view.customtouch.CustomTouchView;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:35
 **/
public class CustomTouchFg extends BaseFragment {
    private CustomTouchView mCustomTouchView;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eleven_custom_touch_view;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mCustomTouchView = rootView.findViewById(R.id.custom_touch_view);
        initListener();
    }

    private void initListener() {
        mCustomTouchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "自定义点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
