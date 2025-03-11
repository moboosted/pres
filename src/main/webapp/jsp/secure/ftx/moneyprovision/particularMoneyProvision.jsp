<%@ page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="moneyProvisionForm" scope="request"
             type="com.silvermoongroup.fsa.web.ftx.moneyprovision.MoneyProvisionForm"/>

<div class="row">
    <label for="moneyProvisionDetails" class="groupHeading col-md-6">
        <fmt:message key="label.details"/></label>
</div>

<div class="row form-container" id="moneyProvisionDetails">

    <%--Left column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="label.id"/></label></div>
            <div class="col-md-9"><smg:objectReference
                    value="${moneyProvisionForm.moneyProvision.objectReference}" display="id"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.type"/></label>
            </div>
            <div class="col-md-9"><smg:objectReference value="${moneyProvisionForm.moneyProvision.objectReference}"
                                                       display="type"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message
                    key="ftx.moneyprovision.description"/></label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.description}"/></div>
        </div>
    </div>

    <%--Right column--%>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.startdate"/> &rarr;
                <fmt:message
                        key="ftx.moneyprovision.enddate"/></label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.startDate}"/> &rarr;
                <c:out value="${moneyProvisionForm.moneyProvision.endDate}"/></div>
        </div>
        <div class="row">
            <div class="col-md-3"><label class="control-label"><fmt:message key="ftx.moneyprovision.status"/> &nbsp;&nbsp;(<smg:fmt
                    value="page.moneyprovision.label.asat"/> <smg:fmt value="${moneyProvisionForm.statusDate}"/>)
            </label></div>
            <div class="col-md-9"><c:out value="${moneyProvisionForm.moneyProvision.status}"/></div>
        </div>
    </div>
</div>

<div class="row" style="margin-top: 30px">
    <label for="moneyProvisionDetails" class="groupHeading col-md-6">
        <fmt:message key="page.moneyprovision.label.moneyprovisionelements"/></label>
</div>

