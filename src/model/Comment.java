package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    // 存于数据库的成员变量
    private int ID;
    private String username;
    private int bookID;
    private Timestamp datetime;
    private String content;
    // 未存于数据库的成员变量
    private String nickname;
    private String avatar;


    public Comment(String username, int bookID, Timestamp datetime, String content) {
        this.username = username;
        this.bookID = bookID;
        this.datetime = datetime;
        this.content = content;
    }

    public Comment(int ID, String username, int bookID, Timestamp datetime, String content) {
        this.ID = ID;
        this.username = username;
        this.bookID = bookID;
        this.datetime = datetime;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
