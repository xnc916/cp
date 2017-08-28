package com.xiongyuan.lottery.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiongyuan.lottery.R;

public class ToastUtils {

	/*public static  void showToast(Context ctx, String text) {
		Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
	}*/
	public static void showToast(Context context, String msg){
		View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
		//Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		Toast toast=new Toast(context);
		toast.setView(toastRoot);
		TextView tv = (TextView) toastRoot.findViewById(R.id.tv_toast);
		tv.setText(msg);
		toast.setGravity(Gravity.BOTTOM, 0, 200);
		toast.show();
	}
}
