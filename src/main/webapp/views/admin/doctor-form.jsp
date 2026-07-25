<%-- File: WebContent/views/admin/doctor-form.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><c:out value="${empty doctor ? 'Add Doctor' : 'Edit Doctor'}"/> - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-admin.jsp"/>

<section class="band">
    <div class="container">
        <a href="${pageContext.request.contextPath}/views/admin/doctors" style="font-size: var(--text-sm);">&larr; Back to doctors</a>
        <h1 style="margin-top: var(--space-3);"><c:out value="${empty doctor ? 'Add New Doctor' : 'Edit Doctor'}"/></h1>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
        </c:if>

        <div class="card" style="max-width: 640px;">
            <c:choose>
                <c:when test="${empty doctor}">
                    <form action="${pageContext.request.contextPath}/views/admin/doctors/add" method="post">
                        <div class="grid grid--2">
                            <div class="field">
                                <label for="firstName">First Name</label>
                                <input type="text" id="firstName" name="firstName" required>
                            </div>
                            <div class="field">
                                <label for="lastName">Last Name</label>
                                <input type="text" id="lastName" name="lastName" required>
                            </div>
                        </div>
                        <div class="field">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" required>
                        </div>
                        <div class="field">
                            <label for="phoneNumber">Phone Number</label>
                            <input type="tel" id="phoneNumber" name="phoneNumber">
                        </div>
                        <div class="field">
                            <label for="temporaryPassword">Temporary Password</label>
                            <input type="password" id="temporaryPassword" name="temporaryPassword" required minlength="8">
                            <p class="field-hint">Share this with the doctor so they can log in and change it.</p>
                        </div>
                        <div class="field">
                            <label for="licenseNumber">License Number</label>
                            <input type="text" id="licenseNumber" name="licenseNumber">
                        </div>
                        <div class="grid grid--2">
                            <div class="field">
                                <label for="departmentId">Department</label>
                                <select id="departmentId" name="departmentId">
                                    <option value="">None</option>
                                    <c:forEach var="dep" items="${departments}">
                                        <option value="${dep.departmentId}"><c:out value="${dep.departmentName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="field">
                                <label for="specialtyId">Specialty</label>
                                <select id="specialtyId" name="specialtyId">
                                    <option value="">None</option>
                                    <c:forEach var="spec" items="${specialties}">
                                        <option value="${spec.specialtyId}"><c:out value="${spec.specialtyName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <button type="submit" class="btn btn--primary">Create Doctor Account</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="${pageContext.request.contextPath}/views/admin/doctors/edit" method="post">
                        <input type="hidden" name="doctorId" value="${doctor.doctorId}">
                        <div class="grid grid--2">
                            <div class="field">
                                <label for="licenseNumber">License Number</label>
                                <input type="text" id="licenseNumber" name="licenseNumber" value="${doctor.licenseNumber}">
                            </div>
                            <div class="field">
                                <label for="yearsOfExperience">Years of Experience</label>
                                <input type="number" id="yearsOfExperience" name="yearsOfExperience" value="${doctor.yearsOfExperience}">
                            </div>
                        </div>
                        <div class="grid grid--2">
                            <div class="field">
                                <label for="consultationFee">Consultation Fee</label>
                                <input type="number" step="0.01" id="consultationFee" name="consultationFee" value="${doctor.consultationFee}">
                            </div>
                            <div class="field">
                                <label for="defaultSlotMinutes">Default Slot Minutes</label>
                                <input type="number" id="defaultSlotMinutes" name="defaultSlotMinutes" value="${doctor.defaultSlotMinutes}">
                            </div>
                        </div>
                        <div class="grid grid--2">
                            <div class="field">
                                <label for="departmentId">Department</label>
                                <select id="departmentId" name="departmentId">
                                    <option value="">None</option>
                                    <c:forEach var="dep" items="${departments}">
                                        <option value="${dep.departmentId}" ${dep.departmentId == doctor.departmentId ? 'selected' : ''}><c:out value="${dep.departmentName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="field">
                                <label for="specialtyId">Specialty</label>
                                <select id="specialtyId" name="specialtyId">
                                    <option value="">None</option>
                                    <c:forEach var="spec" items="${specialties}">
                                        <option value="${spec.specialtyId}" ${spec.specialtyId == doctor.specialtyId ? 'selected' : ''}><c:out value="${spec.specialtyName}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="field">
                            <label for="qualifications">Qualifications</label>
                            <textarea id="qualifications" name="qualifications" rows="3">${doctor.qualifications}</textarea>
                        </div>
                        <div class="field">
                            <label for="biography">Biography</label>
                            <textarea id="biography" name="biography" rows="4">${doctor.biography}</textarea>
                        </div>
                        <button type="submit" class="btn btn--primary">Save Changes</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>