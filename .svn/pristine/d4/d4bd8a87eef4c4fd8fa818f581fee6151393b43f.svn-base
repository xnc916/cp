package com.xiongyuan.lottery.homepage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TextItemActivity extends BaseActivity {
    @BindView(R.id.tv_item)
    TextView tvItem;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.title_name)
    TextView titleName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_text_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("系统公告");
        tvItem.setText(getIntent().getStringExtra("title"));
        tvContent.setText(getIntent().getStringExtra("content"));
    }

    @Override
    public void initData() {


    }
    @OnClick({R.id.title_left})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}
