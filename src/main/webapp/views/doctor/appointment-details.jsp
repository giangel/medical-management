<%-- File: WebContent/views/doctor/appointment-details.jsp --%>
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

<jsp:include page="/views/shared/header-doctor.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/doctor/appointments" style="font-size: var(--text-sm);">&larr; Back to appointments</a>

        <c:if test="${not empty param.error}">
            <div class="alert alert--error" style="margin-top: var(--space-4);"><c:out value="${param.error}"/></div>
        </c:if>

        <div class="grid grid--2" style="margin-top: var(--space-4); align-items:start;">

            <div class="card">
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom: var(--space-3);">
                    <h1 style="font-size: var(--text-xl); margin:0;"><c:out value="${appointment.patientFullName}"/></h1>
                    <span class="status-pill status-${fn:toLowerCase(appointment.status)}"><c:out value="${appointment.status}"/></span>
                </div>
                <p style="color: var(--color-ink-muted); margin-bottom: var(--space-1);">Phone: <c:out value="${appointment.patientPhoneNumber}"/></p>
                <p style="color: var(--color-ink-muted); margin-bottom: var(--space-1);">
                    <c:out value="${appointment.appointmentDate}"/> at <c:out value="${appointment.startTime}"/>
                </p>
                <h3 style="font-size: var(--text-sm); margin-top: var(--space-4);">Reason for Visit</h3>
                <p style="color: var(--color-ink-muted);"><c:out value="${appointment.reason}"/></p>

                <c:if test="${appointment.status == 'PENDING'}">
                    <div style="display:flex; gap: var(--space-3); margin-top: var(--space-5); flex-wrap:wrap;">
                        <form action="${pageContext.request.contextPath}/views/doctor/appointments/update-status" method="post">
                            <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                            <input type="hidden" name="action" value="confirm">
                            <button type="submit" class="btn btn--primary">Confirm Appointment</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/views/doctor/appointments/update-status" method="post" style="display:flex; gap: var(--space-2);">
                            <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                            <input type="hidden" name="action" value="reject">
                            <input type="text" name="reason" placeholder="Reason for rejection" required style="border:1px solid var(--color-border-strong); border-radius: var(--radius-sm); padding: var(--space-2);">
                            <button type="submit" class="btn btn--danger">Reject</button>
                        </form>
                    </div>
                </c:if>

                <c:if test="${appointment.status == 'CONFIRMED'}">
                    <form action="${pageContext.request.contextPath}/views/doctor/appointments/update-status" method="post" style="margin-top: var(--space-5);">
                        <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                        <input type="hidden" name="action" value="complete">
                        <button type="submit" class="btn btn--primary">Mark as Completed</button>
                    </form>
                </c:if>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Notes</h2>
                <c:forEach var="n" items="${notes}">
                    <div style="padding: var(--space-3) 0; border-bottom: 1px solid var(--color-border);">
                        <p style="margin:0; font-size: var(--text-sm); color: var(--color-ink-muted);">
                            <c:out value="${n.authorFullName}"/> &middot; <c:out value="${n.createdAt}"/>
                        </p>
                        <p style="margin: var(--space-1) 0 0;"><c:out value="${n.noteText}"/></p>
                    </div>
                </c:forEach>
                <c:if test="${empty notes}">
                    <p style="color: var(--color-ink-muted);">No notes yet.</p>
                </c:if>

                <form action="${pageContext.request.contextPath}/views/doctor/appointments/update-status" method="post" style="margin-top: var(--space-4);">
                    <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                    <input type="hidden" name="action" value="addNote">
                    <div class="field">
                        <textarea name="noteText" rows="3" placeholder="Add a clinical note" required></textarea>
                    </div>
                    <button type="submit" class="btn btn--secondary btn--sm">Add Note</button>
                </form>
            </div>

        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>