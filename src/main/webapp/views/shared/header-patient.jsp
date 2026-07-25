<%-- File: WebContent/views/shared/header-patient.jsp --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="topnav">
    <div class="topnav__inner">
        <a class="topnav__brand" href="${pageContext.request.contextPath}/views/patient/dashboard">Meridian Health</a>
        <button class="topnav__toggle" aria-label="Toggle navigation">&#9776;</button>
        <ul class="topnav__links">
            <li><a href="${pageContext.request.contextPath}/views/patient/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/views/patient/doctors">Find a Doctor</a></li>
            <li><a href="${pageContext.request.contextPath}/views/patient/appointments">My Appointments</a></li>
            <li><a href="${pageContext.request.contextPath}/views/patient/notifications">Notifications</a></li>
            <li><a href="${pageContext.request.contextPath}/views/patient/profile">Profile</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </div>
</nav>