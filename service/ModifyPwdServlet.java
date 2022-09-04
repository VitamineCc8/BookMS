package com.wei.service;

import com.wei.common.Common;
import com.wei.dao.AccountDao;
import com.wei.entity.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bms/modPwd")
public class ModifyPwdServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 解决跨域和编码问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");


        String mod_account = request.getParameter("mod_account");
        String mod_pwd = request.getParameter("mod_pwd");

        // 进行修改
        Account account = new Account(mod_account, mod_pwd);

        AccountDao accountDao = new AccountDao();
        // 返回修改结果
        int res = accountDao.modifyPwd(account);

        // 数据封装，返回结果
        Map map = new HashMap();
        map.put("checkOK", res);

        common.convertJson(response, map);
    }
}