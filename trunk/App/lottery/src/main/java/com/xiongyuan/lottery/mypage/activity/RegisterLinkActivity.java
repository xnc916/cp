package com.xiongyuan.lottery.mypage.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.bean.RegisterLinkResult;
import com.xiongyuan.lottery.mypage.dialog.RegisterDialog;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.OkHttpClient;

public class RegisterLinkActivity extends BaseActivity {


    @BindView(R.id.title_name)
    TextView titleName;


    @BindView(R.id.register_et_num_hight)
    EditText hightet;
    @BindView(R.id.register_et_num_short)
    EditText shortet;


    @BindView(R.id.regiter_link__bt_true)
    Button button;

    @BindView(R.id.register_link_rg)
    RadioGroup radiogroup;
    @BindView(R.id.register_link_rb_dl)
    RadioButton dlrbutton;
    @BindView(R.id.register_link_rb_ph)
    RadioButton phrbutton;

    private OkHttpClient okHttpClient;

    private String uid;
    private String maxr;
    private String minR;
    private String type = "0";

    private String maxfan;//高频返采点
    private String minfan;//低频返采点

    @Override
    public int getLayoutId() {
        ShareSDK.initSDK(this);
        return R.layout.activity_register_link;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("生成注册链接");
        String gao = "请输入0~"+maxfan+"范围的数";
        hightet.setHint(gao);
        String di = "请输入0~"+minfan+"范围的数";
        shortet.setHint(di);


        initEdit();

    }

    private void initEdit() {
        hightet.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        shortet.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    public void initData() {
        maxfan = getIntent().getStringExtra("gao");
        minfan = getIntent().getStringExtra("di");

    }
    @OnClick({R.id.title_left,R.id.regiter_link__bt_true})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.regiter_link__bt_true:
                //判断高低返点
                if (judgenum()){
                    uid = getIntent().getExtras().getString("userId");
                    maxr = hightet.getText().toString();
                    minR = shortet.getText().toString();

                    radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                            if (checkedId == dlrbutton.getId()){
                                type = "0";
                            }
                            if (checkedId == phrbutton.getId()){
                                type = "1";
                            }
                        }
                    });

                    //生成注册链接码
                    createRegister();

                }

                break;
        }
    }

    private void createRegister() {

        Call call = LotteryClient.getInstance().getRegisterLink(uid,maxr,minR,type);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(RegisterLinkActivity.this,e.getMessage());

            }

            @Override
            public void onResponseUI(Call call, String body) {

                RegisterLinkResult rlresult = new Gson().fromJson(body,RegisterLinkResult.class);

                String msg = rlresult.getResult().getCode();

                //展示注册链接生成码
                showmyDialog(msg);
            }
        });
    }

    private boolean judgenum() {
        double hightd = -1;
        double shortd = -1;
        String hights = hightet.getText().toString();
        if (!hights.equals("") && !hights.equals(null)){
            hightd = Double.valueOf(hights);
        }
        String shorts = shortet.getText().toString();
        if (!shorts.equals("") && !shorts.equals(null)) {
            shortd = Double.valueOf(shorts);
        }
        double gao = Double.valueOf(maxfan);
        double di = Double.valueOf(minfan);

        if (hightd < 0 | hightd >gao | shortd < 0 | shortd > di){
            if (hightd < 0 | hightd >gao){
                hightet.setText("");
                ToastUtils.showToast(this,"请根据提示输入高频彩返点");
            }
            if (shortd < 0 | shortd > di){
                shortet.setText("");
                ToastUtils.showToast(this,"请根据提示输入低频彩返点");
            }

            return false;
        }
        return true;
    }

    private void showmyDialog(String msg) {
        RegisterDialog.Builder builder = new RegisterDialog.Builder(this);
        builder.setTitle("注册链接码");
        builder.setMessage(msg);
        String shares = "#猜猜乐#"+msg;
        builder.setcancelText("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setshareText("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showShare(shares);
            }
        });
        builder.create().show();
    }

    private void showShare(String share) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");

        // text是分享文本，所有平台都需要这个字段
        oks.setText(share);


        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }
}

