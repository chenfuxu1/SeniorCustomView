package com.example.seniorcustomview.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 20:36
 * <p>
 * 多行文字绘制
 **/
public class MultilineTextView extends View {
    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private String mText = "Vestibulum quis dolor arcu. Proin porttitor, nulla eu pulvinar tincidunt," +
            " purus orci bibendum justo, vel posuere tellus tellus a urna. Duis ultrices pretium elit," +
            " in viverra odio facilisis id. Sed ut ante vitae dui porttitor semper at et massa. Nullam" +
            " ut euismod libero. In sit amet volutpat lorem. Vivamus in viverra odio. In hac habitasse" +
            " platea dictumst. Aenean nec urna congue, ultrices enim eget, lacinia metus. Sed porttitor" +
            " porttitor lorem at laoreet. Curabitur efficitur molestie nisi. Mauris convallis metus quis" +
            " erat ullamcorper, vel faucibus nunc rhoncus. Curabitur tincidunt ac neque id pretium. Duis" +
            " condimentum blandit tincidunt. Vestibulum nec leo libero. Proin tristique porta magna, quis" +
            " sagittis nisi.";
    private StaticLayout mStaticLayout;
    private float mWidth;
    private float mHeight;

    {
        mTextPaint.setTextSize(60);
        mTextPaint.setColor(Color.CYAN);
        mTextPaint.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.satisfy_regular));
    }

    public MultilineTextView(Context context) {
        this(context, null);
    }

    public MultilineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultilineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultilineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getWidth();
        mHeight = getHeight();
        /**
         * 参数 1：文字
         * 参数 2：画笔
         * 参数 3：绘制宽度，在哪里开始换行
         * 参数 4：是文字的对齐方向
         * 参数 5：spacingmult 是行间距的倍数，通常情况下填 1 就好
         * 参数 6：spacingadd 是行间距的额外增加值，通常情况下填 0 就好
         * 参数 7：includepad 是指是否在文字上下添加额外的空间，来避免某些过高的字符的绘制出现越界
         */
        mStaticLayout = new StaticLayout(mText, mTextPaint, (int) mWidth, Layout.Alignment.ALIGN_NORMAL,
                1, 0, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStaticLayout.draw(canvas);
    }
}
