package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.SubordinateAccountItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-22.
 */

public class SubordinateAccountAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SubordinateAccountItemBean> mList=new ArrayList<>();
    public SubordinateAccountAdapter(Context context) {
        this.context = context;
    }

    //刷新方法
    public void refresh(ArrayList<SubordinateAccountItemBean> list) {
        if (list != null && list.size() >= 0) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_subordinate_account_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvWeek.setText(mList.get(position).getWeek());
        viewHolder.tvDate.setText(mList.get(position).getDate());
        viewHolder.tvTitle.setText(mList.get(position).getType());
        viewHolder.tvContent.setText(mList.get(position).getIntro());
        viewHolder.tvUsable.setText(mList.get(position).getUsable().concat("元"));
        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.item_iv)
        ImageView iv;
        @BindView(R.id.item_tv_title)
        TextView tvTitle;
        @BindView(R.id.item_tv_content)
        TextView tvContent;
        @BindView(R.id.item_tv_week)
        TextView tvWeek;
        @BindView(R.id.item_tv_date)
        TextView tvDate;
        @BindView(R.id.item_tv_usable)
        TextView tvUsable;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
