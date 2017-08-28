package com.xiongyuan.lottery.secondpage.activity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.secondpage.bean.IntegralResult;
import com.xiongyuan.lottery.secondpage.bean.YhdrjfResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Call;

public class IntegralActivity extends BaseActivity {

    private String userid;
    @Override
    public int getLayoutId() {
        return R.layout.activity_integral;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        userid = getIntent().getStringExtra("uid");
        //积分活动详情
        jfhdxq();


        //当日消费积分抽奖
        xfjfcj();

        //获取用户当日积分
        hqdryhjfxq();
    }

    private void hqdryhjfxq() {
        Call call = LotteryClient.getInstance().getDrjf(userid,"recharge");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(IntegralActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                YhdrjfResult result = new Gson().fromJson(body,YhdrjfResult.class);
                result.getResult().getNum();
            }
        });
    }

    private void xfjfcj() {
        Call call = LotteryClient.getInstance().getDrxfjfcj(userid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(IntegralActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                //Log.e("++++++++++",body);
            }
        });
    }



    private void jfhdxq() {
        Call call = LotteryClient.getInstance().getHqjfhdxq(userid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(IntegralActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {

                IntegralResult result = new Gson().fromJson(body,IntegralResult.class);

                result.getResult().getTime().get_$1().getEnd();
            }
        });
    }
}
