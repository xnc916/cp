package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.AddManageBean;
import com.xiongyuan.lottery.thirdpage.bean.ArcList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-05.
 */

public class FragmentAddManageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AddManageBean> listList=new ArrayList<>();

    public FragmentAddManageAdapter(Context context) {
        this.context = context;

    }
    //刷新方法
    public void refresh(ArrayList<AddManageBean> list) {
        if (list != null && list.size() >= 0) {
            listList.clear();
            listList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return listList.size();
    }

    @Override
    public Object getItem(int position) {
        return listList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_add_manage_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSurplusDate.setText(listList.get(position).getExpireTime());
        viewHolder.tvAddMax.setText(listList.get(position).getMaxRebate());
        viewHolder.tvAddMin.setText(listList.get(position).getMinRebate());
        viewHolder.tvAddLink.setText(listList.get(position).getRegUrl());

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.tv_add_max)
                TextView tvAddMax;
        @BindView(R.id.tv_add_min)
                TextView tvAddMin;
        @BindView(R.id.tv_add_link)
                TextView tvAddLink;
        @BindView(R.id.tv_surplus_date)
                TextView tvSurplusDate;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

