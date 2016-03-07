<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/4
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>getCookie</title>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    for (Cookie c : cookies) {
%>
<h3><%=c.getName()%>:<%=c.getValue()%><h3/>
<%
    }
%>
</body>
</html>
