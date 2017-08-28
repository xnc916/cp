package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.WithdrawalInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-06-07.
 */

public class WithdrawalAdapter extends BaseAdapter{
    private Context context;
    private List<WithdrawalInfo> list = new ArrayList<>();
    public WithdrawalAdapter(Context context) {
        this.context = context;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder viewHolder;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.bank_list_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.b_user.setText(list.get(position).getAccountName());
        viewHolder.b_hang.setText(list.get(position).getBankName());
        viewHolder.b_id.setText(list.get(position).getCardNum());
        return convertView;
    }

    public void setDatalist(List<WithdrawalInfo> datalist) {
        this.list = datalist;
    }


    class ViewHolder{
        @BindView(R.id.bank_user)
        TextView b_user;
        @BindView(R.id.bank_hang)
        TextView b_hang;
        @BindView(R.id.bank_id)
        TextView b_id;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
