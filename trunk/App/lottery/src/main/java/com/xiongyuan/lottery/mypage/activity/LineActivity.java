package com.xiongyuan.lottery.mypage.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.CachePreferences;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LineActivity extends BaseActivity {

    @BindView(R.id.line_lv)
    ListView listView;

    private LineAdapter adapter;


    private Map<Integer, Boolean> isSelected;
    private List beSelectedData = new ArrayList();
    private List<String> cs = null;


    private String url0 = "http://hk.178yl.vip.com/model/json/?";
    private String url1 = "http://kor.178yl.vip.com/model/json/?";
    private String url2 = "http://www.178yl.vip.com/model/json/?";
    private String url3 = "http://us.178yl.vip.com/model/json/?";
    private String url4 = "http://sgp.178yl.vip.com/model/json/?";

    private int xl = 0;
    private User user = new User();

    @Override
    public int getLayoutId() {
        return R.layout.activity_line;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        cs = new ArrayList();
        for (int i = 0; i < 5; i++) {
            cs.add("线路" + (i + 1));
        }


        if (cs == null || cs.size() == 0)
            return;
        if (isSelected != null)
            isSelected = null;
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < cs.size(); i++) {
            isSelected.put(i, false);
        }
        String user_url = CachePreferences.getUser().getUser_url();
        if (user_url.equals(url0)){
            xl = 0;
        }else if(user_url.equals(url1)){
            xl = 1;
        }else if (user_url.equals(url2)){
            xl = 2;
        }else if (user_url.equals(url3)){
            xl = 3;
        }else if (user_url.equals(url4)){
            xl = 4;
        }

        isSelected.put(xl,true);

        // 清除已经选择的项
        if (beSelectedData.size() > 0) {
            beSelectedData.clear();
        }
        adapter = new LineAdapter(this, cs);
        listView.setAdapter(adapter);
        //listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 当前点击的CB
                if (!isSelected.get(position)) {
                    boolean cu = !isSelected.get(position);

                    // 先将所有的置为FALSE
                    for (Integer p : isSelected.keySet()) {
                        isSelected.put(p, false);
                    }
                    // 再将当前选择CB的实际状态
                    isSelected.put(position, cu);
                    adapter.notifyDataSetChanged();
                    beSelectedData.clear();
                    if (cu) beSelectedData.add(cs.get(position));


                    switch (position) {
                        case 0:
                            user.setUser_url(url0);
                            break;
                        case 1:
                            user.setUser_url(url1);
                            break;
                        case 2:
                            user.setUser_url(url2);
                            break;
                        case 3:
                            user.setUser_url(url3);
                            break;
                        case 4:
                            user.setUser_url(url4);
                            break;
                    }


                }
            }
        });

    }

    @Override
    public void initData() {
        user.setUser_id(CachePreferences.getUser().getUser_id());
        user.setUser_name(CachePreferences.getUser().getUser_name());
        user.setUser_password(CachePreferences.getUser().getUser_password());
        user.setUser_url(CachePreferences.getUser().getUser_url());

    }

    @OnClick({R.id.title_left})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                CachePreferences.setUser(user);

                finish();

