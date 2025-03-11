<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="accountEntryForm" scope="request"
             type="com.silvermoongroup.fsa.web.account.AccountEntryForm"/>

<c:url var="accountUrl" value="/secure/account.do">
    <c:param name="accountObjectReference" value="${accountEntryForm.accountEntry.account.objectReference}" />
</c:url>

<c:url var="oppositeAccountEntryUrl" value="/secure/account/accountentry.do">
    <c:param name="accountEntryObjectReference" value="${accountEntryForm.accountEntry.oppositeEntry.objectReference}" />
</c:url>

<div class="row">
    <label for="accountEntryDetailsTable" class="groupHeading col-md-6">
        <fmt:message key="page.accountentry.label.detailsfor"/> </label>
</div>

<div class="row form-container" id="accountEntryDetailsTable">
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-8"><smg:objectReference value="${accountEntryForm.accountEntry.objectReference}"
                                                       display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-8"><smg:objectReference value="${accountEntryForm.accountEntry.objectReference}" display="type"/></div>
        </div>

        <c:choose>
            <c:when test="${accountEntryForm.financialTransaction != null}">
                <div class="row">
                    <div class="col-md-4"><label class="control-label"><fmt:message key="account.accountentry.amount"/></label></div>
                    <div class="col-md-8"><smg:fmtCurrencyAmount value="${accountEntryForm.accountEntry.amount}"/></div>
                </div>
            </c:when>
            <c:when test="${accountEntryForm.unitTransaction != null}">
                <div class="row">
                    <div class="col-md-4"><label class="control-label"><fmt:message key="account.accountentry.amount"/></label></div>
                    <div class="col-md-8"><smg:fmt value="${accountEntryForm.accountEntry.amount}"/></div>
                </div>
            </c:when>
        </c:choose>

        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.accountentry.debitcreditindicator"/></label></div>
            <div class="col-md-8"><smg:fmt value="${accountEntryForm.accountEntry.debitCreditIndicator}" /></div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.accountentry.description"/></label></div>
            <div class="col-md-8"><c:out value="${accountEntryForm.description}" /></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-4"><label class="control-label"><fmt:message key="account.accountentry.oppositeentry"/></label></div>
            <div class="col-md-8"><a class="text-info" href="${oppositeAccountEntryUrl}"><smg:objectReference display="type" value="${accountEntryForm.accountEntry.oppositeEntry.objectReference}"/></a></div>
        </div>

        <c:choose>
            <c:when test="${accountEntryForm.financialTransaction != null}">

                <c:url var="financialTransactionUrl" value="/secure/ftx/financialtransaction.do">
                    <c:param name="financialTransactionObjectReference" value="${accountEntryForm.financialTransaction.objectReference}" />
                </c:url>

                <div class="row">
                    <div class="col-md-4"><label class="control-label"><fmt:message key="page.accountentry.label.postedbyfinanicaltransaction"/></label></div>
                    <div class="col-md-8"><a class="text-info" href="${financialTransactionUrl}"><smg:objectReference value="${accountEntryForm.financialTransaction.objectReference}" display="type" /></a></div>
                </div>

            </c:when>

            <c:when test="${accountEntryForm.unitTransaction != null}">

                <c:url var="unitTransactionUrl" value="/secure/ftx/unittransaction.do">
                    <c:param name="unitTransactionObjectReference" value="${accountEntryForm.unitTransaction.objectReference}" />
                </c:url>

                <div class="row">
                    <div class="col-md-4"><label class="control-label"><fmt:message key="page.accountentry.label.postedbyunittransaction"/></label></div>
                    <div class="col-md-8"><a class="text-info" href="${unitTransactionUrl}"><smg:objectReference value="${accountEntryForm.unitTransaction.objectReference}" display="type" /></a></div>
                </div>

            </c:when>

        </c:choose>
    </div>

    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.accountentry.posteddate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${accountEntryForm.accountEntry.postedDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.accountentry.valuedate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${accountEntryForm.accountEntry.valueDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="page.accountentry.label.postedintoaccount"/></label></div>
            <div class="col-md-9"><a class="text-info" href="${accountUrl}"><c:out value="${accountEntryForm.accountEntry.account.name}"/></a></div>
        </div>
    </div>

</div>
