package Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    /**
     * 连接数据库
     */
    public static Connection connectDB() {
        Connection conn = null;
        String classForName = "com.mysql.jdbc.Driver";
        String ServandDB = "jdbc:mysql://104.207.135.139:3306/ourbook";
        String DBUser = "root";
        String DBPWD = "root";

        try {
            Class.forName(classForName).newInstance();
            conn = DriverManager.getConnection(ServandDB, DBUser, DBPWD);
            System.out.println("DBUtil: 数据库连接成功");
        } catch (Exception e) {
            System.out.println("DBUtil: 数据库连接失败");
        }
        return conn;
    }

    public static String keywordsMatchCondition(String field, String[] keywords) {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (String keyword : keywords) {
            stringBuilder.append(field).append(" LIKE '%").append(keyword).append("%' and ");
        }
        stringBuilder.append("1)");
        return stringBuilder.toString();
    }
}
