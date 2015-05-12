package com.portal.bean;

import java.util.Date;

/**
 * Created by zhangzheming on 2014/4/8.
 */
public class channelInfoBean {

    private String tag;
    private String date;
    private String pv;
    private String uv;
    private String registerCount;
    private String orderCount;
    private String payedCount;
    private String payedAmount;
    private String cashAmount;

    public channelInfoBean(String tag,
                           String date,
                           String pv,
                           String uv,
                           String registerCount,
                           String orderCount,
                           String payedCount,
                           String payedAmount,
                           String cashAmount) {
        this.tag = tag;
        this.date = date;
        this.pv = pv;
        this.uv = uv;
        this.registerCount = registerCount;
        this.orderCount = orderCount;
        this.payedCount = payedCount;
        this.payedAmount = payedAmount;
        this.cashAmount = cashAmount;
    }

    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public channelInfoBean(){}

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(String payedAmount) {
        this.payedAmount = payedAmount;
    }

    public String getPayedCount() {
        return payedCount;
    }

    public void setPayedCount(String payedCount) {
        this.payedCount = payedCount;
    }

    public String getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(String registerCount) {
        this.registerCount = registerCount;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
