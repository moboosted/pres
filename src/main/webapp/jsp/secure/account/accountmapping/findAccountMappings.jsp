<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="findAccountMappingForm" scope="request"
             type="com.silvermoongroup.fsa.web.account.accountmapping.FindAccountMappingForm"/>

<html:form action="/secure/account/accountmapping/find.do" styleId="filterForm" styleClass="form-inline">

    <input type="hidden" name="method" value=".filter"/>

    <div class="form-group">
        <label for="companyCode"><smg:fmt value="account.accountmapping.companycode"/></label>
        <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm">
            <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>
        </html:select>
    </div>

    <div class="form-group">
        <label for="financialTransactionType"><smg:fmt value="account.accountmapping.financialtransactiontype"/></label>
        <html:select property="financialTransactionType" styleId="financialTransactionType" styleClass="form-control input-sm">
            <smg:generalOptionsCollection property="financialTransactionTypeOptions"/>
        </html:select>
    </div>

    <html:submit onclick="this.form.method.value = '.filter'; return true;" styleClass="btn btn-primary btn-sm">
        <fmt:message key="page.findaccountmapping.action.applyfilter"/>
    </html:submit>
    <html:submit onclick="this.form.method.value = '.clearFilter'; return true;" styleClass="btn btn-default btn-sm">
        <fmt:message key="page.findaccountmapping.action.clearfilter"/>
    </html:submit>
    <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>

</html:form>

<div class="text-right">
    <html:form action="/secure/account/accountmapping/find.do">
        <input type="hidden" name="method" value=".add"/>
        <html:submit property="method" styleClass="btn btn-primary btn-sm">
            <fmt:message key="page.findaccountmapping.action.add"/>
        </html:submit>
    </html:form>
</div>

<div class="table-responsive spacer">
<display:table name="${findAccountMappingForm.results}" id="accountMapping" pagesize="15"
               requestURI="/secure/account/accountmapping/find.do"
               decorator="com.silvermoongroup.fsa.web.account.accountmapping.AccountMappingTableDecorator">

    <c:url value="/secure/account.do" var="accountURL"/>

    <display:column property="companyCode" titleKey="account.accountmapping.companycode"/>
    <display:column property="financialTransactionType" titleKey="account.accountmapping.financialtransactiontype"
                    maxLength="25"/>
    <display:column property="contextType" titleKey="account.accountmapping.contexttype" maxLength="25"/>
    <display:column property="currencyCode" titleKey="account.accountmapping.currency"/>
    <display:column property="meansOfPayment" titleKey="account.accountmapping.meansofpayment" maxLength="15"/>
    <display:column property="mappingDirection" titleKey="account.accountmapping.mappingdirection"/>
    <display:column property="account" titleKey="account.accountmapping.accountid" href="${accountURL}"
                    paramId="accountObjectReference" paramProperty="account.objectReference"/>

    <c:url value="/secure/account/accountmapping/update.do" var="updateAccountMappingURL">
        <c:param name="accountMappingObjectReference" value="${accountMapping.objectReference}"/>
    </c:url>

    <display:column class="m">
        <html:form action="/secure/account/accountmapping/find.do">
            <html:hidden property="accountMappingObjectReference" value="${accountMapping.objectReference}"/>
            <input type="hidden" name="method" value=".update"/>
            <html:submit styleClass="btn btn-default btn-sm">
                <fmt:message key="page.findaccountmapping.action.update"/>
            </html:submit>
        </html:form>
    </display:column>

    <display:column class="m">
        <html:form action="/secure/account/accountmapping/find.do">
            <html:hidden property="companyCode"/>
            <html:hidden property="financialTransactionType"/>
            <html:hidden property="accountMappingObjectReference" value="${accountMapping.objectReference}"/>
            <input type="hidden" name="method" value=".delete"/>
            <html:submit styleClass="btn btn-default btn-sm">
                <fmt:message key="page.findaccountmapping.action.delete"/>
            </html:submit>
        </html:form>
    </display:column>
 </div>

</display:table>
