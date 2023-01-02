package com.example.seniorcustomview.customview10.view.tagview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniorcustomview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/28 22:50
 *
 * 自定义布局之 Layout 自定义
 **/
public class TagLayout extends ViewGroup {
    // 用来保存每个子 view 的上下左右坐标
    private List<Rect> mChildBounds = new ArrayList<>();
    private int mSelfWidth; // TagLayout 的宽度
    private int mSelfHeight; // TagLayout 的高度
    private int mCurrentLineWidthUsed = PADDING; // TagLayout 的当前行已用宽度
    private int mHeightUsed; // TagLayout 已用高度
    private int mCurrentLineMaxHeight; // TagLayout 每一行中，最高的子 view 的高度
    private static final int PADDING = (int) Utils.dpToPixel(5);

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            mChildBounds.add(new Rect());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 当前行已用宽度
        mCurrentLineWidthUsed = PADDING;
        // 已用高度
        mHeightUsed = PADDING;
        // 每行中最高子 view 的高度
        mCurrentLineMaxHeight = 0;

        // 1、获取 TagLayout 的父 view 对 TagLayout 的限制
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 2、遍历所有子 view 进行测量
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            // 3、对每个子 view 进行测量
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, mHeightUsed);
            // 4、如果父 view 的限制无上限，或者当前已用宽度 + 当前子 view 的宽度 > 父 view 限制的宽度，那么需要换行
            if (specWidthMode != MeasureSpec.UNSPECIFIED
                    && mCurrentLineWidthUsed + childView.getMeasuredWidth() + PADDING > specWidthSize) {
                mCurrentLineWidthUsed = PADDING; // 当前行已用宽度置为 0
                mHeightUsed = mHeightUsed + mCurrentLineMaxHeight + PADDING; // 已经换行了，已经使用的高度等于：mHeightUsed + 上一行的最高高度
                mCurrentLineMaxHeight = 0; // 上一行中最高高度置为 0
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, mHeightUsed); // 重新测量一遍
            }
            // 5、取出每个子 view 测量后的坐标，进行保存
            Rect childBound = mChildBounds.get(i);
            /**
             * left: 已用宽度
             * top：已用高度
             * right：已用宽度 + 当前子 view 的宽度
             * bottom：已用高度 + 当前子 view 的高度
             * 测量阶段，可用的是 getMeasuredWidth，getMeasuredHeight，不是 getWidth 、getHeight
             */
            childBound.set(mCurrentLineWidthUsed, mHeightUsed, mCurrentLineWidthUsed + childView.getMeasuredWidth(), mHeightUsed + childView.getMeasuredHeight());
            // 6、当前行已用宽度：mCurrentLineWidthUsed + 当前子 view 的测量宽度
            mCurrentLineWidthUsed = mCurrentLineWidthUsed + childView.getMeasuredWidth() + PADDING;
            // 7、当前行最大的高度：mCurrentLineMaxHeight 与当前子 view 的最大值
            mCurrentLineMaxHeight = Math.max(mCurrentLineMaxHeight, childView.getMeasuredHeight());
        }
        // 8、TagLayout 的宽度高度，与父 view 的限制设置的值保持一致
        mSelfWidth = specWidthSize;
        mSelfHeight = specHeightSize;
        // 9、重新设置 TagLayout 的宽度高度
        setMeasuredDimension(mSelfWidth, mSelfHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 1、遍历所有的子 view
        for (int i = 0; i < getChildCount(); i++) {
            // 2、取出对应子 view 的坐标
            Rect childBounds = mChildBounds.get(i);
            // 3、对每个子 view 进行摆放布局
            getChildAt(i).layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        /**
         * 使用 measureChildWithMargins 方法，需要重写 generateLayoutParams 方法
         * 否则类型 LayoutParams 不匹配
         */
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    /**
     * =====================================================================================
     * 下面 switch/case 等价于
     * measureChildWithMargins(childView, widthMeasureSpec, 宽度已用空间, heightMeasureSpec,
     * 高度已用空间); 方法
     * 参数1：子 view
     * 参数2：TagLayout 的宽度压缩属性
     * 参数1：TagLayout 宽度已用空间
     * 参数1：TagLayout 的高度压缩属性
     * 参数1：TagLayout 高度度已用空间
     */
    // switch (layoutParams.width) {
    //     case LayoutParams.MATCH_PARENT:
    //         switch (specWidthMode) {
    //             case MeasureSpec.EXACTLY:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 是一个精确的值，例如：100 dp
    //                  * 而子 childView 的 layoutParams.width 是 MATCH_PARENT，表明要填满整个
    //                  * TagLayout 的宽度（specWidthSize - 已用空间）
    //                  * 因此，子 view 的宽度为：specWidthSize - 已用空间，子 view 的限制：因为给
    //                  * 子 view 也是一个确切的值，所以限制为：EXACTLY
    //                  */
    //             case MeasureSpec.AT_MOST:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 是一个上限值，不知道具体多大
    //                  * 而子 childView 的 layoutParams.width 是 MATCH_PARENT，表明要填满整个
    //                  * 因此，TagLayout 的可用空间有多大，全部都设置给 childView 即可
    //                  * 因此，子 view 的宽度为：specWidthSize - 已用空间，子 view 的限制：因为
    //                  * TagLayout 的宽度全部都给了子 view，所以是一个精确的值，所以限制为：EXACTLY
    //                  */
    //                 specChildWidthMode = MeasureSpec.EXACTLY;
    //                 specChildWidthSize = specWidthSize - 已用空间;
    //                 break;
    //             case MeasureSpec.UNSPECIFIED:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 没有限制，表示可用空间是无限的
    //                  * 但是子 childView 的 layoutParams.width 是 MATCH_PARENT，表明要填满整个
    //                  * 这又该如何处理呢？
    //                  * 因此，这里就不对子 view 进行限制了，子 view 的宽度：0
    //                  * 子 view 的限制：UNSPECIFIED
    //                  */
    //                 specChildWidthMode = MeasureSpec.UNSPECIFIED;
    //                 specChildWidthSize = 0;
    //                 break;
    //         }
    //         break;
    //     case LayoutParams.WRAP_CONTENT:
    //         switch (specWidthMode) {
    //             case MeasureSpec.EXACTLY:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 是一个精确的值，例如：100 dp
    //                  * 而子 childView 的 layoutParams.width 是 WRAP_CONTENT，表明包裹子 view 大小即可
    //                  * 因此，对于子 view 的宽度，可以随便测量，但是不能超过 TagLayout 宽度的上限
    //                  * 即不能超过（specWidthSize - 已用空间）
    //                  * 因此，子 view 的宽度上限为：specWidthSize - 已用空间，
    //                  * 子 view 的限制：因为是一个上限值，所以限制为：AT_MOST
    //                  */
    //             case MeasureSpec.AT_MOST:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 是一个上限值，不知道具体多大
    //                  * 而子 childView 的 layoutParams.width 是 WRAP_CONTENT，表明包裹子 view 大小即可
    //                  * 因此，对于子 view 的宽度，可以随便测量，但是不能超过 TagLayout 宽度的上限值
    //                  * 即不能超过（specWidthSize - 已用空间）
    //                  * 因此，子 view 的宽度上限为：specWidthSize - 已用空间，
    //                  * 子 view 的限制：因为是一个上限值，所以限制为：AT_MOST
    //                  */
    //                 specChildWidthMode = MeasureSpec.AT_MOST;
    //                 specChildWidthSize = specWidthSize - 已用空间;
    //                 break;
    //             case MeasureSpec.UNSPECIFIED:
    //                 /**
    //                  * 表示 TagLayout 的父 view 对 TagLayout 没有限制，表示可用空间是无限的
    //                  * 而子 childView 的 layoutParams.width 是 WRAP_CONTENT，表明包裹子 view 大小即可
    //                  * 表明子 view 随便测量即可，这又该如何处理呢
    //                  * 因此，这里就不对子 view 进行限制了，子 view 的宽度：0
    //                  * 子 view 的限制：可用空间无上限，对子 view 的大小又没有限制，直接给子
    //                  * view 的 mode 设置为 UNSPECIFIED 即可
    //                  */
    //                 specChildWidthMode = MeasureSpec.UNSPECIFIED;
    //                 specChildWidthSize = 0;
    //                 break;
    //         }
    //         break;
    //     default:
    //         /**
    //          * 表示对于子 childView 设置的是固定值大小
    //          * 因为开发者已经固定了子 view 的尺寸大小，所以不存在尺寸空间的问题
    //          * 因此，直接用 EXACTLY 把子 view 的尺寸限制为 layoutParams.width 即可
    //          */
    //         specChildWidthMode = MeasureSpec.EXACTLY;
    //         specChildWidthSize = layoutParams.width;
    //         break;
    // }
    /**
     * =====================================================================================
     */
}
