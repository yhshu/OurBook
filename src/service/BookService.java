package service;

import model.Book;
import model.Chapter;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param name            书名
     * @param description     书的简介（可选）
     * @param chiefEditorName 主编用户名
     * @param keywords        书的关键字
     */
    void add(String name, String description, String chiefEditorName,String keywords);

    /**
     * 根据关键字查找书籍
     *
     * @param keywords 书籍关键字
     * @return
     */
    Book[] findByKeywords(String keywords);

    /**
     *
     * @param name          章节名
     * @param bookID        书名
     * @param sectionNumber 章节序号
     * @param description   简介
     * @param content       章节内容URL
     * @param keywords      章节关键词
     */
     void addChapter(String name, int bookID, int sectionNumber, String description, String content, String keywords);

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

    /**
     * 查找当前章节的前一章节
     *
     * @param chapter 当前章节
     * @return 当前章节的前一章节
     */
    Chapter findPrev(Chapter chapter);

    /**
     * 查找当前章节的后一章节
     * @param chapter 当前章节
     * @return 当前章节的后一章节
     */
    Chapter findNext(Chapter chapter);
}