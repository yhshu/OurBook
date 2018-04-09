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
     * 根据主键查找章节
     *
     * @param bookID   书籍ID
     * @param sequence 章节在书中的0序号
     * @return 匹配的章节
     */
    Chapter findByPri(int bookID, int sequence);

    /**
     * 根据关键字查找章节
     *
     * @param keywords 章节关键字
     * @return 匹配的所有章节
     */
    Chapter[] findByKeywords(String[] keywords);

    /**
     * 查找书籍中的所有章节
     *
     * @param bookID 书籍ID
     * @return 书中的所有章节
     */
    Chapter[] findByBookID(int bookID);
}
