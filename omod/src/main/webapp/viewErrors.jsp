<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/timepicker/timepicker.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/moduleResources/errorlogging/viewErrors.css"/>

<script src="<openmrs:contextPath/>/dwr/interface/DWRExceptionLogService.js"></script>

<b class="boxHeader"><spring:message code="errorlogging.querytools.title" /></b>
<div class="box" id="querytoolsbox">
    <form name="querytools">
        <table>
            <tr>
                <td><spring:message code="errorlogging.querytools.class" />:</td>
                <td>
                    <input type="text" id="exceptionLogClass" name="exceptionLogClass"/>
                </td>
            </tr>
            <tr>
                <td><spring:message code="errorlogging.querytools.since" />:<br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="general.format"/>: <spring:message code="errorlogging.dateTimeFormat" />)</i></td>
                <td valign="top">
                    <input type="text" id="exceptionLogStartDateTime" name="exceptionLogStartDateTime" onfocus="showDateTimePicker(this)" />            
                </td>
            </tr>
            <tr>
                <td><spring:message code="errorlogging.querytools.to" />:<br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="general.format"/>: <spring:message code="errorlogging.dateTimeFormat" />)</i></td>
                <td valign="top">
                    <input type="text" id="exceptionLogEndDateTime" name="exceptionLogEndDateTime" onfocus="showDateTimePicker(this)"/>  
                </td>
            </tr>    
        </table>       
    </form>
</div>

<div>
    <input type="button" value="<spring:message code="errorlogging.show" />"  style="margin-left: auto; margin-top: 10px;" onclick="fillTable('show')"/>
</div>

<div id="tablenav" style="margin-top: 15px; margin-bottom: 5px;">
    <form name="tablenavigation">
        <div>
            <label for="onPage"><spring:message code="errorlogging.tableNavigation.onPage" />:</label>
            <input type="text" id="onPage"  style="width: 25px;" name="onPage" value="10"/>

            <input type="button" id="beginExceptionlog" value="<<" title="<spring:message code="errorlogging.tableNavigation.begin" />" disabled="true" onclick="fillTable('begin')"/>
            <input type="button" id="prevExceptionlog" value="<" title="<spring:message code="errorlogging.tableNavigation.end" />" disabled="true" onclick="fillTable('prev')"/>

            <label for="pageNum"><spring:message code="errorlogging.tableNavigation.page" />:</label>
            <select id="pageNum" name="pageNum" style="width : 45px;" disabled="true" onclick="fillTable('page')">
            </select>

            <input type="button" id="nextExceptionlog" value=">" title="<spring:message code="errorlogging.tableNavigation.next" />" disabled="true" onclick="fillTable('next')"/>
            <input type="button" id="endExceptionlog" value=">>" title="<spring:message code="errorlogging.tableNavigation.end" />" disabled="true" onclick="fillTable('end')"/>
            <input type="button" id="removeExcLogs" value="<spring:message code="errorlogging.tableNavigation.removeSelected" />" disabled="true" style="float: right; margin-right: auto;" onclick="removeSelectedExcLogs()"/>
        </div>	
    </form>
</div>

