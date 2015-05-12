package com.portal.bean;

/**
 * Created by zhangzheming on 2014/4/10.
 */
public class detailBean {

    private String user_channel_tag;
    private String email;
    private String user_name;
    private String first_channel_visit;
    private String total_order_amount;
    private String payed_order_amount;
    private String order_cash_amount;

    public detailBean(){}

    public detailBean(String user_channel_tag,
                      String email,
                      String user_name,
                      String first_channel_visit,
                      String total_order_amount,
                      String payed_order_amount,
                      String order_cash_amount) {
        this.user_channel_tag = user_channel_tag;
        this.email = email;
        this.user_name = user_name;
        this.first_channel_visit = first_channel_visit;
        this.total_order_amount = total_order_amount;
        this.payed_order_amount = payed_order_amount;
        this.order_cash_amount = order_cash_amount;
    }

    public String getUser_name(){return user_name;}

    public void setUser_name(String user_name){this.user_name = user_name;}

    public String getUser_channel_tag() {
        return user_channel_tag;
    }

    public void setUser_channel_tag(String user_channel_tag) {
        this.user_channel_tag = user_channel_tag;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_channel_visit() {
        return first_channel_visit;
    }

    public void setFirst_channel_visit(String first_channel_visit) {
        this.first_channel_visit = first_channel_visit;
    }

    public String getTotal_order_amount() {
        return total_order_amount;
    }

    public void setTotal_order_amount(String total_order_amount) {
        this.total_order_amount = total_order_amount;
    }

    public String getPayed_order_amount() {
        return payed_order_amount;
    }

    public void setPayed_order_amount(String payed_order_amount) {
        this.payed_order_amount = payed_order_amount;
    }

    public String getOrder_cash_amount() {
        return order_cash_amount;
    }

    public void setOrder_cash_amount(String order_cash_amount) {
        this.order_cash_amount = order_cash_amount;
    }
}
