package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-05-22.
 */

public class MyList2Adapter extends BaseAdapter {
    private int[] imagesIds = new int[]{R.mipmap.lianjie,R.mipmap.lianjie, R.mipmap.wenhao, R.mipmap.guanyu, R.mipmap.tuichudenglu_icon};
    private String[] itemNames = new String[]{"切换线路","生成注册链接", "帮助中心","关于猜猜乐","退出登录"};
    private Context context;

    public MyList2Adapter(Context context) {
        this.context = context;
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
            convertView=View.inflate(context, R.layout.my_list,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.ivLeftIcon.setImageResource(imagesIds[position]);
        viewHolder.tvContent.setText(itemNames[position]);
        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.iv_me_icon)
        ImageView ivLeftIcon;
        @BindView(R.id.iv_me_right)
        ImageView ivRight;
        @BindView(R.id.tv_content)
        TextView tvContent;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
