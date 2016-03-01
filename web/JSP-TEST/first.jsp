<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/2/26
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>first JSP</title>
</head>
<body>
<%=application.getRealPath("/")%>
<h1>test</h1>
<a href="<%=request.getContextPath()%>/index.jsp">test link</a>
<%=request.getContextPath()%>
<br>
<%--<%@include file="/inc/top1.jsp"%>--%>

<jsp:include page="/inc/top1.jsp"/>
<br>
<%
    String str = "world";
%>
<%=str%>
<br>
<%
//    Map<String, Integer> stu = (Map)pageContext.getAttribute("student");
//    Map<String, Integer> stu = (Map)request.getAttribute("student");
//    Map<String, Integer> stu = (Map)session.getAttribute("student");
    Map<String, Integer> stu = (Map)application.getAttribute("student");
    String score = "No Score";
    if (stu != null) {
        score = "Score = " + stu.get("Amy");
    } else {

    }

%>
<%=score%>
</body>
</html>