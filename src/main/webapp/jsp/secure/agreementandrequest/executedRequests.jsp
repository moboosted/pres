<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<c:if test="${!empty structuredActualDTO.executedRequests}">
	<table cellpadding="6px">
		<tr>
			<td class="agreementExpand" width="18px"><b><img src="<%=request.getContextPath()%>/images/icon_minus.gif" name="raisedRequests" width="13" height="13" border="0" onClick="toggleSection(this, '${pageContext.request.contextPath}')"></b></td>
			<td class="lightgrey"><fmt:message key="page.agreement.executedrequests.heading" /></td>
		</tr>
	</table>
	<div id="raisedRequests">
		<table width="100%">
	    	<tr>
				<td class="lightgreybold"><fmt:message key="page.agreement.raisedrequests.name" /></td>
				<td class="lightgreybold"><fmt:message key="page.agreement.raisedrequests.requestedon" /></td>
				<td class="lightgreybold"><fmt:message key="page.agreement.raisedrequests.status" /></td>
				<td class="lightgreybold" colspan="2" align="center"><fmt:message key="page.agreement.raisedrequests.action" /></td>
			</tr>
			<c:forEach items="${structuredActualDTO.executedRequests}" var="request">
				<tr class="odd">
					<td class="title" title="<smg:fmtSpecificationObjectHint value="${request}"/>">
                        <smg:fmtSpecificationObjectName value="${request}" /></td>
					<td class="title"><smg:fmt value="${request.requestedDate}" /></td>
					<td class="title">
                      <smg:fmt value="${request.state}" />
                      <c:if test="${! empty request.executedDate}">
                      (<smg:fmt value="${request.executedDate}" />)
                      </c:if>
                    </td>
                    <td class="title">
                      <c:url value="${contextPath}.do?method=.viewRequest" var="requestActionUrl">
                        <c:param name="requestObjectReference" value="${request.objectReference}" />
                        <c:if test="${!smg:isTopLevelAgreement(pageContext, agreementViewForm.structuredActualDTO)}">
                            <c:param name="contextObjectReference" value="${contextObjectReference}"/>
                            <c:param name="path" value="${currentPath}"/>
                            <c:param name="kind" value="${request.kind}"/>
                        </c:if>
                      </c:url>
                      <a href="${requestActionUrl}">
                        <fmt:message key="page.agreement.raisedrequests.viewrequest"/>
                      </a>
                    </td>
				</tr>
			</c:forEach>
			<tr><td colspan="4">&nbsp;</td></tr>
	  </table>
	</div>
</c:if>