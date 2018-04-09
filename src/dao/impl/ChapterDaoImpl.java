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
    public void add(Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm =
                    conn.prepareStatement("INSERT INTO Chapter (name,bookID,description,content) VALUES (?,?,?,?)");
            stm.setString(1, chapter.getName());
            stm.setInt(2, chapter.getBookID());
            stm.setString(3, chapter.getDescription());
            stm.setString(3, chapter.getContent());
            try {
                stm.executeUpdate();
                System.out.println("ChapterDao: 添加章节成功");
            } catch (Exception e1) {
                System.out.println("ChapterDao: 添加章节失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Chapter findByID(int ID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Chapter WHERE ID = ?");
            stm.setInt(1, ID);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Chapter[] findByKeywords(String[] keywords) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Chapter WHERE "
                    + DBUtil.keywordsMatchCondition("keywords", keywords));
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Chapter[0];
    }

    @Override
    public Chapter[] findByBookID(int bookID) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Chapter WHERE bookID = ?");
            stm.setInt(1, bookID);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Chapter[0];
    }

    @Override
    public Chapter[] find(int bookID, int sectionNumber) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm =
                    conn.prepareStatement("SELECT * FROM Chapter WHERE bookID = ? AND sectionNumber = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sectionNumber);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Chapter[0];
    }

    @Override
    public Chapter findPrev(int bookID, int sequenceNumber) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm =
                    conn.prepareStatement("SELECT * FROM Chapter WHERE bookID = ? AND sequence = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sequenceNumber - 1);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Chapter findNext(int bookID, int sequenceNumber) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm =
                    conn.prepareStatement("SELECT * FROM Chapter WHERE bookID = ? AND sequence = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sequenceNumber + 1);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Chapter[] getChapters(PreparedStatement stm) {
        try {
            ResultSet rs = stm.executeQuery();
            ArrayList<Chapter> chapters = new ArrayList<>();
            while (rs.next()) {
                Chapter chapter = new Chapter(rs.getString("name"),
                        rs.getInt("bookID"), rs.getInt("sectionNumber"),
                        rs.getString("description"), rs.getString("content"));
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
