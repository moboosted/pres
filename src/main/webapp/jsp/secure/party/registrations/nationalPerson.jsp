<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.nationalRegPersonDisplayMap}">
<table class="party-table" style="width:60%">		
	<tr>
		<td>
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="nationalPersonRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj2',this)">	
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblBirth"><fmt:message key="page.party.registrations.label.nationalregistration" /></label></td>
					</tr>
				</table>
			</div>
			<div id="obj2">
				<div style="display: block;" id="nationalPerson">
					<table class="table table-striped">
						<c:if test="${! empty partyForm.nationalRegPersonDisplayMap}">
							<tr>	
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.type" /></b></td>			
								<td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.number" /></b></td>
								<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.registeringauthority" /></b></td>				
								<td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>
								<td class="lightgrey" style="color:#000066;width:18%"><b><fmt:message key="page.party.registrations.label.countryname" /></b></td>
								<td class="lightgrey"style="width:2%"><b></b></td>																											
							</tr>
                            <c:forEach var="nationalPersonRegs" items="${partyForm.nationalRegPersonDisplayMap}">										
								<tr id="editReg${nationalPersonRegs.key}">
									<td class="title"><smg:fmtType value="${nationalPersonRegs.value.typeId}" /></td>	
								    <td class="title"><c:out value="${nationalPersonRegs.value.externalReference}"/></td>
									<td class="title"><c:out value="${nationalPersonRegs.value.description}"/></td>	
									<td class="title"><smg:fmt value="${nationalPersonRegs.value.issueDate}" /></td>	
									<td class="title"><smg:fmt value="${nationalPersonRegs.value.countryCode}"/></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${nationalPersonRegs.key}" onclick="loadNationalRegPersonToEdit(this.form, ${nationalPersonRegs.value.typeId});"/>
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