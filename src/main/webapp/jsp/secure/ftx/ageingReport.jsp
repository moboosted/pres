<%@ page import="com.silvermoongroup.common.enumeration.EnumerationType" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="ageingReportForm" scope="request" type="com.silvermoongroup.fsa.web.ftx.form.AgeingReportForm"/>

<html:form action="/secure/ageingreport.do" styleId="tbform" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".calculate"/>

    <div class="form-group">
        <label for="companyCode" class="col-md-2 control-label"><fmt:message
                key="page.ageingreport.label.companycode"/></label>

        <div class="col-md-3">
            <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm">
                <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>
            </html:select>
        </div>
    </div>
    <div class="form-group">
        <label for="rootAccountTypeId" class="col-md-2 control-label"><fmt:message
                key="page.ageingreport.label.accounttype"/></label>

        <div class="col-md-3">
            <html:select property="rootAccountTypeId" styleId="rootAccountTypeId" styleClass="form-control input-sm">
                <smg:typeTree rootTypeId="6001" includeRootType="true" indent="true"/>
            </html:select>
            <div class="small">&nbsp;&nbsp;<fmt:message key="page.ageingreport.label.subtypesalwaysincluded"/></div>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
            <html:submit styleId="submitButton" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.ageingreport.action.calculate"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

    <c:url value="/secure/account.do" var="accountUrl"/>

    <c:if test="${!empty ageingReportForm.ageingReport}">

        <div class="table-responsive">

            <display:table name="requestScope.ageingReportForm.ageingReport.entries"
                           id="tableAgeingEntries"
                           decorator="com.silvermoongroup.fsa.web.ftx.AgeingReportTableDecorator"
                           requestURI="/secure/ageingreport.do"
                           export="true">

                <display:setProperty name="export.csv.filename" value="${ageingReportForm.exportFilename}.csv"/>
                <display:setProperty name="export.excel.filename" value="${ageingReportForm.exportFilename}.xls"/>

                <display:column property="id" titleKey="page.ageingreport.label.accountid" href="${accountUrl}" paramId="accountObjectReference"
                                paramProperty="accountObjectReference"/>
                <display:column property="accountName" titleKey="page.ageingreport.label.accountname"
                                href="${accountUrl}" paramId="accountObjectReference"
                                paramProperty="accountObjectReference"
                                sortable="true"/>
                <display:column property="unitOfMeasure" titleKey="page.ageingreport.label.unitofmeasure"
                                sortable="true"/>
                <display:column property="accountTypeId" titleKey="page.ageingreport.label.accounttype"
                                sortable="true"/>
                <display:column property="balanceNinetyDays" titleKey="page.ageingreport.label.balanceninetydays"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="openNinetyDays" titleKey="page.ageingreport.label.openninetydays"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="openSixtyDays" titleKey="page.ageingreport.label.opensixtydays"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="openThirtyDays" titleKey="page.ageingreport.label.openthirtydays"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="openCurrent" titleKey="page.ageingreport.label.opencurrent"
                                sortable="false" class="monetary" headerClass="monetary"/>

            </display:table>
        </div>

    </c:if>

</html:form>
