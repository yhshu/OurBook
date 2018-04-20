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
     * @param username 用户名
     * @return 用户名对应的用户
     */
    User find(String username);


    /**
     * 查找某用户的关注列表
     *
     * @param username 用户编号
     * @return 用户正关注的其他用户的用户名
     */
    String[] findFollowing(String username);

    /**
     * 登录后，通过用户名获取昵称
     *
     * @param username 用户名
     * @return 用户昵称
     */
    String getNickname(String username);

    /**
     * 修改昵称和一句话描述
     *
     * @param username    通过用户名查找用户
     * @param nickname    新昵称
     * @param description 新描述
     * @param avatar      头像地址
     */
    boolean modify(String username, String nickname, String description,String avatar);
}
