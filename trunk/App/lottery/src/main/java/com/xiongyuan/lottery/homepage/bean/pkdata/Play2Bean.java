package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-07-02.
 */

public class Play2Bean {

    private String id;
    private String game_id;
    private String pid;
    private String title;
    private String returnAmount;//返点率金钱
    private String oneAmount;//单价
    private String bonus;//奖金
    private String explain;//玩法

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getOneAmount() {
        return oneAmount;
    }

    public void setOneAmount(String oneAmount) {
        this.oneAmount = oneAmount;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Play2Bean{" +
                "id='" + id + '\'' +
                ", game_id='" + game_id + '\'' +
                ", pid='" + pid + '\'' +
                ", title='" + title + '\'' +
                ", returnAmount='" + returnAmount + '\'' +
                ", oneAmount='" + oneAmount + '\'' +
                ", bonus='" + bonus + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
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