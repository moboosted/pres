<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<script src="${pageContext.request.contextPath}/js/bootbox.min.js"></script>

<jsp:useBean id="unitTransactionForm" scope="request" type="com.silvermoongroup.fsa.web.ftx.UnitTransactionForm"/>

<div class="row">
    <label for="unitTransactionDetailsTable" class="groupHeading col-md-6">
        <fmt:message key="page.unittransaction.label.detailsfor"/></label>
</div>

<div class="row form-container" id="unitTransactionDetailsTable">

    <%-- Left column--%>
    <div class="col-md-6">

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${unitTransactionForm.unitTransaction.objectReference}" display="id"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${unitTransactionForm.unitTransaction.objectReference}"
                                                       display="type"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.externalreference"/></label></div>
            <div class="col-md-9"><c:out value="${unitTransactionForm.unitTransaction.externalReference}"/></div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.companycode"/></label></div>
            <div class="col-md-9"><smg:fmt value="${unitTransactionForm.unitTransaction.internalCompanyCode}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.financialtransaction.amount"/></label></div>
            <div class="col-md-9"><smg:fmtMeasureAmount value="${unitTransactionForm.unitTransaction.numberOfUnits}"/></div>
        </div>


        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
    </div>

    <%-- Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.unittransaction.posteddate"/></label></div>
            <div class="col-md-4"><smg:fmt value="${unitTransactionForm.unitTransaction.postedDate}"/></div>
        </div>

        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.financialtransaction.context"/></label></div>
            <div class="col-md-4"><smg:objectReference value="${unitTransactionForm.unitTransaction.context}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.description"/></label></div>
            <div class="col-md-4"><c:out value="${unitTransactionForm.unitTransaction.description}"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>

    </div>

</div>

<div class="row">
    <div class="col-md-12">
        <div class="table-responsive spacer">
            <label for="postedAccountEntriesTable" class="groupHeading"><fmt:message
                    key="page.unittransaction.label.postedaccountentries"/></label>

            <c:url var="accountUrl" value="/secure/account.do"/>
            <c:url var="accountEntryUrl" value="/secure/account/accountentry.do"/>

            <display:table name="${unitTransactionForm.unitTransaction.postedToAccountEntries}"
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

<div class="row">
    <div class="col-md-12">
        <div class="table-responsive spacer">
            <label for="unitTransactionFormTable" class="groupHeading">
                <fmt:message key="page.unittransaction.label.financialtransactions"/>
            </label>

            <display:table name="${unitTransactionForm.unitTransaction.unitFinancialTransactionRelationships}"
                           id="unitTransactionFormTable"
                           decorator="com.silvermoongroup.fsa.web.ftx.UnitFinancialTransactionTableDecorator">

                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="financialTransaction.objectReference" titleKey="ftx.financialtransaction.transactionnumber"
                                decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.PaymentLinkColumnDecorator"/>
                <display:column property="financialTransaction.postedDate" titleKey="ftx.financialtransaction.posteddate"/>
                <display:column property="currencyAmount" titleKey="ftx.financialtransaction.amount"/>

            </display:table>
        </div>
    </div>
</div>

<div class="row spacer">

    <c:url var="unitTransactionUrl" value="/secure/ftx/unittransaction.do"/>

    <div class="col-md-6">
        <div><label for="relatedFromUnitTransactions" class="groupHeading"><fmt:message
                key="page.unittransaction.label.relatedfromunittransactions"/></label></div>
        <div class="table-responsive">
            <display:table name="${unitTransactionForm.unitTransaction.relatedFromUnitTransactions}"
                           id="relatedFromUnitTransactions"
                           decorator="com.silvermoongroup.fsa.web.ftx.UnitTransactionRelationshipTableDecorator">

                <display:column property="relatedFrom" titleKey="ftx.financialtransactionrelationship.relatedfrom"
                                href="${unitTransactionUrl}" paramId="unitTransactionObjectReference"
                                paramProperty="relatedFromUnitTransaction"/>
                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="measureAmount" titleKey="ftx.financialtransactionrelationship.amount"/>

            </display:table>
        </div>
    </div>

    <div class="col-md-6">
        <div><label for="relatedToUnitTransactions" class="groupHeading"><fmt:message
                key="page.unittransaction.label.relatedtounittransactions"/></label></div>
        <div class="table-responsive">
            <display:table name="${unitTransactionForm.unitTransaction.relatedToUnitTransactions}"
                           id="relatedToUnitTransactions"
                           decorator="com.silvermoongroup.fsa.web.ftx.UnitTransactionRelationshipTableDecorator">

                <display:column property="relatedTo" titleKey="ftx.financialtransactionrelationship.relatedto"
                                href="${unitTransactionUrl}" paramId="unitTransactionObjectReference"
                                paramProperty="relatedToUnitTransaction"/>
                <display:column property="type" titleKey="ftx.financialtransactionrelationship.type"/>
                <display:column property="measureAmount" titleKey="ftx.financialtransactionrelationship.amount"/>

            </display:table>
        </div>
    </div>

</div>

