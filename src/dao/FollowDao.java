package dao;

import model.Follow;
import model.User;

public interface FollowDao {
    /**
     * 添加新关注的用户
     *
     * @param username 新关注用户
     */
    void add(Follow username);

    /**
     * 取消关注
     *
     * @param follow 被取消的用户
     */
    void del(Follow follow);

    /**
     * 查找某用户的关注列表
     *
     * @param follower 用户编号
     * @return 用户正关注的其他用户的用户名
     */
    String[] findFollowees(String follower);

    /**
     * 查找某用户的被关注列表
     *
     * @param followee 用户编号
     * @return 用户被其他人关注的列表
     */
    String[] findFollowers(String followee);

    /**
     * 添加用户
     *
     * @param follow 新关注用户
     */
    void addDialog(Follow follow);

    /**
     * 查找对话content
     *
     * @param follow 找到相应的对话
     * @return 用户正关注的其他用户的用户名
     */
    String[] findDialogMessage(Follow follow);

    /**
     * 检查是否关注
     *
     * @param follower  关注者
     * @param followee  被关注者
     * @return
     */
    boolean isFollowing(String follower, String followee);
}
