package model;

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

    public java.sql.Date getTime() {
        return time;
    }

    public void setTime(java.sql.Date time) {
        this.time = time;
    }

    private java.sql.Date time;

    public Follow(String followee, String follower) {
        this.follower = follower;
        this.followee = followee;
    }

    public Follow(String followee, String follower, String message, java.sql.Date time) {
        this.followee = followee;
        this.follower = follower;
        this.message = message;
        this.time = time;
    }

    public Follow(String message, java.sql.Date time) {

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
