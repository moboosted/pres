<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.atg.com/taglibs/json" prefix="json" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findMoneySchedulerExecutionsForm" scope="request"
             type="com.silvermoongroup.fsa.web.moneyscheduler.execution.FindMoneySchedulerExecutionsForm"/>

<script type="text/javascript">
    $(function () {
        // clear out elements that are not relevant
        //
        $('#moneySchedulerOptionAny').change(function () {
            $('#moneySchedulerObjectReference').val("");
        });

        $('#moneySchedulerOptionType').change(function () {
            $('#moneySchedulerObjectReference').val("");
        });

        $('#requestedExecutionDateOptionAny').change(function () {
            $('#requestedExecutionOnDate').val("");
            $('#requestedExecutedBetweenDateStart').val("");
            $('#requestedExecutedBetweenDateEnd').val("");
        });

        $('#requestedExecutionDateOptionOn').change(function () {
            $('#requestedExecutedBetweenDateStart').val("");
            $('#requestedExecutedBetweenDateEnd').val("");
        });

        $('#requestedExecutionDateOptionBetween').change(function () {
            $('#requestedExecutionOnDate').val("");
        });

        $('#executionStartedDateOptionAny').change(function () {
            $('#executionStartedOnDate').val("");
            $('#executionStartedBetweenDateStart').val("");
            $('#executionStartedBetweenDateEnd').val("");
        });

        $('#executionStartedDateOptionOn').change(function () {
            $('#executionStartedBetweenDateStart').val("");
            $('#executionStartedBetweenDateEnd').val("");
        });

        $('#executionStartedDateOptionBetween').change(function () {
            $('#executionStartedOnDate').val("");
        });

        // focus
        //
        $('#moneySchedulerObjectReference').focus(function () {
            $('#moneySchedulerOptionSpecific').prop('checked', true).change();
        });

        $('#moneySchedulerTypeSelect').focus(function () {
            $('#moneySchedulerOptionType').prop('checked', true).change();
        });

        $('#requestedExecutionOnDate').focus(function () {
            $('#requestedExecutionDateOptionOn').prop('checked', true).change();
        });
        $('#requestedExecutedBetweenDateStart').focus(function () {
            $('#requestedExecutionDateOptionBetween').prop('checked', true).change();
        });
        $('#requestedExecutedBetweenDateEnd').focus(function () {
            $('#requestedExecutionDateOptionBetween').prop('checked', true).change();
        });

        $('#executionStartedOnDate').focus(function () {
            $('#executionStartedDateOptionOn').prop('checked', true).change();
        });
        $('#executionStartedBetweenDateStart').focus(function () {
            $('#executionStartedDateOptionBetween').prop('checked', true).change();
        });
        $('#executionStartedBetweenDateEnd').focus(function () {
            $('#executionStartedDateOptionBetween').prop('checked', true).change();
        });

        // yesterday/today links
        $('#requestedYesterdayLink').bind('click', function (e) {
            $('#requestedExecutionOnDate').val("${findMoneySchedulerExecutionsForm.yesterday}");
            $('#requestedExecutionDateOptionOn').prop('checked', true).change();

        });
        $('#requestedTodayLink').bind('click', function (e) {
            $('#requestedExecutionOnDate').val("${findMoneySchedulerExecutionsForm.today}");
            $('#requestedExecutionDateOptionOn').prop('checked', true).change();
        });
        $('#executedYesterdayLink').bind('click', function (e) {
            $('#executionStartedOnDate').val("${findMoneySchedulerExecutionsForm.yesterday}");
            $('#executionStartedDateOptionOn').prop('checked', true).change();
        });
        $('#executedTodayLink').bind('click', function (e) {
            $('#executionStartedOnDate').val("${findMoneySchedulerExecutionsForm.today}");
            $('#executionStartedDateOptionOn').prop('checked', true).change();
        });
    });
</script>

<html:form action="/secure/moneyscheduler/execution/find.do" styleId="findForm" styleClass="form-horizontal">
<input type="hidden" name="method" value=".find"/>

