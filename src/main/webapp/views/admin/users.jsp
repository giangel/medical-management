<%-- File: WebContent/views/admin/users.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band band--tight band--alt">
    <div class="container">
        <h1 style="margin-bottom: var(--space-4);">All Users</h1>
        <form method="get" class="field" style="max-width: 240px; margin-bottom:0;">
            <select name="role" onchange="this.form.submit()">
                <option value="" ${empty roleFilter ? 'selected' : ''}>All Roles</option>
                <option value="ADMIN" ${roleFilter == 'ADMIN' ? 'selected' : ''}>Admin</option>
                <option value="DOCTOR" ${roleFilter == 'DOCTOR' ? 'selected' : ''}>Doctor</option>
                <option value="PATIENT" ${roleFilter == 'PATIENT' ? 'selected' : ''}>Patient</option>
            </select>
        </form>
    </div>
</section>

<section class="band">
    <div class="container">
        <table class="data-table">
            <tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th><th>Action</th></tr>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td><c:out value="${u.fullName}"/></td>
                    <td><c:out value="${u.email}"/></td>
                    <td><c:out value="${u.roleName}"/></td>
                    <td>
                        <span class="status-pill ${u.active ? 'status-confirmed' : 'status-cancelled'}">
                            <c:out value="${u.active ? 'Active' : 'Inactive'}"/>
                        </span>
                    </td>
                    <td>
                        <form action="${pageContext.request.contextPath}/views/admin/users" method="post" data-confirm="Change this account's status?">
                            <input type="hidden" name="userId" value="${u.userId}">
                            <input type="hidden" name="active" value="${!u.active}">
                            <button type="submit" class="btn btn--secondary btn--sm">
                                <c:out value="${u.active ? 'Deactivate' : 'Activate'}"/>
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty users}">
                <tr><td colspan="5" style="text-align:center; color: var(--color-ink-muted);">No users found.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>