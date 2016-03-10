<%@ page import="java.util.List" %>
<%@ page import="msg.manage.modal.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="msg.manage.dao.IUserDao" %>
<%@ page import="msg.manage.dao.DaoFactory" %>
<%@ page import="msg.manage.modal.Role" %>
<%@ page import="msg.manage.modal.Pager" %><%--
  Created by IntelliJ IDEA.
  User: Amysue
  Date: 2016/3/6
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Info List</title>
    <style type="text/css">
        ul.tsc_pagination {
            font: 14px "Tahoma";
            height: 100%;
            list-style-type: noe;
            margin: 4px 0;
            overflow: hidden;
            padding: 0;
            text-align: center;
        }

        ul.tsc_pagination li {
            /*float: left;*/
            display: inline-block;
            margin: 0 0 0 5px;
            padding: 0;
        }

        ul.tsc_pagination li:first-child {
            margin-left: 0;
        }

        ul.tsc_pagination li a {
            color: black;
            display: block;
            padding: 7px 10px;
            text-decoration: none;
        }

        ul.tsc_paginationA li a {
            border-radius: 3px;
            color: #ffffff;
        }

        ul.tsc_paginationA05 li a {
            background: rgba(0, 0, 0, 0) -moz-linear-gradient(center top, #87ab19, #699613) repeat scroll 0 0;
        }

        ul.tsc_paginationA05 li a:hover, ul.tsc_paginationA05 li a.current {
            background: #e7f2c7 none repeat scroll 0 0;
            color: #4f7119;
        }
    </style>
    <%
        int pageItems = Integer.parseInt(config.getInitParameter("pageItems"));
        int pageShow = Integer.parseInt(config.getInitParameter("pageShow"));
        List<User> users;
        Pager pager;
        int currentPage = 1;
        if (request.getParameter("toPage") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("toPage"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }
        if (currentPage < 1) {
            currentPage = 1;
        }
        int pageNums = 1;
        if (request.getAttribute("pager") == null) {
            IUserDao udao = DaoFactory.getUserDao();
            pager = udao.loadList(currentPage, pageItems);
        } else {
            pager = (Pager) request.getAttribute("pager");
        }
        users = pager.getUsers();
        currentPage = pager.getCurrentPage();
        pageNums = (pager.getItemCounts() + pageItems - 1) / pageItems;
        User luser = (User) session.getAttribute("loguser");
        String errMsg = (String) request.getAttribute("errMsg");

    %>
</head>
<body>
<jsp:include page="<%=request.getContextPath() + \"/inc/nav.jsp\"%>"/>
<%
    if (errMsg != null) {
%>
<span><%=errMsg%></span>
<%
    }
%>
<table align="center" border="1">
    <tr>
        <th>ID</th>
        <th>USERNAME</th>
        <th>PASSWORD</th>
        <th>NICKNAME</th>
        <th>ROLE</th>
        <th>STATUS</th>
        <%
            if (luser != null) {
                if (luser.getRole().equals(Role.ADMIN)) {
        %>
        <th colspan="2">ACTIONS</th>
        <%
        } else {
        %>
        <th>ACTION</th>
        <%
                }
            }
        %>
    </tr>
    <%
        for (User u : users) {
    %>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td><%=u.getUsername()%>
        </td>
        <td><%=u.getPassword()%>
        </td>
        <td><%=u.getNickname()%>
        </td>
        <td><%=u.getRole()%>
        </td>
        <td><%=u.getStatus()%>
        </td>
        <%
            if (luser != null) {
                if (luser.getRole().equals(Role.ADMIN)) {
        %>
        <td>
            <a href="<%=request.getContextPath()+ "/admin/user/edit.jsp?id=" + u.getId()%>">更新</a>
        </td>
        <%
            if (u.getId() == luser.getId()) {
        %>
        <td>&nbsp;</td>
        <%
        } else {
        %>
        <td>
            <a href="<%=request.getContextPath() + "/admin/user/control?action=delete&id=" + u.getId()%>">删除</a>
        </td>
        <%
            }
        } else {
            if (u.getId() == luser.getId()) {
        %>
        <td>
            <a href="<%=request.getContextPath()+ "/admin/user/edit.jsp?id=" + u.getId()%>">更新</a>
        </td>
        <%
        } else {
        %>
        <td>&nbsp;</td>
        <%
                    }
                }
            }
        %>
    </tr>
    <%
        }
    %>
</table>
<form action="/admin/user/control" method="get" >
    <input type="hidden" name="action" value="query">
    <div align="center">
        <span>当前第<%=currentPage%>页(<%=(currentPage - 1) * pageItems + 1%>-<%=(currentPage - 1) * pageItems + pageItems%>)，共<%=pageNums%>页</span>
        <br/>
        <input type="text" name="toPage" align="center">
        <input type="submit" name="jump" value="JumpTo">
    </div>
    <div align="center">
        <select name="searchKey">
            <option value="username">用户名</option>
            <option value="nickname">昵称</option>
        </select>
        <input type="text" name="searchValue">
        <input type="submit" name="search" value="Search">
    </div>
    <ul class="tsc_pagination tsc_paginationA tsc_paginationA05">
        <li><a href="list.jsp?toPage=1">首页</a></li>
        <%
            if (currentPage > 1) {
        %>
        <li><a href="list.jsp?toPage=<%=currentPage-1%>">前一页</a></li>
        <%
            }
        %>
        <%
            int iStart = currentPage - pageShow;
            int iEnd = currentPage + pageShow;
            if (pageNums <= (pageShow * 2 + 1)) {
                iStart = 1;
                iEnd = pageNums;
            } else {
                if (iStart < 1) {
                    iEnd = iEnd + 1 - iStart;
                    iStart = 1;
                }
                if (iEnd > pageNums) {
                    iStart -= (iEnd - pageNums);
                    iEnd = pageNums;
                }
            }

            int i = iStart;
            while (i <= iEnd) {
                if (i == currentPage) {
        %>
        <li><a href="#" class="current"><%=i%>
        </a></li>
        <%
        } else {
        %>
        <li><a href="list.jsp?toPage=<%=i%>"><%=i%>
        </a></li>
        <%
                }
                i++;
            }

        %>

        <%
            if (currentPage < pageNums) {
        %>
        <li><a href="list.jsp?toPage=<%=currentPage-1%>">下一页</a></li>
        <%
            }
        %>
        <li><a href="list.jsp?toPage=<%=pageNums%>">尾页</a></li>
    </ul>
</form>
</body>
</html>
