<%-- File: WebContent/views/patient/appointments.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Appointments - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <h1>My Appointments</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
        </c:if>

        <h2 style="font-size: var(--text-lg); margin-top: var(--space-6);">Upcoming</h2>
        <c:forEach var="appt" items="${upcomingAppointments}">
            <div class="card" style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap: var(--space-3);">
                <div>
                    <p style="margin:0; font-weight:600;">Dr. <c:out value="${appt.doctorFullName}"/></p>
                    <p style="margin:0; font-size: var(--text-sm); color: var(--color-ink-muted);">
                        <c:out value="${appt.appointmentDate}"/> at <c:out value="${appt.startTime}"/> &middot; <c:out value="${appt.reason}"/>
                    </p>
                </div>
                <div style="display:flex; align-items:center; gap: var(--space-3);">
                    <span class="status-pill status-${fn:toLowerCase(appt.status)}"><c:out value="${appt.status}"/></span>
                    <a class="btn btn--secondary btn--sm" href="${pageContext.request.contextPath}/views/patient/appointments/reschedule?appointmentId=${appt.appointmentId}">Reschedule</a>
                    <form action="${pageContext.request.contextPath}/views/patient/appointments/cancel" method="post" data-confirm="Cancel this appointment?" style="display:inline;">
                        <input type="hidden" name="appointmentId" value="${appt.appointmentId}">
                        <input type="hidden" name="reason" value="Cancelled by patient">
                        <button type="submit" class="btn btn--danger btn--sm">Cancel</button>
                    </form>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty upcomingAppointments}">
            <div class="card" style="color: var(--color-ink-muted);">No upcoming appointments.</div>
        </c:if>

        <h2 style="font-size: var(--text-lg); margin-top: var(--space-8);">Past</h2>
        <c:forEach var="appt" items="${pastAppointments}">
            <div class="card" style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap: var(--space-3);">
                <div>
                    <p style="margin:0; font-weight:600;">Dr. <c:out value="${appt.doctorFullName}"/></p>
                    <p style="margin:0; font-size: var(--text-sm); color: var(--color-ink-muted);">
                        <c:out value="${appt.appointmentDate}"/> at <c:out value="${appt.startTime}"/>
                    </p>
                </div>
                <span class="status-pill status-${fn:toLowerCase(appt.status)}"><c:out value="${appt.status}"/></span>
            </div>
        </c:forEach>
        <c:if test="${empty pastAppointments}">
            <div class="card" style="color: var(--color-ink-muted);">No past appointments.</div>
        </c:if>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>