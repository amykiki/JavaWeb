<%@ page import="java.util.List" %>
<%@ page import="msg.manage.modal.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="msg.manage.dao.IUserDao" %>
<%@ page import="msg.manage.dao.DaoFactory" %><%--
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
    %>
</head>
<body>
<table align="center" border="1">
    <tr>
        <th>ID</th>
        <th>USERNAME</th>
        <th>PASSWORD</th>
        <th>NICKNAME</th>
        <th>ROLE</th>
        <th>STATUS</th>
    </tr>
    <%
        for (User u: users) {
    %>
    <tr>
        <td><%=u.getId()%></td>
        <td><%=u.getUsername()%></td>
        <td><%=u.getPassword()%></td>
        <td><%=u.getNickname()%></td>
        <td><%=u.getRole()%></td>
        <td><%=u.getStatus()%></td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
