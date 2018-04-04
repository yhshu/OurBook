package model;

import java.io.Serializable;

public class Article implements Serializable {
    private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
