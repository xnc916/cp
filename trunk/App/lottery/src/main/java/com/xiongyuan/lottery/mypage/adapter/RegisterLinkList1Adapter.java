package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.xiongyuan.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by gameben on 2017-06-02.
 */

public class RegisterLinkList1Adapter extends BaseAdapter{
    private String[] itemNames = new String[]{"代理链接", "会员链接"};

    private Context context;

    public RegisterLinkList1Adapter(Context context) {
        this.context = context;
    }

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
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.register_list_1,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvContent.setText(itemNames[position]);
        viewHolder.reet.setVisibility(View.GONE);


        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.register_tv_content)
        TextView tvContent;
        @BindView(R.id.register_et_num)
        EditText reet;
        @BindView(R.id.register_link_checkBox)
        CheckBox cb;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
