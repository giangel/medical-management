<%-- File: WebContent/views/doctor/appointments.jsp --%>
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

<jsp:include page="/views/shared/header-doctor.jsp"/>

<section class="band band--tight band--alt">
    <div class="container">
        <h1 style="margin-bottom: var(--space-4);">My Appointments</h1>
        <form method="get" style="display:flex; gap: var(--space-3); flex-wrap:wrap; align-items:flex-end;">
            <div class="field" style="margin-bottom:0;">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <option value="">All Statuses</option>
                    <option value="PENDING" ${selectedStatus == 'PENDING' ? 'selected' : ''}>Pending</option>
                    <option value="CONFIRMED" ${selectedStatus == 'CONFIRMED' ? 'selected' : ''}>Confirmed</option>
                    <option value="COMPLETED" ${selectedStatus == 'COMPLETED' ? 'selected' : ''}>Completed</option>
                    <option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                    <option value="REJECTED" ${selectedStatus == 'REJECTED' ? 'selected' : ''}>Rejected</option>
                </select>
            </div>
            <div class="field" style="margin-bottom:0;">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" value="${selectedDate}">
            </div>
            <button type="submit" class="btn btn--primary">Filter</button>
        </form>
    </div>
</section>

<section class="band">
    <div class="container">
        <table class="data-table">
            <tr><th>Date</th><th>Time</th><th>Patient</th><th>Status</th></tr>
            <c:forEach var="a" items="${appointments}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/views/doctor/appointments/view?id=${a.appointmentId}"><c:out value="${a.appointmentDate}"/></a></td>
                    <td><c:out value="${a.startTime}"/></td>
                    <td><c:out value="${a.patientFullName}"/></td>
                    <td><span class="status-pill status-${fn:toLowerCase(a.status)}"><c:out value="${a.status}"/></span></td>
                </tr>
            </c:forEach>
            <c:if test="${empty appointments}">
                <tr><td colspan="4" style="text-align:center; color: var(--color-ink-muted);">No appointments match this filter.</td></tr>
            </c:if>
        </table>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>