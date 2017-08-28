package com.xiongyuan.lottery.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.xiongyuan.lottery.R;


/**
 * description:弹窗浮动加载进度条
 */
public class LoadingDialog extends Dialog{
    Context context;
    String str;

    public LoadingDialog(@NonNull Context context,String str) {
        super(context, R.style.CustomProgressDialog);
        this.context=context;
        this.str=str;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_loading);
        TextView loadingText = (TextView) this.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText(str);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }
}
