package com.xiongyuan.lottery.mypage.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jiang.android.indicatordialog.IndicatorBuilder;
import com.jiang.android.indicatordialog.IndicatorDialog;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.BaseAdapter;
import com.xiongyuan.lottery.mypage.adapter.BaseViewHolder;
import com.xiongyuan.lottery.mypage.adapter.StatisticsTodayAdapter;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class StatisticsTodayActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_right)
    TextView titleright;
    @BindView(R.id.list_statistics_today)
    ListView listView;
    private StatisticsTodayAdapter adapter;
    private ArrayList<String> list;

    private List<String> mLists = new ArrayList<>();
    private IndicatorDialog dialog;

    private Date d;
    private int page=1;
    private int p = 0;
    private String date;
    private String enddata;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
            Date da = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(da);
            da = calendar.getTime();
            switch (msg.arg1) {
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
            getStatData(getIntent().getStringExtra("userId"),jsonStr(date,enddata));

        }

    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_statistics_today;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("个人统计");
        titleright.setText("全部");
        titleright.setVisibility(View.VISIBLE);

        adapter=new StatisticsTodayAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        list=new ArrayList<>();
        getStatData(getIntent().getStringExtra("userId"),jsonStr("",""));
    }
    @OnClick({R.id.title_left,R.id.title_right})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                showTopDialog(titleright, 0.9f, IndicatorBuilder.GRAVITY_RIGHT);
                break;
        }
    }
    private void showTopDialog(View v, float v1, int gravityCenter) {
        mLists.clear();
        mLists.add("全部");
        mLists.add("今天");
        mLists.add("近一周");
        mLists.add("前一天");
        mLists.add("后一天");
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int height = dm.heightPixels;
        int width = dm.widthPixels;
        dialog = new IndicatorBuilder(this)
                .width(300)
                .height((int) (height * 0.5))
                .ArrowDirection(IndicatorBuilder.TOP)
                .bgColor(Color.parseColor("#49484b"))
                .gravity(gravityCenter)
                .radius(18)
                .ArrowRectage(v1)
                .layoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                .adapter(new BaseAdapter() {
                    @Override
                    public void onBindView(BaseViewHolder holder, int position) {
                        TextView tv = holder.getView(R.id.item_add);
                        tv.setText(mLists.get(position));
                        //tv.setCompoundDrawablesWithIntrinsicBounds(mICons.get(position), 0, 0, 0);

                        if (position == mLists.size() - 1) {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.GONE);
                        } else {
                            holder.setVisibility(R.id.item_line, BaseViewHolder.VISIBLE);

                        }
                    }

                    @Override
                    public int getLayoutID(int position) {
                        return R.layout.item;
                    }

                    @Override
                    public boolean clickable() {
                        return true;
                    }

                    @Override
                    public void onItemClick(View v, int position) {
                        p= position;
                        Message msg = new Message();
                        msg.arg1 = p;
                        handler.sendMessage(msg);
                        dialog.dismiss();
                        titleright.setText(mLists.get(position));
                    }

                    @Override
                    public int getItemCount() {
                        return mLists.size();
                    }
                }).create();

        dialog.setCanceledOnTouchOutside(true);
        dialog.show(v);

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

    //个人统计
    private void getStatData(String userId,String search) {
        if (list!=null){
            list.clear();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.getStatData")
                .addParams("user_id", userId)
                .addParams("search",search)
                .addParams("type","1")
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(StatisticsTodayActivity.this, "网络错误");
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-个人统计-==", o);
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject object = jsonObject.optJSONObject("result");
                                list.add(object.optString("recharge"));
                                list.add(object.optString("cashSuc"));
                                list.add(object.optString("bet"));
                                list.add(object.optString("betReturn"));
                                list.add(object.optString("winBet"));
                                list.add(object.optString("proAndLoss"));
                                adapter.refresh(list);
                            } else {
                                ToastUtils.showToast(StatisticsTodayActivity.this, errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}
