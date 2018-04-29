package service.impl;

import Util.FileUtil;
import dao.BookDao;
import dao.ChapterDao;
import dao.NotificationDao;
import dao.UserDao;
import dao.impl.BookDaoImpl;
import dao.impl.ChapterDaoImpl;
import dao.impl.NotificationDaoImpl;
import dao.impl.UserDaoImpl;
import model.Book;
import model.Chapter;
import model.User;
import service.BookService;

import java.io.File;

public class BookServiceImpl implements BookService {
    private BookDao bookDao = new BookDaoImpl();
    private ChapterDao chapterDao = new ChapterDaoImpl();
    private NotificationDao notificationDao = new NotificationDaoImpl();
    private static final int chiefEditorAuthority = 2;
    private static final int collaboratorAuthority = 1;
    private static final int noAuthority = 0;

    @Override
    public boolean addBook(String name, String description, String chiefEditor, String nickname, String keywords, String cover, String rootDir) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空，添加失败");
            return false;
        }
        if (!keywords.contains(chiefEditor)) keywords += " " + chiefEditor; // 添加主编用户名
        if (!keywords.contains(name)) keywords += " " + name; // 添加书名
        int ID = bookDao.add(new Book(name, description, chiefEditor, keywords, cover));
        if ((ID) == -1) {
            System.out.println("BookService: 添加书目失败");
            return false;
        }
        if (!new File(rootDir + "resources/book/" + ID).mkdir()) {
            System.out.println("BookService: 创建文件夹失败");
            return false;
        }
        if (notificationDao.notifyFollowers(chiefEditor, nickname + "创建了一本新书",
                "你关注的<a href='home?user=" + chiefEditor + "'>" + nickname +
                        "</a>刚刚创建了<a href='book?id=" + ID + "'>" + name + "</a>，快来看看吧")) {
            System.out.println("BookService: 通知关注者成功");
            return true;
        } else
            System.out.println("BookService: 通知关注者失败");
        return false;
    }

    @Override
    public Book find(int ID) {
        return bookDao.findByID(ID);
    }

    @Override
    public Book[] findByKeywords(String keywords, String sort, String range) {
        switch (sort) {
            case "click":
                return bookDao.findByKeywordsClick(keywords.split(" "), range);
            case "fav":
                return bookDao.findByKeywordsFav(keywords.split(" "), range);
            default:
                return bookDao.findByKeywords(keywords.split(""));
        }
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
    public boolean addChapter(String username, String nickname, String name, int bookID, String bookName, String content, String rootDir, int sequence) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容为空");
            return false;
        }
        // 将章节内容存放在文件中
        String absPath = writeChapterToFile(rootDir, bookID, sequence, content);
        if (absPath == null) return false;

        if (chapterDao.add(username, new Chapter(name, bookID, sequence, absPath.replaceFirst(rootDir, "")))) {
            System.out.println("BookService: 添加编辑信息成功");
        } else {
            System.out.println("BookService: 添加编辑信息失败");
            return false;
        }

        if (notificationDao.notifySubscribers(bookID, bookName + "已更新",
                "<a href='home?user=" + username + "'>" + nickname +
                        "</a>刚刚更新了<a href='book?id=" + bookID + "'>" + bookName + "</a>，快来看看吧")) {
            System.out.println("BookService: 通知收藏者成功");
        } else {
            System.out.println("BookService: 通知收藏者失败");
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyChapter(String username, String nickname, String name, int bookID, String bookName, String content, String rootDir, int sequence) {
        if (name == null || name.length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (content == null || content.length() == 0) {
            System.out.println("BookService: 内容URL为空");
            return false;
        }
        // 将章节内容存放在文件中
        String absPath = writeChapterToFile(rootDir, bookID, sequence, content);
        if (absPath == null) return false;

        // 添加编辑信息
        if (chapterDao.modify(username, new Chapter(name, bookID, sequence, absPath.replaceFirst(rootDir, "")))) {
            System.out.println("BookService: 添加编辑信息成功");
        } else {
            System.out.println("BookService: 添加编辑信息失败");
            return false;
        }
        // 通知收藏者
        if (notificationDao.notifySubscribers(bookID, bookName + "已更新",
                "<a href='home?user=" + username + "'>" + nickname +
                        "</a>刚刚更新了<a href='book?id=" + bookID + "'>" + bookName + "</a>，快来看看吧")) {
            System.out.println("BookService: 通知收藏者成功");
        } else {
            System.out.println("BookService: 通知收藏者失败");
            return false;
        }
        return true;
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
    public boolean delete(int bookID, String username, String rootDir) {
        if (!bookDao.findByID(bookID).getChiefEditor().equals(username))
            return false;
        String cover = bookDao.delete(bookID);
        if (cover == null) {
            System.out.println("BookService: 删除书籍失败");
            return false;
        }
        return FileUtil.delete(rootDir + "resources/book/" + bookID, "ChapterServlet: 删除章节文件失败")
                && FileUtil.delete(rootDir + cover, "ChapterServlet: 删除封面失败");
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
    public boolean deleteChapter(int bookID, int sequence, String rootDir) {
        String[] paths = chapterDao.delete(bookID, sequence);
        if (paths == null) {
            System.out.println("ChapterServlet: 删除章节文件失败");
            return false;
        }
        // 删除章节文件
        for (String path : paths) {
            FileUtil.delete(rootDir + path, "ChapterServlet: 删除章节文件失败");
        }
        return true;
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

    private static String writeChapterToFile(String rootDir, int bookID, int sequence, String content) {
        String absPath = null;
        try {
            // 写入文件并获取文件名
            absPath = FileUtil.write(new File(rootDir + "resources/book/" + bookID + "/" +
                    "/" + sequence + ".html"), content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (absPath == null) {
            System.out.println("BookService: 写入章节文件失败");
        } else {
            System.out.println("BookService: 写入章节文件成功");
        }
        return absPath;
    }
}