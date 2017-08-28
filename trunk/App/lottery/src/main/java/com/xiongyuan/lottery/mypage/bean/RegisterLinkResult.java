package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by gameben on 2017-06-06.
 */

public class RegisterLinkResult {

    private String errormsg;
    private RegisterLinkInfo result;


    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public RegisterLinkInfo getResult() {
        return result;
    }

    public void setResult(RegisterLinkInfo result) {
        this.result = result;
    }


}
