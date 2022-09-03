package com.wei.service;

import com.google.gson.Gson;
import com.wei.dao.AccountDao;
import com.wei.entity.Account;
import com.wei.util.Common;
import com.wei.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
