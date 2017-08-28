package com.xiongyuan.lottery.base;

/**
 * Created by gameben on 2017-06-07.
 */
public class Preconditions {

    public static void checkNonNull(Object object, String info){
        if (object == null){
            throw new RuntimeException("CheckNonNull fail: "+ info);
        }
    }
}
