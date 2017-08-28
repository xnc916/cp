package com.chuck.wheelpackerview.library;

import com.chuck.wheelpackerview.library.WheelItemView.H;

import java.util.TimerTask;


public class InertiaTask extends TimerTask {
    private float value;
    private final float MAX_Y = 2000f;
    private final float MIN_Y = -2000f;
    private final float velocityY;
    private WheelItemView view;

    public InertiaTask(WheelItemView view, float velocityY) {
        this.value = Float.MAX_VALUE;
        this.velocityY = velocityY;
        this.view = view;
    }

    @Override
    public void run() {
        if (value == Float.MAX_VALUE) {
            if (Math.abs(velocityY) > MAX_Y) {
                if (velocityY > 0f) {
                    value = MAX_Y;
                } else {
                    value = MIN_Y;
                }
            } else {
                value = velocityY;
            }
        }
        float tempValue = Math.abs(value);
        if (tempValue > 0f && tempValue <= 30F) {
            view.cancelFuture();
            view.getHandler().sendEmptyMessage(H.WHAT_SMOOTH_SCROLL);
            return;
        }
        int i = (int) ((value * 10F) / 1000F);
        view.scrollY = view.scrollY - i;
        float itemHeight = view.getMeasureHelper().itemHeight;
        float top = (-view.initPosition) * itemHeight;
        float bottom = (view.getItemCount() - 1 - view.initPosition) * itemHeight;
        if (view.scrollY - itemHeight * 0.3 < top) {
            top = view.scrollY + i;
        } else if (view.scrollY + itemHeight * 0.3 > bottom) {
            bottom = view.scrollY + i;
        }

        if (view.scrollY <= top) {
            value = 40F;
            view.scrollY = (int) top;
        } else if (view.scrollY >= bottom) {
            view.scrollY = (int) bottom;
            value = -40F;
        }
        if (value < 0.0F) {
            value = value + 20F;
        } else {
            value = value - 20F;
        }
        view.getHandler().sendEmptyMessage(H.WHAT_INVALIDATE);

    }
}