<div class="row form-container">

        <%--Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="form-group">
                <label for="moneySchedulerExecutionId" class="col-md-3 control-label"><fmt:message
                        key="label.id"/></label>

                <div class="col-md-3">
                    <html:text property="moneySchedulerExecutionId" styleId="moneySchedulerExecutionId"
                               styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"/>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="form-group">
                <div class="col-md-offset-3 col-md-9 form-inline">
                    <html:radio property="moneySchedulerOption" value="any" styleId="moneySchedulerOptionAny" styleClass="radio-inline"/>
                    <fmt:message key="page.findmoneyschedulerexecutions.label.anymoneyscheduler"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <label for="moneySchedulerOptionSpecific" class="col-md-3 control-label"><fmt:message
                        key="ftx.moneyschedulerexecution.generatedby"/></label>

                <div class="col-md-9">
                    <div class="form-inline">
                        <html:radio property="moneySchedulerOption" value="specific"
                                    styleId="moneySchedulerOptionSpecific" styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findmoneyschedulerexecutions.label.specificmoneyscheduler"/>
                        <html:text property="moneySchedulerObjectReference" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error"
                                   styleId="moneySchedulerObjectReference"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <div class=" col-md-offset-3 col-md-9">
                    <div class="form-inline">
                        <html:radio property="moneySchedulerOption" value="type" styleId="moneySchedulerOptionType"
                                styleClass="radio-inline"/>
                        <fmt:message key="page.findmoneyschedulerexecutions.label.oftype"/>
                        <html:select property="moneySchedulerTypeId" errorStyleClass="form-control input-sm has-error" styleClass="form-control input-sm"
                                     styleId="moneySchedulerTypeSelect">
                            <smg:typeTree rootTypeId="502"/>
                        </html:select>
                        <div class="spacer-left">
                            <html:checkbox property="restrictMoneySchedulerType"/> <fmt:message
                                key="label.restricttothistype"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">
                <label for="returnCode" class="col-md-3 control-label"><fmt:message
                        key="ftx.moneyschedulerexecution.returncode"/></label>

                <div class="col-md-6">
                    <html:select property="returnCodes" styleId="returnCode" multiple="true" styleClass="form-control input-sm">
                        <smg:immutableEnumOptions immutableEnumerationClass="com.silvermoongroup.ftx.domain.enumeration.MoneySchedulerExecutionReturnCode"/>                    </html:select>
                </div>
            </div>
        </div>

    </div>

        <%--Right column--%>
    <div class="col-md-6">

        <div class="row spacer">

            <div class="form-group">
                <div class="col-md-offset-3 col-md-9 form-inline">
                    <html:radio property="requestedExecutionDateOption" value="any" styleClass="radio-inline"
                                styleId="requestedExecutionDateOptionAny"/>
                    <fmt:message key="page.findmoneyschedulerexecutions.label.atanytime"/>

                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <label for="moneySchedulerOptionSpecific" class="col-md-3 control-label"><fmt:message
                        key="ftx.moneyschedulerexecution.requestedexecutiondate"/></label>

                <div class="col-md-9">
                    <div class="form-inline">
                        <html:radio property="requestedExecutionDateOption" value="on"
                                    styleId="requestedExecutionDateOptionOn" styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findmoneyschedulerexecutions.label.on"/>
                        <html:text property="requestedExecutionOnDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="requestedExecutionOnDate"/>
                        &nbsp;&nbsp;[ <a href="#" id="requestedYesterdayLink"><fmt:message key="label.yesterday"/></a> ]
                        &nbsp;[ <a href="#" id="requestedTodayLink"><fmt:message key="label.today"/></a> ]
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <div class=" col-md-offset-3 col-md-9">
                    <div class="form-inline">
                        <html:radio property="requestedExecutionDateOption" value="between"
                                    styleId="requestedExecutionDateOptionBetween"/>
                        <fmt:message
                                key="page.findmoneyschedulerexecutions.label.between"/>
                        <html:text property="requestedExecutionBetweenDateStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="requestedExecutedBetweenDateStart"/>
                        <fmt:message key="page.findmoneyschedulerexecutions.label.and"/>
                        <html:text property="requestedExecutionBetweenDateEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="requestedExecutedBetweenDateEnd"/>
                    </div>
                </div>
            </div>
        </div>


        <div class="row spacer">

            <div class="form-group">
                <div class="col-md-offset-3 col-md-9 form-inline">
                    <html:radio property="executionStartedDateOption" value="any"
                                styleId="executionStartedDateOptionAny"/>
                    <fmt:message key="page.findmoneyschedulerexecutions.label.atanytime"/>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <label for="executionStartedDateOptionOn" class="col-md-3 control-label"><fmt:message
                        key="ftx.moneyschedulerexecution.startedat"/></label>

                <div class="col-md-9">
                    <div class="form-inline">
                        <html:radio property="executionStartedDateOption" value="on"
                                    styleId="executionStartedDateOptionOn"/>
                        <fmt:message
                                key="page.findmoneyschedulerexecutions.label.on"/>
                        <html:text property="executionStartedOnDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="executionStartedOnDate"/>
                        &nbsp;&nbsp;[ <a href="#" id="executedYesterdayLink"><fmt:message key="label.yesterday"/></a> ]
                        &nbsp;[ <a href="#" id="executedTodayLink"><fmt:message key="label.today"/></a> ]
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="form-group">

                <div class=" col-md-offset-3 col-md-9">
                    <div class="form-inline">
                        <html:radio property="executionStartedDateOption" value="between"
                                    styleId="executionStartedDateOptionBetween"/>
                        <fmt:message
                                key="page.findmoneyschedulerexecutions.label.between"/>
                        <html:text property="executionStartedBetweenDateStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="executionStartedBetweenDateStart"/>
                        <fmt:message key="page.findmoneyschedulerexecutions.label.and"/>
                        <html:text property="executionStartedBetweenDateEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="executionStartedBetweenDateEnd"/>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="row spacer">
    <div class="col-md-12 text-center">
        <html:submit accesskey="s" styleClass="btn btn-primary btn-sm"
                     titleKey="label.submitwithalts">
            <fmt:message key="button.search"/>
        </html:submit>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>
