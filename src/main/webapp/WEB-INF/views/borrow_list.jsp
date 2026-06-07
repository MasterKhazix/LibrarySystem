<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="page-title">借阅记录</h1>

    <div class="card">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <c:if test="${sessionScope.user.role == 'admin'}">
                    <th>借阅人</th>
                </c:if>
                <th>图书</th>
                <th>借阅日期</th>
                <th>应还日期</th>
                <th>归还日期</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${recordList}" var="r" varStatus="s">
                <tr>
                    <td>${s.count}</td>
                    <c:if test="${sessionScope.user.role == 'admin'}">
                        <td>${r.userName}</td>
                    </c:if>
                    <td>${r.bookTitle}</td>
                    <td>${r.borrowDate}</td>
                    <td>${r.dueDate}</td>
                    <td>${r.returnDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${r.status == 'borrowed'}">
                                <span class="tag tag-borrowed">借阅中</span>
                            </c:when>
                            <c:when test="${r.status == 'returned'}">
                                <span class="tag tag-returned">已归还</span>
                            </c:when>
                            <c:otherwise>
                                <span class="tag tag-overdue">已逾期</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${r.status == 'borrowed' && (sessionScope.user.id == r.userId || sessionScope.user.role == 'admin')}">
                            <a href="${pageContext.request.contextPath}/borrow/return?recordId=${r.id}"
                               class="btn btn-warning btn-sm"
                               onclick="return confirm('确认归还《${r.bookTitle}》吗？')">归还</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty recordList}">
                <tr>
                    <td colspan="${sessionScope.user.role == 'admin' ? 8 : 7}"
                        style="text-align:center;color:#999;padding:24px;">暂无借阅记录</td>
                </tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="footer.jsp" %>