<div>
    <html:form action="/secure/ftx/moneyprovision.do" styleId="filterForm" styleClass="form-inline">

        <input type="hidden" name="method" value=".applyFilter"/>
        <html:hidden property="moneyProvisionContextObjectReference"/>
        <html:hidden property="moneyProvisionObjectReference"/>

        <div class="form-group">
            <label for="filterType"><smg:fmt
                    value="page.moneyprovision.label.filterdateson"/></label>
            <html:select property="filterType" styleId="filterType" styleClass="form-control input-sm">
                <html:optionsCollection property="filterTypeOptions" value="value" label="label"/>
            </html:select>
        </div>

        <div class="form-group">
            <label for="filterDateFrom"><smg:fmt value="page.moneyprovision.label.from"/>:</label>
            <html:text property="filterDateFrom" styleClass="form-control input-sm datefield"
                       errorStyleClass="form-control input-sm datefield has-error" styleId="filterDateFrom"/>
        </div>

        <div class="form-group">
            <label for="filterDateTo"><smg:fmt value="page.moneyprovision.label.to"/>:</label>
            <html:text property="filterDateTo" styleClass="form-control input-sm datefield"
                       errorStyleClass="form-control input-sm datefield has-error" styleId="filterDateTo"/>
        </div>

        <div class="form-group">
            <label for="showTotals"><smg:fmt value="page.moneyprovision.label.showtotals"/></label>
            <html:checkbox property="showTotals" styleId="showTotals" styleClass="checkbox-inline"/>
        </div>

        <div class="form-group">
            <label for="groupBy"><smg:fmt value="label.groupby"/></label>
            <html:select
                    property="groupBy" styleId="groupBy" styleClass="form-control input-sm">
                <html:optionsCollection property="groupByOptions" value="value" label="label"/>
            </html:select>
        </div>

        <div class="form-group">
            <html:submit property="method" styleClass="btn btn-primary btn-sm">
                <fmt:message key="page.moneyprovision.action.applyfilter"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </html:form>


    <div class="table-responsive spacer">

        <h4><smg:fmt value="page.moneyprovision.label.seriesofcashflows"/></h4>

        <display:table name="${moneyProvisionForm.seriesOfCashFlows}"
                       defaultsort="${moneyProvisionForm.seriesDefaultSortColumn}" defaultorder="ascending"
                       decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.TotalTableDecorator">


            <display:column property="objectReference.objectId" titleKey="label.id"/>
            <display:column property="objectReference" titleKey="ftx.moneyprovisionelement.type"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.ObjectReferenceTypeNameColumnDecorator"/>
            <display:column property="targetFinancialTransactionType" titleKey="ftx.moneyprovisionelement.targetfinancialtransactiontype"/>

            <c:choose>
                <c:when test="${moneyProvisionForm.groupBy == 'FREQUENCY'}">
                    <display:column property="frequency" titleKey="ftx.moneyprovisionelement.frequency"
                                    group="1"/>
                </c:when>
                <c:otherwise>
                    <display:column property="frequency" titleKey="ftx.moneyprovisionelement.frequency"/>
                </c:otherwise>
            </c:choose>

            <display:column property="startDate" titleKey="ftx.moneyprovisionelement.startdate"/>
            <display:column property="endDate" titleKey="ftx.moneyprovisionelement.enddate"/>
            <display:column property="advanceArrearsIndicator"
                            titleKey="ftx.moneyprovisionelement.advancearrears"/>
            <display:column property="anniversaryDate"
                            titleKey="ftx.moneyprovisionelement.anniversarydate"/>
            <display:column property="runDate" titleKey="ftx.moneyprovisionelement.rundate"
                    />

            <c:choose>
                <c:when test="${moneyProvisionForm.groupBy == 'DUEDATE'}">
                    <display:column property="nextDueDate"
                                    titleKey="ftx.moneyprovisionelement.nextduedate"
                                    group="1"/>
                </c:when>
                <c:otherwise>
                    <display:column property="nextDueDate" titleKey="ftx.moneyprovisionelement.nextduedate"/>
                </c:otherwise>
            </c:choose>

            <display:column property="currencyCode" titleKey="label.currencycode"/>
            <display:column property="baseAmount" class="monetary total"
                            titleKey="ftx.moneyprovisionelement.amount" total="${moneyProvisionForm.showTotals}"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.BigDecimalColumnDecorator"/>

        </display:table>
    </div>

    <div class="table-responsive spacer">

        <h4><smg:fmt value="page.moneyprovision.label.singlecashflows"/></h4>

        <display:table name="${moneyProvisionForm.singleCashFlows}"
                       defaultsort="${moneyProvisionForm.singleDefaultSortColumn}" defaultorder="ascending"
                       decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.TotalTableDecorator">

            <display:column property="objectReference.objectId" titleKey="label.id"/>
            <display:column property="objectReference" titleKey="ftx.moneyprovisionelement.type"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.ObjectReferenceTypeNameColumnDecorator"/>
            <display:column property="targetFinancialTransactionType" titleKey="ftx.moneyprovisionelement.targetfinancialtransactiontype"/>

            <display:column property="runDate" titleKey="ftx.moneyprovisionelement.rundate"
                    />

            <c:choose>
                <c:when test="${moneyProvisionForm.groupBy == 'DUEDATE'}">
                    <display:column property="dueDate" titleKey="ftx.moneyprovisionelement.nextduedate"
                                    group="1"/>
                </c:when>
                <c:otherwise>
                    <display:column property="dueDate" titleKey="ftx.moneyprovisionelement.nextduedate"
                            />
                </c:otherwise>
            </c:choose>


            <display:column property="currencyCode" titleKey="label.currencycode"/>
            <display:column property="baseAmount" class="monetary total"
                            titleKey="ftx.moneyprovisionelement.amount" total="${moneyProvisionForm.showTotals}"
                            decorator="com.silvermoongroup.fsa.web.common.displaytag.decorator.BigDecimalColumnDecorator"/>

        </display:table>
    </div>

</div>

<% if (!CallBackUtility.isCallBackEmpty(request, response)) { %>
<div class="row">
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