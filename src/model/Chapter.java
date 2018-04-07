package model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int ID; // 章节编号
    private String name;
    private int bookID; // 所属书目的编号
    private String description;
    private String content;

    public Chapter() {
    }

    public Chapter(int ID, String name, int bookID, String description, String content) {
        this.ID = ID;
        this.name = name;
        this.bookID = bookID;
        this.description = description;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
