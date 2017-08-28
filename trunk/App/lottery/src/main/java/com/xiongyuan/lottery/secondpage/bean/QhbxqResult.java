package com.xiongyuan.lottery.secondpage.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gameben on 2017-07-06.
 */

public class QhbxqResult {


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
         * 500 : {"num":[1,8],"qualif":500}
         * 2000 : {"num":[9,28],"qualif":2000}
         * 8000 : {"num":[29,58],"qualif":8000}
         */

        @SerializedName("500")
        private _$500Bean _$500;
        @SerializedName("2000")
        private _$2000Bean _$2000;
        @SerializedName("8000")
        private _$8000Bean _$8000;

        public _$500Bean get_$500() {
            return _$500;
        }

        public void set_$500(_$500Bean _$500) {
            this._$500 = _$500;
        }

        public _$2000Bean get_$2000() {
            return _$2000;
        }

        public void set_$2000(_$2000Bean _$2000) {
            this._$2000 = _$2000;
        }

        public _$8000Bean get_$8000() {
            return _$8000;
        }

        public void set_$8000(_$8000Bean _$8000) {
            this._$8000 = _$8000;
        }

        public static class _$500Bean {
            /**
             * num : [1,8]
             * qualif : 500
             */

            private int qualif;
            private List<Integer> num;

            public int getQualif() {
                return qualif;
            }

            public void setQualif(int qualif) {
                this.qualif = qualif;
            }

            public List<Integer> getNum() {
                return num;
            }

            public void setNum(List<Integer> num) {
                this.num = num;
            }
        }

        public static class _$2000Bean {
            /**
             * num : [9,28]
             * qualif : 2000
             */

            private int qualif;
            private List<Integer> num;

            public int getQualif() {
                return qualif;
            }

            public void setQualif(int qualif) {
                this.qualif = qualif;
            }

            public List<Integer> getNum() {
                return num;
            }

            public void setNum(List<Integer> num) {
                this.num = num;
            }
        }

        public static class _$8000Bean {
            /**
             * num : [29,58]
             * qualif : 8000
             */

            private int qualif;
            private List<Integer> num;

            public int getQualif() {
                return qualif;
            }

            public void setQualif(int qualif) {
                this.qualif = qualif;
            }

            public List<Integer> getNum() {
                return num;
            }

            public void setNum(List<Integer> num) {
                this.num = num;
            }
        }
    }
}
