package model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name; // 章节名称
    private int bookID; // 所属书目的编号
    private int sequence; // 章节序号
    private String content; // 章节内容地址

    public Chapter(String name, int bookID, int sequence, String content) {
        // 插入数据库前构造
        this.name = name;
        this.bookID = bookID;
        this.sequence = sequence;
        this.content = content;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
