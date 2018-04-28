package dao.impl;

import Util.DBUtil;
import dao.MessageDao;
import model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageDaoImpl implements MessageDao {
    private Connection conn = null;

    @Override
    public void read(String owner, String from) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("UPDATE message SET `read`=TRUE WHERE owner=? AND `from`=?");
            stm.setString(1, owner);
            stm.setString(2, from);
            try {
                stm.executeUpdate();
                System.out.println("MessageDao: 设置已读成功");
            } catch (Exception e1) {
                System.out.println("MessageDao: 设置已读失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(String to, String from, String content) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM message");
            ResultSet rs = stm1.executeQuery();
            rs.next();
            int maxID = rs.getInt("max_ID");
            rs.close();
            stm1.close();
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO message(owner, `to`, `from`, time, content,ID,`read`)" +
                    " values (?,?,?,NOW(),?,?,TRUE)");
            stm2.setString(1, from);
            stm2.setString(2, to);
            stm2.setString(3, from);
            stm2.setString(4, content);
            stm2.setInt(5, maxID + 1);
            PreparedStatement stm3 = conn.prepareStatement("INSERT INTO message(owner, `to`, `from`, time, content,ID,`read`)" +
                    " values (?,?,?,NOW(),?,?,FALSE)");
            stm3.setString(1, to);
            stm3.setString(2, to);
            stm3.setString(3, from);
            stm3.setString(4, content);
            stm3.setInt(5, maxID + 2);
            try {
                stm2.executeUpdate();
                stm3.executeUpdate();
                System.out.println("MessageDao: 添加成功");
            } catch (Exception e1) {
                System.out.println("MessageDao: 添加失败");
            }
            stm2.close();
            stm3.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void delete(int ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("DELETE FROM message WHERE ID=?");
            stm.setInt(1, ID);
            try {
                stm.executeUpdate();
                System.out.println("MessageDao: 删除成功");
            } catch (Exception e1) {
                System.out.println("MessageDao: 取消失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear(String owner, String target) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("DELETE FROM message WHERE owner=? AND (`from`=? OR `to`=?)");
            stm.setString(1, owner);
            stm.setString(2, target);
            stm.setString(3, target);
            try {
                stm.executeUpdate();
                System.out.println("MessageDao: 清除成功");
            } catch (Exception e1) {
                System.out.println("MessageDao: 清除失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message[] get(String owner) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM message WHERE owner = ? ORDER BY time DESC");
            stm.setString(1, owner);
            Message[] messages = getMessages(stm);
            conn.close();
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按获取私信失败");
        }
        return new Message[0];
    }

    private Message[] getMessages(PreparedStatement stm) throws SQLException {
        ResultSet rs = stm.executeQuery();
        ArrayList<Message> messages = new ArrayList<>();
        while (rs.next()) {
            Message message = new Message();
            message.setTime(rs.getTimestamp("time"));
            message.setTo(rs.getString("to"));
            message.setFrom(rs.getString("from"));
            message.setContent(rs.getString("content"));
            message.setRead(rs.getBoolean("read"));
            message.setID(rs.getInt("ID"));
            messages.add(message);
        }
        rs.close();
        stm.close();
        return messages.toArray(new Message[0]);
    }
}
