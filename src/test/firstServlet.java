package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Amysue on 2016/2/26.
 */
public class firstServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        for (int i = 1; i < 11; i++) {
            out.print("<h1>");
            out.print("Hello Servlet" + i);
            out.println("</h1>");
        }
        out.println("</body>");
        out.println("</html>");
    }

}
