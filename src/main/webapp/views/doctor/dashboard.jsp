<%-- File: WebContent/views/doctor/dashboard.jsp --%>
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

<jsp:include page="/views/shared/header-doctor.jsp"/>

<section class="band">
    <div class="container">
        <h1>Good day, Dr. <c:out value="${sessionScope.userFullName}"/></h1>
        <p style="color: var(--color-ink-muted);">
            <c:out value="${doctor.specialtyName}"/> &middot; <c:out value="${doctor.departmentName}"/> &middot;
            Currently <c:out value="${doctor.acceptingAppointments ? 'accepting' : 'not accepting'}"/> new appointments
        </p>

        <div class="grid grid--4" style="margin-top: var(--space-5);">
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Today's Appointments</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${todayAppointments.size()}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Pending Requests</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${pendingRequests.size()}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Upcoming Confirmed</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${upcomingAppointments.size()}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Completed to Date</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${completedCount}"/></p>
            </div>
        </div>

        <div class="grid grid--2" style="margin-top: var(--space-6); align-items:start;">

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Today's Schedule</h2>
                <c:forEach var="a" items="${todayAppointments}">
                    <div style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-3) 0; border-bottom: 1px solid var(--color-border);">
                        <div>
                            <p style="margin:0; font-weight:600;"><c:out value="${a.startTime}"/> &middot; <c:out value="${a.patientFullName}"/></p>
                        </div>
                        <span class="status-pill status-${fn:toLowerCase(a.status)}"><c:out value="${a.status}"/></span>
                    </div>
                </c:forEach>
                <c:if test="${empty todayAppointments}">
                    <p style="color: var(--color-ink-muted);">Nothing scheduled for today.</p>
                </c:if>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Pending Requests</h2>
                <c:forEach var="a" items="${pendingRequests}">
                    <a href="${pageContext.request.contextPath}/views/doctor/appointments/view?id=${a.appointmentId}"
                       style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-3) 0; border-bottom: 1px solid var(--color-border); color: var(--color-ink);">
                        <div>
                            <p style="margin:0; font-weight:600;"><c:out value="${a.patientFullName}"/></p>
                            <p style="margin:0; font-size: var(--text-sm); color: var(--color-ink-muted);">
                                <c:out value="${a.appointmentDate}"/> at <c:out value="${a.startTime}"/>
                            </p>
                        </div>
                        <span class="status-pill status-pending">Review</span>
                    </a>
                </c:forEach>
                <c:if test="${empty pendingRequests}">
                    <p style="color: var(--color-ink-muted);">No pending requests right now.</p>
                </c:if>
            </div>

        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>