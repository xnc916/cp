package com.chuck.wheelpackerview.library.listener;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;


public class WheelItemGestureListener extends SimpleOnGestureListener {
    private IFlingListener listener;

    public WheelItemGestureListener(IFlingListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (listener != null) {
            listener.onFling(e1, e2, velocityX, velocityY);
        }

        return true;
    }
}
