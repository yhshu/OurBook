package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;

import javax.servlet.RequestDispatcher;
import java.sql.*;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findbyID(String ID) {
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        return new Book[0];
    }

    @Override
    public void add(Book book) {
        try {
            conn = DBUtil.connectDB("book"); // 连接数据库
            PreparedStatement stm = null;
            stm = conn.prepareStatement("INSERT INTO Book (name,description,chiefEditorID) VALUES (?,?,?)");
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            stm.setString(3, book.getChiefEditorID());
            try {
                stm.executeUpdate();
                System.out.println("插入信息成功");
            } catch (Exception e1) {
                System.out.println("插入信息成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
