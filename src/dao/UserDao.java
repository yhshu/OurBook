package dao;

import model.User;

public interface UserDao {
    /**
     * 添加用户
     *
     * @param name
     * @param password
     */
    void add(String name, String password);

    /**
     * 查找用户
     *
     * @param
     */
    User find();
}
