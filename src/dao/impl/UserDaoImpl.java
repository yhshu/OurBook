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
    public User[] search(String keyword) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            ArrayList<User> users = new ArrayList<>();
            stm = conn.prepareStatement("SELECT * FROM author WHERE username LIKE ? or nickname LIKE ?");
            stm.setString(1, "%" + keyword + "%");
            stm.setString(2, "%" + keyword + "%");
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("nickname"),
                        null, rs.getString("description"), rs.getString("avatar"));
                user.setClicks(rs.getInt("clicks"));
                user.setFavorites(rs.getInt("favorites"));
                users.add(user);
            }
            rs.close();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 获取用户失败");
            return new User[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public void add(User user) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("INSERT INTO user(username,nickname,password,avatar) VALUES (?,?,?,?)");
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getNickname());
            stm.setString(3, user.getPassword());
            stm.setString(4, "resources/avatar/empty-avatar.png");
            stm.executeUpdate();
            System.out.println("UserDao: 注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 注册失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean exist(String username) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                rs.close();
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 获取用户失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public User find(String username) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("nickname"), rs.getString("password"), rs.getString("description"), rs.getString("avatar"));
                rs.close();
                return user;
            } else return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 获取用户失败");
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    public String[] findFollowing(String follower) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM follow WHERE follower = ?");
            stm.setString(1, follower);
            ResultSet rs = stm.executeQuery();
            ArrayList<String> users = new ArrayList<>();
            while (rs.next()) {
                users.add(rs.getString("username"));
            }
            rs.close();
            return users.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 获取关注列表失败");
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean modify(String username, String nickname, String description, String avatar) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("UPDATE user SET nickname = ?, description = ?, avatar = ? WHERE username = ?");
            stm.setString(1, nickname);
            stm.setString(2, description);
            stm.setString(3, avatar);
            stm.setString(4, username);
            stm.executeUpdate();
            System.out.println("UserDao: 修改用户信息成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 修改用户信息失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean modifyPassword(String username, String newPassword) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("UPDATE user SET password = ? WHERE username =?");
            stm.setString(1, newPassword);
            stm.setString(2, username);
            stm.executeUpdate();
            System.out.println("UserDao: 修改密码成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 修改密码失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean addFavorite(String username, int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("INSERT INTO favorite VALUES (?,?,NOW())");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.executeUpdate();
            System.out.println("UserDao: 收藏成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 收藏失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean cancelFavorite(String username, int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("DELETE FROM favorite WHERE username = ? AND bookid = ?");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.executeUpdate();
            System.out.println("UserDao: 取消收藏成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 取消收藏失败");
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean isFavorite(String username, int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("SELECT COUNT(*) FROM favorite WHERE username = ? AND bookid = ?");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            ResultSet rs = stm.executeQuery();
            rs.next();
            boolean result = rs.getInt(1) != 0;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public User[] recommend() {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            ArrayList<User> users = new ArrayList<>();
            stm = conn.prepareStatement("SELECT * FROM author ORDER BY favorites * 10 + clicks DESC");
            int displayUserNum = 5;
            ResultSet rs = stm.executeQuery();
            while (displayUserNum-- > 0 && rs.next()) {
                User user = new User(rs.getString("username"),
                        rs.getString("nickname"),
                        null, rs.getString("description"),
                        rs.getString("avatar"));
                user.setFollowers(rs.getInt("followers"));
                users.add(user);
            }
            rs.close();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new User[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }
}
