<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg" %>

<smg:datepicker selector=".datefield"/>

<!-- Email -->
<div style="display:none;" id="electronicAddress">
  <table class="party-table" cellpadding="3px">
    <tr>
      <td colspan="2" style="color:#000066;">
        <label>
          <b><fmt:message key="page.party.contactpoints.label.electronicaddress"/></b>
        </label>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.emailaddress"/></label>
      </td>
      <td colspan="3" class="input" onkeypress="return emailCharacter(event)">
        <html:text property="txtEmail" styleClass="form-control input-sm" style="width:25%"/>
      </td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.startdate"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="datefield form-control input-sm" property="txtStartDateEmail" style="width:50%" onchange="dateChanged(this);"
                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.enddate"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="datefield form-control input-sm" property="txtEndDateEmail" style="width:50%" onchange="dateChanged(this);"
                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
    </tr>
  </table>
</div>

<!-- Phone -->
<div style="display:none;" id="landline">
  <table class="party-table" cellpadding="3px">
    <tr>
      <td colspan="2" style="color:#000066;">
        <label><b><fmt:message key="page.party.contactpoints.label.landfaxmobile"/></b></label>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.countrycode"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtLandLineCountryCode" styleClass="form-control input-sm" style="width:30%"/>
      </td>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.areacode"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtLandLineAreaCode" styleClass="form-control input-sm" style="width:30%"/>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.number"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtLandLineLocalNumber" styleClass="form-control input-sm" style="width:50%"/>
      </td>
      <td class="title" style="color:#000066;width:20%" id="landLineExtLabel">
        <label><fmt:message key="page.party.contactpoints.label.extension"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtLandLineExt" styleClass="form-control input-sm" styleId="landLineExt" style="width:30%;"
                   onkeypress="return emailCharacter(event)"/>
      </td>
    </tr>
    <tr>
      <td class="title">
        <label><fmt:message key="page.party.contactpoints.label.telephoneType"/></label>
      </td>
      <td class="input">
        <html:select property="txtTelephoneElectronicType" styleClass="form-control input-sm" >
          <option value=""><fmt:message key="label.unknown" /></option>
          <smg:enumOptions enumTypeId="<%=EnumerationType.TELEPHONIC_ELECTRONIC_TYPE.getValue()%>"  showTerminated = "true"/>
        </html:select>
      </td>
      <td class="title" colspan="2">&nbsp;
      </td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.startdate"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="datefield form-control input-sm" property="txtStartDateNumber" style="width:50%" onchange="dateChanged(this);"
                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.enddate"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="datefield form-control input-sm" property="txtEndDateNumber" style="width:50%" onchange="dateChanged(this);"
                   onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
    </tr>
  </table>
</div>

<!-- Physical -->
<div style="display:none;" id="physicalAddress">
  <table class="party-table" align="left" cellpadding="3px">
    <tr>
      <td colspan="2" style="color:#000066;">
        <label><b><fmt:message key="page.party.contactpoints.label.physicaladdress"/></b></label>
      </td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.unitnr"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtUnitNumber" styleClass="form-control input-sm" style="width:30%"/></td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.floornr"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtFloorNumber" styleClass="form-control input-sm" style="width:30%"/></td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.buildingname"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtBuildingName" styleClass="form-control input-sm" style="width:75%"/></td>
      <td class="mandatory" style="width:20%" length=4>
        <label><fmt:message key="page.party.contactpoints.label.streetnumber"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtHouseNumber" styleClass="form-control input-sm" style="width:30%" maxlength="4"/></td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.streetname"/>*</label>
      </td>
      <td class="input" style="color:#000066;width:30%">
        <html:text property="txtStreet" styleClass="form-control input-sm" style="width:75%"/></td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.suburb"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtPhysicalAddressSubRegion" styleClass="form-control input-sm" style="width:75%"/></td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.city"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleId="physAddrCity" styleClass="form-control input-sm" size="1" property="txtPhysicalAddressCity"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.region"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text style="width:75%" styleClass="form-control input-sm" property="txtPhysicalAddressRegion"/>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.countryname"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:select size="1" property="selPhysicalAddressCountryName" styleClass="form-control input-sm" style="width:75%">
          <smg:enumOptions enumTypeId="<%=EnumerationType.COUNTRY_CODE.getValue()%>"  showTerminated = "true"/>
        </html:select>
      </td>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.postalcode"/>
        </label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" styleId="physAddrPostalCd" property="txtPhysicalAddressPostalCode"/>
      </td>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.startdate"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtStartDatePhysicalAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.enddate"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtEndDatePhysicalAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this)}"/>
      </td>
    </tr>
  </table>
</div>

