package service;

import model.Book;
import model.Chapter;
import model.Edit;
import model.User;

public interface BookService {
    /**
     * 添加书籍
     *
     * @param chiefEditor 主编的用户名
     * @param nickname    主编的昵称
     * @param book        书
     * @param rootDir     服务器根目录
     * @return 添加成功 true，添加失败 false
     */
    boolean addBook(String chiefEditor, String nickname, Book book, String rootDir);

    /**
     * 根据书籍ID查找书籍
     *
     * @param ID 书籍ID
     * @return 书籍
     */
    Book find(int ID);

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
     * @param username    用户名
     * @param nickname    用户昵称
     * @param chapter     章节
     * @param rootDir     服务器根目录
     * @param description 添加说明
     * @return 成功 true 失败 false
     */
    boolean addChapter(String username, String nickname, Chapter chapter, String description, String rootDir);

    /**
     * 修改章节
     *
     * @param username    用户名
     * @param nickname    用户昵称
     * @param chapter     章节
     * @param rootDir     服务器根目录
     * @param description 改动说明
     * @return 成功 true 失败 false
     */
    boolean modifyChapter(String username, String nickname, Chapter chapter, String description, String rootDir);

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
     * @param rootDir  服务器根目录
     * @return 删除成功返回true；失败返回false
     */
    boolean delete(int bookID, String username, String rootDir);

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
     * @param rootDir  服务器根目录
     * @return 删除成功返回true；失败返会false
     */
    boolean deleteChapter(int bookID, int sequence, String rootDir);

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