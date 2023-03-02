package com.example.seniorcustomview.customview18.fragment;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import android.transition.Scene;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.base.BaseFragment;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/26 23:41
 *
 * 使用过渡动画的 go 方法，实现两种 xml 布局的过渡动画
 **/
public class TransitionGoFg extends BaseFragment {
    private static final String TAG = "TransitionGoFg";
    private ConstraintLayout mRootView;
    private ConstraintLayout mRoot;
    private ImageView mCover;
    private boolean mToggle = true;
    private RatingBar mRatingBar;
    private TextView mFilmTitle;
    private TextView mFilmDescription;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 创建开始和结束的两个场景
            Scene startScene = Scene.getSceneForLayout(mRoot, R.layout.custom_view_eighteen_transition_go_start_scene_view, getContext());
            Scene endScene = Scene.getSceneForLayout(mRoot, R.layout.custom_view_eighteen_transition_go_end_scene_view, getContext());
            if (mToggle) {
                // 调用 TransitionManager.go 方法执行过渡动画
                TransitionManager.go(endScene);
            } else {
                TransitionManager.go(startScene);
            }
            mToggle = !mToggle;
            // 执行完动画后需要重新绑定一下数据，因为 view 已经被移除了
            bindData();
        }
    };

    @Override
    protected int getResourceId() {
        return R.layout.custom_view_eighteen_transition_go_start_scene_view;
    }

    @Override
    protected void initView(View rootView) {
        mRootView = (ConstraintLayout) rootView;
        bindData();
    }


    private void bindData() {
        mRoot = mRootView;
        mCover = mRoot.findViewById(R.id.image_film_cover);
        mCover.setOnClickListener(mOnClickListener);
        if (mToggle) {
            mRatingBar = mRoot.findViewById(R.id.rating_film_rating);
            mFilmTitle = mRoot.findViewById(R.id.text_film_title);
            mFilmDescription = mRoot.findViewById(R.id.text_film_description);
            mRatingBar.setRating(4.5f);
            mFilmTitle.setText(getString(R.string.film_title));
            mFilmDescription.setText(getString(R.string.film_description));
        }
    }
}
