package com.wei.service;

import com.wei.common.Common;
import com.wei.dao.BorrowDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bms/return")
public class returnServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //解决跨域问题，需要添加响应头；设置相应报文的字符集
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        int returnUserId = Integer.parseInt(request.getParameter("return_userId"));
        int returnBookId = Integer.parseInt(request.getParameter("return_bookId"));
        int returnNumber = Integer.parseInt(request.getParameter("return_number"));

        BorrowDao borrowDao = new BorrowDao();
        int res = borrowDao.returnBook(returnUserId, returnBookId, returnNumber);

        // 数据封装，返回结果
        Map map = new HashMap();
        map.put("checkOK", res);

        common.convertJson(response, map);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}