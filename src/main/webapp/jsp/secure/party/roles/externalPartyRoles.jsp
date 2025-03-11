<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<table class="party-table" >
    <logic:present name="partyForm" property="partyRolePartiesMap">
        <tr id="row35">
            <td>
                <div style="display: block;">
                    <table>
                        <tr>
                            <td class="agreementExpand" >
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label id="lblRolesAgreements">
                                    <fmt:message key="page.party.partyroles.label.externalPartyRoles" />
                                </label>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="obj35">
                    <table id="tblRoles" class="party-table">
                        <tr>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.partyRoleType" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.externalContextReference" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.externalSystemName" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.externalType" /></label>
                            </td>
                        </tr>
                        <c:forEach var="roles" items="${partyForm.externalPartyRolesMap}" varStatus="idx">
                            <tr>
                                <td class="title">
                                    <c:out value="${roles.key.partyRole}"/>
                                </td>
                                <td class="title">
                                    <c:out value="${roles.key.externalContextReference}"/>
                                </td>
                                <td>
                                    <c:out value="${roles.key.externalSystemName}"/>
                                </td>
                                <td class="title">
                                    <c:out value="${roles.key.externalTypeName}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </td>
        </tr>
    </logic:present>
</table>
<br>