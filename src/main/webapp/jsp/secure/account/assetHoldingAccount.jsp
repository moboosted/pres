<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="assetHoldingAccountForm" scope="request"
             type="com.silvermoongroup.fsa.web.account.AssetHoldingAccountForm"/>

<div class="row">
    <label for="accountDetailsTable" class="groupHeading col-md-6">
        <fmt:message key="page.assetholdingaccount.label.detailsfor"/>
        <c:out value="${assetHoldingAccountForm.account.name}"/></label>
</div>

<div class="row form-container" id="accountDetailsTable">

    <%--Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${assetHoldingAccountForm.account.objectReference}"
                                                       display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.type"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${assetHoldingAccountForm.account.objectReference}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.name"/></label></div>
            <div class="col-md-9"><c:out value="${assetHoldingAccountForm.account.name}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="account.assetholdingaccount.financialasset"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${assetHoldingAccountForm.account.financialAsset.objectReference}" display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="account.account.externalreference"/></label></div>
            <div class="col-md-9"><c:out value="${assetHoldingAccountForm.account.externalReference}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.description"/></label>
            </div>
            <div class="col-md-9"><c:out value="${assetHoldingAccountForm.account.description}"/></div>
        </div>
    </div>

        <%--Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.companycode"/></label></div>
            <div class="col-md-9"><smg:fmt
                    value="${assetHoldingAccountForm.account.internalCompanyCode}"/></div>
        </div>
        <div class="row">
            <div class="col-md-12">&nbsp;</div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.openingdate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${assetHoldingAccountForm.account.openingDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="account.account.closingdate"/></label></div>
            <div class="col-md-9"><smg:fmt value="${assetHoldingAccountForm.account.closingDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="page.assetholdingaccount.label.currentstatus"/></label></div>
            <div class="col-md-9"><smg:fmt value="${assetHoldingAccountForm.account.status.state}"/></div>
        </div>
    </div>
</div>

<div id="recentAccountEntryPostings" class="table-responsive spacer">
    <label for="recentAccountEntryPostingsTable" class="groupHeading"><fmt:message
            key="page.assetholdingaccount.label.recententries"/></label>

    <c:if test="${!empty assetHoldingAccountForm.accountEntries}">
        <div>
            <fmt:message key="page.assetholdingaccount.label.formoreentries"/>
            <c:url value="/secure/account/accountentry/find.do" var="findAccountEntriesUrl">
                <c:param name="accountObjectReference" value="${assetHoldingAccountForm.accountObjectReference}"/>
                <c:param name="method" value=".findAndDisplay"/>
            </c:url>
            <a class="text-info" href="<c:out value="${findAccountEntriesUrl}" />"><fmt:message
                    key="page.assetholdingaccount.label.enquirefurther"/></a>
        </div>
    </c:if>

    <c:url var="accountEntryUrl" value="/secure/account/accountentry.do"/>
    <c:url var="unitTransactionUrl" value="/secure/ftx/unittransaction.do"/>

    <display:table name="${assetHoldingAccountForm.accountEntries}"
                   decorator="com.silvermoongroup.fsa.web.account.AccountEntriesTableDecorator"
                   id="recentAccountEntryPostingsTable">

        <display:column property="type" titleKey="account.accountentry.type" href="${accountEntryUrl}"
                        paramId="accountEntryObjectReference" paramProperty="objectReference"/>
        <display:column property="transactionType" titleKey="account.accountentry.generatingunittransaction" href="${unitTransactionUrl}"
                        paramId="unitTransactionObjectReference" paramProperty="unitTransaction"/>
        <display:column property="postedDate" titleKey="account.accountentry.posteddate"/>
        <display:column property="valueDate" titleKey="account.accountentry.valuedate"/>
        <display:column property="debitEntry" titleKey="account.accountentry.debitabbreviation" class="monetary"
                        headerClass="monetary"
                        decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
        <display:column property="creditEntry" titleKey="account.accountentry.creditabbreviation" class="monetary"
                        headerClass="monetary"
                        decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.AmountColumnDecorator"/>
    </display:table>

    <div class="text-right">
        <a class="text-info" href="<c:out value="${findAccountEntriesUrl}" />"><fmt:message
                key="page.assetholdingaccount.label.moreentriesforthisaccount"/></a>
    </div>
</div>

<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="row">
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
