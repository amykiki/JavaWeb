<%@ page import="msg.manage.modal.User" %>
<%@ page import="msg.manage.dao.IUserDao" %>
<%@ page import="msg.manage.dao.DaoFactory" %>
<%@ page import="msg.manage.modal.Role" %>
<%@ page import="msg.manage.modal.Status" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/9
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
    <style type="text/css">
        #edit-all {
            text-align: center;
        }
        .edit-d {
            text-align: center;
            margin: 4px auto;
        }
        .edit-name {
            display: inline-block;
            width: 50%;
            text-align: right;
            float: left;
            clear: left;
            margin-right: 3px;
        }
        .edit-value {
            text-align: left;
            display: inline-block;
            float: left;
        }
        .edit-but {
            text-align: center;
            clear: both;
        }

    </style>
    <%
        int id = Integer.parseInt(request.getParameter("id"));
        IUserDao udao = DaoFactory.getUserDao();
        User u = udao.load(id);
        User lu = (User) session.getAttribute("loguser");
        Map<String, String> selopt = new HashMap<>();
        if (u.getRole().equals(Role.ADMIN)) {
            selopt.put(Role.ADMIN.toString(), "selected");
            selopt.put(Role.NORMAL.toString(), "");
        } else {
            selopt.put(Role.ADMIN.toString(), "");
            selopt.put(Role.NORMAL.toString(), "selected");
        }

        if (u.getStatus().equals(Status.INUSE)) {
            selopt.put(Status.INUSE.toString(), "selected");
            selopt.put(Status.OFFUSE.toString(), "");
        } else {
            selopt.put(Status.INUSE.toString(), "");
            selopt.put(Status.OFFUSE.toString(), "selected");
        }
    %>
</head>
<body>
<jsp:include page="<%=request.getContextPath() + \"/inc/nav.jsp\"%>"/>
<form id="edit-all" method="post" action="/admin/user/control">
    <div class="edit-d">
        <span class="edit-name">用户名:</span><span class="edit-value"><%=u.getUsername()%></span>
    </div>
    <div class="edit-d">
        <span class="edit-name">密码:</span><input class="edit-value" type="password" name="password" value="<%=u.getPassword()%>" />
    </div>
    <div class="edit-d">
        <span class="edit-name">昵称:</span><input class="edit-value" type="text" name="nickname" value="<%=u.getNickname()%>"/>
    </div>
    <div class="edit-d">
        <span class="edit-name">权限:</span>
        <%
            if (lu.getRole().equals(Role.ADMIN) && lu.getId() != u.getId()) {
        %>
        <select class="edit-value" name="role">
            <option value="<%=Role.ADMIN.toString()%>" <%=selopt.get(Role.ADMIN.toString())%>>管理员</option>
            <option value="<%=Role.NORMAL.toString()%>" <%=selopt.get(Role.NORMAL.toString())%>>普通用户</option>
        </select>
        <%
        }
        else {
        %>
        <span class="edit-value"><%=u.getRole().toString()%></span>
        <%
            }
        %>
    </div>
    <div class="edit-d">
        <span class="edit-name">状态:</span>
        <%
            if (lu.getRole().equals(Role.ADMIN) && lu.getId() != u.getId()) {
        %>
        <select class="edit-value" name="status">
            <option value="<%=Status.INUSE.toString()%>" <%=selopt.get(Status.INUSE.toString())%>>有效用户</option>
            <option value="<%=Status.OFFUSE.toString()%>" <%=selopt.get(Status.OFFUSE.toString())%>>暂停用户</option>
        </select>
        <%
            }
            else {
        %>
        <span class="edit-value"><%=u.getStatus().toString()%></span>
        <%
            }
        %>
    </div>
    <div class="edit-but"></div>
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%=id%>">
    <input class="edit-but" type="submit" value="提交">
    <input class="edit-but" type="reset" value="重置">
</form>
</body>
</html>
