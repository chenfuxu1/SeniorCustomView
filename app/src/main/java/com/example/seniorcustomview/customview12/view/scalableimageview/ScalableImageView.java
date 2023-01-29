package com.example.seniorcustomview.customview12.view.scalableimageview;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/20 18:58
 * <p>
 * 双向滑动的 scalableImageView
 **/
public class ScalableImageView extends View implements GestureDetector.OnGestureListener {
    private static final String TAG = "ScalableImageView";
    private Bitmap mBitmap;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mOriginalOffsetX = 0f; // 初始图片 x 方向偏移
    private float mOriginalOffsetY = 0f; // 初始图片 y 方向偏移
    private float mFingerOffsetX = 0f; // 手指移动图片 x 方向偏移
    private float mFingerOffsetY = 0f; // 手指移动图片 y 方向偏移
    private static final float IMAGE_SIZE = Utils.dpToPixel(300);
    private static final float EXTRA_SCALE_FRACTOR = 1.5f; // 放缩系数
    /**
     * 填充屏幕的小系数, 就是看图片是宽胖型还是高瘦型，如果是宽胖型，那么小的 scale 是宽度方向，宽度方向先到边界
     * 如果是高瘦型，那么小的 scale 的高度方向，因为高度方向先到边界
     */
    private float mSmallScale = 0f;
    private float mBigScale = 0f; // 填充屏幕的大系数
    private float mCurrentScale = mSmallScale; // 当前的 scale
    private GestureDetectorCompat mGestureDetectorCompat;
    private boolean mIsBigOrSmallScale = false; // 默认是小的 scale
    private float mScaleFraction = 0f;
    private ObjectAnimator mScaleAnimator;
    private OverScroller mOverScroller;
    // 执行快滑的刷新任务
    private Runnable mFlingRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
        }
    };

    public ScalableImageView(Context context) {
        this(context, null);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScalableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mGestureDetectorCompat = new GestureDetectorCompat(context, this);
        // 给 mGestureDetectorCompat 设置双击监听
        mGestureDetectorCompat.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            /**
             * 支持双击的境况下用该方法更准确，不支持双击的时候用 onSingleTapUp 更准确
             * 支持双击时，第一次按下抬起后，会等待 300ms，如果 300ms 内第二次按下了，会触发 onDoubleTap 方法
             * 如果 300ms 内没按下，会触发 onSingleTapConfirmed 方法
             * 所以支持双击的情况，用 onSingleTapConfirmed 方法比较准确
             * 如果不支持双击的情况，用 onSingleTapConfirmed 方法不够准确，因为第一次点击抬起后，第二次点击的时候都会等 300ms 才会触发
             * 在时间上会不够及时，这个时候用 onSingleTapUp 更及时
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                return false;
            }

            /**
             * 连续点击两次，该方法触发
             * 第一次和第二次点击间隔在 300ms 内才生效
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Logit.d(TAG, "cfx onDoubleTap...");
                // 1、双击时切换显示的 scale
                mIsBigOrSmallScale = !mIsBigOrSmallScale;
                if (mIsBigOrSmallScale) {
                    startAnim();
                } else {
                    reverseAnim();
                }
                return true;
            }

            /**
             * 处理双击之后的操作
             * @param e
             * @return
             */
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
        mOverScroller = new OverScroller(context);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mBitmap = Utils.getAvatar((int) IMAGE_SIZE, R.mipmap.yihu);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOriginalOffsetX = (getWidth() - IMAGE_SIZE) / 2;
        mOriginalOffsetY = (getHeight() - IMAGE_SIZE) / 2;

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
        mBigScale = Math.max(tempBigScale, mBigScale) * EXTRA_SCALE_FRACTOR;
        Logit.d(TAG, "cfx mSmallScale = " + mSmallScale + " mBigScale = " + mBigScale);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 3、移动图片的位置
        canvas.translate(mFingerOffsetX, mFingerOffsetY);
        mCurrentScale = mSmallScale + (mBigScale - mSmallScale) * mScaleFraction;
        // 2、开始放缩
        canvas.scale(mCurrentScale, mCurrentScale, getWidth() / 2, getHeight() / 2);
        // 1、倒着想，先画图
        canvas.drawBitmap(mBitmap, mOriginalOffsetX, mOriginalOffsetY, mPaint);
    }

    public float getScaleFraction() {
        return mScaleFraction;
    }

    public void setScaleFraction(float scaleFraction) {
        mScaleFraction = scaleFraction;
        invalidate();
    }

    /**
     * 开始由小 scale 到大 scale 的动画
     */
    private void startAnim() {
        resetAnim();
        mScaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f);
        mScaleAnimator.setDuration(500);
        mScaleAnimator.start();
    }

    private void reverseAnim() {
        resetAnim();
        mScaleAnimator = ObjectAnimator.ofFloat(this, "scaleFraction", 1f, 0f);
        mScaleAnimator.setDuration(500);
        mScaleAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 重写 onTouchEvent 方法，使用，mGestureDetectorCompat 的 onTouchEvent 方法
         */
        return mGestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        /**
         * 几乎是永恒返回 true，只有这里返回 true 了，在 onTouchEvent 方法中才能拿到事件序列，进而在
         * mGestureDetectorCompat.onTouchEvent(event) 中才能拿到，如果返回 false，就全拿不到了
         * 等价于返回：return e.getActionMasked() == MotionEvent.ACTION_DOWN;
         */
        return e.getActionMasked() == MotionEvent.ACTION_DOWN;
        // return true;
    }

    /**
     * 回调该 view 是否被按下
     *
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * 1、不支持长按的场景
     * 2、支持长按的场景，需要在 300 ms 内完成按下抬起，即在长按的时间间隔内抬起，认为是点击了，否则是长按了
     * mGestureDetectorCompat.setIsLongpressEnabled(false); 可以设置是否支持长按的开关，默认是支持的
     * 单击事件，可以代替点击监听
     *
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    /**
     * 实际上是 onMove，表示在手指移动过程中调用触发的方法
     *
     * @param downEvent    事件序列的 down 事件
     * @param currentEvent 当前的事件
     * @param distanceX    上次事件的 x 点坐标 - 这次事件的 x 点坐标
     * @param distanceY    上次事件的 y 点坐标 - 这次事件的 y 点坐标
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent downEvent, MotionEvent currentEvent, float distanceX, float distanceY) {
        /**
         * 因为 distanceX、distanceY 是 上次事件的点坐标 - 这次事件的点坐标，所以要加负号
         */
        if (mIsBigOrSmallScale) {
            Logit.d(TAG, "cfx distanceX = " + distanceX + " distanceY = " + distanceY);
            mFingerOffsetX -= distanceX;
            /**
             * 修正下手指移动的距离，防止无限滑动
             * 保证图片放大到最大时，mFingerOffsetX = (mBitmap.getWidth() * mBigScale- getWidth()) / 2
             * 正向移动时，mFingerOffsetX 为 mFingerOffsetX 与 (mBitmap.getWidth() * mBigScale- getWidth()) / 2 的最小值
             * 负向移动是，mFingerOffsetX 为 mFingerOffsetX 与 -(mBitmap.getWidth() * mBigScale- getWidth()) / 2 的最大值
             */
            mFingerOffsetX = Math.min(mFingerOffsetX, (mBitmap.getWidth() * mBigScale - getWidth()) / 2);
            mFingerOffsetX = Math.max(mFingerOffsetX, -(mBitmap.getWidth() * mBigScale - getWidth()) / 2);
            mFingerOffsetY -= distanceY;
            mFingerOffsetY = Math.min(mFingerOffsetY, (mBitmap.getHeight() * mBigScale - getHeight()) / 2);
            mFingerOffsetY = Math.max(mFingerOffsetY, -(mBitmap.getHeight() * mBigScale - getHeight()) / 2);
            invalidate();
        }
        return true;
    }

    /**
     * 手指滑的比较快的情况下，松手后仍然保持滑动
     * 快滑（惯性滑动）的过程中，方法会被调用
     *
     * @param downEvent    事件序列的 down 事件
     * @param currentEvent 当前的事件
     * @param velocityX    x 方向的速率
     * @param velocityY    y 方向的速率
     * @return
     */
    @Override
    public boolean onFling(MotionEvent downEvent, MotionEvent currentEvent, float velocityX, float velocityY) {
        if (mIsBigOrSmallScale) {
            mOverScroller.fling((int) mFingerOffsetX, (int) mFingerOffsetY, (int) velocityX, (int) velocityY,
                    (int) (-(mBitmap.getWidth() * mBigScale - getWidth()) / 2), (int) (mBitmap.getWidth() * mBigScale - getWidth()) / 2,
                    (int) (-(mBitmap.getHeight() * mBigScale - getHeight()) / 2), (int) (mBitmap.getHeight() * mBigScale - getHeight()) / 2,
                    100, 100);
        }
        /**
         * ViewCompat.postOnAnimation() 比较安全
         * 高版本用 postOnAnimation 方法，低版本用 postDelayed 放到下一帧
         */
        ViewCompat.postOnAnimation(this, mFlingRunnable);
        return false;
    }

    private void refresh() {
        // 判断快滑动画是否在执行
        if (mOverScroller != null && mOverScroller.computeScrollOffset()) {
            mFingerOffsetX = mOverScroller.getCurrX();
            mFingerOffsetY = mOverScroller.getCurrY();
            invalidate();
            ViewCompat.postOnAnimation(this, mFlingRunnable);
        }
    }

    /**
     * 长按时，该方法被调用
     *
     * @param e
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    private void resetAnim() {
        if (mScaleAnimator != null) {
            mScaleAnimator.cancel();
            mScaleAnimator = null;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetAnim();
    }


}
