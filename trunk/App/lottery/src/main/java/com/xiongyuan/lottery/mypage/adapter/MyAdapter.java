package com.xiongyuan.lottery.mypage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-10.
 */

public class MyAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private String myPhone;
    private ArrayList<Map<String, Integer>> list=new ArrayList<>();
    private onExpandItemClickListener itemClickListener = null;

    //private int[] imagesIds = new int[]{R.mipmap.item1, R.mipmap.item2, R.mipmap.item3, R.mipmap.item4,R.mipmap.item5,R.mipmap.item6};
    //private String[] itemNames = new String[]{"我的代理", "个人信息", "今日统计","现金交易", "我的消息", "在线客服"};
    public MyAdapter(Context mContext, ArrayList<Map<String, Integer>> list) {
        this.list = list;
        this.mContext = mContext;
    }

    //刷新方法
    public void refresh(ArrayList<Map<String, Integer>> mList,String phone) {
            myPhone=phone;
            notifyDataSetInvalidated();
    }

    private final int TYPE_1 = 0;

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.rv_list, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_me_icon);
            holder.ivmeRight = (ImageView) convertView.findViewById(R.id.iv_me_right);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.tvText = (TextView) convertView.findViewById(R.id.tv_me_text);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String, Integer> map = list.get(groupPosition);
        for (Map.Entry<String, Integer> m : map.entrySet()) {
            holder.imageView.setImageResource(m.getValue());
            holder.tvContent.setText(m.getKey());
            if (m.getKey().equals("我的客服")) {
                holder.tvText.setVisibility(View.VISIBLE);
                holder.tvText.setText(myPhone);
                holder.tvText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick(v, "我的客服");
                        }
                    }
                });
            } else {
                holder.tvText.setVisibility(View.GONE);
            }
        }


        //判断isExpanded就可以控制是按下还是关闭，同时更换图片
        if (isExpanded) {
            holder.ivmeRight.setBackgroundResource(R.mipmap.buy_jczq_title_down);
        } else {
            holder.ivmeRight.setBackgroundResource(R.mipmap.buy_lottery_guide);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView imageView, ivmeRight;
        TextView tvContent, tvText;

    }

    public int getItemViewType(int groupPosition) {
        //int p = groupPosition;
        return TYPE_1;
        /*if (p == 0) {
            return TYPE_1;
        } else if (p == 1) {
            return TYPE_1;
        } else if (p == 2) {
            return TYPE_2;
        } else if (p == 3) {
            return TYPE_1;
        } else {
            return TYPE_1;
        }*/

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Tyep1ViewHolder holder1 = null;
        int type = getItemViewType(groupPosition);
        switch (type) {
            case TYPE_1:
                convertView = View.inflate(mContext, R.layout.type1, null);
                holder1 = new Tyep1ViewHolder();
                holder1.tvLeft = (TextView) convertView.findViewById(R.id.type1_tv_left);
                holder1.tvCenter = (TextView) convertView.findViewById(R.id.type1_tv_center);
                holder1.tvRight = (TextView) convertView.findViewById(R.id.type1_tv_right);
                if (list.size() == 6) {
                    if (groupPosition == 0) {
                        holder1.tvLeft.setText("代理开户");
                        holder1.tvCenter.setText("团队统计");
                        holder1.tvRight.setText("团队报表");
                        holder1.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "代理开户");
                                }
                            }
                        });
                        holder1.tvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "团队统计");
                                }
                            }
                        });
                        holder1.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "团队报表");
                                }
                            }
                        });
                    } else if (groupPosition == 1) {
                        holder1.tvLeft.setText("个人资料");
                        holder1.tvCenter.setText("帐变记录");
                        holder1.tvRight.setText("积分记录");
                        holder1.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "个人资料");
                                }
                            }
                        });
                        holder1.tvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "帐变记录");
                                }
                            }
                        });
                        holder1.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "积分记录");
                                }
                            }
                        });
                    } else if (groupPosition == 3) {
                        holder1.tvLeft.setText("购彩记录");
                        holder1.tvCenter.setText("追号记录");
                        holder1.tvRight.setText("中奖记录");
                        holder1.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "购彩记录");
                                }
                            }
                        });
                        holder1.tvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "追号记录");
                                }
                            }
                        });
                        holder1.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "中奖记录");
                                }
                            }
                        });
                    }
                }else if (list.size()==5){
                    if (groupPosition == 0) {
                        holder1.tvLeft.setText("个人资料");
                        holder1.tvCenter.setText("积分记录");
                        holder1.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "个人资料");
                                }
                            }
                        });
                        holder1.tvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "积分记录");
                                }
                            }
                        });
                    } else if (groupPosition == 2) {
                        holder1.tvLeft.setText("购彩记录");
                        holder1.tvCenter.setText("追号记录");
                        holder1.tvRight.setText("中奖记录");
                        holder1.tvLeft.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "购彩记录");
                                }
                            }
                        });
                        holder1.tvCenter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "追号记录");
                                }
                            }
                        });
                        holder1.tvRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick(v, "中奖记录");
                                }
                            }
                        });
                    }
                }

                break;
        }

        return convertView;
    }

    class Tyep1ViewHolder {
        TextView tvLeft, tvCenter, tvRight;

    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setOnExpandItemClickListener(onExpandItemClickListener listener) {
        this.itemClickListener = listener;
    }


    public interface onExpandItemClickListener {

        void onItemClick(View v, String tag);
    }
}