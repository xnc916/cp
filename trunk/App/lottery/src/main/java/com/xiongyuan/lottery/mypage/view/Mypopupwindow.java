package com.xiongyuan.lottery.mypage.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gameben on 2017-06-30.
 */

public class Mypopupwindow extends PopupWindow implements PopupWindow.OnDismissListener {

    @BindView(R.id.popu_all)
    TextView popuAll;
    @BindView(R.id.popu_jin)
    TextView popuJin;
    @BindView(R.id.popu_yizhou)
    TextView popuYizhou;
    @BindView(R.id.popu_yiyue)
    TextView popuYiyue;
    @BindView(R.id.popu_sanyue)
    TextView popuSanyue;
    private BaseActivity mActivity;
    private final ViewGroup mParent;
    private OnConfirmListener mOnConfirmListener;

    public Mypopupwindow(BaseActivity activity) {

        this.mActivity = activity;


        // 布局的填充
        // 获取顶层视图
        mParent = (ViewGroup) activity.getWindow().getDecorView();
        Context context = mParent.getContext();
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_popupwindow_right_all, mParent, false);
        // 设置布局
        setContentView(view);
        // 设置宽和高
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        setFocusable(true);

        // 设置消失的监听：弹出和隐藏的时候改变透明度
        setOnDismissListener(this);

        ButterKnife.bind(this, view);



    }

   @OnClick({R.id.popu_all,R.id.popu_jin,R.id.popu_yizhou,R.id.popu_yiyue,R.id.popu_sanyue})
   public void click(View view){
       switch (view.getId()){
           case R.id.popu_all:
               String popuAllText = (String) popuAll.getText();
               mOnConfirmListener.onConfirm(popuAllText);
               break;
           case R.id.popu_jin:
               String popuJinText = (String) popuJin.getText();
               mOnConfirmListener.onConfirm(popuJinText);
               break;
           case R.id.popu_yizhou:
               String popuYizhouText = (String) popuYizhou.getText();
               mOnConfirmListener.onConfirm(popuYizhouText);
               break;
           case R.id.popu_yiyue:
               String popuYiyueText = (String) popuYiyue.getText();
               mOnConfirmListener.onConfirm(popuYiyueText);
               break;
           case R.id.popu_sanyue:
               String popuSanyueText = (String) popuSanyue.getText();
               mOnConfirmListener.onConfirm(popuSanyueText);
               break;
       }

   }

    // show方法：展示PopupWindow,参数中将监听传递过来
    public void show(OnConfirmListener onConfirmListener) {

        this.mOnConfirmListener = onConfirmListener;

        // 从哪显示出来
        showAtLocation(mParent, Gravity.TOP, 0, 10);

    }

    @Override
    public void onDismiss() {

    }

    // 利用接口回调：将选择的数量传递出去
    public interface OnConfirmListener {
        void onConfirm(String xuan);
    }
}
