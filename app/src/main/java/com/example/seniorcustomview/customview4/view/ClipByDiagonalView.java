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
 * <p>
 * 沿图片对角线翻折
 * 在平移到坐标原点后，对 canvas 进行旋转
 * 然后再进行裁切即可
 * 然后再将 canvas 旋转回来，坐标平移到图片中心
 **/
public class ClipByDiagonalView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float BITMAP_WIDTH = Utils.dpToPixel(200);
    private static final float BITMAP_PADDING = Utils.dpToPixel(100);
    private Bitmap mBitmap;
    private Camera mCamera = new Camera();

    {
        mBitmap = Utils.getAvatar((int) BITMAP_WIDTH, R.mipmap.sha);
        mCamera.rotateX(45f);
        /**
         * 设置相机 camera 的位置，往屏幕里面方向为正，屏幕外面为负
         * 越远离屏幕图片显示越大，默认值是 -8f 个像素
         */
        mCamera.setLocation(0f, 0f, -6f * getResources().getDisplayMetrics().density);
    }


    public ClipByDiagonalView(Context context) {
        this(context, null);
    }

    public ClipByDiagonalView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipByDiagonalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ClipByDiagonalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 1、绘制上半部分不变的图像
        canvas.save();
        // )5、将图片中心移回原处
        canvas.translate(BITMAP_PADDING + BITMAP_WIDTH / 2, BITMAP_PADDING + BITMAP_WIDTH / 2);
        // )4、将整个图像再旋转回去
        canvas.rotate(-45f);
        // )3、对整个图片的上半部分进行裁切（这个时候因为有旋转，极限是范围扩大了根号 2 倍，所以这里乘以 1.45）
        canvas.clipRect(-(float) ((BITMAP_WIDTH / 2) * 1.45), -(float) ((BITMAP_WIDTH / 2) * 1.45),
                (float) ((BITMAP_WIDTH / 2) * 1.45), 0f);
        // )2、对整个图像进行旋转 45 度
        canvas.rotate(45f);
        // )1、把图像中心移到坐标原点(因为 camera 的坐标不变，实际移动的是 canvas 的坐标，但这样便于复杂变化的理解与操作)
        canvas.translate(-(BITMAP_PADDING + BITMAP_WIDTH / 2), -(BITMAP_PADDING + BITMAP_WIDTH / 2));
        canvas.drawBitmap(mBitmap, BITMAP_PADDING, BITMAP_PADDING, mPaint);
        canvas.restore();

        // 2、绘制下半部分沿 x 轴旋转 30 度的图片
        canvas.save();
        // )6、将图片中心移回原处
        canvas.translate(BITMAP_PADDING + BITMAP_WIDTH / 2, BITMAP_PADDING + BITMAP_WIDTH / 2);
        // )5、将整个图像再旋转回去
        canvas.rotate(-45f);
        // )4、将 camera 运用到 canvas 中
        mCamera.applyToCanvas(canvas);
        // )3、对整个图片的下半部分进行裁切（这个时候因为有旋转，极限是范围扩大了根号 2 倍，所以这里乘以 1.45）
        canvas.clipRect(-(float) ((BITMAP_WIDTH / 2) * 1.45), 0f,
                (float) ((BITMAP_WIDTH / 2) * 1.45), (float) ((BITMAP_WIDTH / 2) * 1.45));
        // )2、对整个图像进行旋转 45 度
        canvas.rotate(45f);
        // )1、把图像中心移到坐标原点(因为 camera 的坐标不变，实际移动的是 canvas 的坐标，但这样便于复杂变化的理解与操作)
        canvas.translate(-(BITMAP_PADDING + BITMAP_WIDTH / 2), -(BITMAP_PADDING + BITMAP_WIDTH / 2));
        canvas.drawBitmap(mBitmap, BITMAP_PADDING, BITMAP_PADDING, mPaint);
        canvas.restore();
    }
}
