package com.xiongyuan.lottery.mypage.fragment;


import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.adapter.VipAdapter;
import com.xiongyuan.lottery.secondpage.bean.JfrecodeInfo;
import com.xiongyuan.lottery.secondpage.bean.JfrecodeResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentVip extends BaseFragment {
    @BindView(R.id.vip_lv)
    ListView vipLv;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;

    private int pageNo = 1;

    private ArrayList<JfrecodeInfo> onlineSaleBeanList;

    private VipAdapter adapter;


    private int pp;
    private String date = "";
    private String enddata = "";
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            switch (pp) {
                case 0:
                    pageNo = 1;
                    doGetDatas(pageNo,jsonStr(date,enddata));
                    break;
            }

        }

    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_vip;
    }

    @Override
    public void initView() {
        adapter = new VipAdapter(getContext());
        vipLv.setAdapter(adapter);

        doGetDatas(pageNo,jsonStr(date,enddata));
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
                pageNo++;
                doGetDatas(pageNo,jsonStr(date,enddata));
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                pageNo=1;
                doGetDatas(pageNo,jsonStr(date,enddata));

            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上

    }

    @Override
    public void initData() {
    }

    public void doGetDatas(int pagenos,String search) {
        onlineSaleBeanList = new ArrayList<>();
        Call call = LotteryClient.getInstance().getJfjl(search,"1",pagenos+"");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                JfrecodeResult result = new Gson().fromJson(body, JfrecodeResult.class);
                List<JfrecodeInfo> data = result.getResult().getData();

                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        JfrecodeInfo jf = new JfrecodeInfo();
                        jf.setAddTime(stampToDate(data.get(i).getAddTime()));

                        String s = "";
                        if (data.get(i).getType().equals("bet")){
                            s = "消费赠送";
                        }
                        if (data.get(i).getType().equals("activConsume")){
                            s = "参与活动消费";
                        }
                        if (data.get(i).getType().equals("activGet")){
                            s = "参与活动所得";
                        }
                        if (data.get(i).getType().equals("adminOpe")){
                            s = "管理员操作";
                        }
                        jf.setType(s);
                        jf.setUsable(data.get(i).getUsable());
                        jf.setUsableAmount(data.get(i).getUsableAmount());
                        onlineSaleBeanList.add(jf);
                    }

                    if (pageNo == 1){
                        adapter.setOnlineSaleBeanList(onlineSaleBeanList);
                    }else {
                        adapter.refust(onlineSaleBeanList);
                    }
                    //加载数据成功，增加页数
                    pageNo++;

                } else {
                    if(pageNo == 1) {
                        adapter.setOnlineSaleBeanList(onlineSaleBeanList);
                    }
                        ToastUtils.showToast(getContext(), "没有更多数据！");
                }
                refreshLayout.refreshComplete();
                }

        });

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

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.valueOf(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    public void sendMessage(int pk ,String start, String end) {
        date = start;
        enddata = end;
        Message message = handler.obtainMessage();
        message.sendToTarget();
        this.pp = pk;
    }
}
