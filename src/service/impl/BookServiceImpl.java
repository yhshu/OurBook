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
    public void add(String name, String description, int chiefEditorID, String keywords) {
        if (name == null || name.length() == 0) {
            System.out.println("书名为空");
            return;
        }
        bookDao.add(new Book(name, description, chiefEditorID, keywords));
    }

    @Override
    public Book[] findByKeywords(String keywords) {
        return bookDao.findByKeywords(keywords.split(" "));
    }

    @Override
    public Chapter[] getChapters(int bookID) {
        return chapterDao.findByBookID(bookID);
    }

    @Override
    public Chapter[] findChapterByKeywords(String keywords) {
        return chapterDao.findByKeywords(keywords.split(" "));
    }

    @Override
    public Chapter findPrev(Chapter chapter) {
        return chapterDao.findPrev(chapter.getBookID(), chapter.getSectionNumber());
    }

    @Override
    public Chapter findNext(Chapter chapter) {
        return chapterDao.findNext(chapter.getBookID(), chapter.getSectionNumber());
    }
}