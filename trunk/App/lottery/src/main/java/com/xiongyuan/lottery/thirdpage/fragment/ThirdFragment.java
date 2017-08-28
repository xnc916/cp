package com.xiongyuan.lottery.thirdpage.fragment;

import android.content.Intent;
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
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.homepage.bean.GameListResult;
import com.xiongyuan.lottery.homepage.bean.Gamelist3Bean;
import com.xiongyuan.lottery.thirdpage.activity.LotteryDetailsActivity;
import com.xiongyuan.lottery.thirdpage.adapter.MyListViewAdapter;
import com.xiongyuan.lottery.thirdpage.bean.ArcList;
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
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Response;

public class ThirdFragment extends BaseFragment {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.list_view_third)
    ListView listview;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv)
    TextView tv;
    private int p=1;
    private LoadingDialog loadingDialog;

    private TreeMap<String ,ArrayList<ArcList>> map=new TreeMap<>();
    private ArrayList<String> arrayList=new ArrayList<>();
    private  MyListViewAdapter adapter;
    private TreeMap<String, String> maps = new TreeMap<>();
    private ArrayList<Gamelist3Bean> beanList = new ArrayList<>();

    private ArrayList<String> numb;
    private int[] tubiao ={R.mipmap.button_icon_gfssc03,R.mipmap.button_icon_gfssc04,R.mipmap.button_icon_gfssc01,
            R.mipmap.button_icon_gfssc02,R.mipmap.button_icon_pk10_0,R.mipmap.button_icon_pl5,R.mipmap.button_icon_pl3,
            R.mipmap.button_icon_gfssc05,R.mipmap.button_icon_fc3d,R.mipmap.button_icon_gfssc06,R.mipmap.button_icon_ssc0,
            R.mipmap.button_icon_sh11x5,R.mipmap.kuai3_3,R.mipmap.kuai3_4,R.mipmap.button_icon_ssc3,R.mipmap.button_icon_ssc2,
            R.mipmap.button_icon_shssl,R.mipmap.button_icon_ssc1,R.mipmap.button_icon_gd11x5,R.mipmap.button_icon_tj10fen,
            R.mipmap.button_icon_jx11x5,R.mipmap.button_icon_ah11x5,R.mipmap.kuai3_3,R.mipmap.button_icon_ssc2,R.mipmap.button_icon_gd10,
            R.mipmap.kuai3_2,R.mipmap.button_icon_sd11x5};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_third;
    }

    @Override
    public void initView() {
        titleName.setText("开奖信息");
        adapter=new MyListViewAdapter(getActivity());
        listview.setAdapter(adapter);

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
                getdatalist();

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //刷新时触发
                p = 1;
                getdatalist();

            }
        });

    }
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        Log.e("---","---"+event);
    }
    @Override
    public void initData() {
        getdatalist();
       // getCodeHistory();
    }
    @OnClick({R.id.tv})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv:
                getdatalist();
                break;
        }
    }
    public void getdatalist() {
        Call call = LotteryClient.getInstance().getGameList("0");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(),"没网了吧！");

            }

            @Override
            public void onResponseUI(Call call, String body) {
                beanList.clear();
                GameListResult result = new Gson().fromJson(body,GameListResult.class);

                Gamelist3Bean g1 = new Gamelist3Bean();
                g1.setId(result.getResult().getData().get_$1().getId());
                g1.setPid(result.getResult().getData().get_$1().getPid());
                g1.setTitle(result.getResult().getData().get_$1().getTitle());
                g1.setShortTitle(result.getResult().getData().get_$1().getShortTitle());
                beanList.add(g1);
                Gamelist3Bean g38 = new Gamelist3Bean();
                g38.setId(result.getResult().getData().get_$38().getId());
                g38.setPid(result.getResult().getData().get_$38().getPid());
                g38.setTitle(result.getResult().getData().get_$38().getTitle());
                g38.setShortTitle(result.getResult().getData().get_$38().getShortTitle());
                beanList.add(g38);
                Gamelist3Bean g37 = new Gamelist3Bean();
                g37.setId(result.getResult().getData().get_$37().getId());
                g37.setPid(result.getResult().getData().get_$37().getPid());
                g37.setTitle(result.getResult().getData().get_$37().getTitle());
                g37.setShortTitle(result.getResult().getData().get_$37().getShortTitle());
                beanList.add(g37);
                Gamelist3Bean g36 = new Gamelist3Bean();
                g36.setId(result.getResult().getData().get_$36().getId());
                g36.setPid(result.getResult().getData().get_$36().getPid());
                g36.setTitle(result.getResult().getData().get_$36().getTitle());
                g36.setShortTitle(result.getResult().getData().get_$36().getShortTitle());
                beanList.add(g36);
                Gamelist3Bean g35 = new Gamelist3Bean();
                g35.setId(result.getResult().getData().get_$35().getId());
                g35.setPid(result.getResult().getData().get_$35().getPid());
                g35.setTitle(result.getResult().getData().get_$35().getTitle());
                g35.setShortTitle(result.getResult().getData().get_$35().getShortTitle());
                beanList.add(g35);
                Gamelist3Bean g34 = new Gamelist3Bean();
                g34.setId(result.getResult().getData().get_$34().getId());
                g34.setPid(result.getResult().getData().get_$34().getPid());
                g34.setTitle(result.getResult().getData().get_$34().getTitle());
                g34.setShortTitle(result.getResult().getData().get_$34().getShortTitle());
                beanList.add(g34);
                Gamelist3Bean g33 = new Gamelist3Bean();
                g33.setId(result.getResult().getData().get_$33().getId());
                g33.setPid(result.getResult().getData().get_$33().getPid());
                g33.setTitle(result.getResult().getData().get_$33().getTitle());
                g33.setShortTitle(result.getResult().getData().get_$33().getShortTitle());
                beanList.add(g33);
                Gamelist3Bean g25 = new Gamelist3Bean();
                g25.setId(result.getResult().getData().get_$25().getId());
                g25.setPid(result.getResult().getData().get_$25().getPid());
                g25.setTitle(result.getResult().getData().get_$25().getTitle());
                g25.setShortTitle(result.getResult().getData().get_$25().getShortTitle());
                beanList.add(g25);
                Gamelist3Bean g23 = new Gamelist3Bean();
                g23.setId(result.getResult().getData().get_$23().getId());
                g23.setPid(result.getResult().getData().get_$23().getPid());
                g23.setTitle(result.getResult().getData().get_$23().getTitle());
                g23.setShortTitle(result.getResult().getData().get_$23().getShortTitle());
                beanList.add(g23);
                Gamelist3Bean g22 = new Gamelist3Bean();
                g22.setId(result.getResult().getData().get_$22().getId());
                g22.setPid(result.getResult().getData().get_$22().getPid());
                g22.setTitle(result.getResult().getData().get_$22().getTitle());
                g22.setShortTitle(result.getResult().getData().get_$22().getShortTitle());
                beanList.add(g22);
                Gamelist3Bean g21 = new Gamelist3Bean();
                g21.setId(result.getResult().getData().get_$21().getId());
                g21.setPid(result.getResult().getData().get_$21().getPid());
                g21.setTitle(result.getResult().getData().get_$21().getTitle());
                g21.setShortTitle(result.getResult().getData().get_$21().getShortTitle());
                beanList.add(g21);
                Gamelist3Bean g20 = new Gamelist3Bean();
                g20.setId(result.getResult().getData().get_$20().getId());
                g20.setPid(result.getResult().getData().get_$20().getPid());
                g20.setTitle(result.getResult().getData().get_$20().getTitle());
                g20.setShortTitle(result.getResult().getData().get_$20().getShortTitle());
                beanList.add(g20);
                Gamelist3Bean g19 = new Gamelist3Bean();
                g19.setId(result.getResult().getData().get_$19().getId());
                g19.setPid(result.getResult().getData().get_$19().getPid());
                g19.setTitle(result.getResult().getData().get_$19().getTitle());
                g19.setShortTitle(result.getResult().getData().get_$19().getShortTitle());
                beanList.add(g19);
                Gamelist3Bean g18 = new Gamelist3Bean();
                g18.setId(result.getResult().getData().get_$18().getId());
                g18.setPid(result.getResult().getData().get_$18().getPid());
                g18.setTitle(result.getResult().getData().get_$18().getTitle());
                g18.setShortTitle(result.getResult().getData().get_$18().getShortTitle());
                beanList.add(g18);
                Gamelist3Bean g17 = new Gamelist3Bean();
                g17.setId(result.getResult().getData().get_$17().getId());
                g17.setPid(result.getResult().getData().get_$17().getPid());
                g17.setTitle(result.getResult().getData().get_$17().getTitle());
                g17.setShortTitle(result.getResult().getData().get_$17().getShortTitle());
                beanList.add(g17);
//                Gamelist3Bean g32 = new Gamelist3Bean();
//                g32.setId(result.getResult().getData().get_$32().getId());
//                g32.setPid(result.getResult().getData().get_$32().getPid());
//                g32.setTitle(result.getResult().getData().get_$32().getTitle());
//                g32.setShortTitle(result.getResult().getData().get_$32().getShortTitle());
//                beanList.add(g32);
                Gamelist3Bean g31 = new Gamelist3Bean();
                g31.setId(result.getResult().getData().get_$31().getId());
                g31.setPid(result.getResult().getData().get_$31().getPid());
                g31.setTitle(result.getResult().getData().get_$31().getTitle());
                g31.setShortTitle(result.getResult().getData().get_$31().getShortTitle());
                beanList.add(g31);
                Gamelist3Bean g30 = new Gamelist3Bean();
                g30.setId(result.getResult().getData().get_$30().getId());
                g30.setPid(result.getResult().getData().get_$30().getPid());
                g30.setTitle(result.getResult().getData().get_$30().getTitle());
                g30.setShortTitle(result.getResult().getData().get_$30().getShortTitle());
                beanList.add(g30);
                Gamelist3Bean g28 = new Gamelist3Bean();
                g28.setId(result.getResult().getData().get_$28().getId());
                g28.setPid(result.getResult().getData().get_$28().getPid());
                g28.setTitle(result.getResult().getData().get_$28().getTitle());
                g28.setShortTitle(result.getResult().getData().get_$28().getShortTitle());
                beanList.add(g28);
                Gamelist3Bean g27 = new Gamelist3Bean();
                g27.setId(result.getResult().getData().get_$27().getId());
                g27.setPid(result.getResult().getData().get_$27().getPid());
                g27.setTitle(result.getResult().getData().get_$27().getTitle());
                g27.setShortTitle(result.getResult().getData().get_$27().getShortTitle());
                beanList.add(g27);
                Gamelist3Bean g24 = new Gamelist3Bean();
                g24.setId(result.getResult().getData().get_$24().getId());
                g24.setPid(result.getResult().getData().get_$24().getPid());
                g24.setTitle(result.getResult().getData().get_$24().getTitle());
                g24.setShortTitle(result.getResult().getData().get_$24().getShortTitle());
                beanList.add(g24);
                Gamelist3Bean g14 = new Gamelist3Bean();
                g14.setId(result.getResult().getData().get_$14().getId());
                g14.setPid(result.getResult().getData().get_$14().getPid());
                g14.setTitle(result.getResult().getData().get_$14().getTitle());
                g14.setShortTitle(result.getResult().getData().get_$14().getShortTitle());
                beanList.add(g14);
                Gamelist3Bean g13 = new Gamelist3Bean();
                g13.setId(result.getResult().getData().get_$13().getId());
                g13.setPid(result.getResult().getData().get_$13().getPid());
                g13.setTitle(result.getResult().getData().get_$13().getTitle());
                g13.setShortTitle(result.getResult().getData().get_$13().getShortTitle());
                beanList.add(g13);
                Gamelist3Bean g12 = new Gamelist3Bean();
                g12.setId(result.getResult().getData().get_$12().getId());
                g12.setPid(result.getResult().getData().get_$12().getPid());
                g12.setTitle(result.getResult().getData().get_$12().getTitle());
                g12.setShortTitle(result.getResult().getData().get_$12().getShortTitle());
                beanList.add(g12);
                Gamelist3Bean g11 = new Gamelist3Bean();
                g11.setId(result.getResult().getData().get_$11().getId());
                g11.setPid(result.getResult().getData().get_$11().getPid());
                g11.setTitle(result.getResult().getData().get_$11().getTitle());
                g11.setShortTitle(result.getResult().getData().get_$11().getShortTitle());
                beanList.add(g11);
                Gamelist3Bean g10 = new Gamelist3Bean();
                g10.setId(result.getResult().getData().get_$10().getId());
                g10.setPid(result.getResult().getData().get_$10().getPid());
                g10.setTitle(result.getResult().getData().get_$10().getTitle());
                g10.setShortTitle(result.getResult().getData().get_$10().getShortTitle());
                beanList.add(g10);
                Gamelist3Bean g9 = new Gamelist3Bean();
                g9.setId(result.getResult().getData().get_$9().getId());
                g9.setPid(result.getResult().getData().get_$9().getPid());
                g9.setTitle(result.getResult().getData().get_$9().getTitle());
                g9.setShortTitle(result.getResult().getData().get_$9().getShortTitle());
                beanList.add(g9);
                Gamelist3Bean g8 = new Gamelist3Bean();
                g8.setId(result.getResult().getData().get_$8().getId());
                g8.setPid(result.getResult().getData().get_$8().getPid());
                g8.setTitle(result.getResult().getData().get_$8().getTitle());
                g8.setShortTitle(result.getResult().getData().get_$8().getShortTitle());
                beanList.add(g8);
                Gamelist3Bean g7 = new Gamelist3Bean();
                g7.setId(result.getResult().getData().get_$7().getId());
                g7.setPid(result.getResult().getData().get_$7().getPid());
                g7.setTitle(result.getResult().getData().get_$7().getTitle());
                g7.setShortTitle(result.getResult().getData().get_$7().getShortTitle());
                beanList.add(g7);
                Gamelist3Bean g6 = new Gamelist3Bean();
                g6.setId(result.getResult().getData().get_$6().getId());
                g6.setPid(result.getResult().getData().get_$6().getPid());
                g6.setTitle(result.getResult().getData().get_$6().getTitle());
                g6.setShortTitle(result.getResult().getData().get_$6().getShortTitle());
                beanList.add(g6);
                Gamelist3Bean g5 = new Gamelist3Bean();
                g5.setId(result.getResult().getData().get_$5().getId());
                g5.setPid(result.getResult().getData().get_$5().getPid());
                g5.setTitle(result.getResult().getData().get_$5().getTitle());
                g5.setShortTitle(result.getResult().getData().get_$5().getShortTitle());
                beanList.add(g5);
                Gamelist3Bean g2 = new Gamelist3Bean();
                g2.setId(result.getResult().getData().get_$2().getId());
                g2.setPid(result.getResult().getData().get_$2().getPid());
                g2.setTitle(result.getResult().getData().get_$2().getTitle());
                g2.setShortTitle(result.getResult().getData().get_$2().getShortTitle());
                beanList.add(g2);
                Gamelist3Bean g3 = new Gamelist3Bean();
                g3.setId(result.getResult().getData().get_$3().getId());
                g3.setPid(result.getResult().getData().get_$3().getPid());
                g3.setTitle(result.getResult().getData().get_$3().getTitle());
                g3.setShortTitle(result.getResult().getData().get_$3().getShortTitle());
                beanList.add(g3);
                Gamelist3Bean g4 = new Gamelist3Bean();
                g4.setId(result.getResult().getData().get_$4().getId());
                g4.setPid(result.getResult().getData().get_$4().getPid());
                g4.setTitle(result.getResult().getData().get_$4().getTitle());
                g4.setShortTitle(result.getResult().getData().get_$4().getShortTitle());
                beanList.add(g4);


                maps.clear();
                for (int i = 0; i < beanList.size(); i++){
                    if (!beanList.get(i).getPid().equals("0")){
                        maps.put(beanList.get(i).getId(),beanList.get(i).getTitle());
                    }
                }
                getCodeHistory();
            }
        });

    }
    //取得历史开奖号码
    public void  getCodeHistory(){
        numb = new ArrayList<>();
        String keys = "";
        Set<String> set = maps.keySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()){
            String key = (String) iter.next();
            numb.add(key);
        }
        ArrayList<String> xiaonum = new ArrayList<>();

            for (int i = 0; i < numb.size(); i++) {
                xiaonum.add(numb.get(i));
                keys = keys +numb.get(i)+",";

            }





        String substring = keys.substring(0, keys.length() - 1);
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(getActivity(),"加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Games.getCodeHistory")
                .addParams("game_id",substring)
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
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-取得历史开奖号码-==", o+"---");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")){

                                JSONObject result = jsonObject.optJSONObject("result");

                                ArrayList<ArcList> list8=new ArrayList<>();

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("8")) {
                                        JSONArray jsonArray8 = result.optJSONArray("8");
                                        for (int j = 0; j < jsonArray8.length(); j++) {
                                            JSONObject object8 = jsonArray8.optJSONObject(j);
                                            String endTime = object8.optString("endTime");
                                            String code = object8.optString("code");
                                            String[] strings = convertStrToArray(code);
                                            String issue = object8.optString("issue");
                                            String name = maps.get("8");
                                            int imgd = tubiao[26];
                                            list8.add(new ArcList(issue, stampToDate(endTime), strings.length, strings, name, imgd));
                                        }
                                        map.put("8", list8);
                                    }
                                }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("9")) {
                                        ArrayList<ArcList> list9=new ArrayList<>();
                                        JSONArray jsonArray9 = result.optJSONArray("9");
                                        for (int j=0;j<jsonArray9.length();j++){
                                            JSONObject object9 = jsonArray9.optJSONObject(j);
                                            String endTime = object9.optString("endTime");
                                            String code = object9.optString("code");
                                            String[] strings = convertStrToArray(code);
                                            String issue = object9.optString("issue");
                                            String name = maps.get("9");
                                            int imgd = tubiao[25];
                                            list9.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                        }
                                        map.put("9",list9);
                                    }
                                }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("31")) {
                                ArrayList<ArcList> list31=new ArrayList<>();
                                JSONArray jsonArray31 = result.optJSONArray("31");
                                for (int j=0;j<jsonArray31.length();j++){
                                    JSONObject object31 = jsonArray31.optJSONObject(j);
                                    String endTime = object31.optString("endTime");
                                    String code = object31.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object31.optString("issue");
                                    String name = maps.get("31");
                                    int imgd = tubiao[24];
                                    list31.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("31",list31);
                                    }
                                }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("4")) {
                                ArrayList<ArcList> list4=new ArrayList<>();
                                JSONArray jsonArray4 = result.optJSONArray("4");
                                for (int j=0;j<jsonArray4.length();j++){
                                    JSONObject object4 = jsonArray4.optJSONObject(j);
                                    String endTime = object4.optString("endTime");
                                    String code = object4.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object4.optString("issue");
                                    String name = maps.get("4");
                                    int imgd = tubiao[23];
                                    list4.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("4",list4);
                            }
                        }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("32")) {
                                ArrayList<ArcList> list32=new ArrayList<>();
                                JSONArray jsonArray32 = result.optJSONArray("32");
                                for (int j=0;j<jsonArray32.length();j++){
                                    JSONObject object32 = jsonArray32.optJSONObject(j);
                                    String endTime = object32.optString("endTime");
                                    String code = object32.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object32.optString("issue");
                                    String name = maps.get("32");
                                    int imgd = tubiao[22];
                                    list32.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("32",list32);
                                    }
                                }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("5")) {
                                ArrayList<ArcList> list5=new ArrayList<>();
                                JSONArray jsonArray5 = result.optJSONArray("5");
                                for (int j=0;j<jsonArray5.length();j++){
                                    JSONObject object5 = jsonArray5.optJSONObject(j);
                                    String endTime = object5.optString("endTime");
                                    String code = object5.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object5.optString("issue");
                                    String name = maps.get("5");
                                    int imgd = tubiao[21];
                                    list5.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("5",list5);
                            }
                        }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("6")) {
                                ArrayList<ArcList> list6=new ArrayList<>();
                                JSONArray jsonArray6 = result.optJSONArray("6");
                                for (int j=0;j<jsonArray6.length();j++){
                                    JSONObject object6 = jsonArray6.optJSONObject(j);
                                    String endTime = object6.optString("endTime");
                                    String code = object6.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object6.optString("issue");
                                    String name = maps.get("6");
                                    int imgd = tubiao[20];
                                    list6.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("6",list6);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("30")) {
                                ArrayList<ArcList> list30=new ArrayList<>();
                                JSONArray jsonArray30 = result.optJSONArray("30");
                                for (int j=0;j<jsonArray30.length();j++){
                                    JSONObject object30 = jsonArray30.optJSONObject(j);
                                    String endTime = object30.optString("endTime");
                                    String code = object30.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object30.optString("issue");
                                    String name = maps.get("30");
                                    int imgd = tubiao[19];
                                    list30.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("30",list30);
                            }
                        }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("7")) {
                                ArrayList<ArcList> list7=new ArrayList<>();
                                JSONArray jsonArray7 = result.optJSONArray("7");
                                for (int j=0;j<jsonArray7.length();j++){
                                    JSONObject object7 = jsonArray7.optJSONObject(j);
                                    String endTime = object7.optString("endTime");
                                    String code = object7.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object7.optString("issue");
                                    String name = maps.get("7");
                                    int imgd = tubiao[18];
                                    list7.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("7",list7);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("1")) {
                                ArrayList<ArcList> list1=new ArrayList<>();
                                JSONArray jsonArray1 = result.optJSONArray("1");
                                for (int j=0;j<jsonArray1.length();j++){
                                    JSONObject object1 = jsonArray1.optJSONObject(j);
                                    String endTime = object1.optString("endTime");
                                    String code = object1.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object1.optString("issue");
                                    String name = maps.get("1");
                                    int imgd = tubiao[17];
                                    list1.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("1",list1);
                                            }
                                        }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("10")) {
                                ArrayList<ArcList> list10=new ArrayList<>();
                                JSONArray jsonArray10 = result.optJSONArray("10");
                                for (int j=0;j<jsonArray10.length();j++){
                                    JSONObject object10 = jsonArray10.optJSONObject(j);
                                    String endTime = object10.optString("endTime");
                                    String code = object10.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object10.optString("issue");
                                    String name = maps.get("10");
                                    int imgd = tubiao[16];
                                    list10.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("10",list10);
                            }
                        }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("2")) {
                                ArrayList<ArcList> list2=new ArrayList<>();
                                JSONArray jsonArray2 = result.optJSONArray("2");
                                for (int j=0;j<jsonArray2.length();j++){
                                    JSONObject object2 = jsonArray2.optJSONObject(j);
                                    String endTime = object2.optString("endTime");
                                    String code = object2.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object2.optString("issue");
                                    String name = maps.get("2");
                                    int imgd = tubiao[15];
                                    list2.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("2",list2);
                                    }
                                }
                                        for (int p = 0;p < xiaonum.size(); p++) {
                                            if (xiaonum.get(p).equals("3")) {
                                ArrayList<ArcList> list3=new ArrayList<>();
                                JSONArray jsonArray3 = result.optJSONArray("3");
                                for (int j=0;j<jsonArray3.length();j++){
                                    JSONObject object3 = jsonArray3.optJSONObject(j);
                                    String endTime = object3.optString("endTime");
                                    String code = object3.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object3.optString("issue");
                                    String name = maps.get("3");
                                    int imgd = tubiao[14];
                                    list3.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("3",list3);
                                            }
                                        }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("28")) {
                                ArrayList<ArcList> list28=new ArrayList<>();
                                JSONArray jsonArray28 = result.optJSONArray("28");
                                for (int j=0;j<jsonArray28.length();j++){
                                    JSONObject object28 = jsonArray28.optJSONObject(j);
                                    String endTime = object28.optString("endTime");
                                    String code = object28.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object28.optString("issue");
                                    String name = maps.get("28");
                                    int imgd = tubiao[13];
                                    list28.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("28",list28);
                                    }
                                }

                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("27")) {
                                ArrayList<ArcList> list27=new ArrayList<>();
                                JSONArray jsonArray27 = result.optJSONArray("27");
                                for (int j=0;j<jsonArray27.length();j++){
                                    JSONObject object27 = jsonArray27.optJSONObject(j);
                                    String endTime = object27.optString("endTime");
                                    String code = object27.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object27.optString("issue");
                                    String name = maps.get("27");
                                    int imgd = tubiao[12];
                                    list27.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("27",list27);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("25")) {
                                ArrayList<ArcList> list25=new ArrayList<>();
                                JSONArray jsonArray25 = result.optJSONArray("25");
                                for (int j=0;j<jsonArray25.length();j++){
                                    JSONObject object25 = jsonArray25.optJSONObject(j);
                                    String endTime = object25.optString("endTime");
                                    String code = object25.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object25.optString("issue");
                                    String name = maps.get("25");
                                    int imgd = tubiao[11];
                                    list25.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("25",list25);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("24")) {
                                ArrayList<ArcList> list24=new ArrayList<>();
                                JSONArray jsonArray24 = result.optJSONArray("24");
                                for (int j=0;j<jsonArray24.length();j++){
                                    JSONObject object24 = jsonArray24.optJSONObject(j);
                                    String endTime = object24.optString("endTime");
                                    String code = object24.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object24.optString("issue");
                                    String name = maps.get("24");
                                    int imgd = tubiao[10];
                                    list24.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("24",list24);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("38")) {
                                ArrayList<ArcList> list38=new ArrayList<>();
                                JSONArray jsonArray38 = result.optJSONArray("38");
                                for (int j=0;j<jsonArray38.length();j++){
                                    JSONObject object38 = jsonArray38.optJSONObject(j);
                                    String endTime = object38.optString("endTime");
                                    String code = object38.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object38.optString("issue");
                                    String name = maps.get("38");
                                    int imgd = tubiao[9];
                                    list38.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("38",list38);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("12")) {
                                ArrayList<ArcList> list12=new ArrayList<>();
                                JSONArray jsonArray12 = result.optJSONArray("12");
                                for (int j=0;j<jsonArray12.length();j++){
                                    JSONObject object12 = jsonArray12.optJSONObject(j);
                                    String endTime = object12.optString("endTime");
                                    String code = object12.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object12.optString("issue");
                                    String name = maps.get("12");
                                    int imgd = tubiao[8];
                                    String s = stampToDate(endTime);

                                    list12.add(new ArcList(issue,s,strings.length,strings,name,imgd));
                                }
                                map.put("12",list12);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("37")) {
                                ArrayList<ArcList> list37=new ArrayList<>();
                                JSONArray jsonArray37 = result.optJSONArray("37");
                                for (int j=0;j<jsonArray37.length();j++){
                                    JSONObject object37 = jsonArray37.optJSONObject(j);
                                    String endTime = object37.optString("endTime");
                                    String code = object37.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object37.optString("issue");
                                    String name = maps.get("37");
                                    int imgd = tubiao[7];
                                    list37.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("37",list37);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("11")) {
                                ArrayList<ArcList> list11=new ArrayList<>();
                                JSONArray jsonArray11 = result.optJSONArray("11");
                                for (int j=0;j<jsonArray11.length();j++){
                                    JSONObject object11 = jsonArray11.optJSONObject(j);
                                    String endTime = object11.optString("endTime");
                                    String code = object11.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object11.optString("issue");
                                    String name = maps.get("11");
                                    int imgd = tubiao[6];

                                    list11.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("11",list11);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("14")) {
                                ArrayList<ArcList> list14=new ArrayList<>();
                                JSONArray jsonArray14 = result.optJSONArray("14");
                                for (int j=0;j<jsonArray14.length();j++){
                                    JSONObject object14 = jsonArray14.optJSONObject(j);
                                    String endTime = object14.optString("endTime");
                                    String code = object14.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object14.optString("issue");
                                    String name = maps.get("14");
                                    int imgd = tubiao[5];
                                    list14.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("14",list14);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("13")) {
                                ArrayList<ArcList> list13=new ArrayList<>();
                                JSONArray jsonArray13 = result.optJSONArray("13");
                                for (int j=0;j<jsonArray13.length();j++){
                                    JSONObject object13 = jsonArray13.optJSONObject(j);
                                    String endTime = object13.optString("endTime");
                                    String code = object13.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object13.optString("issue");
                                    String name = maps.get("13");
                                    int imgd = tubiao[4];
                                    list13.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("13",list13);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("34")) {
                                ArrayList<ArcList> list34=new ArrayList<>();
                                JSONArray jsonArray34 = result.optJSONArray("34");
                                for (int j=0;j<jsonArray34.length();j++){
                                    JSONObject object34 = jsonArray34.optJSONObject(j);
                                    String endTime = object34.optString("endTime");
                                    String code = object34.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object34.optString("issue");
                                    String name = maps.get("34");
                                    int imgd = tubiao[3];
                                    list34.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("34",list34);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("33")) {
                                ArrayList<ArcList> list33=new ArrayList<>();
                                JSONArray jsonArray33 = result.optJSONArray("33");
                                for (int j=0;j<jsonArray33.length();j++){
                                    JSONObject object33 = jsonArray33.optJSONObject(j);
                                    String endTime = object33.optString("endTime");
                                    String code = object33.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object33.optString("issue");
                                    String name = maps.get("33");
                                    int imgd = tubiao[2];
                                    list33.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("33",list33);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("36")) {
                                ArrayList<ArcList> list36=new ArrayList<>();
                                JSONArray jsonArray36 = result.optJSONArray("36");
                                for (int j=0;j<jsonArray36.length();j++){
                                    JSONObject object36 = jsonArray36.optJSONObject(j);
                                    String endTime = object36.optString("endTime");
                                    String code = object36.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object36.optString("issue");
                                    String name = maps.get("36");
                                    int imgd = tubiao[1];
                                    list36.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("36",list36);
                                    }
                                }
                                for (int p = 0;p < xiaonum.size(); p++) {
                                    if (xiaonum.get(p).equals("35")) {
                                ArrayList<ArcList> list35=new ArrayList<>();
                                JSONArray jsonArray35 = result.optJSONArray("35");
                                for (int j=0;j<jsonArray35.length();j++){
                                    JSONObject object35 = jsonArray35.optJSONObject(j);
                                    String endTime = object35.optString("endTime");
                                    String code = object35.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object35.optString("issue");
                                    String name = maps.get("35");
                                    int imgd = tubiao[0];
                                    list35.add(new ArcList(issue,stampToDate(endTime),strings.length,strings,name,imgd));
                                }
                                map.put("35",list35);
                                    }
                                }

                                adapter=new MyListViewAdapter(getActivity());
                                listview.setAdapter(adapter);
                                adapter.refresh(map);
                                adapter.setDatas(xiaonum);
                                adapter.notifyDataSetChanged();


                                refreshLayout.refreshComplete();


                                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Set set = map.keySet();
                                        Iterator iter = set.iterator();
                                        while (iter.hasNext()) {
                                            String key = (String) iter.next();
                                            arrayList.add(key);
                                        }
                                        String s = maps.get(arrayList.get(position));
                                        Intent intent=new Intent(getActivity(),LotteryDetailsActivity.class);
                                        intent.putExtra("gameId",arrayList.get(position));
                                        intent.putExtra("name",s);
                                        intent.putExtra("img",map.get(arrayList.get(position)).get(0).getImg()+"");
                                        startActivity(intent);
                                    }
                                });

                            }else{

                                ToastUtils.showToast(getActivity(),errormsg);
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
    public String stampToDate(String s){
        String res = null;
        if (isNumeric(s)){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long lt = Long.valueOf(s);
            Date date = new Date(lt*1000);
            res = simpleDateFormat.format(date);
        }else {
            res = "";
        }

        return res;
    }
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    //拆分字符返回数组
    public String[] convertStrToArray(String str){
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }
}