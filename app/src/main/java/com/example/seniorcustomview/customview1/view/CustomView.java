package com.example.seniorcustomview.customview1.view;

import static android.graphics.Path.FillType.EVEN_ODD;
import static android.graphics.Path.FillType.INVERSE_EVEN_ODD;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/11 10:38
 **/
public class CustomView extends View {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath = new Path();
    private static final float RADIUS = Utils.dpToPixel(100);

    {
        mPaint.setColor(Color.RED);
    }

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        // 在 view 尺寸改变的时候，初始化 path
        mPath.reset();
        // CW: 顺时针 CCW: 逆时针
        mPath.addCircle(width, height, RADIUS, Path.Direction.CW);
        // 添加矩形区域，矩形左上顶点与圆左边重合，右上顶点与圆右边重合，高度为圆的直径
        float leftRect = width - RADIUS;
        float topRect = height;
        float rightRect = width + RADIUS;
        float bottomRect = height + RADIUS * 2;
        mPath.addRect(leftRect, topRect, rightRect, bottomRect, Path.Direction.CW);
        mPath.addCircle(width, height, (float) (RADIUS * 1.5), Path.Direction.CW);
        /**
         * setFillType 默认是 WINDING
         * WINDING 遵守：相同方向加 1，相反方向减 1，只要结果不为 0，表示该点在内部
         * EVEN_ODD 不遵守方向了：只要相交就加 1，结果是奇数，表示该点在内部，偶数在外部
         */
        mPath.setFillType(EVEN_ODD); // 需要镂空的就设置这种类型，不用考虑绘制的方向了
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }
}
