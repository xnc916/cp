package com.xiongyuan.lottery.mypage.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.LoginActivity;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.activity.CashTransactionActivity;
import com.xiongyuan.lottery.mypage.activity.MyMessageActivity;
import com.xiongyuan.lottery.mypage.activity.MySettings;
import com.xiongyuan.lottery.mypage.activity.PersonalInformationActivity;
import com.xiongyuan.lottery.mypage.activity.ProxyAccountActivity;
import com.xiongyuan.lottery.mypage.activity.RechargeActivity;
import com.xiongyuan.lottery.mypage.activity.RecordNumberActivity;
import com.xiongyuan.lottery.mypage.activity.StatisticsTodayActivity;
import com.xiongyuan.lottery.mypage.activity.TeamReportActivity;
import com.xiongyuan.lottery.mypage.activity.TeamTableActivity;
import com.xiongyuan.lottery.mypage.activity.WithdrawalActivity;
import com.xiongyuan.lottery.mypage.adapter.MyAdapter;
import com.xiongyuan.lottery.mypage.bean.Paybean;
import com.xiongyuan.lottery.mypage.bean.UserInfo;
import com.xiongyuan.lottery.mypage.dialog.RegisterDialog;
import com.xiongyuan.lottery.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class MyCenterFragment extends BaseFragment {
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.expandablelistview)
    ExpandableListView listView;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_balance)
    TextView tvUserBalance;
    @BindView(R.id.tv_user_maxRebate)
    TextView tvUserMaxRebate;
    @BindView(R.id.tv_user_minRebate)
    TextView tvUserMinRebate;
    private boolean isLogin = false;
    private String userId;
    private String serviceHotline;
    private MyAdapter myAdapter;
    private ArrayList<Map<String, Integer>> list;
    private int[] imagesIds = new int[]{R.mipmap.item2, R.mipmap.item3, R.mipmap.item4, R.mipmap.item5, R.mipmap.item6};
    private String[] itemNames = new String[]{"账户详情", "个人报表", "投注记录", "我的消息", "我的客服"};

    private String maxfan;//高频返采点
    private String minfan;//低频返采点


    private ArrayList<Paybean> paylist = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_my;
    }

    @Override
    public void initView() {
        titleName.setText("帐号管理");
        titleLeft.setVisibility(View.GONE);
        titleRight.setVisibility(View.VISIBLE);
        Drawable drawable= getResources().getDrawable(R.mipmap.comm_btn_setting_on);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        titleRight.setCompoundDrawables(drawable,null,null,null);
        myAdapter = new MyAdapter(getActivity(), list);
        listView.setAdapter(myAdapter);
        listView.setGroupIndicator(null);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Map<String, Integer> map = list.get(groupPosition);
                for (Map.Entry<String, Integer> m : map.entrySet()) {
                    if (m.getKey().equals("我的消息")) {
                        if (isLogin) {
                            Intent intent = new Intent(getActivity(), MyMessageActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    } else if (m.getKey().equals("个人报表")) {
                        if (isLogin) {
                            Intent intent = new Intent(getActivity(), StatisticsTodayActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                        return true;
                    } else if (m.getKey().equals("我的客服")) {
                        return true;
                    }
                }
                return false;

            }
        });

        myAdapter.setOnExpandItemClickListener(new MyAdapter.onExpandItemClickListener() {
            @Override
            public void onItemClick(View v, String tag) {

                if (tag.equals("个人资料")) {
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                        intent.putExtra("tag", 0);
                        startActivityForResult(intent, 3);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, 1);
                    }

                } else if (tag.equals("积分记录")) {
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                        intent.putExtra("tag", 2);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivityForResult(intent, 2);
                    }
                } else if (tag.equals("代理开户")) {
                    Intent intent = new Intent(getActivity(), ProxyAccountActivity.class);
                    startActivity(intent);
                } else if (tag.equals("团队统计")) {
                    Intent intent = new Intent(getActivity(), TeamReportActivity.class);
                    startActivity(intent);
                } else if (tag.equals("购彩记录")) {
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), CashTransactionActivity.class);
                        intent.putExtra("tag", 0);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                } else if (tag.equals("追号记录")) {
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), RecordNumberActivity.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                } else if (tag.equals("中奖记录")) {
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), CashTransactionActivity.class);
                        intent.putExtra("tag", 5);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                } else if (tag.equals("我的客服")) {
                    RegisterDialog.Builder builder = new RegisterDialog.Builder(getActivity());
                    builder.setTitle(serviceHotline);
                    builder.setMessage("");
                    builder.setcancelText("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setshareText("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:" + serviceHotline);
                            intent.setData(data);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }else if (tag.equals("帐变记录")){
                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                        intent.putExtra("tag",1);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }else if(tag.equals("团队报表")){

                    if (isLogin) {
                        Intent intent = new Intent(getActivity(), TeamTableActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }



            }
        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        isLogin = event.isLogin();
        if (isLogin) {
            userId = event.getUserId();
            getUserData(userId);

        } else {
            initViews();
        }
    }



    private void initViews() {
        tvUserName.setText(getResources().getText(R.string.un_user_name));
        tvUserBalance.setText(getResources().getText(R.string.un_money));
        tvUserMaxRebate.setText(getResources().getText(R.string.un_money));
        tvUserMinRebate.setText(getResources().getText(R.string.un_money));
        if (list != null) {
            list.clear();
        }
        for (int i = 0; i < 4; i++) {
            if (listView.isGroupExpanded(i)) {
                listView.collapseGroup(i);
            }
        }
        for (int i = 0; i < itemNames.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(itemNames[i], imagesIds[i]);
            list.add(map);
        }
        myAdapter.refresh(list, "");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                    intent.putExtra("tag", 0);
                    startActivity(intent);
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                    intent.putExtra("tag", 1);
                    startActivity(intent);
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    for (int i = 0; i < 4; i++) {
                        if (listView.isGroupExpanded(i)) {
                            listView.collapseGroup(i);
                        }
                    }
                }
                break;
            default:
        }

    }

    //用户信息
    private void getUserData(String userId) {
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
                        ToastUtils.showToast(getActivity(), "网络错误");
                    }

                    @Override
                    public void onResponse(String o, int i) {
                        Log.e("==-账户信息-==", o);
                        try {
                            JSONObject jsonObject = new JSONObject(o);
                            String errormsg = jsonObject.optString("errormsg");
                            if (errormsg.equals("")) {
                                JSONObject result = jsonObject.optJSONObject("result");
                                tvUserName.setText(result.optString("username"));

                                UserInfo info = new Gson().fromJson(o,UserInfo.class);

                                DecimalFormat df = new DecimalFormat("#.###");
                                String s = df.format(info.getResult().getBalance());
                                tvUserBalance.setText(s);
                                String maxRebate = result.optString("maxRebate");
                                if (maxRebate.equals("null")) {
                                    maxRebate = "";
                                }
                                tvUserMaxRebate.setText(maxRebate);
                                maxfan = maxRebate;
                                String minRebate = result.optString("minRebate");
                                if (minRebate.equals("null")) {
                                    minRebate = "";
                                }
                                tvUserMinRebate.setText(minRebate);
                                minfan = minRebate;
                                serviceHotline = result.optString("serviceHotline");
                                String type = result.optString("type");
                                if (type.equals("0")) {
                                    if (list.size() == 5) {
                                        Map<String, Integer> map = new HashMap<>();
                                        map.put("代理中心", R.mipmap.item1);
                                        list.add(0, map);
                                    }
                                } else if (type.equals("1")) {
                                    if (list.size() == 6) {
                                        list.remove(0);
                                    }
                                }
                                myAdapter.refresh(list, serviceHotline);
                            } else {
                                ToastUtils.showToast(getActivity(), errormsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < itemNames.length; i++) {
            Map<String, Integer> map = new HashMap<>();
            map.put(itemNames[i], imagesIds[i]);
            list.add(map);
        }

    }

    @OnClick({R.id.ll_login, R.id.title_right,R.id.btn_center,R.id.btn_left})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.ll_login:
                if (isLogin) {
                    Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                    intent.putExtra("tag", 0);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.title_right:
                Intent intent = new Intent(getActivity(), MySettings.class);
                intent.putExtra("isLogin", isLogin);
                intent.putExtra("userId", userId);
                intent.putExtra("gao",maxfan);
                intent.putExtra("di",minfan);
                startActivity(intent);
                break;
            case R.id.btn_center:
                if (isLogin) {
                    Intent intent1 = new Intent(getActivity(), WithdrawalActivity.class);
                    intent1.putExtra("isLogin", isLogin);
                    intent1.putExtra("userId", userId);
                    startActivity(intent1);
                } else {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent1);
                }
                break;
            case R.id.btn_left:
                if (isLogin) {
                    Intent intent1 = new Intent(getActivity(), RechargeActivity.class);
                    intent1.putExtra("tag", 0);
                    startActivity(intent1);

                } else {
                    Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent1);
                }
                break;
        }
    }



}
