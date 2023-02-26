package com.example.seniorcustomview.customview17.helper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;

import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/25 10:29
 **/
public class CircularRevealHelper extends ConstraintHelper {
    public CircularRevealHelper(Context context) {
        this(context, null);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularRevealHelper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void updatePostLayout(ConstraintLayout container) {
        super.updatePostLayout(container);
        for (int i = 0; i < getReferencedIds().length; i++) {
            // 获取需要执行动画的 view
            View view = container.getViewById(getReferencedIds()[i]);
            if (view != null) {
                // 计算出平方根，就是图片的对角线长度
                double radius = Math.hypot(view.getWidth(), view.getHeight());
                // 由左上角顶点到右下角执行动画显示出来
                ViewAnimationUtils.createCircularReveal(view, 0, 0, 0f, (float) radius)
                        .setDuration(2000)
                        .start();
            }
        }
    }
}
