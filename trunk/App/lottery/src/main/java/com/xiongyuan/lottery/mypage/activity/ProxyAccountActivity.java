package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.SimpleFragmentPagerAdapter;
import com.xiongyuan.lottery.mypage.fragment.FragmentAddManage;
import com.xiongyuan.lottery.mypage.fragment.FragmentProxyAccount;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class ProxyAccountActivity extends BaseActivity {
    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private SimpleFragmentPagerAdapter mAdapter;
    private List<String> tabTitles = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_proxy_account;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mFragments.add(new FragmentProxyAccount());
        mFragments.add(new FragmentAddManage());
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),tabTitles, mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置当前显示哪个标签页
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动监听加载数据，一次只加载一个标签页
                if (position==1){
                    ((FragmentAddManage) mAdapter.getItem(position)).sendMessage();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        tabTitles.add("代理开户");
        tabTitles.add("链接管理");

    }
    @OnClick({R.id.title_left})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
}
