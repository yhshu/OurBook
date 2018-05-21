package service;

import model.User;

public interface UserService {
    /**
     * 搜索用户
     *
     * @param keyword 关于用户名或昵称的关键字
     * @return 匹配的所有用户
     */
    User[] search(String keyword);

    /**
     * 精准查找用户
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
     * @return 登录成功 User对象，用户名或密码错误 null
     */
    User login(String username, String password);

    /**
     * 查找关注该用户的其他用户
     *
     * @param username 用户名
     * @return 关注该用户的用户
     */
    User[] getFollowers(String username);

    /**
     * 查找被该用户关注的用户
     *
     * @param username 用户名
     * @return 被该用户关注的用户
     */
    User[] getFollowees(String username);

    /**
     * 修改昵称和一句话描述
     *
     * @param username    用户名
     * @param nickname    新昵称
     * @param description 一句话描述
     * @param avatar      头像地址
     * @return 修改成功 true 修改失败 false
     */
    boolean modify(String username, String nickname, String description, String avatar);

    /**
     * 修改密码
     *
     * @param username         用户名
     * @param newPassword      新密码
     * @return 修改成功 true 修改失败 false
     */
    boolean modifyPassword(String username, String newPassword);

    /**
     * 添加收藏
     *
     * @param username 用户名
     * @param bookID   书ID
     * @return 收藏成功 true 收藏失败 false
     */
    boolean addFavorite(String username, int bookID);

    /**
     * 取消收藏
     *
     * @param username 用户名
     * @param bookID   书ID
     * @return 取消成功 true 取消失败 false
     */
    boolean cancelFavorite(String username, int bookID);

    /**
     * 检查是否被收藏
     *
     * @param username 用户名
     * @param bookID   书ID
     * @return 被收藏 true 未被收藏 false
     */
    boolean isFavorite(String username, int bookID);

    /**
     * 首页推荐活跃用户
     *
     * @return 活跃用户
     */
    User[] recommend();
}
