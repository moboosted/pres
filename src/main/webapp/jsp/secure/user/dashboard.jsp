<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.atg.com/taglibs/json" prefix="json" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3-tip.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/d3-visualisation/barchart.js"></script>

<jsp:useBean id="dashboardForm" scope="request" type="com.silvermoongroup.fsa.web.user.DashboardForm"/>

<div class="row">

    <div class="col-md-6">

        <c:url value="/secure/request/search.do?method=.select" var="requestUrl"/>
        <c:url value="/secure/request/search.do?method=.findAndDisplay&requestStatus=EXECUTED"
               var="findRequestUrl"/>
        <c:url value="/secure/agreementversion/tla.do" var="agreementUrl">

        </c:url>

        <label class="groupHeading" for="recentlyExecutedRequestsTable"><fmt:message
                key="page.dashboard.label.recentlyexecutedrequests"/></label>

        <div class="table-responsive">
            <display:table name="${dashboardForm.recentlyExecutedRequests.results}" id="recentlyExecutedRequestsTable"
                           decorator="com.silvermoongroup.fsa.web.user.RecentlyExecutedRequestsTableDecorator">

                <display:column property="requestKind" titleKey="page.requestsearch.label.requestkind"
                                href="${requestUrl}"
                                paramId="selectedRequestObjectReference" paramProperty="requestObjectRef"
                                maxLength="25"/>
                <display:column property="executedDate" titleKey="spf.request.executeddate"/> />
                <display:column property="agreementSummary" titleKey="spf.agreement"
                                href="${agreementUrl}" paramId="contextObjectReference"
                                paramProperty="agreementObjectReference"/>
            </display:table>
        </div>

        <div class="text-right text-info">
            <a href="<c:out value="${findRequestUrl}" />"><fmt:message
                    key="page.dashboard.label.moreexecutedrequests"/></a>
        </div>
    </div>
    <div class="col-md-6">


        <c:url value="/secure/moneyscheduler.do" var="moneySchedulerExecutionUrl"/>
        <c:url value="/secure/moneyscheduler/execution/find.do" var="findMoneySchedulerExecutionsUrl"/>

        <label class="groupHeading" for="recentlyExecutedSchedulersTable"><fmt:message
                key="page.dashboard.label.recentlyexecutedschedulers"/></label>

        <div class="table-responsive">
            <display:table name="${dashboardForm.recentlyExecutedSchedulers.results}"
                           id="recentlyExecutedSchedulersTable"
                           decorator="com.silvermoongroup.fsa.web.moneyscheduler.MoneySchedulerExecutionsTableDecorator">

                <display:column property="moneyScheduler" titleKey="common.type.Money Scheduler"
                                href="${moneySchedulerExecutionUrl}" paramId="moneySchedulerObjectReference"
                                paramProperty="moneyScheduler.objectReference"/>
                <display:column property="startedAt" titleKey="ftx.moneyschedulerexecution.startedat"/>
                <display:column property="duration" titleKey="ftx.moneyschedulerexecution.duration" class="numeric"/>
                <display:column property="returnCode" titleKey="ftx.moneyschedulerexecution.returncode"/>

            </display:table>
        </div>

        <div class="text-right text-info">
            <a href="<c:out value="${findMoneySchedulerExecutionsUrl}" />"><fmt:message
                    key="page.dashboard.label.moremoneyschedulerexecutions"/></a>
        </div>

        <div id="recentRequestsChart"></div>

    </div>

</div>

<script type="text/javascript">
    var moneySchedulerExecutions =
            <json:array items="${dashboardForm.recentlyExecutedSchedulers.results}" var="e">
            <json:object>
            <json:property name="id" value="${e.id}"/>
            <json:property name="duration" value="${e.endedAt.millis - e.startedAt.millis}" />
            </json:object>
            </json:array>;

    renderBarGraph('recentRequestsChart', moneySchedulerExecutions, 0, 100);
</script>

