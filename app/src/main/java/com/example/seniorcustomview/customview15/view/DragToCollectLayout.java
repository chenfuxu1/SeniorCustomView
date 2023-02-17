package com.example.seniorcustomview.customview15.view;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.example.seniorcustomview.R;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2023/2/15 23:46
 **/
public class DragToCollectLayout extends ConstraintLayout {
    private Context mContext;
    private ImageView mLiuYifei;
    private ImageView mJinShang;
    private LinearLayout mCollectLayout;
    private CollectListener mCollectListener = new CollectListener();
    private OnLongClickListener mDragStarterListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            // v 表示被拖拽的 view
            ClipData clipData = ClipData.newPlainText("name", v.getContentDescription());
            /**
             * 参数2：可以跨进程使用
             * 参数3：表示拖拽过程中 view 的显示内容，可以自定义
             * 参数4：只能本地使用
             */
            ViewCompat.startDragAndDrop(v, clipData, new DragShadowBuilder(v), null, 0);
            return true;
        }
    };

    public DragToCollectLayout(@NonNull Context context) {
        this(context, null);
    }

    public DragToCollectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragToCollectLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragToCollectLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        mLiuYifei = findViewById(R.id.avatar_view);
        mJinShang = findViewById(R.id.logo_view);
        mCollectLayout = findViewById(R.id.collector_layout);
    }

    private void initListener() {
        mLiuYifei.setOnLongClickListener(mDragStarterListener);
        mJinShang.setOnLongClickListener(mDragStarterListener);
        mCollectLayout.setOnDragListener(mCollectListener);
    }

    private class CollectListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            /**
             * 只需要监听落下事件，当有 view 进入到 linearLayout 中，在 LinearLayout 中添加 TextView
             */
            if (event.getAction() == DragEvent.ACTION_DROP) {
                if (v instanceof LinearLayout) {
                    TextView textView = new TextView(mContext);
                    textView.setTextSize(16);
                    textView.setText(event.getClipData().getItemAt(0).getText());
                    ((LinearLayout) v).addView(textView);
                }
            }
            return true;
        }
    }
}
