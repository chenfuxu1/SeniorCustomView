package com.example.seniorcustomview.customview17.fragment;

import android.view.View;
import android.widget.ImageView;

import androidx.constraintlayout.widget.Placeholder;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/25 10:46
 *
 * PlaceHolder 实现占位符
 **/
public class PlaceHolderFg extends BaseFragment {
    private static final String TAG = "PlaceHolderFg";

    private Placeholder mPlaceholder;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlaceholder != null) {
                Logit.d(TAG, "cfx onClick v.getId(): " + v.getId());
                mPlaceholder.setContentId(v.getId());
            }
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_seventeen_item_place_holder_view;
    }

    @Override
    protected void initView(View rootView) {
        mPlaceholder = rootView.findViewById(R.id.place_holder);
        mImageView1 = rootView.findViewById(R.id.image_view_one);
        mImageView2 = rootView.findViewById(R.id.image_view_two);
        mImageView3 = rootView.findViewById(R.id.image_view_three);
        mImageView4 = rootView.findViewById(R.id.image_view_four);
        initListener();
    }

    private void initListener() {
        mImageView1.setOnClickListener(mOnClickListener);
        mImageView2.setOnClickListener(mOnClickListener);
        mImageView3.setOnClickListener(mOnClickListener);
        mImageView4.setOnClickListener(mOnClickListener);
    }

}
