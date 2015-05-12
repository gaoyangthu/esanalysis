package com.portal.bean;

/**
 * Created by zhangzheming on 2014/4/4.
 */
public class channelBean {

    private String id;
    private String name;

    public channelBean(){}

    public channelBean(String id,String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
