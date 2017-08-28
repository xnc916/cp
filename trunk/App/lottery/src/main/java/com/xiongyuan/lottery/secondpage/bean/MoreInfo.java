package com.xiongyuan.lottery.secondpage.bean;

/**
 * Created by gameben on 2017-06-29.
 */

public class MoreInfo {
    private String id;
    private String title;
    private String content;
    private String addTime;
    private String menus_id;
    private String startTime;
    private String endTime;
    private Object image;

    @Override
    public String toString() {
        return "MoreInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", addTime='" + addTime + '\'' +
                ", menus_id='" + menus_id + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", image=" + image +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getMenus_id() {
        return menus_id;
    }

    public void setMenus_id(String menus_id) {
        this.menus_id = menus_id;
    }

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

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }
}
