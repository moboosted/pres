<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="moneySchedulerForm" scope="request"
             type="com.silvermoongroup.fsa.web.moneyscheduler.form.MoneySchedulerForm"/>

<div class="row">
    <label for="moneySchedulerDetailsTable" class="groupHeading col-md-12"><fmt:message
            key="page.moneyscheduler.label.detailsofthe"/>
        <c:out value="${moneySchedulerForm.type}"/></label>
</div>

<c:url value="/secure/account.do" var="sourceAccountUrl">
    <c:param name="accountObjectReference" value="${moneySchedulerForm.sourceAccountObjectReference}"/>
</c:url>

<c:url value="/secure/account.do" var="targetAccountUrl">
    <c:param name="accountObjectReference" value="${moneySchedulerForm.targetAccountObjectReference}"/>
</c:url>

<c:url value="/secure/moneyscheduler/execute.do" var="executeMoneySchedulerUrl">
    <c:param name="moneySchedulerObjectReference" value="${moneySchedulerForm.moneySchedulerObjectReference}"/>
</c:url>


<div class="row form-container" id="moneySchedulerDetailsTable">

    <%-- Left column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-8"><smg:objectReference
                    value="${moneySchedulerForm.moneySchedulerObjectReference}"/></div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.type"/></label> </div>
            <div class="col-md-8"><smg:objectReference
                    value="${moneySchedulerForm.moneySchedulerObjectReference}" display="type"/></div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.startdate"/> &rarr; <fmt:message
                    key="page.moneyscheduler.label.enddate"/></label> </div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.startDate}"/> &rarr; <c:out
                    value="${moneySchedulerForm.endDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.statusanddates"/></label> </div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.moneySchedulerStatus}"/> (<c:out
                    value="${moneySchedulerForm.moneySchedulerStatusStart}"/> &rarr; <c:out
                    value="${moneySchedulerForm.moneySchedulerStatusEnd}"/>)
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="ftx.moneyscheduler.anniversarydate"/></label></div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.anniversaryDate}"/></div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="ftx.moneyscheduler.anniversarydatefixing"/></label></div>
            <div class="col-md-8"><smg:fmt value="${moneySchedulerForm.anniversaryFixingType}"/></div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="ftx.moneyscheduler.nextrundate"/></label></div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.nextRunDate}"/></div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="ftx.moneyscheduler.frequency"/></label></div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.frequency}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.rollup"/></label></div>
            <div class="col-md-8"><smg:fmt value="${moneySchedulerForm.rollup}"/></div>
        </div>
    </div>

    <%-- Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.sourceaccount"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${sourceAccountUrl}"><c:out
                    value="${moneySchedulerForm.sourceAccountName}"/></a></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.targetaccount"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${targetAccountUrl}"><c:out
                    value="${moneySchedulerForm.targetAccountName}"/></a></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.paymentmethod"/></label></div>
            <div class="col-md-8"><smg:fmt value="${moneySchedulerForm.paymentMethod}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.receiver"/></label></div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.receiverName}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.payer"/></label></div>
            <div class="col-md-8"><c:out value="${moneySchedulerForm.payerName}"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="page.moneyscheduler.label.companycode"/></label></div>
            <div class="col-md-8"><smg:fmt value="${moneySchedulerForm.internalCompanyCode}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-12"><a href="${executeMoneySchedulerUrl}" class="btn btn-danger btn-sm"><fmt:message
                    key="page.moneyscheduler.label.execute"/></a></div>
        </div>
    </div>
</div>

<c:url value="/secure/moneyscheduler/execution.do" var="moneySchedulerExecutionUrl"/>

