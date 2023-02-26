package com.example.seniorcustomview.customview17.fragment;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/25 10:26
 *
 * 将动画通过 helper 声明出来，哪个控件需要使用，添加进去即可
 * 可以实现动画的复用
 **/
public class HelperFg extends BaseFragment {

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_helper_view;
    }
}
