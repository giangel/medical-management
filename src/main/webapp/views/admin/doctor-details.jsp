<%-- File: WebContent/views/admin/doctor-details.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Details - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/admin/doctors" style="font-size: var(--text-sm);">&larr; Back to doctors</a>

        <div class="grid grid--2" style="margin-top: var(--space-4); align-items:start;">
            <div class="card">
                <h1 style="font-size: var(--text-xl); margin-bottom: var(--space-1);">Dr. <c:out value="${doctor.fullName}"/></h1>
                <p style="color: var(--color-primary-dark); font-weight:600;"><c:out value="${doctor.specialtyName}"/>, <c:out value="${doctor.departmentName}"/></p>
                <p style="color: var(--color-ink-muted); margin-bottom: var(--space-1);">
                    <c:out value="${doctor.email}"/> &middot; <c:out value="${doctor.phoneNumber}"/>
                </p>
                <p style="color: var(--color-ink-muted); margin-bottom: var(--space-3);">
                    License <c:out value="${doctor.licenseNumber}"/> &middot; <c:out value="${doctor.yearsOfExperience}"/> years experience
                </p>
                <p><c:out value="${doctor.biography}"/></p>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Appointments</h2>
                <c:forEach var="a" items="${appointments}">
                    <div style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border);">
                        <div>
                            <p style="margin:0; font-size: var(--text-sm);"><c:out value="${a.appointmentDate}"/></p>
                            <p style="margin:0; font-size: var(--text-xs); color: var(--color-ink-muted);">with <c:out value="${a.patientFullName}"/></p>
                        </div>
                        <span class="status-pill status-${fn:toLowerCase(a.status)}"><c:out value="${a.status}"/></span>
                    </div>
                </c:forEach>
                <c:if test="${empty appointments}">
                    <p style="color: var(--color-ink-muted);">No appointments on record.</p>
                </c:if>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>