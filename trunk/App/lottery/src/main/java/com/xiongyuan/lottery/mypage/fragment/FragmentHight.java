package com.xiongyuan.lottery.mypage.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.PayerwActivity;
import com.xiongyuan.lottery.mypage.bean.Paybean;
import com.xiongyuan.lottery.mypage.bean.Payyan;
import com.xiongyuan.lottery.mypage.bean.Payzhil;
import com.xiongyuan.lottery.mypage.bean.RechargeResult;
import com.xiongyuan.lottery.mypage.dialog.RegisterDialog;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class FragmentHight extends BaseFragment {

    @BindView(R.id.wei_z)
    RelativeLayout weiZ;
    @BindView(R.id.zhi_z)
    RelativeLayout zhiZ;
    @BindView(R.id.qq_z)
    RelativeLayout qqZ;
    @BindView(R.id.draw_money_money)
    EditText drawMoneyMoney;
    @BindView(R.id.draw_money_true_m)
    Button drawMoneyTrueM;

    private String userId;
    private ArrayList<Paybean> paylist = new ArrayList<>();
    @BindView(R.id.wei_tv)
    TextView weitv;
    @BindView(R.id.zhi_tv)
    TextView zhitv;
    @BindView(R.id.qq_tv)
    TextView qqtv;

    int i = 1;
    private int p = -1;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){         // handle message
            switch (msg.what) {
                case 1:
                    Call call = LotteryClient.getInstance().getPayjieg(userId,log_id);
                    call.enqueue(new UICallBack() {
                        @Override
                        public void onFailureUI(Call call, IOException e) {
                            ToastUtils.showToast(getContext(),e.getMessage());
                        }

                        @Override
                        public void onResponseUI(Call call, String body) {
                            Payyan result = new Gson().fromJson(body,Payyan.class);

                            if (result.getResult().getStatus().equals("wait")){
                                Message message = handler.obtainMessage(1);
                                handler.sendMessageDelayed(message, 5000);
                            }

                            if (result.getResult().getStatus().equals("fail")){
                                //timer.cancel();
                                RegisterDialog.Builder builder = new RegisterDialog.Builder(getContext());
                                builder.setTitle("支付失败");
                                builder.setMessage("请重新支付");

                                builder.setcancelText("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.setshareText("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }
                            if (result.getResult().getStatus().equals("success")){
                                //timer.cancel();

                                RegisterDialog.Builder builder = new RegisterDialog.Builder(getContext());
                                builder.setTitle("支付成功");
                                builder.setMessage("时间："+result.getResult().getBackTime());

                                builder.setcancelText("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.setshareText("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.create().show();
                            }

                        }
                    });

                    break;
            }

            super.handleMessage(msg);
        }
    };
    private String log_id;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fragment_hight;
    }

    @Override
    public void initView() {
        drawMoneyMoney.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getpay(userId);
    }


    @Override
    public void initData() {
        drawMoneyMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @OnClick({R.id.wei_z, R.id.zhi_z, R.id.qq_z,R.id.draw_money_true_m})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.wei_z:
                slidingImagmentBackground();
                weiZ.setBackgroundColor(R.color.contentColor);
                p = 0;
                break;
            case R.id.zhi_z:
                slidingImagmentBackground();
                zhiZ.setBackgroundColor(R.color.contentColor);
                p = 1;
                break;
            case R.id.qq_z:
                slidingImagmentBackground();
                qqZ.setBackgroundColor(R.color.contentColor);
                p = 2;
                break;
            case R.id.draw_money_true_m:
                if (p < 0){
                    ToastUtils.showToast(getContext(),"请选择一种支付方式！");
                    return;
                }

                if (drawMoneyMoney.getText().toString().equals("")){
                    ToastUtils.showToast(getContext(),"请输入充值金额！");
                    return;
                }

                Call call = LotteryClient.getInstance().getPayjiao(userId,paylist.get(p).getName(),drawMoneyMoney.getText().toString());
                call.enqueue(new UICallBack() {


                    @Override
                    public void onFailureUI(Call call, IOException e) {
                        ToastUtils.showToast(getContext(),e.getMessage());
                    }

                    @Override
                    public void onResponseUI(Call call, String body) {

                        Payzhil result = new Gson().fromJson(body,Payzhil.class);
                        if (result.getResult().isStatus()){
                            String url = result.getResult().getSrc();
                            log_id = result.getResult().getLog_id();

                            Intent intent = new Intent(getActivity(), PayerwActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);

                            //timer.schedule(timerTask, 0, 5000);//定时每秒执行一次
                            Message message = handler.obtainMessage(1);
                            handler.sendMessageDelayed(message, 5000);
                        }else {
                            ToastUtils.showToast(getContext(),"请重试");
                        }


                    }
                });


                break;
        }
    }



    private void slidingImagmentBackground() {
        weiZ.setBackground(null);
        zhiZ.setBackground(null);
        qqZ.setBackground(null);

    }
//    MyTimerTask timerTask = new MyTimerTask();
//    Timer timer = new Timer(true);
//
//
//    //定时任务,定时发送message
//    private class MyTimerTask extends TimerTask {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 1;
//            handler.sendMessage(message);  //发送message
//        }
//    }

    public void getpay(String uid) {

        Call call = LotteryClient.getInstance().getPay(userId);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(), e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                RechargeResult result = new Gson().fromJson(body, RechargeResult.class);

                paylist.clear();
                RechargeResult.ResultBean.PayWayBean.AlipayScanBean alipay_scan = result.getResult().getPayWay().getAlipay_scan();
                RechargeResult.ResultBean.PayWayBean.QqScanBean qq_scan = result.getResult().getPayWay().getQq_scan();

                Paybean p1 = new Paybean();
                p1.setName("weixin_scan");
                p1.setMode(result.getResult().getPayWay().getWeixin_scan().getMode());
                p1.setTitle(result.getResult().getPayWay().getWeixin_scan().getTitle());
                p1.setType(result.getResult().getPayWay().getWeixin_scan().getType());
                weitv.setText(result.getResult().getPayWay().getWeixin_scan().getTitle());
                paylist.add(p1);

                Paybean p2 = new Paybean();
                p2.setName("alipay_scan");
                p2.setType(alipay_scan.getType());
                p2.setTitle(alipay_scan.getTitle());
                p2.setMode(alipay_scan.getMode());
                zhitv.setText(alipay_scan.getTitle());
                paylist.add(p2);

                Paybean p3 = new Paybean();
                p3.setName("qq_scan");
                p3.setType(qq_scan.getType());
                p3.setTitle(qq_scan.getTitle());
                p3.setMode(qq_scan.getMode());
                qqtv.setText(qq_scan.getTitle());
                paylist.add(p3);

            }
        });

    }


}