package com.xiongyuan.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.xiongyuan.lottery.base.LotteryClient;
import com.xiongyuan.lottery.base.UICallBack;
import com.xiongyuan.lottery.mypage.bean.User;
import com.xiongyuan.lottery.utils.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SplashActivity extends Activity {
    @BindView(R.id.iv_splash)
    ImageView ivSplash;
    private AlphaAnimation alphaAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        User user = new User();
        user.setUser_url("http://hk.178yl.vip/model/?c=json&");
        user.setUser_name(CachePreferences.getUser().getUser_name());
        if (CachePreferences.getUser().isIfji() == null) {
            //user.setUser_url("http://101.288.79.228/lotteryModel/?c=json&");
            user.setIfji("1");
        }else {
            user.setIfji(CachePreferences.getUser().isIfji());
            if (CachePreferences.getUser().isIfji().equals("1")){
                user.setUser_password(CachePreferences.getUser().getUser_password());
            }
        }
        CachePreferences.setUser(user);
        initjianc();

        initView();

    }

    private void initjianc() {
        Call call = LotteryClient.getInstance().getVersions();
        call.enqueue(new UICallBack() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                ToastUtils.showToast(SplashActivity.this,e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {

            }
        });
    }


    private void initView() {
        setAnimator();


    }
    //设置动画
    private void setAnimator() {
        alphaAnimation = new AlphaAnimation(0.5f,1);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        alphaAnimation.setDuration(2000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(scaleAnimation);
        ivSplash.setAnimation(set);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        });
    }
}
