package com.example.seniorcustomview.customview10.view.flowview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/1/1 20:33
 **/
public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private List<String> mDatas = new ArrayList<>(); // 所有子 view 的文字集合
    private List<Rect> mChildBounds = new ArrayList<>(); // 所有子 view 的坐标
    private int mCurrentLineWidthUsed = 0; // 当前行已使用宽度
    private int mHeightUsed = 0; // 当前行以上的 y 坐标
    private static final int PADDING = (int) Utils.dpToPixel(10);
    private int mLeftMargin = PADDING;
    private int mRightMargin = PADDING;
    private int mTopMargin = PADDING;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        float itemLeftMargin = typedArray.getDimension(R.styleable.FlowLayout_itemLeftMargin, PADDING);
        float itemTopMargin = typedArray.getDimension(R.styleable.FlowLayout_itemTopMargin, PADDING);
        float itemRightMargin = typedArray.getDimension(R.styleable.FlowLayout_itemRightMargin, PADDING);
        float itemBottomMargin = typedArray.getDimension(R.styleable.FlowLayout_itemBottomMargin, PADDING);
        mLeftMargin = (int) itemLeftMargin;
        mTopMargin = (int) itemTopMargin;
        mRightMargin = (int) itemRightMargin;
        typedArray.recycle();
    }

    public void setData(List<String> provinces) {
        mDatas.clear();
        mDatas.addAll(provinces);
        setUpChildView();
    }

    /**
     * 创建子 view
     */
    private void setUpChildView() {
        // 1、先清空原来的子 view
        removeAllViews();
        mChildBounds.clear();
        // 2、循环进行添加
        for (String text : mDatas) {
            FlowTextView flowTextView = new FlowTextView(this.getContext());
            flowTextView.setText(text);
            flowTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logit.d(TAG, "cfx flowTextView click: " + flowTextView.getText());
                }
            });
            addView(flowTextView);
            mChildBounds.add(new Rect());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mDatas.size() == 0) {
            // 如果子 view 为空，直接返回
            return;
        }
        mCurrentLineWidthUsed = mLeftMargin;
        mHeightUsed = mTopMargin;
        // 1、获取 FlowLayout 的父 view 对 FlowLayout 的限制
        int selfSpecWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfSpecWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int selfSpecHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 2、遍历所有子 view 进行测量
        for (int i = 0; i < mDatas.size(); i++) {
            View childView = getChildAt(i);
            Rect childBound = mChildBounds.get(i);
            // 3、对每个子 view 进行测量
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, mHeightUsed);
            // 4、如果父 view 的限制无上限，或者当前已用宽度 + 当前子 view 的宽度 + margin > 父 view 限制的宽度，那么需要换行
            if (mCurrentLineWidthUsed + childView.getMeasuredWidth() + mRightMargin > selfSpecWidthSize
                    && selfSpecWidthMode != MeasureSpec.UNSPECIFIED) {
                mCurrentLineWidthUsed = mLeftMargin;
                mHeightUsed = mHeightUsed + childView.getMeasuredHeight() + mTopMargin;
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, mHeightUsed);
            }
            // 5、取出每个子 view 测量后的坐标，进行保存
            childBound.set(mCurrentLineWidthUsed, mHeightUsed, mCurrentLineWidthUsed + childView.getMeasuredWidth(),
                    mHeightUsed + childView.getMeasuredHeight());
            mCurrentLineWidthUsed += childView.getMeasuredWidth() + mLeftMargin;
        }
        // 6、重新设置 FlowLayout 的宽度高度
        setMeasuredDimension(selfSpecWidthSize, selfSpecHeightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < mChildBounds.size(); i++) {
            View child = getChildAt(i);
            Rect childBound = mChildBounds.get(i);
            child.layout(childBound.left, childBound.top, childBound.right, childBound.bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        /**
         * 使用 measureChildWithMargins 方法，需要重写 generateLayoutParams 方法
         * 否则类型 LayoutParams 不匹配
         */
        return new CustomMarginLayoutParams(this.getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new CustomMarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    private class CustomMarginLayoutParams extends MarginLayoutParams {

        public CustomMarginLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public CustomMarginLayoutParams(int width, int height) {
            super(width, height);
        }

        public CustomMarginLayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public CustomMarginLayoutParams(LayoutParams source) {
            super(source);
        }
    }
}
