package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-06-12.
 */

public class Gameid3 {
    private String threename;
    private String id;
    private String pid;
    public Gameid3() {
    }

    public Gameid3(String threename, String id, String pid) {
        this.threename = threename;
        this.id = id;
        this.pid = pid;
    }

    public String getThreename() {
        return threename;
    }

    public void setThreename(String threename) {
        this.threename = threename;
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
