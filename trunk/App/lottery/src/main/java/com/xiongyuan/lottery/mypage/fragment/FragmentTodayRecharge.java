package com.xiongyuan.lottery.mypage.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.homepage.bean.GameListResult;
import com.xiongyuan.lottery.mypage.activity.BetDetailsActivity;
import com.xiongyuan.lottery.mypage.adapter.TodayRechargeAdapter;
import com.xiongyuan.lottery.mypage.bean.GameIconBean;
import com.xiongyuan.lottery.mypage.bean.TodayRechargeBean;
import com.xiongyuan.lottery.mypage.bean.TouzhuInfo;
import com.xiongyuan.lottery.mypage.bean.TouzhuResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentTodayRecharge extends BaseFragment {

    @BindView(R.id.list_today_recharge)
    ListView listView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv)
    TextView tv;
    private Call call;
    private Date d;
    private int page=1;
    private int p;
    private static int mSerial = 0;
    private int mTabPos = 0;
    private String userId;
    private String date;
    private TodayRechargeAdapter mAdapter;
    private ArrayList<TodayRechargeBean> arrayList;
    private ArrayList<TodayRechargeBean> list;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            switch (p) {
                case 0:
                    date = "";
                    page=1;
                    break;
                case 1:
                    page=1;
                    date = getNextDate(Calendar.DAY_OF_MONTH, -1);
                    break;
                case 2:
                    page=1;
                    date = getNextDate(Calendar.DAY_OF_MONTH, -7);
                    break;
                case 3:
                    page=1;
                    date = getNextDate(Calendar.MONTH, -1);
                    break;
                case 4:
                    page=1;
                    date = getNextDate(Calendar.MONTH, -3);
                    break;
            }
            refreshData(mTabPos, jsonStr(date),1);

        }

    };

    private List<GameIconBean> beanList = new ArrayList<>();
    private int[] tubiao ={R.mipmap.button_icon_gfssc03,R.mipmap.button_icon_gfssc04,R.mipmap.button_icon_gfssc01,
            R.mipmap.button_icon_gfssc02,R.mipmap.button_icon_pk10_0,R.mipmap.button_icon_pl5,R.mipmap.button_icon_pl3,
            R.mipmap.button_icon_gfssc05,R.mipmap.button_icon_fc3d,R.mipmap.button_icon_gfssc06,R.mipmap.button_icon_ssc0,
            R.mipmap.button_icon_sh11x5,R.mipmap.kuai3_3,R.mipmap.kuai3_4,R.mipmap.button_icon_ssc3,R.mipmap.button_icon_ssc2,
            R.mipmap.button_icon_shssl,R.mipmap.button_icon_ssc1,R.mipmap.button_icon_gd11x5,R.mipmap.button_icon_tj10fen,
            R.mipmap.button_icon_jx11x5,R.mipmap.button_icon_ah11x5,R.mipmap.kuai3_3,R.mipmap.button_icon_ssc2,R.mipmap.button_icon_gd10,
            R.mipmap.kuai3_2,R.mipmap.button_icon_sd11x5};

    private String idss;

    public FragmentTodayRecharge(int serial) {
        mSerial = serial;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_today_recharge;
    }

    public void sendMessage(int p) {
        Message message = handler.obtainMessage();
        message.sendToTarget();
        this.p = p;
    }

    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Override
    public void initView() {

        mAdapter = new TodayRechargeAdapter(getActivity());
        listView.setAdapter(mAdapter);
        mAdapter.setBeanList(beanList);
        arrayList = new ArrayList<>();
        list = new ArrayList<>();
        //设置页和当前页一致时加载，防止预加载
        if (mTabPos == mSerial) {
            sendMessage(p);
        }
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
                page++;
                refreshData(mTabPos, jsonStr(date),page);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                page=1;
                refreshData(mTabPos, jsonStr(date),page);

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BetDetailsActivity.class);
                intent.putExtra("list",arrayList.get(position));
                startActivity(intent);
            }
        });




    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    private void refreshData(int n, String search,int pageInt) {
        if (pageInt==1){
            if (arrayList!=null){
                arrayList.clear();
            }
        }
        call = LotteryClient.getInstance().getTouzhuji(userId, pageInt+"", search);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                TouzhuResult result = new Gson().fromJson(body, TouzhuResult.class);
                List<TouzhuInfo> ls = result.getResult().getData();
                if (ls.toString().equals("[]")) {
                    if (pageInt==1) {
                        page=1;
                        refreshLayout.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                    }
                } else {
                    refreshLayout.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < ls.size(); i++) {
                        String ids = ls.get(i).getId();
                        String issue = ls.get(i).getIssue();
                        String gameTitle = ls.get(i).getGameTitle();
                        String betAmount = ls.get(i).getBetAmount();
                        String status = ls.get(i).getStatus();
                        String winAmount = ls.get(i).getWinAmount();
                        String addTime = ls.get(i).getAddTime();
                        String date = stampToDate(addTime);

                        String typeTitle = ls.get(i).getTypeTitle();
                        String playTitle = ls.get(i).getPlayTitle();
                        String log_id = ls.get(i).getLog_id();
                        String returnPer = ls.get(i).getReturnPer();
                        String returnAmount = ls.get(i).getReturnAmount();
                        String code = ls.get(i).getCode();
                        String betCount = ls.get(i).getBetCount();
                        String betTimes = ls.get(i).getBetTimes();
                        String winCount = ls.get(i).getWinCount();
                        if (ls.get(i).getBingoCode() == null){
                            ls.get(i).setBingoCode("");
                        }
                        String bingoCode = ls.get(i).getBingoCode().toString();

                        arrayList.add(new TodayRechargeBean(ids,issue, gameTitle, betAmount, status, winAmount, date,typeTitle,playTitle,log_id
                        ,returnPer,returnAmount,code,betCount,betTimes,winCount,bingoCode));
                    }
                    Log.e("arraylist",pageInt+"  "+body);
                    getBetLogsItem(arrayList, n,pageInt);
                }
                refreshLayout.refreshComplete();

            }
        });
    }




    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();

    }

    @Override
    public void initData() {
        getdatalist();
    }
    @OnClick({R.id.tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv:
                refreshData(mTabPos, jsonStr(date),1);
                break;
        }
    }


    private void getBetLogsItem(ArrayList<TodayRechargeBean> arrayList, int n,int page) {
        switch (n) {
            case 0:
                mAdapter.refresh(arrayList);
                break;
            case 1:
                getList(arrayList, "-1",page);
                break;
            case 2:
                getList(arrayList, "0",page);
                break;
            case 3:
                getList(arrayList, "1",page);
                break;
            case 4:
                getList(arrayList, "2",page);
                break;
            case 5:
                getList(arrayList, "3",page);
                break;
        }
    }

    private void getList(ArrayList<TodayRechargeBean> arrayList, String i,int page) {
            if (list!=null){
                list.clear();
            }
        for (int j = 0; j < arrayList.size(); j++) {
            TodayRechargeBean todayRechargeBean = arrayList.get(j);
            if (todayRechargeBean.getStatus().equals(i)) {
                String ids = todayRechargeBean.getId();
                String issue = todayRechargeBean.getIssue();
                String gameTitle = todayRechargeBean.getGameTitle();
                String betAmount = todayRechargeBean.getBetAmount();
                String status = todayRechargeBean.getStatus();
                String winAmount = todayRechargeBean.getWinAmount();
                String addTime = todayRechargeBean.getAddTime();

                String typeTitle = todayRechargeBean.getTypeTitle();
                String playTitle = todayRechargeBean.getPlayTitle();
                String log_id = todayRechargeBean.getLog_id();
                String returnPer = todayRechargeBean.getReturnPer();
                String returnAmount = todayRechargeBean.getReturnAmount();
                String code = todayRechargeBean.getCode();
                String betCount = todayRechargeBean.getBetCount();
                String betTimes = todayRechargeBean.getBetTimes();
                String winCount = todayRechargeBean.getWinCount();
                String bingoCode = todayRechargeBean.getBingoCode().toString();


                list.add(new TodayRechargeBean(ids,issue, gameTitle, betAmount, status, winAmount, addTime,typeTitle,playTitle,log_id
                        ,returnPer,returnAmount,code,betCount,betTimes,winCount,bingoCode));
            }
        }
        if (list.toString().equals("[]")) {
            if (page==1) {
                tv.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.GONE);
            }
        } else {
            refreshLayout.setVisibility(View.VISIBLE);
            tv.setVisibility(View.INVISIBLE);
        }
            mAdapter.refresh(list);

        }
    /*
* 将时间戳转换为时间
*/

    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.valueOf(s);
        Date date = new Date(lt * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
    * 获取指定时间前段时间的时间戳
    * */
    public String getNextDate(int field, int i) {
        d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(field, i);
        d = calendar.getTime();
        return String.valueOf(d.getTime());
    }

    /*
    * 参数
    * */
    public static String jsonStr(String date) {
        if (date.equals("")) {
            return "";
        }
        JSONObject jsonObj = new JSONObject();
        JSONObject object = new JSONObject();
        try {
            object.put("mode", "betweenText");
            object.put("field", "addTime");
            object.put("val", date);
            jsonObj.put("addTime", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj.toString();
    }

    public void getdatalist() {
        Call call = LotteryClient.getInstance().getGameList("0");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String body) {
                beanList.clear();
                GameListResult result = new Gson().fromJson(body,GameListResult.class);

                GameIconBean g1 = new GameIconBean();
                g1.setId(result.getResult().getData().get_$1().getId());
                g1.setPid(result.getResult().getData().get_$1().getPid());
                g1.setTitle(result.getResult().getData().get_$1().getTitle());
                g1.setShortTitle(result.getResult().getData().get_$1().getShortTitle());
                g1.setIcon(tubiao[17]);
                beanList.add(g1);

                GameIconBean g38 = new GameIconBean();
                g38.setId(result.getResult().getData().get_$38().getId());
                g38.setPid(result.getResult().getData().get_$38().getPid());
                g38.setTitle(result.getResult().getData().get_$38().getTitle());
                g38.setShortTitle(result.getResult().getData().get_$38().getShortTitle());
                g38.setIcon(tubiao[9]);
                beanList.add(g38);

                GameIconBean g37 = new GameIconBean();
                g37.setId(result.getResult().getData().get_$37().getId());
                g37.setPid(result.getResult().getData().get_$37().getPid());
                g37.setTitle(result.getResult().getData().get_$37().getTitle());
                g37.setShortTitle(result.getResult().getData().get_$37().getShortTitle());
                g37.setIcon(tubiao[7]);
                beanList.add(g37);

                GameIconBean g36 = new GameIconBean();
                g36.setId(result.getResult().getData().get_$36().getId());
                g36.setPid(result.getResult().getData().get_$36().getPid());
                g36.setTitle(result.getResult().getData().get_$36().getTitle());
                g36.setShortTitle(result.getResult().getData().get_$36().getShortTitle());
                g36.setIcon(tubiao[1]);
                beanList.add(g36);

                GameIconBean g35 = new GameIconBean();
                g35.setId(result.getResult().getData().get_$35().getId());
                g35.setPid(result.getResult().getData().get_$35().getPid());
                g35.setTitle(result.getResult().getData().get_$35().getTitle());
                g35.setShortTitle(result.getResult().getData().get_$35().getShortTitle());
                g35.setIcon(tubiao[0]);
                beanList.add(g35);

                GameIconBean g34 = new GameIconBean();
                g34.setId(result.getResult().getData().get_$34().getId());
                g34.setPid(result.getResult().getData().get_$34().getPid());
                g34.setTitle(result.getResult().getData().get_$34().getTitle());
                g34.setShortTitle(result.getResult().getData().get_$34().getShortTitle());
                g34.setIcon(tubiao[3]);
                beanList.add(g34);

                GameIconBean g33 = new GameIconBean();
                g33.setId(result.getResult().getData().get_$33().getId());
                g33.setPid(result.getResult().getData().get_$33().getPid());
                g33.setTitle(result.getResult().getData().get_$33().getTitle());
                g33.setShortTitle(result.getResult().getData().get_$33().getShortTitle());
                g33.setIcon(tubiao[2]);
                beanList.add(g33);

                GameIconBean g25 = new GameIconBean();
                g25.setId(result.getResult().getData().get_$25().getId());
                g25.setPid(result.getResult().getData().get_$25().getPid());
                g25.setTitle(result.getResult().getData().get_$25().getTitle());
                g25.setShortTitle(result.getResult().getData().get_$25().getShortTitle());
                g25.setIcon(tubiao[11]);
                beanList.add(g25);

                GameIconBean g23 = new GameIconBean();
                g23.setId(result.getResult().getData().get_$23().getId());
                g23.setPid(result.getResult().getData().get_$23().getPid());
                g23.setTitle(result.getResult().getData().get_$23().getTitle());
                g23.setShortTitle(result.getResult().getData().get_$23().getShortTitle());
                beanList.add(g23);
                GameIconBean g22 = new GameIconBean();
                g22.setId(result.getResult().getData().get_$22().getId());
                g22.setPid(result.getResult().getData().get_$22().getPid());
                g22.setTitle(result.getResult().getData().get_$22().getTitle());
                g22.setShortTitle(result.getResult().getData().get_$22().getShortTitle());
                beanList.add(g22);
                GameIconBean g21 = new GameIconBean();
                g21.setId(result.getResult().getData().get_$21().getId());
                g21.setPid(result.getResult().getData().get_$21().getPid());
                g21.setTitle(result.getResult().getData().get_$21().getTitle());
                g21.setShortTitle(result.getResult().getData().get_$21().getShortTitle());
                beanList.add(g21);
                GameIconBean g20 = new GameIconBean();
                g20.setId(result.getResult().getData().get_$20().getId());
                g20.setPid(result.getResult().getData().get_$20().getPid());
                g20.setTitle(result.getResult().getData().get_$20().getTitle());
                g20.setShortTitle(result.getResult().getData().get_$20().getShortTitle());
                beanList.add(g20);
                GameIconBean g19 = new GameIconBean();
                g19.setId(result.getResult().getData().get_$19().getId());
                g19.setPid(result.getResult().getData().get_$19().getPid());
                g19.setTitle(result.getResult().getData().get_$19().getTitle());
                g19.setShortTitle(result.getResult().getData().get_$19().getShortTitle());
                beanList.add(g19);
                GameIconBean g18 = new GameIconBean();
                g18.setId(result.getResult().getData().get_$18().getId());
                g18.setPid(result.getResult().getData().get_$18().getPid());
                g18.setTitle(result.getResult().getData().get_$18().getTitle());
                g18.setShortTitle(result.getResult().getData().get_$18().getShortTitle());
                beanList.add(g18);
                GameIconBean g17 = new GameIconBean();
                g17.setId(result.getResult().getData().get_$17().getId());
                g17.setPid(result.getResult().getData().get_$17().getPid());
                g17.setTitle(result.getResult().getData().get_$17().getTitle());
                g17.setShortTitle(result.getResult().getData().get_$17().getShortTitle());
                beanList.add(g17);
//                GameIconBean g32 = new GameIconBean();
//                g32.setId(result.getResult().getData().get_$32().getId());
//                g32.setPid(result.getResult().getData().get_$32().getPid());
//                g32.setTitle(result.getResult().getData().get_$32().getTitle());
//                g32.setShortTitle(result.getResult().getData().get_$32().getShortTitle());
//                beanList.add(g32);
                GameIconBean g31 = new GameIconBean();
                g31.setId(result.getResult().getData().get_$31().getId());
                g31.setPid(result.getResult().getData().get_$31().getPid());
                g31.setTitle(result.getResult().getData().get_$31().getTitle());
                g31.setShortTitle(result.getResult().getData().get_$31().getShortTitle());
                g31.setIcon(tubiao[24]);
                beanList.add(g31);
                GameIconBean g30 = new GameIconBean();
                g30.setId(result.getResult().getData().get_$30().getId());
                g30.setPid(result.getResult().getData().get_$30().getPid());
                g30.setTitle(result.getResult().getData().get_$30().getTitle());
                g30.setShortTitle(result.getResult().getData().get_$30().getShortTitle());
                g30.setIcon(tubiao[19]);
                beanList.add(g30);

                GameIconBean g28 = new GameIconBean();
                g28.setId(result.getResult().getData().get_$28().getId());
                g28.setPid(result.getResult().getData().get_$28().getPid());
                g28.setTitle(result.getResult().getData().get_$28().getTitle());
                g28.setShortTitle(result.getResult().getData().get_$28().getShortTitle());
                g28.setIcon(tubiao[13]);
                beanList.add(g28);

                GameIconBean g27 = new GameIconBean();
                g27.setId(result.getResult().getData().get_$27().getId());
                g27.setPid(result.getResult().getData().get_$27().getPid());
                g27.setTitle(result.getResult().getData().get_$27().getTitle());
                g27.setShortTitle(result.getResult().getData().get_$27().getShortTitle());
                g27.setIcon(tubiao[12]);
                beanList.add(g27);

                GameIconBean g24 = new GameIconBean();
                g24.setId(result.getResult().getData().get_$24().getId());
                g24.setPid(result.getResult().getData().get_$24().getPid());
                g24.setTitle(result.getResult().getData().get_$24().getTitle());
                g24.setShortTitle(result.getResult().getData().get_$24().getShortTitle());
                g24.setIcon(tubiao[10]);
                beanList.add(g24);

                GameIconBean g14 = new GameIconBean();
                g14.setId(result.getResult().getData().get_$14().getId());
                g14.setPid(result.getResult().getData().get_$14().getPid());
                g14.setTitle(result.getResult().getData().get_$14().getTitle());
                g14.setShortTitle(result.getResult().getData().get_$14().getShortTitle());
                g14.setIcon(tubiao[5]);
                beanList.add(g14);

                GameIconBean g13 = new GameIconBean();
                g13.setId(result.getResult().getData().get_$13().getId());
                g13.setPid(result.getResult().getData().get_$13().getPid());
                g13.setTitle(result.getResult().getData().get_$13().getTitle());
                g13.setShortTitle(result.getResult().getData().get_$13().getShortTitle());
                g13.setIcon(tubiao[4]);
                beanList.add(g13);

                GameIconBean g12 = new GameIconBean();
                g12.setId(result.getResult().getData().get_$12().getId());
                g12.setPid(result.getResult().getData().get_$12().getPid());
                g12.setTitle(result.getResult().getData().get_$12().getTitle());
                g12.setShortTitle(result.getResult().getData().get_$12().getShortTitle());
                g12.setIcon(tubiao[8]);
                beanList.add(g12);

                GameIconBean g11 = new GameIconBean();
                g11.setId(result.getResult().getData().get_$11().getId());
                g11.setPid(result.getResult().getData().get_$11().getPid());
                g11.setTitle(result.getResult().getData().get_$11().getTitle());
                g11.setShortTitle(result.getResult().getData().get_$11().getShortTitle());
                g11.setIcon(tubiao[6]);
                beanList.add(g11);

                GameIconBean g10 = new GameIconBean();
                g10.setId(result.getResult().getData().get_$10().getId());
                g10.setPid(result.getResult().getData().get_$10().getPid());
                g10.setTitle(result.getResult().getData().get_$10().getTitle());
                g10.setShortTitle(result.getResult().getData().get_$10().getShortTitle());
                g10.setIcon(tubiao[16]);
                beanList.add(g10);

                GameIconBean g9 = new GameIconBean();
                g9.setId(result.getResult().getData().get_$9().getId());
                g9.setPid(result.getResult().getData().get_$9().getPid());
                g9.setTitle(result.getResult().getData().get_$9().getTitle());
                g9.setShortTitle(result.getResult().getData().get_$9().getShortTitle());
                g9.setIcon(tubiao[25]);
                beanList.add(g9);

                GameIconBean g8 = new GameIconBean();
                g8.setId(result.getResult().getData().get_$8().getId());
                g8.setPid(result.getResult().getData().get_$8().getPid());
                g8.setTitle(result.getResult().getData().get_$8().getTitle());
                g8.setShortTitle(result.getResult().getData().get_$8().getShortTitle());
                g8.setIcon(tubiao[26]);
                beanList.add(g8);

                GameIconBean g7 = new GameIconBean();
                g7.setId(result.getResult().getData().get_$7().getId());
                g7.setPid(result.getResult().getData().get_$7().getPid());
                g7.setTitle(result.getResult().getData().get_$7().getTitle());
                g7.setShortTitle(result.getResult().getData().get_$7().getShortTitle());
                g7.setIcon(tubiao[18]);
                beanList.add(g7);

                GameIconBean g6 = new GameIconBean();
                g6.setId(result.getResult().getData().get_$6().getId());
                g6.setPid(result.getResult().getData().get_$6().getPid());
                g6.setTitle(result.getResult().getData().get_$6().getTitle());
                g6.setShortTitle(result.getResult().getData().get_$6().getShortTitle());
                g6.setIcon(tubiao[20]);
                beanList.add(g6);

                GameIconBean g5 = new GameIconBean();
                g5.setId(result.getResult().getData().get_$5().getId());
                g5.setPid(result.getResult().getData().get_$5().getPid());
                g5.setTitle(result.getResult().getData().get_$5().getTitle());
                g5.setShortTitle(result.getResult().getData().get_$5().getShortTitle());
                g5.setIcon(tubiao[21]);
                beanList.add(g5);

                GameIconBean g2 = new GameIconBean();
                g2.setId(result.getResult().getData().get_$2().getId());
                g2.setPid(result.getResult().getData().get_$2().getPid());
                g2.setTitle(result.getResult().getData().get_$2().getTitle());
                g2.setShortTitle(result.getResult().getData().get_$2().getShortTitle());
                g2.setIcon(tubiao[15]);
                beanList.add(g2);

                GameIconBean g3 = new GameIconBean();
                g3.setId(result.getResult().getData().get_$3().getId());
                g3.setPid(result.getResult().getData().get_$3().getPid());
                g3.setTitle(result.getResult().getData().get_$3().getTitle());
                g3.setShortTitle(result.getResult().getData().get_$3().getShortTitle());
                g3.setIcon(tubiao[14]);
                beanList.add(g3);

                GameIconBean g4 = new GameIconBean();
                g4.setId(result.getResult().getData().get_$4().getId());
                g4.setPid(result.getResult().getData().get_$4().getPid());
                g4.setTitle(result.getResult().getData().get_$4().getTitle());
                g4.setShortTitle(result.getResult().getData().get_$4().getShortTitle());
                g4.setIcon(tubiao[23]);
                beanList.add(g4);

            }
        });

    }
}
