<%@ page import="java.util.List" %>
<%@ page import="msg.manage.modal.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="msg.manage.dao.IUserDao" %>
<%@ page import="msg.manage.dao.DaoFactory" %>
<%@ page import="msg.manage.modal.Role" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/6
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info List</title>
    <%
        List<User> users = new ArrayList<>();
        if (request.getAttribute("users") == null) {
            IUserDao udao = DaoFactory.getUserDao();
            users = udao.loadList();
        } else {
            users = (List<User>) request.getAttribute("users");
        }
        User luser = (User) session.getAttribute("loguser");
        String errMsg = (String)request.getAttribute("errMsg");

    %>
</head>
<body>
<jsp:include page="<%=request.getContextPath() + \"/inc/nav.jsp\"%>"/>
<%
    if (errMsg != null) {
%>
<span><%=errMsg%></span>
<%
    }
%>
<table align="center" border="1">
    <tr>
        <th>ID</th>
        <th>USERNAME</th>
        <th>PASSWORD</th>
        <th>NICKNAME</th>
        <th>ROLE</th>
        <th>STATUS</th>
        <%
            if (luser != null) {
                if (luser.getRole().equals(Role.ADMIN)) {
        %>
        <th colspan="2">ACTIONS</th>
        <%
                } else {
        %>
        <th>ACTION</th>
        <%
                }
            }
        %>
    </tr>
    <%
        for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td><%=u.getUsername()%>
        </td>
        <td><%=u.getPassword()%>
        </td>
        <td><%=u.getNickname()%>
        </td>
        <td><%=u.getRole()%>
        </td>
        <td><%=u.getStatus()%>
        </td>
        <%
            if (luser != null) {
                if (luser.getRole().equals(Role.ADMIN)) {
        %>
        <td><a href="<%=request.getContextPath()+ "/admin/user/edit.jsp?id=" + u.getId()%>">更新</a></td>
        <%
                    if (u.getId() == luser.getId()) {
        %>
        <td>&nbsp;</td>
        <%
                    } else {
        %>
        <td><a href="<%=request.getContextPath() + "/admin/user/control?action=delete&id=" + u.getId()%>">删除</a></td>
        <%
                    }
                } else {
                    if (u.getId() == luser.getId()) {
        %>
        <td><a href="<%=request.getContextPath()+ "/admin/user/edit.jsp?id=" + u.getId()%>">更新</a></td>
        <%
                    } else {
        %>
        <td>&nbsp;</td>
        <%
                    }
                }
            }
        %>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
