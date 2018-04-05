package dao.impl;

import Util.DBUtil;
import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    private Connection conn = null;

    @Override
    public void add(User user) {
        try {
            conn = DBUtil.connectDB("User"); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO User(name,password) VALUES (?,?)");
            stm.setString(1, user.getNickname());
            stm.setString(2, user.getPassword());
            try {
                stm.executeUpdate();
                System.out.println("注册成功");
            } catch (Exception e1) {
                System.out.println("注册失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User find(String ID) {
        try {
            conn = DBUtil.connectDB("User"); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM User WHERE ID = ?");
            stm.setString(1, ID);
            try {
                ResultSet rs = stm.executeQuery();
                if (rs.next()) {
                    User user = new User(rs.getString("ID"), rs.getString("nickname"), rs.getString("password"));
                    rs.close();
                    stm.close();
                    conn.close(); // 关闭数据库连接
                    return user;
                } else return null;
            } catch (Exception e1) {
                System.out.println("获取用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
