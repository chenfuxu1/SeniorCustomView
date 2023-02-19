package com.example.seniorcustomview.customview16.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/18 13:12
 **/
public class NestedScalableImageView extends View implements NestedScrollingChild3 {
    private static final int IMAGE_SIZE = (int) Utils.dpToPixel(300);
    private static final float EXTRA_SCALE_FACTOR = 1.5f; // 放缩系数

    private float mOriginalOffsetX = 0f; // 初始图片 x 方向偏移
    private float mOriginalOffsetY = 0f; // 初始图片 y 方向偏移
    private float mFingerOffsetX = 0f; // 手指移动图片 x 方向偏移
    private float mFingerOffsetY = 0f; // 手指移动图片 y 方向偏移
    /**
     * 填充屏幕的小系数, 就是看图片是宽胖型还是高瘦型，如果是宽胖型，那么小的 scale 是宽度方向，宽度方向先到边界
     * 如果是高瘦型，那么小的 scale 的高度方向，因为高度方向先到边界
     */
    private float mSmallScale = 0f;
    private float mBigScale = 0f; // 填充屏幕的大系数
    private float mCurrentScale = 0f; // 当前的 scale
    private float mScaleFraction = 0f;

    private boolean mIsBigOrSmallScale = false; // 默认是小的 scale

    private Paint mPaint;
    private Bitmap mBitmap;
    private GestureListener mGestureListener; // 给 mGestureDetectorCompat 设置双击监听
    private FlingRunner mFlingRunner; // 执行快滑的刷新任务
    private ScaleGestureListener mScaleGestureListener; // 双指缩放的监听
    private ScaleGestureDetector mScaleGestureDetector; // 双指缩放
    private GestureDetectorCompat mGestureDetectorCompat;
    private OverScroller mOverScroller;
    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private ObjectAnimator mScaleAnimator;


    public NestedScalableImageView(Context context) {
        this(context, null);
    }

    public NestedScalableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestedScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NestedScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = Utils.getAvatar(IMAGE_SIZE, R.mipmap.liuyifei);
        mGestureListener = new GestureListener();
        mFlingRunner = new FlingRunner();
        mScaleGestureListener = new ScaleGestureListener();
        mScaleGestureDetector = new ScaleGestureDetector(context, mScaleGestureListener);
        mOverScroller = new OverScroller(context);
        mGestureDetectorCompat = new GestureDetectorCompat(context, mGestureListener); // 给 mGestureDetectorCompat 设置双击监听
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
        mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", mSmallScale, mBigScale);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOriginalOffsetX = (float) (getWidth() - IMAGE_SIZE) / 2;
        mOriginalOffsetY = (float) (getHeight() - IMAGE_SIZE) / 2;

        float tempSmallScale = 0f;
        float tempBigScale = 0f;

        if ((float) mBitmap.getWidth() / mBitmap.getHeight() > (float) getWidth() / getHeight()) {
            // 如果图片的宽高比大于 view 的宽高比，表明图片是宽胖型的
            tempSmallScale = (float) getWidth() / mBitmap.getWidth();
            tempBigScale = (float) getHeight() / (float) mBitmap.getHeight();
        } else {
            // 如果图片的宽高比小于 view 的宽高比，表明图片是瘦高型的
            tempSmallScale = (float) getHeight() / mBitmap.getHeight();
            tempBigScale = (float) getWidth() / mBitmap.getWidth();
        }
        mSmallScale = Math.max(tempSmallScale, mSmallScale);
        mBigScale = Math.max(tempBigScale, mBigScale) * EXTRA_SCALE_FACTOR;
        mCurrentScale = mSmallScale;
        mScaleAnimator.setFloatValues(mSmallScale, mBigScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        if (!mScaleGestureDetector.isInProgress()) {
            mGestureDetectorCompat.onTouchEvent(event);
            mNestedScrollingChildHelper.startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mScaleFraction = (mCurrentScale - mSmallScale) / (mBigScale - mSmallScale);
        // 3、移动图片的位置
        canvas.translate(mFingerOffsetX * mScaleFraction, mFingerOffsetY * mScaleFraction);
        // 2、开始放缩
        canvas.scale(mCurrentScale, mCurrentScale, getWidth() / 2f, getHeight() / 2f);
        // 1、倒着想，先画图
        canvas.drawBitmap(mBitmap, mOriginalOffsetX, mOriginalOffsetY, mPaint);
    }

    public float getCurrentScale() {
        return mCurrentScale;
    }

    public void setCurrentScale(float currentScale) {
        mCurrentScale = currentScale;
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mScaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", mSmallScale, mBigScale);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mScaleAnimator != null) {
            mScaleAnimator.cancel();
            mScaleAnimator = null;
        }
    }

