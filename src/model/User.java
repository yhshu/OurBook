package model;

import java.io.Serializable;

public class User implements Serializable {
    String ID;
    String nickname;
    String password;


    public User(String username, String password) {
        this.nickname = username;
        this.password = password;
    }

    public User(String ID, String nickname, String password) {
        this.ID = ID;
        this.nickname = nickname;
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
