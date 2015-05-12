package com.portal.util;

import com.portal.bean.Databean;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangzheming on 2014/4/17.
 */
public class testUtil {

    public  testUtil(){}

    public static Connection getMySQLConnection(){
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

    public static JSONObject getData(){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject object = JSONObject.fromObject(bean);
        return object;
    }

}
