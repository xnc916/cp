package com.xiongyuan.lottery.mypage.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.StatisticsTodayAdapter;
import com.xiongyuan.lottery.mypage.bean.RecordNumberBean;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordNumberItemActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.list_record_number_item)
    ListView listView;
    private ArrayList<String> list;
    @Override
    public int getLayoutId() {
        return R.layout.activity_record_number_item;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("投注详情");
        MyAdapter myAdapter=new MyAdapter();
        listView.setAdapter(myAdapter);
    }

    @Override
    public void initData() {
        list=new ArrayList<>();
        RecordNumberBean bean = (RecordNumberBean) getIntent().getSerializableExtra("list");
        list.add(bean.getGame_id());
        list.add(bean.getIssueCount());
        list.add(bean.getBetAmount());
        list.add(bean.getWinAmount());
        list.add(bean.getStartTime());
        list.add(bean.getEndTime());
        list.add(bean.getWinStop());
        list.add(bean.getAddTime());
        list.add(bean.getStatus());
        list.add(bean.getProgress());
    }
    @OnClick({R.id.title_left})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
     class MyAdapter extends BaseAdapter {
        private String[] itemNames = new String[]{"彩种名称", "追号期数", "投注金额", "赢得奖金", "开始时间", "结束时间","中奖停止","添加时间","追号状态","追号进度"};

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
                convertView = View.inflate(RecordNumberItemActivity.this, R.layout.adapter_record_number_item, null);
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