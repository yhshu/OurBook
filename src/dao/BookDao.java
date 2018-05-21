package dao;

import model.Book;
import model.User;

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
     * 根据关键字查找书籍，并按时间范围内的点击量排序
     *
     * @param keywords 关键字
     * @param range    时间范围
     * @return 经过排序的书籍
     */
    Book[] findByKeywordsClick(String[] keywords, String range);

    /**
     * 根据关键字查找书籍，并按时间范围内的收藏量排序
     *
     * @param keywords 关键字
     * @param range    时间范围
     * @return 经过排序的书籍
     */
    Book[] findByKeywordsFav(String[] keywords, String range);

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
     * @return 书的ID
     */
    int add(Book book);

    /**
     * 返回最大的 book 表中最大的 ID
     * 用于在添加新书之后重定向
     *
     * @return book 表中最大的 ID
     */
    int maxID();

    /**
     * 删除一本书及其所有章节，并返回封面地址
     *
     * @param bookID 书目编号
     * @return 书籍封面地址
     */
    String delete(int bookID);

    /**
     * 获取用户收藏的书
     *
     * @param username 用户名
     * @return 用户收藏的书
     */
    Book[] getFavorites(String username);

    /**
     * 记录点击
     *
     * @param username 用户名
     * @param bookID   书编号
     */
    boolean click(String username, int bookID);

    /**
     * 推荐点击量最多的书
     *
     * @return 推荐书目
     */
    Book[] recommend();

    /**
     * 设置协作者
     * 注意，主编不在此表
     *
     * @param bookID        书籍
     * @param collaborators 协作者
     * @return 添加成功返回true；失败返回false
     */
    boolean setCollaborators(int bookID, String[] collaborators);

    /**
     * 查询协作者
     * 注意，主编不在此表
     *
     * @param bookID 书籍
     * @return 协作者
     */
    User[] getCollaborators(int bookID);
}
