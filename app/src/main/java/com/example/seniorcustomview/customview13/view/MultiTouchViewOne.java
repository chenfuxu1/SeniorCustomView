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
 * 多点触摸 view
 * 完成需求：手指移动图片也跟着移动
 *
 * 多指触控的事件序列：这里每根手指对应一个事件
 * MotionEvent.ACTION_DOWN; p(x, y, index, id)
 * MotionEvent.ACTION_MOVE; p(x, y, index, id)
 * MotionEvent.ACTION_MOVE; p(x, y, index, id)
 * MotionEvent.ACTION_MOVE; p(x, y, index, id)
 * MotionEvent.ACTION_POINTER_DOWN; p(x, y, index, id) p(x, y, index, id) 表示有第二根手指按下，后续事件都有两个，除非手指抬起
 * MotionEvent.ACTION_MOVE; p(x, y, index, id) p(x, y, index, id)
 * MotionEvent.ACTION_MOVE; p(x, y, index, id) p(x, y, index, id)
 * MotionEvent.ACTION_POINTER_DOWN;
 * MotionEvent.ACTION_MOVE;
 * MotionEvent.ACTION_MOVE;
 * MotionEvent.ACTION_POINTER_UP;
 * MotionEvent.ACTION_MOVE;
 * MotionEvent.ACTION_MOVE;
 * MotionEvent.ACTION_POINTER_UP;
 * MotionEvent.ACTION_MOVE;
 * MotionEvent.ACTION_UP;
 * 1、这些事件都是相对与 view 的，MotionEvent.ACTION_DOWN 表示 view 上有手指按下了，而不是表示有根手指按下了
 * 2、event.getX() 表示该 view 上第一根手指的 x 坐标，等价于 event.getX(0)
 * 3、event.getX(event.getActionIndex()) 获取当前刚刚按下手指的坐标
 * 4、对于 move 事件来说 event.getActionIndex() 总是返回 0，因为不管是一根还是多根手指移动，都是在移动，在 move 过程中
 * event.getActionIndex() 没有意义，不代表正在移动的手指
 **/
public class MultiTouchViewOne extends View {
    private static final int IMAGE_SIZE = (int) Utils.dpToPixel(200);
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mOffsetX = 0f; // 图片 x 方向偏移
    private float mOffsetY = 0f; // 图片 y 方向偏移
    private float mDownX = 0f; // 每次手指按下时的 x 坐标
    private float mDownY = 0f; // 每次手指按下时的 y 坐标
    private float mOriginalOffsetX = 0f; // 图片 x 方向初始偏移
    private float mOriginalOffsetY = 0f; // 图片 y 方向初始偏移

    public MultiTouchViewOne(Context context) {
        this(context, null);
    }

    public MultiTouchViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultiTouchViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();

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
                mOffsetX = event.getX() - mDownX + mOriginalOffsetX;
                mOffsetY = event.getY() - mDownY + mOriginalOffsetY;
                invalidate(); // 重新绘制
                break;
        }
        return true;
    }
}
