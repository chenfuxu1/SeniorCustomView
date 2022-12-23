package com.example.seniorcustomview.customview3.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/17 16:25
 *
 * 动态文字绘制
 * 使用 FontMetrics
 * FontMetrics.ascent 大部分文字的顶部界限
 * FontMetrics.descent 大部分文字的低部界限
 * 保证文字在动态变化时，绘制的区域一直在这个范围内
 **/
public class DynamicSportsView extends View {
    private static final String TAG = "StaticSportsView";
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mWidth;
    private float mHeight;
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private static final float RADIUS = Utils.dpToPixel(180);
    private final RectF mArcRectF = new RectF(); // 进度条圆弧的绘制显示区域
    private static final int START_ANGLE = -90; // 圆弧开始角度
    private static final int SWEEP_ANGLE = 225; // 圆弧扫过的角度
    private final String mText = "剩余时间：";
    private final Rect mTextBounds = new Rect(); // 可以获取包裹文字区域的坐标
    private final FontMetrics mFontMetrics = new FontMetrics();
    private static final int DURATION = 10 * 1000;
    private float mProgress = 60;
    private String mShowText;
    private ObjectAnimator mObjectAnimator;
    private final Interpolator mPathInterporator = new LinearInterpolator();

    {
        mPaint.setColor(CIRCLE_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPaint.setTypeface(ResourcesCompat.getFont(this.getContext(), R.font.font));
    }

    public DynamicSportsView(Context context) {
        this(context, null);
    }

    public DynamicSportsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicSportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DynamicSportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        mShowText = mText + (int) mProgress + "s";
        mPaint.setTextAlign(Paint.Align.CENTER); // 设置文字开始(可以保证 x 方向居中)
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(140);
        // mPaint.getTextBounds(mShowText, 0, mShowText.length(), mTextBounds);
        mPaint.getFontMetrics(mFontMetrics);
        // 计算文字中心的 y 坐标
        // int textCenterY = (mTextBounds.top + mTextBounds.bottom) / 2;
        // Logit.d(TAG, "cfx mTextBounds.top = " + mTextBounds.top + " mTextBounds.bottom = " + mTextBounds.bottom);

        float textCenterY = (mFontMetrics.ascent + mFontMetrics.descent) / 2;
        Logit.d(TAG, "cfx mFontMetrics.ascent = " + mFontMetrics.ascent + " mFontMetrics.descent = " + mFontMetrics.descent);
        /**
         * 为了在 y 方向上居中显示，设置绘制的 y 坐标需要加上文字中心 y 坐标的偏移 textCenterY
         */
        canvas.drawText(mShowText, mWidth / 2, mHeight / 2 + (-textCenterY), mPaint);

        // 4、重置画笔的状态
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @SuppressLint("ObjectAnimatorBinding")
    public void setProgress(float progress) {
        this.mProgress = progress;
        invalidate();
    }

    // 开始执行动画
    public void startAnim() {
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
        mObjectAnimator = ObjectAnimator.ofFloat(this, "progress", 60, 0);
        mObjectAnimator.setInterpolator(mPathInterporator);
        mObjectAnimator.setDuration(DURATION);
        mObjectAnimator.start();
    }

    // 重置动画状态
    public void resetAnim() {
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
        mObjectAnimator = ObjectAnimator.ofFloat(this, "progress", 0, 60);
        mObjectAnimator.setDuration(1000);
        mObjectAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
    }
}
