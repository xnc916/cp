package com.xiongyuan.lottery.secondpage.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.LoginActivity;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.secondpage.activity.EventdetailsActivity;
import com.xiongyuan.lottery.secondpage.activity.IntegralActivity;
import com.xiongyuan.lottery.secondpage.bean.MoreInfo;
import com.xiongyuan.lottery.secondpage.bean.MoreResult;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class SecondFragment extends BaseFragment {
    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.tv_img_jbjjp)
    TextView tvImgJbjjp;
    @BindView(R.id.tv_img_jjzz)
    TextView tvImgJjzz;
    @BindView(R.id.tv_img_zzyhq)
    TextView tvImgZzyhq;
    @BindView(R.id.rl_sclj)
    RelativeLayout rlSclj;
    @BindView(R.id.rl_czqhb)
    RelativeLayout rlCzqhb;
    @BindView(R.id.rl_yhcs)
    RelativeLayout rlYhcs;

    @BindView(R.id.subject_iv1)
    ImageView subjectIv1;
    @BindView(R.id.subject_tv_intro1)
    TextView subjectTvIntro1;
    @BindView(R.id.subject_tv_intro_end1)
    TextView subjectTvIntroEnd1;
    @BindView(R.id.subject_tv_name1)
    TextView subjectTvName1;

    @BindView(R.id.subject_iv)
    ImageView subjectIv;
    @BindView(R.id.subject_tv_intro)
    TextView subjectTvIntro;
    @BindView(R.id.subject_tv_intro_end)
    TextView subjectTvIntroEnd;
    @BindView(R.id.subject_tv_name)
    TextView subjectTvName;

    @BindView(R.id.subject_tv_intro3)
    TextView subjectTvIntro3;
    @BindView(R.id.subject_tv_intro_end3)
    TextView subjectTvIntroEnd3;
    @BindView(R.id.subject_tv_name3)
    TextView subjectTvName3;


    private boolean isLogin = false;
    private String userId;
    private List<MoreInfo> list = new ArrayList<>();
    private SecondFragment secondFragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_second;

    }

    @Override
    public void initView() {
        titleName.setText("天天有礼");

    }

    @Override
    public void initData() {


    }

    private void initHDdata(String uid) {
        Call call = LotteryClient.getInstance().getArticle(uid, "81");
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                list.clear();
                MoreResult result = new Gson().fromJson(body, MoreResult.class);
                list = result.getResult().getData();

                subjectTvName1.setText(list.get(0).getTitle());
                String s = stampToDate(list.get(0).getStartTime());
                subjectTvName1.setTextSize(12);
                subjectTvIntro1.setText("开始时间：" + s);
                String s1 = stampToDate(list.get(0).getEndTime());
                subjectTvIntroEnd1.setTextSize(12);
                subjectTvIntroEnd1.setText("结束时间：" + s1);

                subjectTvName.setText(list.get(1).getTitle());
                subjectTvIntro.setTextSize(12);
                subjectTvIntroEnd.setTextSize(12);
                subjectTvIntro.setText("开始时间：" + stampToDate(list.get(1).getStartTime()));
                subjectTvIntroEnd.setText("结束时间：" + stampToDate(list.get(1).getEndTime()));

                subjectTvName3.setText(list.get(2).getTitle());
                subjectTvIntro3.setTextSize(12);
                subjectTvIntroEnd3.setTextSize(12);
                subjectTvIntro3.setText("开始时间：" + stampToDate(list.get(2).getStartTime()));
                subjectTvIntroEnd3.setText("结束时间：" + stampToDate(list.get(2).getEndTime()));
            }
        });
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        isLogin = event.isLogin();
        if (isLogin) {
            userId = event.getUserId();
            initHDdata(userId);
        }
        //将获取的数据展示在界面上
        Log.e("---", "---" + event);
    }

    @OnClick({R.id.rl_yhcs, R.id.rl_sclj, R.id.rl_czqhb, R.id.tv_img_jbjjp, R.id.tv_img_jjzz, R.id.tv_img_zzyhq})
    public void click(View view) {
        switch (view.getId()) {

            case R.id.rl_sclj:
                if (isLogin) {
                    Intent intent = new Intent(getActivity(), EventdetailsActivity.class);
                    intent.putExtra("title", subjectTvName1.getText());
                    intent.putExtra("content", "");
                    intent.putExtra("uid", userId);
                    startActivity(intent);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.rl_czqhb:
                if (isLogin) {
                    Intent intent2 = new Intent(getActivity(), EventdetailsActivity.class);
                    intent2.putExtra("title", subjectTvName.getText());
                    intent2.putExtra("content", "");
                    intent2.putExtra("uid", userId);
                    startActivity(intent2);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.rl_yhcs:
                if (isLogin) {
                    Intent intent2 = new Intent(getActivity(), EventdetailsActivity.class);
                    intent2.putExtra("title", subjectTvName3.getText());
                    intent2.putExtra("content", "优惠测试");
                    intent2.putExtra("uid", userId);
                    startActivity(intent2);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;
            case R.id.tv_img_jbjjp:
                ToastUtils.showToast(getContext(), "首充大礼包");
                break;
            case R.id.tv_img_jjzz:
                ToastUtils.showToast(getContext(), "充值抢红包");
                break;
            case R.id.tv_img_zzyhq:
                if (isLogin) {
                    Intent intent1 = new Intent(getContext(), IntegralActivity.class);
                    intent1.putExtra("uid", userId);
                    startActivity(intent1);
                } else {
                    Intent intents = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intents);
                }
                break;

        }
    }

    public String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt * 1000L);
        res = simpleDateFormat.format(date);
        return res;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            if (isLogin) {
                initHDdata(userId);
            }

        }
    }


}