package com.xiongyuan.lottery.mypage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.adapter.TeamTableAdapter;
import com.xiongyuan.lottery.mypage.view.table.BaseBean;
import com.xiongyuan.lottery.mypage.view.table.TableModel;
import com.xiongyuan.lottery.mypage.view.team.TimePopupWindow;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

public class TeamTableActivity extends BaseActivity implements TimePopupWindow.OnOptionsSelectListener,TimePopupWindow.Shishi{

    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.table_lv)
    ListView listView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;
    @BindView(R.id.view)
    View v;

    private int pageNo = 1;
    private ArrayList<TableModel> onlineSaleBeanList;
    private ArrayList<String> list;
    private TeamTableAdapter adapter;

    ArrayList<String> ProvinceList;
    ArrayList<ArrayList<String>> CityList;
    ArrayList<ArrayList<ArrayList<String>>> CountyList;
    private TimePopupWindow popupWindow;

    private String start = "";
    private String end = "";
    @Override
    public int getLayoutId() {
        return R.layout.activity_team_table;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        titleName.setText("团队报表");
        titleRight.setText("搜索");
        titleRight.setVisibility(View.VISIBLE);


        adapter = new TeamTableAdapter(this);
        listView.setAdapter(adapter);

        doGetDatas(pageNo,"","");
        refreshLayout.setLastUpdateTimeRelateObject(this);
        refreshLayout.setBackgroundResource(R.color.color_333333);
        refreshLayout.setDurationToCloseHeader(1500);
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                pageNo++;
                doGetDatas(pageNo,start,end);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo=1;
                doGetDatas(pageNo,start,end);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list = new ArrayList<>();
                list.add(onlineSaleBeanList.get(position).getUsername());
                list.add(onlineSaleBeanList.get(position).getTier()+"级");
                list.add(onlineSaleBeanList.get(position).getRecharge().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getCash().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getCancelBet().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getWinBet().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getManualTrans().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getReturnUserAmount().toString()+"元");
                list.add(onlineSaleBeanList.get(position).getYingkui().toString()+"元");
                Intent intent = new Intent(TeamTableActivity.this,TeamtabActivity.class);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });

    }



    @Override
    public void initData() {
        showNum();
    }

    @OnClick({R.id.title_left,R.id.title_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
//                getPopupWindow();
//                popupWindow.showAtLocation(v,Gravity.BOTTOM, 0, 0);
                popupWindow = new TimePopupWindow(TeamTableActivity.this);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);//textView
                popupWindow.setPicker(ProvinceList, CityList, CountyList, true);
                popupWindow.setOnoptionsSelectListener(this);
                popupWindow.setshishi(this);
                popupWindow.setCyclic(false);


                break;
        }
    }




    //模拟网络请求
    public void doGetDatas(int pagenos,String sta,String end) {
        onlineSaleBeanList = new ArrayList<>();
        Call call = LotteryClient.getInstance().getBaob(pagenos + "",sta,end);
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(TeamTableActivity.this, e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                BaseBean result = new Gson().fromJson(body, BaseBean.class);
                List<TableModel> data = result.getResult().getData();
                if (data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        TableModel tableModel = new TableModel();
                        tableModel.setId(data.get(i).getId());
                        tableModel.setUsername(data.get(i).getUsername());//用户名
                        tableModel.setTier(data.get(i).getTier());//用户层级
                        if (data.get(i).getUser_id() == null) {
                            data.get(i).setUser_id("");
                        }
                        tableModel.setUser_id(data.get(i).getUser_id().toString());
                        if (data.get(i).getRecharge() == null) {
                            data.get(i).setRecharge("0");
                        }
                        tableModel.setRecharge(data.get(i).getRecharge().toString());//充值金额
                        if (data.get(i).getCash() == null) {
                            data.get(i).setCash("0");
                        }
                        tableModel.setCash(data.get(i).getCash().toString());//取款金额
                        if (data.get(i).getCancelBet() == null) {
                            data.get(i).setCancelBet("0");
                        }
                        tableModel.setCancelBet(data.get(i).getCancelBet().toString());//投注总额

                        if (data.get(i).getWinBet() == null) {
                            data.get(i).setWinBet("0");
                        }
                        tableModel.setWinBet(data.get(i).getWinBet().toString());//中奖金额

                        if (data.get(i).getManualTrans() == null) {
                            data.get(i).setManualTrans("0");
                        }
                        tableModel.setManualTrans(data.get(i).getManualTrans().toString());//赠点金额

                        if (data.get(i).getReturnUserAmount() == null) {
                            data.get(i).setReturnUserAmount("0");
                        }
                        tableModel.setReturnUserAmount(data.get(i).getReturnUserAmount().toString());//返定金额

                        if (data.get(i).getYingkui() == null) {
                            data.get(i).setYingkui("0");
                        }
                        tableModel.setYingkui(data.get(i).getYingkui().toString());//盈亏

                        onlineSaleBeanList.add(tableModel);
                    }

                    if (pageNo == 1){
                        adapter.setOnList(onlineSaleBeanList);
                    }else {
                        adapter.refust(onlineSaleBeanList);
                    }
                    //加载数据成功，增加页数
                    pageNo++;

                } else {
                        if (pageNo == 1){
                            adapter.cleer();
                        }
                        ToastUtils.showToast(TeamTableActivity.this, "没有更多数据！");
                    }
                refreshLayout.refreshComplete();
                }

        });

    }

    private void showNum() {
        ArrayList<String> one = new ArrayList<>();
        ProvinceList = new ArrayList<>();
        for (int i = 2000; i < 2050; i++) {
                ProvinceList.add(i+"年");
            one.add(i+"");
        }
        CityList = new ArrayList<>();
        for (int j = 0; j < ProvinceList.size(); j++) {
            ArrayList<String> l21 = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                if (i < 9){
                    l21.add(0+""+(i+1)+"月");
                }else {
                    l21.add(i+1+"月");
                }
            }

            CityList.add(l21);

        }
        CountyList = new ArrayList<>();
        for (int j = 0; j < one.size(); j++) {

            int year = Integer.valueOf(one.get(j));
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            if((year%4==0&&year%100!=0)||year%400==0){
                for (int i = 0; i < 12; i++) {
                    ArrayList<String> l21 = new ArrayList<>();
                    if (i == 0 || i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11){
                        for (int p = 0;p < 31;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else if (i == 1){
                        for (int p = 0;p < 29;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else {
                        for (int p = 0;p < 30;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }

                    temp.add(l21);
                }
            }else {
                for (int i = 0; i < 12; i++) {
                    ArrayList<String> l21 = new ArrayList<>();
                    if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12){
                        for (int p = 0;p < 31;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else if (i == 2){
                        for (int p = 0;p < 28;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }else {
                        for (int p = 0;p < 30;p++){
                            if (p < 9){
                                l21.add(0+""+(p+1)+"日");
                            }else {
                                l21.add(p+1+"日");
                            }
                        }
                    }

                    temp.add(l21);
                }
            }

            CountyList.add(temp);

        }

    }

    @Override
    public void onOptionsSelect(int options1, int option2, int options3) {

    }

    @Override
    public void onshishi(String options1,String ends) {
        start = options1;
        end = ends;
        pageNo=1;
        doGetDatas(pageNo,start,end);
    }
}
