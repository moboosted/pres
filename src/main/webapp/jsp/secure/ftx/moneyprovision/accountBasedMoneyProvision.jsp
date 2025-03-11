<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<jsp:useBean id="moneyProvisionForm" scope="request"
             type="com.silvermoongroup.fsa.web.ftx.moneyprovision.MoneyProvisionForm"/>

<div class="row form-container" id="moneyProvisionDetails">

    <%--Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${moneyProvisionForm.moneyProvision.objectReference}" display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.type"/></label></div>
            <div class="col-md-9"><smg:objectReference value="${moneyProvisionForm.moneyProvision.objectReference}" display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.accountbalance"/></label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.accountBalance}"/> <c:out
                    value="${moneyProvisionForm.debitCreditIndicator}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.description"/></label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.description}"/></div>
        </div>
     </div>

     <%--Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.startdate"/> &rarr; <fmt:message key="ftx.moneyprovision.enddate"/></label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.startDate}"/> &rarr; <c:out value="${moneyProvisionForm.moneyProvision.endDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.status"/> &nbsp;&nbsp;(<smg:fmt
                    value="page.moneyprovision.label.asat"/> <smg:fmt value="${moneyProvisionForm.statusDate}"/>)
            </label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.status}"/></div>
        </div>
     </div>
</div>


<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="row spacer">
    <div class="col-md-12 text-center">
        <html:form action="/secure/ftx/moneyprovision.do">
            <input type="hidden" name="method" value=".back"/>
            <html:submit property="method" styleClass="btn btn-default btn-sm">
                <fmt:message key="button.back"/>
            </html:submit>
        </html:form>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
    </div>
</div>
<%} %>