package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;
import model.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findByID(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.book_info WHERE ID = ?");
            stm.setInt(1, ID);
            Book[] books = getBooks(stm);
            if (books != null && books[0] != null)
                return books[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookDao: findByID(" + ID + ")失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.book_info WHERE name = ?");
            stm.setString(1, name);
            Book[] books = getBooks(stm);
            if (books != null)
                return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywords(String[] keywords) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.book_info WHERE " + DBUtil.keywordsMatchCondition("keywords", keywords));
            Book[] books = getBooks(stm);
            if (books != null)
                return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywordsClick(String[] keywords, String range) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT book_info.ID AS ID, book_info.name AS name,book_info.description AS " +
                    "description, book_info.keywords as keywords, book_info.chief_editor AS chief_editor," +
                    "book_info.cover AS cover, book_info.last_modified AS last_modified, book_info.chapter_num AS " +
                    "chapter_num, book_info.favorites AS favorites, book_info.clicks AS clicks," +
                    " book_info.chief_editor_nickname as chief_editor_nickname FROM ourbook.book_info LEFT JOIN " +
                    "(SELECT * FROM click WHERE " + DBUtil.timeLimit("time", range) + ") AS c ON ID = bookID WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords) +
                    " GROUP BY ID ORDER BY COUNT(bookID) DESC");
            Book[] books = getBooks(stm);
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywordsFav(String[] keywords, String range) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT book_info.ID AS ID, book_info.name AS name,book_info.description AS " +
                    "description, book_info.keywords as keywords, book_info.chief_editor AS chief_editor," +
                    "book_info.cover AS cover, book_info.last_modified AS last_modified, book_info.chapter_num AS " +
                    "chapter_num, book_info.favorites AS favorites,book_info.clicks as clicks," +
                    " book_info.chief_editor_nickname as chief_editor_nickname FROM ourbook.book_info LEFT JOIN " +
                    "(SELECT * FROM favorite WHERE " + DBUtil.timeLimit("time", range) + ") AS f ON ID=bookID WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords)
                    + " GROUP BY ID ORDER BY COUNT(bookID) DESC");
            Book[] books = getBooks(stm);
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    @Override
    public int add(Book book) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm1 = conn.prepareStatement("INSERT INTO ourbook.book " +
                    "(ID,name,description,chief_editor,keywords,cover) VALUES (null,?,?,?,?,?)");
            stm1.setString(1, book.getName());
            stm1.setString(2, book.getDescription());
            stm1.setString(3, book.getChiefEditor());
            stm1.setString(4, book.getKeywords());
            stm1.setString(5, book.getCover());
            stm1.executeUpdate();
            stm2 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM book");
            ResultSet rs = stm2.executeQuery();
            rs.next();
            int ID = rs.getInt("max_ID");
            rs.close();
            System.out.println("BookDao: 添加书目成功");
            return ID;
        } catch (Exception e) {
            System.out.println("BookDao: 添加书目失败");
            e.printStackTrace();
            return -1;
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public int maxID() {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT MAX(ID) AS max_id FROM book");
            ResultSet rs = stm.executeQuery();
            int ret = -1;
            while (rs.next())
                ret = rs.getInt("max_id");
            rs.close();
            System.out.println("BookDao: 查询最大ID成功");
            return ret;
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            System.out.println("BookDao: 查询最大ID失败");
            return -1;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    public Book[] findByUserID(String chiefEditorID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.book_info WHERE chief_editor = ?");
            stm.setString(1, chiefEditorID);
            Book[] books = getBooks(stm);
            if (books != null)
                return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    /**
     * 返回符合语句的多个书籍
     *
     * @param stm SQL语句
     * @return 符合语句的多个书籍
     */
    private Book[] getBooks(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<Book> books = new ArrayList<>();
            while (rs.next()) {
                Book book = new Book(rs.getInt("ID"), rs.getString("name"),
                        rs.getString("description"), rs.getString("chief_editor"),
                        rs.getString("keywords"), rs.getString("cover"),
                        rs.getInt("chapter_num"));
                try {
                    book.setClicks(rs.getInt("clicks"));
                } catch (Exception ignored) {
                }
                try {
                    book.setFavorites(rs.getInt("favorites"));
                } catch (Exception ignored) {
                }
                try {
                    book.setLastModified(rs.getTimestamp("last_modified"));
                } catch (Exception ignored) {
                }
                try {
                    book.setChapterNum(rs.getInt("chapter_num"));
                } catch (Exception ignored) {
                }
                try {
                    book.setChiefEditorNickname(rs.getString("chief_editor_nickname"));
                } catch (Exception ignored) {
                }
                books.add(book);
            }
            rs.close();
            return books.toArray(new Book[0]);
        } catch (Exception e) {
            System.out.println("BookDao: 获取书目失败:");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String delete(int bookID) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm1 = conn.prepareStatement("SELECT cover FROM book WHERE ID=?");
            stm1.setInt(1, bookID);
            ResultSet rs = stm1.executeQuery();
            rs.next();
            String cover = rs.getString("cover");
            stm2 = conn.prepareStatement("DELETE FROM book WHERE ID = ?");
            stm2.setInt(1, bookID);
            stm2.executeUpdate();
            System.out.println("BookDao: 删除书目成功");
            return cover;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookDao: 删除书目失败");
            return null;
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Book[] getFavorites(String username) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info, favorite WHERE username = ? AND bookid = ID");
            stm.setString(1, username);
            Book[] books = getBooks(stm);
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.safeClose(conn);
        }
        return new Book[0];
    }

    @Override
    public boolean click(String username, int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("INSERT INTO click VALUES (?,?,NOW())");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Book[] recommend() {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            ArrayList<Book> books = new ArrayList<>();
            stm = conn.prepareStatement("SELECT * FROM book_info ORDER BY favorites * 10 + clicks DESC");
            int displayBookNum = 10;
            ResultSet rs = stm.executeQuery();
            while (displayBookNum-- > 0 && rs.next()) {
                books.add(new Book(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("chief_editor"),
                        rs.getString("keywords"),
                        rs.getString("cover"),
                        rs.getInt("chapter_num"),
                        rs.getTimestamp("last_modified"),
                        rs.getInt("clicks"),
                        rs.getInt("favorites"),
                        rs.getString("chief_editor_nickname")));
            }
            rs.close();
            return books.toArray(new Book[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean setCollaborators(int bookID, String[] collaborators) {
        PreparedStatement delete_stm = null;
        PreparedStatement insert_stm = null;
        try {
            conn = DBUtil.connectDB();
            StringBuilder collaborator_sql = new StringBuilder();
            for (String collaborator : collaborators) {
                if (collaborator != null) {
                    collaborator_sql.append("(").append(bookID).append(",\"").append(collaborator).append("\"),");
                }
            }
            // 将最后一个逗号修改为分号
            collaborator_sql.setCharAt(collaborator_sql.length() - 1, ';');

            delete_stm = conn.prepareStatement("DELETE FROM writes WHERE bookID = ?");
            delete_stm.setInt(1, bookID);
            delete_stm.executeUpdate();
            String sql = String.format("INSERT IGNORE INTO writes(bookID, username) VALUES %s", collaborator_sql);
            insert_stm = conn.prepareStatement(sql);
            insert_stm.executeUpdate();
            System.out.println("BookDao: 设置协作者成功");
            return true;
        } catch (StringIndexOutOfBoundsException siobe) {
            siobe.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookDao: 设置协作者失败");
            return false;
        } finally {
            DBUtil.safeClose(delete_stm);
            DBUtil.safeClose(insert_stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public User[] getCollaborators(int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            ArrayList<User> users = new ArrayList<>();
            stm = conn.prepareStatement("SELECT * FROM writes,user WHERE writes.username = user.username AND writes.bookID = ?");
            stm.setInt(1, bookID);
            ResultSet rs = stm.executeQuery();
            boolean exist = false;
            while (rs.next()) {
                exist = true;
                User user = new User(rs.getString("username"), rs.getString("nickname"), rs.getString("password"), rs.getString("description"), rs.getString("avatar"));
                users.add(user);
            }
            rs.close();
            return (exist) ? users.toArray(new User[0]) : null;
        } catch (Exception e) {
            System.out.println("BookDao: 获取协作者失败");
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }
}
