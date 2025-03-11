<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>

<jsp:useBean id="paymentForm" scope="request" type="com.silvermoongroup.fsa.web.ftx.PaymentForm"/>

<script type="text/javascript">
    function reversePayment() {
        var confirmMessage = "<fmt:message key="page.payment.label.confirmreversal"/>";
        var labelYes = '<fmt:message key="label.yesno.true" />';
        var labelNo = '<fmt:message key="label.yesno.false" />';
        var title = '<fmt:message key="label.confirmation" />';
        bootbox.dialog({
            message: confirmMessage,
            title: title,
            buttons: {
                ok: {
                    label: labelYes,
                    className: "btn-primary",
                    callback: function () {
                        document.getElementById("reversePaymentForm").submit();
                        return true;
                    }
                },
                cancel: {
                    label: labelNo
                }
            }
        });
        return false;
    }
</script>

<html:form action="/secure/ftx/payment.do" method="POST" styleId="reversePaymentForm">
    <html:hidden property="financialTransactionObjectReference"/>
    <input type="hidden" name="method" value=".reversePayment"/>
</html:form>

<c:url var="moneyProvisionUrl" value="/secure/ftx/moneyprovision.do">
    <c:param name="moneyProvisionObjectReference" value="${paymentForm.financialTransaction.moneyProvision}"/>
</c:url>

<c:url var="moneySchedulerExecutionUrl" value="/secure/moneyscheduler/execution.do">
    <c:param name="moneySchedulerExecutionObjectReference"
             value="${paymentForm.financialTransaction.moneySchedulerExecution}"/>
</c:url>

<c:url var="moneySchedulerUrl" value="/secure/moneyscheduler.do">
    <c:param name="moneySchedulerObjectReference" value="${paymentForm.financialTransaction.moneyScheduler}"/>
</c:url>

<div class="row">
    <label for="paymentDetailsTable" class="groupHeading col-md-6">
        <fmt:message key="page.payment.label.detailsfor"/></label>
</div>

<div class="row form-container" id="paymentDetailsTable">

    <%-- Left column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${paymentForm.financialTransaction.objectReference}" display="id"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${paymentForm.financialTransaction.objectReference}"
                                                       display="type"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.externalreference"/></label></div>
            <div class="col-md-9"><c:out value="${paymentForm.financialTransaction.externalReference}"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.companycode"/></label></div>
            <div class="col-md-9"><smg:fmt value="${paymentForm.financialTransaction.internalCompanyCode}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.amount"/></label></div>
            <div class="col-md-9"><smg:fmtCurrencyAmount value="${paymentForm.financialTransaction.amount}"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.moneyprovision"/></label></div>
            <div class="col-md-9"><a class="text-info" href="${moneyProvisionUrl}"><smg:objectReference
                    value="${paymentForm.financialTransaction.moneyProvision}" display="type"/></a></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.moneyprovisionelement"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${paymentForm.financialTransaction.moneyProvisionElement}"
                                                       display="type"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.moneyscheduler"/></label></div>
            <div class="col-md-9"><a class="text-info" href="${moneySchedulerUrl}"><smg:objectReference
                    value="${paymentForm.financialTransaction.moneyScheduler}" display="type"/></a></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.moneyschedulerexecution"/></label></div>
            <div class="col-md-9"><a class="text-info" href="${moneySchedulerExecutionUrl}"><smg:objectReference
                    value="${paymentForm.financialTransaction.moneySchedulerExecution}" display="type"/></a></div>
        </div>

    </div>

    <%-- Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.posteddate"/></label></div>
            <div class="col-md-4"><smg:fmt value="${paymentForm.financialTransaction.postedDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.payment.valuedate"/></label></div>
            <div class="col-md-4"><c:out value="${paymentForm.financialTransaction.valueDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.payment.paymentdate"/></label></div>
            <div class="col-md-4"><smg:fmt value="${paymentForm.financialTransaction.paymentDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.paymentmethod"/></label></div>
            <div class="col-md-4"><smg:fmt value="${paymentForm.financialTransaction.paymentMethod.theMethod}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.payment.collectionmandate"/></label></div>
            <div class="col-md-4"><smg:objectReference value="${paymentForm.financialTransaction.collectionMandate}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.context"/></label></div>
            <div class="col-md-4"><smg:objectReference value="${paymentForm.financialTransaction.context}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.description"/></label></div>
            <div class="col-md-4"><c:out value="${paymentForm.financialTransaction.description}"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.payee"/></label></div>
            <div class="col-md-4"><smg:objectReference value="${paymentForm.financialTransaction.financialTransactionPayee}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.payer"/></label></div>
            <div class="col-md-4"><smg:objectReference value="${paymentForm.financialTransaction.financialTransactionPayer}"
                                                       display="type"/></div>
        </div>

        <div class="col-md-12 text-right form-group">
            <input type="button" class="btn btn-danger btn-sm"
                   value="<fmt:message key="page.payment.label.reversepayment"/>"
                   onclick="reversePayment()"/>
        </div>

    </div>

