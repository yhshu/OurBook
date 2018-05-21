package model;

import java.io.Serializable;

public class User implements Serializable {
    private String username; // 用户名
    private String nickname; // 昵称
    private String password;
    private String description; // 个人简介
    private String avatar; // 头像
    private int clicks; // 书的总点击量
    private int favorites; // 书的总收藏量
    private int followers; // 粉丝数

    public User() {
    }

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    public User(String username, String nickname, String password, String description, String avatar) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.description = description;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}