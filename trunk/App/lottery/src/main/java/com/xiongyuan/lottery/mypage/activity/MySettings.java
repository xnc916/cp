package com.xiongyuan.lottery.mypage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.LoginActivity;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.mypage.adapter.MyList1Adapter;
import com.xiongyuan.lottery.mypage.adapter.MyList2Adapter;
import com.xiongyuan.lottery.mypage.bean.User;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MySettings extends BaseActivity {
    @BindView(R.id.title_name)
    TextView titleName;
    private MyList1Adapter myList1Adapter;
    private MyList2Adapter myList2Adapter;
    @BindView(R.id.lv_1)
    ListView lv1;
    @BindView(R.id.lv_2)
    ListView lv2;
    private  boolean isLogin=false;
    private LoadingDialog loadingDialog;
    private String userId;

    private String maxfan;//高频返采点
    private String minfan;//低频返采点

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_settings;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("设置");
        myList1Adapter=new MyList1Adapter(this);
        lv1.setAdapter(myList1Adapter);
        myList2Adapter=new MyList2Adapter(this);
        lv2.setAdapter(myList2Adapter);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        //跳转到切换线路页面
                        if (isLogin){
//                        OkHttpUtils.post()
//                                .url(CachePreferences.getUser().getUser_url())
//                                .addParams("action","Users.loginOut")
//                                .addParams("user_id",CachePreferences.getUser().getUser_id())
//                                .build()
//                                .execute(new Callback<String>() {
//                                    @Override
//                                    public String parseNetworkResponse(Response response, int i) throws Exception {
//                                        String s= response.body().string();
//                                        return s;
//                                    }
//
//                                    @Override
//                                    public void onError(Call call, Exception e, int i) {
//
//                                    }
//
//                                    @Override
//                                    public void onResponse(String o, int i) {
//
//                                        try {
//                                            JSONObject jsonObject=new JSONObject(o);
//                                            String errormsg = jsonObject.optString("errormsg");
//                                            if (errormsg.equals("")){
//                                                EventBus.getDefault().postSticky(new LoginEvent(false));
//                                            }
//                                        } catch (JSONException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//                                });
                            Intent intent = new Intent(getBaseContext(),LineActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (isLogin){
                        //跳转到生成注册链接页面
                            Intent intent = new Intent(getBaseContext(),RegisterLinkActivity.class);
                            intent.putExtra("userId",userId);
                            intent.putExtra("gao",maxfan);
                            intent.putExtra("di",minfan);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(getBaseContext(),LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (isLogin){
                            Intent intent = new Intent(getBaseContext(),HelpCenterActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        if (isLogin){
                                AlertDialog.Builder builder = new AlertDialog.Builder(MySettings.this);
                                builder.setTitle("退出当前帐号");
                                builder.setMessage("确定退出？");
                                builder.setNegativeButton("取消", null);
                                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        getLoginOut(userId);
                                        User user = new User();
                                        user.setUser_url(CachePreferences.getUser().getUser_url());
                                        user.setUser_name(CachePreferences.getUser().getUser_name());

                                        user.setIfji(CachePreferences.getUser().isIfji());
                                        if (CachePreferences.getUser().isIfji().equals("1")) {
                                            user.setUser_password(CachePreferences.getUser().getUser_password());
                                        }
                                        CachePreferences.setUser(user);
                                    }
                                });
                                builder.show();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        isLogin = getIntent().getBooleanExtra("isLogin", false);
        userId = getIntent().getStringExtra("userId");

        maxfan = getIntent().getStringExtra("gao");
        minfan = getIntent().getStringExtra("di");
    }

    @OnClick({R.id.title_left})
    public void click(View view){
        switch (view.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
    //退出登录
    public void  getLoginOut( String id){
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"加载中");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.loginOut")
                .addParams("user_id",id)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(MySettings.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==--==", o+"---");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")){
                                EventBus.getDefault().postSticky(new LoginEvent(false));
                                finish();
                            }else{
                                ToastUtils.showToast(MySettings.this,errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
}
