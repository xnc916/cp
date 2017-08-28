package com.xiongyuan.lottery.mypage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;
import com.xiongyuan.lottery.mypage.adapter.PayBlankAdapter;
import com.xiongyuan.lottery.mypage.bean.Paybean;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by gameben on 2017-08-03.
 */

public class PayBlankpopu extends PopupWindow {
    private BaseActivity mActivity;
    private final ViewGroup mParent;
    private OnConfirmListener mOnConfirmListener;
    private PayBlankAdapter adapter;
    private ArrayList<Paybean> paylist = new ArrayList<>();
    public PayBlankpopu(BaseActivity activity, @NonNull ArrayList<Paybean> list) {

        this.mActivity = activity;
        this.paylist = list;

        // 布局的填充
        // 获取顶层视图
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = mParent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.popup_goods_spec, mParent, false);
        // 设置布局
        setContentView(view);

        // 设置宽和高
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);


        // 设置得到焦点
        setFocusable(true);

        // 设置点击窗口外部，PopupWindow消失
        setOutsideTouchable(true);

        ButterKnife.bind(this,view);

        ListView listView = (ListView) view.findViewById(R.id.popu_list);
        adapter = new PayBlankAdapter(view.getContext(),paylist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mOnConfirmListener.onConfirm(position);
            }
        });



    }


    // show方法：展示PopupWindow,参数中将监听传递过来
    public void show(OnConfirmListener onConfirmListener){

        this.mOnConfirmListener = onConfirmListener;

        // 从哪显示出来
        showAtLocation(mParent, Gravity.BOTTOM,0,0);

    }

    // 利用接口回调：将选择的数量传递出去
    public interface OnConfirmListener{
        void onConfirm(int number);
    }
}
