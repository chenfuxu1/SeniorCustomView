package com.example.seniorcustomview.customview5.view.province;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Project: SeniorCustomView
 * Create By: ChenFuXu
 * DateTime: 2022/12/21 21:41
 **/
public class ProvinceView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String[] mProvinces = new String[]{"北京市",
            "天津市",
            "上海市",
            "重庆市",
            "河北省",
            "山西省",
            "辽宁省",
            "吉林省",
            "黑龙江省",
            "江苏省",
            "浙江省",
            "安徽省",
            "福建省",
            "江西省",
            "山东省",
            "河南省",
            "湖北省",
            "湖南省",
            "广东省",
            "海南省",
            "四川省",
            "贵州省",
            "云南省",
            "陕西省",
            "甘肃省",
            "青海省",
            "台湾省",
            "内蒙古自治区",
            "广西壮族自治区",
            "西藏自治区",
            "宁夏回族自治区",
            "新疆维吾尔自治区",
            "香港特别行政区",
            "澳门特别行政区"};
    private ProvinceEvaluator mProvinceEvaluator;
    private String mCurrentText = mProvinces[0];
    private ObjectAnimator mObjectAnimator;

    {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(140);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mProvinceEvaluator = new ProvinceEvaluator();

        // setLayerType(LAYER_TYPE_HARDWARE, null); 设置 view 的离屏缓冲，并通过硬件绘制的方式实现
        // setLayerType(LAYER_TYPE_SOFTWARE, null); 设置 view 的离屏缓冲，并通过软件绘制的方式实现
        // setLayerType(LAYER_TYPE_HARDWARE, null); 不使用离屏缓冲
    }

    public ProvinceView(Context context) {
        this(context, null);
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProvinceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(mCurrentText, getWidth() / 2, getHeight() / 2, mPaint);
    }

    public void setCurrentText(String currentText) {
        this.mCurrentText = currentText;
        invalidate();
    }

    public void startAnim() {
        reset();
        mProvinceEvaluator.setReverse(false);
        // 最终状态值和这里设置的最后一个参数没有关系，取决于估值器 evaluator 中最终状态返回的值
        mObjectAnimator = ObjectAnimator.ofObject(this, "currentText", mProvinceEvaluator, mProvinces[mProvinces.length - 1]);
        mObjectAnimator.setDuration(6000);
        mObjectAnimator.start();
    }

    public void resetAnim() {
        reset();
        mProvinceEvaluator.setReverse(true);
        // 最终状态值和这里设置的最后一个参数没有关系，取决于估值器 evaluator 中最终状态返回的值
        mObjectAnimator = ObjectAnimator.ofObject(this, "currentText", mProvinceEvaluator, mProvinces[0]);
        mObjectAnimator.setDuration(6000);
        mObjectAnimator.start();
    }

    private class ProvinceEvaluator implements TypeEvaluator<String> {
        private boolean mIsReverse = false;

        public void setReverse(boolean reverse) {
            mIsReverse = reverse;
        }

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            int startIndex = 0;
            int endIndex = mProvinces.length - 1;
            int currentIndex = mIsReverse ? (int) (endIndex - (endIndex - startIndex) * fraction)
                    : (int) (startIndex + (endIndex - startIndex) * fraction);
            return mProvinces[currentIndex]; // 返回当前省份的字符串名称，然后会设置给 currentText，进行重新绘制
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    private void reset() {
        if (mObjectAnimator != null) {
            mObjectAnimator.cancel();
            mObjectAnimator = null;
        }
    }
}
