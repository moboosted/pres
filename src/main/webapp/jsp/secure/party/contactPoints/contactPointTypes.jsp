<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<c:if test="${! empty partyForm.prefsPointsMap}">
<div>
	<table class="party-table" id="contactPointTypes" style="display:none;width:70%;">
        <tr>
            <td class="lightgrey" style="color:#000066;width:10%;height:27px;">
            <label><fmt:message key="page.party.contactpoints.label.contactpointtype"/></label>
            </td>
            <td class="lightgrey" style="width:25%">
                <html:select styleId="selContactPointTypeId" property="selContactPointTypeId" styleClass="form-control input-sm"
                             onchange="showContactPointTypeAdd(this.form);">
                    <html:option value=""><smg:fmt value="page.party.registrations.label.select" /></html:option>
                    <smg:contactPointTypes/>
                </html:select>
            </td>
        </tr>
	</table>
</div>
</c:if> 