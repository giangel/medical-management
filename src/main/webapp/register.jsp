<%-- File: WebContent/register.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-public.jsp"/>

<section class="band">
    <div class="container">
        <div class="form-card" style="max-width: 560px;">
            <h1 style="font-size: var(--text-2xl); text-align:center;">Create Your Account</h1>
            <p style="text-align:center; color: var(--color-ink-muted);">Takes about two minutes.</p>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="grid grid--2">
                    <div class="field">
                        <label for="firstName">First Name</label>
                        <input type="text" id="firstName" name="firstName" value="${firstName}" required>
                    </div>
                    <div class="field">
                        <label for="lastName">Last Name</label>
                        <input type="text" id="lastName" name="lastName" value="${lastName}" required>
                    </div>
                </div>

                <div class="field">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${email}" required>
                </div>

                <div class="field">
                    <label for="phoneNumber">Phone Number</label>
                    <input type="tel" id="phoneNumber" name="phoneNumber" value="${phoneNumber}">
                </div>

                <div class="grid grid--2">
                    <div class="field">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required minlength="8">
                        <p class="field-hint">At least 8 characters.</p>
                    </div>
                    <div class="field">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required minlength="8">
                    </div>
                </div>

                <button type="submit" class="btn btn--primary btn--block">Create Account</button>
            </form>

            <p style="text-align:center; margin-top: var(--space-4); font-size: var(--text-sm);">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Log in here</a>
            </p>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>