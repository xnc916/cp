package com.xiongyuan.lottery.model;

/**
 * Created by Administrator on 2017-05-12.
 */

public class HttpModel {

    //创建对象
    private static HttpModel model=new HttpModel();
    //私有化构造
    private HttpModel() {
    }
    //获取单例对象
    public static HttpModel getInstance(){

        return model;
    }



}
