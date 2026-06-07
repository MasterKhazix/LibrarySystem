<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="header.jsp" %>

<div class="container">
    <h1 class="page-title">
        <c:choose>
            <c:when test="${not empty book}">编辑图书</c:when>
            <c:otherwise>添加新书</c:otherwise>
        </c:choose>
    </h1>

    <div class="card">
        <c:if test="${not empty requestScope.error}">
            <div class="alert alert-error">${requestScope.error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/book/${not empty book ? 'edit' : 'add'}" method="post">
            <c:if test="${not empty book}">
                <input type="hidden" name="id" value="${book.id}">
            </c:if>

            <div class="form-group">
                <label>书名 *</label>
                <input type="text" name="title" value="${book.title}" required autofocus>
            </div>
            <div class="form-group">
                <label>作者 *</label>
                <input type="text" name="author" value="${book.author}" required>
            </div>
            <div class="form-group">
                <label>ISBN</label>
                <input type="text" name="isbn" value="${book.isbn}">
            </div>
            <div class="form-group">
                <label>出版社</label>
                <input type="text" name="publisher" value="${book.publisher}">
            </div>
            <div class="form-group">
                <label>分类</label>
                <select name="category">
                    <option value="">-- 请选择 --</option>
                    <option value="计算机" ${book.category == '计算机' ? 'selected' : ''}>计算机</option>
                    <option value="文学" ${book.category == '文学' ? 'selected' : ''}>文学</option>
                    <option value="科幻" ${book.category == '科幻' ? 'selected' : ''}>科幻</option>
                    <option value="历史" ${book.category == '历史' ? 'selected' : ''}>历史</option>
                    <option value="哲学" ${book.category == '哲学' ? 'selected' : ''}>哲学</option>
                    <option value="经济" ${book.category == '经济' ? 'selected' : ''}>经济</option>
                    <option value="外语" ${book.category == '外语' ? 'selected' : ''}>外语</option>
                    <option value="艺术" ${book.category == '艺术' ? 'selected' : ''}>艺术</option>
                    <option value="其他" ${book.category == '其他' ? 'selected' : ''}>其他</option>
                </select>
            </div>
            <div class="form-group">
                <label>简介</label>
                <textarea name="description">${book.description}</textarea>
            </div>
            <div class="form-group">
                <label>总册数 *</label>
                <input type="number" name="totalCopies" value="${not empty book ? book.totalCopies : 1}" min="1" required>
            </div>
            <div class="form-group">
                <label>可借册数</label>
                <input type="number" name="availableCopies" value="${not empty book ? book.availableCopies : 1}" min="0">
            </div>
            <div class="form-group">
                <label>馆藏位置</label>
                <input type="text" name="location" value="${book.location}" placeholder="如 A-01-01">
            </div>

            <button type="submit" class="btn btn-primary">
                <c:choose>
                    <c:when test="${not empty book}">保存修改</c:when>
                    <c:otherwise>添加图书</c:otherwise>
                </c:choose>
            </button>
            <a href="${pageContext.request.contextPath}/book/list" class="btn" style="background:#95a5a6;color:#fff;">取消</a>
        </form>
    </div>
</div>

<%@ include file="footer.jsp" %>
