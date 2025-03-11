<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<!-- Physical Display  -->
<table class="structure" style="width:90%;">
    <tr>
        <td colspan="4">             
            <div id="obj${count}">
                <div style="display:block;" id="Physicals">
                    <table class="party-table" cellpadding="3px" style="width:100%;">
                        <tr>            
                            <td class="lightgrey" style="color:#000066;width:5%"><b><fmt:message key="page.party.contactpoints.label.unitno"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:5%"><b><fmt:message key="page.party.contactpoints.label.floorno"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.buildingname"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:5%"><b><fmt:message key="page.party.contactpoints.label.streetno"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.streetname"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.suburb"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:8%"><b><fmt:message key="page.party.contactpoints.label.city"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:8%"><b><fmt:message key="page.party.contactpoints.label.region"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.country"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message key="page.party.contactpoints.label.postalcode"/></b></td>                                                  
                            <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message key="page.party.contactpoints.label.startdate"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%; white-space:nowrap;"><b><fmt:message key="page.party.contactpoints.label.enddate"/></b></td>     
                            <td class="lightgrey" style="width:2%"><b></b></td>                                                                                                              
                        </tr>
                        <logic:iterate id="physicals" name="physicalContactPoints">                                                 
                            <tr id="${physicals.value.id}"> 
                            	<td style="display: none;"><smg:fmtType value="${contactPref.typeId}" /></td>

                                        <td class="title"><c:out value="${physicals.value.unitNumber}"/></td>
                                        <td class="title"><c:out value="${physicals.value.floorNumber}"/></td>
                                        <td class="title"><c:out value="${physicals.value.buildingName}"/></td>
                                        <td class="title"><c:out value="${physicals.value.houseNumber}"/></td>
                                        <td class="title"><c:out value="${physicals.value.street}"/></td>
                                        <td class="title"><c:out value="${physicals.value.subregion}"/></td>
                                        <td class="title"><c:out value="${physicals.value.city}"/></td>
                                        <td class="title"><c:out value="${physicals.value.region}"/></td>
                                        <td class="title"><smg:fmt value="${physicals.value.country}" /></td>
                                        <td class="title"><c:out value="${physicals.value.postalCode}"/></td>
                                        <td class="title" style="white-space:nowrap;" ><smg:fmt value="${physicals.value.effectivePeriod.start}" /></td>
                                        <td class="title" style="white-space:nowrap;"><smg:fmt value="${physicals.value.effectivePeriod.end}" /></td>

                                <c:if test="${! partyForm.isDelegating}">
                                    <td class="contactSelection">
                                        <input id="physContPointRadio" name="contactSelected" type="radio"
                                            value="${physicals.value.id}"
                                            onclick="loadContactToEdit(this.form);"/>
                                    </td>
                                </c:if>
                            </tr>   
                        </logic:iterate>
                    </table>
                </div>
            </div>
        </td>
    </tr>
    <tr></tr><tr></tr>
    <tr></tr><tr></tr>
</table>
