package dao;

import model.User;

public interface UserDao {
    /**
     * 添加用户
     *
     * @param name     用户昵称
     * @param password 用户密码
     */
    void add(String name, String password);

    /**
     * 查找用户
     *
     * @param ID 用户ID
     */
    User find(String ID);
}
