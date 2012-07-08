<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/moduleResources/errorlogging/viewErrors.css"/>

<script src="<openmrs:contextPath/>/dwr/interface/DWRExceptionLogService.js"></script>
<style>
   
</style>

<b class="boxHeader"><spring:message code="errorlogging.querytools.title" /></b>
<div class="box" id="querytoolsbox">
    <form name="querytools">
        <table>
            <tr>
                <td>Class:</td>
                <td>
                    <input type="text" id="exceptionLogClass" name="exceptionLogClass"/>
                </td>
            </tr>
            <tr>
                <td>Date:<br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="general.format"/>: <openmrs:datePattern />)</i></td>
                <td valign="top">
                    <input type="text" id="exceptionLogDate" name="exceptionLogDate" onfocus="showCalendar(this,60)" />            
                </td>
            </tr>
            <tr>
                <td>Time:<br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="general.format"/>: hh:mm:ss)</i></td>
                <td valign="top">
                    <input type="text" id="exceptionLogTime" name="exceptionLogTime" />  
                </td>
            </tr>    
        </table>       
    </form>
</div>


<div>
    <input type="button" value="Show"  style="margin-left: auto; margin-top: 10px;"onclick="fillTable('show')"/>
</div>

<div id="tablenav" style="margin-top: 15px; margin-bottom: 5px;">
    <form name="tablenavigation">
        <div>
            <label for="onPage">On page:</label>
            <input type="text" id="onPage"  style="width: 25px;" name="onPage" value="5"/>

            <input type="button" id="begin_exceptionlog" value="Begin" disabled="true" onclick="fillTable('begin')"/>
            <input type="button" id="prev_exceptionlog" value="Prev" disabled="true" onclick="fillTable('prev')"/>
            <input type="button" id="next_exceptionlog" value="Next" disabled="true" onclick="fillTable('next')"/>
            <input type="button" id="end_exceptionlog" value="End" disabled="true" onclick="fillTable('end')"/>
        </div>

    </form>
</div>

