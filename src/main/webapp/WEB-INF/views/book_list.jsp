<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="page-title">图书列表</h1>

    <div class="card">
        <form action="${pageContext.request.contextPath}/book/list" method="get" class="search-box">
            <input type="text" name="keyword" placeholder="搜书名 / 作者 / ISBN..." value="${keyword}">
            <button type="submit" class="btn btn-primary">搜索</button>
            <c:if test="${not empty keyword}">
                <a href="${pageContext.request.contextPath}/book/list" class="btn btn-warning btn-sm">清除</a>
            </c:if>
        </form>
    </div>

    <div class="card">
        <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:12px;">
            <span>共 <strong>${fn:length(bookList)}</strong> 本</span>
            <c:if test="${sessionScope.user.role == 'admin'}">
                <a href="${pageContext.request.contextPath}/book/add" class="btn btn-success btn-sm">添加新书</a>
            </c:if>
        </div>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error">${sessionScope.error}</div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>书名</th>
                <th>作者</th>
                <th>ISBN</th>
                <th>分类</th>
                <th>可借 / 总数</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bookList}" var="book" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <td><strong>${book.title}</strong></td>
                    <td>${book.author}</td>
                    <td>${book.isbn}</td>
                    <td>${book.category}</td>
                    <td>
                        <c:choose>
                            <c:when test="${book.availableCopies > 0}">
                                <span style="color:#27ae60;font-weight:bold;">${book.availableCopies}</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color:#e74c3c;">0</span>
                            </c:otherwise>
                        </c:choose>
                        / ${book.totalCopies}
                    </td>
                    <td class="action-links">
                        <c:choose>
                            <c:when test="${sessionScope.user.role == 'admin'}">
                                <a href="${pageContext.request.contextPath}/book/edit?id=${book.id}" class="btn btn-primary btn-sm">编辑</a>
                                <a href="${pageContext.request.contextPath}/book/delete?id=${book.id}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('确定删除《${book.title}》吗？')">删除</a>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${book.availableCopies > 0}">
                                        <a href="${pageContext.request.contextPath}/borrow/do?bookId=${book.id}"
                                           class="btn btn-success btn-sm">借阅</a>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="btn btn-disabled btn-sm">已借完</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty bookList}">
                <tr>
                    <td colspan="7" style="text-align:center;color:#999;padding:24px;">
                        <c:choose>
                            <c:when test="${not empty keyword}">没有找到匹配的图书</c:when>
                            <c:otherwise>暂无图书，请管理员添加</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="footer.jsp" %>
