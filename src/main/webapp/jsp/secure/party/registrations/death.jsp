<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.deathRegDisplayMap}">
<table class="party-table" style="width:60%">
	<tr>
		<td>	
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="deathRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj4',this)">	
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblDeath"><fmt:message key="page.party.registrations.label.deathcertificate" /></label></td>
					</tr>
				</table>
			</div>			
			<div id="obj4">
				<div style="display: block;" id="death">
					<table class="table table-striped">
                        
						<c:if test="${! empty partyForm.deathRegDisplayMap}" >					
							<tr>
								<td class="lightgrey" style="color:#000066;width:28%"><b><fmt:message key="page.party.registrations.label.dateofdeath" /></b></td>
								<td class="lightgrey" style="color:#000066;width:35%"><b><fmt:message key="page.party.registrations.label.registeringauthority" /></b></td>
								<td class="lightgrey" style="color:#000066;width:35%"><b><fmt:message key="page.party.registrations.label.registrationnumber" /></b></td>
								<td class="lightgrey" style="width:2%"><b></b></td>																		
							</tr>
							<c:forEach var="deathRegs" items="${partyForm.deathRegDisplayMap}">																		 																	
								<tr id="editReg${deathRegs.key}">
									<td style="display:none"></td>
									<td class="title"><smg:fmt value="${deathRegs.value.registeredDeathDate}" /></td>
									<td class="title"><c:out value="${deathRegs.value.description}"/></td>													
									<td class="title"><c:out value="${deathRegs.value.externalReference}"/></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${deathRegs.key}" onclick="loadDeathRegToEdit(this.form, ${deathRegs.value.typeId});"/>
										</td>
									</c:if>
								</tr>					
							</c:forEach>	                                                   
						</c:if>                                   					
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</c:if>