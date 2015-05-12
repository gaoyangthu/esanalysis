package com.portal.servlet;

import com.portal.util.testUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by zhangzheming on 2014/4/17.
 */
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           String param = request.getParameter("param");
           PrintWriter  pw = response.getWriter();
           JSONObject bean = null;

           bean = testUtil.getData();
           System.out.println(bean);

        pw.println(bean);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
