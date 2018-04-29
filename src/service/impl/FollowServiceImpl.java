package service.impl;

import dao.FollowDao;
import dao.impl.FollowDaoImpl;
import service.FollowService;

/**
 * 关注用户、取消关注用户
 */
public class FollowServiceImpl implements FollowService {

    private FollowDao followDao = new FollowDaoImpl();

    @Override
    public boolean follow(String followee, String follower) {
        if (followee == null || follower == null) {
            System.out.println("follow 为空");
            return false;
        }
        try {
            followDao.add(follower, followee);
            System.out.println("FollowService: 添加关注成功");
            return true;
        } catch (Exception e) {
            System.out.println("FollowService: 添加关注失败");
        }
        return false;
    }

    @Override
    public boolean unFollow(String followee, String follower) {
        if (followee.equals("") || follower.equals("")) {
            System.out.println("follow为空");
            return false;
        }
        try {
            followDao.del(follower, followee);
            System.out.println("FollowService: 取消关注成功");
            return true;
        } catch (Exception e) {
            System.out.println("FollowService: 取消关注失败");
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

    @Override
    public boolean isFollowing(String follower, String followee) {
        return followDao.isFollowing(follower, followee);
    }
}

