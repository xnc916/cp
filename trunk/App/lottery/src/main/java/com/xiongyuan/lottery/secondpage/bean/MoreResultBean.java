package com.xiongyuan.lottery.secondpage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-06-29.
 */

public class MoreResultBean {
    private String count;
    private List<MoreInfo> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<MoreInfo> getData() {
        return data;
    }

    public void setData(List<MoreInfo> data) {
        this.data = data;
    }
}
