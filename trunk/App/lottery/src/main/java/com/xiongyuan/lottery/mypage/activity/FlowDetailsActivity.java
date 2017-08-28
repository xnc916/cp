package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.FlowDetailsAdapter;
import com.xiongyuan.lottery.mypage.bean.SubordinateAccountItemBean;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FlowDetailsActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView textView;
    @BindView(R.id.list_flow_details)
    ListView listView;
    private ArrayList<String> list;
    private FlowDetailsAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_flow_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        textView.setText("流水详情");
        adapter=new FlowDetailsAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        list=new ArrayList<>();
        getAccLogInfo(getIntent().getStringExtra("userId"),getIntent().getStringExtra("id"));
    }
    @OnClick({R.id.title_left})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
    //流水详情
    public void  getAccLogInfo(String userId,String id) {
        if (list != null) {
            list.clear();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.getAccLogInfo")
                .addParams("user_id", userId)
                .addParams("id", id)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(FlowDetailsActivity.this, "网络错误");

                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-流水详情记录-==", o + "---");

                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject result = jsonObject.optJSONObject("result");
                                SubordinateAccountItemBean itemBean=new SubordinateAccountItemBean();
                                String addTime = result.optString("addTime");
                                String date = stampToDate(addTime);
                                String type = result.optString("type");
                                itemBean.setType(type);
                                list.add(result.optString("user_name"));
                                list.add(date);
                                list.add(itemBean.getType());
                                list.add(result.optString("usable"));
                                String usableAmount = result.optString("usableAmount");
                                if (usableAmount.equals("null")){
                                    usableAmount = "";
                                }
                                list.add(usableAmount);
                                String intro = result.optString("intro");
                                if (intro.equals("null")){
                                    intro = "";
                                }
                                list.add(intro);
                                adapter.refresh(list);
                            } else {
                                ToastUtils.showToast(FlowDetailsActivity.this, errormsg);
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
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.valueOf(s);
        Date date = new Date(lt*1000);
        res = simpleDateFormat.format(date);
        return res;
    }

    }
