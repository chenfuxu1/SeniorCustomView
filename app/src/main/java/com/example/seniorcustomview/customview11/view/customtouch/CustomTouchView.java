package com.example.seniorcustomview.customview11.view.customtouch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/11 23:34
 **/
public class CustomTouchView extends View {
    public CustomTouchView(Context context) {
        this(context, null);
    }

    public CustomTouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomTouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 触摸代码
     * @param event
     * @return
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            // 当抬起手指时，处理点击事件
            performClick();
        }
        /**
         * 1、这个返回值只在 down 事件的时候返回才有意义，如果 down 事件时返回 true，表示该 view 处理该事件
         * 如果返回 false 那么向下传递该事件, 只有这一次机会
         *
         * 2、表示手指按下:
         * MotionEvent.ACTION_DOWN
         * MotionEvent.ACTION_UP
         * MotionEvent.ACTION_CANCEL
         * MotionEvent.ACTION_MOVE
         *
         * 表示非第一个手指按下:
         * MotionEvent.ACTION_POINTER_DOWN
         * MotionEvent.ACTION_POINTER_UP
         */
        return true; // 等价与 return event.getActionMasked() == MotionEvent.ACTION_DOWN;
    }
}
