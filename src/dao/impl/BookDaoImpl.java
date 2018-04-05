package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;

import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.util.ArrayList;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findByID(String ID) {
        try {
            conn = DBUtil.connectDB("book"); // 连接数据库
            PreparedStatement stm;
            stm = conn.prepareStatement("SELECT * FROM book WHERE ID = ?");
            stm.setString(1, ID);
            try {
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    Book book = new Book();
                    book.setID(rs.getString("ID"));
                    book.setName(rs.getString("name"));
                    book.setDescription(rs.getString("description"));
                    book.setChiefEditorID(rs.getString("chiefEditorID"));
                    return book;
                } else return null;
            } catch (Exception e1) {
                System.out.println("获取信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        try {
            conn = DBUtil.connectDB("book"); // 连接数据库
            PreparedStatement stm;
            stm = conn.prepareStatement("SELECT * FROM book WHERE name = ?");
            stm.setString(1, name);
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<Book> books = new ArrayList<>();
                while (rs.next()) {
                    Book book = new Book();
                    book.setID(rs.getString("ID"));
                    book.setName(rs.getString("name"));
                    book.setDescription(rs.getString("description"));
                    book.setChiefEditorID(rs.getString("chiefEditorID"));
                    books.add(book);
                }
                return books.toArray(new Book[0]);
            } catch (Exception e1) {
                System.out.println("获取信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                System.out.println("插入信息失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
