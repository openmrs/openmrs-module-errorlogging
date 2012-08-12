<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="View Error Logging" otherwise="/login.htm" redirect="/module/errorlogging/viewErrors.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/timepicker/timepicker.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<openmrs:htmlInclude file="/moduleResources/errorlogging/css/viewErrors.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />

<script src="<openmrs:contextPath/>/dwr/interface/DWRExceptionLogService.js"></script>

<b class="boxHeader"><spring:message code="errorlogging.querytools.title" /></b>
<div class="box" id="querytoolsbox">
    <table class="querytable" cellspacing="5">            
        <tr>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.username" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogUser" name="exceptionLogUser"/>
                </div>
            </td>
            <td>
                <div>
                    <spring:message code="errorlogging.querytools.class" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogClass" name="exceptionLogClass" style="width: 250px;"/>
                </div>
            </td>
            <td>
                <div>
                    <spring:message code="errorlogging.querytools.message" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogMessage" name="exceptionLogMessage" style="width: 250px;"/>
                </div>
            </td>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.openmrsVersion" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogOpenMRSVersion" name="exceptionLogOpenMRSVersion"/>
                </div>
            </td>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.since" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogStartDateTime" name="exceptionLogStartDateTime" onfocus="showDateTimePicker(this)" />
                    <br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="errorlogging.dateTimeFormat" />)</i>
                </div>
            </td> 
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.to" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogEndDateTime" name="exceptionLogEndDateTime" onfocus="showDateTimePicker(this)"/> 
                    <br/><i style="font-weight: normal; font-size: 0.8em;">(<spring:message code="errorlogging.dateTimeFormat" />)</i>
                </div>
            </td>
        </tr>
    </table>
    <table class="querytable" cellspacing="5">
        <tr>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.fileName" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogFileName" name="exceptionLogFileName"/>
                </div>
            </td>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.methodName" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogMethodName" name="exceptionLogMethodName"/>
                </div>
            </td>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.lineNum" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogLineNum" name="exceptionLogLineNum"/>
                </div>
            </td>
            <td>
                <div class="title">
                    <spring:message code="errorlogging.querytools.frequency" />:
                </div>
                <div>
                    <input type="text" id="exceptionLogFrequency" name="exceptionLogFrequency"/>
                </div>
            </td>
        </tr> 
    </table>       
</div>

<div>
    <input type="button" id="showExceptionLogs" value="<spring:message code="errorlogging.show" />"  style="margin-left: auto; margin-top: 10px;"/>
</div>

<div id="exceptionLogs" style="clear: both; margin-top: 25px;">
    <b class="boxHeader"><spring:message code="errorlogging.exceptionLogTable.title" /></b>
    <div class="box">
        <table id="exceptionLogTable" cellpadding="0" cellspacing="0" width="100%" >
            <thead>
                <tr class="top">
                    <th><input type="checkbox" id="selectAllExcLogs" name="selectAllExcLogs"></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.class" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.message" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.openMRSVersion" htmlEscape="falshtmle" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.dateTime" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.user" /></th>
                    <th><spring:message code="errorlogging.detail" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.rootCause" htmlEscape="false" /></th>
                    <th><spring:message code="errorlogging.exceptionLogTable.report" /></th>
                </tr>
            </thead>
            <tbody id="elbody">
            </tbody>
        </table>
    </div>
</div>

