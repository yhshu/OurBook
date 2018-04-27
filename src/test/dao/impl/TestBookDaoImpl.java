package test.dao.impl;


import Util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestBookDaoImpl {
    public static void main(String[] args) {
        try {
            Connection conn = DBUtil.connectDB(); // 连接数据库
            PreparedStatement stm1 = conn.prepareStatement("SELECT MAX(ID) FROM ourbook.chapter");
            ResultSet rs = stm1.executeQuery();
            rs.next();
            int maxID = rs.getInt("ID");
            stm1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}