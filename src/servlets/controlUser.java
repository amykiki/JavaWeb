package servlets;

import msg.manage.dao.DaoFactory;
import msg.manage.dao.IUserDao;
import msg.manage.modal.Role;
import msg.manage.modal.User;
import msg.manage.util.MsgException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Amysue on 2016/3/6.
 */
public class controlUser extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserDao          udao   = DaoFactory.getUserDao();
        String            action = req.getParameter("action");
        String            errMsg = "";
        RequestDispatcher rd;
        ;
        if (action.equals("add")) {
            Map<String, String> errMap = new HashMap<>();
            User                u      = new User();

            if (!req.getParameter("username").equals("")) {
                u.setUsername(req.getParameter("username"));
            } else {
                errMap.put("username", "用户名不能为空");
            }
            if (!req.getParameter("password").equals("")) {
                u.setPassword(req.getParameter("password"));
            } else {
                errMap.put("password", "密码不能为空");
            }
            if (!req.getParameter("nickname").equals("")) {
                u.setNickname(req.getParameter("nickname"));
            } else {
                errMap.put("nickname", "昵称不能为空");
            }
            if (req.getParameter("role") != null) {
                u.setRole(Role.valueOf(req.getParameter("role")));
            }
            req.setAttribute("user", u);
            if (!errMap.isEmpty()) {
                rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/add.jsp");
                req.setAttribute("errMap", errMap);
                rd.forward(req, resp);
            }
            try {
                udao.add(u);
                resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp");
            } catch (MsgException e) {
                rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/add.jsp");
                errMsg = e.getMessage();
                req.setAttribute("errMsg", errMsg);
                rd.forward(req, resp);
            }
        } else if (action.equals("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                udao.delete(id);
                resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp");
            } catch (MsgException e) {
                rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/list.jsp");
                errMsg = e.getMessage();
                req.setAttribute("errMsg", errMsg);
                rd.forward(req, resp);
            }
        } else if (action.equals("load")) {
            int id = -1;
            try {
                id = Integer.parseInt(req.getParameter("id"));
                User u = udao.load(id);
            } catch (NumberFormatException e) {
                errMsg += "输入id必须为数字.";
            } catch (MsgException em) {
                errMsg += em.getMessage();
            }
        }

//        out.println("this page should not be viewed");
    }
}
