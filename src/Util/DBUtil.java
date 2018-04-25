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
        String ServandDB = "jdbc:mysql://sh-cdb-7p5q57hl.sql.tencentcdb.com:63280/ourbook";
        String DBUser = "root";
        String DBPWD = "root3306";

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

    public static String timeLimit(String field, String range) {
        if (range.equalsIgnoreCase("day")) return "TO_DAYS(" + field + ") = TO_DAYS(NOW())";
        else if (range.equalsIgnoreCase("week")) return "DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date("
                + field + ")";
        else if (range.equalsIgnoreCase("month")) return "DATE_SUB(CURDATE(), INTERVAL 30 DAY) <= date("
                + field + ")";
        else if (range.equalsIgnoreCase("year")) return "DATE_SUB(CURDATE(), INTERVAL 365 DAY) <= date("
                + field + ")";
        else return "1";
    }

}
