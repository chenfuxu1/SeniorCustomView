package com.example.seniorcustomview.customview3;

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
import com.example.seniorcustomview.customview3.fragment.DynamicSportsFg;
import com.example.seniorcustomview.customview3.fragment.MultilineTextFg;
import com.example.seniorcustomview.customview3.fragment.MultilineTextTypeSettingFg;
import com.example.seniorcustomview.customview3.fragment.StaticSportsFg;
import com.example.seniorcustomview.customview3.fragment.TextAlignFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/15 23:09
 * <p>
 * ???????????????
 **/
public class MainActivityThree extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout ?????????
    private String[] mTabTitle = {"??????????????????", "??????????????????", "????????????", "??????????????????",
            "??????????????????"};
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
        StaticSportsFg staticSportsFg = new StaticSportsFg();
        DynamicSportsFg dynamicSportsFg = new DynamicSportsFg();
        TextAlignFg textAlignFg = new TextAlignFg();
        MultilineTextFg multilineTextFg = new MultilineTextFg();
        MultilineTextTypeSettingFg multilineTextTypeSettingFg = new MultilineTextTypeSettingFg();
        mData.clear();
        mData.add(staticSportsFg);
        mData.add(dynamicSportsFg);
        mData.add(textAlignFg);
        mData.add(multilineTextFg);
        mData.add(multilineTextTypeSettingFg);

    }

    private void initView() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager2 = findViewById(R.id.view_pager);
        mAdapter = new ViewPager2Adapter(this);
        mAdapter.addDatas(mData);
        mViewPager2.setAdapter(mAdapter);
        // ??????????????????
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mViewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        // ?????? tabLayout ?????????
                        tab.setText(mTabTitle[position]);
                    }
                });
        // ????????????
        tabLayoutMediator.attach();
        View child = mViewPager2.getChildAt(0);
        if (child instanceof RecyclerView) {
            // ?????? viewpager ?????????
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
