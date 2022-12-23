package com.example.seniorcustomview.customview2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/14 20:55
 **/
public class XfermodeLayout extends RelativeLayout {
    public XfermodeLayout(Context context) {
        this(context, null);
    }

    public XfermodeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public XfermodeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
