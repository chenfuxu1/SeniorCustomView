package com.example.seniorcustomview.customview3.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 20:36
 * <p>
 * 多行文字排版
 **/
public class MultilineTypeSettingTextView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String mText = "Aliquam lacus mi, fringilla a ligula eget, facilisis tempus lectus." +
            " Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vitae velit nibh. Donec" +
            " et tortor finibus, tempus justo et, maximus felis. Aenean ut tincidunt tellus. Nulla" +
            " facilisi. Mauris libero neque, hendrerit aliquam felis vehicula, convallis convallis" +
            " est. Suspendisse volutpat mi at tortor commodo tincidunt. Phasellus ac diam viverra," +
            " placerat justo vel, pretium elit. Duis et risus sit amet elit tristique convallis et" +
            " et tortor. Donec mi dui, gravida sit amet ultricies ornare, egestas non turpis. Aenean" +
            " elementum dapibus fringilla. Nulla convallis, tellus at feugiat varius, nibh felis" +
            " mattis enim, nec tristique nunc massa eu nulla.";
    private float mWidth;
    private float mHeight;
    private Bitmap mBitmap;
    private static final int BITMAP_WIDTH = (int) Utils.dpToPixel(150);
    private FontMetrics mFontMetrics;
    private float[] mMeasureWidth = new float[]{0f};
    private int mStartPos; // 文字开始绘制位置
    private int mTextCount; // 绘制多少个文字
    private float mVerticalYOffset; // 文字 y 方向偏移量(下一行底部 到 上一行底部的间距)
    private float mMaxWidth; // 绘制文字的最大宽度上限

    {
        mPaint.setTextSize(60);
        mPaint.setColor(Color.GREEN);
        mPaint.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.satisfy_regular));
        mBitmap = getAvatar(BITMAP_WIDTH);
        mFontMetrics = new FontMetrics();
    }

    public MultilineTypeSettingTextView(Context context) {
        this(context, null);
    }

    public MultilineTypeSettingTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultilineTypeSettingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultilineTypeSettingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
         * 1、绘制文字
         */
        mPaint.getFontMetrics(mFontMetrics);

        /**
         * 参数1：要测量的文字
         * 参数2：表示文字的测量方向，true 表示由左往右测量
         * 参数3：maxWidth 是给出的宽度上限
         * 参数4：measuredWidth 是用于接受数据，而不是用于提供数据的：方法测量完成后会把截取的文字宽度（如果宽度没有超限，则为文字总宽度）赋值给 measuredWidth[0]
         * 返回值是截取的文字个数（如果宽度没有超限，则是文字的总个数）
         */
        // int textCount = mPaint.breakText(mText, true, mWidth, mMeasureWidth);
        // canvas.drawText(mText, 0, textCount, 0, 0 - top, mPaint); // 绘制第一行
        // 第二行，应该放到循环中处理
        // int lastCount = textCount;
        // textCount = mPaint.breakText(mText, textCount, mText.length(), true, mWidth, mMeasureWidth);
        // canvas.drawText(mText, lastCount, lastCount + textCount, 0, 0 - top + mPaint.getFontSpacing(), mPaint);

        /**
         * 2、绘制图片
         */
        canvas.drawBitmap(mBitmap, mWidth - BITMAP_WIDTH, Utils.dpToPixel(150), mPaint);

        mStartPos = 0;
        mTextCount = 0;
        mVerticalYOffset = -mFontMetrics.top;
        while (mStartPos < mText.length()) {
            if (mVerticalYOffset + mFontMetrics.bottom < Utils.dpToPixel(150)
                    || mVerticalYOffset + mFontMetrics.top > Utils.dpToPixel(150) + mBitmap.getHeight()) {
                // 当 y 方向偏移加上文字底部的坐标小于图片的顶部，或者当 y 方向偏移加上文字顶部坐标大于图片的低部坐标，绘制宽度为 mWidth
                mMaxWidth = mWidth;
            } else {
                // 否则宽度上限为屏幕宽度减去图片的宽度
                mMaxWidth = mWidth - BITMAP_WIDTH;
            }
            mTextCount = mPaint.breakText(mText, mStartPos, mText.length(), true, mMaxWidth, mMeasureWidth);
            canvas.drawText(mText, mStartPos, mStartPos + mTextCount, 0, mVerticalYOffset, mPaint);
            mVerticalYOffset += mPaint.getFontSpacing();
            mStartPos += mTextCount;
        }


    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.yihu, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.liuyifei, options);
    }
}
