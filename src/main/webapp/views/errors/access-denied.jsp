<%-- File: WebContent/views/errors/access-denied.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-public.jsp"/>

<section class="band" style="min-height: 60vh; display:flex; align-items:center;">
    <div class="container" style="text-align:center;">
        <p style="font-family: var(--font-heading); font-size: var(--text-4xl); color: var(--color-warning); margin-bottom: var(--space-2);">403</p>
        <h1>You do not have access to this page</h1>
        <p style="color: var(--color-ink-muted); max-width: 480px; margin: 0 auto var(--space-5);">
            This area is restricted to a different account type, or your session may have expired.
        </p>
        <a class="btn btn--primary" href="${pageContext.request.contextPath}/login">Log In Again</a>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
</body>
</html>