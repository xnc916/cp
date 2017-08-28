package com.xiongyuan.lottery.bean;

/**
 * Created by Administrator on 2017-05-12.
 */

public class LoginEvent {
    private String userId;
    private String time;
    private boolean isLogin=false;


    public LoginEvent(String userId, String time,boolean isLogin) {
        this.userId = userId;
        this.time = time;
        this.isLogin=isLogin;
    }

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
