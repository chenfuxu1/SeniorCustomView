package com.example.seniorcustomview.customview6.view.candledrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 13:24
 *
 * 简单版本的蜡烛图
 **/
public class SimpleCandleDrawableView extends View {
    private CandleDrawable mCandleDrawable;

    {
        mCandleDrawable = new CandleDrawable();
    }

    public SimpleCandleDrawableView(Context context) {
        this(context, null);
    }

    public SimpleCandleDrawableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleCandleDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SimpleCandleDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制蜡烛图
        // mCandleDrawable.setBounds();
        // mCandleDrawable.draw(canvas);
    }
}