<!-- Postal -->
<div style="display:none;" id="postalAddress">
  <table class="party-table" align="left" cellpadding="3px">
    <tr>
      <td colspan="2" style="color:#000066;">
        <label><b><fmt:message key="page.party.contactpoints.label.postaladdress"/></b></label>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.pobox"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtBoxNumber" styleClass="form-control input-sm" style="width:30%"/></td>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.orsuite"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtPostnetSuite" styleClass="form-control input-sm" style="width:30%"/></td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.suburb"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtPostalAddressSubRegion" styleClass="form-control input-sm" style="width:75%"/></td>
      <td colspan="2" class="title"></td>
    </tr>
    <tr>
      <td class="mandatory" size="1" multiple="false" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.city"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" style="width:75%" property="txtPostalAddressCity"/>
      </td>

      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.region"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" style="width:75%" property="txtPostalAddressRegion"/>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.countryname"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:select size="1" property="selPostalAddressCountryName" style="width:75%" styleClass="form-control input-sm">
          <smg:enumOptions enumTypeId="<%=EnumerationType.COUNTRY_CODE.getValue()%>" showTerminated = "true"/>
        </html:select>
      </td>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.postalcode"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" property="txtPostalAddressPostalCode" style="width:30%"/>
      </td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.startdate"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtStartDatePostalAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this);}"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.enddate"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtEndDatePostalAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this);}"/>
      </td>
    </tr>
  </table>
</div>

<!-- Unstructured -->
<div style="display:none;" id="unstructuredAddress">
  <table class="party-table" align="left" cellpadding="3px">
    <tr>
      <td colspan="2" style="color:#000066;">
        <label><b><fmt:message key="page.party.contactpoints.label.unstructuredaddress"/></b></label>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.addressline1"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtAddressLine1" styleClass="form-control input-sm" style="width:75%"/></td>

      <td class="title" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.addressline2"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtAddressLine2" styleClass="form-control input-sm" style="width:75%"/></td>
    </tr>
    <tr>
      <td class="title" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.addressline3"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtAddressLine3" styleClass="form-control input-sm" style="width:75%"/></td>

      <td class="title" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.addressline4"/>*</label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtAddressLine4" styleClass="form-control input-sm" style="width:75%"/></td>
    </tr>

    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtUnstructuredAddressSubRegion"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text property="txtUnstructuredAddressSubRegion" styleClass="form-control input-sm" style="width:75%"/></td>
      <td colspan="2" class="title"></td>
    </tr>
    <tr>
      <td class="mandatory" size="1" multiple="false" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtUnstructuredAddressCity"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" style="width:75%" property="txtUnstructuredAddressCity"/>
      </td>

      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtUnstructuredAddressRegion"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" style="width:75%" property="txtUnstructuredAddressRegion"/>
      </td>
    </tr>
    <tr>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.selUnstructuredAddressCountryName"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:select size="1" property="selUnstructuredAddressCountryName" style="width:75%" styleClass="form-control input-sm">
          <smg:enumOptions enumTypeId="<%=EnumerationType.COUNTRY_CODE.getValue()%>" showTerminated = "true"/>
        </html:select>
      </td>
      <td class="mandatory" style="width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtUnstructuredAddressPostalCode"/></label>
      </td>
      <td class="input" style="width:30%">
        <html:text styleClass="form-control input-sm" property="txtUnstructuredAddressPostalCode" style="width:30%"/>
      </td>
    </tr>
    <tr>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtStartDateUnstructuredAddress"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtStartDateUnstructuredAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this);}"/>
      </td>
      <td class="title" style="color:#000066;width:20%">
        <label><fmt:message key="page.party.contactpoints.label.txtEndDateUnstructuredAddress"/></label>
      </td>
      <td class="input" style="width:30%"><html:text styleClass="datefield form-control input-sm" property="txtEndDateUnstructuredAddress"
                                                     style="width:50%" onchange="dateChanged(this);"
                                                     onblur="if (dateHaschanged==true) {dateValidateInputFormat(this, this.form); validateDate(this);}"/>
      </td>
    </tr>
  </table>
</div>

<table class="party-table" width="100%" border="0">
  <tr>
    <td style="color:#000066;width:30%" align="left">
      <!--  Checkbox Default point -->
      <div style="display:none;" id="defCntPointDisp">
        <html:checkbox property="defContPoint"/>&nbsp;&nbsp;Default
      </div>
    </td>
  </tr>
  <tr>
    <td style="width:40%" align="middle">
      <!-- Button Add Contact  -->
      <div style="display:none;" id="commonButtonAdd">
        <html:submit property="method" styleClass="btn btn-primary btn-sm pull-left"
                     onfocus="validateForAddContact(this.form);">
          <fmt:message key="page.party.contactpoints.action.addContact"/>
        </html:submit>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <div style="display:none;" id="commonButtonUpdate">
        <!-- Button Edit Contact  -->
        <html:submit property="method" styleClass="btn btn-primary btn-sm pull-left"
                     onfocus="validateForUpdateContact(this.form);">
          <fmt:message key="page.party.contactpoints.action.updateContact"/>
        </html:submit>
      </div>
    </td>
  </tr>
</table>

<div class="sectionDivide"></div>


</td>
</tr>

</table>
