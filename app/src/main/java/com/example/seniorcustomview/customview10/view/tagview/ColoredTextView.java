package com.example.seniorcustomview.customview10.view.tagview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.seniorcustomview.Utils;

import java.util.Random;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/28 22:56
 **/
public class ColoredTextView extends AppCompatTextView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Random mRandom = new Random();
    private static final float CORNER_RADIUS = Utils.dpToPixel(10);
    private static final int LEFT_AND_RIGHT_PADDING = (int) Utils.dpToPixel(16);
    private static final int TOP_AND_BOTTOM_PADDING = (int) Utils.dpToPixel(8);
    private int[] mTextSizes = new int[] {16, 22, 26};
    private int[] mColors = new int[]{
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548")
    };

    {
        setTextColor(Color.WHITE);
        setTextSize(mTextSizes[mRandom.nextInt(mTextSizes.length)]);
        mPaint.setColor(mColors[mRandom.nextInt(mColors.length)]);
        setPadding(LEFT_AND_RIGHT_PADDING, TOP_AND_BOTTOM_PADDING, LEFT_AND_RIGHT_PADDING,
                TOP_AND_BOTTOM_PADDING);
    }


    public ColoredTextView(@NonNull Context context) {
        this(context, null);
    }

    public ColoredTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColoredTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0f, 0f, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, mPaint);
        super.onDraw(canvas);
    }
}
