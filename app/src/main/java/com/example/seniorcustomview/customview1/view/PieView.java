package com.example.seniorcustomview.customview1.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/11 23:29
 *
 * 绘制饼图
 **/
public class PieView extends View {
    private static final String TAG = "PieView";
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Float> mSweepAngle = new ArrayList<>();
    private List<Integer> mColors = new ArrayList<>();
    private static final float RADIUS = Utils.dpToPixel(160);
    private static final int START_ANGLE = 0;
    private int mCurrentAngle = START_ANGLE;
    private static final float LENGTH = 50f; // 表示扇形从圆心到当前扇形顶点的两点间的距离长度

    {
        mSweepAngle.add(60f);
        mSweepAngle.add(90f);
        mSweepAngle.add(150f);
        mSweepAngle.add(60f);
        mColors.add(Color.RED);
        mColors.add(Color.GREEN);
        mColors.add(Color.BLUE);
        mColors.add(Color.YELLOW);
    }

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF();
        rectF.left = getWidth() / 2 - RADIUS;
        rectF.top = getHeight() / 2 - RADIUS;
        rectF.right = getWidth() / 2 + RADIUS;
        rectF.bottom = getHeight() / 2 + RADIUS;
        for (int i = 0; i < 4; i++) {
            /**
             * 将第一块平移出来，平移的距离怎么计算呢，可以利用三角函数
             * 可以从扇形的顶点出发，沿着扇形的对角线进行平移即可
             * 假设从圆心到 扇形顶点的长度为 L，扇形夹角为 m，那么 L * cos(m/2) 为 x 坐标， L * sin(m/2) 为 y 坐标
             */
            if (i == 2) {
                canvas.save();
                float xDiff = (float) (LENGTH * Math.cos(Math.toRadians(mSweepAngle.get(i) / 2 + mCurrentAngle)));
                float yDiff = (float) (LENGTH * Math.sin(Math.toRadians(mSweepAngle.get(i) / 2 + mCurrentAngle) ));
                Logit.d(TAG, "cfx xDiff: " + xDiff + " yDiff: " + yDiff);
                canvas.translate(xDiff, yDiff);
            }
            mPaint.setColor(mColors.get(i));
            canvas.drawArc(rectF, mCurrentAngle, mSweepAngle.get(i), true, mPaint);
            mCurrentAngle += mSweepAngle.get(i);
            if (i == 2) {
                canvas.restore();
            }
        }
        mCurrentAngle = START_ANGLE;
    }
}