<script type="text/javascript">
    var oTable;
    var username;
    var excClass;
    var excMessage;
    var excOpenMRSVersion    
    var excFileName;
    var excMethodName;
    var excLineNum;
    var excFrequency;
    var startDateTimeString;
    var endDateTimeString;
        
    $j(document).ready(function(){
        
        $j("tr.odd, tr.even").live("hover",function(){
            var a = $j(this);
            a.toggleClass("trHover");
            a.children().toggleClass("trHover");
        });                       
        
        $j('#showExceptionLogs').click(function() {
            fnDraw = true;
            username = $j('#exceptionLogUser').val();
            excClass = $j('#exceptionLogClass').val();
            excMessage =$j('#exceptionLogMessage').val();
            excOpenMRSVersion = $j('#exceptionLogOpenMRSVersion').val();
            excFileName = $j('#exceptionLogFileName').val();
            excMethodName = $j('#exceptionLogMethodName').val();
            excLineNum = $j('#exceptionLogLineNum').val();
            startDateTimeString = $j('#exceptionLogStartDateTime').val();
            endDateTimeString = $j('#exceptionLogEndDateTime').val();
            excFrequency =  $j('#exceptionLogFrequency').val();
            if(excLineNum != null && excLineNum != ''){      
                var intExcLineNum = parseInt(excLineNum, 10);
                if(isNaN(intExcLineNum)){
                    alert("<spring:message code="errorlogging.querytools.lineNum.wrongValueMessage" />");
                    return;
                }
            }
            if(excFrequency != null && excFrequency != ''){      
                var intexcFrequency = parseInt(excFrequency, 10);
                if(isNaN(intexcFrequency)){
                    alert("<spring:message code="errorlogging.querytools.frequency.wrongValueMessage" />");
                    return;
                }
                if(intexcFrequency < 1){
                    alert("<spring:message code="errorlogging.querytools.frequency.lessThanOneValueMessage" />");
                }
            }   
            if(oTable == undefined){
                oTable = $j('#exceptionLogTable').dataTable({            
                    "bSort": false,
                    "bFilter": false,
                    "bLengthChange": true,
                    "sPaginationType": "full_numbers",
                    "bServerSide" : true,
                    "bProcessing" : true,
                    "bJQueryUI": true,                     
                    "sDom": '<"ui-helper-clearfix tableTop"l<"removExcLogButton">>rt<"tableBottom"<"fg-button ui-helper-clearfix"ip>>',
                    "sAjaxSource" : '/openmrs/module/errorlogging/viewExceptionLogs.json',
                    "oLanguage": {
                        "oPaginate": {
                            "sFirst": "<spring:message code="errorlogging.exceptionLogTable.sFirst" />",
                            "sLast": "<spring:message code="errorlogging.exceptionLogTable.sLast" />",
                            "sNext": "<spring:message code="errorlogging.exceptionLogTable.sNext" />",
                            "sPrevious": "<spring:message code="errorlogging.exceptionLogTable.sPrevious" />"
                        },
                        "sEmptyTable": "<spring:message code="errorlogging.exceptionLogTable.sEmptyTable" />",
                        "sInfo": "<spring:message code="errorlogging.exceptionLogTable.sInfo" />",
                        "sInfoEmpty": "<spring:message code="errorlogging.exceptionLogTable.sInfoEmpty" />",
                        "sLengthMenu": "<spring:message code="errorlogging.exceptionLogTable.sLengthMenu" />",
                        "sProcessing": "<spring:message code="errorlogging.exceptionLogTable.sProcessing" />",
                        "sZeroRecords": "<spring:message code="errorlogging.exceptionLogTable.sZeroRecords" />"
                    },
                    "aoColumns": [
                        {"sWidth": "1%"},
                        {"sWidth": "25%" },
                        {"sWidth": "45%"},
                        {"sWidth": "10%"},
                        {"sWidth": "7%"},
                        {"sWidth": "7%"},
                        {"sWidth": "2%"},
                        {"sWidth": "2%"},
                        {},
                    ],
                    "fnServerData": function ( sSource, aoData, fnCallback ) {
                        aoData.push( { "name": "username", "value": username },
                        { "name": "excClass", "value": excClass },
                        { "name": "excMessage", "value": excMessage },
                        { "name": "excOpenMRSVersion", "value": excOpenMRSVersion },
                        { "name": "excFileName", "value": excFileName },
                        { "name": "excMethodName", "value": excMethodName },
                        { "name": "excLineNum", "value": excLineNum },
                        { "name": "excFrequency", "value": excFrequency },
                        { "name": "startDateTimeString", "value": startDateTimeString },
                        { "name": "endDateTimeString", "value": endDateTimeString });
                        $j.ajax( {
                            "dataType": 'json', 
                            "type": "POST", 
                            "url": sSource, 
                            "data": aoData, 
                            "success": fnCallback
                        } );
                    },
                    "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {                
                        $j('td:eq(0)', nRow).html( '<input type="checkbox" id="tableExceptionLogSelect" class="tableExceptionLogSelect" name="check'+aData[0]+ '" value="'+aData[0]+ '" onclick="selectTableExcLogCheckBox()"/>' );
                        if(excFrequency != undefined && excFrequency != null && excFrequency != ''){
                            oTable.fnSetColumnVis(4, false);
                            oTable.fnSetColumnVis(5, false);
                            if(aData[6] == "View"){
                                $j('td:eq(4)', nRow).html('<img src="${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png" alt="View" name="'+aData[0]+ '" class="detailExLog">' );
                            }
                            if(aData[7] == "View"){
                                $j('td:eq(5)', nRow).html('<img src="${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png" alt="View" name="'+aData[0]+ '" class="rootCause">' );                                
                            }
                            $j('td:eq(6)', nRow).html('<input type="button" id="tableExceptionLogReportBtn" value="<spring:message code="errorlogging.exceptionLogTable.report" />" onclick="sendReport(this)"/>');    
                        }else{
                            oTable.fnSetColumnVis(4, true);
                            oTable.fnSetColumnVis(5, true);                        
                            if(aData[6] == "View"){
                                $j('td:eq(6)', nRow).html('<img src="${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png" alt="View" name="'+aData[0]+ '" class="detailExLog">' );
                            }
                            if(aData[7] == "View"){
                                $j('td:eq(7)', nRow).html('<img src="${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png" alt="View" name="'+aData[0]+ '" class="rootCause">' );
                            }
                            $j('td:eq(8)', nRow).html('<input type="button" id="tableExceptionLogReportBtn" value="<spring:message code="errorlogging.exceptionLogTable.report" />" onclick="sendReport(this)"/>');
                            $j('.removExcLogButton').html('<input type="button" id="removeExcLogs" value="<spring:message code="errorlogging.tableNavigation.removeSelected" />" style="display:none;" onclick="removeSelectedExcLogs()"/>');                        
                        }
                        //Hidden Repor button until the final design
                        oTable.fnSetColumnVis(8, false);
                        return nRow;
                    }
                });
            }else{
                oTable.fnSettings()._iDisplayStart = 0;                
                oTable.fnDraw();                 
            }            
        });                 
          
        $j('#exceptionLogTable tbody td img.detailExLog, #exceptionLogTable tbody td img.rootCause').live( 'click', function () {
            var nTr = this.parentNode.parentNode;
            var id = this.name;
            if (this.src.match('details_close')){
                /* This row is already open - close it */
                this.src = "${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png";
                $j('.eldetail' + id).parent().remove();
                $j(this).removeClass('exLogTableDetail');
            }else{
                /*If opened another td of row(detailExLog/rootCause) we have to close it*/
                if($j(nTr).find('.exLogTableDetail').length > 0){
                    var openedRow = $j(nTr).find('.exLogTableDetail')[0];
                    openedRow.src = "${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png";
                    $j('.eldetail' + id).parent().remove();
                    $j(openedRow).removeClass('exLogTableDetail');
                }
                /* Open this row */
                this.src = "${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_close.png";
                $j(this).addClass('exLogTableDetail');
                if($j(this).hasClass('detailExLog')){
                    DWRExceptionLogService.getExceptionLogDetail(oTable.fnGetData( nTr )[0], function(excLogDetail){   
                        oTable.fnOpen( nTr, fnFormatDetails(excLogDetail, "detailExLog"), 'eldetail'+id+' eldetail' );
                    });
                }else{ 
                    DWRExceptionLogService.getExceptionRootCause(oTable.fnGetData( nTr )[0], function(excRootCause){   
                        oTable.fnOpen( nTr, fnFormatDetails(excRootCause, "rootCause"), 'eldetail'+id+' eldetail' );
                    });
                }
            }
        });
    
        $j('#exceptionLogTable tbody td img.elRootCauseDetail').live( 'click', function () {
            var nTr = this.parentNode.parentNode;
            var td = nTr.parentNode.parentNode.parentNode;
            var position = $j(this).position();
            if ( this.src.match('details_close') )
            {
                /* This row is already open - close it */
                this.src = "${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png";
                $j("#rootCauseDetail" + this.name).remove();
            }else{            
                /* Open this row */
                this.src = "${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_close.png";
                DWRExceptionLogService.getExceptionRootCauseDetail(this.name, function(excRootCauseDetail){                                                          
                    var sOut = '<table cellpadding="0" cellspacing="0" border="0" style="margin-left:'+position.left+'px;  border: 1px solid #1aac9b;" id="rootCauseDetail'+excRootCauseDetail.id+'">';
                    sOut += '<thead><tr><th colspan="2"><spring:message code="errorlogging.exceptionRootCauseDetailTable.title" /></th></tr></thead>';
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.file" />:</td><td>'+htmlEscape(excRootCauseDetail.fileName)+'</td></tr>';
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.class" />:</td><td>'+htmlEscape(excRootCauseDetail.className)+'</td></tr>';
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.method" />:</td><td>'+htmlEscape(excRootCauseDetail.methodName)+'</td></tr>';
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.lineNumber" />:</td><td>'+htmlEscape(excRootCauseDetail.lineNumber)+'</td></tr>';
                    sOut += '</table>';
                    $j(td).last().append(sOut);
                });                       
            }
        });
               
        // add multiple select / deselect functionality
        $j("#selectAllExcLogs").click(function () {
            $j('.tableExceptionLogSelect').attr('checked', this.checked);
            showHideRemoveButton();
        });        
                    
        $j('#showExceptionLogs').click();        
    });  
           
    function sendReport(obj){
        var reportBugUrl = "<%=org.openmrs.api.context.Context.getAdministrationService().getGlobalProperty(org.openmrs.util.OpenmrsConstants.GLOBAL_PROPERTY_REPORT_BUG_URL)%>";
        var nTr = obj.parentNode.parentNode;
        var data = oTable.fnGetData( nTr );
        var openmrsVersion =  data[3];
        var username =  data[5];
        var errorMessage =  data[1] + " "+ data[2];
        var sForm = '<form action="'+reportBugUrl+'" target="_blank" method="POST">'
            + '<input type="hidden" name="openmrs_version" value="'+openmrsVersion+'" />'
            + '<input type="hidden" name="username" value="'+username+'" />'          
            + '<input type="hidden" name="errorMessage" value="'+errorMessage+'" />';       
            +'<br/><input type=\"submit\" value=\"Report Problem\"></form>";'                        
        $j(sForm).submit();                   
    } 
     
    function fnFormatDetails ( excLogDetail, logDetailOrRootCause ){
        var sOut = '<table cellpadding="0" cellspacing="0" border="0" style="padding-left:50px;">';
        if(logDetailOrRootCause == "detailExLog"){
            sOut+='<thead><tr><th colspan="2"><spring:message code="errorlogging.exceptionLogDetailTable.title" /></th></tr></thead>'
            if(excLogDetail != null){    
                sOut += '<tr><td><spring:message code="errorlogging.detailTables.file" />:</td><td>'+htmlEscape(excLogDetail.fileName)+'</td></tr>';
                if(excLogDetail.className != null && excLogDetail.className != ''){
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.class" />:</td><td>'+htmlEscape(excLogDetail.className)+'</td></tr>';
                }
                if(excLogDetail.methodName != null && excLogDetail.methodName != ''){
                    sOut += '<tr><td><spring:message code="errorlogging.detailTables.method" />:</td><td>'+htmlEscape(excLogDetail.methodName)+'</td></tr>';
                }
                sOut += '<tr><td><spring:message code="errorlogging.detailTables.lineNumber" />:</td><td>'+htmlEscape(excLogDetail.lineNumber)+'</td></tr>';               
            }
        }else{                                   
            sOut+='<thead><tr><th colspan="2"><spring:message code="errorlogging.exceptionRootCauseTable.title" /></th></tr></thead>'
            if(excLogDetail != null){    
                sOut += '<tr><td><spring:message code="errorlogging.exceptionRootCauseTable.class" />:</td><td>'+htmlEscape(excLogDetail.exceptionClass)+'</td></tr>';
                sOut += '<tr><td><spring:message code="errorlogging.exceptionRootCauseTable.message" />:</td><td>'+htmlEscape(excLogDetail.exceptionMessage)+'</td></tr>';
                if(excLogDetail.hasRootCauseDetail){
                    sOut += '<tr><td><spring:message code="errorlogging.detail" />:</td><td><img src="${pageContext.request.contextPath}/moduleResources/errorlogging/images/details_open.png" alt="View" class="elRootCauseDetail" name="' + excLogDetail.id + '"></td></tr>';
                }
            }    
        } 
        sOut += '</table>';
        return sOut;               
    }
           
    function htmlEscape(str) {
        return String(str)
        .replace(/&/g, '&amp;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;');
    }
          
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
            var id = parseInt(selected_cb[i].value, 10);
            if(isNaN(id)){
                return;
            }
            res[i] = id;
        }
        if(res.length > 0){            
            dwr.engine.beginBatch();
            DWRExceptionLogService.purgeExceptionLogs(res, function(result){
                if(result){
                    oTable.fnDraw();
                    $j("#removeExcLogs").css("display","none");
                }
            });
            dwr.engine.endBatch();
        }
    }
    
    function selectTableExcLogCheckBox(){
        if($j(".tableExceptionLogSelect").length == $j(".tableExceptionLogSelect:checked").length) {
            $j("#selectAllExcLogs").attr("checked", "checked");               
        } else {
            $j("#selectAllExcLogs").removeAttr("checked");
        }
        showHideRemoveButton();
    }
    
    function showHideRemoveButton(){
        if(excFrequency!=undefined && excFrequency!=null && excFrequency!=''){
            return;
        }
        if($j("#exceptionLogTable").find("input:checkbox:checked:not('#selectAllExcLogs')").length > 0){
            $j("#removeExcLogs").css("display","block");
        }else{
            $j("#removeExcLogs").css("display","none");
        }               
    }
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>