//                Call call = LotteryClient.getInstance().getLogin(CachePreferences.getUser().getUser_name(),CachePreferences.getUser().getUser_password());
//                call.enqueue(new UICallBack() {
//                    @Override
//                    public void onFailureUI(Call call, IOException e) {
//                        ToastUtils.showToast(LineActivity.this,e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponseUI(Call call, String body) {
//                        try {
//
//                            JSONObject jsonObject=new JSONObject(body);
//                            String errormsg = jsonObject.optString("errormsg");
//                            if (errormsg.equals("")){
//
//                                JSONObject result = jsonObject.optJSONObject("result");
//                                String user_id = result.optString("user_id");
//                                String time = jsonObject.optString("TIME");
//                                User user = new User();
//                                user.setUser_id(user_id);
//                                user.setUser_id(CachePreferences.getUser().getUser_name());
//                                user.setUser_password(CachePreferences.getUser().getUser_password());
//                                user.setUser_url(CachePreferences.getUser().getUser_url());
//                                CachePreferences.setUser(user);
//                                EventBus.getDefault().postSticky(new LoginEvent(user_id,time,true));
//
//                                finish();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });

                break;
        }
    }

    class LineAdapter extends android.widget.BaseAdapter {

        private Context context;

        private List<String> cs;


        public LineAdapter(Context context, List<String> data) {
            this.context = context;
            this.cs = data;

        }

        @Override
        public int getCount() {
            return cs.size();
        }

        @Override
        public Object getItem(int position) {
            return cs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.adapter_line_item, null);
                viewHolder = new ViewHolder();
                viewHolder.tvContent = (TextView) convertView.findViewById(R.id.ada_line_tv);
                viewHolder.cb = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvContent.setText(cs.get(position));

            ViewHolder finalViewHolder = viewHolder;
            viewHolder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 当前点击的CB

                    if (isSelected.get(position)){
                        finalViewHolder.cb.setChecked(false);
                        boolean cu = true;
                        // 先将所有的置为FALSE
                        for (Integer p : isSelected.keySet()) {
                            isSelected.put(p, false);
                        }
                        // 再将当前选择CB的实际状态
                        isSelected.put(position, cu);
                        LineAdapter.this.notifyDataSetChanged();
                        beSelectedData.clear();
                        if (cu) beSelectedData.add(cs.get(position));
                    }

                    if (!isSelected.get(position)) {
                        boolean cu = !isSelected.get(position);
                        // 先将所有的置为FALSE
                        for (Integer p : isSelected.keySet()) {
                            isSelected.put(p, false);
                        }
                        // 再将当前选择CB的实际状态
                        isSelected.put(position, cu);
                        LineAdapter.this.notifyDataSetChanged();
                        beSelectedData.clear();
                        if (cu) beSelectedData.add(cs.get(position));
                    }

                    switch (position) {
                        case 0:
                            user.setUser_url(url0);
                            break;
                        case 1:
                            user.setUser_url(url1);
                            break;
                        case 2:
                            user.setUser_url(url2);
                            break;
                        case 3:
                            user.setUser_url(url3);
                            break;
                        case 4:
                            user.setUser_url(url4);
                            break;
                    }

                }
            });
            viewHolder.cb.setChecked(isSelected.get(position));

            return convertView;
        }

        class ViewHolder {
            TextView tvContent;
            CheckBox cb;

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (KeyEvent.KEYCODE_BACK == keyCode){
            finish();
//            CachePreferences.setUser(user);
//            Call call = LotteryClient.getInstance().getLogin(CachePreferences.getUser().getUser_name(),CachePreferences.getUser().getUser_password());
//            call.enqueue(new UICallBack() {
//                @Override
//                public void onFailureUI(Call call, IOException e) {
//                    ToastUtils.showToast(LineActivity.this,e.getMessage());
//                }
//
//                @Override
//                public void onResponseUI(Call call, String body) {
//
//
//                    try {
//
//                        JSONObject jsonObject=new JSONObject(body);
//                        String errormsg = jsonObject.optString("errormsg");
//                        if (errormsg.equals("")){
//
//                            JSONObject result = jsonObject.optJSONObject("result");
//                            String user_id = result.optString("user_id");
//                            String time = jsonObject.optString("TIME");
//                            User user = new User();
//                            user.setUser_id(user_id);
//                            user.setUser_id(CachePreferences.getUser().getUser_name());
//                            user.setUser_password(CachePreferences.getUser().getUser_password());
//                            user.setUser_url(CachePreferences.getUser().getUser_url());
//                            CachePreferences.setUser(user);
//                            EventBus.getDefault().postSticky(new LoginEvent(user_id,time,true));
//
//                            finish();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });

        }
        return true;
    }
}