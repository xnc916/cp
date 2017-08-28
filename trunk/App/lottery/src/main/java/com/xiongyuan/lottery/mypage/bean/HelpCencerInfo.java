package com.xiongyuan.lottery.mypage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-06-17.
 */

public class HelpCencerInfo {
    private String id;
    private String pid;
    private String title;
    private String href;
    private String type;
    private String sort;
    private String icon;
    private String menus_id;
    private List<?> sub;

    @Override
    public String toString() {
        return "HelpCencerInfo{" +
                "id='" + id + '\'' +
                ", pid='" + pid + '\'' +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", type='" + type + '\'' +
                ", sort='" + sort + '\'' +
                ", icon='" + icon + '\'' +
                ", menus_id='" + menus_id + '\'' +
                ", sub=" + sub +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMenus_id() {
        return menus_id;
    }

    public void setMenus_id(String menus_id) {
        this.menus_id = menus_id;
    }

    public List<?> getSub() {
        return sub;
    }

    public void setSub(List<?> sub) {
        this.sub = sub;
    }
}
