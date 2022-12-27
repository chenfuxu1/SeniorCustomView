package com.example.seniorcustomview.customview9.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/27 23:14
 *
 * II、完全自定义 view 的尺寸
 *
 * 1、重写 onMeasure()
 * 2、计算出自己的尺寸
 * 3、用 resolveSize() 或者 resolveSizeAndState() 修正结果
 * 4、使用 setMeasuredDimension(width, height) 保存结果
 *
 *
 * 希望做到的是这个 CircleView 刚好包裹整个圆的大小
 **/
public class CircleView extends View {
    private static final String TAG = "CircleView";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.dpToPixel(100);
    private static final float PADDING = Utils.dpToPixel(100);

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, mPaint);
    }

    // 1、重写 onMeasure()
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 2、计算出自己的尺寸
        int size = (int) ((PADDING + RADIUS) * 2);
        /**
         * 3、用 resolveSize() 或者 resolveSizeAndState() 修正结果
         * widthMeasureSpec、heightMeasureSpec，指的是父 view 对自身的要求，包含了限制类型与尺寸大小
         * size：是自身需要的大小
         */
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec); // 宽度限制类型
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec); // 宽度限制尺寸
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec); // 高度限制类型
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec); // 高度限制尺寸

        int width = 0;
        int height = 0;
        switch (specWidthMode) {
            case MeasureSpec.EXACTLY:
                // 表示 xml 设置的宽度是固定值，那么需要遵守这个固定值
                width = specWidthSize;
                Logit.d(TAG, "cfx MeasureSpec.EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                // 表示 xml 设置的宽度是一个上限值，那么只要不超过这个 specWidthSize 即可
                width = Math.min(specWidthSize, size);
                Logit.d(TAG, "cfx MeasureSpec.AT_MOST");
                break;
            case MeasureSpec.UNSPECIFIED:
                // 表示 xml 设置的宽度是不限制，那么我想定义多少就是多少
                width = size;
                Logit.d(TAG, "cfx MeasureSpec.UNSPECIFIED");
                break;
        }
        switch (specHeightMode) {
            case MeasureSpec.EXACTLY:
                // 表示 xml 设置的高度是固定值，那么需要遵守这个固定值
                height = specHeightSize;
                break;
            case MeasureSpec.AT_MOST:
                // 表示 xml 设置的高度是一个上限值，那么只要不超过这个 specWidthSize 即可
                height = Math.min(specHeightSize, size);
                break;
            case MeasureSpec.UNSPECIFIED:
                // 表示 xml 设置的高度是不限制，那么我想定义多少就是多少
                height = size;
                break;
        }
        /**
         * 这样便修正了高度与宽度值，上述整个第 3 点的代码和 resolveSize(size, measureSpec) 功能一样
         */
        // size = resolveSize(size, widthMeasureSpec);

        // 4、使用 setMeasuredDimension(width, height) 保存结果
        setMeasuredDimension(width, height);
    }
}
