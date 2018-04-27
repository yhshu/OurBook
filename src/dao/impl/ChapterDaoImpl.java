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
    public boolean add(String username, Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("UPDATE ourbook.chapter SET sequence = sequence+1 WHERE sequence>=? AND OurBook.Chapter.bookID=?");
            stm1.setInt(1, chapter.getSequence());
            stm1.setInt(2, chapter.getBookID());
            stm1.executeUpdate();
            stm1.close();
            PreparedStatement stm2 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM chapter");
            ResultSet rs = stm2.executeQuery();
            rs.next();
            int maxID = rs.getInt("max_ID");
            rs.close();
            stm2.close();
            PreparedStatement stm3 = conn.prepareStatement("INSERT INTO chapter (name,bookID,sequence,ID)" +
                    " VALUES (?,?,?,?)");
            stm3.setString(1, chapter.getName());
            stm3.setInt(2, chapter.getBookID());
            stm3.setInt(3, chapter.getSequence());
            stm3.setInt(4, maxID + 1);
            stm3.executeUpdate();
            stm3.close();
            PreparedStatement stm4 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM edit");
            rs = stm4.executeQuery();
            rs.next();
            int maxID2 = rs.getInt("max_ID");
            rs.close();
            stm4.close();
            PreparedStatement stm5 = conn.prepareStatement("INSERT INTO edit VALUES (?,NOW(),?,?,?)");
            stm5.setString(1, username);
            stm5.setInt(2, maxID2 + 1);
            stm5.setString(3, chapter.getContent());
            stm5.setInt(4, maxID + 1);
            stm5.executeUpdate();
            stm5.close();
            conn.close();
            System.out.println("ChapterDao: 添加章节成功");
            return true;
        } catch (Exception e) {
            System.out.println("ChapterDao: 添加章节失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean modify(String username, Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("UPDATE chapter SET name=?" +
                    "WHERE bookID = ? AND sequence = ?");
            stm1.setString(1, chapter.getName());
            stm1.setInt(2, chapter.getBookID());
            stm1.setInt(3, chapter.getSequence());
            stm1.executeUpdate();
            stm1.close();
            PreparedStatement stm2 = conn.prepareStatement("SELECT MAX(ID) as max_ID FROM edit");
            ResultSet rs = stm2.executeQuery();
            rs.next();
            int maxID = rs.getInt("max_ID");
            rs.close();
            stm2.close();
            PreparedStatement stm3 = conn.prepareStatement("SELECT ID FROM ourbook.chapter WHERE bookID=? AND sequence=?");
            stm3.setInt(1, chapter.getBookID());
            stm3.setInt(2, chapter.getSequence());
            rs = stm3.executeQuery();
            rs.next();
            int ID = rs.getInt("ID");
            rs.close();
            stm3.close();
            PreparedStatement stm5 = conn.prepareStatement("INSERT INTO edit VALUES (?,NOW(),?,?,?)");
            stm5.setString(1, username);
            stm5.setInt(2, maxID + 1);
            stm5.setString(3, chapter.getContent());
            stm5.setInt(4, ID);
            stm5.executeUpdate();
            stm5.close();
            conn.close();
            System.out.println("ChapterDao: 修改章节成功");
            return true;
        } catch (Exception e) {
            System.out.println("ChapterDao: 修改章节失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Chapter findByPri(int bookID, int sequence) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter_info WHERE bookID = ? AND sequence = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sequence);
            Chapter[] chapters = getChapters(stm);
            conn.close();
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
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter_info,book,user WHERE bookID = book.ID " +
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
            conn.close();
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
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM chapter_info WHERE bookID = ? ORDER BY sequence");
            stm.setInt(1, bookID);
            Chapter[] chapters = getChapters(stm);
            if (chapters != null) return chapters;
            conn.close();
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
                try {
                    chapter.setLastModified(rs.getTimestamp("last_modified"));
                } catch (Exception ignored) {
                }
                chapters.add(chapter);
            }
            rs.close();
            stm.close();
            return chapters.toArray(new Chapter[0]);
        } catch (Exception e) {
            System.out.println("ChapterDao: 获取章节失败");
        }
        return null;
    }
}