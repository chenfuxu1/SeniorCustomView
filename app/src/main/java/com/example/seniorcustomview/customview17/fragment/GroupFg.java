package com.example.seniorcustomview.customview17.fragment;

import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.Group;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/24 23:25
 *
 * 控制整个组内的 view
 **/
public class GroupFg extends BaseFragment {
    private Button mButton;
    private Group mGroup;
    private boolean mIsShow = true;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_group_view;
    }

    @Override
    protected void initView(View rootView) {
        mButton = rootView.findViewById(R.id.button);
        mGroup = rootView.findViewById(R.id.group);
        initListener();
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsShow) {
                    mGroup.setVisibility(View.GONE);
                    mIsShow = false;
                } else {
                    mGroup.setVisibility(View.VISIBLE);
                    mIsShow = true;
                }
            }
        });
    }
}
