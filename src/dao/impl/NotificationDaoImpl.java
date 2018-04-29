package dao.impl;

import Util.DBUtil;
import dao.NotificationDao;
import model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NotificationDaoImpl implements NotificationDao {
    private Connection conn = null;

    @Override
    public void read(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("UPDATE notification SET `read`=TRUE WHERE ID=?");
            stm.setInt(1, ID);
            stm.executeUpdate();
            System.out.println("NotificationDao: 设置已读成功");
        } catch (Exception e) {
            System.out.println("NotificationDao: 设置已读失败");
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean notify(String username, String header, String content) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("INSERT INTO " +
                    "notification(ID,username,time,header,content,`read`) VALUES (NULL,?,NOW(),?,?,FALSE)");
            stm.setString(1, username);
            stm.setString(2, header);
            stm.setString(3, content);
            stm.executeUpdate();
            System.out.println("NotificationDao: 添加成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NotificationDao: 添加失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean notifyFollowers(String followee, String header, String content) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            // 获取关注者
            stm = conn.prepareStatement("SELECT follower FROM follow WHERE followee=?");
            stm.setString(1, followee);
            sendNotifications(stm, "follower", header, content);
            return true;
        } catch (Exception e) {
            System.out.println("NotificationDao: 添加失败");
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean notifySubscribers(int bookID, String header, String content) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            // 获取关注者
            stm = conn.prepareStatement("SELECT username FROM favorite WHERE bookid=?");
            stm.setInt(1, bookID);
            sendNotifications(stm, "username", header, content);
            return true;
        } catch (Exception e) {
            System.out.println("NotificationDao: 添加失败");
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public void delete(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("DELETE FROM notification WHERE ID=?");
            stm.setInt(1, ID);
            stm.executeUpdate();
            System.out.println("NotificationDao: 删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NotificationDao: 取消失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Notification[] getByUsername(String username, boolean isRead) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM notification " +
                    "WHERE username = ? AND `read`=? ORDER BY time DESC");
            stm.setString(1, username);
            stm.setBoolean(2, isRead);
            return getNotifications(stm);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按书目获取章节失败");
            return new Notification[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public void clearRead(String username) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("DELETE FROM notification WHERE username=? AND `read`=TRUE");
            stm.setString(1, username);
            stm.executeUpdate();
            System.out.println("NotificationDao: 清除成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("NotificationDao: 清除失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    private Notification[] getNotifications(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setUsername(rs.getString("username"));
                notification.setTime(rs.getTimestamp("time"));
                notification.setHeader(rs.getString("header"));
                notification.setContent(rs.getString("content"));
                notification.setRead(rs.getBoolean("read"));
                notification.setID(rs.getInt("ID"));
                notifications.add(notification);
            }
            rs.close();
            return notifications.toArray(new Notification[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Notification[0];
        }
    }

    private void sendNotifications(PreparedStatement stm1, String columnName, String header, String content) {
        PreparedStatement stm2 = null;
        try {
            ArrayList<String> users = new ArrayList<>();
            ResultSet rs = stm1.executeQuery();
            while (rs.next())
                users.add(rs.getString(columnName));
            rs.close();
            for (String user : users) {
                stm2 = conn.prepareStatement("INSERT INTO notification(ID,username,time,header,content,`read`) " +
                        "VALUES (NULL,?,NOW(),?,?,FALSE)");
                stm2.setString(1, user);
                stm2.setString(2, header);
                stm2.setString(3, content);
                stm2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm2);
        }
    }
}
