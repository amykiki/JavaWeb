package servlets;

import msg.manage.dao.DaoFactory;
import msg.manage.dao.IUserDao;
import msg.manage.modal.Pager;
import msg.manage.modal.Role;
import msg.manage.modal.Status;
import msg.manage.modal.User;
import msg.manage.util.MsgException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        User              lu     = (User) req.getSession().getAttribute("loguser");
        if (lu == null) {
            resp.sendRedirect(req.getContextPath() + "/msg/login.jsp");
            return;
        }
        resp.setCharacterEncoding("UTF-8");

        if (action.equals("add")) {
            Map<String, String> errMap = new HashMap<>();
            User                u      = new User();
            if (checkAuth(lu)) {
                String getValue;
                getValue = req.getParameter("username");
                if (getValue != null && !getValue.equals("")) {
                    u.setUsername(req.getParameter("username"));
                } else {
                    errMap.put("username", "用户名不能为空");
                }
                getValue = req.getParameter("password");
                if (getValue != null && !getValue.equals("")) {
                    u.setPassword(req.getParameter("password"));
                } else {
                    errMap.put("password", "密码不能为空");
                }
                getValue = req.getParameter("nickname");
                if (getValue != null && !getValue.equals("")) {
                    u.setNickname(req.getParameter("nickname"));
                } else {
                    errMap.put("nickname", "昵称不能为空");
                }
                if (req.getParameter("role") != null) {
                    try {
                        u.setRole(Role.valueOf(req.getParameter("role")));
                    } catch (IllegalArgumentException e) {
                        errMsg = "权限不正确";
                        showAlertMsg(errMsg, req.getContextPath() + "/admin/user/list.jsp", resp);
                        return;
                    }
                }
                req.setAttribute("user", u);
                if (!errMap.isEmpty()) {
                    rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/add.jsp");
                    req.setAttribute("errMap", errMap);
                    rd.forward(req, resp);
                    return;
                }
            } else {
                errMsg = "没有权限添加用户";
                showAlertMsg(errMsg, req.getContextPath() + "/admin/user/list.jsp", resp);
                return;
            }
            try {
                udao.add(u);
                resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp");
                return;
            } catch (MsgException e) {
                rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/add.jsp");
                errMsg = e.getMessage();
                req.setAttribute("errMsg", errMsg);
                rd.forward(req, resp);
            }
        } else if (action.equals("delete")) {
            int id = checkID(req);
            if (id > 0 && checkAuth(lu) && lu.getId() != id) {
                try {
                    udao.delete(id);
                    resp.sendRedirect(req.getContextPath() + "/admin/user/list.jsp");
                } catch (MsgException e) {
                    rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/list.jsp");
                    errMsg = e.getMessage();
                    req.setAttribute("errMsg", errMsg);
                    rd.forward(req, resp);
                }
            } else {
                if (id > 0) {
                    errMsg = "没有权限删除用户";
                } else {
                    errMsg = "ID不正确";
                }
                showAlertMsg(errMsg, req.getContextPath() + "/admin/user/list.jsp", resp);
                return;
            }

        } else if (action.equals("update")) {
            int id = checkID(req);
            if (id > 0 && (checkAuth(lu) || lu.getId() == id)) {
                User u = new User();
                u.setId(id);
                String value;
                value = req.getParameter("password");
                if (value != null && !value.equals("")) {
                    u.setPassword(value);
                }
                value = req.getParameter("nickname");
                if (value != null && !value.equals("")) {
                    u.setNickname(value);
                }
                if (lu.getId() != id) {
                    if (req.getParameter("role") != null) {
                        try {
                            System.out.println(req.getParameter("role"));
                            u.setRole(Role.valueOf(req.getParameter("role")));
                        } catch (IllegalArgumentException e) {
                            errMsg = "权限不正确";
                            showAlertMsg(errMsg, req.getContextPath() + "/admin/user/edit.jsp?id=" + id, resp);
                            return;
                        }
                    }
                    if (req.getParameter("status") != null) {
                        try {
                            u.setStatus(Status.valueOf(req.getParameter("status")));
                        } catch (IllegalArgumentException e) {
                            errMsg = "状态不正确";
                            showAlertMsg(errMsg, req.getContextPath() + "/admin/user/edit.jsp?id=" + id, resp);
                            return;
                        }
                    }
                }
                try {
                    udao.update(u);
                    resp.sendRedirect(req.getContextPath() + "/admin/user/edit.jsp?id=" + id);
                } catch (MsgException e) {
                    errMsg = "修改用户失败";
                    showAlertMsg(errMsg, req.getContextPath() + "/admin/user/edit.jsp?id=" + id, resp);
                    return;
                }

            } else {
                if (id > 0) {
                    errMsg = "没有权限修改用户";
                } else {
                    errMsg = "ID不正确";
                }
                showAlertMsg(errMsg, req.getContextPath() + "/admin/user/list.jsp", resp);
                return;
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
        } else if (action.equals("query")) {
            rd = req.getRequestDispatcher(req.getContextPath() + "/admin/user/list.jsp");
            int pageItems = 0;
            try {
                pageItems = Integer.parseInt(req.getParameter("pageItems"));
            } catch (NumberFormatException e) {
                errMsg = "No Page Items";
                req.setAttribute("errMsg", errMsg);
            }
            int    toPage   = 1;
            String username = "";
            String nickname = "";
            String role     = "";
            String status   = "";
            int rvalue = -1;
            int svalue = -1;
            if (req.getParameter("toPage") != null) {
                try {
                    toPage = Integer.parseInt(req.getParameter("toPage"));
                } catch (NumberFormatException e) {
                    toPage = 1;
                }
            }
            try {
                username = req.getParameter("username").trim();
            } catch (NullPointerException e) {
                username = "";
            }

            try {
                nickname = req.getParameter("nickname").trim();
            } catch (NullPointerException e) {
                nickname = "";
            }
            if (req.getParameter("role") != null) {
                try {
                    Role r1 = Role.valueOf(req.getParameter("role"));
                    role = r1.toString();
                    rvalue = (r1.getCode() + 1) % 2;
                } catch (IllegalArgumentException e) {
                    role = "";
                    rvalue = -1;
                }
            }

            if (req.getParameter("status") != null) {
                try {
                    Status s1 = Status.valueOf(req.getParameter("status"));
                    status = s1.toString();
                    svalue = (s1.getCode() + 1)%2;
                } catch (IllegalArgumentException e) {
                    status = "";
                    svalue = -1;
                }
            }
            req.setAttribute("param", "true");
            req.setAttribute("toPage", toPage);
            req.setAttribute("username", username);
            req.setAttribute("nickname", nickname);
            req.setAttribute("role", role);
            req.setAttribute("status", status);
            if (errMsg.equals("")) {
                Pager pager = udao.loadList(toPage, pageItems, username, nickname, rvalue, svalue);
                req.setAttribute("pager", pager);
            }
            rd.forward(req, resp);
            return;
        }

    }

    private boolean checkAuth(User user) {
        if (user.getRole().equals(Role.ADMIN) && user.getStatus().equals(Status.INUSE)) {
            return true;
        }
        return false;
    }

    private void showAlertMsg(String errMsg, String href, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            out.println("<body onload=\"timer=setTimeout(function(){ window.location='" + href + "';}, 3000)\">");
            out.println("<p>" + errMsg + "</p>");
            out.println("</body>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int checkID(HttpServletRequest req) {
        int id = 0;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            id = -1;
        }
        return id;
    }
}
