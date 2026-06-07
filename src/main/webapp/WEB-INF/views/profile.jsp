<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="page-title">个人信息</h1>

    <div class="card" style="max-width:500px; margin:0 auto;">
        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-error">${requestScope.error}</div>
        </c:if>
        <c:if test="${not empty requestScope.success}">
            <div class="alert alert-success">${requestScope.success}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/user/editProfile" method="post">
            <div class="form-group">
                <label>用户名</label>
                <input type="text" value="${sessionScope.user.username}" disabled>
            </div>
            <div class="form-group">
                <label>角色</label>
                <input type="text" value="${sessionScope.user.role == 'admin' ? '管理员' : '普通用户'}" disabled>
            </div>
            <div class="form-group">
                <label>真实姓名 *</label>
                <input type="text" name="realName" value="${sessionScope.user.realName}" required>
            </div>
            <div class="form-group">
                <label>手机号</label>
                <input type="text" name="phone" value="${sessionScope.user.phone}" placeholder="11位手机号" maxlength="11">
            </div>
            <div class="form-group">
                <label>邮箱</label>
                <input type="email" name="email" value="${sessionScope.user.email}"
                       placeholder="例如 user@example.com" maxlength="100">
            </div>
            <hr style="margin:20px 0;border-color:#eee;">
            <div class="form-group">
                <label>新密码（留空则不修改，至少6位）</label>
                <input type="password" name="newPassword" placeholder="不修改可留空">
            </div>
            <button type="submit" class="btn btn-primary">保存修改</button>
            <a href="${pageContext.request.contextPath}/book/list" class="btn" style="background:#95a5a6;color:#fff;">返回</a>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
