package com.xiongyuan.lottery.homepage.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.homepage.view.SimpleNumberPicker;
import com.xiongyuan.lottery.thirdpage.bean.Shil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-07-05.
 */

public class BetAdapter extends BaseAdapter{
    private Context context;
    private List<Shil> list = new ArrayList<>();
    private BeiListener mBeiListener;

    public BetAdapter(Context context) {
        this.context = context;
    }

    public void refresh(List<Shil> s){
        list.clear();
        list.addAll(s);
        notifyDataSetChanged();

    }
    public void sclear(){
        list.clear();
        notifyDataSetChanged();

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
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_bet_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }



        viewHolder.tvhm.setText("号码： "+list.get(position).getHaoma());
        viewHolder.tvwf.setText("玩法： "+list.get(position).getWanfa());
        viewHolder.tvzyf.setText(list.get(position).getZhushu() + " 注，"
                +list.get(position).getDanjia() + " 元，返点率"
                +list.get(position).getFandianl() + "%");

        String beishu = list.get(position).getBeishu();
        viewHolder.numberPicker.setNumber(Integer.valueOf(beishu));
        viewHolder.numberPicker.setOnNumberChangeListener(new SimpleNumberPicker.OnNumberChangeListener() {
            @Override
            public void onNumberChanged(int number) {
                list.get(position).setBeishu(number+"");
                if (mBeiListener!=null){
                    mBeiListener.onBeiChanged(1);
                }
            }
        });

        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.tv_hm)
        TextView tvhm;
        @BindView(R.id.tv_wf)
        TextView tvwf;
        @BindView(R.id.tv_zyf)
        TextView tvzyf;
        @BindView(R.id.number_picker)
        SimpleNumberPicker numberPicker;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
    // 提供一个设置监听的方法
    public void setOnBeiListener(BeiListener beiListener){
        mBeiListener= beiListener;
    }

    // 对外提供一个接口：利用接口回调，写一个方法：用于传递数量的变化
    public interface BeiListener{
        void onBeiChanged(int number);
    }
}
