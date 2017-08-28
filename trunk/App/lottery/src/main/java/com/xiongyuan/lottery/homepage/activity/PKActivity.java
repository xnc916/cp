package com.xiongyuan.lottery.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.homepage.bean.pkdata.GameTimeBean;
import com.xiongyuan.lottery.homepage.bean.pkdata.Gameid1;
import com.xiongyuan.lottery.homepage.bean.pkdata.Pk2Result;
import com.xiongyuan.lottery.homepage.bean.pkdata.PkResult;
import com.xiongyuan.lottery.homepage.bean.pkdata.Play2Bean;
import com.xiongyuan.lottery.homepage.bean.pkdata.Sh;
import com.xiongyuan.lottery.homepage.bean.pkdata.Type2Bean;
import com.xiongyuan.lottery.homepage.bean.pkdata.WebviewResult;
import com.xiongyuan.lottery.homepage.utils.CountDownTimerUtil;
import com.xiongyuan.lottery.homepage.wheelview.OptionsPopupWindow;
import com.xiongyuan.lottery.thirdpage.bean.Shil;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PKActivity extends BaseActivity implements OptionsPopupWindow.OnOptionsSelectListener,SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.title_name)
    TextView titlename;
    @BindView(R.id.title_left_s)
    TextView titleLe;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title)
    RelativeLayout rl;
    @BindView(R.id.Pk_time_show)
    TextView tm_tv;
    @BindView(R.id.view)
    View v;
    @BindView(R.id.tz_bs)
    TextView tzbs;

    @BindView(R.id.pk_web)
    WebView pkweb;
    @BindView(R.id.sb_pk)
    SeekBar sbpk;
    @BindView(R.id.tv_fdl)
    TextView tvfdl;//返点率
    @BindView(R.id.tv_jj)
    TextView tvjj;//奖金

    @BindView(R.id.spinner)
    Spinner spinner;


    private List<Gameid1> mOneItems = new ArrayList<>();
    ArrayList<String> ProvinceList;
    ArrayList<ArrayList<String>> CityList;
    ArrayList<ArrayList<ArrayList<String>>> CountyList;

    private String name;
    private String id;
    private int zhuNum = 0;//多少注
    private String beiNum = "1";//多少倍钱
    private String param;
    private JSONArray type;
    private JSONArray play;
    private CountDownTimerUtil start;
    private boolean xiaoshi = false;
    private String bonus = "35";//奖金
    private String returnAmount = "20";//返点率金钱
    private String oneAmount = "0";//单价
    private String haoma;//号码
    private String gameplay;//玩法
    private String game_id;//游戏id
    private String play_id;//玩法id
    private String fdlv = "0";//返点率
    private String userId;
    private String jlfdlv = "0";//记录返点率
    private String play_rules = "";
    private static final String[] danwei = {"元", "角", "分"};
    private double dww = 1;
    private SpinnerAdapter adapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            xiaoshi = false;
            initgametime();
        }
    };
    private ArrayList<Shil> betlist = new ArrayList<>();
    private String frequency;
    private DecimalFormat df = new DecimalFormat("0.00");
    private String tn;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public int getLayoutId() {
        return R.layout.activity_pk;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
//        titlename.setTextSize(22);
//        titlename.setText(tn);
        titleLe.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setText("刷新");

        sbpk.setOnSeekBarChangeListener(this);

        adapter = new SpinnerAdapter(this, R.layout.layout, danwei);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (adapter.getItem(position).equals("元")) {
                    dww = 1;
                }
                if (adapter.getItem(position).equals("角")) {
                    dww = 0.1;
                }
                if (adapter.getItem(position).equals("分")) {
                    dww = 0.01;
                }


                double a = Double.valueOf(fdlv);
                double v = Double.valueOf(jlfdlv);
                double jiangjin = Double.valueOf(bonus);
                double fdje = Double.valueOf(returnAmount);
                double v1 = (jiangjin + (fdje * (a - v) * 10)) * dww;
                String format1 = df.format(v1);
                tvjj.setText("奖金\n" + format1);

                double j = Double.valueOf(oneAmount) * dww;
                double k = Double.valueOf(beiNum);
                double i = zhuNum * k * j;
                tzbs.setText("共" + zhuNum + "注 ¥" + df.format(i));
                if (zhuNum > 0) {
                    tzbs.setVisibility(View.VISIBLE);
                } else {
                    tzbs.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ViewTreeObserver vto = rl.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rl.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                rl.getHeight();
                rl.getWidth();

            }
        });

        //获取游戏时间
        initgametime();

    }


    private void initgametime() {

        Call call = LotteryClient.getInstance().getGameTime(id);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(PKActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                GameTimeBean result = new Gson().fromJson(body, GameTimeBean.class);
                String time = result.getResult().getCur().getTime() + "";
                String endTime = result.getResult().getCur().getEndTime() + "";

                String timet1 = getTimet(time);
                String timet2 = getTimet(endTime);

                int timec = getTimec(timet1, timet2);

                name = result.getResult().getCur().getIssue();

                //倒计时结束。
                start = new CountDownTimerUtil(timec, 1000) {
                    public void onTick(long millisUntilFinished) {

                        if (PKActivity.this.isFinishing()) {
                            this.cancel();
                        }

                        if (xiaoshi) {
                            this.cancel();
                        }

                        long days = millisUntilFinished / (1000 * 60 * 60 * 24);
                        long hours = (millisUntilFinished % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                        long minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60);
                        long seconds = (millisUntilFinished % (1000 * 60)) / 1000;

                        String time = days + "天" + hours + "时" + minutes + "分" + seconds + "秒";

                        if (days == 0) {
                            time = hours + "时" + minutes + "分" + seconds + "秒";
                            if (hours == 0) {
                                time = minutes + "分" + seconds + "秒";
                                if (minutes == 0) {
                                    time = seconds + "秒";
                                }
                            }
                        }


                        tm_tv.setText("距" + name + "截止：" + time);
                    }

                    public void onFinish() {  //倒计时结束。
                        ToastUtils.showToast(PKActivity.this, name + "结束");

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                initgametime();
                            }
                        }, 5000);


                    }
                }.start();

            }
        });

    }

    public String getTimet(String time) {

        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdf.format(new Date(i * 1000L));
        return times;

    }

    public int getTimec(String t1, String t2) {

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = sdf.parse(t1);
            d2 = sdf.parse(t2);
        } catch (ParseException pe) {

        }

        long dd1 = d1.getTime();
        long dd2 = d2.getTime();

        return (int) (dd2 - dd1);

    }


    @Override
    public void initData() {
        tn = getIntent().getStringExtra("titlename");
        initWEB();
        id = getIntent().getStringExtra("id");


        refest();


    }

    private void refest() {
        Call call = LotteryClient.getInstance().getGamePaly(id);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {

            }

            @Override
            public void onResponseUI(Call call, String body) {
                mOneItems.clear();
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject results = jsonObject.optJSONObject("result");
                    if (results.optJSONObject("type") == null) {
                        Pk2Result re = new Gson().fromJson(body, Pk2Result.class);

                        frequency = re.getResult().getGame().getFrequency();

                        List<Type2Bean> list22 = re.getResult().getType();
                        for (int i = 0; i < list22.size(); i++) {
                            Gameid1 gg = new Gameid1();
                            gg.setId(list22.get(i).getId());
                            gg.setPid(list22.get(i).getPid());
                            gg.setOnename(list22.get(i).getTitle());
                            mOneItems.add(gg);
                        }
                        List<Play2Bean> list33 = re.getResult().getPlay();
                        for (int i = 0; i < list33.size(); i++) {
                            Gameid1 gg = new Gameid1();
                            gg.setId(list33.get(i).getId());
                            gg.setPid(list33.get(i).getPid());
                            gg.setOnename(list33.get(i).getTitle());
                            mOneItems.add(gg);
                        }

                    } else {
                        PkResult result = new Gson().fromJson(body, PkResult.class);

                        Gameid1 g1 = new Gameid1();
                        g1.setId(result.getPk_result().getResultBeantype().get_$148().getTyp_id());
                        g1.setPid(result.getPk_result().getResultBeantype().get_$148().getTyp_pid());
                        g1.setOnename(result.getPk_result().getResultBeantype().get_$148().getTyp_title());
                        mOneItems.add(g1);
                        Gameid1 g199 = new Gameid1();
                        g199.setId(result.getPk_result().getResultBeantype().get_$199().getTyp_id());
                        g199.setPid(result.getPk_result().getResultBeantype().get_$199().getTyp_pid());
                        g199.setOnename(result.getPk_result().getResultBeantype().get_$199().getTyp_title());
                        mOneItems.add(g199);
                        Gameid1 g198 = new Gameid1();
                        g198.setId(result.getPk_result().getResultBeantype().get_$198().getTyp_id());
                        g198.setPid(result.getPk_result().getResultBeantype().get_$198().getTyp_pid());
                        g198.setOnename(result.getPk_result().getResultBeantype().get_$198().getTyp_title());
                        mOneItems.add(g198);
                        Gameid1 g153 = new Gameid1();
                        g153.setId(result.getPk_result().getResultBeantype().get_$153().getTyp_id());
                        g153.setPid(result.getPk_result().getResultBeantype().get_$153().getTyp_pid());
                        g153.setOnename(result.getPk_result().getResultBeantype().get_$153().getTyp_title());
                        mOneItems.add(g153);
                        Gameid1 g282 = new Gameid1();
                        g282.setId(result.getPk_result().getResultBeantype().get_$282().getTyp_id());
                        g282.setPid(result.getPk_result().getResultBeantype().get_$282().getTyp_pid());
                        g282.setOnename(result.getPk_result().getResultBeantype().get_$282().getTyp_title());
                        mOneItems.add(g282);
                        Gameid1 g183 = new Gameid1();
                        g183.setId(result.getPk_result().getResultBeantype().get_$183().getTyp_id());
                        g183.setPid(result.getPk_result().getResultBeantype().get_$183().getTyp_pid());
                        g183.setOnename(result.getPk_result().getResultBeantype().get_$183().getTyp_title());
                        mOneItems.add(g183);
                        Gameid1 g300 = new Gameid1();
                        g300.setId(result.getPk_result().getResultBeantype().get_$300().getTyp_id());
                        g300.setPid(result.getPk_result().getResultBeantype().get_$300().getTyp_pid());
                        g300.setOnename(result.getPk_result().getResultBeantype().get_$300().getTyp_title());
                        mOneItems.add(g300);
                        Gameid1 g299 = new Gameid1();
                        g299.setId(result.getPk_result().getResultBeantype().get_$299().getTyp_id());
                        g299.setPid(result.getPk_result().getResultBeantype().get_$299().getTyp_pid());
                        g299.setOnename(result.getPk_result().getResultBeantype().get_$299().getTyp_title());
                        mOneItems.add(g299);
                        Gameid1 g298 = new Gameid1();
                        g298.setId(result.getPk_result().getResultBeantype().get_$298().getTyp_id());
                        g298.setPid(result.getPk_result().getResultBeantype().get_$298().getTyp_pid());
                        g298.setOnename(result.getPk_result().getResultBeantype().get_$298().getTyp_title());
                        mOneItems.add(g298);
                        Gameid1 g297 = new Gameid1();
                        g297.setId(result.getPk_result().getResultBeantype().get_$297().getTyp_id());
                        g297.setPid(result.getPk_result().getResultBeantype().get_$297().getTyp_pid());
                        g297.setOnename(result.getPk_result().getResultBeantype().get_$297().getTyp_title());
                        mOneItems.add(g297);
                        Gameid1 g296 = new Gameid1();
                        g296.setId(result.getPk_result().getResultBeantype().get_$296().getTyp_id());
                        g296.setPid(result.getPk_result().getResultBeantype().get_$296().getTyp_pid());
                        g296.setOnename(result.getPk_result().getResultBeantype().get_$296().getTyp_title());
                        mOneItems.add(g296);
                        Gameid1 g295 = new Gameid1();
                        g295.setId(result.getPk_result().getResultBeantype().get_$295().getTyp_id());
                        g295.setPid(result.getPk_result().getResultBeantype().get_$295().getTyp_pid());
                        g295.setOnename(result.getPk_result().getResultBeantype().get_$295().getTyp_title());
                        mOneItems.add(g295);
                        Gameid1 g286 = new Gameid1();
                        g286.setId(result.getPk_result().getResultBeantype().get_$286().getTyp_id());
                        g286.setPid(result.getPk_result().getResultBeantype().get_$286().getTyp_pid());
                        g286.setOnename(result.getPk_result().getResultBeantype().get_$286().getTyp_title());
                        mOneItems.add(g286);
                        Gameid1 g285 = new Gameid1();
                        g285.setId(result.getPk_result().getResultBeantype().get_$285().getTyp_id());
                        g285.setPid(result.getPk_result().getResultBeantype().get_$285().getTyp_pid());
                        g285.setOnename(result.getPk_result().getResultBeantype().get_$285().getTyp_title());
                        mOneItems.add(g285);
                        Gameid1 g284 = new Gameid1();
                        g284.setId(result.getPk_result().getResultBeantype().get_$284().getTyp_id());
                        g284.setPid(result.getPk_result().getResultBeantype().get_$284().getTyp_pid());
                        g284.setOnename(result.getPk_result().getResultBeantype().get_$284().getTyp_title());
                        mOneItems.add(g284);
                        Gameid1 g283 = new Gameid1();
                        g283.setId(result.getPk_result().getResultBeantype().get_$283().getTyp_id());
                        g283.setPid(result.getPk_result().getResultBeantype().get_$283().getTyp_pid());
                        g283.setOnename(result.getPk_result().getResultBeantype().get_$283().getTyp_title());
                        mOneItems.add(g283);
                        Gameid1 g281 = new Gameid1();
                        g281.setId(result.getPk_result().getResultBeantype().get_$281().getTyp_id());
                        g281.setPid(result.getPk_result().getResultBeantype().get_$281().getTyp_pid());
                        g281.setOnename(result.getPk_result().getResultBeantype().get_$281().getTyp_title());
                        mOneItems.add(g281);
                        Gameid1 g203 = new Gameid1();
                        g203.setId(result.getPk_result().getResultBeantype().get_$203().getTyp_id());
                        g203.setPid(result.getPk_result().getResultBeantype().get_$203().getTyp_pid());
                        g203.setOnename(result.getPk_result().getResultBeantype().get_$203().getTyp_title());
                        mOneItems.add(g203);
                        Gameid1 g202 = new Gameid1();
                        g202.setId(result.getPk_result().getResultBeantype().get_$202().getTyp_id());
                        g202.setPid(result.getPk_result().getResultBeantype().get_$202().getTyp_pid());
                        g202.setOnename(result.getPk_result().getResultBeantype().get_$202().getTyp_title());
                        mOneItems.add(g202);
                        Gameid1 g201 = new Gameid1();
                        g201.setId(result.getPk_result().getResultBeantype().get_$201().getTyp_id());
                        g201.setPid(result.getPk_result().getResultBeantype().get_$201().getTyp_pid());
                        g201.setOnename(result.getPk_result().getResultBeantype().get_$201().getTyp_title());
                        mOneItems.add(g201);
                        Gameid1 g200 = new Gameid1();
                        g200.setId(result.getPk_result().getResultBeantype().get_$200().getTyp_id());
                        g200.setPid(result.getPk_result().getResultBeantype().get_$200().getTyp_pid());
                        g200.setOnename(result.getPk_result().getResultBeantype().get_$200().getTyp_title());
                        mOneItems.add(g200);
                        Gameid1 g195 = new Gameid1();
                        g195.setId(result.getPk_result().getResultBeantype().get_$195().getTyp_id());
                        g195.setPid(result.getPk_result().getResultBeantype().get_$195().getTyp_pid());
                        g195.setOnename(result.getPk_result().getResultBeantype().get_$195().getTyp_title());
                        mOneItems.add(g195);
                        Gameid1 g189 = new Gameid1();
                        g189.setId(result.getPk_result().getResultBeantype().get_$189().getTyp_id());
                        g189.setPid(result.getPk_result().getResultBeantype().get_$189().getTyp_pid());
                        g189.setOnename(result.getPk_result().getResultBeantype().get_$189().getTyp_title());
                        mOneItems.add(g189);
                        Gameid1 g188 = new Gameid1();
                        g188.setId(result.getPk_result().getResultBeantype().get_$188().getTyp_id());
                        g188.setPid(result.getPk_result().getResultBeantype().get_$188().getTyp_pid());
                        g188.setOnename(result.getPk_result().getResultBeantype().get_$188().getTyp_title());
                        mOneItems.add(g188);
                        Gameid1 g182 = new Gameid1();
                        g182.setId(result.getPk_result().getResultBeantype().get_$182().getTyp_id());
                        g182.setPid(result.getPk_result().getResultBeantype().get_$182().getTyp_pid());
                        g182.setOnename(result.getPk_result().getResultBeantype().get_$182().getTyp_title());
                        mOneItems.add(g182);
                        Gameid1 g181 = new Gameid1();
                        g181.setId(result.getPk_result().getResultBeantype().get_$181().getTyp_id());
                        g181.setPid(result.getPk_result().getResultBeantype().get_$181().getTyp_pid());
                        g181.setOnename(result.getPk_result().getResultBeantype().get_$181().getTyp_title());
                        mOneItems.add(g181);
                        Gameid1 g180 = new Gameid1();
                        g180.setId(result.getPk_result().getResultBeantype().get_$180().getTyp_id());
                        g180.setPid(result.getPk_result().getResultBeantype().get_$180().getTyp_pid());
                        g180.setOnename(result.getPk_result().getResultBeantype().get_$180().getTyp_title());
                        mOneItems.add(g180);
                        Gameid1 g179 = new Gameid1();
                        g179.setId(result.getPk_result().getResultBeantype().get_$179().getTyp_id());
                        g179.setPid(result.getPk_result().getResultBeantype().get_$179().getTyp_pid());
                        g179.setOnename(result.getPk_result().getResultBeantype().get_$179().getTyp_title());
                        mOneItems.add(g179);
                        Gameid1 g178 = new Gameid1();
                        g178.setId(result.getPk_result().getResultBeantype().get_$178().getTyp_id());
                        g178.setPid(result.getPk_result().getResultBeantype().get_$178().getTyp_pid());
                        g178.setOnename(result.getPk_result().getResultBeantype().get_$178().getTyp_title());
                        mOneItems.add(g178);
                        Gameid1 g172 = new Gameid1();
                        g172.setId(result.getPk_result().getResultBeantype().get_$172().getTyp_id());
                        g172.setPid(result.getPk_result().getResultBeantype().get_$172().getTyp_pid());
                        g172.setOnename(result.getPk_result().getResultBeantype().get_$172().getTyp_title());
                        mOneItems.add(g172);
                        Gameid1 g171 = new Gameid1();
                        g171.setId(result.getPk_result().getResultBeantype().get_$171().getTyp_id());
                        g171.setPid(result.getPk_result().getResultBeantype().get_$171().getTyp_pid());
                        g171.setOnename(result.getPk_result().getResultBeantype().get_$171().getTyp_title());
                        mOneItems.add(g171);
                        Gameid1 g170 = new Gameid1();
                        g170.setId(result.getPk_result().getResultBeantype().get_$170().getTyp_id());
                        g170.setPid(result.getPk_result().getResultBeantype().get_$170().getTyp_pid());
                        g170.setOnename(result.getPk_result().getResultBeantype().get_$170().getTyp_title());
                        mOneItems.add(g170);
                        Gameid1 g169 = new Gameid1();
                        g169.setId(result.getPk_result().getResultBeantype().get_$169().getTyp_id());
                        g169.setPid(result.getPk_result().getResultBeantype().get_$169().getTyp_pid());
                        g169.setOnename(result.getPk_result().getResultBeantype().get_$169().getTyp_title());
                        mOneItems.add(g169);
                        Gameid1 g168 = new Gameid1();
                        g168.setId(result.getPk_result().getResultBeantype().get_$168().getTyp_id());
                        g168.setPid(result.getPk_result().getResultBeantype().get_$168().getTyp_pid());
                        g168.setOnename(result.getPk_result().getResultBeantype().get_$168().getTyp_title());
                        mOneItems.add(g168);
                        Gameid1 g154 = new Gameid1();
                        g154.setId(result.getPk_result().getResultBeantype().get_$154().getTyp_id());
                        g154.setPid(result.getPk_result().getResultBeantype().get_$154().getTyp_pid());
                        g154.setOnename(result.getPk_result().getResultBeantype().get_$154().getTyp_title());
                        mOneItems.add(g154);
                        Gameid1 g151 = new Gameid1();
                        g151.setId(result.getPk_result().getResultBeantype().get_$151().getTyp_id());
                        g151.setPid(result.getPk_result().getResultBeantype().get_$151().getTyp_pid());
                        g151.setOnename(result.getPk_result().getResultBeantype().get_$151().getTyp_title());
                        mOneItems.add(g151);
                        Gameid1 g165 = new Gameid1();
                        g165.setId(result.getPk_result().getResultBeantype().get_$165().getTyp_id());
                        g165.setPid(result.getPk_result().getResultBeantype().get_$165().getTyp_pid());
                        g165.setOnename(result.getPk_result().getResultBeantype().get_$165().getTyp_title());
                        mOneItems.add(g165);
                        Gameid1 g185 = new Gameid1();
                        g185.setId(result.getPk_result().getResultBeantype().get_$185().getTyp_id());
                        g185.setPid(result.getPk_result().getResultBeantype().get_$185().getTyp_pid());
                        g185.setOnename(result.getPk_result().getResultBeantype().get_$185().getTyp_title());
                        mOneItems.add(g185);
                        Gameid1 g149 = new Gameid1();
                        g149.setId(result.getPk_result().getResultBeantype().get_$149().getTyp_id());
                        g149.setPid(result.getPk_result().getResultBeantype().get_$149().getTyp_pid());
                        g149.setOnename(result.getPk_result().getResultBeantype().get_$149().getTyp_title());
                        mOneItems.add(g149);
                        Gameid1 g150 = new Gameid1();
                        g150.setId(result.getPk_result().getResultBeantype().get_$150().getTyp_id());
                        g150.setPid(result.getPk_result().getResultBeantype().get_$150().getTyp_pid());
                        g150.setOnename(result.getPk_result().getResultBeantype().get_$150().getTyp_title());
                        mOneItems.add(g150);


                        Gameid1 gameid3 = new Gameid1();
                        gameid3.setId(result.getPk_result().getPlay().get_$3().getId());
                        gameid3.setPid(result.getPk_result().getPlay().get_$3().getPid());
                        gameid3.setOnename(result.getPk_result().getPlay().get_$3().getTitle());
                        mOneItems.add(gameid3);

                        Gameid1 gameid5 = new Gameid1();
                        gameid5.setId(result.getPk_result().getPlay().get_$5().getId());
                        gameid5.setPid(result.getPk_result().getPlay().get_$5().getPid());
                        gameid5.setOnename(result.getPk_result().getPlay().get_$5().getTitle());
                        mOneItems.add(gameid3);

                        Gameid1 gameid6 = new Gameid1();
                        gameid6.setId(result.getPk_result().getPlay().get_$6().getId());
                        gameid6.setPid(result.getPk_result().getPlay().get_$6().getPid());
                        gameid6.setOnename(result.getPk_result().getPlay().get_$6().getTitle());
                        mOneItems.add(gameid6);


                        Gameid1 gameid7 = new Gameid1();
                        gameid7.setId(result.getPk_result().getPlay().get_$7().getId());
                        gameid7.setPid(result.getPk_result().getPlay().get_$7().getPid());
                        gameid7.setOnename(result.getPk_result().getPlay().get_$7().getTitle());
                        mOneItems.add(gameid7);


                        Gameid1 gameid8 = new Gameid1();
                        gameid8.setId(result.getPk_result().getPlay().get_$8().getId());
                        gameid8.setPid(result.getPk_result().getPlay().get_$8().getPid());
                        gameid8.setOnename(result.getPk_result().getPlay().get_$8().getTitle());
                        mOneItems.add(gameid8);

                        Gameid1 gameid9 = new Gameid1();
                        gameid9.setId(result.getPk_result().getPlay().get_$9().getId());
                        gameid9.setPid(result.getPk_result().getPlay().get_$9().getPid());
                        gameid9.setOnename(result.getPk_result().getPlay().get_$9().getTitle());
                        mOneItems.add(gameid9);

                        Gameid1 gameid10 = new Gameid1();
                        gameid10.setId(result.getPk_result().getPlay().get_$10().getId());
                        gameid10.setPid(result.getPk_result().getPlay().get_$10().getPid());
                        gameid10.setOnename(result.getPk_result().getPlay().get_$10().getTitle());
                        mOneItems.add(gameid10);

                        Gameid1 gameid11 = new Gameid1();
                        gameid11.setId(result.getPk_result().getPlay().get_$11().getId());
                        gameid11.setPid(result.getPk_result().getPlay().get_$11().getPid());
                        gameid11.setOnename(result.getPk_result().getPlay().get_$11().getTitle());
                        mOneItems.add(gameid11);

                        Gameid1 gameid12 = new Gameid1();
                        gameid12.setId(result.getPk_result().getPlay().get_$12().getId());
                        gameid12.setPid(result.getPk_result().getPlay().get_$12().getPid());
                        gameid12.setOnename(result.getPk_result().getPlay().get_$12().getTitle());
                        mOneItems.add(gameid12);

                        Gameid1 gameid13 = new Gameid1();
                        gameid13.setId(result.getPk_result().getPlay().get_$13().getId());
                        gameid13.setPid(result.getPk_result().getPlay().get_$13().getPid());
                        gameid13.setOnename(result.getPk_result().getPlay().get_$13().getTitle());
                        mOneItems.add(gameid13);
                        Gameid1 gameid14 = new Gameid1();
                        gameid14.setId(result.getPk_result().getPlay().get_$14().getId());
                        gameid14.setPid(result.getPk_result().getPlay().get_$14().getPid());
                        gameid14.setOnename(result.getPk_result().getPlay().get_$14().getTitle());
                        mOneItems.add(gameid14);
                        Gameid1 gameid15 = new Gameid1();
                        gameid15.setId(result.getPk_result().getPlay().get_$15().getId());
                        gameid15.setPid(result.getPk_result().getPlay().get_$15().getPid());
                        gameid15.setOnename(result.getPk_result().getPlay().get_$15().getTitle());
                        mOneItems.add(gameid15);

                        Gameid1 gameid71 = new Gameid1();
                        gameid71.setId(result.getPk_result().getPlay().get_$71().getId());
                        gameid71.setPid(result.getPk_result().getPlay().get_$71().getPid());
                        gameid71.setOnename(result.getPk_result().getPlay().get_$71().getTitle());
                        mOneItems.add(gameid71);
                        Gameid1 gameid72 = new Gameid1();
                        gameid72.setId(result.getPk_result().getPlay().get_$72().getId());
                        gameid72.setPid(result.getPk_result().getPlay().get_$72().getPid());
                        gameid72.setOnename(result.getPk_result().getPlay().get_$72().getTitle());
                        mOneItems.add(gameid72);

                        Gameid1 gameid73 = new Gameid1();
                        gameid73.setId(result.getPk_result().getPlay().get_$73().getId());
                        gameid73.setPid(result.getPk_result().getPlay().get_$73().getPid());
                        gameid73.setOnename(result.getPk_result().getPlay().get_$73().getTitle());
                        mOneItems.add(gameid73);
                        Gameid1 gameid74 = new Gameid1();
                        gameid74.setId(result.getPk_result().getPlay().get_$74().getId());
                        gameid74.setPid(result.getPk_result().getPlay().get_$74().getPid());
                        gameid74.setOnename(result.getPk_result().getPlay().get_$74().getTitle());
                        mOneItems.add(gameid74);
                        Gameid1 gameid75 = new Gameid1();
                        gameid75.setId(result.getPk_result().getPlay().get_$75().getId());
                        gameid75.setPid(result.getPk_result().getPlay().get_$75().getPid());
                        gameid75.setOnename(result.getPk_result().getPlay().get_$75().getTitle());
                        mOneItems.add(gameid75);

                        Gameid1 gameid76 = new Gameid1();
                        gameid76.setId(result.getPk_result().getPlay().get_$76().getId());
                        gameid76.setPid(result.getPk_result().getPlay().get_$76().getPid());
                        gameid76.setOnename(result.getPk_result().getPlay().get_$76().getTitle());
                        mOneItems.add(gameid76);
                        Gameid1 gameid77 = new Gameid1();
                        gameid77.setId(result.getPk_result().getPlay().get_$77().getId());
                        gameid77.setPid(result.getPk_result().getPlay().get_$77().getPid());
                        gameid77.setOnename(result.getPk_result().getPlay().get_$77().getTitle());
                        mOneItems.add(gameid77);
                        Gameid1 gameid78 = new Gameid1();
                        gameid78.setId(result.getPk_result().getPlay().get_$78().getId());
                        gameid78.setPid(result.getPk_result().getPlay().get_$78().getPid());
                        gameid78.setOnename(result.getPk_result().getPlay().get_$78().getTitle());
                        mOneItems.add(gameid78);
                        Gameid1 gameid79 = new Gameid1();
                        gameid79.setId(result.getPk_result().getPlay().get_$79().getId());
                        gameid79.setPid(result.getPk_result().getPlay().get_$79().getPid());
                        gameid79.setOnename(result.getPk_result().getPlay().get_$79().getTitle());
                        mOneItems.add(gameid79);
                        Gameid1 gameid80 = new Gameid1();
                        gameid80.setId(result.getPk_result().getPlay().get_$80().getId());
                        gameid80.setPid(result.getPk_result().getPlay().get_$80().getPid());
                        gameid80.setOnename(result.getPk_result().getPlay().get_$80().getTitle());
                        mOneItems.add(gameid80);


                        Gameid1 gameid83 = new Gameid1();
                        gameid83.setId(result.getPk_result().getPlay().get_$83().getId());
                        gameid83.setPid(result.getPk_result().getPlay().get_$83().getPid());
                        gameid83.setOnename(result.getPk_result().getPlay().get_$83().getTitle());
                        mOneItems.add(gameid83);

                        Gameid1 gameid84 = new Gameid1();
                        gameid84.setId(result.getPk_result().getPlay().get_$84().getId());
                        gameid84.setPid(result.getPk_result().getPlay().get_$84().getPid());
                        gameid84.setOnename(result.getPk_result().getPlay().get_$84().getTitle());
                        mOneItems.add(gameid84);
                        Gameid1 gameid85 = new Gameid1();
                        gameid85.setId(result.getPk_result().getPlay().get_$85().getId());
                        gameid85.setPid(result.getPk_result().getPlay().get_$85().getPid());
                        gameid85.setOnename(result.getPk_result().getPlay().get_$85().getTitle());
                        mOneItems.add(gameid85);
                        Gameid1 gameid86 = new Gameid1();
                        gameid86.setId(result.getPk_result().getPlay().get_$86().getId());
                        gameid86.setPid(result.getPk_result().getPlay().get_$86().getPid());
                        gameid86.setOnename(result.getPk_result().getPlay().get_$86().getTitle());
                        mOneItems.add(gameid86);
                        Gameid1 gameid87 = new Gameid1();
                        gameid87.setId(result.getPk_result().getPlay().get_$87().getId());
                        gameid87.setPid(result.getPk_result().getPlay().get_$87().getPid());
                        gameid87.setOnename(result.getPk_result().getPlay().get_$87().getTitle());
                        mOneItems.add(gameid87);
                        Gameid1 gameid88 = new Gameid1();
                        gameid88.setId(result.getPk_result().getPlay().get_$88().getId());
                        gameid88.setPid(result.getPk_result().getPlay().get_$88().getPid());
                        gameid88.setOnename(result.getPk_result().getPlay().get_$88().getTitle());
                        mOneItems.add(gameid88);
                        Gameid1 gameid89 = new Gameid1();
                        gameid89.setId(result.getPk_result().getPlay().get_$89().getId());
                        gameid89.setPid(result.getPk_result().getPlay().get_$89().getPid());
                        gameid89.setOnename(result.getPk_result().getPlay().get_$89().getTitle());
                        mOneItems.add(gameid89);
                        Gameid1 gameid90 = new Gameid1();
                        gameid90.setId(result.getPk_result().getPlay().get_$90().getId());
                        gameid90.setPid(result.getPk_result().getPlay().get_$90().getPid());
                        gameid90.setOnename(result.getPk_result().getPlay().get_$90().getTitle());
                        mOneItems.add(gameid90);
                        Gameid1 gameid91 = new Gameid1();
                        gameid91.setId(result.getPk_result().getPlay().get_$91().getId());
                        gameid91.setPid(result.getPk_result().getPlay().get_$91().getPid());
                        gameid91.setOnename(result.getPk_result().getPlay().get_$91().getTitle());
                        mOneItems.add(gameid91);
                        Gameid1 gameid92 = new Gameid1();
                        gameid92.setId(result.getPk_result().getPlay().get_$92().getId());
                        gameid92.setPid(result.getPk_result().getPlay().get_$92().getPid());
                        gameid92.setOnename(result.getPk_result().getPlay().get_$92().getTitle());
                        mOneItems.add(gameid92);
                        Gameid1 gameid93 = new Gameid1();
                        gameid93.setId(result.getPk_result().getPlay().get_$93().getId());
                        gameid93.setPid(result.getPk_result().getPlay().get_$93().getPid());
                        gameid93.setOnename(result.getPk_result().getPlay().get_$93().getTitle());
                        mOneItems.add(gameid93);

                        Gameid1 gameid94 = new Gameid1();
                        gameid94.setId(result.getPk_result().getPlay().get_$94().getId());
                        gameid94.setPid(result.getPk_result().getPlay().get_$94().getPid());
                        gameid94.setOnename(result.getPk_result().getPlay().get_$94().getTitle());
                        mOneItems.add(gameid94);
                        Gameid1 gameid95 = new Gameid1();
                        gameid95.setId(result.getPk_result().getPlay().get_$95().getId());
                        gameid95.setPid(result.getPk_result().getPlay().get_$95().getPid());
                        gameid95.setOnename(result.getPk_result().getPlay().get_$95().getTitle());
                        mOneItems.add(gameid95);

                        Gameid1 gameid96 = new Gameid1();
                        gameid96.setId(result.getPk_result().getPlay().get_$96().getId());
                        gameid96.setPid(result.getPk_result().getPlay().get_$96().getPid());
                        gameid96.setOnename(result.getPk_result().getPlay().get_$96().getTitle());
                        mOneItems.add(gameid96);
                        Gameid1 gameid97 = new Gameid1();
                        gameid97.setId(result.getPk_result().getPlay().get_$97().getId());
                        gameid97.setPid(result.getPk_result().getPlay().get_$97().getPid());
                        gameid97.setOnename(result.getPk_result().getPlay().get_$97().getTitle());
                        mOneItems.add(gameid97);

                        Gameid1 gameids1 = new Gameid1();
                        gameids1.setId(result.getPk_result().getPlay().get_$161().getId());
                        gameids1.setPid(result.getPk_result().getPlay().get_$161().getPid());
                        gameids1.setOnename(result.getPk_result().getPlay().get_$161().getTitle());
                        mOneItems.add(gameids1);

                        Gameid1 gameids0 = new Gameid1();
                        gameids0.setId(result.getPk_result().getPlay().get_$160().getId());
                        gameids0.setPid(result.getPk_result().getPlay().get_$160().getPid());
                        gameids0.setOnename(result.getPk_result().getPlay().get_$160().getTitle());
                        mOneItems.add(gameids0);

                        Gameid1 gameid156 = new Gameid1();
                        gameid156.setId(result.getPk_result().getPlay().get_$156().getId());
                        gameid156.setPid(result.getPk_result().getPlay().get_$156().getPid());
                        gameid156.setOnename(result.getPk_result().getPlay().get_$156().getTitle());
                        mOneItems.add(gameid156);

                        Gameid1 gameid155 = new Gameid1();
                        gameid155.setId(result.getPk_result().getPlay().get_$155().getId());
                        gameid155.setPid(result.getPk_result().getPlay().get_$155().getPid());
                        gameid155.setOnename(result.getPk_result().getPlay().get_$155().getTitle());
                        mOneItems.add(gameid155);

                        Gameid1 gameid154 = new Gameid1();
                        gameid154.setId(result.getPk_result().getPlay().get_$154().getId());
                        gameid154.setPid(result.getPk_result().getPlay().get_$154().getPid());
                        gameid154.setOnename(result.getPk_result().getPlay().get_$154().getTitle());
                        mOneItems.add(gameid154);

                        Gameid1 gameid153 = new Gameid1();
                        gameid153.setId(result.getPk_result().getPlay().get_$153().getId());
                        gameid153.setPid(result.getPk_result().getPlay().get_$153().getPid());
                        gameid153.setOnename(result.getPk_result().getPlay().get_$153().getTitle());
                        mOneItems.add(gameid153);

                        Gameid1 gameid150 = new Gameid1();
                        gameid150.setId(result.getPk_result().getPlay().get_$150().getId());
                        gameid150.setPid(result.getPk_result().getPlay().get_$150().getPid());
                        gameid150.setOnename(result.getPk_result().getPlay().get_$150().getTitle());
                        mOneItems.add(gameid150);

                        Gameid1 gameid149 = new Gameid1();
                        gameid149.setId(result.getPk_result().getPlay().get_$149().getId());
                        gameid149.setPid(result.getPk_result().getPlay().get_$149().getPid());
                        gameid149.setOnename(result.getPk_result().getPlay().get_$149().getTitle());
                        mOneItems.add(gameid149);

                        Gameid1 gameid148 = new Gameid1();
                        gameid148.setId(result.getPk_result().getPlay().get_$148().getId());
                        gameid148.setPid(result.getPk_result().getPlay().get_$148().getPid());
                        gameid148.setOnename(result.getPk_result().getPlay().get_$148().getTitle());
                        mOneItems.add(gameid148);
                        Gameid1 gameid147 = new Gameid1();
                        gameid147.setId(result.getPk_result().getPlay().get_$147().getId());
                        gameid147.setPid(result.getPk_result().getPlay().get_$147().getPid());
                        gameid147.setOnename(result.getPk_result().getPlay().get_$147().getTitle());
                        mOneItems.add(gameid147);

                        Gameid1 gameid146 = new Gameid1();
                        gameid146.setId(result.getPk_result().getPlay().get_$146().getId());
                        gameid146.setPid(result.getPk_result().getPlay().get_$146().getPid());
                        gameid146.setOnename(result.getPk_result().getPlay().get_$146().getTitle());
                        mOneItems.add(gameid146);

                        Gameid1 gameid142 = new Gameid1();
                        gameid142.setId(result.getPk_result().getPlay().get_$142().getId());
                        gameid142.setPid(result.getPk_result().getPlay().get_$142().getPid());
                        gameid142.setOnename(result.getPk_result().getPlay().get_$142().getTitle());
                        mOneItems.add(gameid142);
                        Gameid1 gameid141 = new Gameid1();
                        gameid141.setId(result.getPk_result().getPlay().get_$141().getId());
                        gameid141.setPid(result.getPk_result().getPlay().get_$141().getPid());
                        gameid141.setOnename(result.getPk_result().getPlay().get_$141().getTitle());
                        mOneItems.add(gameid141);
                        Gameid1 gameid140 = new Gameid1();
                        gameid140.setId(result.getPk_result().getPlay().get_$140().getId());
                        gameid140.setPid(result.getPk_result().getPlay().get_$140().getPid());
                        gameid140.setOnename(result.getPk_result().getPlay().get_$140().getTitle());
                        mOneItems.add(gameid140);
                        Gameid1 gameid139 = new Gameid1();
                        gameid139.setId(result.getPk_result().getPlay().get_$139().getId());
                        gameid139.setPid(result.getPk_result().getPlay().get_$139().getPid());
                        gameid139.setOnename(result.getPk_result().getPlay().get_$139().getTitle());
                        mOneItems.add(gameid139);
                        Gameid1 gameid138 = new Gameid1();
                        gameid138.setId(result.getPk_result().getPlay().get_$138().getId());
                        gameid138.setPid(result.getPk_result().getPlay().get_$138().getPid());
                        gameid138.setOnename(result.getPk_result().getPlay().get_$138().getTitle());
                        mOneItems.add(gameid138);
                        Gameid1 gameid137 = new Gameid1();
                        gameid137.setId(result.getPk_result().getPlay().get_$137().getId());
                        gameid137.setPid(result.getPk_result().getPlay().get_$137().getPid());
                        gameid137.setOnename(result.getPk_result().getPlay().get_$137().getTitle());
                        mOneItems.add(gameid137);
                        Gameid1 gameid136 = new Gameid1();
                        gameid136.setId(result.getPk_result().getPlay().get_$136().getId());
                        gameid136.setPid(result.getPk_result().getPlay().get_$136().getPid());
                        gameid136.setOnename(result.getPk_result().getPlay().get_$136().getTitle());
                        mOneItems.add(gameid136);
                        Gameid1 gameid135 = new Gameid1();
                        gameid135.setId(result.getPk_result().getPlay().get_$135().getId());
                        gameid135.setPid(result.getPk_result().getPlay().get_$135().getPid());
                        gameid135.setOnename(result.getPk_result().getPlay().get_$135().getTitle());
                        mOneItems.add(gameid135);
                        Gameid1 gameid134 = new Gameid1();
                        gameid134.setId(result.getPk_result().getPlay().get_$134().getId());
                        gameid134.setPid(result.getPk_result().getPlay().get_$134().getPid());
                        gameid134.setOnename(result.getPk_result().getPlay().get_$134().getTitle());
                        mOneItems.add(gameid134);
                        Gameid1 gameid133 = new Gameid1();
                        gameid133.setId(result.getPk_result().getPlay().get_$133().getId());
                        gameid133.setPid(result.getPk_result().getPlay().get_$133().getPid());
                        gameid133.setOnename(result.getPk_result().getPlay().get_$133().getTitle());
                        mOneItems.add(gameid133);
                        Gameid1 gameid132 = new Gameid1();
                        gameid132.setId(result.getPk_result().getPlay().get_$132().getId());
                        gameid132.setPid(result.getPk_result().getPlay().get_$132().getPid());
                        gameid132.setOnename(result.getPk_result().getPlay().get_$132().getTitle());
                        mOneItems.add(gameid132);
                        Gameid1 gameid131 = new Gameid1();
                        gameid131.setId(result.getPk_result().getPlay().get_$131().getId());
                        gameid131.setPid(result.getPk_result().getPlay().get_$131().getPid());
                        gameid131.setOnename(result.getPk_result().getPlay().get_$131().getTitle());
                        mOneItems.add(gameid131);
                        Gameid1 gameid130 = new Gameid1();
                        gameid130.setId(result.getPk_result().getPlay().get_$130().getId());
                        gameid130.setPid(result.getPk_result().getPlay().get_$130().getPid());
                        gameid130.setOnename(result.getPk_result().getPlay().get_$130().getTitle());
                        mOneItems.add(gameid130);
                        Gameid1 gameid129 = new Gameid1();
                        gameid129.setId(result.getPk_result().getPlay().get_$129().getId());
                        gameid129.setPid(result.getPk_result().getPlay().get_$129().getPid());
                        gameid129.setOnename(result.getPk_result().getPlay().get_$129().getTitle());
                        mOneItems.add(gameid129);
                        Gameid1 gameid128 = new Gameid1();
                        gameid128.setId(result.getPk_result().getPlay().get_$128().getId());
                        gameid128.setPid(result.getPk_result().getPlay().get_$128().getPid());
                        gameid128.setOnename(result.getPk_result().getPlay().get_$128().getTitle());
                        mOneItems.add(gameid128);
                        Gameid1 gameid127 = new Gameid1();
                        gameid127.setId(result.getPk_result().getPlay().get_$127().getId());
                        gameid127.setPid(result.getPk_result().getPlay().get_$127().getPid());
                        gameid127.setOnename(result.getPk_result().getPlay().get_$127().getTitle());
                        mOneItems.add(gameid127);
                        Gameid1 gameid126 = new Gameid1();
                        gameid126.setId(result.getPk_result().getPlay().get_$126().getId());
                        gameid126.setPid(result.getPk_result().getPlay().get_$126().getPid());
                        gameid126.setOnename(result.getPk_result().getPlay().get_$126().getTitle());
                        mOneItems.add(gameid126);
                        Gameid1 gameid125 = new Gameid1();
                        gameid125.setId(result.getPk_result().getPlay().get_$125().getId());
                        gameid125.setPid(result.getPk_result().getPlay().get_$125().getPid());
                        gameid125.setOnename(result.getPk_result().getPlay().get_$125().getTitle());
                        mOneItems.add(gameid125);
                        Gameid1 gameid124 = new Gameid1();
                        gameid124.setId(result.getPk_result().getPlay().get_$124().getId());
                        gameid124.setPid(result.getPk_result().getPlay().get_$124().getPid());
                        gameid124.setOnename(result.getPk_result().getPlay().get_$124().getTitle());
                        mOneItems.add(gameid124);
                        Gameid1 gameid123 = new Gameid1();
                        gameid123.setId(result.getPk_result().getPlay().get_$123().getId());
                        gameid123.setPid(result.getPk_result().getPlay().get_$123().getPid());
                        gameid123.setOnename(result.getPk_result().getPlay().get_$123().getTitle());
                        mOneItems.add(gameid123);
                        Gameid1 gameid122 = new Gameid1();
                        gameid122.setId(result.getPk_result().getPlay().get_$122().getId());
                        gameid122.setPid(result.getPk_result().getPlay().get_$122().getPid());
                        gameid122.setOnename(result.getPk_result().getPlay().get_$122().getTitle());
                        mOneItems.add(gameid122);
                        Gameid1 gameid121 = new Gameid1();
                        gameid121.setId(result.getPk_result().getPlay().get_$121().getId());
                        gameid121.setPid(result.getPk_result().getPlay().get_$121().getPid());
                        gameid121.setOnename(result.getPk_result().getPlay().get_$121().getTitle());
                        mOneItems.add(gameid121);
                        Gameid1 gameid120 = new Gameid1();
                        gameid120.setId(result.getPk_result().getPlay().get_$120().getId());
                        gameid120.setPid(result.getPk_result().getPlay().get_$120().getPid());
                        gameid120.setOnename(result.getPk_result().getPlay().get_$120().getTitle());
                        mOneItems.add(gameid120);
                        Gameid1 gameid119 = new Gameid1();
                        gameid119.setId(result.getPk_result().getPlay().get_$119().getId());
                        gameid119.setPid(result.getPk_result().getPlay().get_$119().getPid());
                        gameid119.setOnename(result.getPk_result().getPlay().get_$119().getTitle());
                        mOneItems.add(gameid119);
                        Gameid1 gameid118 = new Gameid1();
                        gameid118.setId(result.getPk_result().getPlay().get_$118().getId());
                        gameid118.setPid(result.getPk_result().getPlay().get_$118().getPid());
                        gameid118.setOnename(result.getPk_result().getPlay().get_$118().getTitle());
                        mOneItems.add(gameid118);
                        Gameid1 gameid117 = new Gameid1();
                        gameid117.setId(result.getPk_result().getPlay().get_$117().getId());
                        gameid117.setPid(result.getPk_result().getPlay().get_$117().getPid());
                        gameid117.setOnename(result.getPk_result().getPlay().get_$117().getTitle());
                        mOneItems.add(gameid117);
                        Gameid1 gameid116 = new Gameid1();
                        gameid116.setId(result.getPk_result().getPlay().get_$116().getId());
                        gameid116.setPid(result.getPk_result().getPlay().get_$116().getPid());
                        gameid116.setOnename(result.getPk_result().getPlay().get_$116().getTitle());
                        mOneItems.add(gameid116);
                        Gameid1 gameid115 = new Gameid1();
                        gameid115.setId(result.getPk_result().getPlay().get_$115().getId());
                        gameid115.setPid(result.getPk_result().getPlay().get_$115().getPid());
                        gameid115.setOnename(result.getPk_result().getPlay().get_$115().getTitle());
                        mOneItems.add(gameid115);
                        Gameid1 gameid114 = new Gameid1();
                        gameid114.setId(result.getPk_result().getPlay().get_$114().getId());
                        gameid114.setPid(result.getPk_result().getPlay().get_$114().getPid());
                        gameid114.setOnename(result.getPk_result().getPlay().get_$114().getTitle());
                        mOneItems.add(gameid114);
                        Gameid1 gameid113 = new Gameid1();
                        gameid113.setId(result.getPk_result().getPlay().get_$113().getId());
                        gameid113.setPid(result.getPk_result().getPlay().get_$113().getPid());
                        gameid113.setOnename(result.getPk_result().getPlay().get_$113().getTitle());
                        mOneItems.add(gameid113);
                        Gameid1 gameid112 = new Gameid1();
                        gameid112.setId(result.getPk_result().getPlay().get_$112().getId());
                        gameid112.setPid(result.getPk_result().getPlay().get_$112().getPid());
                        gameid112.setOnename(result.getPk_result().getPlay().get_$112().getTitle());
                        mOneItems.add(gameid112);
                        Gameid1 gameid111 = new Gameid1();
                        gameid111.setId(result.getPk_result().getPlay().get_$111().getId());
                        gameid111.setPid(result.getPk_result().getPlay().get_$111().getPid());
                        gameid111.setOnename(result.getPk_result().getPlay().get_$111().getTitle());
                        mOneItems.add(gameid111);
                        Gameid1 gameid110 = new Gameid1();
                        gameid110.setId(result.getPk_result().getPlay().get_$110().getId());
                        gameid110.setPid(result.getPk_result().getPlay().get_$110().getPid());
                        gameid110.setOnename(result.getPk_result().getPlay().get_$110().getTitle());
                        mOneItems.add(gameid110);
                        Gameid1 gameid109 = new Gameid1();
                        gameid109.setId(result.getPk_result().getPlay().get_$109().getId());
                        gameid109.setPid(result.getPk_result().getPlay().get_$109().getPid());
                        gameid109.setOnename(result.getPk_result().getPlay().get_$109().getTitle());
                        mOneItems.add(gameid109);
                        Gameid1 gameid108 = new Gameid1();
                        gameid108.setId(result.getPk_result().getPlay().get_$108().getId());
                        gameid108.setPid(result.getPk_result().getPlay().get_$108().getPid());
                        gameid108.setOnename(result.getPk_result().getPlay().get_$108().getTitle());
                        mOneItems.add(gameid108);
                        Gameid1 gameid107 = new Gameid1();
                        gameid107.setId(result.getPk_result().getPlay().get_$107().getId());
                        gameid107.setPid(result.getPk_result().getPlay().get_$107().getPid());
                        gameid107.setOnename(result.getPk_result().getPlay().get_$107().getTitle());
                        mOneItems.add(gameid107);
                        Gameid1 gameid106 = new Gameid1();
                        gameid106.setId(result.getPk_result().getPlay().get_$106().getId());
                        gameid106.setPid(result.getPk_result().getPlay().get_$106().getPid());
                        gameid106.setOnename(result.getPk_result().getPlay().get_$106().getTitle());
                        mOneItems.add(gameid106);
                        Gameid1 gameid105 = new Gameid1();
                        gameid105.setId(result.getPk_result().getPlay().get_$105().getId());
                        gameid105.setPid(result.getPk_result().getPlay().get_$105().getPid());
                        gameid105.setOnename(result.getPk_result().getPlay().get_$105().getTitle());
                        mOneItems.add(gameid105);
                        Gameid1 gameid104 = new Gameid1();
                        gameid104.setId(result.getPk_result().getPlay().get_$104().getId());
                        gameid104.setPid(result.getPk_result().getPlay().get_$104().getPid());
                        gameid104.setOnename(result.getPk_result().getPlay().get_$104().getTitle());
                        mOneItems.add(gameid104);
                        Gameid1 gameid103 = new Gameid1();
                        gameid103.setId(result.getPk_result().getPlay().get_$103().getId());
                        gameid103.setPid(result.getPk_result().getPlay().get_$103().getPid());
                        gameid103.setOnename(result.getPk_result().getPlay().get_$103().getTitle());
                        mOneItems.add(gameid103);
                        Gameid1 gameid102 = new Gameid1();
                        gameid102.setId(result.getPk_result().getPlay().get_$102().getId());
                        gameid102.setPid(result.getPk_result().getPlay().get_$102().getPid());
                        gameid102.setOnename(result.getPk_result().getPlay().get_$102().getTitle());
                        mOneItems.add(gameid102);

                        Gameid1 gameid485 = new Gameid1();
                        gameid485.setId(result.getPk_result().getPlay().get_$485().getId());
                        gameid485.setPid(result.getPk_result().getPlay().get_$485().getPid());
                        gameid485.setOnename(result.getPk_result().getPlay().get_$485().getTitle());
                        mOneItems.add(gameid485);
                        Gameid1 gameid486 = new Gameid1();
                        gameid486.setId(result.getPk_result().getPlay().get_$486().getId());
                        gameid486.setPid(result.getPk_result().getPlay().get_$486().getPid());
                        gameid486.setOnename(result.getPk_result().getPlay().get_$486().getTitle());
                        mOneItems.add(gameid486);
                        Gameid1 gameid487 = new Gameid1();
                        gameid487.setId(result.getPk_result().getPlay().get_$487().getId());
                        gameid487.setPid(result.getPk_result().getPlay().get_$487().getPid());
                        gameid487.setOnename(result.getPk_result().getPlay().get_$487().getTitle());
                        mOneItems.add(gameid487);

                        Gameid1 gameid488 = new Gameid1();
                        gameid488.setId(result.getPk_result().getPlay().get_$488().getId());
                        gameid488.setPid(result.getPk_result().getPlay().get_$488().getPid());
                        gameid488.setOnename(result.getPk_result().getPlay().get_$488().getTitle());
                        mOneItems.add(gameid488);
                        Gameid1 gameid489 = new Gameid1();
                        gameid489.setId(result.getPk_result().getPlay().get_$489().getId());
                        gameid489.setPid(result.getPk_result().getPlay().get_$489().getPid());
                        gameid489.setOnename(result.getPk_result().getPlay().get_$489().getTitle());
                        mOneItems.add(gameid489);
                        Gameid1 gameid490 = new Gameid1();
                        gameid490.setId(result.getPk_result().getPlay().get_$490().getId());
                        gameid490.setPid(result.getPk_result().getPlay().get_$490().getPid());
                        gameid490.setOnename(result.getPk_result().getPlay().get_$490().getTitle());
                        mOneItems.add(gameid490);
                        Gameid1 gameid491 = new Gameid1();
                        gameid491.setId(result.getPk_result().getPlay().get_$491().getId());
                        gameid491.setPid(result.getPk_result().getPlay().get_$491().getPid());
                        gameid491.setOnename(result.getPk_result().getPlay().get_$491().getTitle());
                        mOneItems.add(gameid491);
                        Gameid1 gameid492 = new Gameid1();
                        gameid492.setId(result.getPk_result().getPlay().get_$492().getId());
                        gameid492.setPid(result.getPk_result().getPlay().get_$492().getPid());
                        gameid492.setOnename(result.getPk_result().getPlay().get_$492().getTitle());
                        mOneItems.add(gameid492);
                        Gameid1 gameid493 = new Gameid1();
                        gameid493.setId(result.getPk_result().getPlay().get_$493().getId());
                        gameid493.setPid(result.getPk_result().getPlay().get_$493().getPid());
                        gameid493.setOnename(result.getPk_result().getPlay().get_$493().getTitle());
                        mOneItems.add(gameid493);
                        Gameid1 gameid494 = new Gameid1();
                        gameid494.setId(result.getPk_result().getPlay().get_$494().getId());
                        gameid494.setPid(result.getPk_result().getPlay().get_$494().getPid());
                        gameid494.setOnename(result.getPk_result().getPlay().get_$494().getTitle());
                        mOneItems.add(gameid494);

                    }
                    initfdl();
                    showNum();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initfdl() {

        userId = getIntent().getStringExtra("userId");
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.getUserInfo")
                .addParams("user_id", userId)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(PKActivity.this, "网络错误");
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-账户信息-==", o);
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject result = jsonObject.optJSONObject("result");
                                String maxRebate = result.optString("maxRebate");
                                if (maxRebate.equals("null")) {
                                    maxRebate = "";
                                }

                                String minRebate = result.optString("minRebate");
                                if (minRebate.equals("null")) {
                                    minRebate = "";
                                }
                                if (frequency.equals("high")) {
                                    fdlv = maxRebate;
                                }
                                if (frequency.equals("low")) {
                                    fdlv = minRebate;
                                }

                                double a = Double.valueOf(fdlv);
                                String format = df.format(a);
                                tvfdl.setText("返点率\n" + format + "%");

                            } else {
                                ToastUtils.showToast(PKActivity.this, errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


    }


    @OnClick({R.id.title_left, R.id.title_right, R.id.title_left_s, R.id.tv_qd})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left_s:
                xiaoshi = true;
                finish();
                break;
            case R.id.title_left:
                OptionsPopupWindow popupWindow = new OptionsPopupWindow(PKActivity.this);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);//textView
                popupWindow.setPicker(ProvinceList, CityList, CountyList, true);
                popupWindow.setOnoptionsSelectListener(this);
                popupWindow.setCyclic(false);


                break;
            case R.id.title_right:
                refest();
                break;

            case R.id.tv_qd:
                if (zhuNum <= 0) {
                    ToastUtils.showToast(PKActivity.this, "请至少选择一注投注号码");
                    return;
                }
                xiaoshi = true;
                if (jlfdlv.equals("")) {
                    jlfdlv = fdlv;
                }
                double j = Double.valueOf(oneAmount) * dww;
                Shil ss = new Shil();
                ss.setHaoma(haoma);
                ss.setWanfa(gameplay);
                ss.setZhushu(zhuNum);
                ss.setDanjia(j + "");
                ss.setFandianl(jlfdlv);
                ss.setBeishu(beiNum);
                ss.setUnit(dww + "");
                betlist.add(ss);
                double money = 0;
                for (int m = 0; m < betlist.size(); m++) {
                    double aDouble = Double.valueOf(betlist.get(m).getDanjia());
                    int bs = Integer.valueOf(betlist.get(m).getBeishu());
                    money = money + (aDouble * betlist.get(m).getZhushu() * bs);
                }

                Intent intent = new Intent(this, BetActivity.class);
                intent.putExtra("userid", userId);
                intent.putExtra("game_id", game_id);
                intent.putExtra("play_id", play_id);
                intent.putExtra("qian", df.format(money));
                intent.putExtra("betlist", (Serializable) betlist);

                intent.putExtra("id", id);

                startActivityForResult(intent, 1);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (data == null || "".equals(data)) {
                return;
            } else {
                betlist = (ArrayList<Shil>) data.getSerializableExtra("betlist");
                pkweb.loadUrl("javascript:clearCode('" + param + "')");
            }
        }
    }

    private void showNum() {
        List<Gameid1> one = new ArrayList<>();
        ProvinceList = new ArrayList<>();
        for (int i = 0; i < mOneItems.size(); i++) {
            if (mOneItems.get(i).getPid().equals(id)) {

                String model = mOneItems.get(i).getOnename();
                Gameid1 game1 = new Gameid1();
                game1.setId(mOneItems.get(i).getId());
                game1.setPid(mOneItems.get(i).getPid());
                game1.setOnename(mOneItems.get(i).getOnename());
                one.add(game1);
                ProvinceList.add(model);
            }
        }
        CityList = new ArrayList<>();
        for (int j = 0; j < one.size(); j++) {
            ArrayList<String> l21 = new ArrayList<>();
            for (int i = 0; i < mOneItems.size(); i++) {

                if (mOneItems.get(i).getPid().equals(one.get(j).getId())) {

                    String model21 = mOneItems.get(i).getOnename();
                    l21.add(model21);

                }

            }
            if (l21.size() == 0) {
                l21.add("");
            }
            CityList.add(l21);

        }
        CountyList = new ArrayList<>();
        for (int j = 0; j < one.size(); j++) {

            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            if (CityList.get(j).get(0).equals("")) {
                ArrayList<String> lis = new ArrayList<>();
                lis.add("");
                temp.add(lis);
            }

            for (int i = 0; i < mOneItems.size(); i++) {

                if (mOneItems.get(i).getPid().equals(one.get(j).getId())) {

                    ArrayList<String> l21 = new ArrayList<>();
                    for (int k = 0; k < mOneItems.size(); k++) {
                        if (mOneItems.get(i).getId().equals(mOneItems.get(k).getPid())) {
                            String mode = mOneItems.get(k).getOnename();
                            l21.add(mode);
                        }
                    }
                    if (l21.size() == 0) {
                        l21.add("");
                    }

                    temp.add(l21);
                }
            }

            CountyList.add(temp);

        }
        gameplay = ProvinceList.get(0) + " - " + CityList.get(0).get(0) + " - " + CountyList.get(0).get(0).get(0);
        titleLeft.setText(gameplay);
        Xhff(0, 0, 0);
    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {
        Xhff(options1, option2, options3);
        ToastUtils.showToast(PKActivity.this, ProvinceList.get(options1) + CityList.get(options1).get(option2) + CountyList.get(options1).get(option2).get(options3));

    }


    private void Xhff(int options1, int option2, int options3) {
        if (CityList.get(options1).get(option2).equals("")) {
            gameplay = ProvinceList.get(options1);
            titleLeft.setText(gameplay);
        } else if (CountyList.get(options1).get(option2).get(options3).equals("")) {
            gameplay = ProvinceList.get(options1) + " - " + CityList.get(options1).get(option2);
            titleLeft.setText(gameplay);
        } else {
            gameplay = ProvinceList.get(options1) + " - " + CityList.get(options1).get(option2) + " - " + CountyList.get(options1).get(option2).get(options3);
            titleLeft.setText(gameplay);
        }
        String xuanid = null;
        String xuantitle = null;
        String xuanpid = null;
        for (int i = 0; i < mOneItems.size(); i++) {
            if (mOneItems.get(i).getOnename().equals(ProvinceList.get(options1)) && mOneItems.get(i).getPid().equals(id)) {
                xuanid = mOneItems.get(i).getId();
                xuantitle = mOneItems.get(i).getOnename();
                xuanpid = mOneItems.get(i).getPid();
                for (int j = 0; j < mOneItems.size(); j++) {
                    if (mOneItems.get(j).getOnename().equals(CityList.get(options1).get(option2)) && mOneItems.get(j).getPid().equals(xuanid)) {
                        xuanid = mOneItems.get(j).getId();
                        xuantitle = mOneItems.get(j).getOnename();
                        xuanpid = mOneItems.get(j).getPid();
                        for (int k = 0; k < mOneItems.size(); k++) {
                            if (mOneItems.get(k).getOnename().equals(CountyList.get(options1).get(option2).get(options3)) && mOneItems.get(k).getPid().equals(xuanid)) {
                                xuanid = mOneItems.get(k).getId();
                                xuantitle = mOneItems.get(k).getOnename();
                                xuanpid = mOneItems.get(k).getPid();
                                break;
                            }
                        }
                    }
                }
            }
        }
        String finalId = xuanid;
        String finalTitle = xuantitle;
        String finalXuanpid = xuanpid;
        Call call = LotteryClient.getInstance().getGamePaly(id);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(PKActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                String wanfa = null;
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONObject result = jsonObject.optJSONObject("result");
                    type = result.optJSONArray("type");
                    play = result.optJSONArray("play");

                    Pk2Result re = new Gson().fromJson(body, Pk2Result.class);
                    if (wanfa == null) {
                        List<Type2Bean> list22 = re.getResult().getType();
                        for (int i = 0; i < list22.size(); i++) {
                            if (list22.get(i).getId().equals(finalId) && list22.get(i).getTitle().equals(finalTitle) && list22.get(i).getPid().equals(finalXuanpid)) {
                                wanfa = type.get(i).toString();
                            }
                        }
                    }
                    Sh sh = new Sh();
                    if (wanfa == null) {
                        List<Play2Bean> list33 = re.getResult().getPlay();
                        for (int i = 0; i < list33.size(); i++) {
                            if (list33.get(i).getId().equals(finalId) && list33.get(i).getTitle().equals(finalTitle) && list33.get(i).getPid().equals(finalXuanpid)) {

                                JSONObject play1 = play.optJSONObject(i);
                                sh = new Gson().fromJson(play1.toString(), Sh.class);

                                bonus = list33.get(i).getBonus();
                                oneAmount = list33.get(i).getOneAmount();
                                returnAmount = list33.get(i).getReturnAmount();

                                if (list33.get(i).getReturnAmount() == null || list33.get(i).getReturnAmount().equals("null")) {
                                    returnAmount = "0";
                                    sbpk.setEnabled(false);
                                } else {
                                    sbpk.setEnabled(true);
                                }


                                game_id = list33.get(i).getGame_id();
                                play_id = list33.get(i).getId();
                                play_rules = list33.get(i).getExplain();

                                play1.remove("bingoRule");
                                String s = sh.getExplain().replaceAll("'", "");
                                s = s.replaceAll("\"","");
                                play1.put("explain",s);

                                if (sh.getCodeReg() != null){
                                    String s2 = sh.getCodeReg().toString().replaceAll("\\\\", "\\\\\\\\");
                                    play1.put("codeReg",s2);
                                }
                                wanfa = play1.toString();

                            }

                        }
                    }



                    String str = "{" + "\"action\" : \"selectCode\"" + "}";
                    JSONObject dataJson = new JSONObject(str);
                    String s1 = wanfa.substring(1, wanfa.length() - 1);
                    String s2 = dataJson.toString().substring(1, dataJson.toString().length() - 1);

                    String ss3 = "{" + "\"bingoRule\" :" + sh.getBingoRule() + "}";

                    if (sh.getBingoRule().equals("")){
                        ss3 = "{" + "\"bingoRule\" :" + "[]" + "}";
                    }



                    JSONObject ss3json = new JSONObject(ss3);
                    String si = ss3json.toString().substring(1, ss3json.toString().length() - 1);
                    param = "{" + s2 + "," + s1 + "," + si + "}";


                    initJS(param);
                    double jiangjin = Double.valueOf(bonus);

                    String format1 = df.format(jiangjin * dww);
                    tvjj.setText("奖金\n" + format1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        double a = Double.valueOf(fdlv);
        double v = (a * progress) / 100;
        String format = df.format(v);
        jlfdlv = format;
        tvfdl.setText("返点率\n" + format + "%");

        double jiangjin = Double.valueOf(bonus);

        double fdje = Double.valueOf(returnAmount);

        double v1 = (jiangjin + (fdje * (a - v) * 10)) * dww;
        tvjj.setText("奖金\n" + df.format(v1));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xiaoshi) {
            Message mssg = new Message();
            handler.sendMessage(mssg);
        }
    }

    //初始化WebView
    private void initWEB() {
        WebSettings webSettings = pkweb.getSettings();
        //设置支持javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setDefaultTextEncodingName("utf-8");// 编码方式
        pkweb.loadUrl("file:///android_asset/text/selectCode.html");
        getJsData();
    }

    //给js传递数据
    private void initJS(String param) {
        pkweb.loadUrl("javascript:appapi('" + param + "')");
    }

    //从js获取数据
    private void getJsData() {
        pkweb.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //message是获取到的数据
                if (message != null) {
                    WebviewResult resu = new Gson().fromJson(message, WebviewResult.class);
                    DecimalFormat df = new DecimalFormat("0.000");
                    zhuNum = resu.getCount();
                    double d = Double.valueOf(oneAmount);
                    double qian = zhuNum * d * dww;
                    String f = df.format(qian);
                    tzbs.setText("共" + zhuNum + "注 ¥" + f);
                    haoma = resu.getSelectExp();
                    if (zhuNum > 0) {
                        tzbs.setVisibility(View.VISIBLE);
                    } else {
                        tzbs.setVisibility(View.INVISIBLE);
                    }
                }
                result.cancel();
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            finish();
        }
        return true;
    }

    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] items = new String[]{};

        public SpinnerAdapter(final Context context,
                              final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
            this.context = context;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        R.layout.layout, parent, false);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextColor(Color.rgb(0,0,0));
            tv.setPadding(10,10,10,10);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(
                        R.layout.layout, parent, false);
            }
            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);
            tv.setText(items[position]);
            tv.setTextColor(Color.rgb(0,0,0));
            tv.setTextSize(15);
            return convertView;
        }
    }
}