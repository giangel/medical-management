<%-- File: WebContent/views/patient/doctor-search.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Find a Doctor - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band band--tight band--alt">
    <div class="container">
        <h1 style="margin-bottom: var(--space-4);">Find a Doctor</h1>
        <form action="${pageContext.request.contextPath}/views/patient/doctors" method="get"
              style="display:flex; gap: var(--space-3); flex-wrap: wrap; align-items:flex-end;">
            <div class="field" style="flex: 2 1 220px; margin-bottom: 0;">
                <label for="keyword">Doctor Name</label>
                <input type="text" id="keyword" name="keyword" placeholder="Search by name" value="${keyword}">
            </div>
            <div class="field" style="flex: 1 1 180px; margin-bottom: 0;">
                <label for="departmentId">Department</label>
                <select id="departmentId" name="departmentId">
                    <option value="">All Departments</option>
                    <c:forEach var="dept" items="${departments}">
                        <option value="${dept.departmentId}" ${dept.departmentId == selectedDepartmentId ? 'selected' : ''}>
                            <c:out value="${dept.departmentName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="field" style="flex: 1 1 180px; margin-bottom: 0;">
                <label for="specialtyId">Specialty</label>
                <select id="specialtyId" name="specialtyId">
                    <option value="">All Specialties</option>
                    <c:forEach var="spec" items="${specialties}">
                        <option value="${spec.specialtyId}" ${spec.specialtyId == selectedSpecialtyId ? 'selected' : ''}>
                            <c:out value="${spec.specialtyName}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn--primary">Search</button>
        </form>
    </div>
</section>

<section class="band">
    <div class="container">
        <div class="grid grid--3">
            <c:forEach var="doc" items="${doctors}">
                <div class="card">
                    <h3 style="font-size: var(--text-lg); margin-bottom: var(--space-1);">Dr. <c:out value="${doc.fullName}"/></h3>
                    <p style="color: var(--color-primary-dark); font-weight: 600; font-size: var(--text-sm); margin-bottom: var(--space-1);">
                        <c:out value="${doc.specialtyName}"/>
                    </p>
                    <p style="color: var(--color-ink-muted); font-size: var(--text-sm);">
                        <c:out value="${doc.departmentName}"/> &middot; <c:out value="${doc.yearsOfExperience}"/> years experience
                    </p>
                    <a class="btn btn--secondary btn--sm" href="${pageContext.request.contextPath}/views/patient/doctor?id=${doc.doctorId}">View Profile</a>
                </div>
            </c:forEach>
        </div>
        <c:if test="${empty doctors}">
            <div class="card" style="text-align:center; color: var(--color-ink-muted);">
                No doctors match your search. Try a different name, department, or specialty.
            </div>
        </c:if>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>