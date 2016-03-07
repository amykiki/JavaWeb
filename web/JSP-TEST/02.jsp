<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/4
  Time: 14:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test forward</title>
</head>
<body>
<%
    List<String> info = new ArrayList<>();
    info.add("11111111");
    info.add("22222222");
    info.add("33333333");
    request.setAttribute("info", info);
    session.setAttribute("info", info);
//    response.sendRedirect("01.jsp");
%>
<jsp:forward page="01.jsp"/>

</body>
</html>
