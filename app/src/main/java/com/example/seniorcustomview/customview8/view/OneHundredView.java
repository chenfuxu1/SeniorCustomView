package com.example.seniorcustomview.customview8.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/25 22:10
 **/
public class OneHundredView extends View {
    public OneHundredView(Context context) {
        this(context, null);
    }

    public OneHundredView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneHundredView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public OneHundredView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 重新设置宽高，但 xml 中父 view 是 match_parent
         * 所以可能会设置无效，可能会被父 view 强行修正
         * 例如：父 view 是 LinearLayout，这里设置会生效
         * 父 view 是 ConstraintLayout，这里设置无效，被父 view 强行修正了
         */
        setMeasuredDimension(100, 100);
    }
}
