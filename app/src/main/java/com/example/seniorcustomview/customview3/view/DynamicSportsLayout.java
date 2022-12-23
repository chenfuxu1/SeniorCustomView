package com.example.seniorcustomview.customview3.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 16:27
 **/
public class DynamicSportsLayout extends RelativeLayout {
    private DynamicSportsView mDynamicSportsView;
    private TextView mAnimTv;
    private boolean mIsAnimed;

    public DynamicSportsLayout(Context context) {
        super(context);
    }

    public DynamicSportsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicSportsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DynamicSportsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mDynamicSportsView = findViewById(R.id.dynamic_sports_view);
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
        if (mDynamicSportsView != null) {
            if (!mIsAnimed) {
                mDynamicSportsView.startAnim();
            } else {
                mDynamicSportsView.resetAnim();
            }
            mIsAnimed = !mIsAnimed;
        }
    }
}
