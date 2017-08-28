package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by Administrator on 2017-05-24.
 */

public class SubordinateAccountItemBean {
    private String id;
    private String week;
    private String date;
    private String usable;
    private String intro;
    private String type;
    private String str;

    public SubordinateAccountItemBean() {
        super();
    }

    public SubordinateAccountItemBean(String id, String week, String date, String usable, String intro, String type) {
        this.id = id;
        this.week = week;
        this.date = date;
        this.usable = usable;
        this.intro = intro;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsable() {
        return usable;
    }

    public void setUsable(String usable) {
        this.usable = usable;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getType() {

        if (type.equals("adminOpe")){
            str="管理员操作";
        }else if (type.equals("bet")){
            str="投注";
        }else if (type.equals("winBet")){
            str="中奖";
        }else if (type.equals("betReturn")){
            str="返点";
        }else if (type.equals("cancelBet")){
            str="取消投注";
        }else if (type.equals("recharge")){
            str="充值";
        }else if (type.equals("cash")){
            str="提现申请";
        }else if (type.equals("cashSuc")){
            str="提现成功";
        }else if (type.equals("cashFai")){
            str="提现失败";
        }else if (type.equals("cashCounterFee")){
            str="提现手续费";
        }else if (type.equals("manualTrans")){
            str="手动返点";
        }
        return str;
    }

    public void setType(String type) {
        this.type = type;
    }
}
