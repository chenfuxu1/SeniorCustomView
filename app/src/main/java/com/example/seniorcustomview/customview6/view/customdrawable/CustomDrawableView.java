package com.example.seniorcustomview.customview6.view.customdrawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 10:30
 *
 * 使用自定义 drawable 进行绘制
 **/
public class CustomDrawableView extends View {
    private Drawable mDrawable;

    {
        mDrawable = new MeshDrawable();
    }

    public CustomDrawableView(Context context) {
        super(context);
    }

    public CustomDrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 设置 drawable 绘制的区域
        mDrawable.setBounds(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // drawable 进行绘制
        mDrawable.draw(canvas);
    }
}
