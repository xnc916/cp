package com.xiongyuan.lottery.thirdpage.bean;

import java.util.Arrays;

/**
 * Created by Administrator on 2017-05-05.
 */

public class ArcList {
    private int img;
    private String tvIssue;
    private String tvqIssue;
    private String tvDate;
    private String tvqDate;
    private int redNum;
    private int blueNum;
    private String[] redStr;
    private String[] blueStr;
    private int redNumq;
    private int blueNumq;
    private String[] redStrq;
    private String[] blueStrq;
    private String name;

    public ArcList(String tvIssue, String tvDate, int redNum, String[] redStr,String name,int img) {
        this.tvIssue = tvIssue;
        this.tvDate = tvDate;
        this.redNum = redNum;
        this.redStr = redStr;
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTvIssue() {
        return tvIssue;
    }

    public void setTvIssue(String tvIssue) {
        this.tvIssue = tvIssue;
    }

    public String getTvqIssue() {
        return tvqIssue;
    }

    public void setTvqIssue(String tvqIssue) {
        this.tvqIssue = tvqIssue;
    }

    public String getTvDate() {
        return tvDate;
    }

    public void setTvDate(String tvDate) {
        this.tvDate = tvDate;
    }

    public String getTvqDate() {
        return tvqDate;
    }

    public void setTvqDate(String tvqDate) {
        this.tvqDate = tvqDate;
    }

    public int getRedNum() {
        return redNum;
    }

    public void setRedNum(int redNum) {
        this.redNum = redNum;
    }

    public int getBlueNum() {
        return blueNum;
    }

    public void setBlueNum(int blueNum) {
        this.blueNum = blueNum;
    }

    public String[] getRedStr() {
        return redStr;
    }

    public void setRedStr(String[] redStr) {
        this.redStr = redStr;
    }

    public String[] getBlueStr() {
        return blueStr;
    }

    public void setBlueStr(String[] blueStr) {
        this.blueStr = blueStr;
    }

    public int getRedNumq() {
        return redNumq;
    }

    public void setRedNumq(int redNumq) {
        this.redNumq = redNumq;
    }

    public int getBlueNumq() {
        return blueNumq;
    }

    public void setBlueNumq(int blueNumq) {
        this.blueNumq = blueNumq;
    }

    public String[] getRedStrq() {
        return redStrq;
    }

    public void setRedStrq(String[] redStrq) {
        this.redStrq = redStrq;
    }

    public String[] getBlueStrq() {
        return blueStrq;
    }

    public void setBlueStrq(String[] blueStrq) {
        this.blueStrq = blueStrq;
    }


    @Override
    public String toString() {
        return "ArcList{" +
                "img=" + img +
                ", tvIssue='" + tvIssue + '\'' +
                ", tvDate='" + tvDate + '\'' +
                ", redNum=" + redNum +
                ", redStr=" + Arrays.toString(redStr) +
                ", name='" + name + '\'' +
                '}';
    }
}