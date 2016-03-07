<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/2/26
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="pg" uri="http://jsptags.com/tags/navigation/pager" %>
<html>
<head>
    <title>first JSP</title>
</head>
<body>
<%=application.getRealPath("/")%>
<h1>test</h1>
<a href="<%=request.getContextPath()%>/index.jsp">test link</a>
<%=request.getContextPath()%>
<br/>
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
    Map<String, Integer> stu = (Map) application.getAttribute("student");
    String score = "No Score";
    if (stu != null) {
        score = "Score = " + stu.get("Amy");
    } else {

    }

%>
<%=score%>
<br/>
<%!
    public int count;

    public String info() {
        return "hello";
    }
%>
count = <%=count++%><br/>
<%
    out.println(info());
%>

<!-- 下面输出表单域值-->
<%
    String name = request.getParameter("name");
    String gender = request.getParameter("gender");
    String[] colors = request.getParameterValues("color");
%>
<br/>
你的名字：<%=name%><br/>
你的性别：<%=gender%><br/>
你的颜色：
<%
    for (String color : colors) {
        out.print(color + ", ");
    }
%>
<br/>
<%
//    List<String> infos = (List<String>) request.getAttribute("info");
    List<String> infos = (List<String>) session.getAttribute("info");
    if (infos != null) {
        for (String info : infos) {
            out.println(info);
        }
    } else {
%>
<h2><font color="red">没有info信息</font></h2>
<%
    }
%>
<pg:pager maxPageItems="15" items="100">
    <pg:first>
        <a href="<%=pageUrl%>">首页</a>
    </pg:first>
    <pg:pages>
        <a href="<%=pageUrl%>"><%=pageNumber%>
        </a>
    </pg:pages>
    <pg:last>
        <a href="<%=pageUrl%>">尾页</a>
    </pg:last>
</pg:pager>
</body>
</html>