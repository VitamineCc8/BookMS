package com.wei.service;

import com.wei.dao.BookDao;
import com.wei.dao.UserDao;
import com.wei.entity.Book;
import com.wei.entity.User;
import com.wei.util.Common;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/bms/query")
public class QueryServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //解决跨域问题，需要添加响应头；设置相应报文的字符集
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        //获取分页的信息、获取图书名称
        int pn = Integer.parseInt(request.getParameter("pn"));
        int rn = Integer.parseInt(request.getParameter("rn"));
        String checkName = request.getParameter("inputName");  // 浏览器返回的参数

        int total = 0;
        Map map = new HashMap();
        if (Boolean.parseBoolean(request.getParameter("type"))) {
            BookDao dao = new BookDao();
            // 进行分页获取数据
            List<Book> bookList = dao.getBookList(checkName, pn, rn,request);
            total = dao.count(checkName);
            map.put("listByName", bookList);
        } else {
            UserDao userDao = new UserDao();
            List<User> readerList = userDao.getReaderList(checkName, pn, rn);
            total = userDao.count(checkName);
            map.put("listByName", readerList);
        }

        map.put("total", total);

        common.convertJson(response, map);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }
}