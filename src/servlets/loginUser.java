package servlets;

import msg.manage.dao.DaoFactory;
import msg.manage.dao.IUserDao;
import msg.manage.modal.User;
import msg.manage.util.MsgException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Amysue on 2016/3/8.
 */
public class loginUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserDao          udao     = DaoFactory.getUserDao();
        String            username = req.getParameter("username");
        String            password = req.getParameter("password");
        User              u        = null;
        String            errMsg   = "";
        RequestDispatcher rd;
        try {
            u = udao.login(username, password);
        } catch (MsgException e) {
            rd = req.getRequestDispatcher(req.getContextPath() + "/msg/login.jsp");
            errMsg = e.getMessage();
            req.setAttribute("oldname", username);
            req.setAttribute("errMsg", errMsg);
            rd.forward(req, resp);
        }
        HttpSession session = req.getSession();
        session.setAttribute("loguser", u);
        resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp");
    }
}
