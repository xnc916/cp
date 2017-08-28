package com.xiongyuan.lottery.mypage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xiongyuan.lottery.R;
import com.xiongyuan.lottery.base.BaseActivity;

import butterknife.BindView;

public class PayWebActivity extends BaseActivity {


    @BindView(R.id.web_pay)
    WebView mWeb;

    private String url = "";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case 5:
                    webViewGoBack();
                    break;
            }

        }
    };
    private void webViewGoBack() {
        mWeb.goBack();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_web;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        WebSettings settings = mWeb.getSettings();
        mWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWeb.getSettings().setSupportZoom(true);  //支持放大缩小
        mWeb.getSettings().setBuiltInZoomControls(true);
        mWeb.loadUrl(url);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        mWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeb.clearHistory();
        mWeb.clearFormData();
        mWeb.clearCache(true);

        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && mWeb.canGoBack()) {
                    handler.sendEmptyMessage(5);
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public void initData() {
        url = getIntent().getStringExtra("url");
    }



}
