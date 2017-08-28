package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-07-22.
 */

public class FutureBean {

    private String issue;
    private int singleTime;
    private int time;
    private int startTime;
    private int endTime;
    private int id;
    private String times;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(int singleTime) {
        this.singleTime = singleTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" + "\"issue\":" + '\"' +issue + '\"' +
                ", \"singleTime\":" + '\"' +singleTime +'\"'+
                ", \"time\":" + '\"' +time +'\"'+
                ", \"startTime\":" + '\"' +startTime +'\"'+
                ", \"endTime\":" + '\"' +endTime +'\"'+
                ", \"id\":" + '\"' +id +'\"'+
                ", \"times\":" + '\"' +times + '\'' +'\"'+
                '}';
    }
}
