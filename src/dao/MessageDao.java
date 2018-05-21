package dao;

import model.Message;

public interface MessageDao {

    /**
     * 设置用户收件箱内来自某个用户的私信已读
     *
     * @param owner 收件箱所有者用户名
     * @param from  发送者用户名
     */
    void read(String owner, String from);

    /**
     * 发送私信
     *
     * @param to      接收者用户名
     * @param from    发送者用户名
     * @param content 内容
     */
    boolean add(String to, String from, String content);

    /**
     * 删除某条私信
     *
     * @param ID 私信ID
     */
    boolean delete(int ID);

    /**
     * 清除某个收件箱中与被选中用户的所有私信记录
     *
     * @param owner  收件箱所有者用户名
     * @param target 被选中的用户名
     */
    boolean clear(String owner, String target);

    /**
     * 获取收件箱所有者的所有私信
     *
     * @param owner 用户名
     * @return 私信
     */
    Message[] get(String owner);

    /**
     * 获取收件箱中发送者的数量，仅计未读的私信
     *
     * @param owner 用户名
     * @return 发送者数量
     */
    int getUnreadNumber(String owner);

}
