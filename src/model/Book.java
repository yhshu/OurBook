package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int ID;
    private String name;
    private String description;
    private int chiefEditorID;
    private String keywords;

    public Book() {
    }

    public Book(String name, String description, int chiefEditorID, String keywords) {
        // 插入数据库前构造
        this.name = name;
        this.description = description;
        this.chiefEditorID = chiefEditorID;
        this.keywords = keywords;
    }

    public Book(int ID, String name, String description, int chiefEditorID, String keywords) {
        // 查找数据库后构造
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.chiefEditorID = chiefEditorID;
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getChiefEditorID() {
        return chiefEditorID;
    }

    public void setChiefEditorID(int chiefEditorID) {
        this.chiefEditorID = chiefEditorID;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
