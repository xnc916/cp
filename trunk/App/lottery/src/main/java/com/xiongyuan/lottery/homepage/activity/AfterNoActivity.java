package com.xiongyuan.lottery.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.homepage.adapter.AfterNoadapter;
import com.xiongyuan.lottery.homepage.bean.PlayAllResult;
import com.xiongyuan.lottery.homepage.bean.pkdata.BetDataBean;
import com.xiongyuan.lottery.homepage.bean.pkdata.FutureBean;
import com.xiongyuan.lottery.homepage.bean.pkdata.FutureResult;
import com.xiongyuan.lottery.homepage.bean.pkdata.Shili;
import com.xiongyuan.lottery.homepage.view.SimpleNumberPicker2;
import com.xiongyuan.lottery.mypage.activity.RecordNumberActivity;
import com.xiongyuan.lottery.thirdpage.bean.Shil;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class AfterNoActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleleft;
    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.after_no_lv)
    ListView afternolv;
    @BindView(R.id.number_picker)
    SimpleNumberPicker2 numberPicker2;
    @BindView(R.id.switch1)
    Switch switch1;

    @BindView(R.id.tv_qd)
    TextView tvqd;
    @BindView(R.id.tv_hq)
    TextView tvhq;

    private AfterNoadapter adapter;
    private ArrayList<Shil> list = new ArrayList<>();//序列化
    private ArrayList<Shili> llist = new ArrayList<>();//带期号


    private String num = "1";
    private String id;
    private String game_id;//游戏id
    private String play_id;//玩法id
    private String userId;
    private int temp = 1;
    private String zhongt = "1";
    private DecimalFormat df=new DecimalFormat("0.00");

    private ArrayList<String> parent = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    double money = 0;
                    for (int i = 0; i < parent.size(); i++) {
                        String str = parent.get(i);
                        llist.get(i).setQihao(str);
                        double aDouble = Double.valueOf(llist.get(i).getDanjia());
                        int bs = Integer.valueOf(llist.get(i).getBeishu());
                        money = money + aDouble * llist.get(i).getZhushu() * bs;
                    }

                    tvhq.setText("共 "+df.format(money)+" 元");
                }
                break;
            }
        }
    };
    private List<FutureBean> issue = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_after_no;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titlename.setText("追号");

        numberPicker2.setOnNumberChangeListener2(new SimpleNumberPicker2.OnNumberChangeListener2() {
            @Override
            public void onNumberChanged(int number) {
                num = number+"";
                getissue();
            }
        });

        adapter.setOnBsListener(new AfterNoadapter.OnBsListener() {
            @Override
            public void onBsChanged(int number) {
                handler.sendEmptyMessage(1);
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    zhongt = "1";
                }else {
                    zhongt = "0";
                }
            }
        });

    }
    private void getissue(){

        Call call = LotteryClient.getInstance().getFuture(id,num);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(AfterNoActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                FutureResult result = new Gson().fromJson(body,FutureResult.class);
                List<FutureBean> result1 = result.getResult();
                parent.clear();
                for (int i = 0;i< result1.size();i++){
                    parent.add(result1.get(i).getIssue());
                }
                int size = result1.size();
                if (size - temp == 0){

                }
                if (size - temp == 1){
                    Shili shil = new Shili();
                    shil.setBeishu(list.get(0).getBeishu());
                    shil.setUnit(list.get(0).getUnit());
                    shil.setHaoma(list.get(0).getHaoma());
                    shil.setFandianl(list.get(0).getFandianl());
                    shil.setDanjia(list.get(0).getDanjia());
                    shil.setWanfa(list.get(0).getWanfa());
                    shil.setZhushu(list.get(0).getZhushu());
                    llist.add(shil);
                }
                if (size - temp == -1){
                    llist.remove(llist.size()-1);
                }

                double money = 0;
                for (int i = 0; i < parent.size(); i++) {
                    String str = parent.get(i);
                    llist.get(i).setQihao(str);
                    double aDouble = Double.valueOf(llist.get(i).getDanjia());
                    int bs = Integer.valueOf(llist.get(i).getBeishu());
                    money = money + aDouble * llist.get(i).getZhushu() * bs;
                }

                tvhq.setText("共 "+df.format(money)+" 元");
                adapter.refresh(llist);
                temp = size;
            }
        });
    }
    @Override
    public void initData() {
        list = (ArrayList<Shil>) getIntent().getSerializableExtra("bet");
        id = getIntent().getStringExtra("id");
        userId = getIntent().getStringExtra("userid");
        game_id = getIntent().getStringExtra("game_id");
        play_id = getIntent().getStringExtra("play_id");
        Shili shil = new Shili();
        shil.setBeishu(list.get(0).getBeishu());
        shil.setUnit(list.get(0).getUnit());
        shil.setHaoma(list.get(0).getHaoma());
        shil.setFandianl(list.get(0).getFandianl());
        shil.setDanjia(list.get(0).getDanjia());
        shil.setWanfa(list.get(0).getWanfa());
        shil.setZhushu(list.get(0).getZhushu());
        llist.add(shil);
        double aDouble = Double.valueOf(llist.get(0).getDanjia());
        int bs = Integer.valueOf(llist.get(0).getBeishu());
        double money = aDouble * llist.get(0).getZhushu() * bs;
        tvhq.setText("共 "+df.format(money)+" 元");
        adapter = new AfterNoadapter(AfterNoActivity.this);
        afternolv.setAdapter(adapter);
        getissue();



    }

    @OnClick({R.id.title_left,R.id.tv_qd})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.tv_qd:
                getissuet();
                break;
        }
    }
    private void getissuet(){
        Call call = LotteryClient.getInstance().getFuture(id,num);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(AfterNoActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                issue.clear();
                FutureResult result = new Gson().fromJson(body,FutureResult.class);
                issue = result.getResult();
                parent.clear();
                for (int i = 0; i< issue.size(); i++){
                    parent.add(issue.get(i).getIssue());
                }

                for (int i = 0; i < parent.size(); i++) {
                    issue.get(i).setTimes(llist.get(i).getBeishu());
                }
                shishi();
            }
        });
    }

    private void shishi() {

        List<BetDataBean> lists = new ArrayList<>();
        for (int p = 0; p < parent.size();p++){
            BetDataBean bet = new BetDataBean();
            bet.setCode(llist.get(p).getHaoma());
            bet.setTimes("1");
            bet.setGame_id(game_id);
            bet.setPlay_id(play_id);
            double aDouble = Double.valueOf(llist.get(p).getDanjia());
//            int bs = Integer.valueOf(llist.get(p).getBeishu());
            double money = aDouble * llist.get(p).getZhushu();
            bet.setBetAmount(df.format(money));
            bet.setCurrency(llist.get(p).getUnit());
            bet.setReturnPer(llist.get(p).getFandianl());
            bet.setBetCount(llist.get(p).getZhushu()+"");
            lists.add(bet);
        }


        Call call = LotteryClient.getInstance().getchaseBet(game_id,userId,zhongt,issue.toString(),lists.toString());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(AfterNoActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {

                PlayAllResult result = new Gson().fromJson(body,PlayAllResult.class);

                if (result.getErrormsg().equals("")){
                    ToastUtils.showToast(AfterNoActivity.this,"投注成功");
                    Intent intent = new Intent(AfterNoActivity.this, RecordNumberActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtils.showToast(AfterNoActivity.this,result.getErrormsg());
                }
            }
        });

    }
}
