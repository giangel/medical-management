<%-- File: WebContent/views/patient/appointment-confirmation.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment Confirmed - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band" style="min-height: 55vh; display:flex; align-items:center;">
    <div class="container">
        <div class="card" style="max-width: 520px; margin: 0 auto; text-align:center;">
            <div style="width:56px; height:56px; border-radius:50%; background: var(--color-primary-light); color: var(--color-primary-dark); display:flex; align-items:center; justify-content:center; font-size: var(--text-2xl); margin: 0 auto var(--space-4);">&#10003;</div>
            <h1 style="font-size: var(--text-2xl);">Request Sent</h1>
            <p style="color: var(--color-ink-muted);">
                Your appointment has been submitted with status
                <span class="status-pill status-${fn:toLowerCase(appointment.status)}"><c:out value="${appointment.status}"/></span>
            </p>

            <div style="text-align:left; background: var(--color-surface-alt); border-radius: var(--radius-md); padding: var(--space-4); margin: var(--space-5) 0;">
                <p style="margin: 0 0 var(--space-1);"><strong>Doctor:</strong> Dr. <c:out value="${appointment.doctorFullName}"/></p>
                <p style="margin: 0 0 var(--space-1);"><strong>Specialty:</strong> <c:out value="${appointment.specialtyName}"/></p>
                <p style="margin: 0 0 var(--space-1);"><strong>Date and Time:</strong> <c:out value="${appointment.appointmentDate}"/> at <c:out value="${appointment.startTime}"/></p>
                <p style="margin: 0;"><strong>Reason:</strong> <c:out value="${appointment.reason}"/></p>
            </div>

            <a class="btn btn--primary" href="${pageContext.request.contextPath}/views/patient/appointments">View My Appointments</a>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>