<%@ page import="msg.manage.modal.User" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/9
  Time: 15:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        #info_all {
            margin: 0 auto;
        }
        #info-form {
            text-align: center;
        }
        .info-d {
            text-align: center;
            margin: 4px auto;
        }
        .info-name {
            display: inline-block;
            width: 50%;
            text-align: right;
        }
        .info-value {
            display: inline-block;
            width: 50%;
            text-align: left;
        }

    </style>
    <title>User Info</title>
    <%
        User lu = (User) session.getAttribute("loguser");
    %>
</head>
<body>
<jsp:include page="<%=request.getContextPath() + \"/inc/nav.jsp\"%>"/>
<div id="info_all">
    <div class="info-d">
        <span class="info-name">用户名:</span><span class="info-value"><%=lu.getUsername()%></span>
    </div>
    <div class="info-d">
        <span class="info-name">密码:</span><span class="info-value"><%=lu.getPassword()%></span>
    </div>
    <div class="info-d">
        <span class="info-name">昵称:</span><span class="info-value"><%=lu.getNickname()%></span>
    </div>
    <div class="info-d">
        <span class="info-name">权限:</span><span class="info-value"><%=lu.getRole().toString()%></span>
    </div>
    <div class="info-d">
        <span class="info-name">状态:</span><span class="info-value"><%=lu.getStatus()%></span>
    </div>
    <form method="post" action="<%=request.getContextPath()+ "/admin/user/edit.jsp?"%>" id="info-form">
        <input type="hidden" name="id" value="<%=lu.getId()%>">
        <input type="submit" value="编辑用户">
    </form>
</div>
</body>
</html>
