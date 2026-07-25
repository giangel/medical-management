<%-- File: WebContent/views/admin/patient-details.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Patient Details - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/admin/patients" style="font-size: var(--text-sm);">&larr; Back to patients</a>

        <div class="grid grid--2" style="margin-top: var(--space-4); align-items:start;">
            <div class="card">
                <h1 style="font-size: var(--text-xl); margin-bottom: var(--space-1);"><c:out value="${patient.fullName}"/></h1>
                <p style="color: var(--color-ink-muted); margin-bottom: var(--space-3);">
                    <c:out value="${patient.email}"/> &middot; <c:out value="${patient.phoneNumber}"/>
                </p>
                <p style="margin-bottom: var(--space-1);"><strong>Address:</strong> <c:out value="${patient.address}"/></p>
                <p style="margin-bottom: var(--space-1);"><strong>Emergency Contact:</strong> <c:out value="${patient.emergencyContactName}"/> (<c:out value="${patient.emergencyContactPhone}"/>)</p>
                <p style="margin-bottom: var(--space-1);"><strong>Blood Group:</strong> <c:out value="${patient.bloodGroup}"/></p>
                <p style="margin-bottom: 0;"><strong>Allergies:</strong> <c:out value="${patient.allergies}"/></p>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Appointment History</h2>
                <c:forEach var="a" items="${appointments}">
                    <div style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border);">
                        <div>
                            <p style="margin:0; font-size: var(--text-sm);"><c:out value="${a.appointmentDate}"/></p>
                            <p style="margin:0; font-size: var(--text-xs); color: var(--color-ink-muted);">with Dr. <c:out value="${a.doctorFullName}"/></p>
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