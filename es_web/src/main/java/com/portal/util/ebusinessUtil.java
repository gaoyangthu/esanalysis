package com.portal.util;

import com.portal.bean.Databean;
import com.portal.bean.channelBean;
import com.portal.bean.channelInfoBean;
import com.portal.bean.detailBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


/**
 * Created by zhangzheming on 2014/4/2.
 */
public class ebusinessUtil {

   public ebusinessUtil(){}

   public Connection getMySQLConnection(){
       String driver = "com.mysql.jdbc.Driver";
       // URL指向要访问的数据库名scutcs
       String url = "jdbc:mysql://172.18.108.102:3306/esanalysis";
       // MySQL配置时的用户名
       String user = "root";
       // Java连接MySQL配置时的密码
       String password = "bigdata_mysql";
       Connection conn = null;
       try {
           // 加载驱动程序
           Class.forName(driver);
           // 连续数据库
           conn = DriverManager.getConnection(url, user, password);
       }catch(Exception e){
           e.printStackTrace();
       }
       return conn;
   }

    public Connection getPgSQLConnection(String database){
        String url ="jdbc:postgresql://172.18.108.101:5432/"+database ;
        String user="postgres";
        String password="postgres";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection(url, user, password);

        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }

    public  JSONObject getData(){
        Databean bean = new Databean();
        Connection conn = null;
        List<String> timelist = new ArrayList<String>();
        List<String> UVlist = new ArrayList<String>();
        List<String> PVlist = new ArrayList<String>();

        //获取数据库连接
        conn = getMySQLConnection();
        try{
            if (!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            // 要执行的SQL语句
            String sql = "select date,count(distinct bd_user_uuid) as UV,sum(pv) as PV from ebusi_daily_report group by date order by date ;";
            ResultSet rs = statement.executeQuery(sql);
            String date = "";
            String UV = "";
            String PV = "";

            while (rs.next()){
                date = rs.getString("date");
                UV = rs.getString("UV");
                PV = rs.getString("PV");
                if(PV == null || PV.length() == 0){
                    PV = "0";
                }
                timelist.add(date);
                UVlist.add(UV);
                PVlist.add(PV);
            }
            bean.setTime(timelist.toString());
            bean.setUv(UVlist.toString());
            bean.setPv(PVlist.toString());

            rs.close();
            statement.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject object = JSONObject.fromObject(bean);
        return object;
    }

   public List<channelBean> getChannels_list(){
       Connection conn = getPgSQLConnection("ebusi_channel");
       Statement stmt = null;
       ResultSet rs = null;
       channelBean channelbean = null;

       List channellist = new ArrayList<channelBean>();
       try {
           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           String sql = "select serial_id,channel_name from channel_rule";
           rs = stmt.executeQuery(sql);
           while(rs.next()){
              channelbean = new channelBean();
              String serial_id = rs.getString("serial_id");
              String channel_name = rs.getString("channel_name");
              channelbean.setId(serial_id);
              channelbean.setName(channel_name);
              channellist.add(channelbean);
           }

           rs.close();
           stmt.close();
           conn.close();
       }catch(Exception e){
           e.printStackTrace();
       }

       return channellist;
   }

   public List<channelInfoBean> getChannelInfo(String sqlparam,String dateparam,String enddate){
       Connection conn = null;
       conn =  getMySQLConnection();

       Statement stmt = null;
       ResultSet rs = null;
       channelInfoBean channelinfobean = null;
       String uv = "";
       String user_channel_tag = "";
       String register_count = "";
       String total_order_count = "";
       String payed_order_count = "";
       String payed_order_amount = "";
       String order_cash_amount = "";
       String date = "";
       String pv = "";
       List<channelInfoBean> channelInfolist = new ArrayList<channelInfoBean>();
       try{
           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           String sql = "select user_channel_tag,sum(pv) as pv,count(distinct bd_user_uuid) as uv,sum(register_count) as sumregister,sum(total_order_count) as sumordercount,sum(payed_order_count) as sumpayedcount,sum(payed_order_amount) as sumpayamount,sum(order_cash_amount) as sumcashamount,date\n" +
                   "from ebusi_daily_report where user_channel_tag in ("+sqlparam+") and date >='"+dateparam+"'and date <='"+enddate+"' group by user_channel_tag;";
           rs = stmt.executeQuery(sql);
           while (rs.next()){
               date = dateparam + "<->" +enddate;
               pv = rs.getString("pv");
               if(pv == null){
                   pv = "0";
               }
               uv = rs.getString("uv");
               user_channel_tag = rs.getString("user_channel_tag");
               register_count = rs.getString("sumregister");
               total_order_count = rs.getString("sumordercount");
               payed_order_count = rs.getString("sumpayedcount");
               payed_order_amount = rs.getString("sumpayamount");
               order_cash_amount = rs.getString("sumcashamount");

               channelinfobean = new channelInfoBean();
               channelinfobean.setDate(date);
               channelinfobean.setTag(user_channel_tag);
               channelinfobean.setPv(pv);
               channelinfobean.setUv(uv);
               channelinfobean.setRegisterCount(register_count);
               channelinfobean.setOrderCount(total_order_count);
               channelinfobean.setPayedCount(payed_order_count);
               channelinfobean.setPayedAmount(payed_order_amount);
               channelinfobean.setCashAmount(order_cash_amount);

               channelInfolist.add(channelinfobean);
           }

           rs.close();
           stmt.close();
           conn.close();
       }catch(Exception e){
           System.out.println(user_channel_tag+"渠道没有数据!");
           e.printStackTrace();
       }
       return channelInfolist;
   }

   public Connection getTestConnection(){
       String driver = "com.mysql.jdbc.Driver";
       // URL指向要访问的数据库名scutcs
       String url = "jdbc:mysql://172.18.108.102:3306/mysql_test";
       // MySQL配置时的用户名
       String user = "root";
       // Java连接MySQL配置时的密码
       String password = "bigdata_mysql";
       Connection conn = null;
       try {
           // 加载驱动程序
           Class.forName(driver);
           // 连续数据库
           conn = DriverManager.getConnection(url, user, password);
       }catch(Exception e){
           e.printStackTrace();
       }

       return conn;
   }

   public List<detailBean> getdetailInfo(String channel,String date,String enddate) {
       Connection conn = null;
       conn = getMySQLConnection();
       Statement stmt = null;
       ResultSet rs = null;

       detailBean detailbean = null;
       String user_channel_tag = "";
       String email = "";
       String user_name = "";
       String first_channel_visit = "";
       String total_order_amount = "";
       String payed_order_amount = "";
       String order_cash_amount = "";

       List<detailBean> list = new ArrayList<detailBean>();
       try {
           stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           String sql = "select user_channel_tag,user_name,email,first_channel_visit,total_order_amount,payed_order_amount,order_cash_amount from ebusi_daily_report " +
                   "where user_channel_tag=" + channel + " and date >='" + date + "' and date <='"+enddate+"'";
           rs = stmt.executeQuery(sql);
           while (rs.next()) {
               user_channel_tag = rs.getString("user_channel_tag");
               email = rs.getString("email");
               user_name = rs.getString("user_name");
               if(user_name == null || user_name.length() == 0){
                   continue;
               }
               first_channel_visit = rs.getString("first_channel_visit");
               total_order_amount = rs.getString("total_order_amount");
               payed_order_amount = rs.getString("payed_order_amount");
               order_cash_amount = rs.getString("order_cash_amount");

               detailbean = new detailBean();
               detailbean.setEmail(email);
               detailbean.setUser_channel_tag(user_channel_tag);
               detailbean.setFirst_channel_visit(first_channel_visit);
               detailbean.setUser_name(user_name);
               detailbean.setTotal_order_amount(total_order_amount);
               detailbean.setPayed_order_amount(payed_order_amount);
               detailbean.setOrder_cash_amount(order_cash_amount);

               list.add(detailbean);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }

       return list;
   }
}
