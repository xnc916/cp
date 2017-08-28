package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.bean.TodayRechargeBean;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class BetDetailsActivity extends BaseActivity {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.list_record_number_item)
    ListView listview;
    @BindView(R.id.bet_deta_cd)
    Button betDetaCd;
    private ArrayList<String> list;
    private TodayRechargeBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_bet_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("投注详情");
        MyAdapter myAdapter=new MyAdapter();
        listview.setAdapter(myAdapter);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        bean = (TodayRechargeBean) getIntent().getSerializableExtra("list");
        list.add(bean.getGameTitle());
        list.add(bean.getTypeTitle()+"-"+ bean.getPlayTitle());
        list.add(bean.getLog_id());
        list.add(bean.getIssue());
        list.add("");//购买模式
        list.add(bean.getReturnPer()+"%-"+bean.getReturnAmount());
        list.add(bean.getCode());
        list.add(bean.getBetAmount());
        list.add(bean.getBetCount());
        list.add(bean.getBetTimes());
        list.add(bean.getAddTime());
        list.add(bean.getBingoCode().toString());
        String status = null;
        if (bean.getStatus().equals("-1")){
            status = "撤单";
        }else if (bean.getStatus().equals("0")){
            status = "未开奖";
        }else if (bean.getStatus().equals("1")){
            status = "已封单";
        }else if (bean.getStatus().equals("2")){
            status = "已开奖";
        }else if (bean.getStatus().equals("3")){
            status = "中奖";
        }

        list.add(status);
        list.add(bean.getWinCount()+"注-"+bean.getWinAmount()+"元");

    }

    @OnClick({R.id.title_left,R.id.bet_deta_cd})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.bet_deta_cd:
                if (!bean.getStatus().equals("0")){
                    String s = null;
                    if (bean.getStatus().equals("-1")){
                        s = "已撤单，不能重复撤单";
                    }else if (bean.getStatus().equals("1")){
                        s = "已封单，不能撤单";
                    }else if (bean.getStatus().equals("2")){
                        s = "已开奖，不能撤单";
                    }else if (bean.getStatus().equals("3")){
                        s = "已中奖，不能撤单";
                    }
                    ToastUtils.showToast(BetDetailsActivity.this,s);
                    return;
                }
                Call call = LotteryClient.getInstance().getCHe(CachePreferences.getUser().getUser_id(),bean.getId());
                call.enqueue(new UICallBack() {
                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        ToastUtils.showToast(BetDetailsActivity.this,e.getMessage());
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {

                    }
                });


                break;

        }
    }



    class MyAdapter extends BaseAdapter {
        private String[] itemNames = new String[]{"所买彩种", "彩票玩法", "订  单  号 ", "彩票期号", "购买模式", "奖金返点","投注号码","投注金额","投注注数","投注倍数","投注时间","开奖号码","开奖状态","中奖金额"};

        @Override
        public int getCount() {
            return itemNames.length;
        }

        @Override
        public Object getItem(int position) {
            return itemNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(BetDetailsActivity.this, R.layout.adapter_record_number_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvContent.setText(itemNames[position]);
            if (list.size()>0){
                viewHolder.tvText.setText(list.get(position));
            }

            return convertView;
        }


        class ViewHolder {

            @BindView(R.id.tv_content)
            TextView tvContent;
            @BindView(R.id.tv_me_text)
            TextView tvText;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}
