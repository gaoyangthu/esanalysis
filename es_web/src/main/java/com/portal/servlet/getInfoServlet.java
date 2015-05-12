package com.portal.servlet;

import com.portal.bean.channelBean;
import com.portal.bean.channelInfoBean;
import com.portal.bean.detailBean;
import com.portal.util.ebusinessUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangzheming on 2014/4/2.
 */
public class getInfoServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");
        ebusinessUtil util = new ebusinessUtil();
        String param = request.getParameter("param");

        PrintWriter pw = response.getWriter();
        String result = null;
        if (param == null || param == ""){
             System.out.println("error in param，can not provide data!");
             return;
        }

        if(param.equals("data")){
            JSONObject bean = util.getData();
            pw.println(bean);
        }

        if(param.equals("channels_list")){
            List<channelBean> list = util.getChannels_list();
            JSONArray jsonarray = JSONArray.fromObject(list);
            //转化为字符串
            pw.println(jsonarray.toString());
        }

        if(param.equals("channels_info")){
            String timeparam = request.getParameter("timeparam");
            String endtime = request.getParameter("endtime");

            String id_lst = request.getParameter("id_lst");
            id_lst = id_lst.substring(1,id_lst.length()-1);

            List<channelInfoBean> list = util.getChannelInfo(id_lst,timeparam,endtime);
            JSONArray jsonArraylist = JSONArray.fromObject(list);
            pw.println(jsonArraylist.toString());
        }

        if(param.equals("detailinfo")){
            String id = request.getParameter("id");
            String time = request.getParameter("time");
            String endtime = request.getParameter("endtime");
            List<detailBean> list = util.getdetailInfo(id,time,endtime);
            JSONArray jsonArraylist = JSONArray.fromObject(list);
            pw.println(jsonArraylist.toString());
        }

        //pw.println(result);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
            doPost(request,response);
    }


}
