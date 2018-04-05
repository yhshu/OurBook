package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void register(String nickname, String password) {

    }

    @Override
    public void login(String nickname, String password) {

    }
}
