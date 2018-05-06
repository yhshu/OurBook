package dao.impl;

import Util.DBUtil;
import dao.CommentDao;
import model.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommentDaoImpl implements CommentDao {
    private Connection conn = null;

    @Override
    public Comment[] findByBookID(int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM comment WHERE bookID = ? ORDER BY `time` DESC");
            stm.setInt(1, bookID);
            return getComments(stm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Comment find(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM comment WHERE ID = ?");
            stm.setInt(1, ID);
            Comment[] comments = getComments(stm);
            return comments[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean add(String username, int bookID, String content) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("INSERT INTO comment(ID, username, bookID, `time`, content) VALUES (null, ?, ?, NOW(), ?)");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.setString(3, content);
            try {
                stm.executeUpdate();
                System.out.println("CommentDao: 评论添加成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("CommentDao: 评论添加失败");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean delete(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("DELETE FROM comment WHERE ID = ?");
            stm.setInt(1, ID);
            stm.executeUpdate();
            System.out.println("CommentDao: 评论删除成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("CommentDao: 评论删除失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    private Comment[] getComments(PreparedStatement stm) throws SQLException {
        ResultSet rs = stm.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            Comment comment = new Comment(rs.getInt("ID"), rs.getString("username"), rs.getInt("bookID"), rs.getTimestamp("time"), rs.getString("content"));
            comments.add(comment);
        }
        rs.close();
        return comments.toArray(new Comment[0]);
    }
}