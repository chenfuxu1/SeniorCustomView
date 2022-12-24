package com.example.seniorcustomview.customview6.view.customdrawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 10:53
 *
 * 自定义 drawable
 **/
public class MeshDrawable extends Drawable {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float INTERVAL_WIDTH = Utils.dpToPixel(20);
    private float horizontalX;
    private float verticalY;

    {
        mPaint.setColor(Color.CYAN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        /**
         * getBounds().left: 区域最左边
         * getBounds().top：区域最上边
         * getBounds().bottom：区域最下边
         * 这样开始竖着画一条线
         */
        horizontalX = getBounds().left;
        while (horizontalX <= getBounds().right) {
            canvas.drawLine(horizontalX, getBounds().top, horizontalX, getBounds().bottom, mPaint);
            horizontalX += INTERVAL_WIDTH;
        }

        // 开始画横线
        verticalY = getBounds().top;
        while (verticalY <= getBounds().bottom) {
            canvas.drawLine(getBounds().left, verticalY, getBounds().right, verticalY, mPaint);
            verticalY += INTERVAL_WIDTH;
        }
    }

    /**
     * 设置整个 drawable 的透明度
     * @param alpha
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public int getAlpha() {
        return mPaint.getAlpha();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Nullable
    @Override
    public ColorFilter getColorFilter() {
        return mPaint.getColorFilter();
    }

    /**
     * 设置不透明度
     * @return
     */
    @Override
    public int getOpacity() {
        if (mPaint != null) {
            switch (mPaint.getAlpha()) {
                case 0:
                    // 全透明
                    return PixelFormat.TRANSPARENT;
                case 0xff:
                    // 不透明
                    return PixelFormat.OPAQUE;
                default:
                    // 半透明
                    return PixelFormat.TRANSLUCENT;
            }
        }
        return PixelFormat.TRANSLUCENT;
    }
}
