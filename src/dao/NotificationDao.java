package dao;

import model.Notification;

import java.sql.Timestamp;

public interface NotificationDao {

    /**
     * 设置通知已读
     *
     * @param ID 通知编号
     */
    void read(int ID);

    /**
     * 新增通知
     *
     * @param username 接收者用户名
     * @param header   标题
     * @param content  内容
     */
    boolean add(String username, String header, String content);

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


}
