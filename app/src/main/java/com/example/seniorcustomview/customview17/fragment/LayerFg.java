package com.example.seniorcustomview.customview17.fragment;

import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.helper.widget.Layer;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/24 23:48
 * <p>
 * 同 Group 类似，也是将 view 放入一个组内
 **/
public class LayerFg extends BaseFragment {
    private static final String TAG = "LayerFg";
    private Button mButton;
    private Layer mLayer;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_layer_view;
    }

    @Override
    protected void initView(View rootView) {
        mButton = rootView.findViewById(R.id.button);
        mLayer = rootView.findViewById(R.id.layer);
        initListener();
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeAnim();
            }
        });
    }

    private void executeAnim() {
        mLayer.animate().translationX(500f).setDuration(1000).start();
    }
}
