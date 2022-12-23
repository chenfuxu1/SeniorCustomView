package com.example.seniorcustomview.customview1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/11 13:12
 *
 * 画出仪表盘
 **/
public class DashBoardView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.dpToPixel(160);
    private static final float POINTER_LENGTH = Utils.dpToPixel(120); // 指针的长度
    private static final int OPEN_ANGLE = 120; // 开口角度 120
    private Path mPath = new Path();
    private Path mDashPath = new Path(); // 表盘刻度的路径
    private static final float DASH_WIDTH = Utils.dpToPixel(5); // 表盘刻度的宽
    private static final float DASH_HEIGHT = Utils.dpToPixel(15); // 表盘刻度的高
    private PathMeasure mPathMeasure = new PathMeasure(); // 可以测量路径 path 的长度，切角等
    private PathDashPathEffect mPathDashPathEffect;
    private static final int SCALE_COUNT = 20; // 绘制 20 个刻度
    private static final int CURRENT_SCALE = 6; // 绘制的指针指向第六个刻度

    {
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        /**
         * 设置表盘刻度的路径
         */
        mDashPath.addRect(0f, 0f, DASH_WIDTH, DASH_HEIGHT, Path.Direction.CW);
    }

    public DashBoardView(Context context) {
        this(context, null);

    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DashBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mPath.reset();
        RectF rectF = new RectF();
        rectF.left = getWidth() / 2 - RADIUS;
        rectF.top = getHeight() / 2 - RADIUS;
        rectF.right = getWidth() / 2 + RADIUS;
        rectF.bottom = getHeight() / 2 + RADIUS;
        /**
         * startAngle: 90 度加上开口角度的一半
         * sweepAngle: 360 度减去开口角度就是扫过的角度
         */
        mPath.addArc(rectF, 90 + OPEN_ANGLE / 2, 360 - OPEN_ANGLE);
        /**
         * 测量 path 的长度，方便计算 20 个刻度，每个刻度的间隔应该为多少
         * 参数1：path 路径
         * 参数2：路径的是否需要封闭
         */
        mPathMeasure.setPath(mPath, false);
        /**
         * 获取 20 个刻度的间隔
         * 这里需要减去一个刻度的宽度，不然最后一个显示不出来
         */
        float intervalLength = (mPathMeasure.getLength() - DASH_WIDTH) / SCALE_COUNT;
        mPathDashPathEffect = new PathDashPathEffect(mDashPath, intervalLength, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 1、绘制表盘的弧度
         * startAngle: 90 度加上开口角度的一半
         * sweepAngle: 360 度减去开口角度就是扫过的角度
         */
        canvas.drawPath(mPath, mPaint);

        /**
         * 2、画刻度
         * 给画笔也就是当前的路径添加 dashPath 效果
         * 参数 1：参数路径
         * 参数 2：advance，本来是表示距离路径起点多远的距离开始绘制
         * 参数 3：表示每隔多远，绘制下一个
         * 但这里，参数 2，参数 3 需要反过来写
         * 参数4：style，这里影响不大
         */
        mPaint.setPathEffect(mPathDashPathEffect);
        canvas.drawPath(mPath, mPaint); // 因为 dashPath 是直接作用在 path 上，所以这里需要设置完 paint 后重新 drawPath
        mPaint.setPathEffect(null); // 重置状态

        /**
         * 3、绘制指针的线
         * startX：中心点 x 坐标
         * startY：中心点 Y 坐标
         */
        // 第六个刻度的角度为: 90 度加上开口角度的一半，再加上偏移的角度
        float angle = 90 + OPEN_ANGLE / 2 + CURRENT_SCALE * (360 - OPEN_ANGLE) / 20;
        float x = (float) (POINTER_LENGTH * Math.cos(Math.toRadians(angle))) + getWidth() / 2;
        float y = (float) (POINTER_LENGTH * Math.sin(Math.toRadians(angle))) + getHeight() / 2;
        canvas.drawLine(getWidth() / 2, getHeight() / 2, x, y, mPaint);
    }
}
