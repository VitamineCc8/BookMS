package com.wei.service;

import com.google.gson.Gson;
import com.wei.common.Common;
import com.wei.common.Constant;
import com.wei.dao.AccountDao;
import com.wei.entity.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/account/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //解决跨域问题，需要添加响应头；设置相应报文的字符集
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

//        //1. 接收前端发来的JSON格式数据
//        BufferedReader reader = request.getReader();
//        // 2. 创建StringBuilder对象来累加存储从请求体中读取到的每一行
//        StringBuilder builder = new StringBuilder();
//        //3.声明临时变量
//        String bufferStr = null;
//        //4.循环读取
//        while ((bufferStr = reader.readLine()) != null) {
//            builder.append(bufferStr);
//        }
//        //5.关闭读取流
//        reader.close();
//        //6.累加的结果就是整个请求体
//        String requestBody = builder.toString();
//        // 7.创建Gson对象用于解析JSON字符串
//        Gson gson = new Gson();
//        // 8.将JSON字符串还原为Java对象
//        Account account = gson.fromJson(requestBody, Account.class);
//
//        System.out.println("account" + account);
//
//        System.out.println("requestBody = " + requestBody);


        String accountNumber = request.getParameter("account");
        String password = request.getParameter("password");
        System.out.println("账号：" + accountNumber + "密码：" + password);
        Map map = new HashMap();
        AccountDao dao = new AccountDao();
        Account user = dao.getUserByAccount(accountNumber);
        if (user != null && user.getPassword().equals(password)) {
            request.getSession().setAttribute(Constant.USER_SESSION, user);   //session中保存账号信息
            map.put("user", user);
        }
        common.convertJson(response, map);
    }
}
