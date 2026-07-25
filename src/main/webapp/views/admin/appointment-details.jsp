<%-- File: WebContent/views/admin/appointment-details.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment Details - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/admin/appointments" style="font-size: var(--text-sm);">&larr; Back to appointments</a>

        <div class="card" style="max-width: 560px; margin-top: var(--space-4);">
            <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom: var(--space-3);">
                <h1 style="font-size: var(--text-xl); margin:0;">Appointment #<c:out value="${appointment.appointmentId}"/></h1>
                <span class="status-pill status-${fn:toLowerCase(appointment.status)}"><c:out value="${appointment.status}"/></span>
            </div>
            <p style="margin-bottom: var(--space-1);"><strong>Patient:</strong> <c:out value="${appointment.patientFullName}"/> (<c:out value="${appointment.patientPhoneNumber}"/>)</p>
            <p style="margin-bottom: var(--space-1);"><strong>Doctor:</strong> Dr. <c:out value="${appointment.doctorFullName}"/> &middot; <c:out value="${appointment.specialtyName}"/></p>
            <p style="margin-bottom: var(--space-1);"><strong>Date:</strong> <c:out value="${appointment.appointmentDate}"/> at <c:out value="${appointment.startTime}"/></p>
            <p style="margin-bottom: var(--space-1);"><strong>Reason:</strong> <c:out value="${appointment.reason}"/></p>
            <c:if test="${not empty appointment.rejectionReason}">
                <p style="margin-bottom: var(--space-1);"><strong>Rejection Reason:</strong> <c:out value="${appointment.rejectionReason}"/></p>
            </c:if>
            <c:if test="${not empty appointment.cancellationReason}">
                <p style="margin-bottom: 0;"><strong>Cancellation Reason:</strong> <c:out value="${appointment.cancellationReason}"/></p>
            </c:if>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>