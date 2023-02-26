package com.example.seniorcustomview.customview17.helper;

import android.content.Context;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.VirtualLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/25 13:00
 *
 * 这也是一个 Helper，动态实现线性布局
 **/
public class Linear extends VirtualLayout {
    private ConstraintSet mConstraintSet;

    public Linear(Context context) {
        this(context, null);
    }

    public Linear(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Linear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mConstraintSet = new ConstraintSet();
        mConstraintSet.setForceId(false);
    }

    // 布局之前
    @Override
    public void updatePreLayout(ConstraintLayout container) {
        super.updatePreLayout(container);
        mConstraintSet.clone(container);
        for (int i = 1; i < getReferencedIds().length; i++) {
            int current = getReferencedIds()[i];
            int before = getReferencedIds()[i -1];
            /**
             * startID 要改变的控件的id
             * 要改变的控件的边界，TOP,BOTTOM,LEFT,RIGHT
             * 约束条件控件的id
             * 约束条件控件边界
             */
            mConstraintSet.connect(current, ConstraintSet.START, before, ConstraintSet.START);
            mConstraintSet.connect(current, ConstraintSet.TOP, before, ConstraintSet.BOTTOM);
            mConstraintSet.applyTo(container);
        }
    }
}
