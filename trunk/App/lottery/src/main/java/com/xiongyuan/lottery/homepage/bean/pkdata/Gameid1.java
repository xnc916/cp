package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-06-12.
 */

public class Gameid1 {
    private String onename;
    private String id;
    private String pid;



    @Override
    public String toString() {
        return "Gameid1{" +
                "onename='" + onename + '\'' +
                ", id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }

    public Gameid1() {
    }

    public Gameid1(String onename, String id, String pid) {
        this.onename = onename;
        this.id = id;
        this.pid = pid;

    }

    public String getOnename() {
        return onename;
    }

    public void setOnename(String onename) {
        this.onename = onename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }



}
