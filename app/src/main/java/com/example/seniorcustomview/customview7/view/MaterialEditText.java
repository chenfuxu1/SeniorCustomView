package com.example.seniorcustomview.customview7.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.seniorcustomview.Logit;
import com.example.seniorcustomview.R;
import com.example.seniorcustomview.Utils;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/24 22:18
 * <p>
 * 自定义 materialEditText
 **/
public class MaterialEditText extends AppCompatEditText {
    private static final String TAG = "MaterialEditText";
    private static final float TEXT_SIZE = Utils.dpToPixel(14); // 悬浮 hint 字体的大小
    private static final float TEXT_MARGIN = Utils.dpToPixel(8); // 悬浮 hint 字体距离文本框字体的间距
    private static final float HORIZONTAL_OFFSET = Utils.dpToPixel(4); // 悬浮 hint 距离边界横向偏移
    private static final float VERTICAL_OFFSET = Utils.dpToPixel(21); // 悬浮 hint 距离边界的垂直偏移
    private static final float EXTRA_VERTICAL_OFFSET = Utils.dpToPixel(12); // 隐藏 / 显示 hint 时，y 方向上的偏移值
    private float mCurrentVerticalOffset = VERTICAL_OFFSET; // 当前悬浮 hint 的偏移值
    private boolean mFloatingHintIsShow = false; // 悬浮 hint 是否显示过
    private boolean mIsUseFloatingHint = false; // 是否使用悬浮 hint 的开关，可以通过 java/xml 进行设置
    private float mFloatingHintFraction = 0f; // 悬浮 hint 动画完成度(0f - 1f)
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator mFloatingHintAnim;

    {

        Logit.d(TAG, "cfx mIsUseFloatingHint: " + mIsUseFloatingHint);
        if (mIsUseFloatingHint) {
            setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN),
                    getPaddingRight(), getPaddingBottom());
        }
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.setColor(Color.CYAN);
    }

    public MaterialEditText(@NonNull Context context) {
        super(context);
        initAttrs(context, null);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    /**
     * 注意，这个方法需要放在构造方法中进行初始化
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        /**
         * attrs 就是保存了 xml 配置里面所有属性的集合
         * 可以在 SeniorCustomView\app\build\intermediates\runtime_symbol_list\debug\R.txt 查看
         * R.styleable.MaterialEditText 表示的是一个 int 数组 --- int[] styleable MaterialEditText { 0x7f03038a, 0x7f03038b }
         * 其中：
         * 0x7f03038a 表示的为：int attr isUseFloatingHint 0x7f03038a，就是自定义的 isUseFloatingHint
         * 0x7f03038b 表示的为：int attr isUse 0x7f03038b，就是自定义的 isUse
         * 即 0x7f03038a 表示的就是 isUseFloatingHint 这个属性，0x7f03038b 表示的就是 isUse 这个属性
         * R.styleable.MaterialEditText 表示的就是包含了这些属性的数组 int 数组
         *
         * R.styleable.MaterialEditText_isUseFloatingHint --- int styleable MaterialEditText_isUseFloatingHint 0
         * 表示数组里面第 0 个元素
         * R.styleable.MaterialEditText_isUse int styleable MaterialEditText_isUse 1
         * 表示数组里面第 1 个元素
         * 其中 0 表示的是 isUseFloatingHint 在 MaterialEditText 数组中的序号是 0，isUse 在 MaterialEditText 数组中的序号是 1
         */
        if (attrs != null) {
            int attributeCount = attrs.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
                Logit.d(TAG, "cfx attributeName: " + attributeName + " ------ attributeValue: " + attributeValue);
            }
        }

        // 相当于只取出包含 MaterialEditText 的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
        // 通过 R.styleable.MaterialEditText_isUseFloatingHint （对应集合中的位置）找到对应属性的值
        boolean isUseFloatingHint = typedArray.getBoolean(R.styleable.MaterialEditText_isUseFloatingHint, true);
        boolean isUse = typedArray.getBoolean(R.styleable.MaterialEditText_isUse, true);
        Logit.d(TAG, "cfx isUseFloatingHint: " + isUseFloatingHint + " isUse: " + isUse);

        // 因此上述代码可等价写法为
        TypedArray typedArray1 = context.obtainStyledAttributes(attrs, new int[]{R.attr.isUseFloatingHint, R.attr.isUse});
        boolean isUseFloatingHint1 = typedArray1.getBoolean(0, true);
        @SuppressLint("ResourceType")
        boolean isUse1 = typedArray.getBoolean(1, true);
        Logit.d(TAG, "cfx isUseFloatingHint1: " + isUseFloatingHint1 + " isUse1: " + isUse1);
        typedArray1.recycle();

        setUseFloatingHint(isUseFloatingHint);
        typedArray.recycle();
    }

    public float getFloatingHintFraction() {
        return mFloatingHintFraction;
    }

    public void setFloatingHintFraction(float floatingHintFraction) {
        mFloatingHintFraction = floatingHintFraction;
        invalidate();
    }

    public boolean isUseFloatingHint() {
        return mIsUseFloatingHint;
    }

    public void setUseFloatingHint(boolean useFloatingHint) {
        if (mIsUseFloatingHint != useFloatingHint) {
            if (useFloatingHint) {
                setPadding(getPaddingLeft(), (int) (getPaddingTop() + TEXT_SIZE + TEXT_MARGIN),
                        getPaddingRight(), getPaddingBottom());
            } else {
                setPadding(getPaddingLeft(), (int) (getPaddingTop() - TEXT_SIZE - TEXT_MARGIN),
                        getPaddingRight(), getPaddingBottom());
            }
        }
        mIsUseFloatingHint = useFloatingHint;
    }

    /**
     * 文字发生改变时会调用
     *
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (!TextUtils.isEmpty(getText()) && !mFloatingHintIsShow) {
            // 文本框不为空，且当前没有显示悬浮 hint 时，才开始显示 hint
            showHintAnim();
            mFloatingHintIsShow = true;
        } else if (TextUtils.isEmpty(getText()) && mFloatingHintIsShow) {
            // 文本框内容为空，且当前显示了悬浮 hint，才开始隐藏 hint
            hideHintAnim();
            mFloatingHintIsShow = false;
        }
    }

    /**
     * 显示 hint 动画
     */
    private void showHintAnim() {
        resetAnim();
        mFloatingHintAnim = ObjectAnimator.ofFloat(this, "floatingHintFraction",
                0f, 1f);
        mFloatingHintAnim.setDuration(400);
        mFloatingHintAnim.start();
    }

    /**
     * 隐藏 hint 动画
     */
    private void hideHintAnim() {
        resetAnim();
        mFloatingHintAnim = ObjectAnimator.ofFloat(this, "floatingHintFraction",
                1f, 0f);
        mFloatingHintAnim.setDuration(400);
        mFloatingHintAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置当前的透明度
        mPaint.setAlpha((int) (mFloatingHintFraction * 0xff));
        // 设置当前的悬浮 hint 垂直方向的偏移量
        mCurrentVerticalOffset = VERTICAL_OFFSET + (1 - mFloatingHintFraction) * EXTRA_VERTICAL_OFFSET;
        canvas.drawText((String) getHint(), HORIZONTAL_OFFSET, mCurrentVerticalOffset, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetAnim();
    }

    private void resetAnim() {
        if (mFloatingHintAnim != null) {
            mFloatingHintAnim.cancel();
            mFloatingHintAnim = null;
        }
    }
}
