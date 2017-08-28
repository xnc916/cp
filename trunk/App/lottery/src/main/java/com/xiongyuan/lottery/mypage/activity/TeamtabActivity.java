package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.TeamLvAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class TeamtabActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.lv_1)
    ListView lv1;
    private ArrayList<String> list;

    private TeamLvAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_teamtab;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("详情");
        adapter = new TeamLvAdapter(this);
        lv1.setAdapter(adapter);
        adapter.refresh(list);
    }

    @Override
    public void initData() {
        list = getIntent().getStringArrayListExtra("list");
    }

    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }
}