<div id="exceptionLogs"  style="display:none;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionLogTable.title" /></b>
    <div class="box">
        <table id="exceptionLogTable" cellpadding="0" cellspacing="0" width="100%" >
            <thead>
                <tr class="top">
                    <th>Exception Class</th>
                    <th>Exception Message</th>
                    <th>OpenMRS<br/>Version</th>
                    <th>Date/Time</th>
                    <th>User</th>
                    <th>Detail</th>
                    <th>Root<br/>Cause</th>
                </tr>
            </thead>
            <tbody id="elbody">
                <tr id="elpattern" style="display:none;">
                    <td><span id="tableExceptionLogClass">Exception Class</span></td>
                    <td><span id="tableExceptionLogMessage">Exception Message</span></td>
                    <td><span id="tableExceptionLogOpenMRSVersion">OpenMRS Version</span></td>
                    <td><span id="tableExceptionLogDateTime">Date/Time</span></td>  
                    <td><span id="tableExceptionUser">User</span></td>
                    <td><span id="tableExceptionDetail" class="viewLink" onclick="showExcLogDetail(this.id)">View</span></td>
                    <td><span id="tableExceptionRootCause" class="viewLink" onclick="showExcRootCause(this.id)">View</span></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="exceptionLogDetail"  style="display:none; margin-top: 10px;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionLogDetailTable.title" /></b>
    <div class="box">
        <table id="exceptionLogDetailTable" cellpadding="0" cellspacing="0" width="100%">
            <thead>
                <tr class="trTdExcLogClass trTop">
                    <th>Class Name</th>
                    <th>Method Name</th>
                    <th>Line Number</th>
                </tr>
            </thead>
            <tbody id="eldbody">
                <tr id="eldpattern" class="trTdExcLogClass" style="display:none;">
                    <td><span id="tableExceptionLogClassName">Class Name</span></td>
                    <td><span id="tableExceptionLogMethodName">Method Name</span></td>
                    <td><span id="tableExceptionLogLineNumber">Line Number</span></td>              
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="exceptionRootCause"  style="display:none; margin-top: 10px;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionRootCauseTable.title" /></b>
    <div class="box">
        <table id="exceptionRootCauseTable" cellpadding="0" cellspacing="0" width="100%">
            <thead>
                <tr class="trTdExcLogClass trTop">
                    <th>Root Cause Class</th>
                    <th>Root Cause Message</th>
                    <th>Detail</th>
                </tr>
            </thead>
            <tbody id="ercbody">
                <tr id="ercpattern" class="trTdExcLogClass" style="display:none;">
                    <td><span id="tableExceptionRCClass">Root Cause Class</span></td>
                    <td><span id="tableExceptionRCMessage">Root Cause Message</span></td>
                    <td><span id="tableExceptionRCDetail" class="viewLink" onclick="showExcRCDetail(this.id)">View</span></td>           
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var lastSearch = 0; 
    var prevExcLogDetail;
    var prevExcRootCause;
    function fillTable(prevOrNext) {
        var start;        
        var form = document.forms['querytools'];
        var excClass = form.elements['exceptionLogClass'].value;
        var onPage = parseInt(document.forms['tablenavigation'].elements['onPage'].value);

        var dateString = form.elements['exceptionLogDate'].value;
        var timeString = form.elements['exceptionLogTime'].value;   
        
        prevExcLogDetail = undefined;
        prevExcRootCause = undefined;
        $("exceptionLogDetail").style.display = "none";
        $("exceptionRootCause").style.display = "none";
        
        if(prevOrNext == 'next'){
            start = lastSearch;
        }else if(prevOrNext == 'prev'){
            start = lastSearch - 2 * onPage;
            if(start < 0){
                start = 0;
            }
            lastSearch = start;
        }else if(prevOrNext == 'end'){
            lastSearch = count - (count % onPage);
            start = lastSearch;
        }else{
            lastSearch = 0;
            start = 0;
        }    
        
        dwr.engine.beginBatch();
        getCount(excClass, dateString, timeString);

        DWRExceptionLogService.getExceptionLogs(excClass, dateString, timeString, start, onPage, function(exceptionLogs) {
            var isEmpty;
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("elbody", { filter:function(tr) {return (tr.id != "elpattern");}});
            // Create a new set cloned from the pattern row
            var exceptionLog, exceptionLogId;
            if(exceptionLogs.length > 0){
                for (var i = 0; i < exceptionLogs.length; i++) {
                    exceptionLog = exceptionLogs[i];
                    exceptionLogId = exceptionLog.exceptionLogId;
                    dwr.util.cloneNode("elpattern", { idSuffix:exceptionLogId });
                    dwr.util.setValue("tableExceptionLogClass" + exceptionLogId, exceptionLog.exceptionClass);
                    dwr.util.setValue("tableExceptionLogMessage" + exceptionLogId, exceptionLog.exceptionMessage);
                    dwr.util.setValue("tableExceptionLogOpenMRSVersion" + exceptionLogId, exceptionLog.openmrsVersion);
                    dwr.util.setValue("tableExceptionLogDateTime" + exceptionLogId, exceptionLog.exceptionDateTime);
                    dwr.util.setValue("tableExceptionUser" + exceptionLogId, exceptionLog.user);
                    $("elpattern" + exceptionLogId).style.display = "table-row";
                    if(i % 2 == 0){
                        $("elpattern" + exceptionLogId).className = "even";
                    }
                }
                $("exceptionLogs").style.display = "block";
                isEmpty=false;                
            } else{
                $("exceptionLogs").style.display = "none";
                isEmpty=true;               
            }
            
            lastSearch += onPage;       
        
            if(lastSearch > count){               
                $("next_exceptionlog").disabled = true;
                $("end_exceptionlog").disabled = true;
            }else{
                $("next_exceptionlog").disabled = false;
                $("end_exceptionlog").disabled = false;
            }
            
            if(isEmpty == false){
                if((lastSearch - 2 * onPage) >= 0){
                    $("prev_exceptionlog").disabled = false;
                    $("begin_exceptionlog").disabled = false;
                }else{
                    $("prev_exceptionlog").disabled = true;
                    $("begin_exceptionlog").disabled = true;
                }
            }else{
                $("begin_exceptionlog").disabled = true;
                $("prev_exceptionlog").disabled = true;
                $("next_exceptionlog").disabled = true;
                $("end_exceptionlog").disabled = true;
                
            }
        });   
        dwr.engine.endBatch();
    }
    
    function getCount(excClass, dateString, timeString){
        DWRExceptionLogService.getCountOfExceptionLogs(excClass, dateString, timeString, function(countOfExcLogs){
            window.count = countOfExcLogs;
        });
    }
    
    function showExcLogDetail(excLogId){
        var len = ("tableExceptionDetail").length;
        var id = excLogId.toString().substr(len, excLogId.toString().length-len);
        DWRExceptionLogService.getExceptionLogdetail(id,function(excLogDetail){
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("eldbody", { filter:function(tr) {return (tr.id != "eldpattern");}});
            // Create a new set cloned from the pattern row
            if($("exceptionLogDetail").style.display == "block" && prevExcLogDetail == excLogId){
                $("exceptionLogDetail").style.display = "none";
                $("tableExceptionDetail"+id).setAttribute("class", "viewLink");
            }else{
                if(excLogDetail != null){           
                    dwr.util.cloneNode("eldpattern", { idSuffix:id });
                    dwr.util.setValue("tableExceptionLogClassName" + id, excLogDetail.className);
                    dwr.util.setValue("tableExceptionLogMethodName" + id, excLogDetail.methodName);
                    dwr.util.setValue("tableExceptionLogLineNumber" + id, excLogDetail.lineNumber);          
                    $("eldpattern" + id).style.display = "table-row";
                    $("exceptionLogDetail").style.display = "block";
                    $("tableExceptionDetail"+id).setAttribute("class", "viewLinkClick");
                    if(prevExcLogDetail != excLogId && prevExcLogDetail != undefined){
                        $(prevExcLogDetail).setAttribute("class", "viewLink");                  
                    }
                    prevExcLogDetail = excLogId;                   
                }
            }
        });    
    }
    
    function showExcRootCause(excLogId){
        var len = ("tableExceptionRootCause").length;
        var id = excLogId.toString().substr(len, excLogId.toString().length-len);
        DWRExceptionLogService.getExceptionRootCause(id,function(excRootCause){
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("ercbody", { filter:function(tr) {return (tr.id != "ercpattern");}});
            // Create a new set cloned from the pattern row
            if($("exceptionRootCause").style.display == "block" && prevExcRootCause==excLogId){
                $("exceptionRootCause").style.display = "none";
                $("tableExceptionRootCause"+id).setAttribute("class", "viewLink");
            }else{
                if(excRootCause != null){           
                    dwr.util.cloneNode("ercpattern", { idSuffix:id });
                    dwr.util.setValue("tableExceptionRCClass" + id, excRootCause.exceptionClass);
                    dwr.util.setValue("tableExceptionRCMessage" + id, excRootCause.exceptionMessage);
                    $("ercpattern" + id).style.display = "table-row";
                    $("exceptionRootCause").style.display = "block";
                    $("tableExceptionRootCause"+id).setAttribute("class", "viewLinkClick");
                    if(prevExcRootCause != excLogId && prevExcRootCause != undefined){
                        $(prevExcRootCause).setAttribute("class", "viewLink");                  
                    }
                    prevExcRootCause = excLogId;
                }
            }
        });    
    }
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>