package service.impl;

import dao.BookDao;
import dao.ChapterDao;
import dao.impl.BookDaoImpl;
import dao.impl.ChapterDaoImpl;
import model.Book;
import model.Chapter;
import service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private ChapterDao chapterDao = new ChapterDaoImpl();

    @Override
    public void add(String name, String description, int chiefEditorID) {
        if (name == null || name.length() == 0) {
            System.out.println("书名为空");
            return;
        }
        bookDao.add(new Book(name, description, chiefEditorID));
    }

    @Override
    public Chapter[] getChapters(int bookID) {
        return chapterDao.findByBookID(bookID);
    }

    @Override
    public Chapter[] findChapterByName(String name) {
        return chapterDao.findByName(name);
    }

    @Override
    public Chapter[] findPrev(int chapterID) {
        return chapterDao.findPrev(chapterID);
    }

    @Override
    public Chapter[] findNext(int chapterID) {
        return chapterDao.findNext(chapterID);
    }
}