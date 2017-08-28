package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.SimpleFragmentPagerAdapter;
import com.xiongyuan.lottery.mypage.fragment.FragmentHight;
import com.xiongyuan.lottery.mypage.fragment.FragmentNet;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RechargeActivity extends BaseActivity {
    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private int tag;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();



    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFragments.add(new FragmentHight());
        mFragments.add(new FragmentNet());
        SimpleFragmentPagerAdapter mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),tabTitles, mFragments);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置当前显示哪个标签页
        mViewPager.setCurrentItem(tag);

    }

    @Override
    public void initData() {
        tag = getIntent().getIntExtra("tag", 0);
        tabTitles.add("高通扫码");
        tabTitles.add("网银支付");

    }





    @OnClick({R.id.title_left})
    public void click(View v){
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}
