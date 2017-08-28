package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.HelpCenterSubInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-06-20.
 */

public class HelpCenterfAdapter extends BaseAdapter{
    private List<HelpCenterSubInfo> list = new ArrayList<>();
    private Context context;

    public HelpCenterfAdapter(Context context) {
        this.context = context;
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
     ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_help_center_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getTitle());

        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.hc_tv)
        TextView textView;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
    public void setDatalist(List<HelpCenterSubInfo> list){
        this.list = list;
    }
}
