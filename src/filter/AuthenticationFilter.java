package filter;

import msg.manage.modal.User;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Amysue on 2016/3/6.
 */
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("Authentication filter");
        HttpServletRequest req = (HttpServletRequest) request;
        User u = (User) req.getSession().getAttribute("loguser");
        if (u != null) {
            chain.doFilter(request, response);
        } else {
            HttpServletResponse resp = (HttpServletResponse)response;
            resp.sendRedirect(req.getContextPath() + "/msg/login.jsp");
        }
    }
}
