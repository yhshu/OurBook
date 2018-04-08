package dao.impl;

import Util.DBUtil;
import dao.ActivityDao;
import model.Movement;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ActivityDaompl implements ActivityDao {
    private Connection conn = null;

    public void add(Movement movement){
        try{
            conn = DBUtil.connectDB();
            PreparedStatement stm = conn.prepareStatement("INSERT INTO movement(name,thumb,comment) VALUES (?,?,?)");
            stm.setString(1,movement.getNickname());
            stm.setString(2,movement.getComment());
            stm.setInt(3,movement.getThumb());
            try {
                stm.executeUpdate();
                System.out.println("ActivityDao: 添加动态成功");
            } catch (Exception e1) {
                System.out.println("ActivityDao: 添加动态失败");
            }
            stm.close();
            conn.close(); // 关闭数据库连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public movement[] findByName(String name) {
        try {
            conn = DBUtil.connectDB(); //连接数据库
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM movement");
        }
    }

}
