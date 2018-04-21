package dao.impl;

import Util.DBUtil;
import dao.FollowDao;
import model.Follow;

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
    public String[] findFollowers(String followee) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM follow WHERE followee = ?");
            stm.setString(1, followee);
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<String> follower_arraylist = new ArrayList<>();
                while (rs.next()) {
                    follower_arraylist.add(rs.getString("follower"));  //得到followee关注的人
                }
                rs.close();
                stm.close();
                conn.close();
                return follower_arraylist.toArray(new String[0]);
            } catch (Exception el) {
                System.out.println("UserDao: 获取关注列表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找某用户的被关注列表
     *
     * @param follower 用户编号  知道followee的名字，用作follower来搜索
     * @return 用户被其他人关注的列表
     */
    public String[] findFollowees(String follower) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM follow WHERE follower = ?");
            stm.setString(1, follower);                  //查找followee作为follower被关注的人
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<String> followee_arraylist = new ArrayList<>();
                while (rs.next()) {
                    followee_arraylist.add(rs.getString("followee"));
                }
                rs.close();
                stm.close();
                conn.close();
                return followee_arraylist.toArray(new String[0]);
            } catch (Exception el) {
                System.out.println("UserDao: 获取关注列表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
}
