package com.example.seniorcustomview.customview2.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/13 23:24
 **/
public class AvatarView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float IMAGE_PADDING = Utils.dpToPixel(20);
    private static final float IMAGE_WIDTH = Utils.dpToPixel(200);
    private RectF mRectF;
    private Xfermode mXfermode;

    {
        // 表示绘制的区域
        mRectF = new RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH,
                IMAGE_PADDING + IMAGE_WIDTH);
        /**
         * 初始化 Xfermode，SRC_IN 表示会显示内容为：源图像在目标区域内的内容
         */
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    }

    public AvatarView(Context context) {
        this(context, null);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 1、需要进行离屏缓存
         * 因为离屏缓存损耗性能，所以不必整个屏幕都离屏缓存，只离屏需要绘制的目标区域即可
         */
        int count = canvas.saveLayer(mRectF, mPaint);
        canvas.drawOval(mRectF, mPaint);
        // 2、设置 xfermode
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(getAvatar((int) IMAGE_WIDTH), IMAGE_PADDING, IMAGE_PADDING, mPaint);
        // 3、绘制完将 xfermode 置空
        mPaint.setXfermode(null);
        // 4、离屏缓存状态恢复
        canvas.restoreToCount(count);
    }

    private Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.yihu, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.yihu, options);
    }
}
