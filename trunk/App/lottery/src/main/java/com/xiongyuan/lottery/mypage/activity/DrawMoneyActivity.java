package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.adapter.DrawMoneyAdapter;
import com.xiongyuan.lottery.mypage.bean.Balance;
import com.xiongyuan.lottery.mypage.bean.DrawMoneyInfo;
import com.xiongyuan.lottery.mypage.bean.DrawMoneyResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class DrawMoneyActivity extends BaseActivity {


    @BindView(R.id.draw_money_accountName)
    TextView drawMoneyAccountName;
    @BindView(R.id.draw_money_bankName)
    TextView drawMoneyBankName;
    @BindView(R.id.draw_money_cardNum)
    TextView drawMoneyCardNum;
    @BindView(R.id.draw_money_money)
    EditText drawMoneyMoney;
    @BindView(R.id.draw_money_play)
    EditText drawMoneyPlay;
    @BindView(R.id.draw_money_true_m)
    Button drawMoneyTrueM;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.Draw_money_lv)
    ListView DrawMoneyLv;


    private String accountName;
    private String bankName;
    private String cardNum;
    private String uid;
    private String kaohao;
    private List<DrawMoneyInfo> list = new ArrayList<>();

    private DrawMoneyAdapter adapter;
    private String moneyMoneyText;
    private String moneyPlayText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_draw_money;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        drawMoneyAccountName.setText(accountName);
        drawMoneyBankName.setText(bankName);
        drawMoneyCardNum.setText(kaohao);


        //展示提现说明
        showWithdraw(uid);


    }

    private void showWithdraw(String useid) {

        Call call = LotteryClient.getInstance().getReflectThat(useid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(DrawMoneyActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                DrawMoneyResult result = new Gson().fromJson(body, DrawMoneyResult.class);
                String dailyCashTimes = result.result.getDailyCashTimes();
                String dailyCashFree = result.result.getDailyCashFree();
                String cashCommission = result.result.getCashCommission();
                String maxCash = result.result.getMaxCash();
                String minCash = result.result.getMinCash();
                String cashStartTime = result.result.getCashStartTime();
                String cashEndTime = result.result.getCashEndTime();
                String cashConsShare = result.result.getCashConsShare();


                DrawMoneyInfo info = new DrawMoneyInfo(dailyCashTimes,dailyCashFree,cashCommission,maxCash,minCash,cashStartTime,cashEndTime,cashConsShare);

                list.add(info);

                adapter = new DrawMoneyAdapter(DrawMoneyActivity.this);
                DrawMoneyLv.setAdapter(adapter);

                adapter.setDatalist(list);
                adapter.notifyDataSetChanged();

            }
        });


    }

    @Override
    public void initData() {

        accountName = getIntent().getStringExtra("user_name");
        bankName = getIntent().getStringExtra("bank_name");
        cardNum = getIntent().getStringExtra("bank_id");
        uid = getIntent().getStringExtra("user_id");
        kaohao = getIntent().getStringExtra("car_hao");
        drawMoneyMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @OnClick({R.id.title_left, R.id.draw_money_true_m})
    public void click(View view) {
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.draw_money_true_m:
                moneyMoneyText = drawMoneyMoney.getText().toString();
                moneyPlayText = drawMoneyPlay.getText().toString();

                tijiao(uid,moneyMoneyText,cardNum,moneyPlayText);
                finish();

                break;
        }

    }

    private void tijiao(String uids,String moneynum,String bank_id,String bank_psw) {
        Call call = LotteryClient.getInstance().getBalance(uids,moneynum,bank_id,bank_psw);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(DrawMoneyActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                Balance balance = new Gson().fromJson(body,Balance.class);
                String errormsg = balance.getErrormsg();
                if (errormsg.equals("")||errormsg.equals(null)){
                    errormsg = "退款申请成功，请注意查看账户";
                }

                ToastUtils.showToast(DrawMoneyActivity.this,errormsg);
            }
        });
    }


}
