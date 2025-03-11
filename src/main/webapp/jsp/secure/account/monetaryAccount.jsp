<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="monetaryAccountForm" scope="request"
             type="com.silvermoongroup.fsa.web.account.MonetaryAccountForm"/>

<div class="row">
    <label for="accountDetailsTable" class="groupHeading col-md-6">
        <fmt:message key="page.monetaryaccount.label.detailsfor"/>
        <c:out value="${monetaryAccountForm.account.name}"/></label>
</div>

<div class="row form-container" id="accountDetailsTable">

    <%--Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${monetaryAccountForm.account.objectReference}"
                    display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${monetaryAccountForm.account.objectReference}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.name"/></label>
            </div>
            <div class="col-md-9"><c:out value="${monetaryAccountForm.account.name}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="page.monetaryaccount.label.currentbalance"/></label></div>
            <div class="col-md-9"><smg:fmt value="${monetaryAccountForm.account.currencyCode}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="account.account.externalreference"/></label></div>
            <div class="col-md-9"><c:out value="${monetaryAccountForm.account.externalReference}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="account.account.description"/></label></div>
            <div class="col-md-9"><c:out value="${monetaryAccountForm.account.description}"/></div>
        </div>

    </div>

    <%--Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.companycode"/></label></div>
            <div class="col-md-9"><smg:fmt
                    value="${monetaryAccountForm.account.internalCompanyCode}"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.openingdate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${monetaryAccountForm.account.openingDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.closingdate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${monetaryAccountForm.account.closingDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="page.monetaryaccount.label.currentstatus"/></label></div>
            <div class="col-md-9"><smg:fmt value="${monetaryAccountForm.account.status.state}"/></div>
        </div>


    </div>
</div>

<div id="recentAccountEntryPostings" class="table-responsive spacer">
    <label for="recentAccountEntryPostingsTable" class="groupHeading"><fmt:message
            key="page.monetaryaccount.label.recententries"/></label>

    <c:url value="/secure/account/accountentry/find.do" var="findAccountEntriesUrl">
        <c:param name="accountObjectReference" value="${monetaryAccountForm.accountObjectReference}"/>
        <c:param name="method" value=".findAndDisplay"/>
    </c:url>

    <c:if test="${!empty monetaryAccountForm.accountEntries}">
        <div>
            <fmt:message key="page.monetaryaccount.label.formoreentries"/>
            <a class="text-info" href="<c:out value="${findAccountEntriesUrl}" />"><fmt:message
                    key="page.monetaryaccount.label.enquirefurther"/></a>
        </div>
    </c:if>

    <c:url var="accountEntryUrl" value="/secure/account/accountentry.do"/>
    <c:url var="financialTransactionUrl" value="/secure/ftx/financialtransaction.do"/>

        <display:table name="${monetaryAccountForm.accountEntries}"
                       decorator="com.silvermoongroup.fsa.web.account.AccountEntriesTableDecorator"
                       id="recentAccountEntryPostingsTable">

            <display:column property="id" titleKey="label.id" href="${accountEntryUrl}"
                            paramId="accountEntryObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="account.accountentry.type"
                            href="${accountEntryUrl}" paramId="accountEntryObjectReference"
                            paramProperty="objectReference"/>

            <c:choose>
                <c:when test="${!empty recentAccountEntryPostingsTable.financialTransaction}">
                    <display:column property="transactionType"
                                    titleKey="account.accountentry.generatingfinancialtransaction" href="${financialTransactionUrl}"
                                    paramId="financialTransactionObjectReference" paramProperty="financialTransaction"/>
                </c:when>
                <c:otherwise>
                    <display:column property="transactionType"
                                    titleKey="account.accountentry.generatingfinancialtransaction" />
                </c:otherwise>
            </c:choose>

            <display:column property="postedDate" titleKey="account.accountentry.posteddate"/>
            <display:column property="valueDate" titleKey="account.accountentry.valuedate"/>
            <display:column property="debitEntry" titleKey="account.accountentry.debitabbreviation" class="monetary"
                            headerClass="monetary"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
            <display:column property="creditEntry" titleKey="account.accountentry.creditabbreviation" class="monetary"
                            headerClass="monetary"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
        </display:table>

    <div style="text-align: right">
        <a class="text-info" href="<c:out value="${findAccountEntriesUrl}" />"><fmt:message
                key="page.monetaryaccount.label.moreentriesforthisaccount"/></a>
    </div>
</div>


<div id="accountAgeing" style="margin-top: 3em;">
    <table border="1" style="width:60%; margin-left: auto; margin-right: auto; margin-top: 1em; text-align: center;"
           id="accountAgeingTable">
        <tr>
            <td style="width: 20%; vertical-align: middle">Over 120 <br/> <fmt:message
                    key="page.accountenquiry.label.days"/>
            </td>
            <td style="width: 20%; vertical-align: middle">91-120<br/> <fmt:message
                    key="page.accountenquiry.label.days"/>
            </td>
            <td style="width: 20%; vertical-align: middle">61-90<br/> <fmt:message
                    key="page.accountenquiry.label.days"/>
            </td>
            <td style="width:20%; vertical-align: middle">31-60<br/> <fmt:message key="page.accountenquiry.label.days"/>
            </td>
            <td style="width:20%; vertical-align: middle"><fmt:message key="page.accountenquiry.label.current"/></td>
        </tr>
        <tr>
            <c:forEach var="element" items="${monetaryAccountForm.accountAgeing}">
                <td style="width: 20%; vertical-align: middle"><smg:fmtCurrencyAmount cc="false"
                                                                                      value="${element.amount}"/>
                    <smg:fmt
                            value="${element.debitCreditIndicator}"/></td>
            </c:forEach>
        </tr>
    </table>
</div>

<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="row spacer">
    <div class="col-md-12 text-center">
        <html:form action="/secure/account/monetaryAccount.do">
            <input type="hidden" name="method" value=".back"/>
            <html:submit property="method" styleClass="btn btn-default btn-sm">
                <bean:message key="button.back"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </html:form>
    </div>
</div>
<%} %>
