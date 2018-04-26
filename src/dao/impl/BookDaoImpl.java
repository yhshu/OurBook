package dao.impl;

import Util.DBUtil;
import dao.BookDao;
import model.Book;
import model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

public class BookDaoImpl implements BookDao {
    private Connection conn = null;

    @Override
    public Book findByID(int ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info WHERE ID = ?");
            stm.setInt(1, ID);
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null && books[0] != null) return books[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookDao: findByID(" + ID + ")失败");
        }
        return null;
    }

    @Override
    public Book[] findByName(String name) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info WHERE name = ?");
            stm.setString(1, name);
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywords(String[] keywords) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords));
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywordsClick(String[] keywords, String range) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info LEFT JOIN " +
                    "(SELECT * FROM click WHERE " + DBUtil.timeLimit("date", range) + ") AS c ON ID=bookID WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords) +
                    " GROUP BY ID ORDER BY COUNT(bookID) DESC");
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public Book[] findByKeywordsFav(String[] keywords, String range) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info LEFT JOIN " +
                    "(SELECT * FROM favorite WHERE " + DBUtil.timeLimit("date", range) + ") AS f ON ID=bookID WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords)
                    + " GROUP BY ID ORDER BY COUNT(bookid) DESC");
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public void add(Book book) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO book " +
                    "(ID,name,description,chiefEditor,keywords,cover) VALUES (null,?,?,?,?,?)");
            stm.setString(1, book.getName());
            stm.setString(2, book.getDescription());
            stm.setString(3, book.getChiefEditor());
            stm.setString(4, book.getKeywords());
            stm.setString(5, book.getCover());
            try {
                stm.executeUpdate();
                System.out.println("BookDao: 添加书目成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("BookDao: 添加书目失败");
            }
            stm.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int maxID() {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT MAX(ID) AS max_id FROM book");
            ResultSet rs;
            try {
                rs = stm.executeQuery();
                int ret = -1;
                while (rs.next())
                    ret = rs.getInt("max_id");
                rs.close();
                stm.close();
                conn.close();
                System.out.println("BookDao: 查询最大ID成功");
                return ret;
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("BookDao: 查询最大ID失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Book[] findByUserID(String chiefEditorID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info WHERE chiefEditor = ?");
            stm.setString(1, chiefEditorID);
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null)
                return books;
        } catch (Exception e) {
            e.printStackTrace();
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
                        rs.getString("description"), rs.getString("chiefEditor"),
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
                    book.setLastModified(rs.getDate("last_modified"));
                } catch (Exception ignored) {
                }
                try {
                    book.setChapterNum(rs.getInt("chapter_num"));
                } catch (Exception ignored) {
                }
                books.add(book);
            }
            rs.close();
            stm.close();
            return books.toArray(new Book[0]);
        } catch (Exception e) {
            System.out.println("BookDao: 获取书目失败:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(int bookID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement delChapter = conn.prepareStatement("DELETE FROM chapter WHERE bookID = ?");
            delChapter.setInt(1, bookID);
            PreparedStatement delClick = conn.prepareStatement("DELETE FROM click WHERE bookID = ?");
            delClick.setInt(1, bookID);
            PreparedStatement delBook = conn.prepareStatement("DELETE FROM book WHERE ID = ?");
            delBook.setInt(1, bookID);
            try {
                delChapter.executeUpdate();
                delClick.executeUpdate();
                delBook.executeUpdate();
                System.out.println("BookDao: 删除书目成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("BookDao: 删除书目失败");
            }
            delChapter.close();
            delBook.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book[] getFavorites(String username) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info, favorite WHERE username = ? AND bookid = ID");
            stm.setString(1, username);
            Book[] books = getBooks(stm);
            conn.close();
            if (books != null) return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public boolean click(String username, int bookID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO click VALUES (?,?,?)");
            stm.setString(1, username);
            stm.setInt(2, bookID);
            stm.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
            stm.executeUpdate();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Book[] recommend() {
        try {
            conn = DBUtil.connectDB();
            ArrayList<Book> books = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM book_info ORDER BY favorites * 10 + clicks DESC");
            int displayBookNum = 10;
            ResultSet rs = stm.executeQuery();
            while (displayBookNum-- > 0 && rs.next()) {
                books.add(new Book(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("chiefEditor"),
                        rs.getString("keywords"),
                        rs.getString("cover"),
                        rs.getInt("chapter_num"),
                        rs.getDate("last_modified"),
                        rs.getInt("clicks"),
                        rs.getInt("favorites")));
            }
            rs.close();
            stm.close();
            conn.close();
            return books.toArray(new Book[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Book[0];
    }

    @Override
    public boolean setCollaborators(int bookID, String[] collaborators) {
        try {
            conn = DBUtil.connectDB();
            StringBuilder collaborator_sql = null;
            for (String collaborator : collaborators) {
                collaborator_sql.append("(" + bookID + "," + collaborator + "),");
            }
            // 将最后一个逗号修改为分号
            collaborator_sql.setCharAt(collaborator_sql.length() - 1, ';');
            String sql = "INSERT INTO edit(bookID,username) VALUES" + collaborator_sql;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.executeUpdate();
            stm.close();
            System.out.println("BookDao: 设置协作者成功");
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("BookDao: 设置协作者失败");
        }
        return false;
    }

    @Override
    public User[] getCollaborators(int bookID) {
        try {
            conn = DBUtil.connectDB();
            ArrayList<User> users = new ArrayList<>();
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM edit,user WHERE edit.username = user.username AND edit.bookID = ?");
            stm.setInt(1, bookID);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("nickname"), rs.getString("password"), rs.getString("description"), rs.getString("avatar"));
                users.add(user);
            }
            rs.close();
            stm.close();
            return users.toArray(new User[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new User[0];
    }
}
