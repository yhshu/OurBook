package dao;

import model.Book;

public interface BookDao {
    /**
     * 根据编码查找书
     * @param ID 编码
     * @return 符合编码的书
     */
    Book find(String ID);

    /**
     * 根据书名查找书
     * @param name 书名
     * @return 符合书名的全部书
     */
    Book[] findByName(String name);

    /**
     * 添加书
     * @param book 书
     */
    void add(Book book);
}
