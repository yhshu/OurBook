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
    public boolean add(String name, String description, String chiefEditor, String keywords) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空，添加失败");
            return false;
        }
        if (!keywords.contains(chiefEditor)) keywords += " " + chiefEditor; // 添加主编用户名
        if (!keywords.contains(name)) keywords += " " + name; // 添加书名
        try {
            bookDao.add(new Book(name, description, chiefEditor, keywords));
            System.out.println("BookService: 添加书目成功");
            return true;
        } catch (Exception e) {
            System.out.println("BookService: 添加书目失败");
        }
        return false;
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
    public Chapter findChapter(int bookID, int sequence) {
        return chapterDao.findByPri(bookID, sequence);
    }

    @Override
    public void addChapter(String name, int bookID, int sequence, String description, String content, String keywords) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容URL为空");
            return;
        }
        Book book = bookDao.findByID(bookID);
        if (!keywords.contains(book.getName()))
            keywords += " " + book.getName();
        if (!keywords.contains(book.getChiefEditor()))
            keywords += " " + book.getChiefEditor();
        if (!keywords.contains(name))
            keywords += " " + name;
        chapterDao.add(new Chapter(name, bookID, sequence, content, keywords));
    }

    @Override
    public Chapter[] getChapters(int bookID) {
        return chapterDao.findByBookID(bookID);
    }

    @Override
    public Chapter[] findChapterByKeywords(String keywords) {
        return chapterDao.findByKeywords(keywords.split(" "));
    }
}