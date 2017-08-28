package com.xiongyuan.lottery.mypage.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.RegexUtils;
import com.xiongyuan.lottery.utils.ToastUtils;

/**
 * Created by gameben on 2017-06-03.
 */

public class RegistermpDialog extends Dialog{

    private Button clickBtn;

    //定义回调事件，用于dialog的点击事件
    public interface OnCustomDialogListener{
         void back(String username, String password, String repsw);
    }

    private OnCustomDialogListener customDialogListener;
    private EditText userNameet;
    private EditText passwordet;
    private EditText rePasswordet;
    private String code;
    private String username;
    private String password;
    private String repsw;
    private String maxr;
    private String minr;


    public RegistermpDialog(Context context,OnCustomDialogListener customDialogListener) {
        super(context);
        this.customDialogListener = customDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_mp_dialog);
        //设置标题

        userNameet = (EditText)findViewById(R.id.register_lg_mobile);
        passwordet = (EditText) findViewById(R.id.register_lg_password);
        rePasswordet = (EditText) findViewById(R.id.register_lg_agin_password);
        clickBtn = (Button) findViewById(R.id.register_lg_bt_login);

        userNameet.addTextChangedListener(textWatcher);
        passwordet.addTextChangedListener(textWatcher);
        rePasswordet.addTextChangedListener(textWatcher);


        clickBtn.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS) {
                ToastUtils.showToast(getContext(),"用户名");
                return;
            } else if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS) {
                ToastUtils.showToast(getContext(),"mima");
                return;
            } else if (!TextUtils.equals(password,repsw)) {
                ToastUtils.showToast(getContext(),"两次密码不同");
                return;
            }
            customDialogListener.back(username,password,repsw);
            RegistermpDialog.this.dismiss();
        }
    };

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            username = userNameet.getText().toString();
            password = passwordet.getText().toString();
            repsw = rePasswordet.getText().toString();
            boolean canLogin = !(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repsw));
            clickBtn.setEnabled(canLogin);
        }
    };

}
