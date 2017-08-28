package com.xiongyuan.lottery.thirdpage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.thirdpage.adapter.LotteryDetailsAdapter;
import com.xiongyuan.lottery.thirdpage.bean.ArcList;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;
import okhttp3.Response;

public class LotteryDetailsActivity extends BaseActivity {
    private LoadingDialog loadingDialog;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.list_lottery_details)
    ListView listView;
    private LotteryDetailsAdapter adapter;
    private ArrayList<ArcList> list=new ArrayList<>();
    private String name;
    private int img;
    private String game_id;
    private int page=1;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @Override
    public int getLayoutId() {
        return R.layout.activity_lottery_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        titleName.setText("开奖详情");
        adapter=new LotteryDetailsAdapter(LotteryDetailsActivity.this);
        listView.setAdapter(adapter);
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setBackgroundResource(R.color.color_333333);
        refreshLayout.setDurationToCloseHeader(1500);
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                getCodeHistory2(game_id,page);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getCodeHistory(game_id);
            }
        });
    }

    @Override
    public void initData() {
        name = getIntent().getStringExtra("name");
        String ss = getIntent().getStringExtra("img");
        game_id = getIntent().getStringExtra("gameId");
        img = Integer.valueOf(ss);

        getCodeHistory(game_id);

    }
    @OnClick({R.id.title_left})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
    //取得历史开奖号码
    public void  getCodeHistory(String gameId) {
        if (loadingDialog != null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(LotteryDetailsActivity.this, "加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Games.getCodeHistory")
                .addParams("length", "10")
                .addParams("page", "1")
                .addParams("game_id", gameId)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(LotteryDetailsActivity.this, "网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        list.clear();
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject object = jsonArray.optJSONObject(j);
                                    String endTime = object.optString("endTime");
                                    String code = object.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object.optString("issue");
                                    list.add(new ArcList(issue, stampToDate(endTime), strings.length, strings,name,img));
                                }
                                adapter.refresh(list);
                                refreshLayout.refreshComplete();
                                page=2;
                            } else {
                                ToastUtils.showToast(LotteryDetailsActivity.this, errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    public void  getCodeHistory2(String gameId,int pageint) {
        if (loadingDialog != null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(LotteryDetailsActivity.this, "加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Games.getCodeHistory")
                .addParams("length", "10")
                .addParams("page", pageint+"")
                .addParams("game_id", gameId)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(LotteryDetailsActivity.this, "网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        list.clear();
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONArray jsonArray = jsonObject.optJSONArray("result");
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject object = jsonArray.optJSONObject(j);
                                    String endTime = object.optString("endTime");
                                    String code = object.optString("code");
                                    String[] strings = convertStrToArray(code);
                                    String issue = object.optString("issue");
                                    list.add(new ArcList(issue, stampToDate(endTime), strings.length, strings,name,img));
                                }
                                adapter.refresh2(list);
                                refreshLayout.refreshComplete();
                                page++;
                            } else {
                                ToastUtils.showToast(LotteryDetailsActivity.this, errormsg);
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
    //拆分字符返回数组
    public static String[] convertStrToArray(String str){
        String[] strArray = null;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }
    }
