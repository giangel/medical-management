<%-- File: WebContent/views/admin/doctors.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Doctors - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap: var(--space-3);">
            <h1 style="margin:0;">Doctors</h1>
            <a class="btn btn--primary" href="${pageContext.request.contextPath}/views/admin/doctors/add">Add New Doctor</a>
        </div>

        <table class="data-table" style="margin-top: var(--space-5);">
            <tr><th>Name</th><th>Department</th><th>Specialty</th><th>License</th><th>Action</th></tr>
            <c:forEach var="d" items="${doctors}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/views/admin/doctors/view?id=${d.doctorId}">Dr. <c:out value="${d.fullName}"/></a></td>
                    <td><c:out value="${d.departmentName}"/></td>
                    <td><c:out value="${d.specialtyName}"/></td>
                    <td><c:out value="${d.licenseNumber}"/></td>
                    <td style="display:flex; gap: var(--space-2);">
                        <a class="btn btn--secondary btn--sm" href="${pageContext.request.contextPath}/views/admin/doctors/edit?id=${d.doctorId}">Edit</a>
                        <form action="${pageContext.request.contextPath}/views/admin/doctors/toggle-status" method="post" data-confirm="Deactivate this doctor's account?">
                            <input type="hidden" name="userId" value="${d.userId}">
                            <input type="hidden" name="active" value="false">
                            <button type="submit" class="btn btn--danger btn--sm">Deactivate</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty doctors}">
                <tr><td colspan="5" style="text-align:center; color: var(--color-ink-muted);">No doctors found.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>