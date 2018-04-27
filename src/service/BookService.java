package service;

import model.Book;
import model.Chapter;
import model.User;

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
    boolean addBook(String name, String description, String chiefEditor, String keywords, String cover);

    /**
     * 根据书籍ID查找书籍
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
     * 根据关键字查找书籍，并按类型和时间范围排序
     *
     * @param keywords 关键字
     * @param sort     排序类型
     * @param range    排序时间范围
     * @return 经过排序的书籍
     */
    Book[] findByKeywords(String keywords, String sort, String range);

    /**
     * 根据作者查找书籍
     *
     * @param username 作者用户名
     * @return 作者的全部书籍
     */
    Book[] findByEditor(String username);

    /**
     * 查找章节
     *
     * @param bookID   书籍序号
     * @param sequence 章节号
     * @return 章节
     */
    Chapter findChapter(int bookID, int sequence);

    /**
     * 查找书籍中的全部章节
     *
     * @param bookID 书籍编号
     * @return 书籍中的全部章节
     */
    Chapter[] getChapters(int bookID);

    /**
     * 添加章节
     *
     * @param username 用户名
     * @param name     章节名
     * @param bookID   书的编号
     * @param content  章节内容，将以文件形式存储于 resources/book
     * @param path     由 Servlet 传递的文件路径
     * @param sequence 章节号
     * @return 添加到数据库的 sequence
     */
    boolean addChapter(String username, String name, int bookID, String content, String path, int sequence);

    /**
     * 修改章节
     *
     * @param username 用户名
     * @param name     章节名
     * @param bookID   书的编号
     * @param content  章节内容，将以文件形式存储于 resources/book
     * @param path     由 Servlet 传递的文件路径
     * @param sequence 章节号
     * @return 成功 true 失败 false
     */
    boolean modifyChapter(String username, String name, int bookID, String content, String path, int sequence);

    /**
     * 通过关键字查找章节
     *
     * @param keyword 关键字
     * @return 标题中含有关键字的章节
     */
    Chapter[] findChapterByKeywords(String keyword);

    /**
     * 删除一本书及其所有章节
     *
     * @param bookID   书目编号
     * @param username 发起删除请求的用户名
     * @return 删除成功返回true；失败返回false
     */
    boolean delete(int bookID, String username);

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
     * 删除指定章节
     *
     * @param bookID   书目编号
     * @param sequence 章节序号
     * @return 删除成功返回true；失败返会false
     */
    boolean delete_chapter(int bookID, int sequence);

    /**
     * 首页推荐书目
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
     * @param username      添加协作者的主编（它不能是协作者）
     * @return 添加成功返回true；失败返回false
     */
    boolean setCollaborators(int bookID, String collaborators, String username);

    /**
     * 查询协作者
     * 注意，主编不在此表
     *
     * @param bookID 书籍
     * @return 协作者
     */
    User[] getCollaborators(int bookID);

    /**
     * 查询用户对此书的权限
     *
     * @param bookID   书籍
     * @param username 用户
     * @return 无权限0，协作者1，主编2
     */
    int authority(int bookID, String username);
}