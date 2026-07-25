<%-- File: WebContent/views/patient/doctor-profile.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dr. <c:out value="${doctor.fullName}"/> - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/patient/doctors" style="font-size: var(--text-sm);">&larr; Back to search</a>

        <div class="grid grid--2" style="margin-top: var(--space-5); align-items:start;">

            <div class="card">
                <h1 style="font-size: var(--text-2xl); margin-bottom: var(--space-1);">Dr. <c:out value="${doctor.fullName}"/></h1>
                <p style="color: var(--color-primary-dark); font-weight: 600;"><c:out value="${doctor.specialtyName}"/>, <c:out value="${doctor.departmentName}"/></p>
                <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">
                    <c:out value="${doctor.yearsOfExperience}"/> years of experience &middot; Consultation fee: <c:out value="${doctor.consultationFee}"/>
                </p>
                <h3 style="font-size: var(--text-base); margin-top: var(--space-5);">Qualifications</h3>
                <p style="color: var(--color-ink-muted);"><c:out value="${doctor.qualifications}"/></p>
                <h3 style="font-size: var(--text-base);">About</h3>
                <p style="color: var(--color-ink-muted);"><c:out value="${doctor.biography}"/></p>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg);">Book an Appointment</h2>

                <c:if test="${not empty param.error}">
                    <div class="alert alert--error"><c:out value="${param.error}"/></div>
                </c:if>

                <form action="${pageContext.request.contextPath}/views/patient/doctor" method="get" class="field">
                    <input type="hidden" name="id" value="${doctor.doctorId}">
                    <label for="date">Choose a Date</label>
                    <input type="date" id="date" name="date" value="${selectedDate}" onchange="this.form.submit()">
                </form>

                <c:choose>
                    <c:when test="${not empty availableSlots}">
                        <form action="${pageContext.request.contextPath}/views/patient/book" method="post">
                            <input type="hidden" name="doctorId" value="${doctor.doctorId}">
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

                            <div class="field">
                                <label for="reason">Reason for Appointment</label>
                                <textarea id="reason" name="reason" required rows="3"></textarea>
                            </div>

                            <button type="submit" class="btn btn--primary btn--block">Confirm Booking</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p style="color: var(--color-ink-muted);">No available slots on this date. Please try another date.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>