<%-- File: WebContent/views/admin/dashboard.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <h1>Platform Overview</h1>
        <p style="color: var(--color-ink-muted);">A snapshot of how Meridian Health is running today.</p>

        <div class="grid grid--4" style="margin-top: var(--space-5);">
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Total Users</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${totalUsers}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Total Patients</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${totalPatients}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Total Doctors</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${totalDoctors}"/></p>
            </div>
            <div class="card">
                <h3 style="font-size: var(--text-sm); color: var(--color-ink-muted); margin-bottom: var(--space-1);">Total Appointments</h3>
                <p style="font-size: var(--text-3xl); font-family: var(--font-heading); margin:0;"><c:out value="${totalAppointments}"/></p>
            </div>
        </div>

        <div class="grid grid--2" style="margin-top: var(--space-6); align-items:start;">

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Appointments by Status</h2>
                <c:forEach var="entry" items="${statusCounts}">
                    <div style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border);">
                        <span class="status-pill status-${fn:toLowerCase(entry.key)}"><c:out value="${entry.key}"/></span>
                        <strong><c:out value="${entry.value}"/></strong>
                    </div>
                </c:forEach>
            </div>

            <div class="card">
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom: var(--space-3);">
                    <h2 style="font-size: var(--text-lg); margin:0;">Recent Appointments</h2>
                    <a href="${pageContext.request.contextPath}/views/admin/appointments" style="font-size: var(--text-sm);">View all</a>
                </div>
                <c:forEach var="appt" items="${recentAppointments}">
                    <div style="display:flex; justify-content:space-between; align-items:center; padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border);">
                        <div>
                            <p style="margin:0; font-weight:600; font-size: var(--text-sm);"><c:out value="${appt.patientFullName}"/></p>
                            <p style="margin:0; font-size: var(--text-xs); color: var(--color-ink-muted);">
                                with Dr. <c:out value="${appt.doctorFullName}"/> &middot; <c:out value="${appt.appointmentDate}"/>
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