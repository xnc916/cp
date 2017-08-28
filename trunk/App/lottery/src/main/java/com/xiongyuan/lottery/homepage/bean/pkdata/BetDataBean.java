package com.xiongyuan.lottery.homepage.bean.pkdata;

/**
 * Created by gameben on 2017-06-21.
 */

public class BetDataBean {
    private String code;
    private String times;
    private String game_id;
    private String play_id;
    private String betAmount;
    private String currency;
    private String returnPer;
    private String betCount;

    @Override
    public String toString() {

        return "{" +
                "\"code\":" +'\"' +code + '\"' +
                ", \"times\":" +'\"'+times + '\"' +
                ", \"game_id\":" +'\"'+ game_id + '\"' +
                ", \"play_id\":" + '\"'+play_id + '\"' +
                ", \"betAmount\":" + '\"'+betAmount + '\"' +
                ", \"currency\":" + '\"'+currency + '\"' +
                ", \"returnPer\":" + '\"'+returnPer + '\"' +
                ", \"betCount\":" + '\"'+betCount + '\"' +
                "}";
    }

    public String getCode() {
        return code;
    }

    public String getTimes() {
        return times;
    }

    public String getGame_id() {
        return game_id;
    }

    public String getPlay_id() {
        return play_id;
    }

    public String getBetAmount() {
        return betAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getReturnPer() {
        return returnPer;
    }

    public String getBetCount() {
        return betCount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public void setPlay_id(String play_id) {
        this.play_id = play_id;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setReturnPer(String returnPer) {
        this.returnPer = returnPer;
    }

    public void setBetCount(String betCount) {
        this.betCount = betCount;
    }
}
