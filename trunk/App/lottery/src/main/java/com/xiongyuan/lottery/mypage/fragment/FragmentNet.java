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
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.PayWebActivity;
import com.xiongyuan.lottery.mypage.bean.Paybean;
import com.xiongyuan.lottery.mypage.bean.Payyan;
import com.xiongyuan.lottery.mypage.bean.Payzhil;
import com.xiongyuan.lottery.mypage.bean.RechargeResult;
import com.xiongyuan.lottery.mypage.dialog.RegisterDialog;
import com.xiongyuan.lottery.mypage.view.PayBlankpopu;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


public class FragmentNet extends BaseFragment {
    @BindView(R.id.n_xk)
    TextView nXk;
    @BindView(R.id.n_money_money)
    EditText nMoneyMoney;
    @BindView(R.id.n_money_true_m)
    Button nMoneyTrueM;


    private PayBlankpopu payBlankpopu;

    private String userId;
    private ArrayList<Paybean> paylist = new ArrayList<>();
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){         // handle message
            switch (msg.what) {
                case 2:
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
                                Message message = handler.obtainMessage(2);
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
    private int p = -1;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_net;
    }

    @Override
    public void initView() {
        nMoneyMoney.addTextChangedListener(new TextWatcher()
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

    @Override
    public void initData() {
        nMoneyMoney.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
        getpay(userId);
    }

    @OnClick({R.id.net_kk, R.id.n_money_true_m})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.net_kk:
                showGoodsPopupWindow();
                break;
            case R.id.n_money_true_m:
                //timer.cancel();
                if (p < 0){
                    ToastUtils.showToast(getContext(),"请选择一种支付方式！");
                    return;
                }
                if (nMoneyMoney.getText().toString().equals("")){
                    ToastUtils.showToast(getContext(),"请输入充值金额！");
                    return;
                }

                Call call = LotteryClient.getInstance().getPayjiao(userId,paylist.get(p).getName(),nMoneyMoney.getText().toString());
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

                            Intent intent = new Intent(getActivity(), PayWebActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);

                            //timer.schedule(timerTask, 0, 5000);//定时每秒执行一次
                            Message message = handler.obtainMessage(2);
                            handler.sendMessageDelayed(message, 5000);
                        }else {
                            ToastUtils.showToast(getContext(),"请重试");
                        }

                    }
                });

                break;
        }
    }


    private void showGoodsPopupWindow(){

        if (payBlankpopu==null) {
            payBlankpopu = new PayBlankpopu(activity, paylist);
        }
        payBlankpopu.show(new PayBlankpopu.OnConfirmListener() {
            @Override
            public void onConfirm(int number) {
                p = number;
                nXk.setText(paylist.get(number).getTitle());
                payBlankpopu.dismiss();
            }
        });

    }




