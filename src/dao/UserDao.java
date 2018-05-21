package dao;

import model.User;

public interface UserDao {
    /**
     * 搜索用户
     *
     * @param keyword 关于用户名或昵称的关键字
     * @return 匹配的所有用户
     */
    User[] search(String keyword);

    /**
     * 添加用户
     *
     * @param user 新注册用户
     */
    void add(User user);

    /**
     * 判断用户是否存在
     *
     * @param username 用户名
     * @return 存在true，不存在false
     */
    boolean exist(String username);

    /**
     * 查找用户
     *
     * @param username 用户名
     * @return 用户名对应的用户
     */
    User find(String username);

    /**
     * 修改昵称和一句话描述
     *
     * @param username    通过用户名查找用户
     * @param nickname    新昵称
     * @param description 新描述
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
     * 推荐 创作章节数最多的5个用户 作为活跃用户
     *
     * @return 活跃用户
     */
    User[] recommend();
}
