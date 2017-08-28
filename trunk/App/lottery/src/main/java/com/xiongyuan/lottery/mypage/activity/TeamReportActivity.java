package com.xiongyuan.lottery.mypage.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.jiang.android.indicatordialog.IndicatorBuilder;
import com.jiang.android.indicatordialog.IndicatorDialog;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.BaseAdapter;
import com.xiongyuan.lottery.mypage.adapter.BaseViewHolder;
import com.xiongyuan.lottery.mypage.adapter.SimpleFragmentPagerAdapter;
import com.xiongyuan.lottery.mypage.fragment.FragmentMyTeam;
import com.xiongyuan.lottery.mypage.fragment.FragmentSubordinateAccount;
import com.xiongyuan.lottery.mypage.fragment.FragmentTeamStatistics;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamReportActivity extends BaseActivity {
    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title_right)
    TextView titleRight;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private SimpleFragmentPagerAdapter mAdapter;
    private List<String> tabTitles = new ArrayList<>();
    private int p=0;
    private int pos=0;
    private IndicatorDialog dialog;
    private List<String> mLists = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_team_report;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        FragmentSubordinateAccount fragmentSubordinateAccount = new FragmentSubordinateAccount();
        FragmentMyTeam fragmentMyTeam = new FragmentMyTeam();
        mFragments.add(new FragmentTeamStatistics());
        mFragments.add(fragmentSubordinateAccount);
        mFragments.add(fragmentMyTeam);

        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),tabTitles, mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置当前显示哪个标签页
        int tag = getIntent().getIntExtra("tag", 0);
        if (tag==1){
            mTabLayout.setVisibility(View.GONE);
            mViewPager.setCurrentItem(1);
            fragmentSubordinateAccount.setT(tag);
        }else{
            mViewPager.setCurrentItem(0);
        }

    }

    @Override
    public void initData() {
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("全部");
        tabTitles.add("团队统计");
        tabTitles.add("下级流水");
        tabTitles.add("会员/代理");
    }
    @OnClick({R.id.title_left,R.id.title_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                showTopDialog(titleRight, 0.9f, IndicatorBuilder.GRAVITY_RIGHT);
                break;
        }
    }

    private void showTopDialog(View v, float v1, int gravityCenter) {
        mLists.clear();
        mLists.add("全部");
        mLists.add("今天");
        mLists.add("近一周");
        mLists.add("前一天");
        mLists.add("后一天");
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        dialog = new IndicatorBuilder(this)
                .width(300)
                .height((int) (height * 0.5))
                .ArrowDirection(IndicatorBuilder.TOP)
                .bgColor(Color.parseColor("#49484b"))
                .gravity(gravityCenter)
                .radius(18)
                .ArrowRectage(v1)
                .layoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .adapter(new BaseAdapter() {
                    @Override
                    public void onBindView(BaseViewHolder holder, int position) {
                        TextView tv = holder.getView(R.id.item_add);
                        tv.setText(mLists.get(position));
                        //tv.setCompoundDrawablesWithIntrinsicBounds(mICons.get(position), 0, 0, 0);

                        if (position == mLists.size() - 1) {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
                        } else {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);

                        }
                    }

                    @Override
                    public int getLayoutID(int position) {
                        return R.layout.item;
                    }

                    @Override
                    public boolean clickable() {
                        return true;
                    }

                    @Override
                    public void onItemClick(View v, int position) {
                        p= position;
                        ((FragmentTeamStatistics) mAdapter.getItem(pos)).sendMessage(p);
                        ((FragmentSubordinateAccount) mAdapter.getItem(1)).sendMessage(p);
                        ((FragmentMyTeam) mAdapter.getItem(2)).sendMessage(p);

                        dialog.dismiss();
                        titleRight.setText(mLists.get(position));
                    }

                    @Override
                    public int getItemCount() {
                        return mLists.size();
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(true);
        dialog.show(v);

    }
}
