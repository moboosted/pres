<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<table class="party-table" width="100%">
    <logic:present name="partyForm" property="partyRoleAgmtsMap">
        <tr id="row35">
            <td>    
                <div style="display: block;">
                    <table cellpadding="6px">
                        <tr>                    
                            <td class="agreementExpand" width="18px">
                            </td>
                            <td class="lightgrey" style="color:#000066;"><label id="lblRolesAgreements"><fmt:message key="page.party.partyroles.label.partyroleslinkedtoagreements" /></label></td>
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
                                <label><fmt:message key="page.party.partyroles.label.agreementpart" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.agreementnumber" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.agreementkind" /></label>
                            </td>
                            <td class="lightgrey" style="color:#000066;">
                                <label><fmt:message key="page.party.partyroles.label.agreementhistory" /></label>
                            </td>
                        </tr>   
                        <c:forEach var="roles" items="${partyForm.partyRoleAgmtsMap}" varStatus="idx">
                            <c:set var="agreementNo" value="${roles.key.agreementNumber}" scope="session" />
                            <tr>
                                <td class="title">
                                    <c:out value="${roles.key.partyRole}"/>
                                </td>
                                <td class="title">
                                	<c:url value="secure/partyAction.do?method=.viewAgreementPartForPartyRole" var="viewPartyRoleAgreementPartURL">
									   <c:param name="tlaContextObjectReference" value="${roles.key.agreementPartContext}"/>
									   <c:param name="rolePlayerObjectRef" value="${roles.key.partyRoleObjectReference}"/>
									   <c:param name="anchorObjectReference" value="${roles.key.partyRoleContext}"/>
									</c:url>
                                    <html:link action="${viewPartyRoleAgreementPartURL}">
                                        <c:out value="${roles.key.agreementPart}" />
                                    </html:link>
							    </td>                   
                                <td class="title">                                  
                                    <html:link action="/secure/agreement/find.do" paramId="externalReference" paramName="agreementNo">
                                        <c:out value="${roles.key.agreementNumber}" />
                                    </html:link>                                                                                                                    
                                </td>                                   
                                <td class="title">
                                    <c:out value="${roles.key.agreementKind}"/>
                                </td>
                                <td class="title">
   									<a href="#" class="history"
									onclick="retrieveHistory('${pageContext.request.contextPath}', '${roles.key.partyRoleContext}', '${roles.key.agreementPartContext}', '${roles.key.agreementPartObjectReference}');"><fmt:message
 											key="page.party.partyroles.label.agreementhistory" /></a>
                                </td>
                            </tr>       
                        </c:forEach>                    
                    </table>
                </div>
			</td>
        </tr>
        
        <tr id="row35_history">
            <td>    
                <div style="display: block;">
                    <table cellpadding="6px">
                        <tr>                    
                            <td class="agreementExpand" width="18px">
                            </td>
                            <td class="lightgrey" style="color:#000066;"><label><fmt:message key="page.party.partyroles.label.partyrolesversionagreementshistory" /></label><a href="#" style="text-align: right; float:right;" title="Close" class="close">[Close X]</a></td>
                        </tr>
                    </table>
                </div>   
                <div id="obj35_history">
                    <table class="table table-striped" id="tblHistory" cellpadding="3px">
						<tr id="historyHeader">
							<td class="lightgrey" style="color: #000066;"><fmt:message key="page.history.label.agreementversion" /></td>
							<td class="lightgrey c" style="color: #000066;"><fmt:message key="page.history.label.effectivedate" /></td>
							<td class="lightgrey c" style="color: #000066;"><fmt:message key="page.history.label.creationdate" /></td>
							<td class="lightgrey c" style="color: #000066;"><fmt:message key="page.history.label.status" /></td>
							<td class="lightgrey c" style="color: #000066;"><fmt:message key="page.history.label.legallybinding" /></td>
							<td class="lightgrey" style="color: #000066;"><fmt:message key="page.history.label.agreementpart" /></td>
						</tr>
					</table>
                </div>
			</td>
        </tr>
    </logic:present>
</table>
<br>
<hr>