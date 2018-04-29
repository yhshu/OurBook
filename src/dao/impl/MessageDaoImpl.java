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
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("UPDATE message SET `read`=TRUE WHERE owner=? AND `from`=?");
            stm.setString(1, owner);
            stm.setString(2, from);
            stm.executeUpdate();
            System.out.println("MessageDao: 设置已读成功");
        } catch (Exception e) {
            System.out.println("MessageDao: 设置已读失败");
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }

    }

    @Override
    public boolean add(String to, String from, String content) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm1 = conn.prepareStatement("INSERT INTO message(owner, `to`, `from`, time, content,ID,`read`)" +
                    " values (?,?,?,NOW(),?,NULL,TRUE)");
            stm1.setString(1, from);
            stm1.setString(2, to);
            stm1.setString(3, from);
            stm1.setString(4, content);
            stm2 = conn.prepareStatement("INSERT INTO message(owner, `to`, `from`, time, content,ID,`read`)" +
                    " values (?,?,?,NOW(),?,NULL,FALSE)");
            stm2.setString(1, to);
            stm2.setString(2, to);
            stm2.setString(3, from);
            stm2.setString(4, content);
            stm1.executeUpdate();
            stm2.executeUpdate();
            System.out.println("MessageDao: 添加成功");
            return true;
        } catch (Exception e) {
            System.out.println("MessageDao: 添加失败");
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(conn);
        }
        return false;
    }

    @Override
    public boolean delete(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("DELETE FROM message WHERE ID=?");
            stm.setInt(1, ID);
            stm.executeUpdate();
            System.out.println("MessageDao: 删除成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MessageDao: 删除失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return false;
    }

    @Override
    public boolean clear(String owner, String target) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("DELETE FROM message WHERE owner=? AND (`from`=? OR `to`=?)");
            stm.setString(1, owner);
            stm.setString(2, target);
            stm.setString(3, target);
            stm.executeUpdate();
            System.out.println("MessageDao: 清除成功");
            return true;
        } catch (Exception e) {
            System.out.println("MessageDao: 清除失败");
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Message[] get(String owner) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM message_info WHERE owner = ? ORDER BY time");
            stm.setString(1, owner);
            return getMessages(stm);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 获取私信失败");
            return new Message[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public int getUnreadNumber(String owner) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT COUNT(*) AS count FROM message WHERE owner = ? AND `read`=FALSE");
            stm.setString(1, owner);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt("count");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 获取未读私信数量失败");
            return 0;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
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
            try {
                message.setToNickname(rs.getString("to_nickname"));
                message.setToAvatar(rs.getString("to_avatar"));
                message.setFromNickname(rs.getString("from_nickname"));
                message.setFromAvatar(rs.getString("from_avatar"));
            } catch (Exception ignored) {
            }
            messages.add(message);
        }
        rs.close();
        return messages.toArray(new Message[0]);
    }
}
