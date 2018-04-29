package service.impl;

import Util.FileUtil;
import dao.BookDao;
import dao.ChapterDao;
import dao.UserDao;
import dao.impl.BookDaoImpl;
import dao.impl.ChapterDaoImpl;
import dao.impl.UserDaoImpl;
import model.Book;
import model.Chapter;
import model.User;
import service.BookService;

import java.io.*;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private ChapterDao chapterDao = new ChapterDaoImpl();
    public static final int chiefEditorAuthority = 2;
    public static final int collaboratorAuthority = 1;
    public static final int noAuthority = 0;

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
    public Book[] findByKeywords(String keywords, String sort, String range) {
        if (sort.equals("click"))
            return bookDao.findByKeywordsClick(keywords.split(" "), range);
        else if (sort.equals("fav"))
            return bookDao.findByKeywordsFav(keywords.split(" "), range);
        else return bookDao.findByKeywords(keywords.split(""));
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
    public boolean addChapter(String username, String name, int bookID, String content, String rootDir, int sequence) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容为空");
            return false;
        }
        // 将章节内容存放在文件中，并将文件路径插入数据库
        try {
            // 写入文件并获取文件名
            String absPath = FileUtil.write(new File(rootDir + "resources/book/" + bookID + "/" +
                    "/" + sequence + ".html"), content);
            if (absPath == null) {
                System.out.println("BookService: 写入章节文件失败");
                return false;
            }
            System.out.println("BookService: 写入章节文件成功");
            // 修改 book 表中的 chapter_num，并将新章节插入 chapter 表
            if (chapterDao.add(username, new Chapter(name, bookID, sequence, absPath.replaceFirst(rootDir, "")))) {
                System.out.println("BookService: 添加编辑信息成功");
                return true;
            } else System.out.println("BookService: 添加编辑信息失败");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookService: 写入章节文件失败");
        }
        return false;
    }

    @Override
    public boolean modifyChapter(String username, String name, int bookID, String content, String rootDir, int sequence) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容URL为空");
            return false;
        }
        // 将章节内容存放在文件中，并将文件路径插入数据库
        try {
            // 写入文件并获取文件名
            String absPath = FileUtil.write(new File(rootDir + "resources/book/" + bookID + "/" +
                    "/" + sequence + ".html"), content);
            if (absPath == null) {
                System.out.println("BookService: 写入章节文件失败");
                return false;
            }
            System.out.println("BookService: 写入章节文件成功");
            // 修改 book 表中的 chapter_num，并将新章节插入 chapter 表
            if (chapterDao.modify(username, new Chapter(name, bookID, sequence, absPath.replaceFirst(rootDir, "")))) {
                System.out.println("BookService: 添加编辑信息成功");
                return true;
            } else System.out.println("BookService: 添加编辑信息失败");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookService: 写入章节文件失败");
        }
        // 修改 book 表中的 chapter_num，并将新章节插入 chapter 表
        return false;
    }


    @Override
    public Chapter[] getChapters(int bookID) {
        return chapterDao.findByBookID(bookID);
    }

    @Override
    public Chapter[] findChapterByKeywords(String keyword) {
        return chapterDao.findByKeywords(keyword.split(" "));
    }

    @Override
    public boolean delete(int bookID, String username) {
        if (!bookDao.findByID(bookID).getChiefEditor().equals(username))
            return false;
        try {
            bookDao.delete(bookID);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookService: 删除书目失败");
        }
        return false;
    }

    @Override
    public Book[] getFavorites(String username) {
        return bookDao.getFavorites(username);
    }

    @Override
    public boolean click(String username, int bookID) {
        return bookDao.click(username, bookID);
    }

    @Override
    public boolean delete_chapter(int bookID, int sequence) {
        return chapterDao.delete(bookID, sequence);
    }

    @Override
    public Book[] recommend() {
        return bookDao.recommend();
    }

    @Override
    public boolean setCollaborators(int bookID, String collaborators, String username) {
        UserDao userDao = new UserDaoImpl();
        String[] col_split = collaborators.split(" ");
        String[] col_check = new String[col_split.length];
        int i = 0;
        for (String collaborator : col_split) {
            if (userDao.exist(collaborator) && !collaborator.equals(username))
                col_check[i++] = collaborator;
        }
        return bookDao.setCollaborators(bookID, col_check);
    }

    @Override
    public User[] getCollaborators(int bookID) {
        return bookDao.getCollaborators(bookID);
    }

    @Override
    public int authority(int bookID, String username) {
        String chiefEditorUsername = bookDao.findByID(bookID).getChiefEditor();
        if (chiefEditorUsername.equals(username))
            return chiefEditorAuthority;
        User[] collaborators = bookDao.getCollaborators(bookID);
        if (collaborators != null) {
            for (User collaborator : collaborators) {
                if (collaborator.getUsername().equals(username)) {
                    return collaboratorAuthority;
                }
            }
        }
        return noAuthority;
    }
}