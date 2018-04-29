package dao;

import model.Notification;

public interface NotificationDao {

    /**
     * 设置通知已读
     *
     * @param ID 通知编号
     */
    void read(int ID);

    /**
     * 通知单个用户
     *
     * @param username 接收者用户名
     * @param header   标题
     * @param content  内容
     */
    boolean notify(String username, String header, String content);

    /**
     * 通知关注者
     *
     * @param followee 被关注者
     * @param header   标题
     * @param content  内容
     */
    boolean notifyFollowers(String followee, String header, String content);

    /**
     * 通知收藏着
     *
     * @param bookID  书ID
     * @param header  标题
     * @param content 内容
     */
    boolean notifySubscribers(int bookID, String header, String content);

    /**
     * 删除通知
     *
     * @param ID 通知ID
     */
    void delete(int ID);

    /**
     * 获取用户通知
     *
     * @param username 用户名
     * @param isRead   是否已读
     * @return 通知
     */
    Notification[] getByUsername(String username, boolean isRead);

    /**
     * 清空已读通知
     *
     * @param username 用户名
     */
    void clearRead(String username);

}
