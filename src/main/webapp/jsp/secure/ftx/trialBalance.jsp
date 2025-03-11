<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="trialBalanceForm" scope="request" type="com.silvermoongroup.fsa.web.ftx.form.TrialBalanceForm"/>

<script type="text/javascript">
    $(document).ready(function () {
        $('#lastMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#dateFrom').val("${trialBalanceForm.firstOfPreviousMonth}");
            $('#dateTo').val("${trialBalanceForm.lastOfPreviousMonth}");
        });
        $('#currentMonthLink').bind('click', function (e) {
            e.preventDefault();
            $('#dateFrom').val("${trialBalanceForm.firstOfCurrentMonth}");
            $('#dateTo').val("${trialBalanceForm.lastOfCurrentMonth}");
        });

    });
</script>

<html:form action="/secure/trialbalance.do" styleId="tbform" styleClass="form-horizontal">

<input type="hidden" name="method" value=".calculate"/>


<div class="form-group">
    <label for="dateFrom" class="col-md-2 control-label"><fmt:message key="page.trialbalance.label.date"/></label>

    <div class="col-md-10 form-inline">
        <label for="dateFrom"><fmt:message key="page.trialbalance.label.from"/>:</label>
        <html:text property="dateFrom" styleId="dateFrom" styleClass="form-control input-sm datefield"
                   errorStyleClass="form-control input-sm datefield has-error"/>
        <label for="dateTo"><fmt:message key="page.trialbalance.label.to"/>:</label>
        <html:text property="dateTo" styleId="dateTo" styleClass="form-control input-sm datefield"
                   errorStyleClass="form-control input-sm datefield has-error"/>
        &nbsp;&nbsp;[ <a href="#" id="lastMonthLink"><fmt:message key="label.lastmonth"/></a> ]
        &nbsp;[ <a href="#" id="currentMonthLink"><fmt:message key="label.currentmonth"/></a> ]
    </div>
</div>

<div class="form-group">
    <label for="companyCode" class="col-md-2 control-label"><fmt:message
            key="page.trialbalance.label.companycode"/></label>

    <div class="col-md-3">
        <html:select property="companyCode" styleId="companyCode" styleClass="form-control input-sm">
            <smg:enumOptions enumTypeId="<%=EnumerationType.INTERNAL_COMPANY_CODE.getValue()%>" showTerminated = "true"/>
        </html:select>
    </div>
</div>
<div class="form-group">
    <label for="rootAccountTypeId" class="col-md-2 control-label"><fmt:message
            key="page.trialbalance.label.accounttype"/></label>

    <div class="col-md-3">
        <html:select property="rootAccountTypeId" styleId="rootAccountTypeId" styleClass="form-control input-sm">
            <smg:typeTree rootTypeId="6001" includeRootType="true" indent="true"/>
        </html:select>
        <div class="small">&nbsp;&nbsp;<fmt:message key="page.trialbalance.label.subtypesalwaysincluded"/></div>
    </div>
</div>
<div class="form-group">
    <label for="groupByName" class="col-md-2 control-label"><fmt:message key="label.groupby"/></label>

    <div class="col-md-6">
        <html:radio property="groupBy" styleId="groupByName" value="NAME" styleClass="radio-inline"/> <fmt:message
            key="page.trialbalance.label.accountname"/><br/>
        <html:radio property="groupBy" styleId="groupByType" value="TYPE" styleClass="radio-inline"/> <fmt:message
            key="page.trialbalance.label.accounttype"/>
    </div>
</div>
<div class="form-group">
    <label for="balanceTypePosted_Date" class="col-md-2 control-label"><fmt:message key="label.balancetype"/></label>

    <div class="col-md-6">
        <html:radio property="balanceType" styleId="balanceTypePosted_Date" value="POSTED_DATE" styleClass="radio-inline"/> <fmt:message
            key="page.trialbalance.label.balanceTypePosted_Date"/><br/>
        <html:radio property="balanceType" styleId="balanceTypeValue_Date" value="VALUE_DATE" styleClass="radio-inline"/> <fmt:message
            key="page.trialbalance.label.balanceTypeValue_Date"/>
    </div>
</div>

<div class="form-group">
    <label for="dateFrom" class="col-md-2 control-label"><fmt:message
            key="page.trialbalance.label.hideentrieswithnomovement"/></label>

    <div class="col-md-6">
        <html:checkbox property="hideEntriesWithNoMovement" styleId="hideEntries" styleClass="checkbox"/>
    </div>
</div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-10">
        <html:submit styleId="submitButton" styleClass="btn btn-primary btn-sm">
            <fmt:message key="page.trialbalance.action.calculate"/>
        </html:submit>
        <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
            </div>
    </div>

<c:if test="${!empty trialBalanceForm.trialBalance}">

        <div class="table-responsive">
            <display:table name="requestScope.trialBalanceForm.trialBalance.entries"
                           id="tableBalanceEntries"
                           decorator="com.silvermoongroup.fsa.web.ftx.TrialBalanceTableDecorator"
                           requestURI="/secure/trialbalance.do"
                           export="true">

                <display:setProperty name="export.csv.filename" value="${trialBalanceForm.exportFilename}.csv"/>
                <display:setProperty name="export.excel.filename" value="${trialBalanceForm.exportFilename}.xls"/>

                <c:choose>
                    <c:when test="${trialBalanceForm.groupBy eq 'NAME'}">
                        <display:column property="accountName" titleKey="page.trialbalance.label.accountname"
                                        sortable="true"/>
                    </c:when>
                    <c:otherwise>
                        <display:column property="accountType" titleKey="page.trialbalance.label.accounttype"
                                        sortable="true"/>
                    </c:otherwise>
                </c:choose>
                <display:column property="unitOfMeasure" titleKey="page.trialbalance.label.unitofmeasure"
                                sortable="true"/>
                <display:column property="openingBalance" titleKey="page.trialbalance.label.openingbalance"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="debitMovement" titleKey="page.trialbalance.label.drmovement"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="creditMovement" titleKey="page.trialbalance.label.crmovement"
                                sortable="false" class="monetary" headerClass="monetary"/>
                <display:column property="closingBalance" titleKey="page.trialbalance.label.closingbalance"
                                sortable="false" class="monetary" headerClass="monetary"/>

            </display:table>
        </div>

        <c:if test="${! empty trialBalanceForm.trialBalance.controlEntries}">

            <div class="table-responsive spacer">
                <display:table name="requestScope.trialBalanceForm.trialBalance.controlEntries"
                               decorator="com.silvermoongroup.fsa.web.ftx.TrialBalanceControlTableDecorator"
                               id="tbcontroltable">

                    <display:setProperty name="paging.banner.all_items_found" value=""/>

                    <display:column property="unitOfMeasure" titleKey="page.trialbalance.label.unitofmeasure"
                                    sortable="false"/>
                    <display:column property="debitMovement" titleKey="page.trialbalance.label.drmovement"
                                    sortable="false" class="monetary" headerClass="monetary"/>
                    <display:column property="creditMovement" titleKey="page.trialbalance.label.crmovement"
                                    sortable="false" class="monetary" headerClass="monetary" />
                    <display:column property="netMovement" titleKey="page.trialbalance.label.netmovement"
                                    sortable="false" class="monetary" headerClass="monetary"/>

                </display:table>
            </div>

        </c:if>

</c:if>

</html:form>
