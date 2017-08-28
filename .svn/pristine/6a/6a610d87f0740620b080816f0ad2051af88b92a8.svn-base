package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.GameIconBean;
import com.xiongyuan.lottery.mypage.bean.RecordNumberBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/06/18
 */
public class RecordNumberAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<RecordNumberBean> beanArrayList = new ArrayList<>();
    private List<GameIconBean> beanList = new ArrayList<>();

    //刷新方法
    public void refresh(ArrayList<RecordNumberBean> list) {
        if (list != null && list.size() >= 0) {
            beanArrayList.clear();
            beanArrayList.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void setBeanList(List<GameIconBean> beanList) {
        this.beanList = beanList;
    }

    public RecordNumberAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return beanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.today_recharge_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        for (int i = 0;i < beanList.size();i++){
            if (beanArrayList.get(position).getGame_id().equals(beanList.get(i).getId())){
                holder.iv.setImageResource(beanList.get(i).getIcon());
                holder.tvGameTitle.setText(beanList.get(i).getTitle());
            }
        }


        holder.tvBetAmount.setText("追号"+beanArrayList.get(position).getIssueCount()+"期");
        holder.tvAddTime.setText("投注"+beanArrayList.get(position).getBetAmount()+"元");

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tr_iv)
        ImageView iv;
        @BindView(R.id.tr_tv_addTime)
        TextView tvAddTime;
        @BindView(R.id.tr_tv_game_title)
        TextView tvGameTitle;
        @BindView(R.id.tr_tv_betAmount)
        TextView tvBetAmount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
