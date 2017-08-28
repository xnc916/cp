package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.PersonalAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class PersonalActivity extends BaseActivity {

    @BindView(R.id.list_personal)
    ListView listView;
    private PersonalAdapter adapter;
    private LinearLayout listViewHeader,listViewFoot;
    private ArrayList list;
    @BindView(R.id.iv_personal_type)
    ImageView ivType;
    @BindView(R.id.tv_personal_onlineTime)
    TextView tvOnlineTime;
    @BindView(R.id.tv_personal_name)
    TextView tvName;
    private String maxRebate;
    private String minRebate;
    private String usableAmount;
    private String frozenAmount;
    private String date;

    private String dmaxfan;//高频返采点
    private String dminfan;//低频返采点
    @Override
    public int getLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        list=new ArrayList<>();
        if (list.size()!=0){
            list.clear();
        }
        Intent intent = getIntent();

        dmaxfan = intent.getStringExtra("dmax");
        dminfan = intent.getStringExtra("dmin");

        maxRebate = intent.getStringExtra("maxRebate");
        minRebate = intent.getStringExtra("minRebate");
        usableAmount = intent.getStringExtra("usableAmount");
        frozenAmount = intent.getStringExtra("frozenAmount");
         date = intent.getStringExtra("date");
        if (intent!=null){
            String[] str = {maxRebate,minRebate};
            list.add(usableAmount);
            list.add(str);
            list.add(usableAmount);
            list.add(frozenAmount);
            list.add(date);
            list.add("");
            list.add("");

            String type = intent.getStringExtra("type");
            if (type.equals("0")){
                ivType.setImageResource(R.mipmap.vip);
            }else if (type.equals("1")){
                ivType.setImageResource(R.mipmap.putong);
            }
            tvName.setText(intent.getStringExtra("userName"));
            String onlineTime = intent.getStringExtra("onlineTime");
            if (onlineTime.equals("0")){
                tvOnlineTime.setText("不在线");
            }else if (onlineTime.equals("1")){
                tvOnlineTime.setText("在线");
            }


        }

        adapter=new PersonalAdapter(this);
        adapter.refresh(list);
        listViewHeader= (LinearLayout) getLayoutInflater().inflate(R.layout.listview_header_layout,listView, false);
        listViewFoot= (LinearLayout) getLayoutInflater().inflate(R.layout.listview_header_layout,listView, false);
        listView.addHeaderView(listViewHeader);
        listView.addFooterView(listViewFoot);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 6:
                        Intent intent=new Intent(PersonalActivity.this,ModifyRebateActivity.class);
                        intent.putExtra("type","0");
                        intent.putExtra("userId",getIntent().getStringExtra("userId"));
                        intent.putExtra("id",getIntent().getStringExtra("id"));

                        intent.putExtra("maxRebate",maxRebate);
                        intent.putExtra("dmax",dmaxfan);

                        startActivityForResult(intent, 0);
                        break;
                    case 7:
                        Intent intent1=new Intent(PersonalActivity.this,ModifyRebateActivity.class);
                        intent1.putExtra("type","1");
                        intent1.putExtra("userId",getIntent().getStringExtra("userId"));
                        intent1.putExtra("id",getIntent().getStringExtra("id"));
                        intent1.putExtra("minRebate",minRebate);
                        intent1.putExtra("dmin",dminfan);
                        startActivityForResult(intent1, 1);
                        break;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    if (list.size()!=0){
                        list.clear();
                    }
                    maxRebate=intent.getStringExtra("rebate");
                        String[] str = {maxRebate,minRebate};
                        list.add(usableAmount);
                        list.add(str);
                        list.add(usableAmount);
                        list.add(frozenAmount);
                        list.add(date);
                        list.add("");
                        list.add("");
                    Log.e("list",list.toString());
                    Log.e("aaaa",maxRebate+"--"+minRebate);
                    adapter.refresh(list);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    if (list.size()!=0){
                        list.clear();
                    }
                    minRebate=intent.getStringExtra("rebate");
                    String[] str = {maxRebate,minRebate};
                    list.add(usableAmount);
                    list.add(str);
                    list.add(usableAmount);
                    list.add(frozenAmount);
                    list.add(date);
                    list.add("");
                    list.add("");
                    Log.e("list",list.toString());
                    Log.e("aaaa",maxRebate+"--"+minRebate);
                    adapter.refresh(list);
                }
                break;

        }
    }

}
