package model;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String description;
    private String ID;
    private String editorInChiefID;

<<<<<<< HEAD
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
=======
    Book(String name, String description, String editorInChiefID) {
>>>>>>> 27da401c0f9d061f2ae7ea33d7676356580fa2ce
    }
}
