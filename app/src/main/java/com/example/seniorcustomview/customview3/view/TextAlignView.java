package com.example.seniorcustomview.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 19:59
 *
 * 设置文字对齐
 **/
public class TextAlignView extends View {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String mText1 = "oprq";
    private String mText2 = "suvw";
    private String mText3 = "居中文字";
    private String mText4 = "abcd";
    private String mText5 = "efgh";
    private FontMetrics mFontMetrics = new FontMetrics();
    private float mWidth;
    private float mHeight;

    {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(100);
        mPaint.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.font));
        mPaint.setFakeBoldText(true);
    }

    public TextAlignView(Context context) {
        this(context, null);
    }

    public TextAlignView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextAlignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TextAlignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 1、绘制靠近左上角的文字
         */
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.LEFT); // 设置文字靠左显示
        mPaint.getFontMetrics(mFontMetrics);
        float textTop = mFontMetrics.top; // 获取文字的顶部偏移
        canvas.drawText(mText1, 0, -textTop, mPaint);

        /**
         * 2、改变字体大小，继续绘制
         * 会发现两次绘制距离左边边界 0 的位置不一样，这个没有办法修改，是文字的固有间距
         */
        mPaint.setTextSize(200);
        canvas.drawText(mText1, 0, Utils.dpToPixel(120), mPaint);

        /**
         * 3、绘制靠近右上角的文字
         */
        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.RIGHT); // 设置文字靠右显示
        canvas.drawText(mText2, mWidth, -textTop, mPaint);
        mPaint.setTextSize(200);
        canvas.drawText(mText2, mWidth, Utils.dpToPixel(120), mPaint);

        /**
         * 4、设置居中的文字
         */
        mPaint.setColor(Color.GREEN);
        mPaint.setTextSize(200);
        mPaint.setTextAlign(Paint.Align.CENTER); // 设置文字居中显示
        mPaint.getFontMetrics(mFontMetrics);
        float ascent = mFontMetrics.ascent;
        float descent = mFontMetrics.descent;
        float centerY = (ascent + descent) / 2;
        canvas.drawText(mText3, mWidth / 2, mHeight / 2 + (-centerY), mPaint);

        /**
         * 5、绘制左下角文字
         */
        mPaint.setColor(Color.CYAN);
        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.LEFT); // 设置文字靠左显示
        canvas.drawText(mText4, 0, mHeight - Utils.dpToPixel(100), mPaint);
        mPaint.setTextSize(200);
        canvas.drawText(mText4, 0, mHeight - mFontMetrics.bottom, mPaint);

        /**
         * 6、绘制有下角文字
         */
        mPaint.setTextSize(100);
        mPaint.setTextAlign(Paint.Align.RIGHT); // 设置文字靠右显示
        canvas.drawText(mText5, mWidth, mHeight - Utils.dpToPixel(100), mPaint);
        mPaint.setTextSize(200);
        canvas.drawText(mText5, mWidth, mHeight- mFontMetrics.bottom, mPaint);
    }
}
