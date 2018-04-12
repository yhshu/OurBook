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
    public Book findByID(int ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book WHERE ID = ?");
            stm.setInt(1, ID);
            Book[] books = getBooks(stm);
            if (books != null) return books[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book WHERE name = ?");
            stm.setString(1, name);
            Book[] books = getBooks(stm);
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywords(String[] keywords) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Book WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords));
            Book[] books = getBooks(stm);
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public void add(Book book) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO book (ID,name,description,chiefEditor,keywords) VALUES (0,?,?,?,?)");
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            stm.setString(3, book.getChiefEditor());
            stm.setString(4, book.getKeywords());
            try {
                stm.executeUpdate();
                System.out.println("BookDao: 添加书目成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("BookDao: 添加书目失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int maxID() {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT MAX(id) AS max_id FROM book");
            ResultSet rs;
            try {
                rs = stm.executeQuery();
                int ret = rs.getInt("max_id");
                rs.close();
                stm.close();
                conn.close(); // 关闭数据库连接
                System.out.println("BookDao: 查询最大ID成功");
                return ret;
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("BookDao: 查询最大ID失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Book[] findByUserID(String chiefEditorID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book WHERE chiefEditorID = ?");
            stm.setString(1, chiefEditorID);
            Book[] books = getBooks(stm);
            if (books != null)
                return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    /**
     * 返回符合语句的多个书籍
     *
     * @param stm SQL语句
     * @return 符合语句的多个书籍
     */
    private Book[] getBooks(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book(rs.getInt("ID"), rs.getString("name"),
                        rs.getString("description"), rs.getString("chiefEditor"),
                        rs.getString("keywords"));
                books.add(book);
            }
            rs.close();
            stm.close();
            conn.close(); // 关闭数据库连接
            return books.toArray(new Book[0]);
        } catch (Exception e) {
            System.out.println("BookDao: 获取书目失败");
        }
        return null;
    }
}
