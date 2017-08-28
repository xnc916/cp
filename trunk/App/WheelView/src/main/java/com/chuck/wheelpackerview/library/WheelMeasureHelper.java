package com.chuck.wheelpackerview.library;

import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import com.chuck.wheelpackerview.library.adapter.BaseWheelAdapter;
import com.chuck.wheelpackerview.library.listener.IWheelViewModel;

/**
 * 测量相关属性
 * Created by Chuckchen on 16/12/22.
 */

public class WheelMeasureHelper {
    static final float lineSpacingMultiplier = 2.0F;
    static final float SELECTTEXTOFFSET = 6;
    int measuredHeight;
    int measuredWidth;
    int maxTextWidth;
    int maxTextHeight;
    float itemHeight;
    /**
     * 半径
     */
    int radius;
    /**
     * 半圆周长
     */
    int halfCircumference;
    /**
     * 第一条线Y坐标值
     */
    float topLineY;
    /**
     * 第二条线Y坐标
     */
    float bottomLineY;
    /**
     * 中间Y坐标
     */
    float centerY;

    /**
     * 测量最大文字文字的width和height
     */
    protected void measureMaxLengthText(BaseWheelAdapter adapter
            , WheelItemStyleHelper styleOptions) {
        Rect rect = new Rect();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            IWheelViewModel model = adapter.getItem(i);
            String valueString;
            if (model == null) {
                valueString = "";
            } else {
                valueString = model.getValueString();
            }
            if (valueString == null) {
                valueString = "";
            }
            styleOptions.selectTextPaint.getTextBounds(valueString
                    , 0, valueString.length(), rect);
            int width = rect.width();
            if (width > maxTextWidth) {
                maxTextWidth = width;
            }
            styleOptions.selectTextPaint.getTextBounds("轮", 0, 1, rect);
            int height = rect.height();
            if (height > maxTextHeight) {
                maxTextHeight = height;
            }
        }
        itemHeight = lineSpacingMultiplier * maxTextHeight;
    }

    void remeasure(BaseWheelAdapter adapter, WheelItemStyleHelper styleOptions,
                   int widthMeasureSpec, int itemVisibleCount) {
        if (adapter == null) {
            return;
        }
        measureMaxLengthText(adapter, styleOptions);
        //最大Text的高度乘间距倍数得到 可见文字实际的总高度，半圆的周长
        halfCircumference = (int) (itemHeight * (itemVisibleCount - 1));
        //整个圆的周长除以PI得到直径，这个直径用作控件的总高度
        measuredHeight = (int) ((halfCircumference * 2) / Math.PI);
        //半径
        radius = measuredHeight / 2;
        //控件的宽度
        measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec);

        topLineY = (measuredHeight - itemHeight) / 2.0f;
        bottomLineY = (measuredHeight + itemHeight) / 2.0f;
        centerY = (maxTextHeight + maxTextHeight) - SELECTTEXTOFFSET;


    }
}
