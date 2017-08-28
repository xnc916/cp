package com.xiongyuan.lottery.mypage.view.table;

import java.io.Serializable;

public class TableModel implements Serializable{

    private String id;
    private String username;
    private String tier;
    private Object user_id;
    private Object recharge;
    private Object cash;
    private Object adminOpe1;
    private Object adminOpe0;
    private Object cancelBet;
    private Object betReturnSelf;
    private Object betReturn;
    private Object singleBet;
    private Object winBet;
    private Object manualTrans;
    private Object returnUserAmount;
    private Object yingkui;
    private String lowersCount;


    public TableModel(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public Object getUser_id() {
        return user_id;
    }

    public void setUser_id(Object user_id) {
        this.user_id = user_id;
    }

    public Object getRecharge() {
        return recharge;
    }

    public void setRecharge(Object recharge) {
        this.recharge = recharge;
    }

    public Object getCash() {
        return cash;
    }

    public void setCash(Object cash) {
        this.cash = cash;
    }

    public Object getAdminOpe1() {
        return adminOpe1;
    }

    public void setAdminOpe1(Object adminOpe1) {
        this.adminOpe1 = adminOpe1;
    }

    public Object getAdminOpe0() {
        return adminOpe0;
    }

    public void setAdminOpe0(Object adminOpe0) {
        this.adminOpe0 = adminOpe0;
    }

    public Object getCancelBet() {
        return cancelBet;
    }

    public void setCancelBet(Object cancelBet) {
        this.cancelBet = cancelBet;
    }

    public Object getBetReturnSelf() {
        return betReturnSelf;
    }

    public void setBetReturnSelf(Object betReturnSelf) {
        this.betReturnSelf = betReturnSelf;
    }

    public Object getBetReturn() {
        return betReturn;
    }

    public void setBetReturn(Object betReturn) {
        this.betReturn = betReturn;
    }

    public Object getSingleBet() {
        return singleBet;
    }

    public void setSingleBet(Object singleBet) {
        this.singleBet = singleBet;
    }

    public Object getWinBet() {
        return winBet;
    }

    public void setWinBet(Object winBet) {
        this.winBet = winBet;
    }

    public Object getManualTrans() {
        return manualTrans;
    }

    public void setManualTrans(Object manualTrans) {
        this.manualTrans = manualTrans;
    }

    public Object getReturnUserAmount() {
        return returnUserAmount;
    }

    public void setReturnUserAmount(Object returnUserAmount) {
        this.returnUserAmount = returnUserAmount;
    }

    public Object getYingkui() {
        return yingkui;
    }

    public void setYingkui(Object yingkui) {
        this.yingkui = yingkui;
    }

    public String getLowersCount() {
        return lowersCount;
    }

    public void setLowersCount(String lowersCount) {
        this.lowersCount = lowersCount;
    }
}
