<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - 图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">
        <a href="${pageContext.request.contextPath}/">图书管理系统</a>
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
    </div>
</nav>

<div class="auth-container">
    <div class="card">
        <h2>用户登录</h2>

        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-error">${requestScope.error}</div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="alert alert-success">${requestScope.success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" name="username" placeholder="请输入用户名" required autofocus>
            </div>
            <div class="form-group">
                <label>密码</label>
                <input type="password" name="password" placeholder="请输入密码" required>
            </div>
            <button type="submit" class="btn btn-primary">登录</button>
        </form>
        <div class="foot-link">
            还没有账号？<a href="${pageContext.request.contextPath}/register.jsp">立即注册</a>
        </div>
    </div>
</div>

<footer class="footer"><p>&copy; 2026 图书管理系统</p></footer>
</body>
</html>
