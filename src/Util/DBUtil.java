package Util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    /**
     * 连接数据库
     */

    public static Connection connectDB() throws NamingException, SQLException {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ourbook");
        return ds.getConnection();
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
