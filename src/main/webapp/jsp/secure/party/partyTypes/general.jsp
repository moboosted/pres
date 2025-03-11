<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${partyForm.selPartyType eq 'Person'}">
        <%@include file="generalPerson.jsp" %>
    </c:when>
    <c:otherwise>
        <%@include file="organisation.jsp" %>
    </c:otherwise>
</c:choose>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/party/name.js"></script>