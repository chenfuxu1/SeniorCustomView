package com.example.seniorcustomview.customview5.view.animset;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/19 23:33
 **/
public class AnimSetLayout extends RelativeLayout {
    private AnimSetView mAnimSetView;
    private TextView mAnimTv;
    private boolean mIsAnimed;

    public AnimSetLayout(Context context) {
        this(context, null);
    }

    public AnimSetLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimSetLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AnimSetLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mAnimSetView = findViewById(R.id.anim_set_view);
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
        if (mAnimSetView != null) {
            if (!mIsAnimed) {
                mAnimSetView.startAnim();
            } else {
                mAnimSetView.resetAnim();
            }
            mIsAnimed = !mIsAnimed;
        }
    }
}
