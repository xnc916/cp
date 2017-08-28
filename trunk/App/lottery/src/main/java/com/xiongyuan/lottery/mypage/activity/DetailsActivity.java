package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.customview.LoadingDialog;
import com.xiongyuan.lottery.mypage.bean.DetailsItemBean;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class DetailsActivity extends BaseActivity {
    @BindView(R.id.title_name)
    TextView textView;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.linear2)
    LinearLayout linear2;
    @BindView(R.id.linear2_1)
    LinearLayout linear2_1;
    @BindView(R.id.linear3)
    LinearLayout linear3;
    @BindView(R.id.linear4)
    RelativeLayout linear4;
    @BindView(R.id.lv_details)
    SwipeMenuListView listView;
    @BindView(R.id.tv_details_title)
    TextView title;
    @BindView(R.id.et_details_content)
    EditText content;
    @BindView(R.id.tv_details_tv)
    TextView tv;
    @BindView(R.id.tv_details_tv1)
    TextView tv1;
    @BindView(R.id.tv_details_tv2)
    TextView tv2;
    @BindView(R.id.tv_details_tv3)
    TextView tv3;
    @BindView(R.id.et_details_et)
    EditText et;
    @BindView(R.id.et_details_et1)
    EditText et1;
    @BindView(R.id.et_details_et2)
    EditText et2;
    @BindView(R.id.et_details_et3)
    EditText et3;
    @BindView(R.id.btn_details)
    Button btn;
    @BindView(R.id.btn_details1)
    Button btn1;
    private MyAdapter myAdapter;
    private String userId;
    private LoadingDialog loadingDialog;
    private  ArrayList<DetailsItemBean> bankList;
    private AlertDialog alertDialog;
    @Override
    public int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        switch (getIntent().getIntExtra("position",0)){
            case 1:
                textView.setText("设置邮箱号");
                linear1.setVisibility(View.VISIBLE);
                title.setText("邮 箱 号");
                content.setHint(new SpannableString("请输入邮箱号"));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入正确的邮箱号");
                        }else{
                            setEmail(userId,content.getText().toString().trim());
                        }
                    }
                });
                break;
            case 2:
                textView.setText("设置登录密码");
                linear2.setVisibility(View.VISIBLE);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et1.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入原始密码");
                            return;
                        }
                        if (et2.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入新密码");
                            return;
                        }
                        if (et3.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请再次输入新密码");
                            return;
                        }
                        if (et2.getText().toString().trim().equals(et3.getText().toString().trim())){
                            setPassword(userId,et1.getText().toString().trim(),et2.getText().toString().trim(),et3.getText().toString().trim());
                        }else{
                            ToastUtils.showToast(DetailsActivity.this,"两次输入的新密码不一致");
                        }
                    }
                });

                break;
            case 3:
                textView.setText("设置手机号");
                linear1.setVisibility(View.VISIBLE);
                title.setText("手 机 号");
                content.setHint(new SpannableString("请输入手机号"));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入正确的手机号");
                        }else{
                            setMobile(userId,content.getText().toString().trim());
                        }
                    }
                });
                break;
            case 4:
                textView.setText("设置交易密码");
                linear2.setVisibility(View.VISIBLE);
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et1.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入原始密码");
                            return;
                        }
                        if (et2.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入新密码");
                            return;
                        }
                        if (et3.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请再次输入新密码");
                            return;
                        }
                        if (et2.getText().toString().trim().equals(et3.getText().toString().trim())){
                            setBankPassword(userId,et1.getText().toString().trim(),et2.getText().toString().trim(),et3.getText().toString().trim());
                        }else{
                            ToastUtils.showToast(DetailsActivity.this,"两次输入的新密码不一致");
                        }
                    }
                });
                break;
            case 5:
                textView.setText("设置qq号");
                linear1.setVisibility(View.VISIBLE);
                title.setText("qq 号");
                content.setHint(new SpannableString("请输入qq号"));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (content.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入正确的qq号");
                        }else{
                            setQq(userId,content.getText().toString().trim());
                        }
                    }
                });
                break;
            case 6:
                textView.setText("绑定银行卡");
                linear2.setVisibility(View.VISIBLE);
                linear2_1.setVisibility(View.VISIBLE);
                tv1.setText("卡        号");
                tv2.setText("开户地址");
                tv3.setText("交易密码");
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                et1.setInputType(InputType.TYPE_CLASS_NUMBER);
                et2.setInputType(InputType.TYPE_CLASS_TEXT);
                et1.setHint(new SpannableString("请输入正确的银行卡卡号"));
                et2.setHint(new SpannableString("请输入银行卡开户地址"));
                et3.setHint(new SpannableString("请输入交易密码"));
                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (et.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入开户人姓名");
                            return;
                        }
                        if (et1.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入正确的银行卡卡号");
                            return;
                        }
                        if (et2.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入银行卡开户地址");
                            return;
                        }
                        if (et3.getText().toString().equals("")){
                            ToastUtils.showToast(DetailsActivity.this,"请输入交易密码");
                            return;
                        }

                        setBankCard(userId,et.getText().toString().trim(),et1.getText().toString().trim(),et2.getText().toString().trim(),et3.getText().toString().trim());

                    }
                });
                break;
            case 7:
                textView.setText("银行卡详情");
                if (bankList.toString().equals("[]")){
                    linear4.setVisibility(View.VISIBLE);
                }else{
                    myAdapter = new MyAdapter();
                    linear3.setVisibility(View.VISIBLE);
                    listView.setAdapter(myAdapter);
                }
                break;

        }
    }

    @Override
    public void initData() {
         userId = getIntent().getStringExtra("userId");
        bankList = (ArrayList<DetailsItemBean>) getIntent().getSerializableExtra("mBankList");
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(getResources().getColor(R.color.colorWhite));
                // set a icon
                //deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        //getAddressDelete(mAppList.get(position).getId());
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        alertDialog = builder.create();
                        View view = View.inflate(DetailsActivity.this, R.layout.dialog_set_pwd, null);
                        // dialog.setView(view);// 将自定义的布局文件设置给dialog
                        alertDialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题
                        EditText editText = (EditText) view.findViewById(R.id.dialog_et);
                        Button btn= (Button) view.findViewById(R.id.dialog_btn);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (editText.getText().toString().equals("")){
                                    ToastUtils.showToast(DetailsActivity.this,"请输入交易密码");
                                }else{
                                    bankCardDel(bankList.get(position).getId(),editText.getText().toString(),position);
                                }
                            }
                        });
                        alertDialog.show();

                        break;
                }
                return false;
            }
        });
    }
    //删除银行卡
    private void bankCardDel(String id,String bankPassword,int position) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.bankCardDel")
                .addParams("user_id",userId)
                .addParams("id",id)
                .addParams("bankPassword",bankPassword)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-删除银行卡-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }else{
                                ToastUtils.showToast(DetailsActivity.this,"已删除");
                                bankList.remove(position);
                                myAdapter.notifyDataSetChanged();
                                if (bankList.toString().equals("[]")){
                                    linear4.setVisibility(View.VISIBLE);
                                }
                                alertDialog.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("TAG","");
                                setResult(RESULT_OK, intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @OnClick({R.id.title_left})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
        }
    }
    //设置邮箱
    private void setEmail(String userId ,String email) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setEmail")
                .addParams("user_id",userId)
                .addParams("email",email)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-邮箱-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            Intent intent = new Intent();
                            intent.putExtra("TAG","");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    //设置手机号
    private void setMobile(String userId ,String mobile) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setMobile")
                .addParams("user_id",userId)
                .addParams("mobile",mobile)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-手机号-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            Intent intent = new Intent();
                            intent.putExtra("TAG","");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    //设置qq号
    private void setQq(String userId ,String qq) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setQq")
                .addParams("user_id",userId)
                .addParams("qq",qq)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-qq号-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {

                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            Intent intent = new Intent();
                            intent.putExtra("TAG","");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    //设置登录密码
    private void setPassword(String userId ,String pwd,String pwd1,String pwd2) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setPassword")
                .addParams("user_id",userId)
                .addParams("password",pwd)
                .addParams("new_password",pwd1)
                .addParams("renew_password",pwd2)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-密码-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            EventBus.getDefault().postSticky(new LoginEvent(false));
                            Intent intent = new Intent();
                            intent.putExtra("TAG","pwd");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    //设置交易密码
    private void setBankPassword(String userId ,String pwd,String pwd1,String pwd2) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setBankPassword")
                .addParams("user_id",userId)
                .addParams("bankPassword",pwd)
                .addParams("new_bank_password",pwd1)
                .addParams("renew_bank_password",pwd2)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-交易密码-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            ToastUtils.showToast(DetailsActivity.this,"设置成功");
                            Intent intent = new Intent();
                            intent.putExtra("TAG","");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    //设置银行卡
    private void setBankCard(String userId ,String accountName,String bankCard,String bankAddress,String bankPassword) {
        if(loadingDialog!=null)
            loadingDialog.show();
        else {
            loadingDialog = new LoadingDialog(this,"请稍后");
            loadingDialog.show();
        }
        OkHttpUtils.post()
                .url(CachePreferences.getUser().getUser_url())
                .addParams("action","Users.setBankCard")
                .addParams("user_id",userId)
                .addParams("accountName",accountName)
                .addParams("bankCard",bankCard)
                .addParams("bankAddress",bankAddress)
                .addParams("bankPassword",bankPassword)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int i) throws Exception {
                        String s= response.body().string();
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e, int i) {
                        ToastUtils.showToast(DetailsActivity.this,"网络错误");
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-设置银行卡-==", o);
                        if (loadingDialog != null)
                            loadingDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (!errormsg.equals("")){
                                ToastUtils.showToast(DetailsActivity.this,errormsg);
                            }
                        } catch (JSONException e) {
                            ToastUtils.showToast(DetailsActivity.this,"设置成功");
                            Intent intent = new Intent();
                            intent.putExtra("TAG","");
                            setResult(RESULT_OK, intent);
                            finish();
                        }

                    }
                });
    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bankList.size();
        }

        @Override
        public Object getItem(int position) {
            return bankList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(DetailsActivity.this, R.layout.fragment_details_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!bankList.toString().equals("[]")){
                viewHolder.accountName.setText(bankList.get(position).getAccountName());
                viewHolder.cardNum.setText(bankList.get(position).getCardNum());
                viewHolder.bankName.setText(bankList.get(position).getBankName());
                viewHolder.bankAddress.setText(bankList.get(position).getBankAddress());
            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_details_accountName)
            TextView accountName;
            @BindView(R.id.tv_details_cardNum)
            TextView cardNum;
            @BindView(R.id.tv_details_bankName)
            TextView bankName;
            @BindView(R.id.tv_details_bankAddress)
            TextView bankAddress;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