//   MyTimerTaskq timerTask = new MyTimerTaskq();
//    Timer timer = new Timer(true);
//
//
//    //定时任务,定时发送message
//    private class MyTimerTaskq extends TimerTask {
//        @Override
//        public void run() {
//            Message message = new Message();
//            message.what = 2;
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
                RechargeResult.ResultBean.PayWayBean.ABCBean ABC = result.getResult().getPayWay().getABC();
                RechargeResult.ResultBean.PayWayBean.ICBCBean ICBC = result.getResult().getPayWay().getICBC();
                RechargeResult.ResultBean.PayWayBean.CCBBean CCB = result.getResult().getPayWay().getCCB();
                RechargeResult.ResultBean.PayWayBean.BCOMBean BCOM = result.getResult().getPayWay().getBCOM();
                RechargeResult.ResultBean.PayWayBean.BOCBean BOC = result.getResult().getPayWay().getBOC();
                RechargeResult.ResultBean.PayWayBean.CMBBean CMB = result.getResult().getPayWay().getCMB();
                RechargeResult.ResultBean.PayWayBean.CMBCBean CMBC = result.getResult().getPayWay().getCMBC();
                RechargeResult.ResultBean.PayWayBean.CEBBBean CEBB = result.getResult().getPayWay().getCEBB();
                RechargeResult.ResultBean.PayWayBean.BOBBean BOB = result.getResult().getPayWay().getBOB();
                RechargeResult.ResultBean.PayWayBean.SHBBean SHB = result.getResult().getPayWay().getSHB();
                RechargeResult.ResultBean.PayWayBean.NBBBean NBB = result.getResult().getPayWay().getNBB();
                RechargeResult.ResultBean.PayWayBean.HXBBean HXB = result.getResult().getPayWay().getHXB();
                RechargeResult.ResultBean.PayWayBean.CIBBean CIB = result.getResult().getPayWay().getCIB();
                RechargeResult.ResultBean.PayWayBean.PSBCBean PSBC = result.getResult().getPayWay().getPSBC();
                RechargeResult.ResultBean.PayWayBean.SPABANKBean SPABANK = result.getResult().getPayWay().getSPABANK();
                RechargeResult.ResultBean.PayWayBean.SPDBBean SPDB = result.getResult().getPayWay().getSPDB();
                RechargeResult.ResultBean.PayWayBean.ECITICBean ECITIC = result.getResult().getPayWay().getECITIC();
                RechargeResult.ResultBean.PayWayBean.HZBBean HZB = result.getResult().getPayWay().getHZB();
                RechargeResult.ResultBean.PayWayBean.GDBBean GDB = result.getResult().getPayWay().getGDB();

                Paybean p4 = new Paybean();
                p4.setName("ABC");
                p4.setType(ABC.getType());
                p4.setTitle(ABC.getTitle());
                p4.setMode(ABC.getMode());
                paylist.add(p4);

                Paybean p5 = new Paybean();
                p5.setName("ICBC");
                p5.setType(ICBC.getType());
                p5.setTitle(ICBC.getTitle());
                p5.setMode(ICBC.getMode());
                paylist.add(p5);

                Paybean p6 = new Paybean();
                p6.setName("CCB");
                p6.setType(CCB.getType());
                p6.setTitle(CCB.getTitle());
                p6.setMode(CCB.getMode());
                paylist.add(p6);

                Paybean p7 = new Paybean();
                p7.setName("BCOM");
                p7.setType(BCOM.getType());
                p7.setTitle(BCOM.getTitle());
                p7.setMode(BCOM.getMode());
                paylist.add(p7);

                Paybean p8 = new Paybean();
                p8.setName("BOC");
                p8.setType(BOC.getType());
                p8.setTitle(BOC.getTitle());
                p8.setMode(BOC.getMode());
                paylist.add(p8);

                Paybean p9 = new Paybean();
                p9.setName("CMB");
                p9.setType(CMB.getType());
                p9.setTitle(CMB.getTitle());
                p9.setMode(CMB.getMode());
                paylist.add(p9);

                Paybean p10 = new Paybean();
                p10.setName("CMBC");
                p10.setType(CMBC.getType());
                p10.setTitle(CMBC.getTitle());
                p10.setMode(CMBC.getMode());
                paylist.add(p10);

                Paybean p11 = new Paybean();
                p11.setName("CEBB");
                p11.setType(CEBB.getType());
                p11.setTitle(CEBB.getTitle());
                p11.setMode(CEBB.getMode());
                paylist.add(p11);

                Paybean p12 = new Paybean();
                p12.setName("BOB");
                p12.setType(BOB.getType());
                p12.setTitle(BOB.getTitle());
                p12.setMode(BOB.getMode());
                paylist.add(p12);

                Paybean p13 = new Paybean();
                p13.setName("SHB");
                p13.setType(SHB.getType());
                p13.setTitle(SHB.getTitle());
                p13.setMode(SHB.getMode());
                paylist.add(p13);

                Paybean p14 = new Paybean();
                p14.setName("NBB");
                p14.setType(NBB.getType());
                p14.setTitle(NBB.getTitle());
                p14.setMode(NBB.getMode());
                paylist.add(p14);

                Paybean p15 = new Paybean();
                p15.setName("HXB");
                p15.setType(HXB.getType());
                p15.setTitle(HXB.getTitle());
                p15.setMode(HXB.getMode());
                paylist.add(p15);

                Paybean p16 = new Paybean();
                p16.setName("CIB");
                p16.setType(CIB.getType());
                p16.setTitle(CIB.getTitle());
                p16.setMode(CIB.getMode());
                paylist.add(p16);

                Paybean p17 = new Paybean();
                p17.setName("PSBC");
                p17.setType(PSBC.getType());
                p17.setTitle(PSBC.getTitle());
                p17.setMode(PSBC.getMode());
                paylist.add(p17);

                Paybean p18 = new Paybean();
                p18.setName("SPABANK");
                p18.setType(SPABANK.getType());
                p18.setTitle(SPABANK.getTitle());
                p18.setMode(SPABANK.getMode());
                paylist.add(p18);

                Paybean p19 = new Paybean();
                p19.setName("SPDB");
                p19.setType(SPDB.getType());
                p19.setTitle(SPDB.getTitle());
                p19.setMode(SPDB.getMode());
                paylist.add(p19);

                Paybean p20 = new Paybean();
                p20.setName("ECITIC");
                p20.setType(ECITIC.getType());
                p20.setTitle(ECITIC.getTitle());
                p20.setMode(ECITIC.getMode());
                paylist.add(p20);

                Paybean p21 = new Paybean();
                p21.setName("HZB");
                p21.setType(HZB.getType());
                p21.setTitle(HZB.getTitle());
                p21.setMode(HZB.getMode());
                paylist.add(p21);

                Paybean p22 = new Paybean();
                p22.setName("GDB");
                p22.setType(GDB.getType());
                p22.setTitle(GDB.getTitle());
                p22.setMode(GDB.getMode());
                paylist.add(p22);
            }
        });

    }


}
