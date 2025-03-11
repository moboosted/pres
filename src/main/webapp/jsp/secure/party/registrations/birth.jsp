<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>
<c:if test="${! empty partyForm.birthRegDisplayMap}">
<table class="party-table" style="width:60%">		
	<tr>
		<td colspan="4">
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="birthRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj5',this)">
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblBirth"><fmt:message key="page.party.registrations.label.birthcertificate" /></label></td>
					</tr>
				</table>
			</div>			
			<div id="obj5">
				<div style="display: block;" id="birth">
					<table class="table table-striped">

    					<tr>									
    						<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.birthdate" /></b></td>
    						<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.registeringauthority" /></b></td>
    						<td class="lightgrey" style="color:#000066;width:23%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>	
    						<td class="lightgrey" style="width:2%"><b></b></td>																													
    					</tr>
    					<c:forEach var="birthRegs" items="${partyForm.birthRegDisplayMap}">										
    						<tr id="editReg${birthRegs.key}">
    							<td class="title"><smg:fmt value="${birthRegs.value.registeredBirthDate}"/></td>
    							<td class="title"><c:out value="${birthRegs.value.description}"/></td>
    							<td class="title"><smg:fmt value="${birthRegs.value.issueDate}"/></td>
								<c:if test="${! partyForm.isDelegating}">
									<td style="background-color:#6DA1CF;text-align:center;">
										<input type="radio" name="regSelected"
											value="${birthRegs.key}" onclick="loadBirthRegToEdit(this.form, ${birthRegs.value.typeId});"/>
									</td>
								</c:if>
    						</tr>					
    					</c:forEach>	                                                   
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</c:if>                                  					