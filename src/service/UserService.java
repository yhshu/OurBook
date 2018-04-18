package service;

import model.User;

public interface UserService {

    /**
     * 查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    User find(String username);

    /**
     * 用户注册
     * 用户信息插入数据库，注册成功后跳转到登录页
     *
     * @param username 用户名
     * @param nickname 昵称
     * @param password 密码
     * @return 若用户名存在将注册失败 false
     */
    boolean register(String username, String nickname, String password);

    /**
     * 用户登录
     * 核对用户名密码，若登录成功跳转到个人主页，并发送 cookie
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功 true，用户名或密码错误 false
     */
    boolean login(String username, String password);

    /**
     * 登录后，通过用户名获取昵称
     *
     * @param username 用户名
     * @return 用户昵称
     */
    String getNickname(String username);

    /**
     * 查找关注该用户的其他用户
     * @param username 用户名
     * @return 关注该用户的用户的用户名
     */
    User[] getFollowers(String username);

    /**
     * 查找被该用户关注的用户
     * @param username 用户名
     * @return 被该用户关注的用户的用户名
     */
    User[] getFollowees(String username);
}
