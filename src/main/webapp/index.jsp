<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎 - 图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">
        <a href="${pageContext.request.contextPath}/">图书管理系统</a>
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
        <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
    </div>
</nav>

<div class="container">
    <div class="welcome">
        <h1>欢迎使用图书管理系统</h1>
        <p>基于 JSP + Servlet + JDBC + MySQL 的 MVC 项目</p>
        <div>
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-primary">立即登录</a>
            <a href="${pageContext.request.contextPath}/register.jsp" class="btn btn-success">注册账号</a>
        </div>
    </div>
</div>

<footer class="footer"><p>&copy; 2026 图书管理系统</p></footer>
</body>
</html>
