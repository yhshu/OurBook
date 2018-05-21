package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Chapter implements Serializable {
    private String name; // 章节名称
    private int bookID; // 所属书目的编号
    private int sequence; // 章节序号
    private String content; // 章节文件路径
    private Timestamp lastModified;  // 最后修改时间
    private String editorUsername; // 作者用户名
    private String editorNickname; // 作者昵称
    private String bookName;       // 书名
    private String bookCover;      // 书封面

    public Chapter(String name, int bookID, int sequence, String content) {
        // 插入数据库前构造
        this.name = name;
        this.bookID = bookID;
        this.sequence = sequence;
        this.content = content;
    }

    public Chapter(String name, int bookID, int sequence, String content, String editorUsername) {
        this.name = name;
        this.bookID = bookID;
        this.sequence = sequence;
        this.content = content;
        this.editorUsername = editorUsername;
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

    public String getEditorUsername() {
        return editorUsername;
    }

    public void setEditorUsername(String editorUsername) {
        this.editorUsername = editorUsername;
    }

    public String getEditorNickname() {
        return editorNickname;
    }

    public void setEditorNickname(String editorNickname) {
        this.editorNickname = editorNickname;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCover() {
        return bookCover;
    }

    public void setBookCover(String bookCover) {
        this.bookCover = bookCover;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}
