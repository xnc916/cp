package com.xiongyuan.lottery;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.mypage.bean.User;
import com.xiongyuan.lottery.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView textView;
    @BindView(R.id.lg_mobile)
    EditText lgMobile;
    @BindView(R.id.lg_password)
    EditText lgPassWord;
    @BindView(R.id.checkbox)
    CheckBox checkBox;
    @BindView(R.id.checkBox2)
    CheckBox cc;
    private LoadingDialog loadingDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        textView.setText("登  录");
        if (CachePreferences.getUser().isIfji().equals("1")){
            cc.setChecked(true);
        }else {
            cc.setChecked(false);
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码
                    lgPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    lgPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        cc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    //如果选中，显示密码

                    User user = new User();
                    user.setIfji("1");
                    user.setUser_url(CachePreferences.getUser().getUser_url());
                    CachePreferences.setUser(user);
                }else{
                    //否则隐藏密码

                    User user = new User();
                    user.setIfji("2");
                    user.setUser_url(CachePreferences.getUser().getUser_url());
                    CachePreferences.setUser(user);

                }

            }
        });
    }

    @Override
    public void initData() {
        if (CachePreferences.getUser().getUser_name() != null){
            lgMobile.setText(CachePreferences.getUser().getUser_name());
        }
        if (CachePreferences.getUser().getUser_password() != null){
            lgPassWord.setText(CachePreferences.getUser().getUser_password());
        }
    }
    @OnClick({R.id.title_left,R.id.lg_bt_login})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.lg_bt_login:
                if (lgMobile.getText().toString().isEmpty()){
                    ToastUtils.showToast(LoginActivity.this,"用户名不能为空");
                    return;
                }
                if (lgPassWord.getText().toString().isEmpty()){
                    ToastUtils.showToast(LoginActivity.this,"密码不能为空");
                    return;
                }
                getLogin(lgMobile.getText().toString().trim(),lgPassWord.getText().toString().trim());

                break;
        }
    }
    //登录
    public void  getLogin(String name, String pwd){
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"正在登录");
            loadingDialog.show();
        }

        Call call = LotteryClient.getInstance().getLogin(name,pwd);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(LoginActivity.this,e.getMessage());

            }

            @Override
            public void onResponseUI(Call call, String body) {
                Log.e("+++++++++++",body);
                User user = new User();
                if (loadingDialog != null)
                    loadingDialog.dismiss();
                try {

                    JSONObject jsonObject=new JSONObject(body);
                    String errormsg = jsonObject.optString("errormsg");
                    String remote = jsonObject.optString("REMOTE");

                    if (remote != null){
                        user.setUser_url(remote+"?c=json&");
                    }else {
                        user.setUser_url(CachePreferences.getUser().getUser_url());
                    }
                    if (errormsg.equals("")){
                        JSONObject result = jsonObject.optJSONObject("result");
                        String user_id = result.optString("user_id");
                        String time = jsonObject.optString("TIME");
                        user.setUser_id(user_id);
                        user.setUser_name(name);
                        user.setUser_password(pwd);
                        user.setIfji(CachePreferences.getUser().isIfji());
                        CachePreferences.setUser(user);
                        EventBus.getDefault().postSticky(new LoginEvent(user_id,time,true));
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }else{
                        ToastUtils.showToast(LoginActivity.this,errormsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



//        OkHttpUtils.post()
//                .url(CachePreferences.getUser().getUser_url())
//                .addParams("action","Users.login")
//                .addParams("username",name)
//                .addParams("password",pwd)
//                .build()
//                .execute(new Callback<String>() {
//                    @Override
//                    public String parseNetworkResponse(Response response, int i) throws Exception {
//                        String s= response.body().string();
//                        return s;
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int i) {
//                        ToastUtils.showToast(LoginActivity.this,"网络错误");
//                        if (loadingDialog != null)
//                            loadingDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onResponse(String o, int i) {
//                        Log.e("==-登录-==", o+"---");
//                        if (loadingDialog != null)
//                            loadingDialog.dismiss();
//                        try {
//
//
//
//                            JSONObject jsonObject=new JSONObject(o);
//                            String errormsg = jsonObject.optString("errormsg");
//                            if (errormsg.equals("")){
//
//                                JSONObject result = jsonObject.optJSONObject("result");
//                                String user_id = result.optString("user_id");
//                                String time = jsonObject.optString("TIME");
//                                User user = new User();
//                                user.setUser_id(user_id);
//                                user.setUser_name(name);
//                                user.setUser_password(pwd);
//                                user.setIfji(CachePreferences.getUser().isIfji());
//                                user.setUser_url(CachePreferences.getUser().getUser_url());
//                                CachePreferences.setUser(user);
//                                EventBus.getDefault().postSticky(new LoginEvent(user_id,time,true));
//                                Intent intent = new Intent();
//                                setResult(RESULT_OK, intent);
//                                finish();
//                            }else{
//                                ToastUtils.showToast(LoginActivity.this,errormsg);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });

    }
}
