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
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM comment WHERE bookID = ?");
            stm.setInt(1, bookID);
            Comment[] comments = getComments(stm);
            stm.close();
            conn.close();
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean add(String username, int bookID, String content) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO comment(ID, username, bookID, datatime, content) VALUES (null, ?, ?, NOW(), ?)");
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
            stm.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("DELETE FROM comment WHERE ID = ?");
            stm.setInt(1, ID);
            try {
                stm.executeUpdate();
                System.out.println("CommentDao: 评论删除成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("CommentDao: 评论删除失败");
            }
            stm.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Comment[] getComments(PreparedStatement stm) throws SQLException {
        ResultSet rs = stm.executeQuery();
        ArrayList<Comment> comments = new ArrayList<>();
        while (rs.next()) {
            Comment comment = new Comment(rs.getInt("ID"), rs.getString("username"), rs.getInt("bookID"), rs.getTimestamp("datatime"), rs.getString("content"));
            comments.add(comment);
        }
        rs.close();
        return comments.toArray(new Comment[0]);
    }
}