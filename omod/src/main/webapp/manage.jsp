<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<style>
    textarea#exceptions {
        width: 600px;
        height: 120px;
        padding: 5px;
    }
</style>

<div style="margin-bottom: 5px;"><spring:message code="errorlogging.enter.ignored.errors" /></div>
<form method="POST" action="">
    <div style=" float: left;">
        <textarea id="exceptions" name="exceptions">${ignoredExceptions}</textarea>
    </div>
    <div style="clear: both;">
        <input type="submit" id="saveIgnoredExceptions" style="margin-left: auto; margin-top: 10px;" value="<spring:message code="errorlogging.ignredExceptions.save" />"/>
    </div>
 </form>

<%@ include file="/WEB-INF/template/footer.jsp"%>