package com.xiongyuan.lottery.thirdpage.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.thirdpage.bean.ArcList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-05.
 */

public class MyListViewAdapter extends BaseAdapter {
    private Context context;
    private TreeMap<String,ArrayList<ArcList>> listList=new TreeMap<>();
    private ArrayList<String> data = new ArrayList<>();


    public MyListViewAdapter(Context context) {
        this.context = context;

    }
    //刷新方法
    public void refresh(TreeMap<String,ArrayList<ArcList>> list) {
        if (list != null && list.size() >= 0) {
            listList.clear();
            listList.putAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_mylistview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        ArrayList<ArcList> value = listList.get(data.get(position));
        if (value.size()>0) {

            viewHolder.imageView.setImageResource(value.get(0).getImg());

            viewHolder.tv.setText(value.get(0).getName() + "");

            viewHolder.tvIssue.setText(value.get(0).getTvIssue());


            viewHolder.tvDate.setText(value.get(0).getTvDate());


                if (value.get(0).getRedNum()!=0){
                    viewHolder.ll.removeAllViews();
                    for (int i=0;i<value.get(0).getRedStr().length;i++){
                        TextView tv = new TextView(context);
                        tv.setBackgroundResource(R.drawable.textviewred);
                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen.linearLayout_size),
                                context.getResources().getDimensionPixelOffset(R.dimen.linearLayout_size));
                        param.setMargins(0, 0, 5, 0);
                        tv.setGravity(Gravity.CENTER);
                        tv.setIncludeFontPadding(false);
                        tv.setTextSize(16);
                        tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
                        //2.把信息设置为文本框的内容
                        tv.setText(value.get(0).getRedStr()[i]);
                        viewHolder.ll.addView(tv, param);
                    }
                }
                if(value.size() > 1) {
                    viewHolder.tvq.setText(value.get(1).getName() + "");
                    viewHolder.tvqIssue.setText(value.get(1).getTvIssue());
                    viewHolder.tvqDate.setText(value.get(1).getTvDate());
                    if (value.get(1).getRedNum() != 0) {
                        viewHolder.ll1.removeAllViews();
                        for (int i = 0; i < value.get(1).getRedStr().length; i++) {
                            TextView tv = new TextView(context);
                            tv.setBackgroundResource(R.drawable.textviewred);
                            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(context.getResources().getDimensionPixelOffset(R.dimen.linearLayout_size),
                                    context.getResources().getDimensionPixelOffset(R.dimen.linearLayout_size));
                            param.setMargins(0, 0, 5, 0);
                            tv.setGravity(Gravity.CENTER);
                            tv.setIncludeFontPadding(false);
                            tv.setTextSize(16);
                            tv.setTextColor(context.getResources().getColor(R.color.colorWhite));
                            //2.把信息设置为文本框的内容
                            tv.setText(value.get(1).getRedStr()[i]);
                            viewHolder.ll1.addView(tv, param);
                        }
                    }
                }
            }

        return convertView;
    }

    public void setMap(Map<String,ArrayList<ArcList>> map) {
        listList.putAll(map);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void setDatas(List<String> datas) {
        data.addAll(datas);
    }


    class ViewHolder {
        @BindView(R.id.ll)
        LinearLayout ll;
        @BindView(R.id.ll1)
        LinearLayout ll1;
        @BindView(R.id.ll_)
        LinearLayout ll_;
        @BindView(R.id.ll1_)
        LinearLayout ll1_;
        @BindView(R.id.list_img)
        ImageView imageView;
        @BindView(R.id.list_tv)
        TextView tv;
        @BindView(R.id.list_tv_date)
        TextView tvDate;
        @BindView(R.id.list_tv_issue)
        TextView tvIssue;
        @BindView(R.id.list_tvq)
        TextView tvq;
        @BindView(R.id.list_tvq_date)
        TextView tvqDate;
        @BindView(R.id.list_tvq_issue)
        TextView tvqIssue;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
