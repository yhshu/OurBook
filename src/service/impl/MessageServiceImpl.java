package service.impl;

import dao.MessageDao;
import dao.impl.MessageDaoImpl;
import model.Message;
import model.User;
import service.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageServiceImpl implements MessageService {
    private MessageDao messageDao = new MessageDaoImpl();

    @Override
    public void read(String owner, String from) {
        messageDao.read(owner, from);
    }

    @Override
    public boolean send(String to, String from, String content) {
        return messageDao.add(to, from, content);
    }

    @Override
    public boolean delete(int ID) {
        return messageDao.delete(ID);
    }

    @Override
    public boolean clear(String owner, String target) {
        return messageDao.clear(owner, target);
    }

    @Override
    public Map<User, Message[]> get(String owner) {
        Message[] messages = messageDao.get(owner);
        Map<String, ArrayList<Message>> m = new HashMap<>();
        Map<String, User> u = new HashMap<>();
        Map<User, Message[]> result = new HashMap<>();
        for (Message message : messages) {
            // 除收件箱所有者外另一个用户的用户名
            String username = message.getTo().equals(owner) ? message.getFrom() : message.getTo();
            if (!m.containsKey(username)) {
                String nickname = message.getTo().equals(owner) ? message.getFromNickname() : message.getToNickname();
                String avatar = message.getTo().equals(owner) ? message.getFromAvatar() : message.getToAvatar();
                User user = new User();
                user.setUsername(username);
                user.setNickname(nickname);
                user.setAvatar(avatar);
                u.put(username, user);
                m.put(username, new ArrayList<>());
            }
            m.get(username).add(message);
        }
        for (String username : m.keySet()) {
            result.put(u.get(username), m.get(username).toArray(new Message[0]));
        }
        return result;
    }

    @Override
    public int getUnreadNumber(String owner) {
        return messageDao.getUnreadNumber(owner);
    }


}
