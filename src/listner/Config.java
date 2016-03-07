package listner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Amysue on 2016/3/5.
 */
public class Config implements ServletContextListener {
    private static final String ATTRIBUTE_NAME = "config";
    private static DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        Context ctx = null;
        try {
            System.out.println("=====CONFIG IS STARTING====");
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/MyLocalDB");
        } catch (NamingException e) {
            throw new RuntimeException("Config failed: datasource not found", e);
        }
        servletContext.setAttribute(ATTRIBUTE_NAME, this);
    }

    public DataSource getDataSource() {
        return dataSource;
    }
    public static Config getInstance(ServletContext servletContext) {
        return (Config) servletContext.getAttribute(ATTRIBUTE_NAME);
    }

    public static Connection getConn(ServletContext servletContext) {
        Connection conn = null;
        try {
            conn = getInstance(servletContext).getDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Get Database Connection Fail", e);
        }
        return conn;
    }
}
