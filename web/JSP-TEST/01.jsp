<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/2/29
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>forward scope test</title>
</head>
<body>
<%
    Map<String, Integer> stu = new TreeMap<>();
    stu.put("Amy", 100);
//    pageContext.setAttribute("student", stu);
//    request.setAttribute("student", stu);
//    session.setAttribute("student", stu);
    application.setAttribute("student", stu);
    int score = stu.get("Amy");
%>
<%=score%>
<br>
<a href="first.jsp">this is origin page</a>
<%--<jsp:forward page="first.jsp"/>--%>
</body>
</html>
