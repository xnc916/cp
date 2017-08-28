package com.xiongyuan.lottery.secondpage.activity;

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
import com.xiongyuan.lottery.secondpage.adapter.MoreActivityAdapter;
import com.xiongyuan.lottery.secondpage.bean.MoreInfo;
import com.xiongyuan.lottery.secondpage.bean.MoreResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MoreActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.more_list_view)
    ListView moreListView;
    private String userId;

    private List<MoreInfo> list = new ArrayList<>();
    private MoreActivityAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_more;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("更多活动");


    }

    @Override
    public void initData() {
        userId = getIntent().getStringExtra("uid");
        initHDdata(userId);

        moreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MoreActivity.this,EventdetailsActivity.class);
                intent.putExtra("title",list.get(position).getTitle());
                intent.putExtra("content",list.get(position).getContent());
                intent.putExtra("uid",userId);
                startActivity(intent);
            }
        });

    }

    private void initHDdata(String uid) {
        Call call = LotteryClient.getInstance().getArticle(uid,"81");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(MoreActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                list.clear();
                MoreResult result = new Gson().fromJson(body,MoreResult.class);

                list = result.getResult().getData();

                adapter = new MoreActivityAdapter(MoreActivity.this);
                moreListView.setAdapter(adapter);

                adapter.setDatalist(list);
                adapter.notifyDataSetChanged();
            }
        });
    }
    @OnClick(R.id.title_left)
    public void click(){
        finish();
    }
}
