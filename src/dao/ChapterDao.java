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
     */
    Chapter findByID(String ID);
}
