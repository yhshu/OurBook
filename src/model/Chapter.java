package model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String ID; // 章节编号
    private String name;
    private String bookID; // 所属书目的编号
    private String content;

    public Chapter() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
