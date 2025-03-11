<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.licenceRegsDisplayMap}">
<table class="party-table" style="width:60%">
	<tr>
		<td>
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="licenseRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj9',this)">	
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblLicense"><fmt:message key="page.party.registrations.label.licenseregistration" /></label></td>
					</tr>
				</table>
			</div>		
			<div id="obj9">
				<div style="display: block;" id="licence">
					<table class="table table-striped">
						<c:if test="${! empty partyForm.licenceRegsDisplayMap}">
							<tr> 	
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.type" /></b></td>																	
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.externalreference" /></b></td>
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.renewaldate" /></b></td>
								<td class="lightgrey" style="color:#000066;width:18%"><b><fmt:message key="page.party.registrations.label.enddate" /></b></td>
								<td class="lightgrey" style="width:2%"><b></b></td>																		
							</tr>
							<c:forEach var="licenceRegs" items="${partyForm.licenceRegsDisplayMap}">	
								<tr id="editReg${licenceRegs.key}">
									<td class="title"><smg:fmtType value="${licenceRegs.value.typeId}"/></td>																										
									<td class="title"><c:out value="${licenceRegs.value.externalReference}"/></td>
									<td class="title"><smg:fmt value="${licenceRegs.value.issueDate}"/></td>
									<td class="title"><smg:fmt value="${licenceRegs.value.renewalDate}"/></td>
									<td class="title"><smg:fmt value="${licenceRegs.value.effectivePeriod.end}" /></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${licenceRegs.key}" onclick="loadLicenceRegToEdit(this.form, ${licenceRegs.value.typeId});"/>
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