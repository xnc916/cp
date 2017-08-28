package com.chuck.wheelpackerview.library;

import java.util.TimerTask;

import com.chuck.wheelpackerview.library.WheelItemView.H;

/**
 * 将文字移动到中间位置
 * Created by Chuckchen on 16/12/22.
 */

public class MoveCenterTimerTask extends TimerTask {
    private int realTotalOffset;
    private int realOffset;
    private int offset;
    private WheelItemView view;

    public MoveCenterTimerTask(WheelItemView view, int offset) {
        this.view = view;
        this.offset = offset;
        realTotalOffset = Integer.MAX_VALUE;
        realOffset = 0;
    }

    @Override
    public void run() {
        if (realTotalOffset == Integer.MAX_VALUE) {
            realTotalOffset = offset;
        }
        //把要滚动的范围细分
        realOffset = (int) ((float) realTotalOffset * 0.05F);

        if (realOffset == 0) {
            if (realTotalOffset < 0) {
                realOffset = -1;
            } else {
                realOffset = 1;
            }
        }

        if (Math.abs(realTotalOffset) <= 1) {
            view.cancelFuture();
            view.getHandler().sendEmptyMessage(H.WHAT_ITEM_SELECTED);
        } else {
            view.scrollY = view.scrollY + realOffset;

            //回滚，防止出现选到－1 item的情况
            float itemHeight = view.getMeasureHelper().itemHeight;
            float top = (float) (-view.initPosition) * itemHeight;
            float bottom = (float) (view.getItemCount() - 1 - view.initPosition) * itemHeight;
            if (view.scrollY <= top || view.scrollY >= bottom) {
                view.scrollY = view.scrollY - realOffset;
                view.cancelFuture();
                view.getHandler().sendEmptyMessage(H.WHAT_ITEM_SELECTED);
                return;
            }
            view.getHandler().sendEmptyMessage(H.WHAT_INVALIDATE);
            realTotalOffset = realTotalOffset - realOffset;
        }
    }
}
