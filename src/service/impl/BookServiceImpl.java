package service.impl;

import dao.BookDao;
import dao.impl.BookDaoImpl;
import model.Book;
import service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void add(String name, String description, String chiefEditorID) {
        if (name == null || name.length() == 0) {
            System.out.println("书名为空");
            return;
        }
        bookDao.add(new Book(name, description, chiefEditorID));
    }
}