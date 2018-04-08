package dao;


import model.Chapter;

public interface ChapterDao {
    /**
     * 添加章节
     *
     * @param chapter 新增章节
     */
    void add(Chapter chapter);

    /**
     * 根据编码查找章节
     *
     * @param ID 章节ID
     * @return 匹配的所有章节
     */
    Chapter findByID(int ID);

    /**
     * 根据名称查找章节
     *
     * @param name 章节名称
     * @return 匹配的所有章节
     */
    Chapter[] findByName(String name);

    /**
     * 根据关键字查找章节
     *
     * @param keywords 关键字
     * @return 匹配的章节
     */
    Chapter[] findByKeywords(String[] keywords);

    /**
     * 查找书籍中的所有章节
     *
     * @param bookID 书籍ID
     * @return 书中的所有章节
     */
    Chapter[] findByBookID(int bookID);

    /**
     * 根据书籍ID和章节序号查找所有匹配的章节
     *
     * @param bookID 书籍ID
     * @param sectionNumber 章节序号
     * @return 所有匹配的章节
     */
    Chapter[] find(int bookID, int sectionNumber);

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
