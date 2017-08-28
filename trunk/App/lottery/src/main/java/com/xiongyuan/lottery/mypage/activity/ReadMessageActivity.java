package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ReadMessageActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.read_massage_tv)
    TextView readMassageTv;
    @BindView(R.id.read_masage_contnet)
    TextView readMasageContnet;

    private String title;
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        readMassageTv.setText(title);
        readMasageContnet.setText(content);
    }

    @Override
    public void initData() {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
    }

    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }

}
