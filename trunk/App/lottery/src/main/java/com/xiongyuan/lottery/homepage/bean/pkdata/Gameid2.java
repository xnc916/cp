package com.xiongyuan.lottery.homepage.bean.pkdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gameben on 2017-06-12.
 */

public class Gameid2 {
    /**
     * id : 148
     * pid : 1
     * level : 0
     * game_id : 1
     * title : 五星
     * status : 1
     * sort : 90
     * addTime : null
     */
    @SerializedName("id")
    private String typ_id;
    @SerializedName("pid")
    private String typ_pid;
    private String level;
    private String game_id;
    @SerializedName("title")
    private String typ_title;
    private String status;
    private String sort;
    private String addTime;
    private String enabled;
    private Object tag;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public String getTyp_id() {
        return typ_id;
    }

    public void setTyp_id(String typ_id) {
        this.typ_id = typ_id;
    }

    public String getTyp_pid() {
        return typ_pid;
    }

    public void setTyp_pid(String typ_pid) {
        this.typ_pid = typ_pid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getTyp_title() {
        return typ_title;
    }

    public void setTyp_title(String typ_title) {
        this.typ_title = typ_title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
