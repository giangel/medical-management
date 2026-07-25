<%-- File: WebContent/views/admin/patients.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Patients - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <h1>Patients</h1>
        <table class="data-table">
            <tr><th>Name</th><th>Email</th><th>Phone</th><th>Action</th></tr>
            <c:forEach var="p" items="${patients}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/views/admin/patients/view?id=${p.patientId}"><c:out value="${p.fullName}"/></a></td>
                    <td><c:out value="${p.email}"/></td>
                    <td><c:out value="${p.phoneNumber}"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/views/admin/patients" method="post" data-confirm="Deactivate this patient's account?">
                            <input type="hidden" name="userId" value="${p.userId}">
                            <input type="hidden" name="active" value="false">
                            <button type="submit" class="btn btn--secondary btn--sm">Deactivate</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty patients}">
                <tr><td colspan="4" style="text-align:center; color: var(--color-ink-muted);">No patients found.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>