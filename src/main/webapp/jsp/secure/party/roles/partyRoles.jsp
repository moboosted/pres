<%@page import="com.silvermoongroup.fsa.web.common.callback.CallBackUtility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<script type="text/javascript">
<!--
function onPartyRelationshipRoleChange() {
	if ($("#partyRelationshipRoles").val() != '') {
		$("#selectPartyToLinkButton").removeAttr("disabled");
	}
	else {
		$("#selectPartyToLinkButton").attr("disabled", "disabled");
	}
}
//-->
</script>

<%@include file="../partyTypes/partyHeader.jsp" %>

<c:if test="${!empty partyForm.partyRoleAgmtsMap}">
    <%@include file="partyRolesLinkedAgmts.jsp"%>
</c:if>
<c:if test="${empty partyForm.partyRoleAgmtsMap}">
    <table class="party-table" >
        <tr>
            <td>
                <div style="display: block;">
                    <table>
                        <tr>
                            <td class="agreementExpand" >
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label id="lblRolesAgreements">
                                    <fmt:message key="page.party.partyroles.label.noPartyroleslinkedtoagreements" />
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <br>
    <hr>
</c:if>

<c:if test="${!empty partyForm.partyRoleLossEventsMap}">
    <%@include file="partyRolesLinkedLossEvents.jsp"%>
</c:if>

<c:if test="${!empty partyForm.partyRoleClaimsMap}">
    <%@include file="partyRolesLinkedClaims.jsp"%>
</c:if>

<c:if test="${!empty partyForm.partyRoleRelationshipsMap}">
    <%@include file="partyRolesRelationships.jsp"%>
</c:if>

<c:if test="${!empty partyForm.partyRoleNonLinkingMap}">
    <%@include file="partyRolesNonLinking.jsp"%>
</c:if>

<c:if test="${!empty partyForm.partyRolePartiesMap}">
    <%@include file="partyRolesLinkedParties.jsp"%>
</c:if>
<c:if test="${empty partyForm.partyRolePartiesMap}">
    <table class="party-table" >
        <tr>
            <td>
                <div style="display: block;">
                    <table>
                        <tr>
                            <td class="agreementExpand" >
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label id="lblRolesParties">
                                    <fmt:message key="page.party.partyroles.label.noPartyroleslinkedtoparties" />
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <br>
    <hr>
</c:if>

<c:if test="${!empty partyForm.externalPartyRolesMap}">
    <%@include file="externalPartyRoles.jsp"%>
</c:if>
<c:if test="${empty partyForm.externalPartyRolesMap}">
    <table class="party-table" >
        <tr>
            <td>
                <div style="display: block;">
                    <table>
                        <tr>
                            <td class="agreementExpand" >
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label id="lblNoExtRoles">
                                    <fmt:message key="page.party.partyroles.label.noExternalPartyRoles" />
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
    <br>
</c:if>

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
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/role.js"></script>
<script type="text/javascript"src="${pageContext.request.contextPath}/js/party/expandCollapse.js"></script>