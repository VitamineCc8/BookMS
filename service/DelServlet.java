package com.wei.service;

import com.wei.common.Common;
import com.wei.dao.BookDao;
import com.wei.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bms/del")
public class DelServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解决跨域和编码问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        int num = 0;
        if (Boolean.parseBoolean(request.getParameter("type"))) {
            // 得到要删除的信息编号
            int mid = Integer.parseInt(request.getParameter("Id"));
            // 删除操作
            BookDao dao = new BookDao();
            // 删除结果返回判断
            num = dao.deleteBook(mid,request);
        } else {
            String userName = request.getParameter("userName");
            UserDao userDao = new UserDao();
            num = userDao.deleteReader(userName);
        }


        // 数据封装，并返回
        Map map = new HashMap();
        map.put("checkOK", num);

        common.convertJson(response, map);
    }
}