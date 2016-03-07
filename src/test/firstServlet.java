package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

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
        out.println("<h3>");
        out.println("大家好" + "</br>");
        out.println(req.getContextPath() + "</br>");
        out.println(req.getSession().getServletContext().getRealPath("/") + "</br>");
        Map<String, String[]> paramaps = req.getParameterMap();
        for (String key : paramaps.keySet()) {
            String[] values = paramaps.get(key);
            out.print(key + "=");
            out.println(req.getParameter(key).trim() + "</br>");
        }
        if (req.getParameter("times") != null) {
            for (int i = 0; i < Integer.parseInt(req.getParameter("times")); i++) {
                out.println("Hello Servlet" + i + "</br>");
            }
        }
        out.println("</h3>");
        out.println("</body>");
        out.println("</html>");
//        out.flush();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init.....");
    }
}
