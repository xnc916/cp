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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-05.
 */

public class LotteryDetailsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ArcList> listList=new ArrayList<>();

    public LotteryDetailsAdapter(Context context) {
        this.context = context;

    }
    //刷新方法
    public void refresh(ArrayList<ArcList> list) {
        if (list != null && list.size() >= 0) {
            listList.clear();
            listList.addAll(list);
            notifyDataSetChanged();
        }
    }
    public void refresh2(ArrayList<ArcList> list) {
            listList.addAll(list);
            notifyDataSetChanged();

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
            convertView = View.inflate(context, R.layout.adapter_lottery_datail_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(listList.get(position).getImg());
        viewHolder.tv.setText(listList.get(position).getName());
        viewHolder.tvIssue.setText(listList.get(position).getTvIssue());
        viewHolder.tvDate.setText(listList.get(position).getTvDate());

                if (listList.get(position).getRedNum()!=0){
                    viewHolder.ll.removeAllViews();

                    for (int i=0;i<listList.get(position).getRedStr().length;i++){

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
                        tv.setText(listList.get(position).getRedStr()[i]);
                        viewHolder.ll.addView(tv, param);
                    }
                }

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.lll)
        LinearLayout ll;
        @BindView(R.id.lll_)
        LinearLayout ll_;
        @BindView(R.id.llist_img)
        ImageView imageView;
        @BindView(R.id.llist_tv)
        TextView tv;
        @BindView(R.id.llist_tv_date)
        TextView tvDate;
        @BindView(R.id.llist_tv_issue)
        TextView tvIssue;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

