<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<c:if test="${exceptionLogList ne null}">
    <table border="1">
        <tr>
            <th>Exception Class</th>
            <th>Exception Message</th>
            <th>Date/Time</th>
            <th>User</th>
        </tr>

        <c:forEach items="${exceptionLogList}" var="exc">		
            <tr>
                <td>${ exc.exceptionClass }</td>
                <td>${ exc.exceptionMessage }</td>
                <td>${ exc.exceptionDateTime }</td>
                <td>${ exc.user }</td> 
            </tr>
        </c:forEach>
    </table>
    <c:if test="${exceptionLogDetail ne null}">
        <h2>Exception log detail</h2>
        <table border="1">
            <tr>
                <th>Class Name</th>
                <th>Method Name</th>
                <th>Line Num</th>
            </tr>
            <tr>
                <td>${ exceptionLogDetail.className }</td>
                <td>${ exceptionLogDetail.methodName }</td>
                <td>${ exceptionLogDetail.lineNumber }</td>
            </tr>
        </table>
    </c:if>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp"%>