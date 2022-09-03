package com.wei.service;

import com.google.gson.Gson;
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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/bms/add")  // 注解用于标注在一个继承了HttpServlet类
public class AddServlet extends HttpServlet {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Common common = new Common();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解决跨域和编码问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        int num = 0;

        if(Boolean.parseBoolean(request.getParameter("type"))){
            //得到要新增的图书信息
            String addBookName = request.getParameter("addBookName");
            String addBookType = request.getParameter("addBookType");
            String addBookISBN = request.getParameter("addBookISBN");
            String addBookAuthor = request.getParameter("addBookAuthor");
            String addBookPrice = request.getParameter("addBookPrice");
            String addBookBeginNumber = request.getParameter("addBookBeginNumber");

            // 新增操作
            BookDao dao = new BookDao();
            Book book = new Book(addBookName,addBookAuthor,addBookType,addBookISBN,addBookPrice,addBookBeginNumber,addBookBeginNumber);
            // 返回新增结果
            num = dao.addBook(book);   //返回0表示错误
        }else {
            //得到新增的员工信息
            String addUserName = request.getParameter("addUserName");
            String addUserSex = request.getParameter("addUserSex");
            String addUserPhone = request.getParameter("addUserPhone");
            String addUserAccount = request.getParameter("addUserAccount");

            // 新增操作
            UserDao userDao = new UserDao();
            User user = new User(addUserName, addUserSex, addUserPhone, addUserAccount);
            // 返回新增结果
            num = userDao.addReader(user);   //返回0表示错误
        }

        // 数据封装
        Map map = new HashMap();
        map.put("checkOK", num);

        common.convertJson(response, map);
    }
}