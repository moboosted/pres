<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findMoneySchedulersForm" scope="request"
             type="com.silvermoongroup.fsa.web.moneyscheduler.form.FindMoneySchedulersForm"/>

<script type="text/javascript">
    $(function () {

        $('#nextExecutedOptionAny').change(function () {
            $('#nextExecutionOnDate').val("");
            $('#nextExecutionBetweenDateStart').val("");
            $('#nextExecutionBetweenDateEnd').val("");
        });

        $('#nextExecutedOptionOn').change(function () {
            $('#nextExecutionBetweenDateStart').val("");
            $('#nextExecutionBetweenDateEnd').val("");
        });

        $('#nextExecutionTodayLink').bind('click', function (e) {
            e.preventDefault();
            $('#nextExecutionOnDate').val("${findMoneySchedulersForm.today}");
            $('#nextExecutedOptionOn').prop('checked', true).change();
        });

        $('#nextExecutionTomorrowLink').bind('click', function (e) {
            e.preventDefault();
            $('#nextExecutionOnDate').val("${findMoneySchedulersForm.tomorrow}");
            $('#nextExecutedOptionOn').prop('checked', true).change();
        });

        $('#nextExecutedOptionBetween').change(function () {
            $('#nextExecutionOnDate').val("");
        });

        $('#nextExecutionCurrentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#nextExecutionBetweenDateStart').val("${findMoneySchedulersForm.firstOfCurrentMonth}");
            $('#nextExecutionBetweenDateEnd').val("${findMoneySchedulersForm.lastOfCurrentMonth}");
            $('#nextExecutedOptionBetween').prop('checked', true).change();
        });

        $('#nextExecutionNextMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#nextExecutionBetweenDateStart').val("${findMoneySchedulersForm.firstOfNextMonth}");
            $('#nextExecutionBetweenDateEnd').val("${findMoneySchedulersForm.lastOfNextMonth}");
            $('#nextExecutedOptionBetween').prop('checked', true).change();
        });

        $('#lastExecutedOptionAny').change(function () {
            $('#lastExecutedOnDate').val("");
            $('#lastExecutedBetweenDateStart').val("");
            $('#lastExecutedBetweenDateEnd').val("");
        });

        $('#lastExecutedOptionOn').change(function () {
            $('#lastExecutedBetweenDateStart').val("");
            $('#lastExecutedBetweenDateEnd').val("");
        });

        $('#lastExecutedYesterdayLink').bind('click', function (e) {
            e.preventDefault();
            $('#lastExecutedOnDate').val("${findMoneySchedulersForm.yesterday}");
            $('#lastExecutedOptionOn').attr('checked', true).change();
        });

        $('#lastExecutedTodayLink').bind('click', function (e) {
            e.preventDefault();
            $('#lastExecutedOnDate').val("${findMoneySchedulersForm.today}");
            $('#lastExecutedOptionOn').prop('checked', true).change();
        });

        $('#lastExecutedOptionBetween').change(function () {
            $('#lastExecutedOnDate').val("");
        });

        $('#lastExecutedLastMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#lastExecutedBetweenDateStart').val("${findMoneySchedulersForm.firstOfPreviousMonth}");
            $('#lastExecutedBetweenDateEnd').val("${findMoneySchedulersForm.lastOfPreviousMonth}");
            $('#lastExecutedOptionBetween').prop('checked', true).change();
        });

        $('#lastExecutedCurrentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#lastExecutedBetweenDateStart').val("${findMoneySchedulersForm.firstOfCurrentMonth}");
            $('#lastExecutedBetweenDateEnd').val("${findMoneySchedulersForm.lastOfCurrentMonth}");
            $('#lastExecutedOptionBetween').prop('checked', true).change();
        });

        // focus
        //

        $('#lastExecutedOnDate').focus(function () {
            $('#lastExecutedOptionOn').prop('checked', true).change();
        });
        $('#lastExecutedBetweenDateStart').focus(function () {
            $('#lastExecutedOptionBetween').prop('checked', true).change();
        });
        $('#lastExecutedBetweenDateEnd').focus(function () {
            $('#lastExecutedOptionBetween').prop('checked', true).change();
        });

        $('#nextExecutionOnDate').focus(function () {
            $('#nextExecutedOptionOn').prop('checked', true).change();
        });
        $('#nextExecutionBetweenDateStart').focus(function () {
            $('#nextExecutedOptionBetween').prop('checked', true).change();
        });
        $('#nextExecutionBetweenDateEnd').focus(function () {
            $('#nextExecutedOptionBetween').prop('checked', true).change();
        });
    });
