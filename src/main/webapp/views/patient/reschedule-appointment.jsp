<%-- File: WebContent/views/patient/reschedule-appointment.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reschedule Appointment - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <div class="card" style="max-width: 520px; margin: 0 auto;">
            <h1 style="font-size: var(--text-2xl);">Reschedule Appointment</h1>
            <p style="color: var(--color-ink-muted);">
                Currently booked for <c:out value="${appointment.appointmentDate}"/> at <c:out value="${appointment.startTime}"/>
            </p>

            <c:if test="${not empty param.error}">
                <div class="alert alert--error"><c:out value="${param.error}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/views/patient/appointments/reschedule" method="get" class="field">
                <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                <label for="date">New Date</label>
                <input type="date" id="date" name="date" value="${selectedDate}" onchange="this.form.submit()">
            </form>

            <c:choose>
                <c:when test="${not empty availableSlots}">
                    <form action="${pageContext.request.contextPath}/views/patient/appointments/reschedule" method="post">
                        <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                        <input type="hidden" name="date" value="${selectedDate}">

                        <label style="font-size: var(--text-sm); font-weight:600; display:block; margin-bottom: var(--space-2);">Available Times</label>
                        <div style="display:flex; flex-wrap:wrap; gap: var(--space-2); margin-bottom: var(--space-4);">
                            <c:forEach var="slot" items="${availableSlots}">
                                <label style="border:1px solid var(--color-border-strong); border-radius: var(--radius-pill); padding: var(--space-2) var(--space-3); font-size: var(--text-sm); cursor:pointer;">
                                    <input type="radio" name="time" value="${slot}" required style="margin-right: var(--space-1);">
                                    <c:out value="${slot}"/>
                                </label>
                            </c:forEach>
                        </div>

                        <button type="submit" class="btn btn--primary btn--block">Confirm New Time</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <p style="color: var(--color-ink-muted);">No available slots on this date.</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>