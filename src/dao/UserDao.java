package dao;

import model.User;

public interface UserDao {
    /**
     * 添加用户
     *
     * @param user 新注册用户
     */
    void add(User user);

    /**
     * 查找用户
     *
     * @param nickname 用户名
     */
    User find(String nickname);
}
