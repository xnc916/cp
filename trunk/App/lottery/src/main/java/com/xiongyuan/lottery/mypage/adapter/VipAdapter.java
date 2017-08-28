package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.secondpage.bean.JfrecodeInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-08-11.
 */

public class VipAdapter extends android.widget.BaseAdapter{
    private ArrayList<JfrecodeInfo> onlineSaleBeanList = new ArrayList<>();
    private Context context;

    public VipAdapter(Context context) {
        this.context = context;
    }

    public void setOnlineSaleBeanList(ArrayList<JfrecodeInfo> onList) {
        onlineSaleBeanList.clear();
        onlineSaleBeanList.addAll(onList);
        notifyDataSetChanged();
    }

    public void refust(ArrayList<JfrecodeInfo> list){
        onlineSaleBeanList.addAll(list);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return onlineSaleBeanList.size();
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
      ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_vip_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.time.setText(onlineSaleBeanList.get(position).getAddTime());
        viewHolder.lx.setText(onlineSaleBeanList.get(position).getType());
        viewHolder.bjf.setText(onlineSaleBeanList.get(position).getUsable());
        viewHolder.djf.setText(onlineSaleBeanList.get(position).getUsableAmount());
        return convertView;
    }
    class ViewHolder{

        @BindView(R.id.vip_time)
        TextView time;
        @BindView(R.id.vip_lx)
        TextView lx;
        @BindView(R.id.vip_bjf)
        TextView bjf;
        @BindView(R.id.vip_djf)
        TextView djf;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}