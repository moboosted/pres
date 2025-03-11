<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<%@include file="../partyTypes/partyHeader.jsp" %>

<%@include file="partyRelationshipTypes.jsp" %>

<%@include file="partyRelationshipCapture.jsp" %>

<%@include file="partyRelationshipsFrom.jsp" %>

<%@include file="partyRelationshipsTo.jsp" %>

<br>
<table>
    <tr>
        <td colspan="4" align="center">
            <% if (!CallBackUtility.isCallBackEmpty(request, response)) {%>
            <html:submit styleId="rolesBackButton" property="method" styleClass="btn btn-link btn-sm" >
                <fmt:message key="button.cancel"/>
            </html:submit>
            <% } %>
        </td>
    </tr>
</table>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/party/relationships.js"></script>

