package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findByID(String ID) {
        try {
            conn = DBUtil.connectDB("Book"); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Book WHERE ID = ?");
            stm.setString(1, ID);
            try {
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    Book book = new Book();
                    book.setID(rs.getString("ID"));
                    book.setName(rs.getString("name"));
                    book.setDescription(rs.getString("description"));
                    book.setChiefEditorID(rs.getString("chiefEditorID"));
                    rs.close();
                    stm.close();
                    conn.close();
                    return book;
                } else return null;
            } catch (Exception e1) {
                System.out.println("获取书目失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        try {
            conn = DBUtil.connectDB("Book"); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Book WHERE name = ?");
            stm.setString(1, name);
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<Book> books = new ArrayList<>();
                while (rs.next()) {
                    Book book = new Book(rs.getString("ID"), rs.getString("name"),
                            rs.getString("description"), rs.getString("chiefEditorID"));
                    books.add(book);
                }
                rs.close();
                stm.close();
                conn.close(); // 关闭数据库连接
                return books.toArray(new Book[0]);
            } catch (Exception e1) {
                System.out.println("获取书目失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public void add(Book book) {
        try {
            conn = DBUtil.connectDB("Book"); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Book (name,description,chiefEditorID) VALUES (?,?,?)");
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            stm.setString(3, book.getChiefEditorID());
            try {
                stm.executeUpdate();
                System.out.println("添加书目成功");
            } catch (Exception e1) {
                System.out.println("添加书目失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
