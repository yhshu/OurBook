package dao.impl;

import Util.DBUtil;
import dao.FollowDao;
import model.Follow;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * @program:OurBook
 * @description: 关注其他用户、取消关注、查找关注用户的列表、
 * @create: 04-12-23
 */
public class FollowDaoImpl implements FollowDao {
    private Connection conn = null;

    /*
     *添加关注的人的，只能一个一个添加，用按钮形式<关注>
     */
    @Override
    public void add(Follow follow) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO follow(follower,followee) VALUES (?,?)");
            stm.setString(1, follow.getFollower());
            stm.setString(2, follow.getFollowee());
            try {
                stm.executeUpdate();
                System.out.println("FollowDao: 添加成功");
            } catch (Exception e1) {
                System.out.println("FollowDao: 添加失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *取消关注的人，只能一个一个删除，用按钮形式<取消关注>
     */
    @Override
    public void del(Follow follow) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("DELETE FROM follow WHERE followee=? and follower=?");
            stm.setString(1, follow.getFollowee());
            stm.setString(2, follow.getFollower());
            try {
                stm.executeUpdate();
                System.out.println("UserDao: 取消成功");
            } catch (Exception e1) {
                System.out.println("UserDao: 取消失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *查找关注的人的列表<homepage,otherpage 同>
     */
    @Override
    public User[] findFollowers(String followee) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM follow, user WHERE follower = username AND followee = ?");
            stm.setString(1, followee);
            User[] followers = getUsers(stm);
            if(followers!=null) return followers;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User[0];
    }

    /**
     * 查找某用户的被关注列表
     *
     * @param follower 用户编号  知道followee的名字，用作follower来搜索
     * @return 用户被其他人关注的列表
     */
    public User[] findFollowees(String follower) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM follow, user WHERE followee = username AND follower = ?");
            stm.setString(1, follower);
            User[] followees = getUsers(stm);
            if(followees!=null) return followees;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User[0];
    }


    @Override
    /**
     * 添加信息
     *
     * @param follow 查找的主码
     * @return 用户被其他人关注的列表
     */
    public void addDialog(Follow follow) {

    }

    @Override
    /**
     * 查找历史的信息
     *
     * @param follower 用户编号  知道followee的名字，用作follower来搜索
     * @return 用户被其他人关注的列表
     */
    public String[] findDialogMessage(Follow follow) {
        return new String[0];
    }

    @Override
    public boolean isFollowing(String follower, String followee) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("SELECT COUNT(*) FROM follow WHERE follower = ? AND followee = ?");
            stm.setString(1, follower);
            stm.setString(2, followee);
            ResultSet rs = stm.executeQuery();
            rs.next();
            return rs.getInt(1) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private User[] getUsers(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("nickname"),
                        rs.getString("password"), rs.getString("description"),
                        rs.getString("avatar"));
                users.add(user);
            }
            rs.close();
            stm.close();
            conn.close(); // 关闭数据库连接
            return users.toArray(new User[0]);
        } catch (Exception e) {
            System.out.println("BookDao: 获取书目失败:");
            e.printStackTrace();
        }
        return null;
    }
}
