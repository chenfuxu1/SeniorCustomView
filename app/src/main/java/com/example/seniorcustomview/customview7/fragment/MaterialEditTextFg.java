package com.example.seniorcustomview.customview7.fragment;


import android.view.View;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;
import com.example.seniorcustomview.customview7.view.MaterialEditText;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 22:21
 **/
public class MaterialEditTextFg extends BaseFragment {
    private MaterialEditText mMaterialEditText;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seven_material_edit_text_view;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mMaterialEditText = rootView.findViewById(R.id.material_edit_text);
        mMaterialEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMaterialEditText.setUseFloatingHint(true);
            }
        }, 3000);
    }
}
