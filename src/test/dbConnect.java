package test;

import listner.Config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Amysue on 2016/3/4.
 */
@WebServlet("/JDBCDataSource")
public class dbConnect extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = Config.getConn(getServletContext());
            st = conn.createStatement();
            rs = st.executeQuery("select * from t_msg");
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            while (rs.next()) {
                out.println(rs.getString("title"));
                out.println(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