</script>

<html:form action="/secure/moneyscheduler/find.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>

    <div class="row form-container">

            <%--Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group">
                    <label for="moneySchedulerId" class="col-md-3 control-label"><fmt:message key="label.id"/></label>

                    <div class="col-md-4"><html:text property="moneySchedulerId" styleId="moneySchedulerId"
                                                     styleClass="form-control input-sm"/></div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="schedulerTypeId" class="col-md-3 control-label"><fmt:message
                            key="ftx.moneyscheduler.type"/></label>

                    <div class="col-md-4">

                        <html:select property="schedulerTypeId" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error" styleId="schedulerTypeId">
                            <smg:typeTree rootTypeId="502"/>
                        </html:select>
                        <div class="checkbox-inline">
                            <html:checkbox property="restrictSchedulerType"/> <fmt:message
                                key="label.restricttothistype"/>
                        </div>

                    </div>
                </div>
            </div>

            <div class="row">
                <div class="form-group">
                    <label for="frequency" class="col-md-3 control-label"><fmt:message
                            key="ftx.moneyscheduler.frequency"/></label>

                    <div class="col-md-4">
                        <html:select property="frequency" styleId="frequency" styleClass="form-control input-sm"
                                     errorStyleClass="form-control input-sm has-error">
                            <option value=""><fmt:message key="page.findmoneyschedulers.label.anyfrequency"/></option>
                            <smg:enumOptions enumTypeId="<%=EnumerationType.FREQUENCY.getValue()%>" showTerminated = "true"/>
                        </html:select>
                    </div>
                </div>
            </div>

        </div>

            <%--Left column--%>
        <div class="col-md-6">

            <div class="row">
                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9">
                        <html:radio property="lastExecutedOption" value="any" styleId="lastExecutedOptionAny"
                                    styleClass="radio-inline"/>
                        <fmt:message key="page.findmoneyschedulers.label.atanytime"/>
                    </div>
                </div>

                <div class="form-group-sm">
                    <label for="lastExecutedOptionOn" class="col-md-3 control-label"><fmt:message
                            key="page.findmoneyschedulers.label.lastexecuted"/></label>

                    <div class="col-md-9 form-inline">
                        <html:radio property="lastExecutedOption" value="on" styleId="lastExecutedOptionOn"
                                    styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findmoneyschedulers.label.on"/>
                        <html:text property="lastExecutedOnDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="lastExecutedOnDate"/>
                        &nbsp;&nbsp;[ <a href="#" id="lastExecutedYesterdayLink"><fmt:message
                            key="label.yesterday"/></a> ]
                        &nbsp;[ <a href="#" id="lastExecutedTodayLink"><fmt:message key="label.today"/></a> ]
                    </div>
                </div>

                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9 form-inline">
                        <html:radio property="lastExecutedOption" value="between" styleId="lastExecutedOptionBetween"/>
                        <fmt:message
                                key="page.findmoneyschedulers.label.between"/>
                        <html:text property="lastExecutedBetweenDateStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="lastExecutedBetweenDateStart"/>
                        <fmt:message key="page.findmoneyschedulers.label.and"/>
                        <html:text property="lastExecutedBetweenDateEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="lastExecutedBetweenDateEnd"/>
                        &nbsp;&nbsp;[ <a href="#" id="lastExecutedLastMonthLink"><fmt:message
                            key="label.lastmonth"/></a> ]
                        &nbsp;[ <a href="#" id="lastExecutedCurrentMonthLink"><fmt:message
                            key="label.currentmonth"/></a> ]
                    </div>
                </div>
            </div>

            <div class="row spacer">
                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9">
                        <html:radio property="nextExecutionOption" value="any" styleId="nextExecutedOptionAny"
                                    styleClass="radio-inline"/>
                        <fmt:message key="page.findmoneyschedulers.label.atanytime"/>
                    </div>
                </div>

                <div class="form-group-sm">
                    <label for="nextExecutionOnDate" class="col-md-3 control-label"><fmt:message
                            key="ftx.moneyscheduler.nextrundate"/></label>

                    <div class="col-md-9 form-inline">
                        <html:radio property="nextExecutionOption" value="on" styleId="nextExecutedOptionOn"
                                    styleClass="radio-inline"/>
                        <fmt:message
                                key="page.findmoneyschedulers.label.on"/>
                        <html:text property="nextExecutionOnDate" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error" styleId="nextExecutionOnDate"/>
                        &nbsp;&nbsp;[ <a href="#" id="nextExecutionTodayLink"><fmt:message key="label.today"/></a> ]
                        &nbsp;[ <a href="#" id="nextExecutionTomorrowLink"><fmt:message key="label.tomorrow"/></a> ]
                    </div>
                </div>

                <div class="form-group-sm">
                    <div class=" col-md-offset-3 col-md-9 form-inline">
                        <html:radio property="nextExecutionOption" value="between" styleId="nextExecutedOptionBetween"/>
                        <fmt:message
                                key="page.findmoneyschedulers.label.between"/>
                        <html:text property="nextExecutionBetweenDateStart" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="nextExecutionBetweenDateStart"/>
                        <fmt:message key="page.findmoneyschedulers.label.and"/>
                        <html:text property="nextExecutionBetweenDateEnd" styleClass="form-control input-sm datefield"
                                   errorStyleClass="form-control input-sm datefield has-error"
                                   styleId="nextExecutionBetweenDateEnd"/>
                        &nbsp;&nbsp;[ <a href="#" id="nextExecutionCurrentMonthLink"><fmt:message
                            key="label.currentmonth"/></a> ]
                        &nbsp;[ <a href="#" id="nextExecutionNextMonthLink"><fmt:message key="label.nextmonth"/></a> ]
                    </div>
                </div>
            </div>

        </div>

    </div>

    <div class="row spacer">
        <div class="col-md-12 text-center">
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>
</html:form>

<c:url value="/secure/moneyscheduler.do" var="moneySchedulerUrl"/>

<c:if test="${findMoneySchedulersForm.results != null}">
    <div class="table-responsive spacer">
        <display:table name="${findMoneySchedulersForm.results}"
                       decorator="com.silvermoongroup.fsa.web.moneyscheduler.MoneySchedulersTableDecorator">

            <display:column property="id" titleKey="label.id" href="${moneySchedulerUrl}"
                            paramId="moneySchedulerObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type" href="${moneySchedulerUrl}"
                            paramId="moneySchedulerObjectReference" paramProperty="objectReference"/>
            <display:column property="startDate" titleKey="ftx.moneyscheduler.startdate"/>
            <display:column property="endDate" titleKey="ftx.moneyscheduler.enddate"/>
            <display:column property="frequency" titleKey="ftx.moneyscheduler.frequency"/>
            <display:column property="anniversaryDate" titleKey="ftx.moneyscheduler.anniversarydate"/>
            <display:column property="anniversaryFixingType" titleKey="ftx.moneyscheduler.anniversarydatefixing"/>
            <display:column property="nextRunDate" titleKey="ftx.moneyscheduler.nextrundate"/>

        </display:table>
    </div>
</c:if>
