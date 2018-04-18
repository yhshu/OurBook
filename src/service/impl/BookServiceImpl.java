package service.impl;

import dao.BookDao;
import dao.ChapterDao;
import dao.impl.BookDaoImpl;
import dao.impl.ChapterDaoImpl;
import model.Book;
import model.Chapter;
import service.BookService;

import java.io.*;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private ChapterDao chapterDao = new ChapterDaoImpl();

    @Override
    public boolean addBook(String name, String description, String chiefEditor, String keywords, String cover) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空，添加失败");
            return false;
        }
        if (!keywords.contains(chiefEditor)) keywords += " " + chiefEditor; // 添加主编用户名
        if (!keywords.contains(name)) keywords += " " + name; // 添加书名
        try {
            bookDao.add(new Book(name, description, chiefEditor, keywords, cover));
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
    public Book[] findByEditor(String username) {
        return bookDao.findByUserID(username);
    }

    @Override
    public Chapter findChapter(int bookID, int sequence) {
        return chapterDao.findByPri(bookID, sequence);
    }

    @Override
    public boolean addChapter(String name, int bookID, int sequence, String content) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容URL为空");
            return false;
        }
        // TODO 将章节内容存放在文件中，并将文件路径插入数据库
        try {
            File file = new File("web/resources/book"); // TODO
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            chapterDao.add(new Chapter(name, bookID, sequence, content));
            return true;
        } catch (Exception e) {
            System.out.println("BookService: 添加章节失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Chapter[] getChapters(int bookID) {
        return chapterDao.findByBookID(bookID);
    }
}