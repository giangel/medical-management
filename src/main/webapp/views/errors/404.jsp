<%-- File: WebContent/views/errors/404.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page Not Found - Meridian Health</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>
<body>

<jsp:include page="/views/shared/header-public.jsp"/>

<section class="band" style="min-height: 60vh; display:flex; align-items:center;">
    <div class="container" style="text-align:center;">
        <p style="font-family: var(--font-heading); font-size: var(--text-4xl); color: var(--color-primary); margin-bottom: var(--space-2);">404</p>
        <h1>We could not find that page</h1>
        <p style="color: var(--color-ink-muted); max-width: 480px; margin: 0 auto var(--space-5);">
            The page you are looking for may have moved, or the link may be out of date.
        </p>
        <a class="btn btn--primary" href="${pageContext.request.contextPath}/index.jsp">Return Home</a>
    </div>
</section>

<jsp:include page="/views/shared/footer.jsp"/>
</body>
</html>