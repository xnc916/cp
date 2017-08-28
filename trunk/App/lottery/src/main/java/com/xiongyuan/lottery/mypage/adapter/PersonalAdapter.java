package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.AddManageBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-22.
 */

public class PersonalAdapter extends BaseAdapter {
    private String[] itemNames = new String[]{"主帐号余额（元）", "高/低频返点","总入款（元）","总出款（元）","注册时间","修改高频返点","修改低频返点"};
    private Context context;
    private ArrayList list=new ArrayList();

    public PersonalAdapter(Context context) {
        this.context = context;
    }
    //刷新方法
    public void refresh(ArrayList mList) {
        if (mList != null && mList.size() >= 0) {
            list.clear();
            list.addAll(mList);
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
            convertView=View.inflate(context, R.layout.personal_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvLeft.setText(itemNames[position]);
        if (itemNames[position].equals("高/低频返点")){
           String[] str= (String[]) list.get(position);
            viewHolder.tvView.setVisibility(View.VISIBLE);
            viewHolder.tvRightRight.setVisibility(View.VISIBLE);
            viewHolder.tvRight.setText(str[0]);
            viewHolder.tvRightRight.setText(str[1]);
        }else{
            viewHolder.tvView.setVisibility(View.GONE);
            viewHolder.tvRightRight.setVisibility(View.GONE);
            viewHolder.tvRight.setText(list.get(position).toString());
        }

        if (position>4){
            viewHolder.iv.setVisibility(View.VISIBLE);
        }else{
            viewHolder.iv.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.tv_personal_left)
                TextView tvLeft;
        @BindView(R.id.tv_personal_right)
                TextView tvRight;
        @BindView(R.id.iv_personal_item)
                ImageView iv;
        @BindView(R.id.tv_personal_view)
                TextView tvView;
        @BindView(R.id.tv_personal_right_right)
                TextView tvRightRight;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}