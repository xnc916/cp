package com.xiongyuan.lottery.mypage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017-05-22.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabTitles;
    private List<Fragment> fragments;

    public SimpleFragmentPagerAdapter(FragmentManager fm,List<String> tabTitles, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.tabTitles= tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    //防止fragment自动销毁
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}

