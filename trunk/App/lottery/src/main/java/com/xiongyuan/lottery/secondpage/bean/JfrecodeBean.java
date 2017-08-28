package com.xiongyuan.lottery.secondpage.bean;

import java.util.List;

/**
 * Created by gameben on 2017-08-11.
 */

public class JfrecodeBean {
    /**
     * data : [{"id":"G629259590748262","user_name":"user200","user_id":"200","addTime":"1498725959","currentAmount":"398324","usable":"2","usableAmount":"398326","type":"bet","intro":"投注消费2元,赠送2积分"},{"id":"G629258689391217","user_name":"user200","user_id":"200","addTime":"1498725868","currentAmount":"398322","usable":"2","usableAmount":"398324","type":"bet","intro":"投注消费2元,赠送2积分"},{"id":"G629226124300296","user_name":"user200","user_id":"200","addTime":"1498722612","currentAmount":"398318","usable":"4","usableAmount":"398322","type":"bet","intro":"投注消费4元,赠送4积分"},{"id":"G629226123831640","user_name":"user200","user_id":"200","addTime":"1498722612","currentAmount":"398310","usable":"8","usableAmount":"398318","type":"bet","intro":"投注消费8元,赠送8积分"},{"id":"G629226123709751","user_name":"user200","user_id":"200","addTime":"1498722612","currentAmount":"398302","usable":"8","usableAmount":"398310","type":"bet","intro":"投注消费8元,赠送8积分"},{"id":"G629226123587247","user_name":"user200","user_id":"200","addTime":"1498722612","currentAmount":"398296","usable":"6","usableAmount":"398302","type":"bet","intro":"投注消费6元,赠送6积分"},{"id":"G629226123460066","user_name":"user200","user_id":"200","addTime":"1498722612","currentAmount":"398290","usable":"6","usableAmount":"398296","type":"bet","intro":"投注消费6元,赠送6积分"},{"id":"G629224178429311","user_name":"user200","user_id":"200","addTime":"1498722417","currentAmount":"398286","usable":"4","usableAmount":"398290","type":"bet","intro":"投注消费4元,赠送4积分"},{"id":"G629224178309598","user_name":"user200","user_id":"200","addTime":"1498722417","currentAmount":"398278","usable":"8","usableAmount":"398286","type":"bet","intro":"投注消费8元,赠送8积分"},{"id":"G629224178188053","user_name":"user200","user_id":"200","addTime":"1498722417","currentAmount":"398270","usable":"8","usableAmount":"398278","type":"bet","intro":"投注消费8元,赠送8积分"}]
     * count : 58
     */

    private String count;
    private List<JfrecodeInfo> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<JfrecodeInfo> getData() {
        return data;
    }

    public void setData(List<JfrecodeInfo> data) {
        this.data = data;
    }
}
