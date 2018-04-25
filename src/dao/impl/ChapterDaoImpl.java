package dao.impl;

import Util.DBUtil;
import dao.ChapterDao;
import model.Chapter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;


public class ChapterDaoImpl implements ChapterDao {
    private Connection conn = null;

    @Override
    public boolean add(Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO chapter (name,bookID,sequence,content,last_modified)" +
                    " VALUES (?,?,?,?,?)");
            stm.setString(1, chapter.getName());
            stm.setInt(2, chapter.getBookID());
            stm.setInt(3, chapter.getSequence());
            stm.setString(4, chapter.getContent());
            stm.setDate(5, new Date(Calendar.getInstance().getTime().getTime()));
            try {
                stm.executeUpdate();
                stm.close();
                conn.close(); // 关闭数据库连接
                System.out.println("ChapterDao: 添加章节成功");
                return true;
            } catch (Exception e1) {
                System.out.println("ChapterDao: 添加章节失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modify(Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("UPDATE chapter SET name=?,last_modified=? " +
                    "WHERE bookID = ? AND sequence = ?");
            stm.setString(1, chapter.getName());
            stm.setDate(2, new Date(Calendar.getInstance().getTime().getTime()));
            stm.setInt(3, chapter.getBookID());
            stm.setInt(4, chapter.getSequence());
            try {
                stm.executeUpdate();
                stm.close();
                conn.close(); // 关闭数据库连接
                System.out.println("ChapterDao: 修改章节成功");
                return true;
            } catch (Exception e1) {
                System.out.println("ChapterDao: 修改章节失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Chapter findByPri(int bookID, int sequence) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter WHERE bookID = ? AND sequence = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sequence);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters[0];
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按主键获取章节失败");
        }
        return null;
    }

    @Override
    public Chapter[] findByKeywords(String[] keywords) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter,book,user WHERE bookID = book.ID " +
                    "AND username = chiefEditor AND " + DBUtil.keywordsMatchCondition("chapter.name", keywords));
            ResultSet rs = stm.executeQuery();
            ArrayList<Chapter> chapters = new ArrayList<>();
            while (rs.next()) {
                Chapter chapter = new Chapter(rs.getString("chapter.name"), rs.getInt("bookID"),
                        rs.getInt("sequence"), rs.getString("content"));
                chapter.setBookName(rs.getString("book.name"));
                chapter.setEditorNickname(rs.getString("nickname"));
                chapter.setEditorUsername(rs.getString("username"));
                chapter.setBookCover(rs.getString("cover"));
                chapters.add(chapter);
            }
            rs.close();
            stm.close();
            conn.close(); // 关闭数据库连接
            return chapters.toArray(new Chapter[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 通过关键字查找章节失败");
        }
        return new Chapter[0];
    }

    @Override
    public Chapter[] findByBookID(int bookID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter WHERE bookID = ? ORDER BY sequence");
            stm.setInt(1, bookID);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按书目获取章节失败");
        }
        return new Chapter[0];
    }

    @Override
    public boolean delete(int bookID, int sequence) {
        try {
            conn = DBUtil.connectDB();
            PreparedStatement stm1 = conn.prepareStatement("DELETE FROM chapter WHERE bookID = ? and sequence = ?");
            stm1.setInt(1, bookID);
            stm1.setInt(2, sequence);
            try {
                stm1.executeUpdate();
                System.out.println("ChapterDao: 删除指定章节成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("ChapterDao: 删除指定章节失败");
            }
            PreparedStatement stm2 = conn.prepareStatement("UPDATE chapter SET sequence = sequence - 1 WHERE bookID = ? and sequence > ?");
            stm2.setInt(1, bookID);
            stm2.setInt(2, sequence);
            try {
                stm2.executeUpdate();
                System.out.println("ChapterDao: 章节号调整成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("ChapterDao: 章节号调整失败");
            }
            stm2.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Chapter[] getChapters(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<Chapter> chapters = new ArrayList<>();
            while (rs.next()) {
                Chapter chapter = new Chapter(rs.getString("name"), rs.getInt("bookID"),
                        rs.getInt("sequence"), rs.getString("content"));
                chapters.add(chapter);
            }
            rs.close();
            stm.close();
            conn.close(); // 关闭数据库连接
            return chapters.toArray(new Chapter[0]);
        } catch (Exception e) {
            System.out.println("ChapterDao: 获取章节失败");
        }
        return null;
    }
}