package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.SimpleFragmentPagerAdapter;
import com.xiongyuan.lottery.mypage.fragment.FragmentGrzl;
import com.xiongyuan.lottery.mypage.fragment.FragmentSubordinateAccountg;
import com.xiongyuan.lottery.mypage.fragment.FragmentVip;
import com.xiongyuan.lottery.mypage.view.team.TimePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonalInformationActivity extends BaseActivity implements TimePopupWindow.OnOptionsSelectListener,TimePopupWindow.Shishi{
    @BindView(R.id.layout_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.title_right)
    TextView titleright;
    @BindView(R.id.view)
    View v;


    ArrayList<String> ProvinceList;
    ArrayList<ArrayList<String>> CityList;
    ArrayList<ArrayList<ArrayList<String>>> CountyList;
    private TimePopupWindow popupWindow;

    private String start = "";
    private String end = "";

    private int pos=0;

    private int tag;
    private SimpleFragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private List<String> tabTitles = new ArrayList<>();
    private FragmentSubordinateAccountg fragmentSubordinateAccountg;
    private FragmentVip fragmentVip;

    @Override
    public int getLayoutId() {
        return R.layout.activity_personal_information;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleright.setVisibility(View.VISIBLE);
        titleright.setText("搜索");

        fragmentSubordinateAccountg = new FragmentSubordinateAccountg();
        fragmentVip = new FragmentVip();

        mFragments.add(new FragmentGrzl());
        mFragments.add(fragmentSubordinateAccountg);
        mFragments.add(fragmentVip);
        mAdapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(),tabTitles, mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSmoothScrollingEnabled(true);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置当前显示哪个标签页
        mViewPager.setCurrentItem(tag);

    }

    @Override
    public void initData() {
        tag = getIntent().getIntExtra("tag", 0);
        tabTitles.add("个人资料");
        tabTitles.add("账变记录");
        tabTitles.add("积分记录");
        showNum();
    }
    @OnClick({R.id.title_left,R.id.title_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                popupWindow = new TimePopupWindow(PersonalInformationActivity.this);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);//textView
                popupWindow.setPicker(ProvinceList, CityList, CountyList, true);
                popupWindow.setOnoptionsSelectListener(this);
                popupWindow.setshishi(this);
                popupWindow.setCyclic(false);
                break;
        }
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {

    }

    @Override
    public void onshishi(String start, String end) {
        fragmentSubordinateAccountg.sendMessage(0,start,end);
        fragmentVip.sendMessage(0,start,end);
    }
    private void showNum() {
        ArrayList<String> one = new ArrayList<>();
        ProvinceList = new ArrayList<>();
        for (int i = 2000; i < 2050; i++) {
            ProvinceList.add(i+"年");
            one.add(i+"");
        }
        CityList = new ArrayList<>();
        for (int j = 0; j < ProvinceList.size(); j++) {
            ArrayList<String> l21 = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                if (i < 9){
                    l21.add(0+""+(i+1)+"月");
                }else {
                    l21.add(i+1+"月");
                }
            }

            CityList.add(l21);

        }
        CountyList = new ArrayList<>();
        for (int j = 0; j < one.size(); j++) {

            int year = Integer.valueOf(one.get(j));
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            if((year%4==0&&year%100!=0)||year%400==0){
                for (int i = 0; i < 12; i++) {
                    ArrayList<String> l21 = new ArrayList<>();
                    if (i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11){
                        for (int p = 0;p < 31;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else if (i == 1){
                        for (int p = 0;p < 29;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else {
                        for (int p = 0;p < 30;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }

                    temp.add(l21);
                }
            }else {
                for (int i = 0; i < 12; i++) {
                    ArrayList<String> l21 = new ArrayList<>();
                    if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12){
                        for (int p = 0;p < 31;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else if (i == 2){
                        for (int p = 0;p < 28;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else {
                        for (int p = 0;p < 30;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }

                    temp.add(l21);
                }
            }

            CountyList.add(temp);

        }

    }
}
