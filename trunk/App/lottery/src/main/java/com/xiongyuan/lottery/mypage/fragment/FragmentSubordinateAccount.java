package com.xiongyuan.lottery.mypage.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.FlowDetailsActivity;
import com.xiongyuan.lottery.mypage.adapter.SubordinateAccountAdapter;
import com.xiongyuan.lottery.mypage.bean.SubordinateAccountItemBean;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class FragmentSubordinateAccount extends BaseFragment {
    @BindView(R.id.subordinate_account_list_view)
    ListView listView;
    @BindView(R.id.xiaji_refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.xiaji_tv)
    TextView tv;
    private int t=2;
    private int p=1;
    private ArrayList<SubordinateAccountItemBean> list;
    private SubordinateAccountAdapter adapter;
    private String userId;

    public void setT(int t) {
        this.t = t;
    }

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
            getAccLogs(1,jsonStr(date,enddata));
        }

    };



    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_subordinate_account;
    }

    @Override
    public void initView() {

        adapter=new SubordinateAccountAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), FlowDetailsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("id",list.get(position).getId());
                startActivity(intent);
            }
        });
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
                getAccLogs(p,jsonStr(date,enddata));
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                p=1;
                getAccLogs(p,jsonStr(date,enddata));
            }
        });


}
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getAccLogs(1,jsonStr("",""));

    }
    @Override
    public void initData() {
        list=new ArrayList<>();
    }

    //流水记录
    public void  getAccLogs(int pageInt,String search){
        if (pageInt==1){
            if (list!=null){
                list.clear();
            }
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.getAccLogs")
                .addParams("user_id",userId)
                .addParams("type", String.valueOf(t))
                .addParams("search",search)
                .addParams("page",pageInt+"")
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
                        Log.e("==-流水记录-==", o+"---");

                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")){
                                JSONObject result = jsonObject.optJSONObject("result");
                                JSONArray data = result.optJSONArray("data");
                                if (data.toString().equals("[]")){
                                    if (pageInt==1) {
                                        tv.setVisibility(View.VISIBLE);
                                        refreshLayout.setVisibility(View.GONE);
                                        p = 1;
                                    }
                                }else {
                                    tv.setVisibility(View.INVISIBLE);
                                    refreshLayout.setVisibility(View.VISIBLE);
                                    for (int j = 0; j < data.length(); j++) {
                                        JSONObject object = data.optJSONObject(j);
                                        String id = object.optString("id");
                                        String addTime = object.optString("addTime");
                                        String date = stampToDate(addTime);
                                        String week = stampToWeek(addTime);
                                        String usable = object.optString("usable");
                                        String intro = object.optString("intro");
                                        if (object.optString("intro").equals("null")) {
                                            intro = "";
                                        }
                                        String type = object.optString("type");
                                        list.add(new SubordinateAccountItemBean(id, week, date, usable, intro, type));
                                    }
                                    adapter.refresh(list);
                                }

                            }else{
                                ToastUtils.showToast(getActivity(),errormsg);
                            }
                            refreshLayout.refreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    @OnClick({R.id.xiaji_tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.xiaji_tv:
                getAccLogs(1,jsonStr(date,enddata));
                break;
        }
    }
    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");
        long lt = Long.valueOf(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
   * 将时间戳转换为星期
   */
    public static String stampToWeek(String s){
        long lt = Long.valueOf(s);
        Date dt = new Date(lt*1000);
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
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