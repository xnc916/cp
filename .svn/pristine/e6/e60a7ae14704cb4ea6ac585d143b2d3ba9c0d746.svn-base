package com.xiongyuan.lottery.secondpage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.secondpage.bean.FistResult;
import com.xiongyuan.lottery.secondpage.bean.QhbResult;
import com.xiongyuan.lottery.secondpage.bean.QhbxqResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class EventdetailsActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.event_detail_tv)
    TextView eventdetail;
    @BindView(R.id.Qhbxq_title)
    TextView QhbxqTitle;
    @BindView(R.id.Qhbxq)
    TextView Qhbxq;

    private String title;
    private String content;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_eventdetails;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titlename.setText(title);

        if (title.equals("首充领奖")) {
            getFistChange();

        }
        if (title.equals("充值抢红包")) {
            getCzqhb();
            getCzqhbxq();
        }


    }

    @Override
    public void initData() {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        userId = getIntent().getStringExtra("uid");

    }

    @OnClick(R.id.title_left)
    public void click() {
        finish();
    }

    public void getFistChange() {

        Call call = LotteryClient.getInstance().getFirstCharge(userId);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(EventdetailsActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                FistResult rest = new Gson().fromJson(body, FistResult.class);
                content = rest.getErrormsg();
                eventdetail.setText(content);
                Log.e("++1111++", content);
            }
        });
    }

    public void getCzqhb() {
        Call call = LotteryClient.getInstance().getQHb(userId, "600");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(EventdetailsActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                QhbResult result = new Gson().fromJson(body, QhbResult.class);
                content = result.getResult() + "";
                eventdetail.setText(content);
            }
        });
    }

    public void getCzqhbxq(){
        Call call = LotteryClient.getInstance().getQHbxq(userId);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(EventdetailsActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                QhbxqResult result = new Gson().fromJson(body,QhbxqResult.class);
                int min1 = result.getResult().get_$500().getNum().get(0);
                int max1 = result.getResult().get_$500().getNum().get(1);
                int qualif1 = result.getResult().get_$500().getQualif();

                int min2 = result.getResult().get_$2000().getNum().get(0);
                int max2 = result.getResult().get_$2000().getNum().get(1);
                int qualif2 = result.getResult().get_$2000().getQualif();

                int min3 = result.getResult().get_$8000().getNum().get(0);
                int max3 = result.getResult().get_$8000().getNum().get(1);
                int qualif3 = result.getResult().get_$8000().getQualif();

                QhbxqTitle.setVisibility(View.VISIBLE);
                Qhbxq.setVisibility(View.VISIBLE);
                Qhbxq.setText("\t"+min1+"~"+max1+"  需"+qualif1+"积分"+"\n"
                        +"\t"+min2+"~"+max2+"  需"+qualif2+"积分"+"\n"
                        +"\t"+min3+"~"+max3+"  需"+qualif3+"积分");
            }
        });
    }

}
