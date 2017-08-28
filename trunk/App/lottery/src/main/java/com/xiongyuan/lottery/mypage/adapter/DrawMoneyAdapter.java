package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.DrawMoneyInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-06-07.
 */

public class DrawMoneyAdapter extends BaseAdapter{
    private List<DrawMoneyInfo> list = new ArrayList<>();
    private Context context;

    public DrawMoneyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
   ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_darwmoney_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.darwTv1.setText("1.每日提现次数为："+list.get(position).getDailyCashTimes()+"次");
        viewHolder.darwTv2.setText("2.每日免费次数为："+list.get(position).getDailyCashFree()+"次");

        viewHolder.darwTv3.setText("3.提现手续费为："+list.get(position).getCashCommission());

        viewHolder.darwTv4.setText("4.最大提现额度为："+list.get(position).getMaxCash()+"元");
        viewHolder.darwTv5.setText("5.最小提现额度为："+list.get(position).getMinCash()+"元");

        viewHolder.darwTv6.setText("6.人工处理开始时间为："+list.get(position).getCashStartTime());
        viewHolder.darwTv7.setText("7.人工处理结束时间为："+list.get(position).getCashEndTime());

        viewHolder.darwTv8.setText("8.提现消费占比为："+list.get(position).getCashConsShare());

        return convertView;
    }

    public void setDatalist(List<DrawMoneyInfo> datalist) {
        this.list = datalist;
    }


    class ViewHolder{
        @BindView(R.id.darw_tv_1)
        TextView darwTv1;
        @BindView(R.id.darw_tv_2)
        TextView darwTv2;
        @BindView(R.id.darw_tv_3)
        TextView darwTv3;
        @BindView(R.id.darw_tv_4)
        TextView darwTv4;
        @BindView(R.id.darw_tv_5)
        TextView darwTv5;
        @BindView(R.id.darw_tv_6)
        TextView darwTv6;
        @BindView(R.id.darw_tv_7)
        TextView darwTv7;
        @BindView(R.id.darw_tv_8)
        TextView darwTv8;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
