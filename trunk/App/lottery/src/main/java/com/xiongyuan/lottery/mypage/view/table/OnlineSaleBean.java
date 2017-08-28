package com.xiongyuan.lottery.mypage.view.table;

import java.util.List;

/**
 * 实际的数据模型（在多个种表格的常见下，要单独建类似的模型）
 *
 * 特点：每条记录包含行标题与行的所有列内容
 *
 * demo可以直接使用TableModel
 *
 * Data:2016-06-23 16:23
 * Created by YJG
 */
public class OnlineSaleBean{




        private String count;
        private List<TableModel> data;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<TableModel> getData() {
            return data;
        }

        public void setData(List<TableModel> data) {
            this.data = data;
        }


}
