package com.example.seniorcustomview.customview17.fragment;

import android.transition.TransitionManager;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/26 14:48
 *
 * 通过 ConstraintSet 复制另外一个布局中的约束条件
 **/
public class ConstraintSetCopyFg extends BaseFragment {
    private ConstraintLayout mConstraintLayout;
    private ConstraintSet mConstraintSet;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_constraint_start_view;
    }

    @Override
    protected void initView(View rootView) {
        mConstraintLayout = rootView.findViewById(R.id.constraint_start);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.setForceId(false);
        initListener();
    }

    private void initListener() {
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLayout(view);
            }
        });
    }

    /**
     * 改变布局约束
     */
    private void changeLayout(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) view;
        // 复制另一个 xml 布局中的约束条件
        mConstraintSet.clone(getContext(), R.layout.custom_view_seventeen_item_constraint_end_view);
        TransitionManager.beginDelayedTransition(constraintLayout);
        mConstraintSet.applyTo(constraintLayout);
    }
}
