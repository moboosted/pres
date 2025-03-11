<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.marriageRegsDisplayMap}">
<table class="party-table" style="width:60%">
	<tr>
		<td>
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="marriageRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj3',this)">	
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblMarriage"><fmt:message key="page.party.registrations.label.marriageregistration" /></label></td>
					</tr>
				</table>
			</div>				
			<div id="obj3">
				<div style="display: block;" id="marriage">
					<table class="table table-striped">
						<c:if test="${! empty partyForm.marriageRegsDisplayMap}">
							<tr> 								
								<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.registrationnumber" /></b></td>
								<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.registeringauthority" /></b></td>
								<td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.startdate" /></b></td>
								<td class="lightgrey" style="color:#000066;width:23%"><b><fmt:message key="page.party.registrations.label.enddate" /></b></td>
								<td class="lightgrey" style="width:2%"><b></b></td>								
							</tr>
                            <c:forEach var="marriageRegs" items="${partyForm.marriageRegsDisplayMap}">
								<tr id="editReg${marriageRegs.key}">
									<td style="display:none"></td>																											
									<td class="title"><c:out value="${marriageRegs.value.externalReference}"/></td>
									<td class="title"><c:out value="${marriageRegs.value.description}"/></td>
									<td class="title"><smg:fmt value="${marriageRegs.value.effectivePeriod.start}"/></td>
									<td class="title"><smg:fmt value="${marriageRegs.value.effectivePeriod.end}" /></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${marriageRegs.key}" onclick="loadMarriageRegToEdit(this.form, ${marriageRegs.value.typeId});"/>
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