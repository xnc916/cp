package com.xiongyuan.lottery.mypage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-06-08.
 */

public class MyMessageResult {
    private String errormsg;
    private List<MyMessageInfo> result;

    public String getErrormsg() {
        return errormsg;
    }

    public List<MyMessageInfo> getResult() {
        return result;
    }
}
