<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.partyRegsDisplayMap}">
<table class="party-table" style="width:60%">
	<tr>
		<td>
			<div style="display: block;">
				<table cellpadding="6px">
					<tr>					
						<td class="agreementExpand" width="18px">
							<b>							
								<img id="partyRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj2',this)">
							</b>
						</td>										
						<td class="lightgrey" style="color:#000066;"><label id="lblPartyReg"><fmt:message key="page.party.registrations.label.partyregistration" /></label></td>
					</tr>
				</table>
			</div>
			<div id="obj2">
				<div style="display: block;" id="partyRegistration">
					<table class="table table-striped"> 
						<c:if test="${! empty partyForm.partyRegsDisplayMap}">
							<tr>
                                <td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.type" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.registrations.label.externalreference" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:22%"><b><fmt:message key="page.party.registrations.label.description" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:12%"><b><fmt:message key="page.party.registrations.label.startdate" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:12%"><b><fmt:message key="page.party.registrations.label.enddate" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:12%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>
                                <td class="lightgrey"style="width:2%"><b></b></td>
							</tr>
                            <c:forEach var="partyRegs" items="${partyForm.partyRegsDisplayMap}">
								<tr id="editReg${partyRegs.key}">
                                    <td class="title"><smg:fmtType value="${partyRegs.value.typeId}" /></td>
                                    <td class="title"><c:out value="${partyRegs.value.externalReference}"/></td>
									<td class="title"><c:out value="${partyRegs.value.description}"/></td>
                                    <td class="title"><smg:fmt value="${partyRegs.value.effectivePeriod.start}"/></td>
                                    <td class="title"><smg:fmt value="${partyRegs.value.effectivePeriod.end}"/></td>
									<td class="title"><smg:fmt value="${partyRegs.value.issueDate}" /></td>
									<c:if test="${! partyForm.isDelegating}">
										<td style="background-color:#6DA1CF;text-align:center;">
											<input type="radio" name="regSelected"
												value="${partyRegs.key}" onclick="loadPartyRegToEdit(this.form, ${partyRegs.value.typeId});"/>
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