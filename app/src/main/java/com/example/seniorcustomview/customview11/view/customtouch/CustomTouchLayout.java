package com.example.seniorcustomview.customview11.view.customtouch;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:33
 **/
public class CustomTouchLayout extends RelativeLayout {
    public CustomTouchLayout(Context context) {
        this(context, null);
    }

    public CustomTouchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomTouchLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
