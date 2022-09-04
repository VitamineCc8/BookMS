package com.wei.dao;


import com.wei.common.Common;
import com.wei.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Common common = new Common();

    // 根据页数和书名进行获取书籍数据
    public List<User> getReaderList(String readerName, int pn, int rn) {
        String sql = "select * from user_tb where user_name like ? limit ?,? ";
        List<User> list = new ArrayList<User>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            if (readerName == null) {
                readerName = "%";
            } else {
                readerName = "%" + readerName + "%";
            }
            stmt.setString(1, readerName);
            stmt.setInt(2, (pn - 1) * rn);
            stmt.setInt(3, rn);
            rs = stmt.executeQuery();
            return getUsers();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    // 根据信息查询数据
    public List<User> getReaderList(String readerName) {
        String sql = "select * from user_tb where user_name like ? ";
        List<User> list = new ArrayList<User>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            if (readerName == null) {
                readerName = "%";
            } else {
                readerName = "%" + readerName + "%";
            }
            stmt.setString(1, readerName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userSex = rs.getString("user_sex");
                String userPhone = rs.getString("user_phone");
                String userAccount = rs.getString("user_account");
                User user = new User(userId, userName, userSex, userPhone, userAccount);
                list.add(user);
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

    // 根据人名获取员工数据条数，用于查询时的分页显示
    public int count(String userName) {
        String sql = "select count(*) num from user_tb where user_name like ?";
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            if (userName == null) {
                userName = "%";
            } else {
                userName = "%" + userName + "%";
            }
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("num");
            }
            return 0;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    // 添加管理员
    public int addReader(User user) {
        String userName = user.getUserName();
        String userSex = user.getUserSex();
        String userPhone = user.getUserPhone();
        String userAccount = user.getUserAccount();

        String userSql = "INSERT INTO user_tb (user_name, user_sex,user_phone,user_account) "
                + "values('" + userName + "'"
                + ",'" + userSex + "'"
                + ",'" + userPhone + "'"
                + ",'" + userAccount + "' )";

        String acoountSql = "INSERT INTO account_tb (account, password,name) "
                + "values('" + userAccount + "'"
                + ",'123'"
                + ",'" + userName + "' )";

        return common.updateSql(userSql, acoountSql);
    }

    // 根据人名删除管理员用户
    public int deleteReader(String userName) {
        List<User> list = this.getReaderList(userName);
        if (null == list || list.size() == 0) {
            return 0;
        } else {
            String userSql = "DELETE FROM user_tb "
                    + "WHERE user_name = '" + userName + "'";
            String accountSql = "DELETE FROM account_tb "
                    + "WHERE name = '" + userName + "'";
            return common.updateSql(userSql, accountSql);
        }
    }

    // 修改管理员数据
    public int modifyReader(User user) {
        int userId = user.getUserId();
        String userName = user.getUserName();
        String userSex = user.getUserSex();
        String userPhone = user.getUserPhone();
        String userAccount = user.getUserAccount();

        String userSql = "UPDATE user_tb "
                + "SET user_name = '" + userName + "',"
                + "user_sex ='" + userSex + "',"
                + "user_phone = '" + userPhone + "'"
                + " WHERE user_id = '" + userId + "'";

        String accountSql = "UPDATE account_tb "
                + "SET name = '" + userName + "'"
                + " WHERE account = '" + userAccount + "'";

        return common.updateSql(userSql, accountSql);
    }

    // 根据页数进行获取员工数据
    public List<User> getReaderList(int pn, int rn) {
        String sql = "select * from user_tb limit ?,?";
        List<User> list = new ArrayList<User>();
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (pn - 1) * rn);
            stmt.setInt(2, rn);
            rs = stmt.executeQuery();
            return getUsers();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

    private List<User> getUsers() throws SQLException {
        List<User> list = new ArrayList<User>();
        BorrowDao borrowDao = new BorrowDao();
        while (rs.next()) {
            int returnById = 0;
            int userId = rs.getInt("user_id");
            String userName = rs.getString("user_name");
            String userSex = rs.getString("user_sex");
            String userPhone = rs.getString("user_phone");
            String userAccount = rs.getString("user_account");
            if (borrowDao.getBorrowByUserId(userId) != null && borrowDao.getBorrowByUserId(userId).size() > 0) {
                returnById = 1;
            }
            User user = new User(userId, returnById, userName, userSex, userPhone, userAccount);
            list.add(user);
        }
        return list;
    }

    public int count() {
        String sql = "select count(*) num from user_tb ";
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("num");
            }
            return 0;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }

}