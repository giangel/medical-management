<%-- File: WebContent/views/shared/header-doctor.jsp --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="topnav">
    <div class="topnav__inner">
        <a class="topnav__brand" href="${pageContext.request.contextPath}/views/doctor/dashboard">Meridian Health</a>
        <button class="topnav__toggle" aria-label="Toggle navigation">&#9776;</button>
        <ul class="topnav__links">
            <li><a href="${pageContext.request.contextPath}/views/doctor/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/views/doctor/appointments">Appointments</a></li>
            <li><a href="${pageContext.request.contextPath}/views/doctor/availability">Availability</a></li>
            <li><a href="${pageContext.request.contextPath}/views/doctor/notifications">Notifications</a></li>
            <li><a href="${pageContext.request.contextPath}/views/doctor/profile">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </div>
</nav>