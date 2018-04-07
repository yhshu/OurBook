package model;

import java.io.Serializable;

public class User implements Serializable {
    private int ID;
    private String nickname;
    private String password;
    private String description; // 个人简介

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public User(int ID, String nickname, String password, String description) {
        this.ID = ID;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
