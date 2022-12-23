package com.example.seniorcustomview.customview4.view;

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
 * DateTime: 2022/12/18 14:57
 **/
public class RotateXView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float BITMAP_WIDTH = Utils.dpToPixel(200);
    private static final float BITMAP_PADDING = Utils.dpToPixel(100);
    private Bitmap mBitmap;
    private Camera mCamera = new Camera();

    {
        mBitmap = Utils.getAvatar((int) BITMAP_WIDTH, R.mipmap.sha);
        mCamera.rotateX(30f);
        /**
         * 设置相机 camera 的位置，往屏幕里面方向为正，屏幕外面为负
         * 越远离屏幕图片显示越大，默认值是 -8f 个像素
         */
        mCamera.setLocation(0f, 0f, -6f * getResources().getDisplayMetrics().density);
    }


    public RotateXView(Context context) {
        this(context, null);
    }

    public RotateXView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RotateXView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * 沿 x 轴方向旋转 30 度
         * 可以从下往上倒看整个移动的过程
         * 这种方式假定移动的是图片(实际移动的是 canvas 的坐标)
         */
        canvas.save();
        // 3、将图片中心移回原处
        canvas.translate(BITMAP_PADDING + BITMAP_WIDTH / 2, BITMAP_PADDING + BITMAP_WIDTH / 2);
        // 2、将 camera 运用到 canvas 中
        mCamera.applyToCanvas(canvas);
        // 1、把图像中心移到坐标原点(因为 camera 的坐标不变，实际移动的是 canvas 的坐标，但这样便于复杂变化的理解与操作)
        canvas.translate(-(BITMAP_PADDING + BITMAP_WIDTH / 2), -(BITMAP_PADDING + BITMAP_WIDTH / 2));
        canvas.drawBitmap(mBitmap, BITMAP_PADDING, BITMAP_PADDING, mPaint);
        canvas.restore();
    }
}
