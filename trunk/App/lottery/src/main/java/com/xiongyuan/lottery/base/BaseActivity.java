package com.xiongyuan.lottery.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {
    public Fragment upFragment;
    public FragmentManager fragmentManager;

    /**
     * 获取加载View的ID
     */
    public abstract int getLayoutId();

    /**
     * 实例化控件
     */
    public abstract void initView(Bundle savedInstanceState);


    /**
     * 初始化数据
     */
    public abstract void initData();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        setContentView(getLayoutId());
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ButterKnife.bind(this);
        initData();
        initView(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
