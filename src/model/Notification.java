package model;

import java.sql.Timestamp;

public class Notification {

    private String username;    // 接收用户名
    private Timestamp time;     // 时间
    private String message;     // 信息
    private boolean read;       // 是否被阅读
    private String header;      // 标题

    public Notification() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}
