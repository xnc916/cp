package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
/*
* 设置返点页面
* */
public class ModifyRebateActivity extends BaseActivity {
    private Intent intent;
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.tv_modify_title)
    TextView title;
    @BindView(R.id.et_modify_content)
    EditText content;
    private String userId;
    private String id;
    private LoadingDialog loadingDialog;

    private String maxRebate;
    private String minRebate;

    private String dmaxfan;//高频返采点
    private String dminfan;//低频返采点
    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_rebate;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        if (intent.getStringExtra("type").equals("0")){
            titleName.setText("修改高频返点");
            title.setText("高频返点");
            if (maxRebate.equals("")){
                maxRebate = "0";
            }
            String gao = "请输入"+maxRebate+"~"+dmaxfan+"范围的数";

            content.setHint(new SpannableString(gao));
        }else if (intent.getStringExtra("type").equals("1")){
            titleName.setText("修改低频返点");
            title.setText("低频返点");
            if (minRebate.equals("")){
                minRebate = "0";
            }
            String di = "请输入"+minRebate+"~"+dminfan+"范围的数";
            content.setHint(new SpannableString(di));
        }

    }

    @Override
    public void initData() {

         intent = getIntent();
         userId = intent.getStringExtra("userId");
         id = intent.getStringExtra("id");
        dmaxfan = intent.getStringExtra("dmax");
        dminfan = intent.getStringExtra("dmin");

        maxRebate = intent.getStringExtra("maxRebate");
        minRebate = intent.getStringExtra("minRebate");
    }
    @OnClick({R.id.title_left,R.id.btn_modify_rebate})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.btn_modify_rebate:
                if (intent.getStringExtra("type").equals("0")){
                    if (content.getText().toString().equals("")){
                        ToastUtils.showToast(ModifyRebateActivity.this,"请输入高频返点");
                        return;
                    }
                    setRebate(content.getText().toString().trim(),"maxRebate");
                }else if (intent.getStringExtra("type").equals("1")){
                    if (content.getText().toString().equals("")){
                        ToastUtils.showToast(ModifyRebateActivity.this,"请输入低频返点");
                        return;
                    }
                    setRebate(content.getText().toString().trim(),"minRebate");
                }
                break;
        }
    }
    //设置返点
    private void setRebate(String rebate,String type) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"加载中...");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action", "Users.setRebate")
                .addParams("user_id", userId)
                .addParams("id", id)
                .addParams("rebate", rebate)
                .addParams("rebateType", type)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s = response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(ModifyRebateActivity.this, "网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-设置返点-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                ToastUtils.showToast(ModifyRebateActivity.this, "设置成功");
                                Intent intent=new Intent();
                                String s = content.getText().toString();
                                double dd = Double.parseDouble(s);
                                intent.putExtra("rebate",Double.toString(dd));
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                ToastUtils.showToast(ModifyRebateActivity.this, errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

}
