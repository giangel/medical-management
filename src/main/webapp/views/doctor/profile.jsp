<%-- File: WebContent/views/doctor/profile.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-doctor.jsp"/>

<section class="band">
    <div class="container">
        <h1>My Professional Profile</h1>

        <c:if test="${not empty successMessage}">
            <div class="alert alert--success" data-dismissible><c:out value="${successMessage}"/></div>
        </c:if>

        <div class="card" style="max-width: 680px;">
            <form action="${pageContext.request.contextPath}/views/doctor/profile" method="post">

                <h3 style="font-size: var(--text-base); margin-bottom: var(--space-3);">Contact Details</h3>
                <div class="grid grid--2">
                    <div class="field">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" value="${user.firstName}" required>
                    </div>
                    <div class="field">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" value="${user.lastName}" required>
                    </div>
                </div>
                <div class="field">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
                </div>

                <h3 style="font-size: var(--text-base); margin: var(--space-5) 0 var(--space-3);">Professional Details</h3>
                <div class="grid grid--2">
                    <div class="field">
                        <label for="licenseNumber">License Number</label>
                        <input type="text" id="licenseNumber" name="licenseNumber" value="${doctor.licenseNumber}">
                    </div>
                    <div class="field">
                        <label for="yearsOfExperience">Years of Experience</label>
                        <input type="number" id="yearsOfExperience" name="yearsOfExperience" value="${doctor.yearsOfExperience}" min="0">
                    </div>
                </div>

                <div class="grid grid--2">
                    <div class="field">
                        <label for="departmentId">Department</label>
                        <select id="departmentId" name="departmentId">
                            <option value="">None</option>
                            <c:forEach var="dep" items="${departments}">
                                <option value="${dep.departmentId}" ${dep.departmentId == doctor.departmentId ? 'selected' : ''}>
                                    <c:out value="${dep.departmentName}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="field">
                        <label for="specialtyId">Specialty</label>
                        <select id="specialtyId" name="specialtyId">
                            <option value="">None</option>
                            <c:forEach var="spec" items="${specialties}">
                                <option value="${spec.specialtyId}" ${spec.specialtyId == doctor.specialtyId ? 'selected' : ''}>
                                    <c:out value="${spec.specialtyName}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="field">
                    <label for="consultationFee">Consultation Fee</label>
                    <input type="number" step="0.01" id="consultationFee" name="consultationFee" value="${doctor.consultationFee}" style="max-width: 200px;">
                </div>

                <div class="field">
                    <label for="qualifications">Qualifications</label>
                    <textarea id="qualifications" name="qualifications" rows="3">${doctor.qualifications}</textarea>
                </div>

                <div class="field">
                    <label for="biography">Biography</label>
                    <textarea id="biography" name="biography" rows="5">${doctor.biography}</textarea>
                    <p class="field-hint">This appears on your public profile that patients see when searching for a doctor.</p>
                </div>

                <button type="submit" class="btn btn--primary">Save Changes</button>
            </form>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>