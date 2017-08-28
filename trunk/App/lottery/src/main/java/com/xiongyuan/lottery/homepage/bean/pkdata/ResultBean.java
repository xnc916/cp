package com.xiongyuan.lottery.homepage.bean.pkdata;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gameben on 2017-06-10.
 */

public class ResultBean {

    private GameBean game;
    private PlayBean play;
    @SerializedName("type")
    private TypeBean resultBeantype;

    public GameBean getGame() {
        return game;
    }

    public void setGame(GameBean game) {
        this.game = game;
    }

    public PlayBean getPlay() {
        return play;
    }

    public void setPlay(PlayBean play) {
        this.play = play;
    }

    public TypeBean getResultBeantype() {
        return resultBeantype;
    }

    public void setResultBeantype(TypeBean resultBeantype) {
        this.resultBeantype = resultBeantype;
    }
}
