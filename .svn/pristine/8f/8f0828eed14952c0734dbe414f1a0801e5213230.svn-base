package com.chuck.wheelpackerview.library;

/**
 * Created by Chuckchen on 16/12/22.
 */

public final class OnItemSelectedRunnable implements Runnable {
    WheelItemView view;

    public OnItemSelectedRunnable(WheelItemView view) {
        this.view = view;
    }

    @Override
    public void run() {
        view.onItemSelectedListener.onItemSelected(view.getCurrentItem());
    }
}
