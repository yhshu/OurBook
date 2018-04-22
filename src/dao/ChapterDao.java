package dao;


import model.Chapter;

public interface ChapterDao {
    /**
     * 添加章节
     * 修改 book 表中的 chapter_num，并将新章节插入 chapter 表
     *
     * @param chapter 新增章节
     */
    boolean add(Chapter chapter);

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

    /**
     * 删除某一章节
     *
     * @param bookID   书籍ID
     * @param sequence 章节序号
     * @return 删除成功返回true；失败返回false
     */
    boolean delete(int bookID, int sequence);
}
