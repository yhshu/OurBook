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
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("UPDATE notification SET `read`=TRUE WHERE ID=?");
            stm.setInt(1, ID);
            try {
                stm.executeUpdate();
                System.out.println("NotificationDao: 设置已读成功");
            } catch (Exception e1) {
                System.out.println("NotificationDao: 设置已读失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean notify(String username, String header, String content) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM notification");
            ResultSet rs = stm1.executeQuery();
            rs.next();
            int maxID = rs.getInt("max_ID");
            rs.close();
            stm1.close();
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO notification(ID,username,time,header,content,`read`) VALUES (?,?,NOW(),?,?,FALSE)");
            stm2.setInt(1, maxID + 1);
            stm2.setString(2, username);
            stm2.setString(3, header);
            stm2.setString(4, content);
            try {
                stm2.executeUpdate();
                System.out.println("NotificationDao: 添加成功");
            } catch (Exception e1) {
                System.out.println("NotificationDao: 添加失败");
            }
            stm2.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean notifyFollowers(String followee, String header, String content) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            ArrayList<String> followers = new ArrayList<>();

            // 获取关注者
            PreparedStatement stm1 = conn.prepareStatement("SELECT follower FROM follow WHERE followee=?");
            stm1.setString(1, followee);
            ResultSet rs = stm1.executeQuery();
            while (rs.next())
                followers.add(rs.getString("follower"));
            rs.close();
            stm1.close();

            // 添加通知
            for (String follower : followers) {
                PreparedStatement stm2 = conn.prepareStatement("INSERT INTO notification(ID,username,time,header,content,`read`) VALUES (NULL,?,NOW(),?,?,FALSE)");
                stm2.setString(1, follower);
                stm2.setString(2, header);
                stm2.setString(3, content);
                try {
                    stm2.executeUpdate();
                    System.out.println("NotificationDao: 添加成功");
                } catch (Exception e1) {
                    System.out.println("NotificationDao: 添加失败");
                }
                stm2.close();
            }
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
            PreparedStatement stm = conn.prepareStatement("DELETE FROM notification WHERE ID=?");
            stm.setInt(1, ID);
            try {
                stm.executeUpdate();
                System.out.println("NotificationDao: 删除成功");
            } catch (Exception e1) {
                System.out.println("NotificationDao: 取消失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Notification[] getByUsername(String username, boolean isRead) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM notification WHERE username = ? AND `read`=? ORDER BY time DESC");
            stm.setString(1, username);
            stm.setBoolean(2, isRead);
            Notification[] notifications = getNotifications(stm);
            conn.close();
            return notifications;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按书目获取章节失败");
        }
        return new Notification[0];
    }

    private Notification[] getNotifications(PreparedStatement stm) throws SQLException {
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
        stm.close();
        return notifications.toArray(new Notification[0]);
    }
}
