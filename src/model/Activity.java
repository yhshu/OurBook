package model;

public class Activity {
    String nickname; //用户名
    int action;
    // action = 1 :

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Activity(String n, String c, int t) {
        this.nickname = n;
    }
}
