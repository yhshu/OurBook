package model;

import java.io.Serializable;

public class Follow {

    private String followee;    //被关注的  自己主页
    private String follower;    //关注的
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public Follow(String followee,String follower){
        this.follower=follower;
        this.followee=followee;
    }
    public Follow(String followee,String follower,String message,String time){
        this.followee=followee;
        this.follower=follower;
        this.message=message;
        this.time=time;
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
