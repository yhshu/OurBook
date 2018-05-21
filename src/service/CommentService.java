package service;

import model.Comment;

public interface CommentService {
    /**
     * 根据书ID查找对应的评论
     *
     * @param bookID 书籍
     */
    Comment[] findByBookID(int bookID);

    /**
     * 根据ID查找评论
     *
     * @param ID 评论编号
     */
    Comment find(int ID);

    /**
     * 添加评论
     *
     * @param username 评论者
     * @param bookID   书籍
     * @param content  评论内容
     * @return 添加成功true，失败false
     */
    boolean add(String username, int bookID, String content);

    /**
     * 删除评论
     *
     * @param ID 评论编号
     * @return 删除成功true，失败false
     */
    boolean delete(int ID);
}
