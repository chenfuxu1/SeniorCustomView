package com.example.seniorcustomview.customview15.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/12 23:04
 *
 *
 **/
public class DragListenerGridView extends ViewGroup {
    private static final int COLUMNS = 2; // 两列
    private static final int ROWS = 3; // 三行
    private DragListener mDragListener;
    private View mDraggedView;
    private List<View> mOrderedChildren = new ArrayList<>();

    {
        setChildrenDrawingOrderEnabled(true);
    }

    public DragListenerGridView(Context context) {
        this(context, null);
    }

    public DragListenerGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragListenerGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragListenerGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDragListener = new DragListener();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            // 给每个 view 设置长按监听
            child.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mDraggedView = v;
                    v.startDrag(null, new DragShadowBuilder(v), v, 0);
                    return false;
                }
            });
            child.setOnDragListener(mDragListener);
            mOrderedChildren.add(child); // 初始化位置
        }
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);
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
        /**
         * 摆放的时候全都放在左上角
         * 然后通过 translation 动画进行位移
         */
        for (int i = 0; i < getChildCount(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            View child = getChildAt(i);
            child.layout(0, 0, childWidth, childHeight);
            child.setTranslationX(childLeft);
            child.setTranslationY(childTop);
        }
    }

    /**
     * 拖拽过程中，将 view 进行重新排序
     * @param targetView
     */
    private void sort(View targetView) {
        int draggedIndex = -1;
        int targetIndex = -1;
        // 遍历所有的 view，找到拖拽的 view 进入到哪个 view 的范围内，同时找到拖拽 view 的 index
        for (int i = 0; i < mOrderedChildren.size(); i++) {
            View view = mOrderedChildren.get(i);
            if (targetView == view) {
                targetIndex = i; // 找到当前拖拽的 view 进入到哪个 view 的区域范围内了
            } else if (mDraggedView == view){
                draggedIndex = i; // 找到当前拖拽 view 的 index
            }
        }
        mOrderedChildren.remove(draggedIndex); // 先移除当前拖拽的 view
        mOrderedChildren.add(targetIndex, mDraggedView); // 再将当前拖拽的 view 放在进入到目标区域的位置上
        float childLeft = 0;
        float childTop = 0;
        float childWidth = getWidth() / COLUMNS;
        float childHeight = getHeight() / ROWS;
        for (int i = 0; i < mOrderedChildren.size(); i++) {
            childLeft = i % 2 * childWidth;
            childTop = i / 2 * childHeight;
            View view = mOrderedChildren.get(i);
            view.animate()
                    .translationX(childLeft)
                    .translationY(childTop)
                    .setDuration(150);
        }

    }

    // 拖拽 view 过程的监听器
    private class DragListener implements OnDragListener {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                /**
                 * 拖拽刚刚开始的时候调用
                 * v.startDrag(null, new DragShadowBuilder(v), v, 0); 在开始拖拽的时候传进来的 v 就是
                 * event.getLocalState()
                 * 当 event.getLocalState() 等于当前拖拽的 view 时，主动隐藏原位置的 view
                 */
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getLocalState() == view) {
                        view.setVisibility(View.INVISIBLE);
                    }
                    break;
                /**
                 * 表示手指进入了某个 view 的区域内，该事件便会触发
                 * 拖拽的过程中，手指进入了其他 view 的区域内，该事件也会触发
                 */
                case DragEvent.ACTION_DRAG_ENTERED:
                    // 当不是当前拖拽的 view，其他的 view 才进行重新排序
                    if (event.getLocalState() != view) {
                        sort(view);
                    }
                    break;
                /**
                 * 拖拽结束后，需要将拖拽 view 的原 view 给显示出来
                 * 因为在一开始拖拽的时候隐藏了
                 */
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getLocalState() == view) {
                        view.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;

            }
            return true;
        }
    }
}
