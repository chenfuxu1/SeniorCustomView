package com.example.seniorcustomview.customview17;

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
import com.example.seniorcustomview.customview17.fragment.AverageLayoutFg;
import com.example.seniorcustomview.customview17.fragment.AverageLayoutFg2;
import com.example.seniorcustomview.customview17.fragment.AverageLayoutFg3;
import com.example.seniorcustomview.customview17.fragment.BarrierFg;
import com.example.seniorcustomview.customview17.fragment.BaseLineFg;
import com.example.seniorcustomview.customview17.fragment.CenterImageFg;
import com.example.seniorcustomview.customview17.fragment.CenterOneLineFg;
import com.example.seniorcustomview.customview17.fragment.ChainStylePackedFg;
import com.example.seniorcustomview.customview17.fragment.ChainStyleSpreadFg;
import com.example.seniorcustomview.customview17.fragment.ChainStyleSpreadInsideFg;
import com.example.seniorcustomview.customview17.fragment.CircularPositionFg;
import com.example.seniorcustomview.customview17.fragment.ConstrainedWidthFg;
import com.example.seniorcustomview.customview17.fragment.ConstraintBiasFg;
import com.example.seniorcustomview.customview17.fragment.ConstraintSetCopyFg;
import com.example.seniorcustomview.customview17.fragment.ConstraintSetFg;
import com.example.seniorcustomview.customview17.fragment.DimensionRatioFg;
import com.example.seniorcustomview.customview17.fragment.FlowFg;
import com.example.seniorcustomview.customview17.fragment.GoneMarginFg;
import com.example.seniorcustomview.customview17.fragment.GroupFg;
import com.example.seniorcustomview.customview17.fragment.GuideLineFg;
import com.example.seniorcustomview.customview17.fragment.HelperFg;
import com.example.seniorcustomview.customview17.fragment.LayerFg;
import com.example.seniorcustomview.customview17.fragment.LinearFg;
import com.example.seniorcustomview.customview17.fragment.MessageItemFg;
import com.example.seniorcustomview.customview17.fragment.PlaceHolderFg;
import com.example.seniorcustomview.customview17.fragment.WidthPercentFg;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/20 23:39
 **/
public class MainActivitySeventeen extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    // tabLayout 的标题
    private String[] mTabTitle = {"短信格式", "居中格式", "居中某一条线", "等分空间布局", "等分空间布局2",
            "等分空间布局3", "文字基准线对齐", "相对位置旋转", "文本宽度与控件一致", "聊天信息布局",
            "GoneMargin", "ChainStyleSpread", "ChainStylePacked", "ChainStyleSpreadInside", "DimensionRatio",
            "WidthPercent", "GuideLine", "Group", "Layer", "Barrier", "Helper", "PlaceHolder", "ConstraintSet",
            "Linear", "Flow", "复制另一个xml中的布局约束"};
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
        MessageItemFg messageItemFg = new MessageItemFg();
        CenterImageFg centerImageFg = new CenterImageFg();
        CenterOneLineFg centerOneLineFg = new CenterOneLineFg();
        AverageLayoutFg averageLayoutFg = new AverageLayoutFg();
        AverageLayoutFg2 averageLayoutFg2 = new AverageLayoutFg2();
        AverageLayoutFg3 averageLayoutFg3 = new AverageLayoutFg3();
        BaseLineFg baseLineFg = new BaseLineFg();
        CircularPositionFg circularPositionFg = new CircularPositionFg();
        ConstrainedWidthFg constrainedWidthFg = new ConstrainedWidthFg();
        ConstraintBiasFg constraintBiasFg = new ConstraintBiasFg();
        GoneMarginFg goneMarginFg = new GoneMarginFg();
        ChainStyleSpreadFg chainStyleSpreadFg = new ChainStyleSpreadFg();
        ChainStylePackedFg chainStylePackedFg = new ChainStylePackedFg();
        ChainStyleSpreadInsideFg chainStyleSpreadInsideFg = new ChainStyleSpreadInsideFg();
        DimensionRatioFg dimensionRatioFg = new DimensionRatioFg();
        WidthPercentFg widthPercentFg = new WidthPercentFg();
        GuideLineFg guideLineFg = new GuideLineFg();
        GroupFg groupFg = new GroupFg();
        LayerFg layerFg = new LayerFg();
        BarrierFg barrierFg = new BarrierFg();
        HelperFg helperFg = new HelperFg();
        PlaceHolderFg placeHolderFg = new PlaceHolderFg();
        ConstraintSetFg constraintSetFg = new ConstraintSetFg();
        LinearFg linearFg = new LinearFg();
        FlowFg flowFg = new FlowFg();
        ConstraintSetCopyFg constraintSetCopyFg = new ConstraintSetCopyFg();
        mData.clear();
        mData.add(messageItemFg);
        mData.add(centerImageFg);
        mData.add(centerOneLineFg);
        mData.add(averageLayoutFg);
        mData.add(averageLayoutFg2);
        mData.add(averageLayoutFg3);
        mData.add(baseLineFg);
        mData.add(circularPositionFg);
        mData.add(constrainedWidthFg);
        mData.add(constraintBiasFg);
        mData.add(goneMarginFg);
        mData.add(chainStyleSpreadFg);
        mData.add(chainStylePackedFg);
        mData.add(chainStyleSpreadInsideFg);
        mData.add(dimensionRatioFg);
        mData.add(widthPercentFg);
        mData.add(guideLineFg);
        mData.add(groupFg);
        mData.add(layerFg);
        mData.add(barrierFg);
        mData.add(helperFg);
        mData.add(placeHolderFg);
        mData.add(constraintSetFg);
        mData.add(linearFg);
        mData.add(flowFg);
        mData.add(constraintSetCopyFg);
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