</div>


</html:form>

<c:url value="/secure/moneyscheduler/execution.do" var="moneySchedulerExecutionUrl"/>
<c:url value="/secure/moneyscheduler.do" var="moneySchedulerUrl"/>

<c:if test="${findMoneySchedulerExecutionsForm.results != null}">
    <div class="table-responsive spacer">
        <display:table name="${findMoneySchedulerExecutionsForm.results}"
                       id="${findMoneySchedulerExecutionsForm.tableId}"
                       decorator="com.silvermoongroup.fsa.web.moneyscheduler.MoneySchedulerExecutionsTableDecorator"
                       pagesize="${findMoneySchedulerExecutionsForm.rowsPerPage}"
                       requestURI="/secure/moneyscheduler/execution/find.do">

            <display:column property="id" titleKey="label.id" href="${moneySchedulerExecutionUrl}"
                            paramId="moneySchedulerExecutionObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type" href="${moneySchedulerExecutionUrl}"
                            paramId="moneySchedulerExecutionObjectReference" paramProperty="objectReference"/>
            <display:column property="moneyScheduler" titleKey="common.type.Money Scheduler" href="${moneySchedulerUrl}"
                            paramId="moneySchedulerObjectReference" paramProperty="moneyScheduler.objectReference"/>
            <display:column property="requestedExecutionDate"
                            titleKey="ftx.moneyschedulerexecution.requestedexecutiondate"/>
            <display:column property="cyclePeriod" titleKey="ftx.moneyschedulerexecution.cycleperiod"/>
            <display:column property="startedAt" titleKey="ftx.moneyschedulerexecution.startedat"/>
            <display:column property="endedAt" titleKey="ftx.moneyschedulerexecution.endedat"/>
            <display:column property="duration" titleKey="ftx.moneyschedulerexecution.duration" class="numeric"/>
            <display:column property="returnCode" titleKey="ftx.moneyschedulerexecution.returncode"/>

        </display:table>
    </div>
</c:if>

<div id="recentRequestsChart"></div>


<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3-tip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3-visualisation/barchart.js"></script>

<script type="text/javascript">
    var moneySchedulerExecutions =
            <json:array items="${findMoneySchedulerExecutionsForm.results}" var="e">
            <json:object>
            <json:property name="id" value="${e.id}"/>
            <json:property name="duration" value="${e.endedAt.millis - e.startedAt.millis}" />
            </json:object>
            </json:array>;

    renderBarGraph('recentRequestsChart', moneySchedulerExecutions, ${findMoneySchedulerExecutionsForm.startRecord}, ${findMoneySchedulerExecutionsForm.rowsPerPage});
</script>




