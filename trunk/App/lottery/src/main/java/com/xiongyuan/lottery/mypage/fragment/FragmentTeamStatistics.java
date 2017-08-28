package com.xiongyuan.lottery.mypage.fragment;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.adapter.TeamStatisticsAdapter;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentTeamStatistics extends BaseFragment {
    @BindView(R.id.lv_team_statistics)
    ListView lvTeam;
    private ArrayList<String> mlist;
    private TeamStatisticsAdapter adapter;
    private Date d;
    private int page=1;
    private int p;
    private String date;
    private String enddata;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            Date da = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(da);
            da = calendar.getTime();
            switch (p) {
                case 0:
                    date = "";
                    enddata = "";
                    page = 1;
                    break;
                case 1:
                    page = 1;
                    date = getNextDate(Calendar.DAY_OF_MONTH, -1);
                    enddata = String.valueOf(da.getTime());
                    break;
                case 2:
                    page = 1;
                    date = getNextDate(Calendar.DAY_OF_MONTH, -7);
                    enddata = String.valueOf(da.getTime());
                    break;
                case 3:
                    page = 1;
                    date = getNextDate(Calendar.DAY_OF_MONTH, -2);
                    enddata = getNextDate(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 4:
                    page = 1;
                    enddata = getNextDate(Calendar.DAY_OF_MONTH, +1);
                    date = String.valueOf(da.getTime());
                    break;
            }
            getStatData(userId,date,enddata);
        }

    };
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_team_statistics;
    }

    @Override
    public void initView() {
        adapter=new TeamStatisticsAdapter(getActivity());
        lvTeam.setAdapter(adapter);
    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getStatData(userId,"","");
    }
    @Override
    public void initData() {
        mlist = new ArrayList<>();
    }
    //统计数据
    public void  getStatData(String userId,String start,String end){
        if (mlist != null) {
            mlist.clear();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.getStatData")
                .addParams("user_id",userId)
                .addParams("type","2")
                .addParams("startTime",start)
                .addParams("endTime",end)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(getActivity(),"网络错误");

                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-统计数据-==", o+"---");

                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")){
                                JSONObject result = jsonObject.optJSONObject("result");
                                mlist.add(result.optString("userCount"));
                                mlist.add( result.optString("onlineCount"));
                                mlist.add(result.optString("usable"));
                                mlist.add( result.optString("newCount"));
                                mlist.add( result.optString("winBet"));
                                mlist.add( result.optString("betReturn"));
                                mlist.add(result.optString("recharge"));
                                mlist.add(result.optString("cashSuc"));
                                adapter.refresh(mlist);
                            }else{
                                ToastUtils.showToast(getActivity(),errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    public String getNextDate(int field, int i) {
        d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(field, i);
        d = calendar.getTime();
        return String.valueOf(d.getTime());
    }
    public void sendMessage(int p) {
        Message message = handler.obtainMessage();
        message.sendToTarget();
        this.p = p;
    }
}
