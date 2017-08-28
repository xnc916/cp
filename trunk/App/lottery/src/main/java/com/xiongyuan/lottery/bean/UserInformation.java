package com.xiongyuan.lottery.bean;

/**
 * Created by Administrator on 2017-05-12.
 */

public class UserInformation {
    //用户名
    private String username;
    //账户总余额
    private String balance;
    //用户等级
    private String level;
    //邮箱
    private String email;
    //手机号码
    private String mobile;
    //qq号码
    private String qq;
    //头像地址
    private String avatar;
    //注册时间
    private String add_time;
    //可用余额
    private String usableAmount;
    //冻结余额
    private String frozenAmount;
    //资料完整度
    private String dataIntegrity;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(String usableAmount) {
        this.usableAmount = usableAmount;
    }

    public String getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(String frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public String getDataIntegrity() {
        return dataIntegrity;
    }

    public void setDataIntegrity(String dataIntegrity) {
        this.dataIntegrity = dataIntegrity;
    }
}
