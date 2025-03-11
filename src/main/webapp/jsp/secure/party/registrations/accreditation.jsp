<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>
<c:if test="${! empty partyForm.accreditationRegsDisplayMap}">
<table class="party-table" style="width:60%">   
    <tr>
        <td colspan="4">            
            <div style="display: block;">
                <table cellpadding="6px">
                    <tr>                    
                        <td class="agreementExpand" width="18px">
                            <b>                         
                                <img id="accredRegImg" src="${pageContext.request.contextPath}/images/icon_minus.gif" width="13" height="13" onClick="switchMenuReg('obj6',this)">    
                            </b>
                        </td>                                       
                        <td class="lightgrey" style="color:#000066;"><label id="lblAccreds"><fmt:message key="page.party.registrations.label.accreditations" /></label></td>
                    </tr>
                </table>
            </div>                                  
            <div id="obj6">
                <div style="display: block;" id="accreds">
                    <table class="table table-striped">
                      <c:if test="${!empty partyForm.accreditationRegsDisplayMap}">
                            <tr>                                        
                                <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.type" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.startdate" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:25%"><b><fmt:message key="page.party.registrations.label.enddate" /></b></td>
                                <td class="lightgrey" style="color:#000066;width:23%"><b><fmt:message key="page.party.registrations.label.issuedate" /></b></td>
                                <td class="lightgrey" style="width:2%"><b></b></td>                                             
                            </tr>   
                            <c:forEach var="accredRegs" items="${partyForm.accreditationRegsDisplayMap}"> 
                                <tr id="editReg${accredRegs.key}">
                                    <td style="display:none">${accredRegs.value.typeId}</td>                                          
                                    <td class="title"><smg:fmtType value="${accredRegs.value.typeId}"/></td>
                                    <td class="title"><smg:fmt value="${accredRegs.value.effectivePeriod.start}" /></td>
                                    <td class="title"><smg:fmt value="${accredRegs.value.effectivePeriod.end}" /></td>
                                    <td class="title"><smg:fmt value="${accredRegs.value.issueDate}" /></td>
                                    <c:if test="${! partyForm.isDelegating}">
                                        <td style="background-color:#6DA1CF;text-align:center;">
                                            <input type="radio" name="regSelected" value="${accredRegs.key}" onclick="loadAccredRegToEdit(this.form, ${accredRegs.value.typeId});"/>
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