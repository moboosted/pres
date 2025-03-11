<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<ul>
    <c:forEach items="${structuredActualDTO.availableRequests}" var="availableRequestDTO">

        <c:url value="/secure/request.do?method=.createRequest" var="requestURL">
            <c:if test="${!empty currentPath}">
                <c:param name="path" value="${currentPath}"/>
            </c:if>
            <c:param name="context" value="${contextObjectReference}"/>
            <c:param name="requestKind" value="${availableRequestDTO.kind}"/>
        </c:url>

        <li><a href="${requestURL}" title="<smg:fmtSpecificationObjectHint value="${availableRequestDTO}"/>">
            <smg:fmtSpecificationObjectName value="${availableRequestDTO}"/>
        </a></li>

    </c:forEach>
</ul>
