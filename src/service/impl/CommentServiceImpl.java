package service.impl;

import dao.CommentDao;
import dao.UserDao;
import dao.impl.CommentDaoImpl;
import dao.impl.UserDaoImpl;
import model.Comment;
import model.User;
import service.CommentService;

public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao = new CommentDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public Comment[] findByBookID(int bookID) {
        Comment[] comments = commentDao.findByBookID(bookID);
        for (Comment comment : comments) {
            User user = userDao.find(comment.getUsername());
            comment.setAvatar(user.getAvatar());
            comment.setNickname(user.getNickname());
        }
        return comments;
    }

    @Override
    public Comment find(int ID) {
        return commentDao.find(ID);
    }

    @Override
    public boolean add(String username, int bookID, String content) {
        return commentDao.add(username, bookID, content);
    }

    @Override
    public boolean delete(int ID) {
        return commentDao.delete(ID);
    }
}
