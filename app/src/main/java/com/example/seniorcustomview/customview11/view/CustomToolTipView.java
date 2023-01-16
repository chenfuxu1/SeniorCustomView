package com.example.seniorcustomview.customview11.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/13 0:00
 **/
public class CustomToolTipView extends RelativeLayout {
    public CustomToolTipView(Context context) {
        this(context, null);
    }

    public CustomToolTipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomToolTipView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomToolTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
