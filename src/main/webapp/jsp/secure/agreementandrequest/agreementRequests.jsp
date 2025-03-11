<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<c:choose>

    <%-- These properties are only present for top level agreements--%>
    <c:when test="${smg:isTopLevelAgreement(pageContext, structuredActualDTO)}">

        <c:url value="${contextPath}.do?method=.viewRequest" var="originRequestUrl">
            <c:param name="requestObjectReference"
                     value="${structuredActualDTO.originRequest.objectReference}"/>
        </c:url>

        <div class="row">
            <label class="col-md-3">
                <span title='<fmt:message key="page.agreement.label.originrequest.hint" />' rel="tooltip">
                    <fmt:message key="page.agreement.label.originrequest"/>
                </span>
            </label>

            <div class="col-md-9">
                <a href="${originRequestUrl}"
                   title='<smg:fmtSpecificationObjectHint value="${structuredActualDTO.originRequest}"/>'
                   rel="tooltip" class="validate-props">
                    <smg:fmtSpecificationObjectName value="${structuredActualDTO.originRequest}"/>
                </a>
            </div>
        </div>

        <div class="row">
            <label class="col-md-3">
                <span title='<fmt:message key="page.agreement.label.requestinprogress.hint"/>' rel="tooltip">
                    <fmt:message key="page.agreement.label.requestinprogress"/>
                </span>
            </label>

            <div class="col-md-9">

                <c:choose>

                    <%--When there is a request in progress--%>
                    <c:when test="${!empty structuredActualDTO.requestInProgress}">

                        <c:url value="${contextPath}.do?method=.viewRequest" var="requestInProgressUrl">
                            <c:param name="requestObjectReference"
                                     value="${structuredActualDTO.requestInProgress.objectReference}"/>
                        </c:url>

                        <a title='<smg:fmtSpecificationObjectHint value="${structuredActualDTO.requestInProgress}"/>'
                           href="${requestInProgressUrl}" rel="tooltip" class="validate-props">
                            <smg:fmtSpecificationObjectName value="${structuredActualDTO.requestInProgress}"/>
                        </a>

                        <c:if test="${structuredActualDTO.requestInProgress.objectReference eq structuredActualDTO.originRequest.objectReference}">
                            &nbsp;<span class="small"><fmt:message
                                key="page.agreement.label.sameasoriginrequest"/></span>
                        </c:if>

                    </c:when>

                    <c:otherwise>
                        >No request in progress
                    </c:otherwise>


                </c:choose>


            </div>
        </div>

    </c:when>

    <%--Request in progress information for components--%>
    <c:otherwise>
        <div class="row">
            <label class="col-md-3" title='<fmt:message key="page.agreement.label.requestinprogress.hint" />'
                   rel="tooltip">
                <fmt:message key="page.agreement.label.requestinprogress"/>
            </label>

            <div class="col-md-9">
                <c:if test="${!empty structuredActualDTO.requestInProgress}">
                    <ul>
                        <c:forEach items="${structuredActualDTO.pendingRequests}" var="pendingRequestDTO">
                            <c:if test="${pendingRequestDTO.state.name == 'SAVED'}">
                                <li rel="tooltip"
                                    title='<smg:fmtSpecificationObjectHint value="${structuredActualDTO.requestInProgress}"/>'>
                                    <c:url value="${contextPath}.do?method=.viewRequest"
                                           var="requestInProgressUrl">
                                        <c:param name="requestObjectReference"
                                                 value="${pendingRequestDTO.objectReference}"/>
                                        <c:param name="contextObjectReference"
                                                 value="${contextObjectReference}"/>
                                        <c:param name="path" value="${currentPath}"/>
                                        <c:param name="kind" value="${pendingRequestDTO.kind}"/>
                                    </c:url>
                                    <a href="${requestInProgressUrl}" class="validate-props">
                                        <smg:fmtSpecificationObjectName
                                                value="${structuredActualDTO.requestInProgress}" />
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>

    </c:otherwise>
</c:choose>

<c:if test="${!empty structuredActualDTO.pendingAndExecutedRequests}">

        <div class="table-responsive">
        <table class="table table-condensed table-striped">
            <thead>
            <tr>
                <th class="lightgreybold"><fmt:message key="spf.request" /></th>
                <th class="lightgreybold"><fmt:message key="page.agreement.raisedrequests.requestedon" /></th>
                <th class="lightgreybold"><fmt:message key="label.status" /></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${structuredActualDTO.pendingAndExecutedRequests}" var="request">

                <c:url value="${contextPath}.do?method=.viewRequest" var="requestActionUrl">
                    <c:param name="requestObjectReference" value="${request.objectReference}" />
                    <c:if test="${!smg:isTopLevelAgreement(pageContext, agreementViewForm.structuredActualDTO)}">
                        <c:param name="contextObjectReference" value="${contextObjectReference}"/>
                        <c:param name="path" value="${currentPath}"/>
                        <c:param name="kind" value="${request.kind}"/>
                    </c:if>
                </c:url>

                <tr>
                    <td title="<smg:fmtSpecificationObjectHint value="${request}"/>">
                        <a href="${requestActionUrl}" class="validate-props">
                            <smg:fmtSpecificationObjectName value="${request}" />
                        </a>
                    </td>
                    <td><smg:fmt value="${request.requestedDate}" /></td>
                    <td>
                        <smg:fmt value="${request.state}" />
                        <c:if test="${! empty request.executedDate}">
                            (<smg:fmt value="${request.executedDate}" />)
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        </div>
</c:if>