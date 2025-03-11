<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<jsp:useBean id="moneySchedulerExecutionForm" scope="request"
             type="com.silvermoongroup.fsa.web.moneyscheduler.execution.MoneySchedulerExecutionForm"/>

<c:url value="/secure/moneyscheduler.do" var="moneySchedulerUrl">
    <c:param name="moneySchedulerObjectReference"
             value="${moneySchedulerExecutionForm.moneySchedulerExecution.moneyScheduler.objectReference}"/>
</c:url>


<div class="row">
    <label for="moneySchedulerExecutionDetailsTable" class="groupHeading col-md-6"><fmt:message
            key="page.moneyschedulerexecution.label.detailsofthe"/> <smg:fmtType
            value="${moneySchedulerExecutionForm.moneySchedulerExecution.typeId}"/></label>
</div>

<div class="row form-container" id="moneySchedulerExecutionDetails">

    <%-- Left column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${moneySchedulerExecutionForm.moneySchedulerExecution.objectReference}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="common.type.Money Scheduler"/></label>
            </div>
            <div class="col-md-9"><a href="${moneySchedulerUrl}">
                <smg:objectReference
                        value="${moneySchedulerExecutionForm.moneySchedulerExecution.moneyScheduler.objectReference}"
                        display="type"/></a></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.moneyschedulerexecution.requestedexecutiondate"/></label></div>
            <div class="col-md-9"><smg:fmt
                    value="${moneySchedulerExecutionForm.moneySchedulerExecution.requestedExecutionDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.moneyschedulerexecution.cycleperiod"/></label></div>
            <div class="col-md-9"><smg:fmt
                    value="${moneySchedulerExecutionForm.moneySchedulerExecution.schedulerExecutionCyclePeriod.start}"/>
                &rarr; <smg:fmt
                        value="${moneySchedulerExecutionForm.moneySchedulerExecution.schedulerExecutionCyclePeriod.end}"/></div>
        </div>
    </div>

    <%--Right column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyschedulerexecution.startedat"/></label></div>
            <div class="col-md-9"><smg:fmt
                    value="${moneySchedulerExecutionForm.moneySchedulerExecution.startedAt}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyschedulerexecution.endedat"/></label></div>
            <div class="col-md-9"><smg:fmt value="${moneySchedulerExecutionForm.moneySchedulerExecution.endedAt}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyschedulerexecution.duration"/></label></div>
            <div class="col-md-9"><c:out value="${moneySchedulerExecutionForm.durationInMillis}"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyschedulerexecution.returncode"/></label></div>
            <div class="col-md-9"><smg:fmt value="${moneySchedulerExecutionForm.moneySchedulerExecution.returnCode}"/></div>
        </div>
    </div>
</div>


<div class="table-responsive spacer">
    <label for="generatedFinancialTransactions" class="groupHeading"><fmt:message
            key="page.moneyschedulerexecution.label.generatedfinancialtransactions"/></label>

    <c:url var="financialTransactionUrl" value="/secure/ftx/financialtransaction.do"/>

    <display:table name="${moneySchedulerExecutionForm.financialTransactions}" id="generatedFinancialTransactions"
                   decorator="com.silvermoongroup.fsa.web.ftx.FinancialTransactionTableDecorator">

        <display:column property="id" titleKey="label.id" href="${financialTransactionUrl}"
                        paramId="financialTransactionObjectReference" paramProperty="objectReference"/>
        <display:column property="type" titleKey="label.type" href="${financialTransactionUrl}"
                        paramId="financialTransactionObjectReference" paramProperty="objectReference"/>
        <display:column property="externalReference" titleKey="ftx.financialtransaction.externalreference"
                        maxLength="40"/>
        <display:column property="amount" titleKey="ftx.financialtransaction.amount" class="monetary" headerClass="monetary"/>
        <display:column property="postedDate" titleKey="ftx.financialtransaction.posteddate"/>
        <display:column property="paymentMethod" titleKey="ftx.financialtransaction.paymentmethod"/>
        <display:column property="contextType" titleKey="ftx.financialtransaction.context" maxLength="75"/>

    </display:table>
</div>
