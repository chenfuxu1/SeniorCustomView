package com.example.seniorcustomview.customview5.view.keyframe;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/21 12:51
 **/
public class KeyFrameLayout extends RelativeLayout {
    private KeyFrameView mKeyFrameView;
    private TextView mAnimTv;
    private boolean mIsAnimed;
    private static final float LENGTH = Utils.dpToPixel(200);
    private Keyframe mKeyframe1;
    private Keyframe mKeyframe2;
    private Keyframe mKeyframe3;
    private Keyframe mKeyframe4;
    private PropertyValuesHolder mValuesHolder;
    private ObjectAnimator mObjectAnimator;

    public KeyFrameLayout(Context context) {
        this(context, null);
    }

    public KeyFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public KeyFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mKeyFrameView = findViewById(R.id.key_frame_view);
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
        if (!mIsAnimed) {
            startAnim();
        } else {
            resetAnim();
        }
        mIsAnimed = !mIsAnimed;
    }

    private void resetAnim() {

    }

    private void startAnim() {
        reset();
        mKeyframe1 = Keyframe.ofFloat(0f, 0f);
        mKeyframe2 = Keyframe.ofFloat(0.2f, 0.4f * LENGTH);
        mKeyframe3 = Keyframe.ofFloat(0.8f, 0.6f * LENGTH);
        mKeyframe4 = Keyframe.ofFloat(1f, 1f * LENGTH);
        mValuesHolder = PropertyValuesHolder.ofKeyframe("translationX", mKeyframe1, mKeyframe2, mKeyframe3, mKeyframe4);
        mObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(mKeyFrameView, mValuesHolder);
        mObjectAnimator.setDuration(1000);
        mObjectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    private void reset() {
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
    }
}
