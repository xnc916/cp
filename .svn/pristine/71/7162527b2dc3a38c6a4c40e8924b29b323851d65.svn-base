package com.xiongyuan.lottery;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.homepage.fragment.HomeFragment;
import com.xiongyuan.lottery.mypage.bean.Balance;
import com.xiongyuan.lottery.mypage.dialog.RegistermpDialog;
import com.xiongyuan.lottery.mypage.fragment.MyCenterFragment;
import com.xiongyuan.lottery.secondpage.fragment.SecondFragment;
import com.xiongyuan.lottery.thirdpage.fragment.ThirdFragment;
import com.xiongyuan.lottery.utils.PrefUtils;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private HomeFragment homeFragment;  //首页购彩
    private SecondFragment secondFragment;//天天有礼
    private ThirdFragment thirdFragment;//精彩内容
    private MyCenterFragment myCenterFragment;//账户管理
    //公用fragment
    private Fragment currentFragment=new Fragment();
    @BindView(R.id.llhome)
    LinearLayout llhome;
    @BindView(R.id.llsecond)
    LinearLayout llsecond;
    @BindView(R.id.llthird)
    LinearLayout llthird;
    @BindView(R.id.llmy)
    LinearLayout llmy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        llhome.setOnClickListener(this);
        llsecond.setOnClickListener(this);
        llthird.setOnClickListener(this);
        llmy.setOnClickListener(this);


        llhome.setTag(0);
        llsecond.setTag(1);
        llthird.setTag(2);
        llmy.setTag(3);


        llhome.setSelected(true);
        setCurrentFragment(0);

    }

    @Override
    public void initData() {
        homeFragment=new HomeFragment();
        secondFragment=new SecondFragment();
        thirdFragment=new ThirdFragment();
        myCenterFragment=new MyCenterFragment();
        showFragment(homeFragment);
    }
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurrentFragment(position);
        updateBottomIcon(position);
    }
    public  void updateBottomIcon(int position) {
        llhome.setSelected(position == 0 ? true : false);
        llsecond.setSelected(position == 1 ? true : false);
        llthird.setSelected(position == 2 ? true : false);
        llmy.setSelected(position == 3 ? true : false);

    }

    /**
     * 展示Fragment
     */
    private void showFragment(Fragment fragment) {
        if (currentFragment!=fragment) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(currentFragment);
            currentFragment = fragment;
            if (!fragment.isAdded()) {
                transaction.add(R.id.content_home, fragment).show(fragment).commit();
            } else {
                transaction.show(fragment).commit();
            }
        }
    }

    private void setCurrentFragment(int position) {
        switch (position){
            case 0:
                showFragment(homeFragment);
                break;
            case 1:
                showFragment(secondFragment);
                break;
            case 2:
                showFragment(thirdFragment);
                break;
            case 3:
                showFragment(myCenterFragment);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ClipboardManager clipboardmanager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        //判断剪贴板里是否有内容
        if(!clipboardmanager.hasPrimaryClip()) {
            return;
        }
        ClipData clipData =clipboardmanager.getPrimaryClip();
        String name = clipData.getItemAt(0).getText().toString();
        if (name.length() >= 10) {
            String substring = name.substring(0, 5);
            String clipDataName = PrefUtils.getPrefString(MainActivity.this, "clipDataName", "");
            if (substring.equals("#猜猜乐#")) {
                if (clipDataName.equals("")) {
                    PrefUtils.setPrefString(MainActivity.this, "clipDataName", name);
                } else {
                    if (!clipDataName.equals(name)) {
                        PrefUtils.setPrefString(MainActivity.this, "clipDataName", name);
                        ToastUtils.showToast(MainActivity.this, name);
                    }
                }
                String code = name.substring(name.length() - 10, name.length());
                Log.e("9999+++", code);
                RegistermpDialog dialog = new RegistermpDialog(this, new RegistermpDialog.OnCustomDialogListener() {
                    @Override
                    public void back(String username, String password, String repsw) {
                        startRegister(code, username, password, repsw);

                    }
                });
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();

            }

        }
    }

    private void startRegister(String code,String username,String password,String repsw) {
        Call call = LotteryClient.getInstance().getRegister(code,username,password,repsw);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(MainActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Balance balance = new Gson().fromJson(body,Balance.class);
                String errormsg = balance.getErrormsg();
                if (errormsg.equals("")||errormsg.equals(null)){
                    errormsg = "注册成功";

                }
                ToastUtils.showToast(MainActivity.this,errormsg);

            }
        });
    }


}
