package com.xiongyuan.lottery.secondpage.bean;

/**
 * Created by gameben on 2017-07-07.
 */

public class YhdrjfResult {

    /**
     * errormsg :
     * result : {"num":0}
     * TIME : 1499392123
     */
    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public static class ResultBean {
        /**
         * num : 0
         */

        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
