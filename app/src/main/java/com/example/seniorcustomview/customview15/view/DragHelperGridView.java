package com.example.seniorcustomview.customview15.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/12 23:04
 *
 * 1、在 ViewGroup 中初始化 ViewDragHelper，因为这个类是针对于 ViewGroup 控制里面子 view 的
 * 2、处理拦截事件，将拦截事件交给 ViewDragHelper 去处理，即重写 onInterceptTouchEvent 方法
 * 3、拦截事件给了还不行，还得把 touchEvent 事件重写
 * 4、重写 ComputeScroll() 方法, 因为 viewDragHelper 内部是通过 Scroller 类实现的滑动，所以需要重写
 * computeScroll 来实现控件的平滑滚动
 * 5、实现 ViewDragHelper.Callback
 **/
public class DragHelperGridView extends ViewGroup {
    private ViewDragHelper mViewDragHelper;
    private DragCallback mDragCallback;
    private static final int COLUMNS = 2; // 两列
    private static final int ROWS = 3; // 三行

    public DragHelperGridView(Context context) {
        this(context, null);
    }

    public DragHelperGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragHelperGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragHelperGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDragCallback = new DragCallback();
        // 1、在 ViewGroup 中初始化 ViewDragHelper，因为这个类是针对于 ViewGroup 控制里面子 view 的
        mViewDragHelper = ViewDragHelper.create(this, mDragCallback);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = specWidth / COLUMNS;
        int childHeight = specHeight / ROWS;
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY));
        setMeasuredDimension(specWidth, specHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childTop = 0;
        int childWidth = getWidth() / COLUMNS; // 子 view 的宽度
        int childHeight = getHeight() / ROWS; // 子 view 的高度
        for (int i = 0; i < getChildCount(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }
    }

    /**
     * 2、处理拦截事件，将拦截事件交给 ViewDragHelper 去处理，即重写 onInterceptTouchEvent 方法
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        // 交给 mViewDragHelper 去处理拦截事件
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    // 3、拦截事件给了还不行，还得把 touchEvent 事件重写
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 让 mViewDragHelper 去处理 TouchEvent 事件(固定写法)
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    // 4、重写 ComputeScroll() 方法, 因为 viewDragHelper 内部是通过 Scroller 类实现的滑动，所以需要重写
    @Override
    public void computeScroll() {
        // 让 mViewDragHelper 全权负责(固定写法)
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    // 5、实现 ViewDragHelper.Callback
    private class DragCallback extends ViewDragHelper.Callback {
        private float mCapturedLeft = 0f;
        private float mCapturedTop = 0f;

        // view 是否可以拖动
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        // 控制水平方向上的位置滑动
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            /**
             * left 表示的是水平方向上的子 view 的移动距离，dx 表示的是相较上一次的增量
             * 返回值表示在水平方向上滑动的距离，返回 0 表示在水平方向上不做滑动
             */
            return left;
        }

        // 控制垂直方向上的位置滑动
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // top 表示的是垂直方向上的子 view 的移动距离，dy 表示的是相较上一次的增量，这里直接返回 top
            return top;
        }

        // 当 capturedChild 被捕获时调用. 其实就是上面方法 tryCaptureView() return true 之后，点击 view 被调用
        @Override
        public void onViewCaptured(@NonNull View capturedChild, int activePointerId) {
            capturedChild.setElevation(getElevation() + 1); // z 轴加 1，这样拖拽的 view 可以显示在任何兄弟 view 的上方
            mCapturedLeft = capturedChild.getLeft();
            mCapturedTop = capturedChild.getTop();
        }

        /**
         * 该方法在子 view 位置发生改变时都会被调用，可以在这个方法中做一些拖动过程中渐变的动画等操作
         * @param changedView
         * @param left
         * @param top
         * @param dx
         * @param dy
         */
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**
         * 该方法在手势拖动释放的时候被调用，可以在这里设置子 View 预期到达的位置，如果人为的手势拖动没有
         * 到达预期位置，我们可以让子 View 在人为的拖动结束后，再自动的滑动到指定位置
         * @param releasedChild
         * @param xvel
         * @param yvel
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            // 设置 view 自动滚动到顶部
            mViewDragHelper.settleCapturedViewAt((int) mCapturedLeft, (int) mCapturedTop);
            postInvalidateOnAnimation();
        }
    }
}
