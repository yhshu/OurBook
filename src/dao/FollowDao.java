package dao;

import model.Follow;
import model.User;

public interface FollowDao {
    /**
     * 添加新关注的用户
     *
     * @param user 新关注用户
     */
    void add(Follow user);

    /**
     * 取消关注
     *
     * @param username 被取消的用户
     */
    void del(String username);

    /**
     * 查找某用户的关注列表
     *
     * @param follower 用户编号
     * @return 用户正关注的其他用户的用户名
     */
    String[] findFollowing(String follower);

    /**
     * 查找某用户的被关注列表
     *
     * @param followee 用户编号
     * @return 用户被其他人关注的列表
     */
    String[] findFollowed(String followee);

}
