package com.xiongyuan.lottery.mypage.view.table;

/**
 * Data:2016/5/27 17:47
 * Created by WCP
 */
public class BaseBean {
    private String errormsg;
    private OnlineSaleBean result;
    private int TIME;

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public OnlineSaleBean getResult() {
        return result;
    }

    public void setResult(OnlineSaleBean result) {
        this.result = result;
    }

}
