package dao.impl;

import Util.DBUtil;
import dao.ChapterDao;
import model.Chapter;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class ChapterDaoImpl implements ChapterDao {
    private Connection conn = null;

    @Override
    public void add(Chapter chapter) {
        try {
            conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm = conn.prepareStatement("INSERT INTO Chapter (name,bookID,content) VALUES (?,?,?)");
            stm.setString(1, chapter.getName());
            stm.setString(2, chapter.getBookID());
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
    public Chapter find(String ID) {
        return null;
    }
}