    @Override
    public void dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type, @NonNull int[] consumed) {
        mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type, consumed);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mNestedScrollingChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mNestedScrollingChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mNestedScrollingChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    private int fixOffsets() {
        float rawOffsetY = mFingerOffsetY;
        float maxOffsetY = (mBitmap.getHeight() * mBigScale - getHeight()) / 2;
        mFingerOffsetX = Math.min(mFingerOffsetX, (mBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mFingerOffsetX = Math.max(mFingerOffsetX, -(mBitmap.getWidth() * mBigScale - getWidth()) / 2);
        mFingerOffsetY = Math.min(mFingerOffsetY, maxOffsetY);
        mFingerOffsetY = Math.max(mFingerOffsetY, -maxOffsetY);
        if (rawOffsetY < -maxOffsetY) {
            return (int) (-maxOffsetY - rawOffsetY);
        } else if (rawOffsetY > maxOffsetY) {
            return (int) (maxOffsetY - rawOffsetY);
        } else {
            return 0;
        }
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (mIsBigOrSmallScale) {
                mOverScroller.fling((int) mFingerOffsetX, (int) mFingerOffsetY, (int) velocityX, (int) velocityY,
                        (int) (-(mBitmap.getWidth() * mBigScale - getWidth()) / 2),
                        (int) ((mBitmap.getWidth() * mBigScale - getWidth()) / 2),
                        (int) (-(mBitmap.getHeight() * mBigScale - getHeight()) / 2),
                        (int) ((mBitmap.getHeight() * mBigScale - getHeight()) / 2));
                ViewCompat.postOnAnimation(NestedScalableImageView.this, mFlingRunner);
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mIsBigOrSmallScale) {
                mFingerOffsetX -= distanceX;
                mFingerOffsetY -= distanceY;
                int unConsumed = fixOffsets();
                if (unConsumed != 0) {
                    mNestedScrollingChildHelper.dispatchNestedScroll(0, 0,
                            0, unConsumed, null);
                } else {
                    invalidate();
                }

            }
            return false;
        }

        /**
         * 连续点击两次，该方法触发
         * 第一次和第二次点击间隔在 300ms 内才生效
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            // 1、双击时切换显示的 scale
            mIsBigOrSmallScale = !mIsBigOrSmallScale;
            if (mIsBigOrSmallScale) {
                /**
                 * e.getX() - getWidth() / 2 表示点击点相对于中心放缩点的偏移值
                 * (e.getX() - getWidth() / 2) * (1 - mBigScale / mSmallScale) 表示点击放大后 该点 与 点击点位置的偏移值
                 * 将 mFingerOffsetX 赋值为放大后 该点 与 点击点位置的偏移值，这样便可以实现从点击的位置处开始放大
                 */
                mFingerOffsetX = (e.getX() - getWidth() / 2f) * (1 - mBigScale / mSmallScale);
                mFingerOffsetY = (e.getY() - getHeight() / 2f) * (1 - mBigScale / mSmallScale);
                fixOffsets();
                mScaleAnimator.start();
            } else {
                mScaleAnimator.reverse();
            }
            return true;
        }
    }

    private class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float tempCurrentScale = mCurrentScale * detector.getScaleFactor();
            if (tempCurrentScale < mSmallScale || tempCurrentScale > mBigScale) {
                return false;
            } else {
                mCurrentScale *= detector.getScaleFactor(); // 0 1; 0 无穷
                return true;
            }
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mFingerOffsetX = (detector.getFocusX() - getWidth() / 2f) * (1 - mBigScale / mSmallScale);
            mFingerOffsetY = (detector.getFocusY() - getHeight() / 2f) * (1 - mBigScale / mSmallScale);
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private class FlingRunner implements Runnable {
        @Override
        public void run() {
            if (mOverScroller.computeScrollOffset()) {
                mFingerOffsetX = mOverScroller.getCurrX();
                mFingerOffsetY = mOverScroller.getCurrY();
                invalidate();
                ViewCompat.postOnAnimation(NestedScalableImageView.this, this);
            }
        }
    }
}
