package com.xiongyuan.lottery.secondpage.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gameben on 2017-07-06.
 */

public class IntegralResult {

    /**
     * errormsg :
     * result : {"time":{"1":{"start":90000,"end":129600,"qualif":"888"},"2":{"start":122400,"end":126000,"qualif":"2888"},"3":{"start":140400,"end":144000,"qualif":"5888"}},"prize":{"1":{"prize":"50积分","times":"4","num":"50","unit":""},"2":{"prize":"500积分","times":"4","num":"500","unit":""},"3":{"prize":"5000积分","times":"2","num":"5000","unit":""},"4":{"prize":"iphone6","times":"1","num":1,"unit":"台"},"5":{"prize":"ipad pro","times":"1","num":1,"unit":"台"}}}
     * TIME : 1499334098
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


    public static class ResultBean {
        /**
         * time : {"1":{"start":90000,"end":129600,"qualif":"888"},"2":{"start":122400,"end":126000,"qualif":"2888"},"3":{"start":140400,"end":144000,"qualif":"5888"}}
         * prize : {"1":{"prize":"50积分","times":"4","num":"50","unit":""},"2":{"prize":"500积分","times":"4","num":"500","unit":""},"3":{"prize":"5000积分","times":"2","num":"5000","unit":""},"4":{"prize":"iphone6","times":"1","num":1,"unit":"台"},"5":{"prize":"ipad pro","times":"1","num":1,"unit":"台"}}
         */

        private TimeBean time;
        private PrizeBean prize;

        public TimeBean getTime() {
            return time;
        }

        public void setTime(TimeBean time) {
            this.time = time;
        }

        public PrizeBean getPrize() {
            return prize;
        }

        public void setPrize(PrizeBean prize) {
            this.prize = prize;
        }

        public static class TimeBean {
            /**
             * 1 : {"start":90000,"end":129600,"qualif":"888"}
             * 2 : {"start":122400,"end":126000,"qualif":"2888"}
             * 3 : {"start":140400,"end":144000,"qualif":"5888"}
             */

            @SerializedName("1")
            private _$1Bean _$1;
            @SerializedName("2")
            private _$1Bean _$2;
            @SerializedName("3")
            private _$1Bean _$3;

            public _$1Bean get_$1() {
                return _$1;
            }

            public _$1Bean get_$2() {
                return _$2;
            }

            public _$1Bean get_$3() {
                return _$3;
            }

            public static class _$1Bean {


                private int start;
                private int end;
                private String qualif;

                public int getStart() {
                    return start;
                }

                public void setStart(int start) {
                    this.start = start;
                }

                public int getEnd() {
                    return end;
                }

                public void setEnd(int end) {
                    this.end = end;
                }

                public String getQualif() {
                    return qualif;
                }

                public void setQualif(String qualif) {
                    this.qualif = qualif;
                }
            }

        }

        public static class PrizeBean {
            /**
             * 1 : {"prize":"50积分","times":"4","num":"50","unit":""}
             * 2 : {"prize":"500积分","times":"4","num":"500","unit":""}
             * 3 : {"prize":"5000积分","times":"2","num":"5000","unit":""}
             * 4 : {"prize":"iphone6","times":"1","num":1,"unit":"台"}
             * 5 : {"prize":"ipad pro","times":"1","num":1,"unit":"台"}
             */

            @SerializedName("1")
            private _$1BeanX _$1;
            @SerializedName("2")
            private _$1BeanX _$2;
            @SerializedName("3")
            private _$1BeanX _$3;
            @SerializedName("4")
            private _$1BeanX _$4;
            @SerializedName("5")
            private _$1BeanX _$5;

            public _$1BeanX get_$1() {
                return _$1;
            }

            public void set_$1(_$1BeanX _$1) {
                this._$1 = _$1;
            }

            public _$1BeanX get_$2() {
                return _$2;
            }

            public void set_$2(_$1BeanX _$2) {
                this._$2 = _$2;
            }

            public _$1BeanX get_$3() {
                return _$3;
            }

            public void set_$3(_$1BeanX _$3) {
                this._$3 = _$3;
            }

            public _$1BeanX get_$4() {
                return _$4;
            }

            public void set_$4(_$1BeanX _$4) {
                this._$4 = _$4;
            }

            public _$1BeanX get_$5() {
                return _$5;
            }

            public void set_$5(_$1BeanX _$5) {
                this._$5 = _$5;
            }

            public static class _$1BeanX {
                /**
                 * prize : 50积分
                 * times : 4
                 * num : 50
                 * unit :
                 */

                private String prize;
                private String times;
                private String num;
                private String unit;

                public String getPrize() {
                    return prize;
                }

                public void setPrize(String prize) {
                    this.prize = prize;
                }

                public String getTimes() {
                    return times;
                }

                public void setTimes(String times) {
                    this.times = times;
                }

                public String getNum() {
                    return num;
                }

                public void setNum(String num) {
                    this.num = num;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }
            }


        }
    }
}
