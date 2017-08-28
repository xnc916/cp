package com.xiongyuan.lottery.homepage.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.homepage.bean.pkdata.Shili;
import com.xiongyuan.lottery.homepage.view.SimpleNumberPicker;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-07-22.
 */

public class AfterNoadapter extends BaseAdapter {
    private Context context;
    private ArrayList<Shili> llist = new ArrayList<>();//带期号
    private OnBsListener mBsListener;

    public AfterNoadapter(Context context) {
        this.context = context;
    }

    public void refresh(ArrayList<Shili> list){
        llist.clear();
        llist.addAll(list);

        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return llist.size();
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
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_bet_item2,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tvqh.setText("期号："+llist.get(position).getQihao());


        viewHolder.tvhm.setText("号码： "+llist.get(position).getHaoma());
        viewHolder.tvwf.setText("玩法： "+llist.get(position).getWanfa());
        viewHolder.tvzyf.setText(llist.get(position).getZhushu() + " 注，"
                +llist.get(position).getDanjia() + " 元，返点率"
                +llist.get(position).getFandianl() + "%");

        String beishu = llist.get(position).getBeishu();
        viewHolder.numberPicker.setNumber(Integer.valueOf(beishu));
        viewHolder.numberPicker.setOnNumberChangeListener(new SimpleNumberPicker.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                llist.get(position).setBeishu(number+"");
                if (mBsListener!=null){
                    mBsListener.onBsChanged(1);
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_qh)
        TextView tvqh;
        @BindView(R.id.tv_hm)
        TextView tvhm;
        @BindView(R.id.tv_wf)
        TextView tvwf;
        @BindView(R.id.tv_zyf)
        TextView tvzyf;
        @BindView(R.id.number_picker)
        SimpleNumberPicker numberPicker;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // 提供一个设置监听的方法
    public void setOnBsListener(OnBsListener bsListener){
        mBsListener= bsListener;
    }

    // 对外提供一个接口：利用接口回调，写一个方法：用于传递数量的变化
    public interface OnBsListener{
        void onBsChanged(int number);
    }
}
