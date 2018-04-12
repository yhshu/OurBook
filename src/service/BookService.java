package service;

import model.Book;
import model.Chapter;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param name        书名
     * @param description 书的简介（可选）
     * @param chiefEditor 主编的用户名
     * @param keywords    书的关键字
     * @return 添加成功 true，添加失败 false
     */
    boolean add(String name, String description, String chiefEditor, String keywords, String cover);

    /**
     * 根据ID查找书籍
     *
     * @param ID 书籍ID
     * @return 书籍
     */
    Book find(int ID);

    /**
     * 根据关键字查找书籍
     *
     * @param keywords 书籍关键字
     * @return 含有关键字的所有书籍
     */
    Book[] findByKeywords(String keywords);

    /**
     * 查找章节
     *
     * @param bookID   书籍序号
     * @param sequence 章节号
     * @return 章节
     */
    Chapter findChapter(int bookID, int sequence);

    /**
     * @param name          章节名
     * @param bookID        书名
     * @param sectionNumber 章节序号
     * @param description   简介
     * @param content       章节内容URL
     */
    void addChapter(String name, int bookID, int sectionNumber, String description, String content);

    /**
     * 查找书籍中的全部章节
     *
     * @param bookID 书籍编号
     * @return 书籍中的全部章节
     */
    Chapter[] getChapters(int bookID);

    /**
     * 根据章节名查找章节
     *
     * @param keywords 关键字
     * @return 章节
     */
    Chapter[] findChapterByKeywords(String keywords);
}