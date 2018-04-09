package service.impl;

import dao.BookDao;
import dao.ChapterDao;
import dao.UserDao;
import dao.impl.BookDaoImpl;
import dao.impl.ChapterDaoImpl;
import dao.impl.UserDaoImpl;
import model.Book;
import model.Chapter;
import service.BookService;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private ChapterDao chapterDao = new ChapterDaoImpl();
    private UserDao userDao = new UserDaoImpl();

    @Override
    public void add(String name, String description, int chiefEditorName, String keywords) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return;
        }
        if (!keywords.contains(chiefEditorName)) keywords += " " + chiefEditorName;
        if (!keywords.contains(name)) keywords += " " + name;
        bookDao.add(new Book(name, description, chiefEditorName, keywords));
    }

    @Override
    public Book find(int ID) {
        return bookDao.findByID(ID);
    }

    @Override
    public Book[] findByKeywords(String keywords) {
        return bookDao.findByKeywords(keywords.split(" "));
    }

    @Override
    public void addChapter(String name, int bookID, int sectionNumber, String description, String content, String keywords) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容URL为空");
            return;
        }
        Book book = bookDao.findByID(bookID);
        if (!keywords.contains(book.getName())) keywords += " " + book.getName();
        if (!keywords.contains(book.getChiefEditorName())) keywords += " " + book.getChiefEditorName();
        if (!keywords.contains(name)) keywords += " " + name;
        chapterDao.add(new Chapter(name, bookID, sectionNumber, description, content, keywords));
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