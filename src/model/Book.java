package model;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String description;
    private String ID;
    private String chiefEditorID;

    public Book() {
    }

    public Book(String name, String description, String chiefEditorID) {
        // 插入数据库前构造
        this.name = name;
        this.description = description;
        this.chiefEditorID = chiefEditorID;
    }

    public Book(String name, String description, String ID, String chiefEditorID) {
        // 查找数据库后构造
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.chiefEditorID = chiefEditorID;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getChiefEditorID() {
        return chiefEditorID;
    }

    public void setChiefEditorID(String chiefEditorID) {
        this.chiefEditorID = chiefEditorID;
    }
}
