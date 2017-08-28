package com.xiongyuan.lottery.mypage.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.DetailsActivity;
import com.xiongyuan.lottery.mypage.bean.DetailsItemBean;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentGrzl extends BaseFragment {
    @BindView(R.id.lv_grzl)
    ListView lvGrzl;
    private ArrayList<String> mlist;
    private ArrayList<DetailsItemBean> mBankList;
    private MyAdapter myAdapter;
    private String userType;
    private String userId = "";
    private  String cardNum;
    private String bankName;
    private String accountName ;
    private String bankAddress;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_grzl;
    }

    @Override
    public void initView() {
        myAdapter = new MyAdapter();
        lvGrzl.setAdapter(myAdapter);
        lvGrzl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!userId.equals("")) {
                    if (position > 0 && position < 8) {
                        if (mlist.get(position).equals("")) {
                            if (position==7){
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("mBankList",mBankList);
                                intent.putExtra("position", 7);
                                intent.putExtra("userId", userId);
                                intent.putExtras(bundle);
                                startActivityForResult(intent, 0);
                            }else{
                                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                                intent.putExtra("position", position);
                                intent.putExtra("userId", userId);
                                startActivityForResult(intent, 0);
                            }

                        }
                    }
                }
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getUserData(userId);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {

            if (data.getStringExtra("TAG").equals("pwd")) {
                ToastUtils.showToast(getActivity(), "密码已修改,请重新登录");
                Intent intent = new Intent();
                getActivity().setResult(RESULT_OK, intent);
                getActivity().finish();
            } else {
                getUserData(userId);
            }
        }
    }

    private void getUserData(String userId) {
        if (mlist != null) {
            mlist.clear();
        }
        if (mBankList!=null){
            mBankList.clear();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.getUserInfo")
                .addParams("user_id", userId)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(getActivity(), "网络错误");
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==--==", o);
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject result = jsonObject.optJSONObject("result");
                                String username = result.optString("username");
                                String tier = result.optString("tier");
                                String email = result.optString("email");
                                if (email.equals("null")) {
                                    email = "";
                                }
                                String mobile = result.optString("mobile");
                                if (mobile.equals("null")) {
                                    mobile = "";
                                }
                                String qq = result.optString("qq");
                                if (qq.equals("null")) {
                                    qq = "";
                                }

                                String addTime = result.optString("addTime");
                                String time = getTime(addTime);
                                String type = result.optString("type");
                                if (type.equals("0")) {
                                    userType = "代理";
                                } else if (type.equals("1")) {
                                    userType = "会员";
                                }
                                String usableAmount = result.optString("usableAmount");
                                String frozenAmount = result.optString("frozenAmount");
                                DecimalFormat df = new DecimalFormat("#.###");
                                String balance = df.format(result.optDouble("balance"));
                                String dataIntegrity = result.optString("dataIntegrity");
                                JSONArray bankInfo = result.optJSONArray("bankInfo");
                                if (bankInfo!=null){
                                    for (int j=0;j<bankInfo.length();j++){
                                        JSONObject object=bankInfo.getJSONObject(j);
                                        String id = object.optString("id");
                                        cardNum = object.optString("cardNum");
                                        bankName = object.optString("bankName");
                                        accountName = object.optString("accountName");
                                        bankAddress = object.optString("bankAddress");
                                        mBankList.add(new DetailsItemBean(id,accountName,cardNum,bankAddress,bankName));
                                    }

                                }
                                mlist.add(username);
                                mlist.add(email);
                                mlist.add("");
                                mlist.add(mobile);
                                mlist.add("");
                                mlist.add(qq);
                                mlist.add("");
                                mlist.add("");
                                mlist.add(balance);
                                mlist.add(usableAmount);
                                mlist.add(frozenAmount);
                                mlist.add(tier);
                                mlist.add(time);
                                mlist.add(userType);
                                mlist.add(dataIntegrity);
                                myAdapter.refresh(mlist);
                            } else {
                                ToastUtils.showToast(getActivity(), errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void initData() {
        mlist = new ArrayList<>();
        mBankList=new ArrayList<>();
    }

    public String getTime(String t) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        long l = Long.valueOf(t);
        Date curDate = new Date(l * 1000);
        return formatter.format(curDate);
    }


    class MyAdapter extends BaseAdapter {
        List<String> mArcLists = new ArrayList<>();
        String[] str = {"用户名", "邮箱", "登录密码", "手机号码", "交易密码", "QQ", "绑定银行卡", "银行卡详情", "帐户总余额", "可用余额", "冻结余额", "用户等级", "注册时间", "会员类型", "资料完整度"};

        @Override
        public int getCount() {
            return str.length;
        }

        @Override
        public Object getItem(int position) {
            return str[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void refresh(List<String> mList) {
            if (mList != null && mList.size() >= 0) {
                mArcLists.clear();
                mArcLists.addAll(mList);
                notifyDataSetChanged();
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.fragment_page_grzl_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(str[position]);
            if (position > 0 && position < 8) {
                viewHolder.image.setVisibility(View.VISIBLE);
            } else {
                viewHolder.image.setVisibility(View.GONE);
            }
            if (mArcLists.size() == 15) {
                viewHolder.content.setText(mArcLists.get(position));
                if (position == 14) {
                    viewHolder.content.setTextColor(getResources().getColor(R.color.colorTabbar));
                    viewHolder.content.setText(mArcLists.get(14).concat("%"));
                }
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_grzl_title)
            TextView title;
            @BindView(R.id.tv_grzl_content)
            TextView content;
            @BindView(R.id.iv_grzl_right)
            ImageView image;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}