<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<openmrs:htmlInclude file="/dwr/util.js" />

<script src="<openmrs:contextPath/>/dwr/interface/DWRExceptionLogService.js"></script>

<style>
    textarea#errors {
        width: 600px;
        height: 120px;
        padding: 5px;
    }
    .successSave{
        background-color: lightyellow;
    }
    .errorSave{
        background-color: lightpink;
    }
    #successOrErrorSave{
        float:left;
        display: none;
        margin-left: 10px;
        padding: 5px;
        border: 1px dashed lightgrey;        
    }
</style>

<div style="margin-bottom: 5px;"><spring:message code="errorlogging.enter.ignored.errors" /></div>

<div style=" float: left;">
    <textarea id="errors"></textarea>
</div>

<div id="successOrErrorSave"></div>

<div style="clear: both;">
    <input type="button" id="saveIgnoredErrors" style="margin-left: auto; margin-top: 10px;" value="<spring:message code="errorlogging.ignredExceptions.save" />" onclick="saveIgnoredErrors()"/>
</div>

<script type="text/javascript">
    $j(document).ready(function(){
        dwr.engine.beginBatch();
        fillTextArea();
        dwr.engine.endBatch();
    });
            
    function saveIgnoredErrors(){
        var ignoredErrors = document.getElementById("errors").value;
        DWRExceptionLogService.saveIgnoredErrors(ignoredErrors, function(success){
            if(success == true){
                $j("#successOrErrorSave").replaceWith('<div id ="successOrErrorSave"><p><spring:message code="errorlogging.ignredExceptions.successSaveMessage" /></p> </div>');
                $j("#successOrErrorSave").addClass("successSave");
                
               
            } else{
                $j("#successOrErrorSave").replaceWith('<div id ="successOrErrorSave"><p><spring:message code="errorlogging.ignredExceptions.errorSaveMessage" /></p> </div>');
                $j("#successOrErrorSave").addClass("errorSave");
            }
            $j("#successOrErrorSave").fadeIn(2000);
            $j("#successOrErrorSave").fadeOut(2000);
        });
    }
    
    function fillTextArea(){
        DWRExceptionLogService.getIgnoredErrors(function(ignoredErrors){
            dwr.util.setValue("errors", ignoredErrors); 
        });
    }
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>