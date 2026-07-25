<%-- File: WebContent/views/patient/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <h1>Welcome back, <c:out value="${sessionScope.userFullName}"/></h1>
        <p style="color: var(--color-ink-muted);">Here is where things stand with your care.</p>

        <div class="grid grid--2" style="margin-top: var(--space-6); align-items: start;">

            <div class="card" style="grid-column: span 2;">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-2);">Your Next Appointment</h2>
                <c:choose>
                    <c:when test="${not empty nextAppointment}">
                        <div style="display:flex; justify-content: space-between; align-items:center; flex-wrap: wrap; gap: var(--space-3);">
                            <div>
                                <p style="font-size: var(--text-xl); font-family: var(--font-heading); margin-bottom: var(--space-1);">
                                    Dr. <c:out value="${nextAppointment.doctorFullName}"/>
                                </p>
                                <p style="color: var(--color-ink-muted); margin-bottom: 0;">
                                    <c:out value="${nextAppointment.specialtyName}"/> &middot;
                                    <c:out value="${nextAppointment.appointmentDate}"/> at <c:out value="${nextAppointment.startTime}"/>
                                </p>
                            </div>
                            <span class="status-pill status-${fn:toLowerCase(nextAppointment.status)}">
                                <c:out value="${nextAppointment.status}"/>
                            </span>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p style="color: var(--color-ink-muted);">You have no upcoming appointments right now.</p>
                        <a class="btn btn--primary" href="${pageContext.request.contextPath}/views/patient/doctors">Book an Appointment</a>
                    </c:otherwise>
                </c:choose>
            </div>

            <div class="card">
                <h3 style="font-size: var(--text-base); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Upcoming Appointments</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin: 0;"><c:out value="${upcomingCount}"/></p>
            </div>

            <div class="card">
                <h3 style="font-size: var(--text-base); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Unread Notifications</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin: 0;"><c:out value="${unreadNotifications}"/></p>
                <a href="${pageContext.request.contextPath}/views/patient/notifications" style="font-size: var(--text-sm);">View all</a>
            </div>

            <div class="card" style="grid-column: span 2;">
                <div style="display:flex; justify-content: space-between; align-items:center; margin-bottom: var(--space-3);">
                    <h2 style="font-size: var(--text-lg); margin: 0;">Recent Activity</h2>
                    <a href="${pageContext.request.contextPath}/views/patient/appointments" style="font-size: var(--text-sm);">View all appointments</a>
                </div>
                <c:forEach var="appt" items="${recentAppointments}">
                    <div style="display:flex; justify-content: space-between; align-items:center; padding: var(--space-3) 0; border-bottom: 1px solid var(--color-border);">
                        <div>
                            <p style="margin: 0; font-weight: 600;">Dr. <c:out value="${appt.doctorFullName}"/></p>
                            <p style="margin: 0; font-size: var(--text-sm); color: var(--color-ink-muted);">
                                <c:out value="${appt.appointmentDate}"/> at <c:out value="${appt.startTime}"/>
                            </p>
                        </div>
                        <span class="status-pill status-${fn:toLowerCase(appt.status)}"><c:out value="${appt.status}"/></span>
                    </div>
                </c:forEach>
                <c:if test="${empty recentAppointments}">
                    <p style="color: var(--color-ink-muted);">No appointment activity yet.</p>
                </c:if>
            </div>

        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>