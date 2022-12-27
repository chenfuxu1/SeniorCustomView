package com.example.seniorcustomview.customview9.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/26 23:51
 *
 * I、简单改写已有 view 的尺寸
 *
 * 1、重写 onMeasure()
 * 2、用 getMeasuredWidth() 和 getMeasuredHeight() 获取到测量出的尺寸
 * 3、计算出最终要的尺寸
 * 4、用 setMeasuredDimension(width, height) 把结果保存
 **/
public class SquareImageView extends View {
    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // 1、重写 onMeasure()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 2、用 getMeasuredWidth() 和 getMeasuredHeight() 获取到测量出的尺寸
         * getMeasuredHeight()、getMeasuredWidth() 是测量阶段的期望尺寸
         * getWidth()、getHeight() 是真正布局显示的真时尺寸，在 onMeasure 方法中用 getMeasuredHeight 和
         * getMeasuredWidth 获取尺寸，在其他位置通过 getWidth()、getHeight() 获取
         */

        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        // 3、计算出最终要的尺寸
        int size = Math.min(measuredHeight, measuredWidth);
        // 4、用 setMeasuredDimension(width, height) 把结果保存
        setMeasuredDimension(size, size);
    }

}
