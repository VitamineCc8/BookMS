package com.wei.dao;

import com.wei.entity.Borrow;
import com.wei.util.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//借书表
public class BorrowDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Common common = new Common();

    // 根据书籍编号查询借阅数据
    public List<Integer> getBorrowByBookId(int bookId) {
        String sql = "select user_id from borrow_tb , book_tb where borrow_tb.book_id = book_tb.book_id and borrow_tb.book_id = ? ";
        List<Integer> list = new ArrayList<Integer>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            //给sql中的？占位符指定数据，指定数据类型
            stmt.setInt(1, bookId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                list.add(userId);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    // 根据员工编号查询借阅数据
    public List<Integer> getBorrowByUserId(int userId) {
        String sql = "select book_id from borrow_tb , user_tb where borrow_tb.user_id = user_tb.user_id and borrow_tb.user_id = ? ";
        List<Integer> list = new ArrayList<Integer>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            //给sql中的？占位符指定数据，指定数据类型
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                list.add(bookId);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    // 借阅图书：根据读者ID和书籍的ID新增(使用事务功能)
    public int borrowBook(int userId, int bookId, int borrowNumber) {
        String sql = "INSERT INTO borrow_tb (user_id, book_id) "
                + "values('" + userId + "'"
                + ",'" + bookId + "' )";
        try {
            conn = com.wei.dao.BaseDao.getConnection();
            //关闭数据库的自动提交，自动会开启事务；
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);

            //预编译
            for (; borrowNumber > 0; borrowNumber--) {
                stmt.executeUpdate();
            }

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

    // 归还图书
    public int returnBook(int userId, int bookId,int returnNumber) {
        String sql = "DELETE FROM  borrow_tb "
                + "WHERE user_id = '" + userId + "' and book_id = '" + bookId + "'";
        try {
            conn = com.wei.dao.BaseDao.getConnection();
            //关闭数据库的自动提交，自动会开启事务；
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);

            //预编译
            for (; returnNumber > 0; returnNumber--) {
                stmt.executeUpdate();
            }

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

    //根据页数进行获取借阅数据
    public List<Borrow> getReturnList(int pn, int rn) {
        String sql = "select * from borrow_tb limit ?,?";
        List<BorrowDao> list = new ArrayList<BorrowDao>();
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (pn - 1) * rn);
            stmt.setInt(2, rn);
            rs = stmt.executeQuery();
            return getBorrows();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    //封装借阅数据
    private List<Borrow> getBorrows() throws SQLException {
        List<Borrow> list = new ArrayList<Borrow>();
        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            int userId = rs.getInt("user_id");
            Borrow borrow = new Borrow(userId, bookId);
            list.add(borrow);
        }
        return list;
    }
}