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
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.mypage.activity.AddManageDetails;
import com.xiongyuan.lottery.mypage.adapter.FragmentAddManageAdapter;
import com.xiongyuan.lottery.mypage.bean.AddManageBean;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

public class FragmentAddManage extends BaseFragment {
    private LoadingDialog loadingDialog;
    @BindView(R.id.list_page_add_manage)
    ListView listView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv)
    TextView tv;
    private int p=1;
    private FragmentAddManageAdapter adapter;
    private ArrayList<AddManageBean> list;
    private long time2;
    private String userId;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            getCodes(1);
        };
    };
    public void sendMessage(){
        Message message = handler.obtainMessage();
        message.sendToTarget();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_add_manage;
    }

    @Override
    public void initView() {
        adapter=new FragmentAddManageAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),AddManageDetails.class);
                intent.putExtra("time",list.get(position).getExpireTime());
                intent.putExtra("max",list.get(position).getMaxRebate());
                intent.putExtra("min",list.get(position).getMinRebate());
                intent.putExtra("url",list.get(position).getRegUrl());
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
                getCodes(p);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                p=1;
                getCodes(p);
            }
        });
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getCodes(1);
    }
    @Override
    public void initData() {
        time2 = System.currentTimeMillis();
        list=new ArrayList<>();
    }
    //获取注册码
    private void getCodes(int pageInt) {
        if (pageInt==1){
            if (list.size()>0){
                list.clear();
            }
        }
        if (loadingDialog != null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(getActivity(), "加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "RegCode.getCodes")
                .addParams("user_id",userId)
                .addParams("isNeedUrl",pageInt+"")
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
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-获取注册码-==", o );
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                if (jsonArray.toString().equals("[]")){
                                    if (pageInt==1) {
                                        tv.setVisibility(View.VISIBLE);
                                        refreshLayout.setVisibility(View.GONE);
                                        p = 1;
                                    }
                                }else {
                                    tv.setVisibility(View.INVISIBLE);
                                    refreshLayout.setVisibility(View.VISIBLE);
                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject opt = (JSONObject) jsonArray.opt(j);
                                        String expireTime = opt.optString("expireTime");
                                        String maxRebate = opt.optString("maxRebate");
                                        String minRebate = opt.optString("minRebate");
                                        String url = opt.optString("regUrl");
                                        long longtime = Long.parseLong(expireTime) * 1000;
                                        String distanceTime = getDistanceTime(longtime, time2);
                                        list.add(new AddManageBean(distanceTime, maxRebate, minRebate, url));
                                    }
                                    adapter.refresh(list);
                                }

                            } else {
                                ToastUtils.showToast(getActivity(), errormsg);
                            }
                            refreshLayout.refreshComplete();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    @OnClick({R.id.tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv:
                getCodes(1);
                break;
        }
    }
    //计算剩余时间
    public static String getDistanceTime(long  time1 ,long time2) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff ;
        if(time1>time2) {
            diff = time1 - time2;
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
            if (day!=0){
                if (hour!=0){
                    return day+"天"+hour+"小时";
                }else{
                    return day+"天";
                }
            }else{
                return "";
            }
        }else{
            return "0天";
        }

    }
}
