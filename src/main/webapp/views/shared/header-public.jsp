<%-- File: WebContent/views/shared/header-public.jsp --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<nav class="topnav">
    <div class="topnav__inner">
        <a class="topnav__brand" href="${pageContext.request.contextPath}/index.jsp">Meridian Health</a>
        <button class="topnav__toggle" aria-label="Toggle navigation">&#9776;</button>
        <ul class="topnav__links">
            <li><a href="${pageContext.request.contextPath}/index.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/login">Log In</a></li>
            <li><a href="${pageContext.request.contextPath}/register" class="btn btn--primary btn--sm" style="color: #FFFFFF;">Register</a></li>
        </ul>
    </div>
</nav>