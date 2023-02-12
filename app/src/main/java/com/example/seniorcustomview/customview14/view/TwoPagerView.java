package com.example.seniorcustomview.customview14.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/11 11:52
 *
 * 自定义两页的 ViewPager
 **/
public class TwoPagerView extends ViewGroup {
    private float mDownX = 0f;
    private float mDownY = 0f;
    private float mDownScrollX = 0f;
    private boolean mIsScrolling = false;
    private OverScroller mOverScroller;
    private ViewConfiguration mViewConfiguration;
    private VelocityTracker mVelocityTracker;
    private int mMinimumFlingVelocity;
    private int mMaximumFlingVelocity;
    private int mScaledPagingTouchSlop;


    public TwoPagerView(Context context) {
        this(context, null);
    }

    public TwoPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TwoPagerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mOverScroller = new OverScroller(context);
        mViewConfiguration = ViewConfiguration.get(context);
        mVelocityTracker = VelocityTracker.obtain();
        mMinimumFlingVelocity = mViewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = mViewConfiguration.getScaledMaximumFlingVelocity();
        // 获取开始滑动的阈值
        mScaledPagingTouchSlop = mViewConfiguration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childTop = 0;
        int childRight = getWidth();
        int childBottom = getHeight();
        for (int i = 0; i < getChildCount(); i++) { // 只有两个子 view
            View child = getChildAt(i);
            child.layout(childLeft, childTop, childRight, childBottom);
            /**
             * 一个子 view 摆放完成后，另一个子 view 的左右坐标加上当前 view 的宽度
             */
            childLeft += getWidth();
            childRight += getWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(ev);
        boolean result = false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mIsScrolling = false;
                mDownX = ev.getX();
                mDownY = ev.getY();
                mDownScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                /**
                 * 没开始滑动时判断，开始滑动后不用判断了
                 */
                if (!mIsScrolling) {
                    float dx = mDownX - ev.getX();
                    if (Math.abs(dx) > mScaledPagingTouchSlop) {
                        mIsScrolling = true;
                        /**
                         * 通知父 view 不要拦截了
                         */
                        getParent().requestDisallowInterceptTouchEvent(true);
                        result = true;
                    }
                }
                break;
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mVelocityTracker.clear();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mDownScrollX = getScrollX();
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) (mDownX - event.getX() + mDownScrollX);
                dx = Math.min(dx, getWidth());
                dx = Math.max(dx, 0);
                scrollTo(dx, 0);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                /**
                 * 计算当前的速度
                 * 1000：表示在 1000 ms 移动的像素个数
                 * mMaximumFlingVelocity：速度上限，如果超过便返回该值
                 */
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity); // 5m/s 5km/h
                float xVelocity = mVelocityTracker.getXVelocity();
                float scrollX = getScrollX();
                int targetPage = 0;
                if (Math.abs(xVelocity) < mMinimumFlingVelocity) {
                    // 如果滑动的距离大于当前 view 的一半，那么直接到下一页，否则仍是本页
                    targetPage = (scrollX > getWidth() / 2) ? 1 : 0;
                } else {
                    targetPage = (xVelocity < 0) ? 1 : 0;
                }
                float scrollDistance = (targetPage == 1) ? (getWidth() - scrollX) : -scrollX;
                mOverScroller.startScroll(getScrollX(), 0, (int) scrollDistance, 0);
                postInvalidateOnAnimation();
                break;
        }
        return true;

    }

    @Override
    public void computeScroll() {
        if (mOverScroller.computeScrollOffset()) {
            scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            postInvalidateOnAnimation();
        }
    }
}
