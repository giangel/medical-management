<%-- File: WebContent/views/admin/specialties.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Specialties - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <h1>Specialties</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
        </c:if>

        <div class="card" style="max-width: 720px; margin-bottom: var(--space-5);">
            <h2 style="font-size: var(--text-base); margin-bottom: var(--space-3);">Add Specialty</h2>
            <form action="${pageContext.request.contextPath}/views/admin/specialties" method="post" style="display:flex; gap: var(--space-2); flex-wrap:wrap; align-items:flex-end;">
                <input type="hidden" name="action" value="create">
                <div class="field" style="margin-bottom:0; flex:1 1 160px;">
                    <input type="text" name="specialtyName" placeholder="Specialty name" required>
                </div>
                <div class="field" style="margin-bottom:0; flex:1 1 160px;">
                    <select name="departmentId">
                        <option value="">No department</option>
                        <c:forEach var="dep" items="${departments}">
                            <option value="${dep.departmentId}"><c:out value="${dep.departmentName}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="field" style="margin-bottom:0; flex:2 1 200px;">
                    <input type="text" name="description" placeholder="Description">
                </div>
                <button type="submit" class="btn btn--primary">Add</button>
            </form>
        </div>

        <table class="data-table">
            <tr><th>Name</th><th>Department</th><th>Status</th><th>Action</th></tr>
            <c:forEach var="spec" items="${specialties}">
                <tr>
                    <td><c:out value="${spec.specialtyName}"/></td>
                    <td><c:out value="${spec.departmentName}"/></td>
                    <td>
                        <span class="status-pill ${spec.active ? 'status-confirmed' : 'status-cancelled'}">
                            <c:out value="${spec.active ? 'Active' : 'Inactive'}"/>
                        </span>
                    </td>
                    <td>
                        <c:if test="${spec.active}">
                            <form action="${pageContext.request.contextPath}/views/admin/specialties" method="post" data-confirm="Deactivate this specialty?">
                                <input type="hidden" name="action" value="deactivate">
                                <input type="hidden" name="specialtyId" value="${spec.specialtyId}">
                                <button type="submit" class="btn btn--secondary btn--sm">Deactivate</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty specialties}">
                <tr><td colspan="4" style="text-align:center; color: var(--color-ink-muted);">No specialties yet.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>