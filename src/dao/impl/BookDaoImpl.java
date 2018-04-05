package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;

import java.sql.*;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findByID(String ID) {
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        return new Book[0];
    }

    @Override
    public void add(Book book) {
        conn = DBUtil.connectDB("book");
    }
}
