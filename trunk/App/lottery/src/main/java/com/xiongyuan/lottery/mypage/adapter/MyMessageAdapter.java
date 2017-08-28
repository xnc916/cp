package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.MyMessageInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-06-08.
 */

public class MyMessageAdapter extends BaseAdapter {
    private Context context;
    private String read;
    private List<MyMessageInfo> list = new ArrayList<>();

    public MyMessageAdapter(Context context) {
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_mymessage_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.myMessagTvtitle.setText(list.get(position).getTitle());
        viewHolder.myMessagContent.setText(list.get(position).getContent());
        viewHolder.myMessagTime.setText(list.get(position).getAddTime());
        String status = list.get(position).getStatus();
        int i = Integer.valueOf(status);

        if (i == 0){
            read = "未读";
        }else {
            read = "已读";
        }

        viewHolder.myMessageRead.setText(read);

        return convertView;
    }

    public void setDatilist(List<MyMessageInfo> datilist) {
        this.list = datilist;
    }



    class ViewHolder {
        @BindView(R.id.my_messag_image)
        ImageView myMessagImage;
        @BindView(R.id.my_messag_tvtitle)
        TextView myMessagTvtitle;
        @BindView(R.id.my_messag_content)
        TextView myMessagContent;
        @BindView(R.id.my_messag_time)
        TextView myMessagTime;
        @BindView(R.id.my_message_read)
        TextView myMessageRead;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
