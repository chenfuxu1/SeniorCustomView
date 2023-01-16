package com.example.seniorcustomview.customview11.fragment;

import android.view.View;
import android.widget.Toast;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:23
 **/
public class TouchViewFg extends BaseFragment {
    private View mTouchView;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eleven_touch_view;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mTouchView = rootView.findViewById(R.id.touch_view);
        initListener();
    }

    private void initListener() {
        mTouchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
