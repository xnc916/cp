package com.xiongyuan.lottery.mypage.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-07-02.
 */

public class RecordNumberBean implements Serializable {
    private String startTime;
    private String endTime;
    private String issueCount;
    private String game_id;
    private String winAmount;
    private String betAmount;
    private String winStop;
    private String status;
    private String winCount;
    private String addTime;
    private String progress;
    private String stopTime;


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIssueCount() {
        return issueCount;
    }

    public void setIssueCount(String issueCount) {
        this.issueCount = issueCount;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
            this.game_id = game_id;


    }

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getWinStop() {
        return winStop;
    }

    public void setWinStop(String winStop) {
        if (winStop.equals("0")){
            this.winStop = "否";
        }else if (winStop.equals("1")){
            this.winStop="是";
        }

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status.equals("0")){
            this.status = "未开始";
        }else if (status.equals("1")){
            this.status = "已开始";
        }else if (status.equals("2")){
            this.status = "已停止";
        }else if (status.equals("3")){
            this.status = "已结束";
        }

    }

    public String getWinCount() {
        return winCount;
    }

    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        if (progress.equals("null")){
            this.progress="";
        }else{
            this.progress = progress;
        }

    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}
