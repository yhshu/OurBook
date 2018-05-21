package dao;

import model.User;

public interface FollowDao {
    /**
     * 添加新关注的用户
     *
     * @param follower 关注用户
     * @param followee 被关注用户
     */
    void add(String follower, String followee);

    /**
     * 取消关注
     *
     * @param follower 关注用户
     * @param followee 被关注用户
     */
    void del(String follower, String followee);

    /**
     * 查找某用户的关注列表
     *
     * @param follower 用户编号
     * @return 用户正关注的其他用户的用户名
     */
    User[] findFollowees(String follower);

    /**
     * 查找某用户的被关注列表
     *
     * @param followee 用户编号
     * @return 用户被其他人关注的列表
     */
    User[] findFollowers(String followee);

    /**
     * 检查是否关注
     *
     * @param follower 关注者
     * @param followee 被关注者
     * @return
     */
    boolean isFollowing(String follower, String followee);
}
