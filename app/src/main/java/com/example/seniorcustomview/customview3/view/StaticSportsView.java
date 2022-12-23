package com.example.seniorcustomview.customview3.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 13:02
 *
 * 绘制静态的文字
 * mTextBounds 可以获取显示文字的边界坐标偏移
 * 这种方式适合绘制静态不动的文字，如果文字是动态变化的，那么使用这种方式会觉得文字可能在
 * 上下微弱的跳动，用户体验不是太好
 * 那么如果是动态的文字应该如何处理呢？
 **/
public class StaticSportsView extends View {
    private static final String TAG = "StaticSportsView";
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mWidth;
    private float mHeight;
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private static final float RADIUS = Utils.dpToPixel(180);
    private RectF mArcRectF = new RectF(); // 进度条圆弧的绘制显示区域
    private static final int START_ANGLE = -90; // 圆弧开始角度
    private static final int SWEEP_ANGLE = 225; // 圆弧扫过的角度
    private String mText = "运动健康";
    private Rect mTextBounds = new Rect(); // 可以获取包裹文字区域的坐标

    {
        mPaint.setColor(CIRCLE_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPaint.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.font));
    }

    public StaticSportsView(Context context) {
        this(context, null);
    }

    public StaticSportsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StaticSportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StaticSportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getWidth();
        mHeight = getHeight();

        // 进度条圆弧的绘制显示区域
        mArcRectF.left = mWidth / 2 - RADIUS;
        mArcRectF.top = mHeight / 2 - RADIUS;
        mArcRectF.right = mWidth / 2 + RADIUS;
        mArcRectF.bottom = mHeight / 2 + RADIUS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1、画灰色的圆环
        mPaint.setColor(CIRCLE_COLOR);
        canvas.drawCircle(mWidth / 2, mHeight / 2, RADIUS, mPaint);

        // 2、绘制进度条
        mPaint.setColor(HIGHLIGHT_COLOR);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(mArcRectF, START_ANGLE, SWEEP_ANGLE, false, mPaint);

        // 3、绘制中心的文字
        mPaint.setTextAlign(Paint.Align.CENTER); // 设置文字开始(可以保证 x 方向居中)
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(220);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextBounds);
        // 计算文字中心的 y 坐标
        int textCenterY = (mTextBounds.top + mTextBounds.bottom) / 2;
        Logit.d(TAG, "cfx mTextBounds.top = " + mTextBounds.top + " mTextBounds.bottom = " + mTextBounds.bottom);
        /**
         * 为了在 y 方向上居中显示，设置绘制的 y 坐标需要加上文字中心 y 坐标的偏移 textCenterY
         */
        canvas.drawText(mText, mWidth / 2, mHeight / 2 + (-textCenterY), mPaint);

        // 4、重置画笔的状态
        mPaint.setStyle(Paint.Style.STROKE);
    }
}
