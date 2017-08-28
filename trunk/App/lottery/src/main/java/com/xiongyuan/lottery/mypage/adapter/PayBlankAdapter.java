package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.Paybean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-08-03.
 */

public class PayBlankAdapter extends android.widget.BaseAdapter{
    private Context context;
    private ArrayList<Paybean> paylist = new ArrayList<>();

    public PayBlankAdapter(Context context, ArrayList<Paybean> list) {
        this.context = context;
        this.paylist = list;
    }

    @Override
    public int getCount() {
        return paylist.size();
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
            convertView=View.inflate(context, R.layout.pay_blank_adapter,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(paylist.get(position).getTitle());
        return convertView;
    }
    class ViewHolder{

        @BindView(R.id.pay_blank_tv)
        TextView tv;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
