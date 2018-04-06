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
     * @return 用户名对应的用户
     */
    User find(String nickname);


    /**
     * 查找某用户的关注列表
     *
     * @param ID 用户编号
     * @return 关注列表
     */
    User[] findFriend(String ID);
}
