package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.adapter.HelpCenterfAdapter;
import com.xiongyuan.lottery.mypage.bean.HelpCencerInfo;
import com.xiongyuan.lottery.mypage.bean.HelpCencerResult;
import com.xiongyuan.lottery.mypage.bean.HelpCenterSubInfo;
import com.xiongyuan.lottery.mypage.bean.HelpCenterSubResult;
import com.xiongyuan.lottery.mypage.bean.HelpCenterSubr;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class HelpCenterActivity extends BaseActivity{

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.help_center_lv)
    ListView listview;

    private String uid;
    private List<HelpCencerInfo> list = new ArrayList<>();
    private List<HelpCenterSubInfo> sublist = new ArrayList<>();
    private List<HelpCenterSubInfo> zlist = new ArrayList<>();//总集合

    private HelpCenterfAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("帮助中心");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itent = new Intent(HelpCenterActivity.this,HelpeCenterNewActivity.class);

                if (zlist.get(position).getId().equals("79")){
                    Type type = new TypeToken<List<HelpCenterSubInfo>>() {
                    }.getType();
                    String json = new Gson().toJson(sublist,type);
                    itent.putExtra("list",json);
                }
                    itent.putExtra("title",zlist.get(position).getTitle());
                    itent.putExtra("m_id",zlist.get(position).getMenus_id());
                    itent.putExtra("id",zlist.get(position).getId());
                    itent.putExtra("pid",zlist.get(position).getPid());
                    itent.putExtra("userId",uid);

                startActivity(itent);
            }
        });


    }

    @Override
    public void initData() {
        uid = getIntent().getStringExtra("userId");

        Call call = LotteryClient.getInstance().getHelpMenu(uid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(HelpCenterActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                zlist.clear();
                sublist.clear();

                HelpCencerResult result = new Gson().fromJson(body,HelpCencerResult.class);

                HelpCenterSubr r = new Gson().fromJson(body,HelpCenterSubr.class);

                List<HelpCenterSubResult> result1 = r.getResult();
                for (int i = 0;i < result1.size();i++){
                    sublist = result1.get(i).getSub();
                }

                list = result.getResult();

                for (int i = 0;i < list.size();i++){
                    HelpCenterSubInfo h1 = new HelpCenterSubInfo();
                    h1.setId(list.get(i).getId());
                    h1.setPid(list.get(i).getPid());
                    h1.setTitle(list.get(i).getTitle());
                    h1.setMenus_id(list.get(i).getMenus_id());
                    zlist.add(h1);
                }
                adapter = new HelpCenterfAdapter(HelpCenterActivity.this);
                listview.setAdapter(adapter);

                adapter.setDatalist(zlist);
                adapter.notifyDataSetChanged();
            }
        });

    }
    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }



}

