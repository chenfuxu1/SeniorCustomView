package com.example.seniorcustomview.customview13.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/7 23:07
 *
 * 多点触摸 view，各自为占型
 * 完成需求：手指画板
 *
 **/
public class MultiTouchViewFour extends View {
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final SparseArray<Path> mPathSparseArray = new SparseArray<>();

    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(12);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setColor(Color.RED);
    }

    public MultiTouchViewFour(Context context) {
        this(context, null);
    }

    public MultiTouchViewFour(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchViewFour(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MultiTouchViewFour(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPathSparseArray.size(); i++) {
            Path path = mPathSparseArray.valueAt(i);
            canvas.drawPath(path, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                Path path = new Path();
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex));
                mPathSparseArray.put(event.getPointerId(actionIndex), path);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mPathSparseArray.size(); i++) {
                    int pointerId = event.getPointerId(i);
                    Path path1 = mPathSparseArray.get(pointerId);
                    path1.lineTo(event.getX(i), event.getY(i));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                int pointerId = event.getPointerId(actionIndex);
                mPathSparseArray.remove(pointerId);
                invalidate();
                break;
        }
        return true;
    }
}
