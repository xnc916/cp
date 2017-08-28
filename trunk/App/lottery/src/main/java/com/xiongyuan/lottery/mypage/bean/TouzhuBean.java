package com.xiongyuan.lottery.mypage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-06-30.
 */

public class TouzhuBean {


        private String count;
        private List<TouzhuInfo> data;

        public String getCount() {
            return count;
        }
        public void setCount(String count) {
            this.count = count;
        }

        public List<TouzhuInfo> getData() {
            return data;
        }

        public void setData(List<TouzhuInfo> data) {
            this.data = data;
        }

    }
