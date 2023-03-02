package com.example.seniorcustomview.customview18.fragment;

import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/26 23:12
 *
 * 使用过渡动画实现点击放大的效果
 **/
public class ObjectAnimatorFg2 extends BaseFragment {
    private LinearLayout mRoot;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeHeightAndWidth(view);
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eighteen_object_animator_2_view;
    }

    @Override
    protected void initView(View rootView) {
        mRoot = (LinearLayout) rootView;
        mImageView1 = rootView.findViewById(R.id.image_view_one);
        mImageView2 = rootView.findViewById(R.id.image_view_two);
        mImageView3 = rootView.findViewById(R.id.image_view_three);
        initListener();
    }

    private void initListener() {
        mImageView1.setOnClickListener(mOnClickListener);
        mImageView2.setOnClickListener(mOnClickListener);
        mImageView3.setOnClickListener(mOnClickListener);
    }

    private void changeHeightAndWidth(View view) {
        // 执行过渡动画：开始场景 --> 结束场景
        TransitionManager.beginDelayedTransition(mRoot);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.height *= 2;
        layoutParams.width *= 2;
        view.setLayoutParams(layoutParams);
        view.requestLayout();
    }
}