</div>

<div class="row">
    <div class="col-md-12">
        <div class="table-responsive spacer">
            <label for="postedAccountEntriesTable" class="groupHeading"><fmt:message
                    key="page.payment.label.postedaccountentries"/></label>

            <c:url var="accountUrl" value="/secure/account.do"/>
            <c:url var="accountEntryUrl" value="/secure/account/accountentry.do"/>

            <display:table name="${paymentForm.financialTransaction.postedToAccountEntries}"
                           id="postedAccountEntriesTable"
                           decorator="com.silvermoongroup.fsa.web.account.AccountEntriesTableDecorator">

                <display:column property="id" titleKey="label.id" href="${accountEntryUrl}"
                                paramId="accountEntryObjectReference" paramProperty="objectReference"/>
                <display:column property="type" titleKey="account.accountentry.type"
                                href="${accountEntryUrl}"
                                paramId="accountEntryObjectReference" paramProperty="objectReference"/>
                <display:column property="accountName" titleKey="page.paymentdue.label.postedtoaccount"
                                href="${accountUrl}" paramId="accountObjectReference" paramProperty="account"/>
                <display:column property="postedDate" titleKey="account.accountentry.posteddate"/>
                <display:column property="valueDate" titleKey="account.accountentry.valuedate"/>
                <display:column property="debitEntry" titleKey="account.accountentry.debitabbreviation"

                                decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
                <display:column property="creditEntry" titleKey="account.accountentry.creditabbreviation"

                                decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>

            </display:table>
        </div>
    </div>
</div>

<c:url value="/secure/ftx/unittransaction.do" var="unitTransactionUrl"/>
<div class="row">
    <div class="col-md-12">
        <div class="table-responsive spacer">
            <label for="unitTransactionTable" class="groupHeading"><fmt:message
                    key="page.payment.label.unittransactions"/></label>

            <display:table name="${paymentForm.financialTransaction.unitFinancialTransactionRelationships}"
                           id="unitTransactionTable"
                           decorator="com.silvermoongroup.fsa.web.ftx.UnitFinancialTransactionTableDecorator">

                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="unitTransaction.objectReference.objectId" titleKey="ftx.unittransaction.transactionnumber"
                                href="${unitTransactionUrl}"
                                paramId="unitTransactionObjectReference" paramProperty="unitTransaction.objectReference"/>
                <display:column property="unitTransaction.postedDate" titleKey="ftx.unittransaction.posteddate"/>
                <display:column property="unitTransaction.effectiveDate" titleKey="ftx.unittransaction.effectivedate"/>
                <display:column property="numberOfUnits" titleKey="ftx.unittransaction.numberofunits"/>

            </display:table>
        </div>
    </div>
</div>

<div class="row spacer">

    <c:url var="financialTransactionUrl" value="/secure/ftx/financialtransaction.do"/>

    <div class="col-md-6">
        <div><label for="relatedFromFinancialTransactions" class="groupHeading"><fmt:message
                key="page.payment.label.relatedfromfinancialtransactions"/></label></div>
        <div class="table-responsive">
            <display:table name="${paymentForm.financialTransaction.relatedFromFinancialTransactions}"
                           id="relatedFromFinancialTransactions"
                           decorator="com.silvermoongroup.fsa.web.ftx.FinancialTransactionRelationshipTableDecorator">

                <display:column property="relatedFrom" titleKey="ftx.financialtransactionrelationship.relatedfrom"
                                href="${financialTransactionUrl}" paramId="financialTransactionObjectReference"
                                paramProperty="relatedFromFinancialTransaction"/>
                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="amount" titleKey="ftx.financialtransactionrelationship.amount"/>

            </display:table>
        </div>
    </div>

    <div class="col-md-6">
        <div><label for="relatedToFinancialTransactions" class="groupHeading"><fmt:message
                key="page.payment.label.relatedtofinancialtransactions"/></label></div>
        <div class="table-responsive">
            <display:table name="${paymentForm.financialTransaction.relatedToFinancialTransactions}"
                           id="relatedToFinancialTransactions"
                           decorator="com.silvermoongroup.fsa.web.ftx.FinancialTransactionRelationshipTableDecorator">

                <display:column property="relatedTo" titleKey="ftx.financialtransactionrelationship.relatedto"
                                href="${financialTransactionUrl}" paramId="financialTransactionObjectReference"
                                paramProperty="relatedToFinancialTransaction"/>
                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="amount" titleKey="ftx.financialtransactionrelationship.amount"/>

            </display:table>
        </div>
    </div>

</div>

