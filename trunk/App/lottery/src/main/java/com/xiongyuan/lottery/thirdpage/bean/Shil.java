package com.xiongyuan.lottery.thirdpage.bean;

import java.io.Serializable;

/**
 * Created by gameben on 2017-07-05.
 */

public class Shil implements Serializable{

    private String haoma;//号码
    private String wanfa;
    private String beishu;
    private int zhushu;
    private String danjia;
    private String fandianl;
    private String unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getHaoma() {
        return haoma;
    }

    public void setHaoma(String haoma) {
        this.haoma = haoma;
    }

    public String getWanfa() {
        return wanfa;
    }

    public void setWanfa(String wanfa) {
        this.wanfa = wanfa;
    }

    public String getBeishu() {
        return beishu;
    }

    public void setBeishu(String beishu) {
        this.beishu = beishu;
    }

    public int getZhushu() {
        return zhushu;
    }

    public void setZhushu(int zhushu) {
        this.zhushu = zhushu;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getFandianl() {
        return fandianl;
    }

    public void setFandianl(String fandianl) {
        this.fandianl = fandianl;
    }

    @Override
    public String toString() {
        return "Shil{" +
                "haoma='" + haoma + '\'' +
                ", wanfa='" + wanfa + '\'' +
                ", beishu='" + beishu + '\'' +
                ", zhushu=" + zhushu +
                ", danjia='" + danjia + '\'' +
                ", fandianl='" + fandianl + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
