package com.xiongyuan.lottery.homepage.fragment;

import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseFragment;
import com.xiongyuan.lottery.bean.LoginEvent;
import com.xiongyuan.lottery.mypage.adapter.TodayRechargeAdapter;
import com.xiongyuan.lottery.mypage.bean.TodayRechargeBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import okhttp3.Call;

public class BetShowFragment extends BaseFragment {

    @BindView(R.id.list_today_recharge)
    ListView listView;
    @BindView(R.id.refreshLayout)
    PtrClassicFrameLayout refreshLayout;//刷新加载的控件
    @BindView(R.id.tv)
    TextView tv;
    private Call call;
    private Date d;
    private int page=1;
    private int p;
    private static int mSerial = 0;
    private int mTabPos = 0;
    private String userId;
    private String date;
    private TodayRechargeAdapter mAdapter;
    private ArrayList<TodayRechargeBean> arrayList;
    private ArrayList<TodayRechargeBean> list;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //这里执行加载数据的操作
//            switch (p) {
//                case 0:
//                    date = "";
//                    page=1;
//                    break;
//                case 1:
//                    page=1;
//                    date = getNextDate(Calendar.DAY_OF_MONTH, -1);
//                    break;
//                case 2:
//                    page=1;
//                    date = getNextDate(Calendar.DAY_OF_MONTH, -7);
//                    break;
//                case 3:
//                    page=1;
//                    date = getNextDate(Calendar.MONTH, -1);
//                    break;
//                case 4:
//                    page=1;
//                    date = getNextDate(Calendar.MONTH, -3);
//                    break;
//            }
//            refreshData(mTabPos, jsonStr(date),1);
//            Log.e("jsonStr:", jsonStr(date)+"  "+mTabPos);
        }

        ;
    };


    public BetShowFragment(int serial) {
        mSerial = serial;
    }
    public void sendMessage(int p) {
        Message message = handler.obtainMessage();
        message.sendToTarget();
        this.p = p;
    }
    public void setTabPos(int mTabPos) {
        this.mTabPos = mTabPos;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bet_show;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onShowDataEvent(LoginEvent event) {
        //将获取的数据展示在界面上
        userId = event.getUserId();
    }

}
