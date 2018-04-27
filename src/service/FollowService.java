package service;

import model.Follow;

public interface FollowService {

    /**
     * 添加新关注的用户
     *
     * @param followee 新关注用户
     * @param follower
     */
    boolean addFollow(String followee, String follower);

    /**
     * 取消关注
     *
     * @param followee 取消的用户 follower 被取消的用户
     */
    boolean delFollow(String followee, String follower);

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

    Follow findmessagediallog(String followee, String follower);

    /**
     * 检查是否关注
     *
     * @param follower 关注者
     * @param followee 被关注者
     * @return
     */
    boolean isFollowing(String follower, String followee);
}
