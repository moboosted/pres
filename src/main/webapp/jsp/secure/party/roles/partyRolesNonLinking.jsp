<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="party-table" width="100%">
    <logic:present name="partyForm" property="partyRoleNonLinkingMap">
        <tr>
            <td>
                <div style="display: block;">
                    <table class="party-table" cellpadding="6px">
                        <tr>                    
                            <td class="agreementExpand" width="18px">
                                <b>                         
                                <div style="display: block;">
                                    <img id="partyRoleNonLinkImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj38',this)">
                                </div>                          
                                </b>
                            </td>                                       
                            <td class="lightgrey" style="color:#000066;"><label id="lblRolesNonLinking"><fmt:message key="page.party.partyroles.label.partyrolesdescriptive" /></label></td>
                        </tr>
                    </table>
                </div>      
                <div id="obj38">
                    <table class="party-table">
                        <tr>
                            <td class="lightgrey" style="color:#000066;width:50%"><fmt:message key="page.party.partyroles.label.party" /></td>
                            <td class="lightgrey" style="color:#000066;width:50%"><fmt:message key="page.party.partyroles.label.partyrole" /></td>               
                        </tr>
                        <logic:iterate id="rolesNonLinking" name="partyForm" property="partyRoleNonLinkingMap">                    
                            <tr>
                                <td class="title"><c:out value="${rolesNonLinking.key.party}"/></td>        
                                <td class="title"><c:out value="${rolesNonLinking.key.partyRole}"/></td>                                                            
                            </tr>
                        </logic:iterate>
                    </table>
                </div>
            </td>
        </tr>
        <tr><td>&nbsp;</td></tr>
    </logic:present>
</table> 