package com.example.seniorcustomview.customview10.fragment;

import android.view.View;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;
import com.example.seniorcustomview.customview10.view.flowview.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/1 20:39
 **/
public class FlowFg extends BaseFragment {
    private List<String> mProvinces = new ArrayList<>(Arrays.asList(
            "北京市",
            "天津市",
            "上海市",
            "重庆市",
            "河北省",
            "山西省",
            "辽宁省",
            "吉林省",
            "黑龙江省",
            "江苏省",
            "浙江省",
            "安徽省",
            "福建省",
            "江西省",
            "山东省",
            "河南省",
            "湖北省",
            "湖南省",
            "广东省",
            "海南省",
            "四川省",
            "贵州省",
            "云南省",
            "陕西省",
            "甘肃省",
            "青海省",
            "台湾省",
            "内蒙古自治区",
            "广西壮族自治区",
            "西藏自治区",
            "宁夏回族自治区",
            "新疆维吾尔自治区",
            "香港特别行政区",
            "澳门特别行政区"));
    private FlowLayout mFlowLayout;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_ten_flow_layout;
    }

    @Override
    protected void initView(View rootView) {
        super.initView(rootView);
        mFlowLayout = rootView.findViewById(R.id.flow_layout);
        mFlowLayout.setData(mProvinces);
    }
}
