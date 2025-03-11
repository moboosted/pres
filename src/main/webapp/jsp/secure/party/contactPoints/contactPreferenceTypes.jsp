<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<%@include file="../partyTypes/partyHeader.jsp" %>

<div>
    <table class="party-table" style="width:60%">
        <tr>
            <td class="mandatory" style="width:25%">
                <label><fmt:message key="page.party.contactpoints.label.contactpreftypes"/></label>
            </td>
            <td class="lightgrey" style="width:30%">
                <html:select styleId="selContactPreferenceTypeId" styleClass="form-control input-sm" property="selContactPreferenceTypeId" onchange="showContactPreferenceTypeAdd(this.form);">
                    <html:option value=""><smg:fmt value="page.party.registrations.label.select" /></html:option>
                    <smg:types subTypesOf="Contact Preference" excludedTypes="Banking"/>
                </html:select>
            </td>
        </tr>
        <tr>
            <td class="lightgrey" style="color:#000066;width:25%">
                <label><fmt:message key="page.party.contactpoints.label.contactdetailsfor"/></label>
            </td>
            <td class="lightgrey" style="width:30%">
                <html:select styleId="roleTypeForCpointsLoad" styleClass="form-control input-sm" property="selRoleTypeForCpointsLoad"
                onchange="var actn=document.forms[0].action;actn=actn+'?method=.load';document.forms[0].action=actn;document.forms[0].submit();">
                    <smg:generalOptionsCollection name="partyForm" property="rolesForCpsList" label="label"/>
                </html:select>
            </td>
        </tr>
    </table>
</div>