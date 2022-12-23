package com.example.seniorcustomview.customview4;

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
import com.example.seniorcustomview.customview3.fragment.DynamicSportsFg;
import com.example.seniorcustomview.customview3.fragment.MultilineTextFg;
import com.example.seniorcustomview.customview3.fragment.MultilineTextTypeSettingFg;
import com.example.seniorcustomview.customview3.fragment.StaticSportsFg;
import com.example.seniorcustomview.customview3.fragment.TextAlignFg;
import com.example.seniorcustomview.customview4.fragment.ClipByDiagonalFg;
import com.example.seniorcustomview.customview4.fragment.RotateXAndClipFg;
import com.example.seniorcustomview.customview4.fragment.RotateXFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/18 9:30
 *
 * 范围裁切和几何变换
 **/
public class MainActivityFour extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"沿 x 轴旋转", "沿图片中心翻折", "沿图片对角线翻折"};
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
        RotateXFg rotateXFg = new RotateXFg();
        RotateXAndClipFg rotateXAndClipFg = new RotateXAndClipFg();
        ClipByDiagonalFg clipByDiagonalFg = new ClipByDiagonalFg();
        mData.clear();
        mData.add(rotateXFg);
        mData.add(rotateXAndClipFg);
        mData.add(clipByDiagonalFg);

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
