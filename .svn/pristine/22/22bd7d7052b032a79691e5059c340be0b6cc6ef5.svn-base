package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.mypage.bean.MyTeamBean;
import com.xiongyuan.lottery.mypage.bean.SubordinateAccountItemBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017-05-05.
 */

public class MyTeamAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<MyTeamBean> mList=new ArrayList<>();
    public MyTeamAdapter(Context context) {
        this.context = context;
    }

    //刷新方法
    public void refresh(ArrayList<MyTeamBean> list) {
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
            convertView=View.inflate(context, R.layout.adapter_my_team_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tvUserName.setText(mList.get(position).getUsername());
        viewHolder.tvUsable.setText(mList.get(position).getUsableAmount());
        viewHolder.tvMaxRebate.setText(mList.get(position).getMaxRebate());
        viewHolder.tvMinRebate.setText(mList.get(position).getMinRebate());
        if (mList.get(position).getType().equals("0")){
            viewHolder.ivType.setImageResource(R.mipmap.vip);
        }else if (mList.get(position).getType().equals("1")){
            viewHolder.ivType.setImageResource(R.mipmap.putong);
        }

        return convertView;
    }
    class ViewHolder{
        @BindView(R.id.adapter_my_team_iv_tou)
        CircleImageView ivTou;
        @BindView(R.id.adapter_my_team_iv_online)
                ImageView ivOnline;
        @BindView(R.id.adapter_my_team_iv_type)
                ImageView ivType;
        @BindView(R.id.adapter_my_team_user_name)
                TextView tvUserName;
        @BindView(R.id.adapter_my_team_tv_usable)
                TextView tvUsable;
        @BindView(R.id.adapter_my_team_tv_maxRebate)
                TextView tvMaxRebate;
        @BindView(R.id.adapter_my_team_tv_minRebate)
                TextView tvMinRebate;
        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
