package com.example.seniorcustomview.customview5.view.animset;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/19 23:34
 *
 * AnimSet 属性动画
 **/
public class AnimSetView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float BITMAP_WIDTH = Utils.dpToPixel(200);
    private static final float BITMAP_PADDING = Utils.dpToPixel(100);
    private Bitmap mBitmap;
    private Camera mCamera = new Camera();
    private float mWidth;
    private float mHeight;

    private float mTopFlipAngle = 0f; // 图片从顶部向上翻折的角度
    private float mBottomFlipAngle = 0f; // 图片从底部向上翻折的角度
    private float mFlipRotateAngle = 0f; // 整张图片折痕的角度

    private ObjectAnimator mTopFlipAnimator;
    private ObjectAnimator mBottomFlipAnimator;
    private ObjectAnimator mFlipRotateAnimator;
    private AnimatorSet mAnimatorSet;

    {
        mBitmap = Utils.getAvatar((int) BITMAP_WIDTH, R.mipmap.sha);
        /**
         * 设置相机 camera 的位置，往屏幕里面方向为正，屏幕外面为负
         * 越远离屏幕图片显示越大，默认值是 -8f 个像素
         */
        mCamera.setLocation(0f, 0f, -6f * getResources().getDisplayMetrics().density);
    }

    public AnimSetView(Context context) {
        this(context, null);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
         * 1、绘制上半部分沿横向轴线翻折 mTopFlipAngle 度的图片
         */
        canvas.save();
        // )6、将图片中心移回原处
        canvas.translate(mWidth / 2, mHeight / 2);
        // )5、将整个图像再旋转回去
        canvas.rotate(-mFlipRotateAngle);
        mCamera.save();
        mCamera.rotateX(mTopFlipAngle);
        // )4、将 camera 运用到 canvas 中
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
        // )3、对整个图片的上半部分进行裁切（这个时候因为有旋转，极限是范围扩大了根号 2 倍，所以这里乘以 1.45）
        canvas.clipRect(-(float) ((BITMAP_WIDTH / 2) * 1.45), -(float) ((BITMAP_WIDTH / 2) * 1.45),
                (float) ((BITMAP_WIDTH / 2) * 1.45), 0f);
        // )2、对整个图像进行旋转 30 度
        canvas.rotate(mFlipRotateAngle);
        // )1、把图像中心移到坐标原点(因为 camera 的坐标不变，实际移动的是 canvas 的坐标，但这样便于复杂变化的理解与操作)
        canvas.translate(-(mWidth / 2), -(mHeight / 2));
        canvas.drawBitmap(mBitmap, (mWidth - BITMAP_WIDTH) / 2,(mHeight - BITMAP_WIDTH) / 2, mPaint);
        canvas.restore();

        /**
         *  2、绘制下半部分横向轴线翻折 mBottomFlipAngle 度的图片
         */
        canvas.save();
        // )6、将图片中心移回原处
        canvas.translate(mWidth / 2, mHeight / 2);
        // )5、将整个图像再旋转回去
        canvas.rotate(-mFlipRotateAngle);
        mCamera.save();
        mCamera.rotateX(mBottomFlipAngle);
        // )4、将 camera 运用到 canvas 中
        mCamera.applyToCanvas(canvas);
        mCamera.restore();
        // )3、对整个图片的下半部分进行裁切（这个时候因为有旋转，极限是范围扩大了根号 2 倍，所以这里乘以 1.45）
        canvas.clipRect(-(float) ((BITMAP_WIDTH / 2) * 1.45), 0f,
                (float) ((BITMAP_WIDTH / 2) * 1.45), (float) ((BITMAP_WIDTH / 2) * 1.45));
        // )2、对整个图像进行旋转 30 度
        canvas.rotate(mFlipRotateAngle);
        // )1、把图像中心移到坐标原点(因为 camera 的坐标不变，实际移动的是 canvas 的坐标，但这样便于复杂变化的理解与操作)
        canvas.translate(-(mWidth / 2), -(mHeight / 2));
        canvas.drawBitmap(mBitmap, (mWidth - BITMAP_WIDTH) / 2,(mHeight - BITMAP_WIDTH) / 2, mPaint);
        canvas.restore();
    }

    public float getTopFlipAngle() {
        return mTopFlipAngle;
    }

    public void setTopFlipAngle(float topFlipAngle) {
        this.mTopFlipAngle = topFlipAngle;
        invalidate();
    }

    public float getBottomFlipAngle() {
        return mBottomFlipAngle;
    }

    public void setBottomFlipAngle(float bottomFlipAngle) {
        this.mBottomFlipAngle = bottomFlipAngle;
        invalidate();
    }

    public float getFlipRotateAngle() {
        return mFlipRotateAngle;
    }

    public void setFlipRotateAngle(float flipRotateAngle) {
        this.mFlipRotateAngle = flipRotateAngle;
        invalidate();
    }

    public void startAnim() {
        resetAllAnim();
        mAnimatorSet = new AnimatorSet();
        /**
         * 1、底部向上翻折 45 度
         */
        mBottomFlipAnimator = ObjectAnimator.ofFloat(this, "bottomFlipAngle", 0, 45);
        mBottomFlipAnimator.setStartDelay(300);
        mBottomFlipAnimator.setDuration(1000);
        /**
         * 2、整体旋转 270 度
         */
        mFlipRotateAnimator = ObjectAnimator.ofFloat(this, "flipRotateAngle", 0, 270);
        mFlipRotateAnimator.setStartDelay(200);
        mFlipRotateAnimator.setDuration(1000);
        /**
         * 3、顶部向上翻折的动画
         */
        mTopFlipAnimator = ObjectAnimator.ofFloat(this, "topFlipAngle", 0, -45);
        mTopFlipAnimator.setStartDelay(200);
        mTopFlipAnimator.setDuration(1000);

        mAnimatorSet.playSequentially(mBottomFlipAnimator, mFlipRotateAnimator, mTopFlipAnimator);
        mAnimatorSet.start();
    }

    public void resetAnim() {
        resetAllAnim();
        mAnimatorSet = new AnimatorSet();
        /**
         * 1、顶部向下翻折的动画
         */
        mTopFlipAnimator = ObjectAnimator.ofFloat(this, "topFlipAngle", -45, 0);
        mTopFlipAnimator.setStartDelay(300);
        mTopFlipAnimator.setDuration(1000);
        /**
         * 2、整体旋转 -270 度
         */
        mFlipRotateAnimator = ObjectAnimator.ofFloat(this, "flipRotateAngle", 270, 0);
        mFlipRotateAnimator.setStartDelay(200);
        mFlipRotateAnimator.setDuration(1000);
        /**
         * 3、底部向下翻折 45 度
         */
        mBottomFlipAnimator = ObjectAnimator.ofFloat(this, "bottomFlipAngle", 45, 0);
        mBottomFlipAnimator.setStartDelay(200);
        mBottomFlipAnimator.setDuration(1000);
        mAnimatorSet.playSequentially(mTopFlipAnimator, mFlipRotateAnimator, mBottomFlipAnimator);
        mAnimatorSet.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetAllAnim();
    }

    public void resetAllAnim() {
        resetTopFlipAnimator();
        resetBottomFlipAnimator();
        resetFlipRotateAnimator();
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
    }

    private void resetTopFlipAnimator() {
        if (mTopFlipAnimator != null) {
            mTopFlipAnimator.cancel();
            mTopFlipAnimator = null;
        }
    }

    private void resetBottomFlipAnimator() {
        if (mBottomFlipAnimator != null) {
            mBottomFlipAnimator.cancel();
            mBottomFlipAnimator = null;
        }
    }

    private void resetFlipRotateAnimator() {
        if (mFlipRotateAnimator != null) {
            mFlipRotateAnimator.cancel();
            mFlipRotateAnimator = null;
        }
    }
}
