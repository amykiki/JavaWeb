<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.util.List" %>
<%@ page import="msg.manage.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
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
<%
        List<String> infos = (List<String>) request.getAttribute("info");
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

<%
    Connection conn = DBUtil.getConn();
    Statement st = conn.createStatement();
    ResultSet rs = st.executeQuery("select * from t_msg");

    while (rs.next()) {
        out.println(rs.getString("title") + "<br/>");
        out.println(rs.getString("content"));
    }
%>
<form method="post" action="first.jsp">
    name:<input type="text" name="name"><br/>
    Gender:<br/>
    男：<input type="radio" name="gender" value="男" checked="checked">
    女：<input type="radio" name="gender" value="female">
    <br/>
    喜欢的颜色:<br/>
    红：<input type="checkbox" name="color" value="红">
    蓝：<input type="checkbox" name="color" value="蓝">
    黄：<input type="checkbox" name="color" value="黄">
    <br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
