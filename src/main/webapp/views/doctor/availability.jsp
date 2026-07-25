<%-- File: WebContent/views/doctor/availability.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Availability - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-doctor.jsp"/>

<section class="band">
    <div class="container">
        <h1>Manage Availability</h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
        </c:if>

        <div class="grid grid--2" style="align-items:start;">

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Weekly Working Hours</h2>

                <c:forEach var="slot" items="${recurringSlots}">
                    <div style="display:flex; justify-content:space-between; padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border); font-size: var(--text-sm);">
                        <c:choose>
                            <c:when test="${slot.dayOfWeek == 0}">Sunday</c:when>
                            <c:when test="${slot.dayOfWeek == 1}">Monday</c:when>
                            <c:when test="${slot.dayOfWeek == 2}">Tuesday</c:when>
                            <c:when test="${slot.dayOfWeek == 3}">Wednesday</c:when>
                            <c:when test="${slot.dayOfWeek == 4}">Thursday</c:when>
                            <c:when test="${slot.dayOfWeek == 5}">Friday</c:when>
                            <c:when test="${slot.dayOfWeek == 6}">Saturday</c:when>
                        </c:choose>
                        <span><c:out value="${slot.startTime}"/> to <c:out value="${slot.endTime}"/> (<c:out value="${slot.slotMinutes}"/> min slots)</span>
                    </div>
                </c:forEach>
                <c:if test="${empty recurringSlots}">
                    <p style="color: var(--color-ink-muted);">No recurring hours configured yet.</p>
                </c:if>

                <h3 style="font-size: var(--text-sm); margin-top: var(--space-5);">Add Recurring Hours</h3>
                <form action="${pageContext.request.contextPath}/views/doctor/availability" method="post">
                    <input type="hidden" name="action" value="addRecurring">
                    <div class="field">
                        <label for="dayOfWeek">Day of Week</label>
                        <select id="dayOfWeek" name="dayOfWeek">
                            <option value="0">Sunday</option>
                            <option value="1">Monday</option>
                            <option value="2">Tuesday</option>
                            <option value="3">Wednesday</option>
                            <option value="4">Thursday</option>
                            <option value="5">Friday</option>
                            <option value="6">Saturday</option>
                        </select>
                    </div>
                    <div class="grid grid--2">
                        <div class="field">
                            <label for="startTime">Start</label>
                            <input type="time" id="startTime" name="startTime" required>
                        </div>
                        <div class="field">
                            <label for="endTime">End</label>
                            <input type="time" id="endTime" name="endTime" required>
                        </div>
                    </div>
                    <div class="field">
                        <label for="slotMinutes">Slot Length (minutes)</label>
                        <input type="number" id="slotMinutes" name="slotMinutes" value="30" min="5" required style="max-width: 140px;">
                    </div>
                    <button type="submit" class="btn btn--primary btn--sm">Add Hours</button>
                </form>
            </div>

            <div class="card">
                <h2 style="font-size: var(--text-lg); margin-bottom: var(--space-3);">Date Specific Overrides</h2>

                <c:forEach var="ov" items="${overrides}">
                    <div style="padding: var(--space-2) 0; border-bottom: 1px solid var(--color-border); font-size: var(--text-sm);">
                        <strong><c:out value="${ov.specificDate}"/>:</strong>
                        <c:choose>
                            <c:when test="${ov.unavailable}">
                                <span class="status-pill status-cancelled">Blocked Out</span>
                            </c:when>
                            <c:otherwise>
                                Extra hours <c:out value="${ov.startTime}"/> to <c:out value="${ov.endTime}"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
                <c:if test="${empty overrides}">
                    <p style="color: var(--color-ink-muted);">No overrides set.</p>
                </c:if>

                <h3 style="font-size: var(--text-sm); margin-top: var(--space-5);">Block Out a Date</h3>
                <form action="${pageContext.request.contextPath}/views/doctor/availability" method="post" style="display:flex; gap: var(--space-2); align-items:flex-end;">
                    <input type="hidden" name="action" value="addBlockedDate">
                    <div class="field" style="margin-bottom:0; flex:1;">
                        <input type="date" name="blockedDate" required>
                    </div>
                    <button type="submit" class="btn btn--secondary btn--sm">Mark Unavailable</button>
                </form>

                <h3 style="font-size: var(--text-sm); margin-top: var(--space-5);">Add Extra Hours for a Date</h3>
                <form action="${pageContext.request.contextPath}/views/doctor/availability" method="post">
                    <input type="hidden" name="action" value="addExtraHours">
                    <div class="field">
                        <input type="date" name="extraDate" required>
                    </div>
                    <div class="grid grid--2">
                        <div class="field">
                            <input type="time" name="extraStartTime" required>
                        </div>
                        <div class="field">
                            <input type="time" name="extraEndTime" required>
                        </div>
                    </div>
                    <button type="submit" class="btn btn--secondary btn--sm">Add Extra Hours</button>
                </form>
            </div>

        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>