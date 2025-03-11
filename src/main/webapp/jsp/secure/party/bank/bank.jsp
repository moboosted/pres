<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<c:if test="${!empty partyForm.fullName}">
    <%@include file="../partyTypes/partyHeader.jsp" %>

   	<%-- Display the list of roles/parties that share this bank detail or display the regular bank details   --%>
	<%@include file="bankDetailsDisplay.jsp" %>
    <c:if test="${! partyForm.isDelegating}">
	    <%@include file="bankDetailCapture.jsp" %>
    </c:if>

    <br></br>
    <table> 
        <tr>
            <td colspan="4" align="center">
                <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
                    <html:submit styleId="bankBackButton" property="method" styleClass="btn btn-link btn-sm">
                        <fmt:message key="button.cancel"/>
                    </html:submit>
                <% } %>
            </td>
        </tr>
    </table>
    <script type="text/javascript"src="${pageContext.request.contextPath}/js/party/contact.js"></script>
    <script type="text/javascript"src="${pageContext.request.contextPath}/js/party/bank.js"></script>
</c:if>