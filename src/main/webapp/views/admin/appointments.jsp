<%-- File: WebContent/views/admin/appointments.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Appointments - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band band--tight band--alt">
    <div class="container">
        <h1 style="margin-bottom: var(--space-4);">All Appointments</h1>
        <form method="get" style="display:flex; gap: var(--space-3); flex-wrap:wrap; align-items:flex-end;">
            <div class="field" style="margin-bottom:0;">
                <label for="doctorId">Doctor</label>
                <select id="doctorId" name="doctorId">
                    <option value="">All Doctors</option>
                    <c:forEach var="d" items="${doctors}">
                        <option value="${d.doctorId}" ${d.doctorId == selectedDoctorId ? 'selected' : ''}>Dr. <c:out value="${d.fullName}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="field" style="margin-bottom:0;">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <option value="">All Statuses</option>
                    <option value="PENDING" ${selectedStatus == 'PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="CONFIRMED" ${selectedStatus == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                    <option value="COMPLETED" ${selectedStatus == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                    <option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                    <option value="REJECTED" ${selectedStatus == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                    <option value="RESCHEDULED" ${selectedStatus == 'RESCHEDULED' ? 'selected' : ''}>Rescheduled</option>
                </select>
            </div>
            <div class="field" style="margin-bottom:0;">
                <label for="fromDate">From</label>
                <input type="date" id="fromDate" name="fromDate" value="${fromDate}">
            </div>
            <div class="field" style="margin-bottom:0;">
                <label for="toDate">To</label>
                <input type="date" id="toDate" name="toDate" value="${toDate}">
            </div>
            <button type="submit" class="btn btn--primary">Filter</button>
        </form>
    </div>
</section>

<section class="band">
    <div class="container">
        <table class="data-table">
            <tr><th>Date</th><th>Patient</th><th>Doctor</th><th>Status</th><th>Action</th></tr>
            <c:forEach var="a" items="${appointments}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/views/admin/appointments/view?id=${a.appointmentId}"><c:out value="${a.appointmentDate}"/> <c:out value="${a.startTime}"/></a></td>
                    <td><c:out value="${a.patientFullName}"/></td>
                    <td>Dr. <c:out value="${a.doctorFullName}"/></td>
                    <td><span class="status-pill status-${fn:toLowerCase(a.status)}"><c:out value="${a.status}"/></span></td>
                    <td>
                        <c:if test="${a.status == 'PENDING' || a.status == 'CONFIRMED'}">
                            <form action="${pageContext.request.contextPath}/views/admin/appointments" method="post" data-confirm="Cancel this appointment?" style="display:flex; gap: var(--space-2);">
                                <input type="hidden" name="appointmentId" value="${a.appointmentId}">
                                <input type="text" name="reason" placeholder="Reason" style="border:1px solid var(--color-border-strong); border-radius: var(--radius-sm); padding: var(--space-1) var(--space-2); font-size: var(--text-xs); width: 120px;">
                                <button type="submit" class="btn btn--danger btn--sm">Cancel</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty appointments}">
                <tr><td colspan="5" style="text-align:center; color: var(--color-ink-muted);">No appointments match this filter.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>