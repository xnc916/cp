package com.xiongyuan.lottery.mypage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-06-07.
 */

public class WithdrawalResult {
    private String errormsg;
    private List<WithdrawalInfo> result;

    public String getErrormsg() {
        return errormsg;
    }

    public List<WithdrawalInfo> getResult() {
        return result;
    }

}
