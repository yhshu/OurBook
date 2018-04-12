package model;

import java.io.Serializable;

public class Follow {

    private String followee;    //被关注的  自己主页
    private String follower;    //关注的

    public Follow(String follower,String followee){
        this.follower=follower;
        this.followee=followee;
    }

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    public String getFollower() {

        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
