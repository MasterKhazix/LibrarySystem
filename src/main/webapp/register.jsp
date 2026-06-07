<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - 图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
    <div class="brand">
        <a href="${pageContext.request.contextPath}/">图书管理系统</a>
    </div>
    <div class="nav-links">
        <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
    </div>
</nav>

<div class="auth-container" style="max-width: 460px;">
    <div class="card">
        <h2>用户注册</h2>

        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-error">${requestScope.error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/register" method="post" onsubmit="return checkForm()">
            <div class="form-group">
                <label>用户名 *</label>
                <input type="text" name="username" id="username" placeholder="请输入用户名" required value="${param.username}">
            </div>
            <div class="form-group">
                <label>真实姓名 *</label>
                <input type="text" name="realName" id="realName" placeholder="请输入真实姓名" required value="${param.realName}">
            </div>
            <div class="form-group">
                <label>密码 *（至少6位）</label>
                <input type="password" name="password" id="password" placeholder="请输入密码" required>
            </div>
            <div class="form-group">
                <label>确认密码 *</label>
                <input type="password" name="confirmPassword" id="confirmPassword" placeholder="请再次输入密码" required>
            </div>
            <div class="form-group">
                <label>手机号（可选）</label>
                <input type="text" name="phone" id="phone" placeholder="可选，填写时需为11位数字" maxlength="11" value="${param.phone}">
            </div>
            <div class="form-group">
                <label>邮箱（可选）</label>
                <input type="email" name="email" id="email" placeholder="可选，例如 user@example.com"
                       maxlength="100" value="${param.email}">
            </div>
            <button type="submit" class="btn btn-success">注册</button>
        </form>
        <div class="foot-link">
            已有账号？<a href="${pageContext.request.contextPath}/login.jsp">立即登录</a>
        </div>
    </div>
</div>

<footer class="footer"><p>&copy; 2026 图书管理系统</p></footer>

<script>
function checkForm() {
    var pwd = document.getElementById("password").value;
    var confirm = document.getElementById("confirmPassword").value;
    if (pwd.length < 6) {
        alert("密码至少6位");
        return false;
    }
    if (pwd !== confirm) {
        alert("两次密码不一致");
        return false;
    }
    var phone = document.getElementById("phone").value.trim();
    if (phone && !/^\d{11}$/.test(phone)) {
        alert("手机号填写时必须是11位数字");
        return false;
    }
    var email = document.getElementById("email").value.trim();
    if (email && !/^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(email)) {
        alert("邮箱格式不正确，例如 user@example.com");
        return false;
    }
    return true;
}
</script>
</body>
</html>
