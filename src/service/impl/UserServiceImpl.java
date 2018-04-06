package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

import javax.servlet.http.Cookie;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void register(String nickname, String password) {
        userDao.add(new User(nickname, password));
    }

    @Override
    public void login(String nickname, String password) {
        User user = userDao.find(nickname);
        if (user != null) { // 查找
            Cookie cookie = new Cookie("nickname", "");
            cookie.setMaxAge(7 * 24 * 60 * 60);
        }
    }
}
