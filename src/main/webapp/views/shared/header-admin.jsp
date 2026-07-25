<%-- File: WebContent/views/shared/header-admin.jsp --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="topnav">
    <div class="topnav__inner">
        <a class="topnav__brand" href="${pageContext.request.contextPath}/views/admin/dashboard">Meridian Health</a>
        <button class="topnav__toggle" aria-label="Toggle navigation">&#9776;</button>
        <ul class="topnav__links">
            <li><a href="${pageContext.request.contextPath}/views/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/users">Users</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/patients">Patients</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/doctors">Doctors</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/departments">Departments</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/specialties">Specialties</a></li>
            <li><a href="${pageContext.request.contextPath}/views/admin/appointments">Appointments</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Log Out</a></li>
        </ul>
    </div>
</nav>