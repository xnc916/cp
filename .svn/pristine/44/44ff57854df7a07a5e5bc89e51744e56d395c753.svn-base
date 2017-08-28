package com.xiongyuan.lottery.homepage.utils;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * Created by gameben on 2017-07-07.
 */

public abstract class CountDownTimerUtil {

    private final long mMillisInFuture;


    private final long mCountdownInterval;

    private long mStopTimeInFuture;


    private boolean mCancelled = false;


    public CountDownTimerUtil(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }


    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }


    public synchronized final CountDownTimerUtil start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }



    public abstract void onTick(long millisUntilFinished);


    public abstract void onFinish();


    private static final int MSG = 1;


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimerUtil.this) {
                if (mCancelled) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                if (millisLeft <= 0) {
                    onFinish();
                } else if (millisLeft < mCountdownInterval) {

                    sendMessageDelayed(obtainMessage(MSG), millisLeft);
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
