<%-- File: WebContent/views/patient/notifications.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <div style="display:flex; justify-content:space-between; align-items:center; flex-wrap:wrap; gap: var(--space-3);">
            <h1 style="margin: 0;">Notifications</h1>
            <form action="${pageContext.request.contextPath}/views/patient/notifications" method="post">
                <input type="hidden" name="action" value="markAllRead">
                <button type="submit" class="btn btn--secondary btn--sm">Mark All as Read</button>
            </form>
        </div>

        <div class="card" style="padding: 0; margin-top: var(--space-5);">
            <c:forEach var="notif" items="${notifications}">
                <div class="notification-item ${notif.read ? '' : 'is-unread'}">
                    <div>
                        <h4><c:out value="${notif.title}"/></h4>
                        <p><c:out value="${notif.message}"/></p>
                    </div>
                    <div style="text-align:right;">
                        <time><c:out value="${notif.createdAt}"/></time>
                        <c:if test="${!notif.read}">
                            <form action="${pageContext.request.contextPath}/views/patient/notifications" method="post">
                                <input type="hidden" name="notificationId" value="${notif.notificationId}">
                                <button type="submit" class="btn btn--secondary btn--sm" style="margin-top: var(--space-2);">Mark as Read</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty notifications}">
                <div style="padding: var(--space-6); text-align:center; color: var(--color-ink-muted);">No notifications yet.</div>
            </c:if>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>