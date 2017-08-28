package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.GameIconBean;
import com.xiongyuan.lottery.mypage.bean.TodayRechargeBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/06/18
 */
public class TodayRechargeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TodayRechargeBean> beanArrayList = new ArrayList<>();

    private List<GameIconBean> beanList = new ArrayList<>();

    //刷新方法
    public void refresh(ArrayList<TodayRechargeBean> list) {
        if (list != null && list.size() >= 0) {
            beanArrayList.clear();
            beanArrayList.addAll(list);
            notifyDataSetChanged();
        }

    }

    public void setBeanList(List<GameIconBean> beanList) {
        this.beanList = beanList;
    }

    public TodayRechargeAdapter(Context context) {
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

        String status = beanArrayList.get(position).getStatus();
        if (status.equals("-1")){
            holder.tvStatus.setText("撤单");
        }else if (status.equals("0")){
            holder.tvStatus.setText("未开奖");
        }else if (status.equals("1")){
            holder.tvStatus.setText("已封单");
        }else if (status.equals("2")){
            holder.tvStatus.setText("已开奖");
        }else if (status.equals("3")){
            holder.tvStatus.setText("中奖:"+beanArrayList.get(position).getWinAmount()+"元");
        }

        for (int i = 0;i < beanList.size();i++){
            if (beanArrayList.get(position).getGameTitle().equals(beanList.get(i).getTitle())){
                int tu = beanList.get(i).getIcon();
                holder.iv.setImageResource(tu);
                break;
            }
        }

        holder.tvGameTitle.setText(beanArrayList.get(position).getGameTitle());

        if(beanArrayList.get(position).getIssue() == null || beanArrayList.get(position).getIssue().equals("")){
            beanArrayList.get(position).setIssue("暂无该");
        }
        holder.tvIssue.setText(beanArrayList.get(position).getIssue().concat("期"));

        if (beanArrayList.get(position).getIssue()!=null){
            holder.tvIssue.setText(beanArrayList.get(position).getIssue().concat("期"));
        }

        holder.tvBetAmount.setText("投注:"+beanArrayList.get(position).getBetAmount()+"元");
        holder.tvAddTime.setText(beanArrayList.get(position).getAddTime());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tr_iv)
        ImageView iv;
        @BindView(R.id.tr_tv_issue)
        TextView tvIssue;
        @BindView(R.id.tr_tv_addTime)
        TextView tvAddTime;
        @BindView(R.id.tr_tv_betAmount)
        TextView tvBetAmount;
        @BindView(R.id.tr_tv_status)
        TextView tvStatus;
        @BindView(R.id.tr_tv_game_title)
        TextView tvGameTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}