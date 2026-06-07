<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="page-title">用户管理</h1>

    <div class="stats-row">
        <div class="stat-card">
            <div class="stat-number">${totalBooks}</div>
            <div class="stat-label">馆藏图书</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${totalUsers}</div>
            <div class="stat-label">系统用户</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${borrowedCount}</div>
            <div class="stat-label">当前借出</div>
        </div>
    </div>

    <div class="card">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>用户名</th>
                <th>真实姓名</th>
                <th>手机号</th>
                <th>邮箱</th>
                <th>角色</th>
                <th>注册时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="u" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td>${u.username}</td>
                    <td>${u.realName}</td>
                    <td>${u.phone}</td>
                    <td>${u.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${u.role == 'admin'}">
                                <span style="color:#e74c3c;font-weight:bold;">管理员</span>
                            </c:when>
                            <c:otherwise>普通用户</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${u.createTime}</td>
                    <td>
                        <c:if test="${u.id != sessionScope.user.id}">
                            <a href="${pageContext.request.contextPath}/user/delete?id=${u.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('确定删除用户 ${u.username} 吗？')">删除</a>
                        </c:if>
                        <c:if test="${u.id == sessionScope.user.id}">
                            <span style="color:#bbb;">当前用户</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty userList}">
                <tr>
                    <td colspan="8" style="text-align:center;color:#999;padding:24px;">暂无用户数据</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="footer.jsp" %>
