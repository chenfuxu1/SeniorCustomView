package com.example.seniorcustomview.customview5.view.province;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/21 21:40
 **/
public class ProvinceLayout extends RelativeLayout {
    private ProvinceView mProvinceView;
    private TextView mAnimTv;
    private boolean mIsAnimed;

    public ProvinceLayout(Context context) {
        this(context, null);
    }

    public ProvinceLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProvinceLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProvinceLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mProvinceView = findViewById(R.id.province_view);
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
        if (mProvinceView != null) {
            if (!mIsAnimed) {
                mProvinceView.startAnim();
            } else {
                mProvinceView.resetAnim();
            }
            mIsAnimed = !mIsAnimed;
        }
    }
}
