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
import com.xiongyuan.lottery.mypage.fragment.FragmentTodayRecharge;
import com.xiongyuan.lottery.mypage.view.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CashTransactionActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    MyViewPager mViewPager;
    private int tag;
    private int curTab = 0;
    private int p=0;
    private int pos=0;
    private IndicatorDialog dialog;
    private List<String> mLists = new ArrayList<>();
    private List<Integer> mICons = new ArrayList<>();
    private SimpleFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> tabTitles = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_cash_transaction;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        for (int i = 0; i < tabTitles.size(); i++) {
            FragmentTodayRecharge fragment = new FragmentTodayRecharge(curTab);
            fragment.setTabPos(i);
            mFragments.add(fragment);
        }
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), tabTitles, mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置当前显示哪个标签页
        mViewPager.setCurrentItem(tag);
        mViewPager.setNoScroll(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //滑动监听加载数据，一次只加载一个标签页
                pos=position;
                ((FragmentTodayRecharge) mAdapter.getItem(position)).sendMessage(p);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("全部");
        titleName.setText("购彩记录");
        tag = getIntent().getIntExtra("tag", 0);
        curTab=getIntent().getIntExtra("tag",0);
        pos=getIntent().getIntExtra("tag",0);
        tabTitles.add("全部");
        tabTitles.add("撤单");
        tabTitles.add("未开奖");
        tabTitles.add("已封单");
        tabTitles.add("已开奖");
        tabTitles.add("已中奖");
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
        mICons.clear();
        mLists.add("全部");
        mLists.add("今天");
        mLists.add("近1周");
        mLists.add("近1个月");
        mLists.add("近3个月");
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
                        ((FragmentTodayRecharge) mAdapter.getItem(pos)).sendMessage(p);
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
