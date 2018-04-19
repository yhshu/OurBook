package service.impl;

import dao.FollowDao;
import dao.impl.FollowDaoImpl;
import model.Follow;
import service.FollowService;

/**
 * 关注用户、取消关注用户
 */
public class FollowServiceImpl implements FollowService {

    private FollowDao followDao = new FollowDaoImpl();

    @Override
    public boolean addFollow(String followee, String follower) {
        if (followee == null || follower == null) {
            System.out.println("用户名为空");
            return false;
        }
        try {
            followDao.add(new Follow(followee, follower));
            System.out.println("FollowService: 添加关注成功");
            return true;
        } catch (Exception e) {
            System.out.println("FollowService: 添加关注失败");
        }
        return false;
    }

    @Override
    public boolean delFollow(String username) {
        if (username == null) {
            System.out.println("用户名为空");
            return false;
        }
        try {
            followDao.del(username);
            System.out.println("FollowService: 添加关注成功");
            return true;
        } catch (Exception e) {
            System.out.println("FollowService: 添加关注失败");
        }
        return false;
    }

    @Override
    public String[] findFollowing(String follower) {
        return new String[0];
    }

    @Override
    public String[] findFollowed(String followee) {
        return new String[0];
    }
}

