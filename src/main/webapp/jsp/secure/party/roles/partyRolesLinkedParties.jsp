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
                                    <fmt:message key="page.party.partyroles.label.partyroleslinkedtoparties" />
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
                                <label><fmt:message key="page.party.partyroles.label.partyType" /> </label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.partyName" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.description" /></label>
                            </td>
                        </tr>   
                        <c:forEach var="roles" items="${partyForm.partyRolePartiesMap}" varStatus="idx">
                            <tr>
                                <td class="title">
                                    <c:out value="${roles.key.partyRole}"/>
                                </td>
                                <td>
                                    <c:out value="${roles.key.contextTypeName}"/>
                                </td>
                                <td class="title">
                                	<c:out value="${roles.key.contextPartyName}"/>
							    </td>
                                <td class="title">
                                    <c:out value="${roles.key.roleType}"/>
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
<hr>