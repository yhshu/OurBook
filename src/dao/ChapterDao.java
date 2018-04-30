package dao;


import model.Chapter;
import model.Edit;

public interface ChapterDao {
    /**
     * 添加章节
     * 将新章节插入 chapter 表
     *
     * @param username    用户名
     * @param chapter     新增章节
     * @param description 添加说明
     */
    boolean add(String username, Chapter chapter, String description);

    /**
     * 修改章节信息
     *
     * @param username    用户名
     * @param chapter     新增章节
     * @param description 改动说明
     */
    boolean modify(String username, Chapter chapter, String description);

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
     * 删除某一章节，并返回历史文件在服务器上的位置
     *
     * @param bookID   书籍ID
     * @param sequence 章节序号
     * @return 该章节所有历史文件在服务器上的位置
     */
    String[] delete(int bookID, int sequence);

    /**
     * 查找章节的历史记录
     *
     * @param bookID   书籍ID
     * @param sequence 章节序号
     * @return 历史记录
     */
    Edit[] getHistory(int bookID, int sequence);

    /**
     * 查找某个历史记录
     *
     * @param ID 记录ID
     * @return 历史记录
     */
    Edit getEdit(int ID);
}
