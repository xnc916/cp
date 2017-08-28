package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.MyMessageInfo;
import com.xiongyuan.lottery.mypage.bean.MyTeamBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-06-08.
 */

public class StatisticsTodayAdapter extends BaseAdapter {
    private int[] imagesIds = new int[]{R.mipmap.item2, R.mipmap.item3, R.mipmap.item4, R.mipmap.item5, R.mipmap.item6, R.mipmap.item1};
    private String[] itemNames = new String[]{"充值金额", "取款总额", "投注金额", "赚水", "中奖金额", "盈亏"};
    private Context context;
    private ArrayList<String> list=new ArrayList<>();


    public StatisticsTodayAdapter(Context context) {
        this.context = context;
    }

    //刷新方法
    public void refresh(ArrayList<String> mList) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_statistics_today, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(imagesIds[position]);
        viewHolder.tvContent.setText(itemNames[position]);
        if (list.size()>0){
            viewHolder.tvText.setText(list.get(position));
        }
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.iv_me_icon)
        ImageView imageView;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_me_text)
        TextView tvText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
