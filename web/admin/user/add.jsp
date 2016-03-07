<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="msg.manage.modal.User" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/7
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add User</title>
    <style type="text/css">
        body {
            text-align: center;
        }

        div {
            margin: 0 auto;
            width: 350px;
            clear: both;
            text-align: right;
        }

        input [type="text"] {
            width: 60%;
            clear: both;
        }

        #but1, #but2 {
            width: 20%;
        }

        label {
            width: 30%;
        }

        select {
            width: 47.5%;
            clear: both;
        }

    </style>
    <%
        Map<String, String> errMap = new HashMap<>();
        User u = new User();

    %>
</head>
<body>
<span style="color: red;font-weight: bold">
    <%
        if (request.getAttribute("errMsg") != null) {
            out.println(request.getAttribute("errMsg") + "<br/>");
        }

        if (request.getAttribute("errMap") != null) {
            errMap = (Map<String, String>) request.getAttribute("errMap");
        }

        if (request.getAttribute("user") != null) {
            u = (User) request.getAttribute("user");
        }
        if (u.getUsername() == null) {
            u.setUsername("");
        }
        if (u.getNickname() == null) {
            u.setNickname("");
        }
        Map<String, String> selOpt = new HashMap<>();
        selOpt.put("ADMIN", "");
        selOpt.put("NORMAL", "selected");
        if (u.getRole() != null) {
            String role = u.getRole().toString();
            if (role.equals("ADMIN")) {
                selOpt.put("ADMIN", "selected");
                selOpt.put("NORMAL", "");
            }
        }

    %>
</span>
<div align="center">
    <form method="post" action="/admin/user/control">
        <input type="hidden" name="action" value="add"/>
        <label>用户名:</label><input type="text" name="username"
                                  value="<%=u.getUsername()%>"/>
        <%
            if (errMap.get("username") != null) {
        %>
        <span style="color: red"><%=errMap.get("username")%></span>
        <%
            }
        %><br/>
        <label>密码:</label><input type="password" name="password"/>
        <%
            if (errMap.get("password") != null) {
        %>
        <span style="color: red"><%=errMap.get("password")%></span>
        <%
            }
        %><br/>
        <label>昵称:</label><input type="text" name="nickname"
                                 value="<%=u.getNickname()%>"/>
        <%
            if (errMap.get("nickname") != null) {
        %>
        <span style="color: red"><%=errMap.get("nickname")%></span>
        <%
            }
        %><br/>
        <label>权限:</label><select name="role">
        <option value="ADMIN" <%=selOpt.get("ADMIN")%>>管理员</option>
        <option value="NORMAL" <%=selOpt.get("NORMAL")%>>普通用户</option>
    </select><br/>
        <input id="but1" type="submit" value="提交"/>
        &nbsp;
        <input id="but2" type="reset" value="重置"/>
    </form>
</div>
</body>
</html>
