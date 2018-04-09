package dao.impl;

import Util.DBUtil;
import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    private Connection conn = null;

    @Override
    public void add(User user) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO user(ID,nickname,password) VALUES (0,?,?)");
            stm.setString(1, user.getNickname());
            stm.setString(2, user.getPassword());
            try {
                stm.executeUpdate();
                System.out.println("UserDao: 注册成功");
            } catch (Exception e1) {
                System.out.println("UserDao: 注册失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String username) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, username);
            try {
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    User user = new User(rs.getString("username"), rs.getString("nickname"),
                            rs.getString("password"), rs.getString("description"));
                    rs.close();
                    stm.close();
                    conn.close(); // 关闭数据库连接
                    return user;
                } else return null;
            } catch (Exception e1) {
                System.out.println("UserDao: 获取用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] findFollowing(String ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Friend WHERE ID = ?");
            stm.setString(1, ID);
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<String> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(rs.getString("nickname"));
                }
                rs.close();
                stm.close();
                conn.close();
                return users.toArray(new String[0]);
            } catch (Exception el) {
                System.out.println("UserDao: 获取关注列表失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
