package com.xiongyuan.lottery.mypage.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.PersonalActivity;
import com.xiongyuan.lottery.mypage.adapter.MyTeamAdapter;
import com.xiongyuan.lottery.mypage.bean.MyTeamBean;
import com.xiongyuan.lottery.mypage.bean.UserInfo;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentMyTeam extends BaseFragment {
    @BindView(R.id.my_team_list_view)
    ListView listView;
    @BindView(R.id.refreshLayout1)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv1)
    TextView tv;
    private int p=1;
    private ArrayList<MyTeamBean> list;
    private MyTeamAdapter adapter;
    private String userId;
    private String dmaxfan;//高频返采点
    private String dminfan;//低频返采点


    private Date d;
    private int page=1;
    private int pp;
    private String date = "";
    private String enddata = "";
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            Date da = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(da);
            da = calendar.getTime();
            switch (pp) {
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
            getLowers(1,jsonStr(date,enddata));
        }

    };








    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_my_team;
    }

    @Override
    public void initView() {
        //初始化RefreshLayout
        //使用本对象作为key，用来记录上一次刷新的事件，如果两次下拉刷新间隔太近，不会触发刷新方法
        refreshLayout.setLastUpdateTimeRelateObject(this);
        //设置刷新时显示的背景色
        refreshLayout.setBackgroundResource(R.color.color_333333);
        //关闭header所耗时长
        refreshLayout.setDurationToCloseHeader(1500);
        //实现刷新，加载回调
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //加载更多时触发
                p++;
                getLowers(p,jsonStr(date,enddata));
                refreshLayout.refreshComplete();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                p=1;
                getLowers(p,jsonStr(date,enddata));
                refreshLayout.refreshComplete();
            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();

        getLowers(1,jsonStr("",""));


    }

    @Override
    public void onResume() {
        super.onResume();
        getLowers(1,jsonStr(date,enddata));
    }
    @OnClick({R.id.tv1})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv1:
                getLowers(1,jsonStr(date,enddata));
                break;
        }
    }
    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new MyTeamAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), PersonalActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("id",list.get(position).getId());
                intent.putExtra("userName",list.get(position).getUsername());
                intent.putExtra("type",list.get(position).getType());
                intent.putExtra("maxRebate",list.get(position).getMaxRebate());
                intent.putExtra("minRebate",list.get(position).getMinRebate());
                intent.putExtra("date",list.get(position).getDate());
                intent.putExtra("onlineTime",list.get(position).getOnlineTime());
                intent.putExtra("frozenAmount",list.get(position).getFrozenAmount());
                intent.putExtra("usableAmount",list.get(position).getUsableAmount());

                intent.putExtra("dmax",dmaxfan);
                intent.putExtra("dmin",dminfan);



                startActivity(intent);
            }
        });
    }

    //用户列表
    public void getLowers(int pageInt,String search) {

        Call call = LotteryClient.getInstance().getUserInfo(CachePreferences.getUser().getUser_id());
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(),e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserInfo userInfo = new Gson().fromJson(body,UserInfo.class);
                dmaxfan = userInfo.getResult().getMaxRebate();
                dminfan = userInfo.getResult().getMinRebate();


            }
        });
        if (pageInt==1){
            if (list!=null){
                list.clear();
            }
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.getLowers")
                .addParams("user_id", CachePreferences.getUser().getUser_id())
                .addParams("page", pageInt+"")
                .addParams("search",search)
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
                        Log.e("==-用户列表-==", o + "---");

                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg1 = jsonObject.optString("errormsg");
                            if (errormsg1.equals("")) {
                                JSONObject result = jsonObject.optJSONObject("result");
                                JSONArray data = result.optJSONArray("data");
                                if (data.toString().equals("[]")){
//                                    if (pageInt==1) {
//                                        tv.setVisibility(View.VISIBLE);
//                                        refreshLayout.setVisibility(View.GONE);
//                                        p = 1;
//                                    }
                                }else {
                                    tv.setVisibility(View.INVISIBLE);
                                    refreshLayout.setVisibility(View.VISIBLE);
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject object = data.optJSONObject(j);
                                        String id = object.optString("id");
                                        String username = object.optString("username");
                                        String usableAmount = object.optString("usableAmount");
                                        String frozenAmount = object.optString("frozenAmount");
                                        String addTime = object.optString("addTime");
                                        String date = stampToDate(addTime);
                                        String type = object.optString("type");
                                        String maxRebate = object.optString("maxRebate");
                                        if (maxRebate.equals("null")) {
                                            maxRebate = "";
                                        }
                                        String minRebate = object.optString("minRebate");
                                        if (minRebate.equals("null")) {
                                            minRebate = "";
                                        }
                                        String onlineTime = object.optString("onlineTime");
                                        list.add(new MyTeamBean(id, username, usableAmount, frozenAmount, type, date, maxRebate, minRebate, onlineTime));
                                    }
                                    adapter.refresh(list);
                                }

                            } else {
                                ToastUtils.showToast(getActivity(), errormsg1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long lt = Long.valueOf(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String jsonStr(String date,String end) {
        if (date.equals("")) {
            return "";
        }
        JSONObject jsonObj = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object.put("mode", "betweenTime");
            object.put("field", "addTime");
            object.put("val", date+","+end);
            jsonObj.put("addTime", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj.toString();
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
        this.pp = p;
    }

}
