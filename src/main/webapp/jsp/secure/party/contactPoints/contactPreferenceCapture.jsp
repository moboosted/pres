<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<div style="display:none;" id="contactPreference">
<hr>
    <table class="party-table" style="width:70%">
        <tr>
            <td class="mandatory">
                <label><fmt:message key="page.party.contactpoints.label.preflanguage"/></label>
            </td>
            <td class="input">
                <html:select styleId="selPreferredLanguage" styleClass="form-control input-sm" property="selPreferredLanguage" style="width:50%">
                    <smg:enumOptions enumTypeId="<%=EnumerationType.LANGUAGE.getValue()%>"  showTerminated = "true"/>
                </html:select>
            </td>
            <td class="title">
                <label><fmt:message key="page.party.contactpoints.label.default"/></label>
            </td>
            <td class="input">
                <html:checkbox property="defContPref" />
            </td>
        </tr>
        <tr>
            <td class="title">
                <label><fmt:message key="page.party.contactpoints.label.startdate"/></label>
            </td>
            <td class="input">
                <html:text styleClass="datefield form-control input-sm" styleId="txtStartDateContactPref" property="txtStartDateContactPref" onchange="dateChanged(this);" onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
            </td>
            <td class="title">
                <label><fmt:message key="page.party.contactpoints.label.enddate"/></label>
            </td>
            <td class="input">
                <html:text styleClass="datefield form-control input-sm" styleId="txtEndDateContactPref" property="txtEndDateContactPref" onchange="dateChanged(this);" onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
            </td>
        </tr>
    </table>
        <!-- Buttons Add and Edit Preference  -->
        <table class="party-table" style="width:70%">
        <tr>
            <td colspan="1" style="width:100%" align="middle">
                <div style="display:none;" id="buttonAddPref">
                    <html:submit property="method" styleClass="btn btn-primary btn-sm pull-left">
                        <fmt:message key="page.party.contactpoints.action.addPreference"/>
                    </html:submit>
                </div>
                <div style="display:none;" id="buttonUpdatePref">
                    <html:submit property="method" styleClass="btn btn-primary btn-sm pull-left">
                        <fmt:message key="page.party.contactpoints.action.updatePreference"/>
                    </html:submit>
                </div>
            </td>
        </tr>
        </table>
</div>
