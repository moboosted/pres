<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<smg:datepicker selector=".datefield"/>

<jsp:useBean id="findMoneySchedulersToExecuteForm" scope="request" type="com.silvermoongroup.fsa.web.moneyscheduler.form.FindMoneySchedulersToExecuteForm"/>

<html:form action="/secure/moneyscheduler/findmoneyschedulerstoexecute.do" styleId="findForm" styleClass="form-horizontal">

    <input type="hidden" name="method" value=".find"/>

    <div class="form-group">
        <label for="moneySchedulerTypeId" class="col-md-2 control-label"><fmt:message key="ftx.moneyscheduler.type"/></label>
        <div class="col-md-3">
            <html:select property="moneySchedulerTypeId" styleClass="form-control input-sm" errorStyleClass="form-control input-sm has-error" styleId="moneySchedulerTypeId">
                <smg:typeTree rootTypeId="502" />
            </html:select>
            <div class="checkbox">
                <label>
                    <html:checkbox property="restrictMoneySchedulerType"/> <fmt:message
                        key="label.restricttothistype"/>
                </label>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label for="nextRunDate" class="col-md-2 control-label"><fmt:message key="ftx.moneyscheduler.nextrundate"/></label>
        <div class="col-md-3">
            <html:text property="nextRunDate" styleClass="form-control input-sm datefield"
                       errorStyleClass="form-control input-sm datefield has-error" styleId="nextRunDate"/>
        </div>
    </div>

    <div class="form-group">
        <div class="col-md-offset-2 col-md-3">
            <html:submit accesskey="s" titleKey="label.submitwithalts" styleClass="btn btn-primary btn-sm">
                <fmt:message key="button.search"/>
            </html:submit>
            <span class="spinner"><img src='${pageContext.request.contextPath}/images/wait.gif'/></span>
        </div>
    </div>

    <div>

    </div>
</html:form>

<c:url value="/secure/moneyscheduler.do" var="moneySchedulerUrl" />
<c:url value="/secure/moneyscheduler/execute.do" var="executeMoneySchedulerUrl">
    <c:param name="executionDate" value="${findMoneySchedulersToExecuteForm.nextRunDate}" />
</c:url>

<c:if test="${findMoneySchedulersToExecuteForm.results != null}">
    <div class="table-responsive spacer">
        <display:table name="${findMoneySchedulersToExecuteForm.results}"
                       decorator="com.silvermoongroup.fsa.web.moneyscheduler.MoneySchedulersTableDecorator">

            <display:column property="id" titleKey="label.id" href="${moneySchedulerUrl}" paramId="moneySchedulerObjectReference" paramProperty="objectReference"/>
            <display:column property="type" titleKey="label.type" href="${moneySchedulerUrl}" paramId="moneySchedulerObjectReference" paramProperty="objectReference" />
            <display:column property="startDate" titleKey="ftx.moneyscheduler.startdate"/>
            <display:column property="endDate" titleKey="ftx.moneyscheduler.enddate"/>
            <display:column property="frequency" titleKey="ftx.moneyscheduler.frequency"/>
            <display:column property="anniversaryDate" titleKey="ftx.moneyscheduler.anniversarydate"/>
            <display:column property="anniversaryFixingType" titleKey="ftx.moneyscheduler.anniversarydatefixing"
                           />
            <display:column property="nextRunDate" titleKey="ftx.moneyscheduler.nextrundate"/>
            <display:column titleKey="label.action" href="${executeMoneySchedulerUrl}" paramId="moneySchedulerObjectReference" paramProperty="objectReference" class="text-info">
                <fmt:message key="page.findmoneyschedulerstoexecute.label.execute" />
            </display:column>

        </display:table>
    </div>
    <c:if test="${fn:length(findMoneySchedulersToExecuteForm.results) eq 100}">
        <div class="small">
            <fmt:message key="page.findmoneyschedulerstoexecute.label.limitedto100results"/>
        </div>
    </c:if>
</c:if>
