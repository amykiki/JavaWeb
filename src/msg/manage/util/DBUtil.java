package msg.manage.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Amysue on 2016/3/5.
 */
public class DBUtil {
    private static DataSource ds = null;
    public static final String tUser = "t_user";
    public static final String tMsg = "t_msg";

    private static DataSource getDataSource() {
        if (ds == null) {
            Context ctx = null;
            try {
                ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
            } catch (NamingException e) {
                throw new RuntimeException("Config failed: datasource not found", e);
            }
            System.out.println("=====GET DATASOURCE========");
        }
        return ds;
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Get Database Connection Fail", e);
        }
        return conn;
    }
}
