package com.portal.bean;

/**
 * Created by zhangzheming on 2014/4/17.
 */
public class Databean {

    private String time;
    private String pv;
    private String uv;

    public Databean(){}

    public Databean(String time,String pv,String uv){
        this.time = time;
        this.pv = pv;
        this.uv = uv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }
}
