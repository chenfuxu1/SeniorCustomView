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
 **/
public class DetailCandleDrawableView extends View {
    private CandleDrawable mCandleDrawable;

    {
        mCandleDrawable = new CandleDrawable();
    }

    public DetailCandleDrawableView(Context context) {
        this(context, null);
    }

    public DetailCandleDrawableView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailCandleDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DetailCandleDrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制蜡烛图
        // mCandleDrawable.setBounds();
        // mCandleDrawable.draw(canvas);

        /**
         * 绘制完整的蜡烛图信息
         * 这样相当于把具体的绘制信息封装到了 drawable 的绘制中
         * 在 view 中可以用同一个 drawable 来实现不同的绘制效果
         * 这样在自定义 view 的过程中，可以通过自定义 drawable 来实现类似的整个功能
         * 还可以减少 view 的嵌套层级，提升性能
         */
        // mCandleDrawable.draw();
    }
}
