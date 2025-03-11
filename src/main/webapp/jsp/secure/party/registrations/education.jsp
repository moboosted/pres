<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.educationRegsDisplayMap}">
<table class="party-table" style="width:60%">		
	<tr>
		<td>
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="educationRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj7',this)">	
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblEducation"><fmt:message key="page.party.registrations.label.educationregistration" /></label></td>
					</tr>
				</table>
			</div>									
			<div id="obj7">
				<div style="display: block;" id="education">
					<table class="table table-striped">
						<c:if test="${! empty partyForm.educationRegsDisplayMap}">
							<tr>				
								<td class="lightgrey" style="color:#000066;width:28%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>
								<td class="lightgrey" style="color:#000066;width:35%"><b><fmt:message key="page.party.registrations.label.registeringauthority" /></b></td>											
								<td class="lightgrey" style="color:#000066;width:35%"><b><fmt:message key="page.party.registrations.label.registrationnumber" /></b></td>
								<td class="lightgrey" style="width:2%"><b></b></td>																			
							</tr>
                            <c:forEach var="educationRegs" items="${partyForm.educationRegsDisplayMap}" >									
								<tr id="editReg${educationRegs.key}">
									<td style="display:none"></td>																											
									<td class="title"><smg:fmt value="${educationRegs.value.issueDate}"/></td>
									<td class="title"><c:out value="${educationRegs.value.description}"/></td>					
									<td class="title"><c:out value="${educationRegs.value.externalReference}"/></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${educationRegs.key}" onclick="loadEducationRegToEdit(this.form, ${educationRegs.value.typeId});"/>
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