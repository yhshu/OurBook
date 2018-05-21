package service;

import model.Message;
import model.User;

import java.util.Map;

public interface MessageService {

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
    boolean send(String to, String from, String content);

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
     * @return 按私信发送/接收者存储的哈希表
     */
    Map<User, Message[]> get(String owner);

    /**
     * 获取收件箱未读私信的数量
     *
     * @param owner 用户名
     * @return 未读私信的数量
     */
    int getUnreadNumber(String owner);

}
