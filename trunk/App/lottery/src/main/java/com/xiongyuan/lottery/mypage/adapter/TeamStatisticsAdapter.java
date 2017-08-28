package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-22.
 */

public class TeamStatisticsAdapter extends BaseAdapter {
    private int[] imagesIds = new int[]{R.mipmap.icon_tuandui_01, R.mipmap.icon_tuandui_02,R.mipmap.icon_tuandui_03, R.mipmap.icon_tuandui_04,R.mipmap.icon_tuandui_05, R.mipmap.icon_tuandui_06,R.mipmap.icon_tuandui_07, R.mipmap.icon_tuandui_08};
    private String[] itemNames = new String[]{"团队人数", "在线人数","团队余额","新用户数","中奖金额","团队赚水","充值总额","取款总额"};
    private ArrayList<String> mList=new ArrayList<>();
    private Context context;

    public TeamStatisticsAdapter(Context context) {
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
            convertView=View.inflate(context, R.layout.rv_list,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.ivLeftIcon.setImageResource(imagesIds[position]);
        viewHolder.tvContent.setText(itemNames[position]);
        viewHolder.ivRight.setVisibility(View.INVISIBLE);
        viewHolder.tvText.setVisibility(View.VISIBLE);
        if (mList.size()!=0){
            viewHolder.tvText.setText(mList.get(position));
        }
        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.iv_me_icon)
        ImageView ivLeftIcon;
        @BindView(R.id.iv_me_right)
        ImageView ivRight;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_me_text)
        TextView tvText;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
