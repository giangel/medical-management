<%-- File: WebContent/views/patient/profile.jsp --%>
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

<jsp:include page="/views/shared/header-patient.jsp"/>

<section class="band">
    <div class="container">
        <h1>My Profile</h1>

        <c:if test="${not empty successMessage}">
            <div class="alert alert--success" data-dismissible><c:out value="${successMessage}"/></div>
        </c:if>

        <div class="card" style="max-width: 640px;">
            <form action="${pageContext.request.contextPath}/views/patient/profile" method="post">

                <h3 style="font-size: var(--text-base); margin-bottom: var(--space-3);">Personal Details</h3>
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

                <div class="grid grid--2">
                    <div class="field">
                        <label for="phoneNumber">Phone Number</label>
                        <input type="tel" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
                    </div>
                    <div class="field">
                        <label for="dateOfBirth">Date of Birth</label>
                        <input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth}">
                    </div>
                </div>

                <div class="field">
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender">
                        <option value="" ${empty user.gender ? 'selected' : ''}>Prefer not to say</option>
                        <option value="MALE" ${user.gender == 'MALE' ? 'selected' : ''}>Male</option>
                        <option value="FEMALE" ${user.gender == 'FEMALE' ? 'selected' : ''}>Female</option>
                        <option value="OTHER" ${user.gender == 'OTHER' ? 'selected' : ''}>Other</option>
                    </select>
                </div>

                <h3 style="font-size: var(--text-base); margin: var(--space-5) 0 var(--space-3);">Health and Emergency Details</h3>

                <div class="field">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="${patient.address}">
                </div>

                <div class="grid grid--2">
                    <div class="field">
                        <label for="emergencyContactName">Emergency Contact Name</label>
                        <input type="text" id="emergencyContactName" name="emergencyContactName" value="${patient.emergencyContactName}">
                    </div>
                    <div class="field">
                        <label for="emergencyContactPhone">Emergency Contact Phone</label>
                        <input type="tel" id="emergencyContactPhone" name="emergencyContactPhone" value="${patient.emergencyContactPhone}">
                    </div>
                </div>

                <div class="field">
                    <label for="bloodGroup">Blood Group</label>
                    <input type="text" id="bloodGroup" name="bloodGroup" value="${patient.bloodGroup}" style="max-width: 160px;">
                </div>

                <div class="field">
                    <label for="allergies">Allergies</label>
                    <textarea id="allergies" name="allergies" rows="3">${patient.allergies}</textarea>
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