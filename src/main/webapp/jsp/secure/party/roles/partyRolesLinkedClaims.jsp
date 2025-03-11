<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<table class="party-table" width="100%">
    <logic:present name="partyForm" property="partyRoleClaimsMap">
        <tr id="row35">
            <td>
                <div style="display: block;">
                    <table cellpadding="6px">
                        <tr>
                            <td class="agreementExpand" width="18px">
                            </td>
                            <td class="lightgrey" style="color:#000066;"><label id="lblRolesAgreements"><fmt:message key="page.party.partyroles.label.partyroleslinkedtoclaims" /></label></td>
                        </tr>
                    </table>
                </div>
                <div id="obj35">
                    <table id="tblRoles" class="party-table" cellpadding="3px">
                        <tr>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.roleplayed" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.claim" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.externalreference" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.agreementkind" /></label>
                            </td>
                        </tr>
                        <c:forEach var="roles" items="${partyForm.partyRoleClaimsMap}" varStatus="idx">
                            <c:set var="agreementNo" value="${roles.key.agreementNumber}" scope="session" />
                            <tr>
                                <c:url value="secure/claim/claimnavigator.do" var="viewPartyRoleURL">
                                    <c:param name="nodeObjectReference" value="${roles.key.agreementPartObjectReference}"/>
                                </c:url>

                                <td class="title">
                                    <c:out value="${roles.key.partyRole}"/>
                                </td>
                                <td class="title">
                                    <html:link action="${viewPartyRoleURL}">
                                        <c:out value="${roles.key.agreementPart}" />
                                    </html:link>
                                </td>
                                <td class="title">
                                    <html:link action="${viewPartyRoleURL}">
                                        <c:out value="${roles.key.agreementNumber}" />
                                    </html:link>
                                </td>
                                <td class="title">
                                    <c:out value="${roles.key.agreementKind}"/>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </td>
        </tr>

        <tr><td>&nbsp;</td></tr>
    </logic:present>
</table>