<div id="recentMoneySchedulerExecutions" class="spacer">
    <label for="recentMoneySchedulerExecutionsTable" class="groupHeading"><fmt:message
            key="page.moneyscheduler.label.recentexecutions"/></label>
    <c:if test="${!empty moneySchedulerForm.recentExecutions}">
        <div>
            <fmt:message key="page.moneyscheduler.label.formoreexecutions"/>
            <c:url value="/secure/moneyscheduler/execution/find.do" var="findMoneySchedulerExecutionsUrl">
                <c:param name="moneySchedulerObjectReference"
                         value="${moneySchedulerForm.moneySchedulerObjectReference}"/>
                <c:param name="method" value=".findAndDisplay"/>
            </c:url>
            <a class="text-info" href="<c:out value="${findMoneySchedulerExecutionsUrl}" />"><fmt:message
                    key="page.moneyscheduler.label.enquirefurther"/></a>
        </div>
    </c:if>
    <div class="table-responsive">
        <display:table name="${moneySchedulerForm.recentExecutions}" id="recentMoneySchedulerExecutionsTable"
                       decorator="com.silvermoongroup.fsa.web.moneyscheduler.MoneySchedulerExecutionsTableDecorator">

            <display:column property="id" titleKey="label.id"
                            href="${moneySchedulerExecutionUrl}" paramId="moneySchedulerExecutionObjectReference"
                            paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type"
                            href="${moneySchedulerExecutionUrl}" paramId="moneySchedulerExecutionObjectReference"
                            paramProperty="objectReference"/>
            <display:column property="requestedExecutionDate"
                            titleKey="ftx.moneyschedulerexecution.requestedexecutiondate"
                    />
            <display:column property="cyclePeriod" titleKey="ftx.moneyschedulerexecution.cycleperiod"/>
            <display:column property="startedAt" titleKey="ftx.moneyschedulerexecution.startedat"/>
            <display:column property="endedAt" titleKey="ftx.moneyschedulerexecution.endedat"/>
            <display:column property="duration" titleKey="ftx.moneyschedulerexecution.duration"/>
            <display:column property="returnCode" titleKey="ftx.moneyschedulerexecution.returncode"/>

        </display:table>
    </div>
    <c:if test="${fn:length(moneySchedulerForm.recentExecutions) eq 5}">
        <div class="text-right">
            <a class="text-info" href="<c:out value="${findMoneySchedulerExecutionsUrl}" />"><fmt:message
                    key="page.moneyscheduler.label.moreexecutionsforthisscheduler"/></a>
        </div>
    </c:if>
</div>

<p></p>

<c:url value="/secure/ftx/moneyprovision.do" var="moneyProvisionUrl"/>

<div class="table-responsive spacer">
    <label id="lblMoneyScheduler" class="groupHeading" for="moneyProvisionsTable"><fmt:message
            key="page.moneyscheduler.label.moneyprovisions"/></label>
    <display:table name="${moneySchedulerForm.moneyProvisions}" id="moneyProvisionsTable"
                   decorator="com.silvermoongroup.fsa.web.ftx.moneyprovision.MoneyProvisionTableDecorator">

        <display:column property="id" titleKey="label.id"
                        href="${moneyProvisionUrl}" paramId="moneyProvisionObjectReference"
                        paramProperty="objectReference"/>
        <display:column property="type" titleKey="label.type"
                        href="${moneyProvisionUrl}" paramId="moneyProvisionObjectReference"
                        paramProperty="objectReference"/>
        <display:column property="startDate" titleKey="ftx.moneyprovision.startdate"/>
        <display:column property="endDate" titleKey="ftx.moneyprovision.enddate"/>
        <display:column property="companyCode" titleKey="ftx.moneyprovision.companycode"/>
        <display:column property="context" titleKey="ftx.moneyprovision.context"/>

    </display:table>
</div>

<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="row">
    <div class="col-md-12 text-center">
        <html:form action="/secure/moneyscheduler.do">
            <input type="hidden" name="method" value=".back"/>
            <html:submit property="method" styleClass="btn btn-default btn-sm">
                <fmt:message key="button.back"/>
            </html:submit>
        </html:form>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>
</div>
<%} %>

