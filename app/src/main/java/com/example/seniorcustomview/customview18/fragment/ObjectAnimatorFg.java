package com.example.seniorcustomview.customview18.fragment;

import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/26 22:54
 *
 * 点击实现类似属性动画
 **/
public class ObjectAnimatorFg extends BaseFragment {
    private ImageView mImageView;
    private FrameLayout mRoot;

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eighteen_object_animator_view;
    }

    @Override
    protected void initView(View rootView) {
        mRoot = rootView.findViewById(R.id.root);
        mImageView = rootView.findViewById(R.id.heart);
        initListener();
    }

    private void initListener() {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeAnimator(view);
            }
        });
    }

    private void executeAnimator(View view) {
        // 执行过渡动画
        TransitionManager.beginDelayedTransition(mRoot);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.height *= 2;
        layoutParams.width *= 2;
        mImageView.setLayoutParams(layoutParams);
        view.requestLayout();
    }
}
