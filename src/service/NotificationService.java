package service;

import model.Notification;

public interface NotificationService {

    /**
     * 获取用户已读通知
     *
     * @param username 用户名
     * @return 已读通知
     */
    Notification[] getRead(String username);

    /**
     * 获取用户未读通知
     *
     * @param username 用户名
     * @return 未读通知
     */
    Notification[] getUnread(String username);

    /**
     * 设置通知已读
     *
     * @param ID 通知ID
     */
    void read(int ID);

    /**
     * 新增通知
     *
     * @param username 接收者用户名
     * @param header   标题
     * @param message  信息
     */
    boolean add(String username, String header, String message);

    /**
     * 删除通知
     *
     * @param ID 通知ID
     */
    void delete(int ID);

    /**
     * 清空已读通知
     *
     * @param username 用户名
     */
    void clearRead(String username);

}
