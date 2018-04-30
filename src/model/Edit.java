package model;

import java.sql.Timestamp;

public class Edit {
    private int ID; // 序号
    private String name; // 章节名称
    private int bookID; // 所属书目的编号
    private int sequence; // 章节序号
    private int chapterID;
    private String content; // 章节文件路径
    private Timestamp modifiedTime;  // 最后修改时间
    private String editorUsername; // 作者用户名
    private String editorNickname; // 作者昵称
    private String description; // 改动说明

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getChapterID() {
        return chapterID;
    }

    public void setChapterID(int chapterID) {
        this.chapterID = chapterID;
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

    public Timestamp getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Timestamp modifiedTime) {
        this.modifiedTime = modifiedTime;
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

    public Edit(String name, int chapterID, String content, String description, Timestamp modifiedTime, String editorUsername) {
        this.name = name;
        this.chapterID = chapterID;
        this.content = content;
        this.description = description;
        this.modifiedTime = modifiedTime;
        this.editorUsername = editorUsername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
