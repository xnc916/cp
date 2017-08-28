package com.xiongyuan.lottery.mypage.fragment;


import android.content.Intent;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.mypage.activity.QRCodeActivity;
import com.xiongyuan.lottery.mypage.bean.UserInfo;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017-05-12.
 */

public class FragmentProxyAccount extends BaseFragment {
    private String userId;
    @BindView(R.id.et_proxy_account)
    EditText etProxyAccount;
    @BindView(R.id.et_proxy_pwd)
    EditText etProxyPwd;
    @BindView(R.id.et_proxy_repwd)
    EditText etProxyRePwd;
    @BindView(R.id.et_proxy_max_rebate)
    EditText etProxyMaxRebate;
    @BindView(R.id.et_proxy_min_rebate)
    EditText etProxyMinRebate;
    @BindView(R.id.rg)
    RadioGroup radioGroup;
    @BindView(R.id.rb_ph)
    RadioButton rbPh;
    @BindView(R.id.rb_dl)
    RadioButton rbDl;
    private String type;
    private LoadingDialog loadingDialog;

    private String maxfan;//高频返采点
    private String minfan;//低频返采点

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page_proxy_account;
    }

    @Override
    public void initView() {


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_dl:
                        type = "0";
                        break;
                    case R.id.rb_ph:
                        type = "1";
                        break;
                }
            }
        });
        if (rbDl.isChecked()) {
            type = "0";
        } else if (rbPh.isChecked()) {
            type = "1";
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
    }

    @Override
    public void initData() {

        Call call = LotteryClient.getInstance().getUserInfo(userId);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(getContext(),e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserInfo userInfo = new Gson().fromJson(body,UserInfo.class);
                maxfan = userInfo.getResult().getMaxRebate();
                minfan = userInfo.getResult().getMinRebate();

                etProxyMaxRebate.setHint("请输入0~"+maxfan+"范围的数");
                etProxyMinRebate.setHint("请输入0~"+minfan+"范围的数");
            }
        });
    }

    @OnClick({R.id.btn_generate_links, R.id.btn_proxy_account})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_generate_links:
                if (etProxyMaxRebate.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "高频彩返点不能为空");
                    return;
                }
                if (etProxyMinRebate.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "高频彩返点不能为空");
                    return;
                }
                getRegCode(etProxyMaxRebate.getText().toString().trim(), etProxyMinRebate.getText().toString().trim(), type);

                break;
            case R.id.btn_proxy_account:
                if (etProxyAccount.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "帐号不能为空");
                    return;
                }
                if (etProxyPwd.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "密码不能为空");
                    return;
                }
                if (etProxyRePwd.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "请再次输入密码");
                    return;
                }
                if (etProxyMaxRebate.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "高频彩返点不能为空");
                    return;
                }
                if (etProxyMinRebate.getText().toString().isEmpty()) {
                    ToastUtils.showToast(getActivity(), "高频彩返点不能为空");
                    return;
                }
                getRegister(etProxyAccount.getText().toString().trim(), etProxyPwd.getText().toString().trim(), etProxyRePwd.getText().toString().trim(),
                        etProxyMaxRebate.getText().toString().trim(), etProxyMinRebate.getText().toString().trim(), type);
                break;
        }
    }

    //代理注册新用户
    private void getRegister(String account, String pwd, String repwd, String max, String min, String type) {
        if (loadingDialog != null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(getActivity(), "加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.register")
                .addParams("user_id", userId)
                .addParams("maxRebate", max)
                .addParams("minRebate", min)
                .addParams("type", type)
                .addParams("username", account)
                .addParams("password", pwd)
                .addParams("rePassword", repwd)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(getActivity(), "网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==--==", o + "---");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                ToastUtils.showToast(getActivity(),"已添加新用户");
                            } else {
                                ToastUtils.showToast(getActivity(), errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    //生成注册链接
    private void getRegCode(String max, String min, String type) {
        if (loadingDialog != null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(getActivity(), "加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "RegCode.add")
                .addParams("user_id", userId)
                .addParams("maxRebate", max)
                .addParams("minRebate", min)
                .addParams("type", type)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(getActivity(), "网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==--==", o + "---");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject object = jsonObject.optJSONObject("result");
                                Intent intent = new Intent(getActivity(), QRCodeActivity.class);
                                intent.putExtra("url",object.optString("regUrl"));
                                intent.putExtra("max",etProxyMaxRebate.getText().toString());
                                intent.putExtra("min",etProxyMinRebate.getText().toString());
                                startActivity(intent);
                            } else {
                                ToastUtils.showToast(getActivity(), errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


}