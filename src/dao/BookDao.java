package dao;

import model.Book;

public interface BookDao {
    /**
     * 添加书籍
     *
     * @param book
     */
    void add(Book book);

    /**
     * 查找书籍
     *
     * @param
     */
    Book find();
}
