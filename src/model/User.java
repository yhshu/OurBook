package model;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String nickname;
    private String password;
    private String description; // 个人简介

    public User(String username, String nickname, String password, String description) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
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