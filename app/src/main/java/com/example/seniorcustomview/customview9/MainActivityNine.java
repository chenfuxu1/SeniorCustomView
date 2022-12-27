package com.example.seniorcustomview.customview9;

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
import com.example.seniorcustomview.customview9.fragment.CircleFg;
import com.example.seniorcustomview.customview9.fragment.SquareImageViewFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/26 23:42
 *
 * 自定义布局之尺寸的自定义
 **/
public class MainActivityNine extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"SquareImageView", "CircleView"};
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
        SquareImageViewFg squareImageViewFg = new SquareImageViewFg();
        CircleFg circleFg = new CircleFg();
        mData.clear();
        mData.add(squareImageViewFg);
        mData.add(circleFg);
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
