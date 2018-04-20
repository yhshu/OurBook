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
    public User find(String username) {
        return userDao.find(username);
    }

    public boolean findExist(String username){
        if (userDao.find(username).getUsername().isEmpty()){
            return true;  //用户名不存在
        }else
            return false; //用户名存在
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

    @Override
    public User[] getFollowers(String username) {
        String[] usernames = followDao.findFollowers(username);
        User[] followers = new User[usernames.length];
        for (int i = 0; i < followers.length; i++)
            followers[i] = userDao.find(usernames[i]);
        return followers;
    }

    @Override
    public User[] getFollowees(String username) {
        String[] usernames = followDao.findFollowers(username);
        User[] followees = new User[usernames.length];
        for (int i = 0; i < followees.length; i++)
            followees[i] = userDao.find(usernames[i]);
        return followees;
    }
}