package model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int ID; // 章节编号
    private String name; // 章节名称
    private int bookID; // 所属书目的编号
    private int sectionNumber; // 章节序号
    private String description;
    private String content; // 章节内容地址

    public Chapter() {
    }

    public Chapter(String name, int bookID, int sectionNumber, String description, String content) {
        // 插入数据库前构造
        this.name = name;
        this.bookID = bookID;
        this.sectionNumber = sectionNumber;
        this.description = description;
        this.content = content;
    }

    public Chapter(int ID, String name, int bookID, int sectionNumber, String description, String content) {
        // 插入数据库后构造
        this.ID = ID;
        this.name = name;
        this.bookID = bookID;
        this.sectionNumber = sectionNumber;
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

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
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
