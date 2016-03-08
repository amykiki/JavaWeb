<%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/8
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            font-size: 12px;
            color: #666666;
            background: #ffffff;
            text-align: center;
        }
        * {
            margin: 0;
            padding: 0;
        }
        #formwrapper {
            width: 450px;
            margin: 15px auto;
            padding: 20px;
            text-align: left;
            border: 1px #A4CDF2;
        }
        fieldset {
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #A4CDF2;
            background: #ffffff;
        }

        fieldset legend{
            color: #1E7ACE;
            font-weight: bold;
            padding: 3px 8px;
            border: 1px solid #A4CDF2;
            background: #ffffff;
        }
        fieldset label{
            float: left;
            width: 160px;
            text-align: right;
            margin-right: 3px;
            vertical-align: middle;
        }
        fieldset input[type=text] {
            vertical-align: middle;
        }
        fieldset div {
            clear: left;
            margin: 4px 0;
            vertical-align: middle;
        }
        #button {
            width: 180px;
            margin: 0 auto;
            margin-top: 8px;
        }
        #button input{
            background: #ffffff;
            border: 1px solid #A4CDF2;
            color: #1E7ACE;
            width: 60px;
            margin: auto 6px;
        }
        #but1{
            float: left;
        }
        #but2 {
            float: right;
        }
        #clear-float{
            clear: both;
        }
        #error{
            color: red;
            font-weight: bold;
            text-align: center;
        }
    </style>
    <%
        String errMsg = (String)request.getAttribute("errMsg");
        String oldname = (String) request.getAttribute("oldname");
        if (oldname == null) {
            oldname = "";
        }
        String logout = request.getParameter("logout");
        if (logout != null && logout.equals("true")) {
            request.getSession().setAttribute("loguser", null);
            session.invalidate();
        }
    %>
</head>
<body>
<div id="formwrapper">
    <form action="/msg/loginProcess" method="post" id="Login">
        <fieldset>
            <legend>用户登录</legend>
            <div>
                <label for="name">用户名</label>
                <input type="text" name="username" id="name" size="16" maxlength="30" value="<%=oldname%>"/><br/>
            </div>
            <div>
                <label for="password">密码</label>
                <input type="password" name="password" id="password" size="16" maxlength="30"/><br/>
            </div>
            <div id="button">
                <input id="but1" type="submit" value="提交"/><input id = "but2" type="reset" value="重置"/>
                <div id="clear-float"></div>
            </div>
        </fieldset>
        <div id="error">
            <%
                if (errMsg != null) {
                    out.print(errMsg);
                }
            %>
        </div>
    </form>
</div>


</body>
</html>
