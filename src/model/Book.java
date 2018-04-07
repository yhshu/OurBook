package model;

import java.io.Serializable;

public class Book implements Serializable {
    private int ID;
    private String name;
    private String description;
    private int chiefEditorID;

    public Book() {
    }

    public Book(String name, String description, int chiefEditorID) {
        // 插入数据库前构造
        this.name = name;
        this.description = description;
        this.chiefEditorID = chiefEditorID;
    }

    public Book(int ID, String name, String description, int chiefEditorID) {
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
}
