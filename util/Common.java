package com.wei.util;


import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Common {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;

    //更新sql
    public int updateSql(String sql) {
        try {
            conn = com.wei.dao.BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            int res = stmt.executeUpdate();
            return res;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } finally {
            com.wei.dao.BaseDao.closeAll(conn, stmt, rs);
        }
    }

    //把数据库得到的数据，组装成json格式发送给前端
    public void convertJson(HttpServletResponse response, Map map) throws IOException {
        String json = new Gson().toJson(map);
        PrintWriter out = response.getWriter();
        out.println(json);
        out.close();
    }

    public int updateSql(String firstSql, String secondSql) {
        try {
            conn = com.wei.dao.BaseDao.getConnection();
            //关闭数据库的自动提交，自动会开启事务；
            conn.setAutoCommit(false);

            //预编译
            stmt = conn.prepareStatement(firstSql);
            stmt.executeUpdate();
            stmt = conn.prepareStatement(secondSql);
            stmt.executeUpdate();

            //业务完毕，提交事务
            conn.commit();
            return 1;
        } catch (SQLException e) {
            try {
                conn.rollback();//如果失败回滚事务
            } catch (SQLException e1) {
                e1.printStackTrace();
                return 0;
            }
            e.printStackTrace();
            return 0;
        } finally {
            com.wei.dao.BaseDao.closeAll(conn, stmt, rs);
        }

    }
}
