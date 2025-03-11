<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
int count = 12; // for div id in contacts page - starts at position 12 as not conflict with other id in party
int emailImageCnt = 0;
int telImageCnt = 0;
int physicalImageCnt = 0;   
int postalImageCnt = 0;
int unstructuredImageCnt = 0;
%>

<c:forEach var="prefsPointsMap" items="${partyForm.prefsPointsMap}"> 

<% 
// Expose the variables to the included pages
pageContext.setAttribute("count", count, PageContext.REQUEST_SCOPE);
pageContext.setAttribute("emailImageCnt", emailImageCnt, PageContext.REQUEST_SCOPE);
pageContext.setAttribute("telImageCnt", telImageCnt, PageContext.REQUEST_SCOPE);
pageContext.setAttribute("physicalImageCnt", physicalImageCnt, PageContext.REQUEST_SCOPE);
pageContext.setAttribute("postalImageCnt", postalImageCnt, PageContext.REQUEST_SCOPE);
pageContext.setAttribute("unstructuredImageCnt", unstructuredImageCnt, PageContext.REQUEST_SCOPE);
%>
       
    <c:set var="contactPref" value="${prefsPointsMap.key}" scope="request" />
      
    <%@include file="contactPreferenceDisplay.jsp"%>
    
                        
   <c:set var="contactPointsGuiDisplayMap" value="${prefsPointsMap.value}" scope="request" />
    <c:forEach var="contactPointsGuiDisplayMap" items="${contactPointsGuiDisplayMap}">
        <c:if test="${contactPointsGuiDisplayMap.key eq 'emails'}">
            <c:if test="${!empty contactPointsGuiDisplayMap.value}">
                <c:set var="emailContactPoints" value="${contactPointsGuiDisplayMap.value}" scope="request" />
                <jsp:include page="/jsp/secure/party/contactPoints/email.jsp"/>
                <%emailImageCnt++;%>
                <%count++;%>
            </c:if>
        </c:if>
        <c:if test="${contactPointsGuiDisplayMap.key eq 'phones'}">
            <c:if test="${!empty contactPointsGuiDisplayMap.value}">
                <c:set var="telContactPoints" value="${contactPointsGuiDisplayMap.value}" scope="request" />
                <jsp:include page="/jsp/secure/party/contactPoints/telephone.jsp"/>
                <%telImageCnt++;%>
                <%count++;%>
            </c:if>
        </c:if>
        <c:if test="${contactPointsGuiDisplayMap.key eq 'physicals'}">
            <c:if test="${!empty contactPointsGuiDisplayMap.value}">
                <c:set var="physicalContactPoints" value="${contactPointsGuiDisplayMap.value}" scope="request" />
                <jsp:include page="/jsp/secure/party/contactPoints/physical.jsp"/>
                <%physicalImageCnt++;%>
                <%count++;%>
            </c:if>
        </c:if>
        <c:if test="${contactPointsGuiDisplayMap.key eq 'postals'}">
            <c:if test="${!empty contactPointsGuiDisplayMap.value}">
                <c:set var="postalContactPoints" value="${contactPointsGuiDisplayMap.value}" scope="request" />
                <jsp:include page="/jsp/secure/party/contactPoints/postal.jsp"/>
                <%postalImageCnt++;%>
                <%count++;%>
            </c:if>
        </c:if>
        <c:if test="${contactPointsGuiDisplayMap.key eq 'unstructureds'}">
            <c:if test="${!empty contactPointsGuiDisplayMap.value}">
                <c:set var="unstructuredContactPoints" value="${contactPointsGuiDisplayMap.value}" scope="request" />
                <jsp:include page="/jsp/secure/party/contactPoints/unstructured.jsp"/>
                <%unstructuredImageCnt++;%>
                <%count++;%>
            </c:if>
        </c:if>
    </c:forEach>
</c:forEach>