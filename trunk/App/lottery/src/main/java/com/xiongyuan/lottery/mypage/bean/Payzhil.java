package com.xiongyuan.lottery.mypage.bean;

/**
 * Created by gameben on 2017-08-03.
 */

public class Payzhil {

    /**
     * errormsg :
     * result : {"type":"QRCode","status":true,"log_id":"G803322835714926","src":"weixin://wxpay/bizpayurl?pr=VPvCLHA"}
     * TIME : 1501732285
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
         * type : QRCode
         * status : true
         * log_id : G803322835714926
         * src : weixin://wxpay/bizpayurl?pr=VPvCLHA
         */

        private String type;
        private boolean status;
        private String log_id;
        private String src;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
