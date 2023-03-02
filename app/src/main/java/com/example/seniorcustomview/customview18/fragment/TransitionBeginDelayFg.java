package com.example.seniorcustomview.customview18.fragment;

import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/27 23:43
 *
 * 优化 TransitionManager.go 多次绑定的问题
 **/
public class TransitionBeginDelayFg extends BaseFragment {
    private ConstraintLayout mRootView;
    private ImageView mCover;
    private RatingBar mRatingBar;
    private TextView mFilmTitle;
    private TextView mFilmDescription;
    private ConstraintSet mConstraintSet;
    private boolean mToggle = true;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TransitionManager.beginDelayedTransition(mRootView);
            if (mToggle) {
                mConstraintSet.clone(getContext(), R.layout.custom_view_eighteen_transition_go_end_scene_view);
            } else {
                mConstraintSet.clone(getContext(), R.layout.custom_view_eighteen_transition_go_start_scene_view);
            }
            mConstraintSet.applyTo(mRootView);
            mToggle = !mToggle;
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eighteen_transition_go_start_scene_view;
    }

    @Override
    protected void initView(View rootView) {
        mConstraintSet = new ConstraintSet();
        mRootView = (ConstraintLayout) rootView;
        mCover = mRootView.findViewById(R.id.image_film_cover);
        mCover.setOnClickListener(mOnClickListener);
        mRatingBar = mRootView.findViewById(R.id.rating_film_rating);
        mFilmTitle = mRootView.findViewById(R.id.text_film_title);
        mFilmDescription = mRootView.findViewById(R.id.text_film_description);
        mRatingBar.setRating(4.5f);
        mFilmTitle.setText(getString(R.string.film_title));
        mFilmDescription.setText(getString(R.string.film_description));
    }
}
