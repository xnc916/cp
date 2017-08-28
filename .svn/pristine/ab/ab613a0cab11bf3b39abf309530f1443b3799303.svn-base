package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.xiongyuan.lottery.mypage.bean.ArticleResult;
import com.xiongyuan.lottery.mypage.bean.HelpCenterSubInfo;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class HelpeCenterNewActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleleft;
    @BindView(R.id.hc_new_tv)
    TextView hcnewtv;
    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.hc_new_lv)
    ListView listView;

    private String tname;
    private String id;
    private String uid;
    private String mus_id;
    private List<HelpCenterSubInfo> list = new ArrayList<>();
    private HelpCenterfAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_helpe_center_new;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titlename.setText(tname);

        if (id.equals("79")){
            listView.setVisibility(View.VISIBLE);
            adapter = new HelpCenterfAdapter(this);
            listView.setAdapter(adapter);

            adapter.setDatalist(list);
            adapter.notifyDataSetChanged();
        }else {
            hcnewtv.setVisibility(View.VISIBLE);

            Call call = LotteryClient.getInstance().getArticle(uid,mus_id);
            call.enqueue(new UICallBack() {
                @Override
                public void onFailureUI(Call call, IOException e) {
                    ToastUtils.showToast(HelpeCenterNewActivity.this,e.getMessage());
                }

                @Override
                public void onResponseUI(Call call, String body) {
                    ArticleResult result = new Gson().fromJson(body,ArticleResult.class);
                    if (result.getResult().getData().size() > 0) {
                        String s = Html.fromHtml(result.getResult().getData().get(0).getContent()).toString();
                        hcnewtv.setText(s);
                    }
                }
            });





        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HelpeCenterNewActivity.this,HelpCenterNewtActivity.class);
                intent.putExtra("musid",list.get(position).getMenus_id());
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("userId",uid);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {
        tname = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        uid = getIntent().getStringExtra("userId");
        mus_id = getIntent().getStringExtra("m_id");



        String json = getIntent().getStringExtra("list");
        Type type = new TypeToken<List<HelpCenterSubInfo>>(){}.getType();
        list = new Gson().fromJson(json,type);

    }
    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }

}
