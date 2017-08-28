package com.xiongyuan.lottery.homepage.bean.pkdata;

import com.xiongyuan.lottery.thirdpage.bean.Shil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gameben on 2017-07-20.
 */

public class SerMap implements Serializable {
    public HashMap<String,ArrayList<Shil>> map;
    public  SerMap(){

    }

    public HashMap<String, ArrayList<Shil>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, ArrayList<Shil>> map) {
        this.map = map;

    }

    @Override
    public String toString() {
        return "SerMap{" +
                "map=" + map +
                '}';
    }
}
