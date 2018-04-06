package model;

import java.io.Serializable;

public class User implements Serializable {
    String ID;
    String nickname;
    String password;
    String personalDes; //个人简介

    public User(String username, String password) {
        this.nickname = username;
        this.password = password;
    }

    public User(String ID, String nickname, String password,String personalDes) {
        this.ID = ID;
        this.nickname = nickname;
        this.password = password;
        this.personalDes = personalDes;
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

    public String getPersonalDes() { return personalDes; }

    public void setPersonalDes(String personalDes) { this.personalDes = personalDes; }
}
