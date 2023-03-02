package com.example.seniorcustomview.customview18;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.adapter.ViewPager2Adapter;
import com.example.seniorcustomview.base.BaseFragment;
import com.example.seniorcustomview.customview18.fragment.MotionLayoutFg;
import com.example.seniorcustomview.customview18.fragment.MotionLayoutFg2;
import com.example.seniorcustomview.customview18.fragment.ObjectAnimatorFg;
import com.example.seniorcustomview.customview18.fragment.ObjectAnimatorFg2;
import com.example.seniorcustomview.customview18.fragment.TransitionBeginDelayFg;
import com.example.seniorcustomview.customview18.fragment.TransitionGoFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/26 22:37
 **/
public class MainActivityEighteen extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"ObjectAnimator", "点击放大", "Go方法", "优化Go方法", "MotionLayout",
        "MotionLayout2"};
    private ViewPager2Adapter mAdapter;
    private List<BaseFragment> mData = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        ObjectAnimatorFg objectAnimatorFg = new ObjectAnimatorFg();
        ObjectAnimatorFg2 objectAnimatorFg2 = new ObjectAnimatorFg2();
        TransitionGoFg transitionGoFg = new TransitionGoFg();
        TransitionBeginDelayFg transitionBeginDelayFg = new TransitionBeginDelayFg();
        MotionLayoutFg motionLayoutFg = new MotionLayoutFg();
        MotionLayoutFg2 motionLayoutFg2 = new MotionLayoutFg2();
        mData.add(objectAnimatorFg);
        mData.add(objectAnimatorFg2);
        mData.add(transitionGoFg);
        mData.add(transitionBeginDelayFg);
        mData.add(motionLayoutFg);
        mData.add(motionLayoutFg2);
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager);
        mAdapter = new ViewPager2Adapter(this);
        mAdapter.addDatas(mData);
        mViewPager2.setAdapter(mAdapter);
        // 设置横向滑动
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // 设置 tabLayout 的标题
                        tab.setText(mTabTitle[position]);
                    }
                });
        // 应用生效
        tabLayoutMediator.attach();
        View child = mViewPager2.getChildAt(0);
        if (child instanceof RecyclerView) {
            // 移除 viewpager 的阴影
            child.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    private void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
