package dao.impl;

import Util.DBUtil;
import dao.ChapterDao;
import model.Chapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ChapterDaoImpl implements ChapterDao {
    private Connection conn = null;

    @Override
    public boolean add(Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("UPDATE book SET chapter_num = chapter_num + 1 WHERE ID = ?");
            stm1.setInt(1, chapter.getBookID());
            try {
                stm1.executeUpdate();
                stm1.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ChapterDao: 修改 book 表章节数失败");
                return false;
            }

            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm2 = conn.prepareStatement("INSERT INTO chapter (name,bookID,sequence,content) VALUES (?,?,?,?)");
            stm2.setString(1, chapter.getName());
            stm2.setInt(2, chapter.getBookID());
            stm2.setInt(3, chapter.getSequence());
            stm2.setString(4, chapter.getContent());
            try {
                stm2.executeUpdate();
                stm2.close();
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
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter,book,user WHERE bookID = ID AND username = chiefEditor AND " + DBUtil.keywordsMatchCondition("chapter.name", keywords));
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
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter WHERE bookID = ?");
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
            PreparedStatement chapter = conn.prepareStatement("DELETE FROM chapter WHERE bookID = ? and sequence = ?");
            chapter.setInt(1, bookID);
            chapter.setInt(2, sequence);
            try {
                chapter.executeUpdate();
                System.out.println("ChapterDao: 删除指定章节成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("ChapterDao: 删除指定章节失败");
            }
            chapter.close();
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
                Chapter chapter = new Chapter(rs.getString("name"), rs.getInt("bookID"), rs.getInt("sequence"), rs.getString("content"));
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