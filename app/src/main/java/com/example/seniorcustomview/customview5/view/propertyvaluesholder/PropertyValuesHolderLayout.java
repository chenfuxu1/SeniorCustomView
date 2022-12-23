package com.example.seniorcustomview.customview5.view.propertyvaluesholder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.customview5.view.animset.AnimSetView;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/20 23:52
 **/
public class PropertyValuesHolderLayout extends RelativeLayout {
    private PropertyValuesHolderView mPropertyValuesHolderView;
    private TextView mAnimTv;
    private boolean mIsAnimed;

    public PropertyValuesHolderLayout(Context context) {
        this(context, null);
    }

    public PropertyValuesHolderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PropertyValuesHolderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mPropertyValuesHolderView = findViewById(R.id.property_values_holder_view);
        mAnimTv = findViewById(R.id.anim_tv);
    }

    private void initListener() {
        mAnimTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                executeAnim();
            }
        });
    }

    private void executeAnim() {
        if (mPropertyValuesHolderView != null) {
            if (!mIsAnimed) {
                mPropertyValuesHolderView.startAnim();
            } else {
                mPropertyValuesHolderView.resetAnim();
            }
            mIsAnimed = !mIsAnimed;
        }
    }
}
