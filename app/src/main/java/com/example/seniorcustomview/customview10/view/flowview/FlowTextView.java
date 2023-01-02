package com.example.seniorcustomview.customview10.view.flowview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
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
public class FlowTextView extends AppCompatTextView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Random mRandom = new Random();
    private static final float CORNER_RADIUS = Utils.dpToPixel(10);
    private static final int LEFT_AND_RIGHT_PADDING = (int) Utils.dpToPixel(16);
    private static final int TOP_AND_BOTTOM_PADDING = (int) Utils.dpToPixel(8);
    private int[] mColors = new int[]{
            Color.parseColor("#FFB6C1"),
            Color.parseColor("#C71585"),
            Color.parseColor("#4B0082"),
            Color.parseColor("#E6E6FA"),
            Color.parseColor("#F8F8FF"),
            Color.parseColor("#191970"),
            Color.parseColor("#778899"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#009688"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#87CEEB"),
            Color.parseColor("#00CED1"),
            Color.parseColor("#00FA9A"),
            Color.parseColor("#3CB371"),
            Color.parseColor("#00FF00"),
            Color.parseColor("#ADFF2F"),
            Color.parseColor("#6B8E23"),
            Color.parseColor("#808000"),
            Color.parseColor("#FFFACD"),
            Color.parseColor("#F0E68C"),
            Color.parseColor("#DAA520"),
            Color.parseColor("#FFE4B5"),
            Color.parseColor("#FFEFD5"),
            Color.parseColor("#D2B48C"),
            Color.parseColor("#FF8C00"),
            Color.parseColor("#CD853F"),
            Color.parseColor("#FFF5EE"),
            Color.parseColor("#FF6347"),
            Color.parseColor("#BC8F8F"),
            Color.parseColor("#B22222"),
            Color.parseColor("#F5F5F5"),
            Color.parseColor("#DCDCDC"),
            Color.parseColor("#A9A9A9"),
            Color.parseColor("#696969"),
            Color.parseColor("#FAF0E6"),
            Color.parseColor("#FFE4C4"),
            Color.parseColor("#FFEFD5"),
            Color.parseColor("#FDF5E6"),
            Color.parseColor("#FFD700"),
            Color.parseColor("#7FFF00"),
            Color.parseColor("#228B22"),
            Color.parseColor("#F0FFF0"),
            Color.parseColor("#008080"),
            Color.parseColor("#B0E0E6"),
            Color.parseColor("#BA55D3"),
            Color.parseColor("#8B008B"),
            Color.parseColor("#DDA0DD"),
            Color.parseColor("#FF1493"),
            Color.parseColor("#DC143C"),
            Color.parseColor("#1E90FF"),
            Color.parseColor("#708090"),
            Color.parseColor("#1E90FF"),
            Color.parseColor("#4682B4")
    };

    {
        setTextColor(Color.WHITE);
        setTextSize(18);
        setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setColor(mColors[mRandom.nextInt(mColors.length)]);
        setPadding(LEFT_AND_RIGHT_PADDING, TOP_AND_BOTTOM_PADDING, LEFT_AND_RIGHT_PADDING,
                TOP_AND_BOTTOM_PADDING);
    }


    public FlowTextView(@NonNull Context context) {
        this(context, null);
    }

    public FlowTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRoundRect(0f, 0f, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS, mPaint);
        super.onDraw(canvas);
    }


}
