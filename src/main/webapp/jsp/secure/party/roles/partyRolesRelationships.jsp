<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="structure" class="party-table" width="100%">
    <logic:present name="partyForm" property="partyRoleRelationshipsMap">
        <tr>
            <td>
                <div style="display: block;">
                    <table class="party-table" cellpadding="6px">
                        <tr>                    
                            <td class="agreementExpand" width="18px">
                                <b>                         
                                <div style="display: block;">
                                    <img id="partyRolePartyLinkImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj36',this)">
                                </div>                          
                                </b>
                            </td>                                       
                            <td class="lightgrey" style="color:#000066;"><label id="lblRolesParties"><fmt:message key="page.party.partyroles.label.partyroleslinkedtoparties" /></label></td>
                        </tr>
                    </table>
                </div>      
                <div id="obj36">
                    <table class="party-table" cellpadding="3px">
                        <tr>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.party" /></label></td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.partyrole" /></label></td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.linksto" /></label></td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.effectivefrom" /></label></td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.effectiveto" /></label></td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.end" /></label></td>
                        </tr>
                        <logic:iterate id="rolesRelationships" name="partyForm" property="partyRoleRelationshipsMap">  
                            <tr id="${rolesRelationships.key.partyRoleId}">                                        
                                <td class="title"><c:out value="${rolesRelationships.key.party}"/></td>     
                                <td class="title"><c:out value="${rolesRelationships.key.partyRole}"/></td>                                                         
                                <td class="title"><c:out value="${rolesRelationships.value}"/></td>
                                <td class="title"><c:out value="${rolesRelationships.key.partyRoleEffectiveFrom}"/></td>    
                                <td class="title"><c:out value="${rolesRelationships.key.partyRoleEffectiveTo}"/></td>                                          
                                <td class="title">
                                    <c:if test="${empty rolesRelationships.key.partyRoleEffectiveTo}">
                                      <input type="radio" name="relationshipRoleSelected" value="<c:out value="${rolesRelationships.key.partyRoleId}"/>" onclick="relationShipRoleToEnd(this.form);"/>
                                    </c:if>                                            
                                </td>   
                            </tr>
                        </logic:iterate>
                    </table>
                </div>
            </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    </logic:present>
</table> 