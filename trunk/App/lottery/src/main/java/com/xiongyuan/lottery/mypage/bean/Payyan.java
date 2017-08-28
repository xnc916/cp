package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by gameben on 2017-08-03.
 */

public class Payyan {

    /**
     * errormsg :
     * result : {"status":"wait","backTime":null}
     * TIME : 1501754413
     */

    private String errormsg;
    private ResultBean result;
    private int TIME;

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getTIME() {
        return TIME;
    }

    public void setTIME(int TIME) {
        this.TIME = TIME;
    }

    public static class ResultBean {
        /**
         * status : wait
         * backTime : null
         */

        private String status;
        private Object backTime;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getBackTime() {
            return backTime;
        }

        public void setBackTime(Object backTime) {
            this.backTime = backTime;
        }
    }
}
