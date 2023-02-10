package com.example.seniorcustomview.customview13.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/7 23:07
 *
 * 多点触摸 view，协作型
 * 完成需求：手指移动图片也跟着移动
 *
 **/
public class MultiTouchViewThree extends View {
    private static final int IMAGE_SIZE = (int) Utils.dpToPixel(200);
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mOffsetX = 0f; // 图片 x 方向偏移
    private float mOffsetY = 0f; // 图片 y 方向偏移
    private float mDownX = 0f; // 每次手指按下时的 x 坐标
    private float mDownY = 0f; // 每次手指按下时的 y 坐标
    private float mOriginalOffsetX = 0f; // 图片 x 方向初始偏移
    private float mOriginalOffsetY = 0f; // 图片 y 方向初始偏移

    public MultiTouchViewThree(Context context) {
        this(context, null);
    }

    public MultiTouchViewThree(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchViewThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultiTouchViewThree(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mBitmap = Utils.getAvatar(IMAGE_SIZE, R.mipmap.liuyifei);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mOffsetX, mOffsetY, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 协作型需要获取到多个点的中心坐标
         */
        float centerX = 0f;
        float centerY = 0f;
        int pointerCount = event.getPointerCount();
        float sumX = 0f;
        float sumY = 0f;
        /**
         * 判断是否是抬起事件，因为当次抬起时，pointerCount 还没有减 1，会形成多加和多除了
         */
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;
        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }

        }
        if (isPointerUp) {
            pointerCount -= 1;
        }
        centerX = sumX / pointerCount;
        centerY = sumY / pointerCount;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: // 第 2 根手指按下时
            case MotionEvent.ACTION_POINTER_UP: // 非最后一根手指抬起事件
                mDownX = centerX;
                mDownY = centerY;
                /**
                 * 每次按下时，图片的初始偏移就是上次的 mOffsetX、mOffsetY 偏移
                 */
                mOriginalOffsetX = mOffsetX;
                mOriginalOffsetY = mOffsetY;
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 移动过程中，更改 x，y 偏移值
                 * 图片的偏移量 = 上次手指抬起后的初始偏移 + 手指移动过程中坐标减去 down 事件按下时的坐标值
                 */
                mOffsetX = centerX - mDownX + mOriginalOffsetX;
                mOffsetY = centerY - mDownY + mOriginalOffsetY;
                invalidate(); // 重新绘制
                break;
        }
        return true;
    }
}
