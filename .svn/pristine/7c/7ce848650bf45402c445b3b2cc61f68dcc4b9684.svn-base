package com.chuck.wheelpackerview.library;


import android.graphics.Paint;
import android.view.Gravity;

import com.chuck.wheelpackerview.R;

/**
 * 文字大小，颜色，画笔属性设置都在这儿
 * Created by Chuckchen on 16/12/21.
 */

public class WheelItemStyleHelper {
    protected final int COLOR_TEXT_UNSELECT_DEFAULT = R.color.wheel_item_unselect_text_color_default;
    protected final int COLOR_TEXT_SELECT_DEFAULT = R.color.wheel_item_select_text_color_default;
    protected final int COLOR_BACKGROUND_DEFAULT = R.color.wheel_item_background_color;
    protected final int TEXTSIZE_SELECT = R.dimen.select_textsize_default;
    protected final int TEXTSIZE_UNSELECT = R.dimen.unselect_textsize_default;
    protected final int GRAVITY_DEFAULT = Gravity.CENTER;


    protected int selectTextColor;
    protected int unSelectTextColor;
    protected int backgroundColor;
    protected int unSelectTextSize;
    protected int selectTextSize;
    protected int mGravity;

    protected Paint selectTextPaint;
    protected Paint unSelectTextPain;
    protected Paint linePain;

    protected void initTextPaints() {
        selectTextPaint = new Paint();
        selectTextPaint.setColor(selectTextColor);
        selectTextPaint.setTextSize(selectTextSize);
        selectTextPaint.setAntiAlias(true);

        unSelectTextPain = new Paint();
        unSelectTextPain.setAntiAlias(true);
        unSelectTextPain.setTextSize(unSelectTextSize);
        unSelectTextPain.setColor(unSelectTextColor);

        linePain = new Paint();
        linePain.setColor(backgroundColor);
        linePain.setAntiAlias(true);
    }

    public void setSelectTextColor(int color) {
        if (selectTextPaint != null) {
            selectTextPaint.setColor(color);
        }
    }

    public void setSelectTextSize(int dimen) {
        if (selectTextPaint != null) {
            selectTextPaint.setTextSize(dimen);
        }
    }

    public void setUnselectTextColor(int color) {
        if (unSelectTextPain != null) {
            unSelectTextPain.setColor(color);
        }
    }

    public void setUnselectTextSize(int dimen) {
        if (unSelectTextPain != null) {
            unSelectTextPain.setTextSize(dimen);
        }
    }
}
