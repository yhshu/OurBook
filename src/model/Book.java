package model;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String description;
    private String ID;
    private String editorInChiefID;

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEditorInChiefID() {
        return editorInChiefID;
    }

    public void setEditorInChiefID(String editorInChiefID) {
        this.editorInChiefID = editorInChiefID;
    }
}
