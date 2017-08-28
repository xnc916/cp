package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.adapter.WithdrawalAdapter;
import com.xiongyuan.lottery.mypage.bean.WithdrawalInfo;
import com.xiongyuan.lottery.mypage.bean.WithdrawalResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by gameben on 2017-06-07.
 */

public class WithdrawalActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.with_dra_lv)
    ListView withDraLv;
    private WithdrawalAdapter withdrawalAdapter;
    private  boolean isLogin=false;
    private String userId;
    private List<WithdrawalInfo> list = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_withdrawal;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("选择银行卡");
        //获取数据


        if (isLogin){
            getbankCount(userId);
        }

    }

    @Override
    public void initData() {
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        userId = getIntent().getStringExtra("userId");

    }

    @OnClick(R.id.title_left)
    public void onclick(View v){
        finish();
    }
    public void getbankCount(String uid) {
        Call call = LotteryClient.getInstance().getBankcard(uid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(WithdrawalActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {

                        WithdrawalResult result = new Gson().fromJson(body,WithdrawalResult.class);
                        list.addAll(result.getResult());
                        withdrawalAdapter = new WithdrawalAdapter(WithdrawalActivity.this);
                        withDraLv.setAdapter(withdrawalAdapter);

                        withdrawalAdapter.setDatalist(list);
                        withdrawalAdapter.notifyDataSetChanged();

                withDraLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent itent = new Intent(WithdrawalActivity.this,DrawMoneyActivity.class);
                        itent.putExtra("user_name",list.get(position).getAccountName());
                        itent.putExtra("bank_name",list.get(position).getBankName());
                        itent.putExtra("bank_id",list.get(position).getId());
                        itent.putExtra("user_id",userId);
                        itent.putExtra("car_hao",list.get(position).getCardNum());
                        startActivity(itent);
                    }
                });
            }
        });
    }

}
