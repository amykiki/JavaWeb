<%@ page import="msg.manage.modal.User" %>
<%@ page import="msg.manage.modal.Role" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/8
  Time: 19:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        #nav {
            width: 100%;
        }
        #headright {
            text-align: right;
        }
        ul {
            list-style-type: none;
            margin: 0 auto;
            padding: 0;
            overflow: hidden;
            text-align: center;
        }
        li {
            display: inline;
            margin: 0 4px;
        }
        a:link, a:visited {
            text-align: center;
            text-decoration: none;
        }

        li a:link, li a:visited {
            font-weight: bold;
            color: #ffffff;
            background-color: #98bf21;
            text-align: center;
            text-decoration: none;
        }

        li a:hover, li a:active{
            background-color: #7a991a;
        }
        hr {
            margin-top: 4px;
            clear: both;
        }
    </style>
</head>
<body>
<%
    User luser = (User) session.getAttribute("loguser");
    if (luser != null) {
        %>
<div id="nav">
    <div id="headright">
        <span>欢迎[<a href="#"><%=luser.getNickname()%>]</a> <a href="<%=request.getContextPath()+ "/msg/login.jsp?logout=true"%>">注销</a></span>
    </div>
    <ul>
        <li><a href="<%=request.getContextPath()+ "/admin/user/list.jsp"%>">用户列表</a></li>
        <%
            if (luser.getRole().equals(Role.ADMIN)) {
                %>
        <li><a href="<%=request.getContextPath()+ "/admin/user/add.jsp"%>">添加用户</a></li>
        <%
            }
        %>
        <li><a href="#">修改用户</a></li>
        <li><a href="#">文章列表</a></li>
    </ul>
    <hr/>
</div>
<%
    }
%>
</body>
</html>
