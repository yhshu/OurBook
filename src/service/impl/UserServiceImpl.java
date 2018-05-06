package service.impl;

import dao.FollowDao;
import dao.UserDao;
import dao.impl.FollowDaoImpl;
import dao.impl.UserDaoImpl;
import model.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();
    private FollowDao followDao = new FollowDaoImpl();

    @Override
    public User[] search(String keyword) {
        return userDao.search(keyword);
    }

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
    public User login(String username, String password) {
        User user = userDao.find(username);
        if (user != null && user.getPassword().equals(password)) {
            // 用户名与密码匹配
            System.out.println("UserService: 【用户登录】用户名与密码匹配");
            return user;
        }
        // 用户名或密码错误
        System.out.println("UserService: 【用户登录】用户名或密码错误");
        System.out.println("login as " + username + " " + password);
        //System.out.println("Database:" + user.getUsername() + " " + user.getPassword());
        return null;
    }

    @Override
    public User[] getFollowers(String username) {
        return followDao.findFollowers(username);
    }

    @Override
    public User[] getFollowees(String username) {
        return followDao.findFollowees(username);
    }

    @Override
    public boolean modify(String username, String nickname, String description, String avatar) {
        return userDao.modify(username, nickname, description, avatar);
    }

    @Override
    public boolean modifyPassword(String username, String newPassword) {
        return userDao.modifyPassword(username, newPassword);
    }

    @Override
    public boolean addFavorite(String username, int bookID) {
        return userDao.addFavorite(username, bookID);
    }

    @Override
    public boolean cancelFavorite(String username, int bookID) {
        return userDao.cancelFavorite(username, bookID);
    }

    @Override
    public boolean isFavorite(String username, int bookID) {
        return userDao.isFavorite(username, bookID);
    }

    @Override
    public User[] recommend() {
        return userDao.recommend();
    }
}