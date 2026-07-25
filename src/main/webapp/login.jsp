<%-- File: WebContent/login.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Log In - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-public.jsp"/>

<section class="band" style="min-height: 60vh; display:flex; align-items:center;">
    <div class="container">
        <div class="form-card">
            <h1 style="font-size: var(--text-2xl); text-align:center;">Welcome Back</h1>
            <p style="text-align:center; color: var(--color-ink-muted);">Log in to manage your appointments.</p>

            <c:if test="${not empty successMessage}">
                <div class="alert alert--success" data-dismissible><c:out value="${successMessage}"/></div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert--error" data-dismissible><c:out value="${errorMessage}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" name="redirect" value="${param.redirect}">

                <div class="field">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="${submittedEmail}" required>
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>

                <button type="submit" class="btn btn--primary btn--block">Log In</button>
            </form>

            <p style="text-align:center; margin-top: var(--space-4); font-size: var(--text-sm);">
                Do not have an account? <a href="${pageContext.request.contextPath}/register">Register here</a>
            </p>
        </div>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>