<%--
  Created by IntelliJ IDEA.
  User: LONG
  Date: 3/5/2025
  Time: 8:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    session.invalidate();
    response.sendRedirect("index.jsp"); // Chuyển hướng về trang chủ
%>
</body>
</html>
