<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">
        <a href="${pageContext.request.contextPath}/book/list">图书管理系统</a>
    </div>
    <div class="nav-links">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <span class="user-info">
                    <c:choose>
                        <c:when test="${sessionScope.user.role == 'admin'}">管理员</c:when>
                        <c:otherwise>用户</c:otherwise>
                    </c:choose>
                    ${sessionScope.user.realName}
                </span>
                <a href="${pageContext.request.contextPath}/book/list">图书列表</a>
                <c:if test="${sessionScope.user.role == 'admin'}">
                    <a href="${pageContext.request.contextPath}/user/list">用户管理</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/borrow/list">借阅记录</a>
                <a href="${pageContext.request.contextPath}/user/profile">个人信息</a>
                <a href="${pageContext.request.contextPath}/logout">退出</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
                <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
