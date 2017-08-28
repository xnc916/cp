package com.xiongyuan.lottery.mypage.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-06-21.
 */

public class TodayRechargeBean implements Serializable {
    private String id;
    private Object gameFullName;
    private String log_id;
    private Object bingoLog_id;
    private String issue;
    private String play_id;
    private String game_id;
    private String type_id;
    private String user_id;
    private String gameTitle;
    private String typeTitle;
    private String playTitle;
    private String user_name;
    private String oneAmount;
    private Object returnMoney;
    private String returnAmount;
    private String returnPer;
    private Object maxReturnPer;
    private String betAmount;
    private String betCount;
    private String currency;
    private String betTimes;
    private String code;
    private String status;
    private String winAmount;
    private String winCount;
    private String winBetAmount;
    private Object winBetReturn;
    private String addTime;
    private String singleTime;
    private Object bingoTime;
    private String chase_id;
    private String bonusMode;
    private Object bingoCode;
    private String betReturnSelf;
    private String betReturnParent;
    private String betReturnAmount;
    private Object freezen;

    public TodayRechargeBean(String id ,String issue, String gameTitle, String betAmount, String status, String winAmount,String addTime,
                             String typeTitle,String playTitle,String log_id,String returnPer,
                             String returnAmount,String code,String betCount,String betTimes,
                             String winCount,String bingoCode) {
        this.id = id;
        this.issue = issue;
        this.gameTitle = gameTitle;
        this.betAmount = betAmount;
        this.status = status;
        this.winAmount = winAmount;
        this.addTime=addTime;
        this.typeTitle = typeTitle;
        this.playTitle = playTitle;
        this.log_id = log_id;
        this.returnPer = returnPer;
        this.returnAmount = returnAmount;
        this.code = code;
        this.betCount = betCount;
        this.betTimes = betTimes;
        this.winCount = winCount;
        this.bingoCode = bingoCode;

    }

    public Object getGameFullName() {
        return gameFullName;
    }

    public void setGameFullName(Object gameFullName) {
        this.gameFullName = gameFullName;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public Object getBingoLog_id() {
        return bingoLog_id;
    }

    public void setBingoLog_id(Object bingoLog_id) {
        this.bingoLog_id = bingoLog_id;
    }

    public String getPlay_id() {
        return play_id;
    }

    public void setPlay_id(String play_id) {
        this.play_id = play_id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public String getPlayTitle() {
        return playTitle;
    }

    public void setPlayTitle(String playTitle) {
        this.playTitle = playTitle;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getOneAmount() {
        return oneAmount;
    }

    public void setOneAmount(String oneAmount) {
        this.oneAmount = oneAmount;
    }

    public Object getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Object returnMoney) {
        this.returnMoney = returnMoney;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getReturnPer() {
        return returnPer;
    }

    public void setReturnPer(String returnPer) {
        this.returnPer = returnPer;
    }

    public Object getMaxReturnPer() {
        return maxReturnPer;
    }

    public void setMaxReturnPer(Object maxReturnPer) {
        this.maxReturnPer = maxReturnPer;
    }

    public String getBetCount() {
        return betCount;
    }

    public void setBetCount(String betCount) {
        this.betCount = betCount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBetTimes() {
        return betTimes;
    }

    public void setBetTimes(String betTimes) {
        this.betTimes = betTimes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWinCount() {
        return winCount;
    }

    public void setWinCount(String winCount) {
        this.winCount = winCount;
    }

    public String getWinBetAmount() {
        return winBetAmount;
    }

    public void setWinBetAmount(String winBetAmount) {
        this.winBetAmount = winBetAmount;
    }

    public Object getWinBetReturn() {
        return winBetReturn;
    }

    public void setWinBetReturn(Object winBetReturn) {
        this.winBetReturn = winBetReturn;
    }

    public String getSingleTime() {
        return singleTime;
    }

    public void setSingleTime(String singleTime) {
        this.singleTime = singleTime;
    }

    public Object getBingoTime() {
        return bingoTime;
    }

    public void setBingoTime(Object bingoTime) {
        this.bingoTime = bingoTime;
    }

    public String getChase_id() {
        return chase_id;
    }

    public void setChase_id(String chase_id) {
        this.chase_id = chase_id;
    }

    public String getBonusMode() {
        return bonusMode;
    }

    public void setBonusMode(String bonusMode) {
        this.bonusMode = bonusMode;
    }

    public Object getBingoCode() {
        return bingoCode;
    }

    public void setBingoCode(Object bingoCode) {
        this.bingoCode = bingoCode;
    }

    public String getBetReturnSelf() {
        return betReturnSelf;
    }

    public void setBetReturnSelf(String betReturnSelf) {
        this.betReturnSelf = betReturnSelf;
    }

    public String getBetReturnParent() {
        return betReturnParent;
    }

    public void setBetReturnParent(String betReturnParent) {
        this.betReturnParent = betReturnParent;
    }

    public String getBetReturnAmount() {
        return betReturnAmount;
    }

    public void setBetReturnAmount(String betReturnAmount) {
        this.betReturnAmount = betReturnAmount;
    }

    public Object getFreezen() {
        return freezen;
    }

    public void setFreezen(Object freezen) {
        this.freezen = freezen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(String winAmount) {
        this.winAmount = winAmount;
    }

    @Override
    public String toString() {
        return "TodayRechargeBean{" +
                "issue='" + issue + '\'' +
                ", gameTitle='" + gameTitle + '\'' +
                ", betAmount='" + betAmount + '\'' +
                ", status='" + status + '\'' +
                ", winAmount='" + winAmount + '\'' +
                ", addTime='" + addTime + '\'' +
                '}';
    }
}
