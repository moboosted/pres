<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<!-- Telephone Display -->
<table class="structure" style="width:90%;">
    <tr>
        <td colspan="4">             
            <div id="obj${count}">
                <div style="display: block;" id="telephones">
                    <table class="party-table" cellpadding="3px" style="width:100%;">
                        <tr>
                            <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.contactpoints.label.countrycode"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.areacode"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:20%"><b><fmt:message key="page.party.contactpoints.label.number"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:15%"><b><fmt:message key="page.party.contactpoints.label.extension"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.startdate"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.enddate"/></b></td>
                            <td class="lightgrey" style="color:#000066;width:10%"><b><fmt:message key="page.party.contactpoints.label.phonetype"/></b></td>
                            <td class="lightgrey" style="width:2%"><b></b></td>                                    
                        </tr>
                        <logic:iterate id="tels" name="telContactPoints">                   
                            <tr id="<c:out value="${tels.value.id}"/>">                                                 
                                <td class="title"><c:out value="${tels.value.countryPhoneCode}"/></td>
                                <td class="title"><c:out value="${tels.value.areaCode}"/></td>
                                <td class="title"><c:out value="${tels.value.localNumber}"/></td>
                                <td class="title"><c:out value="${tels.value.extension}"/></td>
                                <td class="title" style="white-space:nowrap;"><smg:fmt value="${tels.value.effectivePeriod.start}" /></td>
                                <td class="title" style="white-space:nowrap;"><smg:fmt value="${tels.value.effectivePeriod.end}" /></td>
                                <td class="title" style="white-space:nowrap;"><smg:fmt value="${tels.value.electronicType}"/></td>
                                <c:if test="${! partyForm.isDelegating}">
                                    <td class="contactSelection">
                                        <input type="radio" name="contactSelected"
                                            value="${tels.value.id}" onclick="loadContactToEdit(this.form);"/>
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