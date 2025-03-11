<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<div>
	<table>
		<tr>
			<td class="lightgrey">
	            <label><fmt:message key="page.party.registrations.label.adda" />&nbsp;&nbsp;</label>
	        </td>
	        <td>    
				<html:select styleId="regTypes" styleClass="form-control input-sm" property="selRegistrationType"  onchange="showRegistrationAdd(this.form);">
					<html:option value=""><smg:fmt value="page.party.registrations.label.select" /></html:option>
	                <smg:partyRegistrationTypes property="selPartyType" />
				</html:select>
	            <html:hidden property="selRegistrationTypeForEdit" styleId="selRegistrationTypeForEdit" />
	        </td>    
	        <td class="lightgrey">    
	            <label>&nbsp;&nbsp;<fmt:message key="page.party.registrations.label.registration" /></label>
			</td>
		</tr>
	</table>
</div>
<hr>