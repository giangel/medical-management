<%-- File: WebContent/views/admin/departments.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Departments - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <h1>Departments</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
        </c:if>

        <div class="card" style="max-width: 640px; margin-bottom: var(--space-5);">
            <h2 style="font-size: var(--text-base); margin-bottom: var(--space-3);">Add Department</h2>
            <form action="${pageContext.request.contextPath}/views/admin/departments" method="post" style="display:flex; gap: var(--space-2); flex-wrap:wrap; align-items:flex-end;">
                <input type="hidden" name="action" value="create">
                <div class="field" style="margin-bottom:0; flex:1 1 160px;">
                    <input type="text" name="departmentName" placeholder="Department name" required>
                </div>
                <div class="field" style="margin-bottom:0; flex:2 1 220px;">
                    <input type="text" name="description" placeholder="Description">
                </div>
                <button type="submit" class="btn btn--primary">Add</button>
            </form>
        </div>

        <table class="data-table">
            <tr><th>Name</th><th>Description</th><th>Status</th><th>Action</th></tr>
            <c:forEach var="dep" items="${departments}">
                <tr>
                    <td><c:out value="${dep.departmentName}"/></td>
                    <td><c:out value="${dep.description}"/></td>
                    <td>
                        <span class="status-pill ${dep.active ? 'status-confirmed' : 'status-cancelled'}">
                            <c:out value="${dep.active ? 'Active' : 'Inactive'}"/>
                        </span>
                    </td>
                    <td>
                        <c:if test="${dep.active}">
                            <form action="${pageContext.request.contextPath}/views/admin/departments" method="post" data-confirm="Deactivate this department?">
                                <input type="hidden" name="action" value="deactivate">
                                <input type="hidden" name="departmentId" value="${dep.departmentId}">
                                <button type="submit" class="btn btn--secondary btn--sm">Deactivate</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty departments}">
                <tr><td colspan="4" style="text-align:center; color: var(--color-ink-muted);">No departments yet.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>