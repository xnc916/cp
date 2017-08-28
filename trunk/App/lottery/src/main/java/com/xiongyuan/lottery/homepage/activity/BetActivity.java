package com.xiongyuan.lottery.homepage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.homepage.adapter.BetAdapter;
import com.xiongyuan.lottery.homepage.bean.PlayAllResult;
import com.xiongyuan.lottery.homepage.bean.pkdata.BetDataBean;
import com.xiongyuan.lottery.mypage.activity.CashTransactionActivity;
import com.xiongyuan.lottery.thirdpage.bean.Shil;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class BetActivity extends BaseActivity {

    @BindView(R.id.title_right)
    TextView titleright;
    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.bet_lv)
    SwipeMenuListView betLv;
    @BindView(R.id.tv_qd)
    TextView tvQd;
    @BindView(R.id.tv_hq)
    TextView tvhq;



    private BetAdapter adapter;

    private String tzmoney = "33"; //接受投注钱数
    private String game_id;//游戏id
    private String play_id;//玩法id
    private String userId;
    private double money = 0;
    private String ids;

    private ArrayList<Shil> s = new ArrayList<>();
    private DecimalFormat df=new DecimalFormat("0.00");

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1: {
                    money =0;
                    initMoney();
                    tvhq.setText("共  "+ money + "  元");
                }
                break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_bet;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleright.setText("清空");
        titleright.setVisibility(View.VISIBLE);
        titlename.setText("投注");

    }

    @Override
    public void initData() {
        ids = getIntent().getStringExtra("id");
        tzmoney = getIntent().getStringExtra("qian");
        userId = getIntent().getStringExtra("userid");
        game_id = getIntent().getStringExtra("game_id");
        play_id = getIntent().getStringExtra("play_id");
        tvhq.setText("共  "+ tzmoney + "  元");
        s = (ArrayList<Shil>) getIntent().getSerializableExtra("betlist");
        adapter = new BetAdapter(this);
        betLv.setAdapter(adapter);
        adapter.refresh(s);

        adapter.setOnBeiListener(new BetAdapter.BeiListener() {
            @Override
            public void onBeiChanged(int number) {
                handler.sendEmptyMessage(1);
            }
        });
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(getResources().getColor(R.color.colorWhite));
                menu.addMenuItem(deleteItem);

                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x40, 0xD0,
                        0xE0)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("追号");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

            }
        };

        betLv.setMenuCreator(creator);
        betLv.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        //删除
                        s.remove(position);
                        money = 0;
                        initMoney();
                        tvhq.setText("共  "+ df.format(money) + "  元");
                        adapter.refresh(s);
                        break;
                    case 1:
                        //追号
                        ArrayList<Shil> betlist = new ArrayList<>();

                        Shil ss = new Shil();
                        ss.setHaoma(s.get(position).getHaoma());
                        ss.setWanfa(s.get(position).getWanfa());
                        ss.setZhushu(s.get(position).getZhushu());
                        ss.setDanjia(s.get(position).getDanjia());
                        ss.setFandianl(s.get(position).getFandianl());
                        ss.setBeishu(s.get(position).getBeishu());
                        ss.setUnit(s.get(position).getUnit());
                        betlist.add(ss);


                        Intent intent = new Intent(BetActivity.this,AfterNoActivity.class);
                        intent.putExtra("id",ids);
                        intent.putExtra("userid",userId);
                        intent.putExtra("game_id",game_id);
                        intent.putExtra("play_id",play_id);
                        intent.putExtra("bet", (Serializable) betlist);

                        startActivity(intent);

                        break;
                }

                return false;


            }
        });

    }

    private void initMoney() {
        for (int i = 0;i < s.size();i++){
            double aDouble = Double.valueOf(s.get(i).getDanjia());
            int bs = Integer.valueOf(s.get(i).getBeishu());
            money = money +(aDouble * s.get(i).getZhushu() * bs);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @OnClick({R.id.title_left,R.id.title_right,R.id.tv_qd})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                Intent intent1 = new Intent();
                intent1.putExtra("betlist",(Serializable)s);
                setResult(2,intent1);
                finish();
                break;
            case R.id.title_right:
                adapter.sclear();
                s.clear();
                money =0;
                initMoney();
                tvhq.setText("共  "+ money + "  元");
                break;
            case R.id.tv_qd:
                int k = 0;
                for (int j = 0;j < s.size();j++){
                    k = k + s.get(j).getZhushu();
                }
                money =0;
                initMoney();
                List<BetDataBean> lists = new ArrayList<>();
                for (int p = 0; p < s.size();p++){
                    BetDataBean bet = new BetDataBean();
                    bet.setCode(s.get(p).getHaoma());
                    bet.setTimes(s.get(p).getBeishu());
                    bet.setGame_id(game_id);
                    bet.setPlay_id(play_id);
                    double aDouble = Double.valueOf(s.get(p).getDanjia());
                    int bs = Integer.valueOf(s.get(p).getBeishu());
                    double money = aDouble * s.get(p).getZhushu() * bs;
                    bet.setBetAmount(df.format(money));
                    bet.setCurrency(s.get(p).getUnit());
                    bet.setReturnPer(s.get(p).getFandianl());
                    bet.setBetCount(s.get(p).getZhushu()+"");

                    lists.add(bet);
                }

                Call call = LotteryClient.getInstance().getBet(userId,k+"",df.format(money),lists.toString());
                call.enqueue(new UICallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        ToastUtils.showToast(BetActivity.this,e.getMessage());
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {

                        PlayAllResult result = new Gson().fromJson(body,PlayAllResult.class);

                        if (result.getErrormsg().equals("")){
                            ToastUtils.showToast(BetActivity.this,"投注成功");
                            Intent intent = new Intent(BetActivity.this, CashTransactionActivity.class);
                            intent.putExtra("tag", 0);
                            intent.putExtra("userId", userId);
                            startActivity(intent);

                            adapter.sclear();
                            s.clear();
                            Intent intent1 = new Intent();
                            intent1.putExtra("betlist",(Serializable)s);
                            setResult(2,intent1);
                            finish();
                        }else {
                            ToastUtils.showToast(BetActivity.this,result.getErrormsg());
                        }

                    }
                });


                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {


            Intent intent1 = new Intent();
            intent1.putExtra("betlist",(Serializable)s);
            setResult(2,intent1);
            finish();
        }
        return true;
    }
}
