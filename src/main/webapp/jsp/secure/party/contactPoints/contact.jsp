<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>


<script type="text/javascript">
<!--
var contactPreferencesJson = <smg:contactPreferenceAndPointJsonTag contactPreferenceMap="${partyForm.prefsPointsMap}" />

$(function() {
	initialiseContactPage();	
});


//-->
</script>
<c:if test="${! partyForm.isDelegating}">
    <%@include file="contactPreferenceTypes.jsp"%>
    <%@include file="contactPreferenceCapture.jsp"%>
</c:if>
<%@include file="contactPointTypes.jsp"%>
<%@include file="contactPointsCapture.jsp"%>
<%@include file="contactPointsDisplay.jsp"%>

<br/>
<table> 
    <tr>
        <td colspan="4" align="center">
            <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
            <html:submit styleId="contactBackButton" property="method" styleClass="btn btn-link btn-sm" >
                <fmt:message key="button.cancel"/>
            </html:submit>
            <% } %>                         
        </td>
    </tr>
</table>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/contact.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/expandCollapse.js"></script>   