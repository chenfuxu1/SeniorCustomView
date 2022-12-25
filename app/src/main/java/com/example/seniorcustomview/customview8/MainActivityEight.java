package com.example.seniorcustomview.customview8;

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
import com.example.seniorcustomview.customview8.fragment.OneHundredFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/25 21:27
 *
 * 布局流程解析
 *
 * 流程：整体
 * 从整体看：
 *      测量流程：从根 view 递归调用每一级子 view 的 measure() 方法，对它们进行测量
 *      布局流程：从根 view 递归调用每一级子 view 的 layout(0 方法，把测量过程得出的子 view 的位置和尺寸
 *      传给子 view，子 view 保存
 *
 *      为什么要分两个流程？
 *      因为测量需要多次，子 view 的测量可能需要多次才能确定最终的尺寸
 *
 * 从个体看，对于每个 view：
 *      1、运行前，开发者在 xml 文件里写入对 view 的布局要求 layout_xxx
 *      2、父 view 在自己的 onMeasure() 中，根据开发者在 xml 中写的对子 view 的要求，和自己的可用空间，
 *      得出对子 view 的具体尺寸要求
 *      3、子 view 在自己的 onMeasure() 中，根据父 view 的要求以及自己的特性算出自己的期望尺寸
 *      如果是 viewGroup，还会在这里调用每个子 view 的 measure() 进行测量
 *      4、父 view 在子 view 计算出期望尺寸后，得出子 view 的实际尺寸和位置
 *      5、子 view 在自己的 layout() 方法中，将父 view 传进来的自己的实际尺寸和位置保存(左上右下坐标)
 *      如果是 viewGroup，还会在 onLayout() 里调用每个子 view 的 layout() 把它们的尺寸位置传给它们
 **/
public class MainActivityEight extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"OneHundredView"};
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
        OneHundredFg oneHundredFg = new OneHundredFg();
        mData.clear();
        mData.add(oneHundredFg);
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
