package com.xiongyuan.lottery.homepage.model;

import java.util.ArrayList;

/**
 * 类: DragIconInfo <p>
 * 描述: 拖动显示的view和icon <p>
 * 作者: wedcel wedcel@gmail.com<p>
 * 时间: 2015年8月25日 下午5:08:23 <p>
 */
public class DragIconInfo {

    /**
     * 可展开的
     **/
    public static final int CATEGORY_EXPAND = 100;

    /**
     * 不可展开的
     **/
    public static final int CATEGORY_ONLY = 300;

    private int id;
    private String name;
    private String content;
    private int resIconId;
    /**
     * 类型
     **/
    private int category;

    /**
     * 展开的child
     */
    private ArrayList<DargChildInfo> childList = new ArrayList<DargChildInfo>();


    public DragIconInfo() {
        // TODO Auto-generated constructor stub
    }


    public DragIconInfo(int id, String name,String content, int resIconId, int category,
                        ArrayList<DargChildInfo> childList) {
        super();
        this.id = id;
        this.content=content;
        this.name = name;
        this.resIconId = resIconId;
        this.category = category;
        this.childList = childList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getResIconId() {
        return resIconId;
    }


    public void setResIconId(int resIconId) {
        this.resIconId = resIconId;
    }


    public int getCategory() {
        return category;
    }


    public void setCategory(int category) {
        this.category = category;
    }


    public ArrayList<DargChildInfo> getChildList() {
        return childList;
    }


    public void setChildList(ArrayList<DargChildInfo> childList) {
        this.childList = childList;
    }


}
