package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-07-02.
 */

public class Type2Bean {


    private String id;
    private String pid;
    private String title;

    @Override
    public String toString() {
        return "Type2Bean{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", title='" + title + '\'' +
                '}';
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
