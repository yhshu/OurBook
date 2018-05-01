package dao.impl;

import Util.DBUtil;
import dao.ChapterDao;
import model.Chapter;
import model.Edit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class ChapterDaoImpl implements ChapterDao {
    private Connection conn = null;

    @Override
    public boolean add(String username, Chapter chapter, String description) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        PreparedStatement stm3 = null;
        PreparedStatement stm4 = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm1 = conn.prepareStatement("UPDATE ourbook.chapter SET sequence = sequence + 1 WHERE sequence >= ? " +
                    "AND OurBook.Chapter.bookID = ?"); // 重新设置章节序号
            stm1.setInt(1, chapter.getSequence());
            stm1.setInt(2, chapter.getBookID());
            stm1.executeUpdate();
            stm2 = conn.prepareStatement("INSERT INTO ourbook.chapter (bookID,sequence) VALUES (?,?)");
            stm2.setInt(1, chapter.getBookID());
            stm2.setInt(2, chapter.getSequence());
            stm2.executeUpdate();
            stm3 = conn.prepareStatement("SELECT ID FROM ourbook.chapter_info WHERE bookID = ? AND sequence = ?");
            stm3.setInt(1, chapter.getBookID());
            stm3.setInt(2, chapter.getSequence());
            ResultSet rs = stm3.executeQuery();
            rs.next();
            int chapterID = rs.getInt("ID");
            rs.close();
            stm4 = conn.prepareStatement("INSERT INTO ourbook.edit(username, time, name, content, chapterID,description) " +
                    "VALUES (?, NOW(), ?, ?, ?,?)"); // 编辑记录
            stm4.setString(1, username);
            stm4.setString(2, chapter.getName());
            stm4.setString(3, chapter.getContent());
            stm4.setInt(4, chapterID);
            stm4.setString(5, description);
            stm4.executeUpdate();
            System.out.println("ChapterDao: 添加章节成功");
            return true;
        } catch (Exception e) {
            System.out.println("ChapterDao: 添加章节失败");
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(stm3);
            DBUtil.safeClose(stm4);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public boolean modify(String username, Chapter chapter, String description) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm1 = conn.prepareStatement("SELECT ID FROM ourbook.chapter_info WHERE bookID = ? AND sequence = ?");
            stm1.setInt(1, chapter.getBookID());
            stm1.setInt(2, chapter.getSequence());
            ResultSet rs = stm1.executeQuery();
            rs.next();
            int chapterID = rs.getInt("ID");
            rs.close();
            stm2 = conn.prepareStatement("INSERT INTO ourbook.edit(username, time, name, content, chapterID,description) " +
                    "VALUES (?, NOW(), ?, ?, ?,?)");
            stm2.setString(1, username);
            stm2.setString(2, chapter.getName());
            stm2.setString(3, chapter.getContent());
            stm2.setInt(4, chapterID);
            stm2.setString(5, description);
            stm2.executeUpdate();
            System.out.println("ChapterDao: 修改章节成功");
            return true;
        } catch (Exception e) {
            System.out.println("ChapterDao: 修改章节失败");
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Chapter findByPri(int bookID, int sequence) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.chapter_info WHERE bookID = ? AND sequence = ?");
            stm.setInt(1, bookID);
            stm.setInt(2, sequence);
            Chapter[] chapters = getChapters(stm);
            if (chapters.length > 0)
                return chapters[0];
            else return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按主键获取章节失败");
            return null;
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Chapter[] findByKeywords(String[] keywords) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.chapter_info,ourbook.book,ourbook.user " +
                    "WHERE chapter_info.bookID = book.ID AND username = chiefEditor AND "
                    + DBUtil.keywordsMatchCondition("chapter_info.name", keywords));
            ResultSet rs = stm.executeQuery();
            ArrayList<Chapter> chapters = new ArrayList<>();
            while (rs.next()) {
                Chapter chapter = new Chapter(rs.getString("chapter_info.name"), rs.getInt("bookID"),
                        rs.getInt("sequence"), rs.getString("content"));
                chapter.setBookName(rs.getString("book.name"));
                chapter.setEditorNickname(rs.getString("nickname"));
                chapter.setEditorUsername(rs.getString("username"));
                chapter.setBookCover(rs.getString("cover"));
                chapters.add(chapter);
            }
            rs.close();
            return chapters.toArray(new Chapter[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 通过关键字查找章节失败");
            return new Chapter[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Chapter[] findByBookID(int bookID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            stm = conn.prepareStatement("SELECT * FROM ourbook.chapter_info WHERE bookID = ? ORDER BY sequence");
            stm.setInt(1, bookID);
            return getChapters(stm);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 按书目获取章节失败");
            return new Chapter[0];
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public String[] delete(int bookID, int sequence) {
        PreparedStatement stm1 = null;
        PreparedStatement stm2 = null;
        PreparedStatement stm3 = null;
        ArrayList<String> result = new ArrayList<>();
        try {
            conn = DBUtil.connectDB();
            stm1 = conn.prepareStatement("SELECT e.content as content FROM edit e JOIN chapter c " +
                    "on e.chapterID = c.ID WHERE c.bookID = ? AND c.sequence = ?"); // 获取历史文件的路径，准备删除
            stm1.setInt(1, bookID);
            stm1.setInt(2, sequence);
            ResultSet rs = stm1.executeQuery();
            while (rs.next())
                result.add(rs.getString("content"));
            rs.close();
            stm2 = conn.prepareStatement("DELETE FROM ourbook.chapter WHERE bookID = ? and sequence = ?"); // 删除章节
            stm2.setInt(1, bookID);
            stm2.setInt(2, sequence);
            try {
                stm2.executeUpdate();
                System.out.println("ChapterDao: 删除指定章节成功");
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println("ChapterDao: 删除指定章节失败");
            }
            stm3 = conn.prepareStatement("UPDATE ourbook.chapter SET sequence = sequence - 1 WHERE bookID = ? and sequence > ?"); // 调整章节号
            stm3.setInt(1, bookID);
            stm3.setInt(2, sequence);
            stm3.executeUpdate();
            System.out.println("ChapterDao: 章节号调整成功");
            return result.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 章节号调整失败");
            return null;
        } finally {
            DBUtil.safeClose(stm1);
            DBUtil.safeClose(stm2);
            DBUtil.safeClose(stm3);
            DBUtil.safeClose(conn);
        }
    }

    @Override
    public Edit[] getHistory(int bookID, int sequence) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            ArrayList<Edit> edits = new ArrayList<>();
            stm = conn.prepareStatement("SELECT * FROM ourbook.edit_info,ourbook.chapter_info WHERE chapter_info.bookID = ? AND chapter_info.sequence = ? AND chapter_info.ID = edit_info.chapterID");
            stm.setInt(1, bookID);
            stm.setInt(2, sequence);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Edit edit = new Edit(rs.getString("name"), rs.getInt("chapterID"),
                        rs.getString("content"), rs.getString("description"),
                        rs.getTimestamp("time"), rs.getString("username"));
                edit.setEditorNickname(rs.getString("editor_nickname"));
                edit.setID(rs.getInt("ID"));
                edit.setSequence(rs.getInt("sequence"));
                edit.setBookID(rs.getInt("bookID"));
                edits.add(edit);
            }
            rs.close();
            return edits.toArray(new Edit[0]);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 获取章节历史记录失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return null;
    }

    @Override
    public Edit getEdit(int ID) {
        PreparedStatement stm = null;
        try {
            conn = DBUtil.connectDB();
            stm = conn.prepareStatement("SELECT * FROM ourbook.edit_info WHERE ID =?");
            stm.setInt(1, ID);
            ResultSet rs = stm.executeQuery();
            rs.next();
            Edit edit = new Edit(rs.getString("name"), rs.getInt("chapterID"),
                    rs.getString("content"), rs.getString("description"),
                    rs.getTimestamp("time"), rs.getString("username"));
            edit.setEditorNickname(rs.getString("editor_nickname"));
            edit.setID(rs.getInt("ID"));
            edit.setSequence(rs.getInt("sequence"));
            edit.setBookID(rs.getInt("bookID"));
            return edit;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ChapterDao: 获取历史失败");
        } finally {
            DBUtil.safeClose(stm);
            DBUtil.safeClose(conn);
        }
        return null;
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
            return chapters.toArray(new Chapter[0]);
        } catch (Exception e) {
            System.out.println("ChapterDao: 获取章节失败");
            return new Chapter[0];
        }
    }
}