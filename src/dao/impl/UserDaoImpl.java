package dao.impl;

import Util.DBUtil;
import dao.UserDao;
import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class UserDaoImpl implements UserDao {
    private Connection conn = null;

    @Override
    public User[] search(String keyword) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            ArrayList<User> users = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM author WHERE username LIKE ? or nickname LIKE ?");
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
            stm.close();
            conn.close();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 获取用户失败");
        }
        return new User[0];
    }

    @Override
    public void add(User user) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO user(username,nickname,password,avatar) VALUES (?,?,?,?)");
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getNickname());
            stm.setString(3, user.getPassword());
            stm.setString(4, "/resources/avatar/empty-avatar.png");
            try {
                stm.executeUpdate();
                System.out.println("UserDao: 注册成功");
            } catch (Exception e1) {
                System.out.println("UserDao: 注册失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean exist(String username) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
            stm.setString(1, username);
            try {
                ResultSet rs = stm.executeQuery();
                if (rs.next()) return true;
                return false;
            } catch (Exception e1) {
                System.out.println("UserDao: 获取用户失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
                            rs.getString("password"), rs.getString("description"),
                            rs.getString("avatar"));
                    rs.close();
                    stm.close();
                    conn.close();
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

    public String[] findFollowing(String follower) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM follow WHERE follower = ?");
            stm.setString(1, follower);
            try {
                ResultSet rs = stm.executeQuery();
                ArrayList<String> users = new ArrayList<>();
                while (rs.next()) {
                    users.add(rs.getString("username"));
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

    @Override
    public boolean modify(String username, String nickname, String description, String avatar) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("UPDATE user SET nickname = ?, description = ?, avatar = ? WHERE username = ?");
            stm.setString(1, nickname);
            stm.setString(2, description);
            stm.setString(3, avatar);
            stm.setString(4, username);
            stm.executeUpdate();
            System.out.println("UserDao: 修改用户信息成功");
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 修改用户信息失败");
        }
        return false;
    }

    @Override
    public boolean addFavorite(String username, int bookID) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO favorite VALUES (?,?,?)");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
            stm.executeUpdate();
            System.out.println("UserDao: 收藏成功");
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 收藏失败");
        }
        return false;
    }

    @Override
    public boolean cancelFavorite(String username, int bookID) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("DELETE FROM favorite WHERE username = ? AND bookid = ?");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.executeUpdate();
            System.out.println("UserDao: 取消收藏成功");
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("UserDao: 取消收藏失败");
        }
        return false;
    }

    @Override
    public boolean isFavorite(String username, int bookID) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("SELECT COUNT(*) FROM favorite WHERE username = ? AND bookid = ?");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            ResultSet rs = stm.executeQuery();
            rs.next();
            boolean result = rs.getInt(1) != 0;
            conn.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User[] recommend() {
        // 推荐 创作章节数最多的5个用户 作为活跃用户
        try {
            conn = DBUtil.connectDB();
            ArrayList<User> users = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM author ORDER BY favorites * 10 + clicks DESC");
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
            stm.close();
            conn.close();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User[0];
    }
}
