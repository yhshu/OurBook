package model;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class Comment {
    private String username;
    private int bookID;
    private Data data;
    private String comments;

    public Comment(String username, int bookID, Data data, String comments) {
        this.username = username;
        this.bookID = bookID;
        this.data = data;
        this.comments = comments;
    }

    public String getUsername() {
        return username;
    }

    public Data getData() {
        return data;
    }

    public String getComments() {
        return comments;
    }

    public int getBookID() {
        return bookID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
