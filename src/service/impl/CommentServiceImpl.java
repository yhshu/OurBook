package service.impl;

import dao.CommentDao;
import dao.impl.CommentDaoImpl;
import model.Comment;
import service.CommentService;

public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao = new CommentDaoImpl();

    @Override
    public Comment[] findByBookID(int bookID) {
        return commentDao.findByBookID(bookID);
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
