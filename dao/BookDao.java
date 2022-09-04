package com.wei.dao;

import com.wei.common.Common;
import com.wei.common.Constant;
import com.wei.entity.Account;
import com.wei.entity.Book;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private Common common = new Common();


    // 根据页数和书名进行获取书籍数据
    public List<Book> getBookList(String bookName, int pn, int rn, HttpServletRequest request) {
        String sql = "select * from book_tb where book_name like ? limit ?,? ";
        List<Book> list = new ArrayList<Book>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            if (bookName == null) {
                bookName = "%";
            } else {
                bookName = "%" + bookName + "%";
            }
            stmt.setString(1, bookName);
            stmt.setInt(2, (pn - 1) * rn);
            stmt.setInt(3, rn);
            rs = stmt.executeQuery();
            return getBooks(request);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }


    // 根据书籍的ID查询数据
    public List<Book> getBookList(int bookId, HttpServletRequest request) {
        String sql = "select * from book_tb where book_id = ? ";
        List<Book> list = new ArrayList<Book>();
        try {
            //通过公共类得到连接对象
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookId);
            rs = stmt.executeQuery();
            return getBooks(request);
        } catch (SQLException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }


    // 根据页数进行获取书籍数据
    public List<Book> getBookList(int pn, int rn, HttpServletRequest request) {
        String sql = "select * from book_tb limit ?,?";
        List<Book> list = new ArrayList<Book>();
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (pn - 1) * rn);
            stmt.setInt(2, rn);
            rs = stmt.executeQuery();
            return getBooks(request);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            BaseDao.closeAll(conn, stmt, rs);
        }
    }


    // 获取书籍数据条数
    public int count() {
        String sql = "select count(*) num from book_tb ";
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


    // 根据书名获取书籍数据条数，用于查询时的分页显示
    public int count(String bookName) {
        String sql = "select count(*) num from book_tb where book_name like ?";
        try {
            conn = BaseDao.getConnection();
            stmt = conn.prepareStatement(sql);
            if (bookName == null) {
                bookName = "%";
            } else {
                bookName = "%" + bookName + "%";
            }
            stmt.setString(1, bookName);
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

    // 添加图书，传入Book对象
    public int addBook(Book book) {
        String bookName = book.getBookName();
        String bookAuthor = book.getBookAuthor();
        String bookTpye = book.getBookType();
        String bookISBN = book.getBookISBN();
        String bookPrice = book.getBookPrice();
        String bookBeginNumber = book.getBookBeginNumber();
        String bookNowNumber = book.getBookNowNumber();
        String sql = "INSERT INTO book_tb (book_name, book_author,book_type,book_ISBN,book_price,book_beginNumber,book_nowNumber) "
                + "values('" + bookName + "'"
                + ",'" + bookAuthor + "'"
                + ",'" + bookTpye + "'"
                + ",'" + bookISBN + "'"
                + ",'" + bookPrice + "'"
                + ",'" + bookBeginNumber + "'"
                + ",'" + bookNowNumber + "' )";
        int i = common.updateSql(sql);
        return i;
    }


    // 根据书的ID删除数据
    public int deleteBook(int bookId, HttpServletRequest request) {
        List<Book> list = this.getBookList(bookId, request);
        if (null == list || list.size() == 0) {
            return 0;
        } else {
            String sql = "DELETE FROM  book_tb "
                    + "WHERE book_id = '" + bookId + "'";
            return common.updateSql(sql);
        }
    }


    // 传入旧书的ID和新书的数据进行修改图书
    public int modifyBook(Book book, int bookId) {
        int count = 0;
        BorrowDao borrowDao = new BorrowDao();
        String bookName = book.getBookName();
        String bookAuthor = book.getBookAuthor();
        String bookTpye = book.getBookType();
        String bookISBN = book.getBookISBN();
        String bookPrice = book.getBookPrice();
        String bookBeginNumber = book.getBookBeginNumber();

        for (int borrowUserId : borrowDao.getBorrowByBookId(bookId)) {
            count++;
        }

        //判断总数不能小于被借阅数
        if (count > Integer.parseInt(bookBeginNumber)) {
            return -1;
        }

        String sql = "UPDATE book_tb "
                + "SET book_name = '" + bookName + "',"
                + "book_author ='" + bookAuthor + "',"
                + "book_type ='" + bookTpye + "',"
                + "book_ISBN ='" + bookISBN + "',"
                + "book_price ='" + bookPrice + "',"
                + "book_beginNumber ='" + bookBeginNumber + "'"
                + "WHERE book_id = '" + bookId + "'";
        return common.updateSql(sql);
    }


    //封装书本数据
    private List<Book> getBooks(HttpServletRequest request) throws SQLException {
        List<Book> list = new ArrayList<Book>();
        BorrowDao borrowDao = new BorrowDao();
        Book book;
        Account account = (Account) request.getSession().getAttribute(Constant.USER_SESSION);
        while (rs.next()) {
            int temp = 0;
            int count = 0;
            int bookId = rs.getInt("book_id");
            String bookName = rs.getString("book_name");
            String bookAuthor = rs.getString("book_author");
            String bookTpye = rs.getString("book_type");
            String bookISBN = rs.getString("book_ISBN");
            String bookPrice = rs.getString("book_price");
            String bookBeginNumber = rs.getString("book_beginNumber");
            for (int borrowUserId : borrowDao.getBorrowByBookId(bookId)) {
                count++;
                if (borrowUserId == account.getId()) {
                    temp++;
                }
            }
            String bookNowNumber = String.valueOf(Integer.parseInt(bookBeginNumber) - count);
            if (temp > 0) {
                book = new Book(bookId, temp, bookName, bookAuthor, bookTpye, bookISBN, bookPrice, bookBeginNumber, bookNowNumber);
            } else {
                book = new Book(bookId, 0, bookName, bookAuthor, bookTpye, bookISBN, bookPrice, bookBeginNumber, bookNowNumber);
            }
            list.add(book);
        }
        return list;
    }
}