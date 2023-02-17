package com.example.seniorcustomview.customview15.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/16 23:55
 **/
public class DragUpDownLayout extends FrameLayout {
    private static final String TAG = "DragUpDownLayout";
    private ViewDragHelper.Callback mDragCallback;
    private ViewDragHelper mViewDragHelper;
    private ViewConfiguration mViewConfiguration;
    private View mDraggedView;

    public DragUpDownLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragUpDownLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mDragCallback = new DragCallback();
        mViewDragHelper = ViewDragHelper.create(this, mDragCallback);
        mViewConfiguration = ViewConfiguration.get(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDraggedView = findViewById(R.id.dragged_view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mDraggedView;
        }

        // 只能在垂直方向上滑动
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (Math.abs(yvel) > mViewConfiguration.getScaledMinimumFlingVelocity()) {
                if (yvel > 0) {
                    mViewDragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }
}
