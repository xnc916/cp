package com.chuck.wheelpackerview;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chuck.wheelpackerview.interfaces.OnDismissListener;

import java.lang.ref.WeakReference;


public abstract class BasePickerView {
    private WeakReference<Context> mContext;
    private WeakReference<LayoutInflater> mInflater;
    private ViewGroup contentContainer;
    /**
     * 所依附activity的DecorView
     */
    private ViewGroup decorView;
    /**
     * 选择器的rootView
     */
    private ViewGroup rootView;

    private OnDismissListener onDismissListener;
    private boolean dismissing;


    private boolean isShowing;

    public BasePickerView(Context mContext) {
        this.mContext = new WeakReference<Context>(mContext);
        mInflater = new WeakReference<LayoutInflater>(LayoutInflater.from(mContext));
        initAttachViews();

    }



    private void initAttachViews() {
        decorView = getDecorView();
        rootView = (ViewGroup) getInflateView(R.layout.view_base_content, decorView, false);

        contentContainer = (ViewGroup) rootView.findViewById(R.id.fl_container);


        contentContainer.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT-1, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER
        ));
    }

    private ViewGroup getDecorView() {
        return (ViewGroup) ((Activity) mContext.get()).getWindow().getDecorView().findViewById(android.R.id.content);
    }

    public View getInflateView(int layoutId, ViewGroup root, boolean attachToRoot) {
        if (mInflater == null) {
            mInflater = new WeakReference<LayoutInflater>(LayoutInflater.from(mContext.get()));
        }
        return mInflater.get().inflate(layoutId, root, attachToRoot);
    }

    public View getInflateView(int layoutId, ViewGroup viewGroup) {
        if (mInflater == null) {
            mInflater = new WeakReference<LayoutInflater>(LayoutInflater.from(mContext.get()));
        }
        return mInflater.get().inflate(layoutId, viewGroup);
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        isShowing = true;
        onAttached(rootView);
    }

    /**
     * 将选择框视图添加到decorView中
     *
     * @param rootView
     */
    private void onAttached(ViewGroup rootView) {
        decorView.addView(rootView);

    }

    public boolean isShowing() {
        return rootView.getParent() != null || isShowing;
    }

    private void dismiss() {
        decorView.removeView(rootView);
        isShowing = false;
        dismissing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
    }

    public void startDismiss() {
        if (dismissing) {
            return;
        }
        dismissing = true;
        dismiss();

    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public void setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.fl_all_container);
        if (isCancelable) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        startDismiss();
                    }
                    return false;
                }
            });
        } else {
            view.setOnTouchListener(null);
        }
    }

    public void addView(View view) {
        if (contentContainer != null) {
            contentContainer.addView(view);
        }
    }
}
