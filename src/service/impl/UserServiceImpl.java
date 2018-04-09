package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(String nickname, String password) {
        User user = userDao.find(nickname);
        if (user != null) { // 用户名已被注册
            return false;
        } else
            userDao.add(new User(nickname, password));
        return true;
    }

    @Override
    public boolean login(String nickname, String password) {
        User user = userDao.find(nickname);
        if (user != null && user.getPassword().equals(password)) { // 用户名与密码匹配
            System.out.println("UserService: 【用户登录】用户名与密码匹配");
            return true;
        } else { // 用户名或密码错误
            System.out.println("UserService: 【用户登录】用户名或密码错误");
        }
        return false;
    }
}
