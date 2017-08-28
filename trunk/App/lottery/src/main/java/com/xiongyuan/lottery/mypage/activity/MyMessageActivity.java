package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.adapter.MyMessageAdapter;
import com.xiongyuan.lottery.mypage.bean.MyMessageInfo;
import com.xiongyuan.lottery.mypage.bean.MyMessageResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

public class MyMessageActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.my_msg_lv)
    ListView msglv;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv)
    TextView tv;
    private int p=1;
    private MyMessageAdapter adapter;
    private String userid;

    private List<MyMessageInfo> list = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("我的消息");

        //展示消息
        showMsg(userid,1);

        //listview的监听

        msglv.setOnItemClickListener(lister);
//初始化RefreshLayout
        //使用本对象作为key，用来记录上一次刷新的事件，如果两次下拉刷新间隔太近，不会触发刷新方法
        refreshLayout.setLastUpdateTimeRelateObject(this);
        //设置刷新时显示的背景色
        refreshLayout.setBackgroundResource(R.color.color_333333);
        //关闭header所耗时长
        refreshLayout.setDurationToCloseHeader(1500);
        //实现刷新，加载回调
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //加载更多时触发
                p++;
                showMsg(userid,p);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                p=1;
                showMsg(userid,p);
            }
        });

    }
    private AdapterView.OnItemClickListener lister = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MyMessageActivity.this,ReadMessageActivity.class);
            intent.putExtra("title",list.get(position).getTitle());
            intent.putExtra("content",list.get(position).getContent());
            startActivity(intent);
            String msgid = list.get(position).getId();
            String status = list.get(position).getStatus();
            int i = Integer.valueOf(status);
            if (i == 0){
                //baocuizhuangtai
                save(userid,msgid);
            }


            showMsg(userid,1);
        }
    };

    private void save(String uid,String msgid) {
        Call call = LotteryClient.getInstance().getStatus(uid,msgid);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(MyMessageActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {

            }
        });
    }

    private void showMsg(String uid,int pageInt) {
        if (pageInt==1){
            if (list!=null){
                list.clear();
            }
        }
        Call call = LotteryClient.getInstance().getSystemmsg(uid,pageInt+"");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(MyMessageActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                MyMessageResult result = new Gson().fromJson(body,MyMessageResult.class);
                list.addAll(result.getResult());

                adapter = new MyMessageAdapter(MyMessageActivity.this);
                msglv.setAdapter(adapter);
                Log.e("--=我的消息=--",body);
                Log.e("list",list+"");
                if (list.toString().equals("[]")){
                    if (pageInt==1) {
                        tv.setVisibility(View.VISIBLE);
                        refreshLayout.setVisibility(View.GONE);
                        p = 1;
                    }
                }else{
                    tv.setVisibility(View.INVISIBLE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    adapter.setDatilist(list);
                    adapter.notifyDataSetChanged();
                }
                refreshLayout.refreshComplete();
            }
        });
    }


    @Override
    public void initData() {
        userid = getIntent().getStringExtra("userId");
    }
    @OnClick({R.id.title_left,R.id.tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.tv:
                showMsg(userid,1);
                break;
        }
    }
}