package com.example.seniorcustomview.customview7.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 22:19
 **/
public class MaterialEditTextView extends RelativeLayout {

    public MaterialEditTextView(Context context) {
        this(context, null);
    }

    public MaterialEditTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MaterialEditTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
