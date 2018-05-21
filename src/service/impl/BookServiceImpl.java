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
import model.Edit;
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
    public boolean addBook(String chiefEditor, String nickname, Book book, String rootDir) {
        if (book.getName() == null || book.getName().length() == 0) {
            System.out.println("BookService: 书名为空，添加失败");
            return false;
        }
        String keywords = book.getKeywords();
        if (!keywords.contains(chiefEditor)) keywords += " " + chiefEditor; // 添加主编用户名
        if (!keywords.contains(nickname)) keywords += " " + nickname; // 添加主编昵称
        if (!keywords.contains(book.getName())) keywords += " " + book.getName(); // 添加书名
        book.setKeywords(keywords);

        int ID = bookDao.add(book);
        if ((ID) == -1) {
            System.out.println("BookService: 添加书目失败");
            return false;
        }
        if (!new File(rootDir + "resources/book/" + ID).mkdir()) {
            System.out.println("BookService: 创建文件夹失败");
            return false;
        }
        if (notificationDao.notifyFollowers(chiefEditor, nickname + " 创建了一本新书",
                "你关注的 <a href='home?user=" + chiefEditor + "'>" + nickname +
                        "</a> 刚刚创建了新书 <a href='book?id=" + ID + "'>" + book.getName() + "</a>，快来看看吧")) {
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
                return bookDao.findByKeywords(keywords.split(" "));
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
    public boolean addChapter(String username, String nickname, Chapter chapter, String description, String rootDir) {
        Book book = bookDao.findByID(chapter.getBookID());
        if (book == null) {
            System.out.println("BookService: 书籍不存在");
            return false;
        }

        if (chapter.getName() == null || chapter.getName().length() == 0) {
            System.out.println("BookService: 章节名为空");
            return false;
        }

        if (chapter.getContent() == null || chapter.getContent().length() == 0) {
            System.out.println("BookService: 内容为空");
            return false;
        }
        // 将章节内容存存储于文件中
        String absPath = writeChapterToFile(rootDir, chapter.getBookID(), chapter.getSequence(), chapter.getContent());
        if (absPath == null) return false;
        chapter.setContent(absPath.replace(rootDir, ""));
        if (chapterDao.add(username, chapter, description)) {
            System.out.println("BookService: 添加编辑信息成功");
        } else {
            System.out.println("BookService: 添加编辑信息失败");
            return false;
        }
        // 若编辑者不是主编，通知主编
        notifyChiefEditor(username, nickname, book);
        // 通知收藏者
        notifySubscribers(username, nickname, book);
        return true;
    }

    @Override
    public boolean modifyChapter(String username, String nickname, Chapter chapter, String description, String rootDir) {
        Book book = bookDao.findByID(chapter.getBookID());
        if (book == null) {
            System.out.println("BookService: 书籍不存在");
            return false;
        }
        if (chapter.getName() == null || chapter.getName().length() == 0) {
            System.out.println("BookService: 书名为空");
            return false;
        }
        if (chapter.getContent() == null || chapter.getContent().length() == 0) {
            System.out.println("BookService: 内容为空");
            return false;
        }
        // 将章节内容存放在文件中
        String absPath = writeChapterToFile(rootDir, chapter.getBookID(), chapter.getSequence(), chapter.getContent());
        if (absPath == null) return false;
        chapter.setContent(absPath.replace(rootDir, ""));
        // 添加编辑信息
        if (chapterDao.modify(username, chapter, description)) {
            System.out.println("BookService: 添加编辑信息成功");
        } else {
            System.out.println("BookService: 添加编辑信息失败");
            return false;
        }
        // 若编辑者不是主编，通知主编
        notifyChiefEditor(username, nickname, book);
        // 通知收藏者
        notifySubscribers(username, nickname, book);
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
        boolean delCover = true;
        if (cover != null)
            delCover = FileUtil.delete(rootDir + cover, "ChapterServlet: 删除封面失败");
        boolean delDir = FileUtil.deleteDir(rootDir + "resources/book/" + bookID);
        return delDir && delCover;
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
        User user = userDao.find(username);
        Book book = bookDao.findByID(bookID);
        String[] col_split = collaborators.split(" ");
        String[] col_check = new String[col_split.length];
        int i = 0;
        for (String collaborator : col_split) {
            if (userDao.exist(collaborator) && !collaborator.equals(username))
                col_check[i++] = collaborator;
        }
        if (!bookDao.setCollaborators(bookID, col_check)) return false;
        for (String collaborator : col_check) {
            if (notificationDao.notify(collaborator, "你被授权编辑 " + book.getName(),
                    "<a href='home?user=" + username + "'>" + user.getNickname() +
                            "</a> 刚刚授予你编辑 <a href='book?id=" + book.getID() + "'>" + book.getName() + "</a> 的权限，" +
                            "和朋友们一起开始创作吧！")) {
                System.out.println("BookService: 通知协作者成功");
            } else
                System.out.println("BookService: 通知协作者失败");
        }
        return true;
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

    @Override
    public Edit[] getHistory(int bookID, int sequence) {
        return chapterDao.getHistory(bookID, sequence);
    }

    @Override
    public Edit getEdit(int ID) {
        return chapterDao.getEdit(ID);
    }

    private static String writeChapterToFile(String rootDir, int bookID, int sequence, String content) {
        String absPath = null;
        try {
            // 写入文件并获取文件名
            absPath = FileUtil.write(new File(rootDir + "resources/book/" + bookID + "/" + sequence + ".html"), content);
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

    private void notifyChiefEditor(String username, String nickname, Book book) {
        if (!username.equals(book.getChiefEditor()))
            if (notificationDao.notify(book.getChiefEditor(), "你的作品 " + book.getName() + " 已更新",
                    "<a href='home?user=" + username + "'>" + nickname +
                            "</a> 刚刚更新了 <a href='book?id=" + book.getID() + "'>" + book.getName() + "</a>。")) {
                System.out.println("BookService: 通知主编成功");
            } else
                System.out.println("BookService: 通知主编失败");

    }

    private void notifySubscribers(String username, String nickname, Book book) {
        if (notificationDao.notifySubscribers(book.getID(), "你收藏的 " + book.getName() + " 已更新",
                "<a href='home?user=" + username + "'>" + nickname +
                        "</a> 刚刚更新了 <a href='book?id=" + book.getID() + "'>" + book.getName() + "</a>，快来看看吧！")) {
            System.out.println("BookService: 通知收藏者成功");
        } else
            System.out.println("BookService: 通知收藏者失败");
    }

}