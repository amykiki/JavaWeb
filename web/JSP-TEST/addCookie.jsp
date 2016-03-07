<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/4
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>add cookie</title>
</head>
<body>
<%
    String name = request.getParameter("name1");
    Cookie c = new Cookie("username1", name);
    c.setMaxAge(1 * 3600);
    response.addCookie(c);
    name = request.getParameter("name2");
    c = new Cookie("username2", name);
    response.addCookie(c);
%>
<%=name%>

</body>
</html>
