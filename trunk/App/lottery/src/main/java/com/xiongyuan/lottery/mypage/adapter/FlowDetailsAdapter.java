package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-22.
 */

public class FlowDetailsAdapter extends BaseAdapter {
    private String[] itemNames = new String[]{"用户帐号", "变动时间","变动类型","支出金额（元）","之后余额（元）","备注"};
    private Context context;
    private ArrayList<String> mList=new ArrayList<>();

    public FlowDetailsAdapter(Context context) {
        this.context = context;
    }
    //刷新方法
    public void refresh(ArrayList<String> list) {
        if (list != null && list.size() >= 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
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
            convertView=View.inflate(context, R.layout.adapter_flow_details_list_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(itemNames[position]);
        if (mList.size()!=0){
            viewHolder.tvContent.setText(mList.get(position));
        }
        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.item_flow_tv_title)
        TextView tvTitle;
        @BindView(R.id.item_flow_tv_content)
        TextView tvContent;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}