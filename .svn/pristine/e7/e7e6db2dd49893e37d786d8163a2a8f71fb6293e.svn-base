package com.xiongyuan.lottery.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    protected  BaseActivity activity;
    /**
     * 获取加载View的ID
     */
    public abstract int getLayoutId();

    /**
     * 实例化控件
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, view);
        activity = (BaseActivity)getActivity();
        //注册事件
        EventBus.getDefault().register(this);
        initData();
        initView();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //取消事件注册
        EventBus.getDefault().unregister(this);
    }

}
