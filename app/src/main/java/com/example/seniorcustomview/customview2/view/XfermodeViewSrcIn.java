package com.example.seniorcustomview.customview2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/13 23:53
 **/
public class XfermodeViewSrcIn extends View {
    private static final String TAG = "XfermodeView";
    private Paint mPaint;
    private static final float RADIUS = Utils.dpToPixel(40);
    private float mWidth;
    private float mHeight;
    private RectF mLeftRectF = new RectF(); // 最左边正方形显示区域
    private RectF mCenterRectF = new RectF(); // 中间正方形显示区域
    private RectF mRightRectF = new RectF(); // 右边正方形显示区域
    private RectF mBoundsRectF = new RectF(); // 离屏缓存区域，只设置第三块 view 显示的区域即可
    private Xfermode mXfermode;
    private Bitmap mSquareBitmap; // 创建整个正方形所在的 bitmap
    private Bitmap mCircleBitmap; // 创建整个圆形所在的 bitmap (与正方形的区域是同一块区域，有空白，实际绘画内容区域不同)

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN); // 源图显示在目标区域内部

        Canvas canvas = new Canvas();
        mSquareBitmap = Bitmap.createBitmap((int) RADIUS * 3, (int) RADIUS * 3,
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(mSquareBitmap);
        mPaint.setColor(Color.RED);
        // 在 bitmap 的区域画出 圆形
        canvas.drawCircle(RADIUS * 2, RADIUS, RADIUS, mPaint);
        // 创建整个圆形所在的 bitmap (与正方形的区域是同一块区域，有空白，实际绘画内容区域不同)
        mCircleBitmap = Bitmap.createBitmap((int) RADIUS * 3, (int) RADIUS * 3,
                Bitmap.Config.ARGB_8888);
        canvas.setBitmap(mCircleBitmap);
        mPaint.setColor(Color.GREEN);
        // 在 bitmap 的区域画出 正方形
        canvas.drawRect(0, RADIUS, RADIUS * 2, RADIUS * 3, mPaint);
    }

    public XfermodeViewSrcIn(Context context) {
        this(context, null);
    }

    public XfermodeViewSrcIn(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeViewSrcIn(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XfermodeViewSrcIn(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = getWidth();
        mHeight = getHeight();
        // 左侧正方形显示区域
        mLeftRectF.left = Utils.dpToPixel(15);
        mLeftRectF.top = Utils.dpToPixel(55);
        mLeftRectF.right = mLeftRectF.left + RADIUS * 2;
        mLeftRectF.bottom = mLeftRectF.top + RADIUS * 2;
        // 中间正方形显示区域
        mCenterRectF.left = mWidth / 2 + RADIUS / 2 - RADIUS * 2;
        mCenterRectF.top = Utils.dpToPixel(55);
        mCenterRectF.right = mCenterRectF.left + RADIUS * 2;
        mCenterRectF.bottom = mCenterRectF.top +  RADIUS * 2;
        // 右侧正方形显示区域
        mRightRectF.left = mWidth - RADIUS * 3 - Utils.dpToPixel(15);
        mRightRectF.top = Utils.dpToPixel(55);
        mRightRectF.right = mWidth - RADIUS - Utils.dpToPixel(15);
        mRightRectF.bottom = mRightRectF.top +  RADIUS * 2;
        // 设置离屏缓存的区域
        mBoundsRectF.left = mWidth - Utils.dpToPixel(15) - RADIUS * 3;
        mBoundsRectF.top = Utils.dpToPixel(15);
        mBoundsRectF.right = mWidth - Utils.dpToPixel(15);
        mBoundsRectF.bottom = mHeight - Utils.dpToPixel(15);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1、画出源图
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(mLeftRectF, mPaint);

        // 2、画出源图覆盖在目标区域(绿色为源图，红色为目标区域)
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mCenterRectF.right, mCenterRectF.top, RADIUS, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(mCenterRectF, mPaint);

        /**
         * 3、绘制源图在目标区域的显示内容
         * 保证 正方形的 bitmap 区域和 圆形的 bitmap 区域都能覆盖到
         * 如果只绘制一个圆，一个正方形，那么正方形之外的区域不会对 圆形的区域产生影响
         */
        int count = canvas.saveLayer(mBoundsRectF, mPaint); // 使用离屏缓存
        canvas.drawBitmap(mCircleBitmap, mBoundsRectF.left, mBoundsRectF.top, mPaint);
        mPaint.setXfermode(mXfermode); // 设置 xfermode
        canvas.drawBitmap(mSquareBitmap, mBoundsRectF.left, mBoundsRectF.top, mPaint);
        mPaint.setXfermode(null); // 将 xfermode 置为空
        canvas.restoreToCount(count); // 重置状态
    }
}
