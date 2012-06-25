<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
    <li class="first"><a
            href="${pageContext.request.contextPath}/admin"><spring:message
                code="admin.title.short" /></a></li>

    <li
        <c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
        <a
            href="${pageContext.request.contextPath}/module/errorlogging/manage.form"><spring:message
                code="errorlogging.manage" /></a>
    </li>
    <li
        <c:if test='<%= request.getRequestURI().contains("/viewErrors") %>'>class="active"</c:if>>
        <a
            href="${pageContext.request.contextPath}/module/errorlogging/viewErrors.form"><spring:message
                code="errorlogging.viewErrors" /></a>
    </li>

    <!-- Add further links here -->
</ul>
<h2>
    <c:if test='<%= request.getRequestURI().contains("/manage") %>'>
        <spring:message code="errorlogging.manage" />
    </c:if>
    <c:if test='<%= request.getRequestURI().contains("/viewErrors") %>'>
        <spring:message code="errorlogging.viewErrors" />
    </c:if>
</h2>
