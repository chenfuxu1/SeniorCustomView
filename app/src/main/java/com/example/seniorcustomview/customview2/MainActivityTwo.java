package com.example.seniorcustomview.customview2;

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
import com.example.seniorcustomview.customview2.fragment.AvatarFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeClearFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeDstAtopFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeDstFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeDstInFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeDstOutFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeDstOverFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeSrcAtopFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeSrcFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeSrcInFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeSrcOutFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeSrcOverFg;
import com.example.seniorcustomview.customview2.fragment.XfermodeXorFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/12 23:43
 * <p>
 * Xfermode 完全使用解析
 *
 * PorterDuff.Mode.SRC          只显示源图
 * PorterDuff.Mode.SRC_OVER     源图覆盖在目标图上方
 * PorterDuff.Mode.SRC_IN       源图显示在目标区域内部
 * PorterDuff.Mode.SRC_ATOP     以目标区域为准，重叠部分显示源图
 * PorterDuff.Mode.DST          只显示目标图
 * PorterDuff.Mode.DST_OVER     目标图覆盖在源图上方
 * PorterDuff.Mode.DST_IN       目标图显示在源图区域范围内
 * PorterDuff.Mode.DST_ATOP     以源图为准，重叠部分显示目标图
 * PorterDuff.Mode.CLEAR        都不显示
 * PorterDuff.Mode.SRC_OUT      取目标图之外的所有内容
 * PorterDuff.Mode.DST_OUT      取源图之外的所有内容
 * PorterDuff.Mode.XOR          目标图与源图重叠部分不显示
 **/
public class MainActivityTwo extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"AvatarView", "XfermodeSrc", "XfermodeSrcOver", "XfermodeSrcIn",
            "XfermodeSrcAtop", "XfermodeViewDst", "XfermodeViewDstOver", "XfermodeViewDstIn",
            "XfermodeViewDstAtop", "XfermodeViewClear", "XfermodeViewSrcOut", "XfermodeViewDstOut",
            "XfermodeViewXor"};
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
        AvatarFg avatarFg = new AvatarFg();
        XfermodeSrcFg xfermodeSrcFg = new XfermodeSrcFg();
        XfermodeSrcOverFg xfermodeSrcOverFg = new XfermodeSrcOverFg();
        XfermodeSrcInFg xfermodeSrcInFg = new XfermodeSrcInFg();
        XfermodeSrcAtopFg xfermodeSrcAtopFg = new XfermodeSrcAtopFg();
        XfermodeDstFg xfermodeDstFg = new XfermodeDstFg();
        XfermodeDstOverFg xfermodeDstOverFg = new XfermodeDstOverFg();
        XfermodeDstInFg xfermodeDstInFg = new XfermodeDstInFg();
        XfermodeDstAtopFg xfermodeDstAtopFg = new XfermodeDstAtopFg();
        XfermodeClearFg xfermodeClearFg = new XfermodeClearFg();
        XfermodeSrcOutFg xfermodeSrcOutFg = new XfermodeSrcOutFg();
        XfermodeDstOutFg xfermodeDstOutFg = new XfermodeDstOutFg();
        XfermodeXorFg xfermodeXorFg = new XfermodeXorFg();

        mData.clear();
        mData.add(avatarFg);
        mData.add(xfermodeSrcFg);
        mData.add(xfermodeSrcOverFg);
        mData.add(xfermodeSrcInFg);
        mData.add(xfermodeSrcAtopFg);
        mData.add(xfermodeDstFg);
        mData.add(xfermodeDstOverFg);
        mData.add(xfermodeDstInFg);
        mData.add(xfermodeDstAtopFg);
        mData.add(xfermodeClearFg);
        mData.add(xfermodeSrcOutFg);
        mData.add(xfermodeDstOutFg);
        mData.add(xfermodeXorFg);
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
