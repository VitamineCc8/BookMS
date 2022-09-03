package com.wei.dao;

import com.wei.entity.Account;
import com.wei.util.Common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AccountDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Common common = new Common();


    public int modifyPwd(Account account) {
        String accountNumber = account.getAccount();
        String password = account.getPassword();

        String sql = "UPDATE account_tb "
                + "SET password = '" + password + "' "
                + "WHERE account = '" + accountNumber + "'";
        return common.updateSql(sql);
    }

    public Account getUserByAccount(String accountNumber) {
        String sql = "select * from account_tb where account = ?";
        Account account = null;
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, accountNumber);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                account = new Account(id, accountNumber, password, name);
            }
            return account;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }
}