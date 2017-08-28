package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.bean.ArticleResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class HelpCenterNewtActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleleft;
    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.hc_newt_tv)
    TextView hcnewttv;

    private String title;
    private String mus_id;
    private String uid;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center_newt;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titlename.setText(title);


        Call call = LotteryClient.getInstance().getArticle(uid,mus_id);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(HelpCenterNewtActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                ArticleResult result = new Gson().fromJson(body,ArticleResult.class);
                String s = Html.fromHtml(result.getResult().getData().get(0).getContent()).toString();
                hcnewttv.setText(s);
            }
        });
    }

    @Override
    public void initData() {

        title = getIntent().getStringExtra("title");
        mus_id = getIntent().getStringExtra("musid");
        uid = getIntent().getStringExtra("userId");


    }
    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }

}
