package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User find(String username) {
        return userDao.find(username);
    }

    @Override
    public boolean register(String userName, String nickname, String password) {
        User user = userDao.find(userName);
        if (user != null) { // 用户名已被注册
            return false;
        } else
            userDao.add(new User(userName, nickname, password));
        return true;
    }

    @Override
    public boolean login(String username, String password) {
        User user = userDao.find(username);
        if (user != null && user.getPassword().equals(password)) {
            // 用户名与密码匹配
            System.out.println("UserService: 【用户登录】用户名与密码匹配");
            return true;
        }
        // 用户名或密码错误
        System.out.println("UserService: 【用户登录】用户名或密码错误");
        System.out.println("login as " + username + " " + password);
        System.out.println("Database:" + user.getUsername() + " " + user.getPassword());
        return false;
    }

    @Override
    public String getNickname(String username) {
        return userDao.getNickname(username);
    }
}