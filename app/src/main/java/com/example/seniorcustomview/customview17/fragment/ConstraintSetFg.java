package com.example.seniorcustomview.customview17.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/25 12:17
 **/
public class ConstraintSetFg extends BaseFragment {
    private ConstraintLayout mConstraintLayout;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_constraint_set_view;
    }

    @Override
    protected void initView(View rootView) {
        mConstraintLayout = rootView.findViewById(R.id.constraint_layout);
        initListener();
    }

    private void initListener() {
        // 通过 ConstraintSet 动态改变布局
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout constraintLayout = (ConstraintLayout) view;
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                /**
                 * startID 要改变的控件的id
                 * 要改变的控件的边界，TOP,BOTTOM,LEFT,RIGHT
                 * 约束条件控件的id
                 * 约束条件控件边界
                 */
                constraintSet.connect(R.id.image_view_set, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);
            }
        });

    }
}
