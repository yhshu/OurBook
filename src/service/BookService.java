package service;

import model.Chapter;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param name          书名
     * @param description   书的简介（可选）
     * @param chiefEditorID 主编用户编号
     */
    void add(String name, String description, int chiefEditorID);

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
     * @param name 章节名
     * @return 名称符合的章节
     */
    Chapter[] findChapterByName(String name);

    /**
     * 查找当前章节的前一章节
     *
     * @param chapterID 当前章节的ID
     * @return 当前章节的前一章节
     */
    Chapter[] findPrev(int chapterID);

    /**
     * 查找当前章节的后一章节
     * @param chapterID 当前章节的ID
     * @return 当前章节的后一章节
     */
    Chapter[] findNext(int chapterID);
}