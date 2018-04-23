package model;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable {
    private int ID;
    private String name;
    private String description;
    private String chiefEditor;
    private String keywords;
    private String cover;
    private Date lastModified;
    private int chapterNum;
    private int clicks;
    private int favorites;

    public Book() {
    }

    public Book(String name, String description, String chiefEditor, String keywords, String cover) {
        // 插入数据库前构造
        this.name = name;
        this.description = description;
        this.chiefEditor = chiefEditor;
        this.keywords = keywords;
        this.cover = cover;
    }

    public Book(int ID, String name, String description, String chiefEditor, String keywords, String cover, int chapterNum) {
        // 查找数据库后构造
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.chiefEditor = chiefEditor;
        this.keywords = keywords;
        this.cover = cover;
        this.chapterNum = chapterNum;
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

    public String getChiefEditor() {
        return chiefEditor;
    }

    public void setChiefEditor(String chiefEditor) {
        this.chiefEditor = chiefEditor;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(int chapterNum) {
        this.chapterNum = chapterNum;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
