package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by gameben on 2017-07-10.
 */

public class User {

    private String user_id;
    private String user_name;
    private String user_password;
    private String user_url;
    private String ifji;

    public String isIfji() {
        return ifji;
    }

    public void setIfji(String ifji) {
        this.ifji = ifji;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_url() {
        return user_url;
    }

    public void setUser_url(String user_url) {
        this.user_url = user_url;
    }

    public String getUser_id() {
            return user_id;
        }

    public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

}
