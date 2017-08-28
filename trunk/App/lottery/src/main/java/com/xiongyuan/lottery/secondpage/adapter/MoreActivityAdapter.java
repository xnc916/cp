package com.xiongyuan.lottery.secondpage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.secondpage.bean.MoreInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-05.
 */

public class MoreActivityAdapter extends BaseAdapter{
    private List<MoreInfo> list = new ArrayList<>();
    private int[] img = {R.mipmap.activity_icon_01,R.mipmap.activity_icon_02,R.mipmap.bbb};
    private Context context;

    public MoreActivityAdapter(Context context) {
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.adapter_subject_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.iv.setImageResource(img[position]);
        viewHolder.name.setText(list.get(position).getTitle());
        String s = stampToDate(list.get(position).getStartTime());
        String s1 = stampToDate(list.get(position).getEndTime());
        viewHolder.intro.setText("开始时间： "+s);
        viewHolder.introend.setText("结束时间： "+s1);
        return convertView;
    }

    public void setDatalist(List<MoreInfo> datalist) {
        this.list = datalist;
    }

    class ViewHolder{
        @BindView(R.id.subject_iv)
        ImageView iv;
        @BindView(R.id.subject_tv_intro)
        TextView intro;
        @BindView(R.id.subject_tv_name)
        TextView name;
        @BindView(R.id.subject_tv_intro_end)
        TextView introend;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

    public  String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = new Long(s);
        Date date = new Date(lt*1000L);
        res = simpleDateFormat.format(date);
        return res;
    }
}