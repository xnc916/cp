package com.xiongyuan.lottery.mypage.bean;

import java.io.Serializable;

/**
 * Created by gameben on 2017-08-03.
 */

public class Paybean implements Serializable {


    private String name;
    private String title;
    private String type;
    private String mode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