<div id="exceptionLogs"  style="display:none;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionLogTable.title" /></b>
    <div class="box">
        <table id="exceptionLogTable" cellpadding="0" cellspacing="0" width="100%" >
            <thead>
                <tr class="top">
                    <th><input type="checkbox" id="selectAllExcLogs" name="selectAllExcLogs"></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.class" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.message" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.openMRSVersion" htmlEscape="false" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.dateTime" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.user" /></th>
                    <th><spring:message code="errorlogging.detailLink" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.rootCause" htmlEscape="false" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.report" /></th>
                </tr>
            </thead>
            <tbody id="elbody">
                <tr id="elpattern" style="display:none;">
                    <td><input type="checkbox" id="tableExceptionLogSelect" class="tableExceptionLogSelect" onclick="selectTableExcLogCheckBox()"/></td>
                    <td><span id="tableExceptionLogClass">Exception Class</span></td>
                    <td><span id="tableExceptionLogMessage">Exception Message</span></td>
                    <td><span id="tableExceptionLogOpenMRSVersion">OpenMRS Version</span></td>
                    <td><span id="tableExceptionLogDateTime">Date/Time</span></td>  
                    <td><span id="tableExceptionUser">User</span></td>
                    <td><span id="tableExceptionDetail" class="viewLink" style="display: none;" onclick="showExcLogDetail(this.id)"><spring:message code="errorlogging.viewLink" /></span></td>
                    <td><span id="tableExceptionRootCause" class="viewLink" style="display: none;" onclick="showExcRootCause(this.id)"><spring:message code="errorlogging.viewLink" /></span></td>
                    <td><input type="button" id="tableExceptionLogReportBtn" value="<spring:message code="errorlogging.exceptionLogTable.report" />"/></td>
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
                    <th><spring:message code="errorlogging.detailTables.file" /></th>
                    <th><spring:message code="errorlogging.detailTables.class" /></th>
                    <th><spring:message code="errorlogging.detailTables.method" /></th>
                    <th><spring:message code="errorlogging.detailTables.lineNumber" /></th>
                </tr>
            </thead>
            <tbody id="eldbody">
                <tr id="eldpattern" class="trTdExcLogClass" style="display:none;">
                    <td><span id="tableExceptionLogFileName">File Name</span></td>
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
                    <th><spring:message code="errorlogging.exceptionRootCauseTable.class" /></th>
                    <th><spring:message code="errorlogging.exceptionRootCauseTable.message" /></th>
                    <th><spring:message code="errorlogging.detailLink" /></th>
                </tr>
            </thead>
            <tbody id="ercbody">
                <tr id="ercpattern" class="trTdExcLogClass" style="display:none;">
                    <td><span id="tableExceptionRCClass">Root Cause Class</span></td>
                    <td><span id="tableExceptionRCMessage">Root Cause Message</span></td>
                    <td><span id="tableExceptionRCDetail" class="viewLink" style="display: none;" onclick="showExcRCDetail(this.id)"><spring:message code="errorlogging.viewLink" /></span></td>           
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div id="exceptionRootCauseDetail"  style="display:none; margin-top: 10px;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionRootCauseDetailTable.title" /></b>
    <div class="box">
        <table id="exceptionRootCauseDetailTable" cellpadding="0" cellspacing="0" width="100%">
            <thead>
                <tr class="trTdExcLogClass trTop">
                    <th><spring:message code="errorlogging.detailTables.file" /></th>
                    <th><spring:message code="errorlogging.detailTables.class" /></th>
                    <th><spring:message code="errorlogging.detailTables.method" /></th>
                    <th><spring:message code="errorlogging.detailTables.lineNumber" /></th>
                </tr>
            </thead>
            <tbody id="ercdbody">
                <tr id="ercdpattern" class="trTdExcLogClass" style="display:none;">
                    <td><span id="tableExceptionRCDetailFileName">File Name</span></td>
                    <td><span id="tableExceptionRCDetailClassName">Class Name</span></td>
                    <td><span id="tableExceptionRCDetailMethodName">Method Name</span></td>
                    <td><span id="tableExceptionRCDetailLineNumber">Line Number</span></td>              
                </tr>
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var lastSearch = 0; 
    var prevonPage;
    var prevExcLogDetail;
    var prevExcRootCause;
    var count;
    function fillTable(prevOrNext) {
        var start;        
        var form = document.forms['querytools'];
        var excClass = form.elements['exceptionLogClass'].value;
        var onPage = parseInt(document.forms['tablenavigation'].elements['onPage'].value, 10);
        if( prevonPage != undefined && prevonPage != onPage && prevOrNext != "show"){
            onPage = prevonPage;
            document.forms['tablenavigation'].elements['onPage'].value = onPage;
        }
        if(isNaN(onPage)){
            alert("<spring:message code="errorlogging.tableNavigation.onPage.wrongValueMessage" />");
            return;
        }
        var startDateTimeString = form.elements['exceptionLogStartDateTime'].value;
        var endDateTimeString = form.elements['exceptionLogEndDateTime'].value;   
        
        prevExcLogDetail = undefined;
        prevExcRootCause = undefined;
        $("exceptionLogDetail").style.display = "none";
        $("exceptionRootCause").style.display = "none";
        
        if(prevOrNext == 'next'){
            start = lastSearch;
            var currPage = parseInt($j("#pageNum option:selected").text());
            $("pageNum").selectedIndex = currPage;            
        }else if(prevOrNext == 'prev'){
            start = lastSearch - 2 * onPage;
            if(start < 0){
                start = 0;
            }
            lastSearch = start;
            var currPage = parseInt($j("#pageNum option:selected").text());
            $("pageNum").selectedIndex = currPage - 2;         
        }else if(prevOrNext == 'end'){
            var leftover = count % onPage;
            if(leftover > 0){
                lastSearch = count - leftover;
            }else{
                lastSearch = count - onPage;
            }
            start = lastSearch;
        }else if(prevOrNext == "page"){
            var page = parseInt($j("#pageNum option:selected").text());
            lastSearch = onPage * page - onPage;
            start = lastSearch;
        }else{
            lastSearch = 0;
            start = 0;
            prevonPage = onPage;
        }    
        
        dwr.engine.beginBatch();
        getCount(excClass, startDateTimeString, endDateTimeString);

        DWRExceptionLogService.getExceptionLogs(excClass, startDateTimeString, endDateTimeString, start, onPage, function(exceptionLogs) {
            var isEmpty;
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("elbody", { filter:function(tr) {return (tr.id != "elpattern");}});
            // Create a new set cloned from the pattern row
            var exceptionLog, exceptionLogId;
            if(exceptionLogs.length > 0){
                for (var i = 0; i < exceptionLogs.length; i++) {
                    exceptionLog = exceptionLogs[i];
                    exceptionLogId = exceptionLog.id;
                    dwr.util.cloneNode("elpattern", { idSuffix:exceptionLogId });
                    dwr.util.setValue("tableExceptionLogClass" + exceptionLogId, exceptionLog.exceptionClass);
                    dwr.util.setValue("tableExceptionLogMessage" + exceptionLogId, exceptionLog.exceptionMessage);
                    dwr.util.setValue("tableExceptionLogOpenMRSVersion" + exceptionLogId, exceptionLog.openmrsVersion);
                    dwr.util.setValue("tableExceptionLogDateTime" + exceptionLogId, exceptionLog.exceptionDateTime);
                    dwr.util.setValue("tableExceptionUser" + exceptionLogId, exceptionLog.user);
                    $("tableExceptionLogSelect" + exceptionLogId).name = exceptionLogId;
                    $("elpattern" + exceptionLogId).style.display = "table-row";
                    if(exceptionLog.hasExceptionLogDetail == true){
                        $("tableExceptionDetail" + exceptionLogId).style.display = "block"; 
                    }
                    if(exceptionLog.hasRootCause){
                        $("tableExceptionRootCause" + exceptionLogId).style.display = "block"; 
                    }
                    if(i % 2 == 0){
                        $("elpattern" + exceptionLogId).className = "even";
                    }else{
                        $("elpattern" + exceptionLogId).className = "odd";
                    }
                }
                $("exceptionLogs").style.display = "block";
                isEmpty=false;                
            } else{
                $("exceptionLogs").style.display = "none";
                isEmpty=true;               
            }
            
            lastSearch += onPage;    
        
            if(lastSearch >= count){               
                $("nextExceptionlog").disabled = true;
                $("endExceptionlog").disabled = true;
            }else{
                $("nextExceptionlog").disabled = false;
                $("endExceptionlog").disabled = false;
            }
            
            if(isEmpty == false){
                if(prevOrNext == 'show'){
                    $j('#pageNum >option').remove();
                    $("pageNum").disabled = false;
                    var pages = Math.floor(count / onPage);
                    var leftover = count % onPage;
                    if(leftover > 0){
                        pages += 1;
                    }
                    for (var i = 0; i < pages; i++) {
                        $("pageNum").options[i] = new Option (i+1, i+1);
                    }
                }

                if((lastSearch - 2 * onPage) >= 0){
                    $("prevExceptionlog").disabled = false;
                    $("beginExceptionlog").disabled = false;
                }else{
                    $("prevExceptionlog").disabled = true;
                    $("beginExceptionlog").disabled = true;
                }                                
            }else{
                $("beginExceptionlog").disabled = true;
                $("prevExceptionlog").disabled = true;
                $("nextExceptionlog").disabled = true;
                $("endExceptionlog").disabled = true;
                $("pageNum").disabled = true;
                
            }
        });   
        dwr.engine.endBatch();
    }
    
    function getCount(excClass, startDateTimeString, endDateTimeString){
        DWRExceptionLogService.getCountOfExceptionLogs(excClass, startDateTimeString, endDateTimeString, function(countOfExcLogs){
            count = countOfExcLogs;
        });
    }
    
    function showExcLogDetail(excLogId){
        var len = ("tableExceptionDetail").length;
        var id = excLogId.toString().substr(len, excLogId.toString().length-len);
        DWRExceptionLogService.getExceptionLogDetail(id, function(excLogDetail){
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("eldbody", { filter:function(tr) {return (tr.id != "eldpattern");}});
            // Create a new set cloned from the pattern row
            if($("exceptionLogDetail").style.display == "block" && prevExcLogDetail == excLogId){
                $("exceptionLogDetail").style.display = "none";
                $("tableExceptionDetail"+id).setAttribute("class", "viewLink");
            }else{
                if(excLogDetail != null){           
                    dwr.util.cloneNode("eldpattern", { idSuffix:id });
                    dwr.util.setValue("tableExceptionLogFileName" + id, excLogDetail.fileName);
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
        DWRExceptionLogService.getExceptionRootCause(id, function(excRootCause){
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("ercbody", { filter:function(tr) {return (tr.id != "ercpattern");}});
            // Create a new set cloned from the pattern row
            if($("exceptionRootCause").style.display == "block" && prevExcRootCause==excLogId){
                $("exceptionRootCause").style.display = "none";
                $("exceptionRootCauseDetail").style.display = "none";
                $("tableExceptionRootCause"+id).setAttribute("class", "viewLink");
            }else{
                if(excRootCause != null){           
                    dwr.util.cloneNode("ercpattern", { idSuffix:id });
                    dwr.util.setValue("tableExceptionRCClass" + id, excRootCause.exceptionClass);
                    dwr.util.setValue("tableExceptionRCMessage" + id, excRootCause.exceptionMessage);
                    $("ercpattern" + id).style.display = "table-row";
                    $("exceptionRootCause").style.display = "block";
                    $("tableExceptionRootCause"+id).setAttribute("class", "viewLinkClick");
                    if(excRootCause.hasRootCauseDetail){
                        $("tableExceptionRCDetail" +id).style.display = "block";
                    }
                    if(prevExcRootCause != excLogId && prevExcRootCause != undefined){
                        $(prevExcRootCause).setAttribute("class", "viewLink");                  
                    }
                    prevExcRootCause = excLogId;
                }
            }
        });    
    }
    
    function showExcRCDetail(excLogId){
        var len = ("tableExceptionRCDetail").length;
        var id = excLogId.toString().substr(len, excLogId.toString().length-len);
        DWRExceptionLogService.getExceptionRootCauseDetail(id, function(excRootCauseDetail){
            // Delete all the rows except for the "pattern" row
            dwr.util.removeAllRows("ercdbody", { filter:function(tr) {return (tr.id != "ercdpattern");}});
            // Create a new set cloned from the pattern row
            if($("exceptionRootCauseDetail").style.display == "block"){
                $("exceptionRootCauseDetail").style.display = "none";
                $("tableExceptionRCDetail"+id).setAttribute("class", "viewLink");
            }else{
                if(excRootCauseDetail != null){           
                    dwr.util.cloneNode("ercdpattern", { idSuffix:id });
                    dwr.util.setValue("tableExceptionRCDetailFileName" + id, excRootCauseDetail.fileName);
                    dwr.util.setValue("tableExceptionRCDetailClassName" + id, excRootCauseDetail.className);
                    dwr.util.setValue("tableExceptionRCDetailMethodName" + id, excRootCauseDetail.methodName);
                    dwr.util.setValue("tableExceptionRCDetailLineNumber" + id, excRootCauseDetail.lineNumber);          
                    $("ercdpattern" + id).style.display = "table-row";
                    $("exceptionRootCauseDetail").style.display = "block";
                    $("tableExceptionRCDetail"+id).setAttribute("class", "viewLinkClick");                                    
                }
            }
        });    
    }  
    
    $j(function(){
        // add multiple select / deselect functionality
        $j("#selectAllExcLogs").click(function () {
            $j('.tableExceptionLogSelect').attr('checked', this.checked);
            showHideRemoveButton();
        });        
    });
    
    $j(document).ready(function(){
        $j("tr.odd, tr.even").live("hover",function(){
            var a = $j(this);
            a.toggleClass("trHover");
            a.children().toggleClass("trHover");
        })
    });  
    
    function removeSelectedExcLogs(){
        var selected_cb = [];
        selected_cb = $j("#exceptionLogTable").find("input:checkbox:checked:not('#selectAllExcLogs')");
        var chklength = selected_cb.length; 
        if(chklength == 0){
            return;
        }
        var agree = confirm("<spring:message code="errorlogging.tableNavigation.removeSelected.confirmMessage" />");
        if (!agree){
            return;
        }
        var res = [];
        for(var i = 0; i < chklength; i++)
        {
            var id = parseInt(selected_cb[i].name, 10);
            if(isNaN(id)){
                return;
            }
            res[i] = id;
        }
        if(res.length > 0){
            dwr.engine.beginBatch();
            DWRExceptionLogService.purgeExceptionLogs(res, function(result){
                if(result){
                    fillTable("show");
                    $("removeExcLogs").disabled = true;
                }
            });
            dwr.engine.endBatch();
        }
    }
    
    function selectTableExcLogCheckBox(){
        if($j(".tableExceptionLogSelect").length - 1 == $j(".tableExceptionLogSelect:checked").length) {
            $j("#selectAllExcLogs").attr("checked", "checked");               
        } else {
            $j("#selectAllExcLogs").removeAttr("checked");
        }
        showHideRemoveButton();
    }
    
    function showHideRemoveButton(){
        if($j("#exceptionLogTable").find("input:checkbox:checked:not('#selectAllExcLogs')").length > 0){
            $("removeExcLogs").disabled = false;
        }else{
            $("removeExcLogs").disabled = true;
        }               
    }
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>