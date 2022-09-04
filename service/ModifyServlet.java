package com.wei.service;

import com.wei.common.Common;
import com.wei.dao.BookDao;
import com.wei.dao.UserDao;
import com.wei.entity.Book;
import com.wei.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/bms/mod")
public class ModifyServlet extends HttpServlet {
    private Common common = new Common();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 解决跨域和编码问题
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        int res = 0;

        if (Boolean.parseBoolean(request.getParameter("type"))) {
            // 得到要修改的图书信息
            int mid = Integer.parseInt(request.getParameter("mod_id"));
            String modBookName = request.getParameter("mod_name");
            String modBookAuthor = request.getParameter("mod_author");
            String modType = request.getParameter("mod_type");
            String modISBN = request.getParameter("mod_ISBN");
            String modPrice = request.getParameter("mod_price");
            String modBeginNumber = request.getParameter("mod_BeginNumber");

            // 进行修改
            Book book = new Book(mid, modBookName, modBookAuthor, modType, modISBN, modPrice, modBeginNumber);
            BookDao dao = new BookDao();
            // 返回修改结果
            res = dao.modifyBook(book, mid);
        } else {
            int mid = Integer.parseInt(request.getParameter("mod_id"));
            String modName = request.getParameter("mod_name");
            String modSex = request.getParameter("mod_sex");
            String modPhone = request.getParameter("mod_phone");
            String modAccount = request.getParameter("mod_account");
            // 进行修改
            User user = new User(mid, modName, modSex, modPhone,modAccount);
            UserDao userDao = new UserDao();
            // 返回修改结果
            res = userDao.modifyReader(user);
        }


        // 数据封装，返回结果
        Map map = new HashMap();
        map.put("checkOK", res);

        common.convertJson(response, map);
    }
}