package dao;

import model.Book;

public interface BookDao {
    /**
     * 根据编码查找书
     *
     * @param ID 编码
     * @return 符合编码的书
     */
    Book findByID(int ID);

    /**
     * 根据书名查找书
     *
     * @param name 书名
     * @return 符合书名的书
     */
    Book[] findByName(String name);

    /**
     * 根据关键字查找书
     *
     * @param keywords 关键字
     * @return 符合关键字的书
     */
    Book[] findByKeywords(String[] keywords);

    /**
     * 根据作者ID查找书
     *
     * @param chiefEditor 作者用户名
     * @return 符合作者ID的全部书
     */
    Book[] findByUserID(String chiefEditor);

    /**
     * 添加书目
     *
     * @param book 新增书目
     */
    void add(Book book);

    /**
     * 返回最大的 book 表中最大的 ID
     * 用于在添加新书之后重定向
     *
     * @return book 表中最大的 ID
     */
    int maxID();
}
