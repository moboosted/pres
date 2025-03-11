<%@page import="com.silvermoongroup.common.enumeration.EnumerationType"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://silvermoongroup.com/tags" prefix="smg"%>

<!-- General -->
<div style="display: none;" id="partyReg">
    <table class = "party-table">
        <tr>
            <td class="title" style="color:#000066;"><label id="lblPartyRegistrationDescription"><fmt:message key="page.party.registrations.label.description" /></label></td>
            <td class="title"><html:text property="txtPartyRegistrationDescription" styleClass="form-control input-sm"/></td>
            <td class="title" style="color:#000066;"><label id="lblPartyRegistrationIssueDate" styleClass="form-control input-sm"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
            <td class="title"><html:text property="txtPartyRegistrationIssueDate" styleClass="form-control input-sm datefield"/></td>
        </tr>
        <tr>
            <td class="title" style="color:#000066;"><label id="lbpartyRegistrationStartDate"><fmt:message key="page.party.registrations.label.startdate" /></label></td>
            <td class="title"><html:text property="txtPartyRegistrationStartDate" styleClass="form-control input-sm datefield"/></td>
            <td class="title" style="color:#000066;"><label id="lblPartyRegistrationEndDate"><fmt:message key="page.party.registrations.label.enddate" /></label></td>
            <td class="title"><html:text property="txtPartyRegistrationEndDate" styleClass="form-control input-sm datefield"/></td>
        </tr>
    </table>
</div>

<!-- Accreditation -->
<div style="display: none;" id="accredReg"> 
    <table class="party-table"> 
        <tr>
            <td class="title" style="color:#000066;"><label id="lblAccreditationSelType"><fmt:message key="page.party.registrations.label.accreditationtype" /></label></td>
            <td class="title">                          
               <html:select styleId="selAccreditationTypeId" styleClass="form-control input-sm" property="selAccreditationTypeId">
                    <html:option value=""><smg:fmt value="page.party.registrations.label.select" /></html:option>
                    <smg:generalOptionsCollection property="accreditationOptions" />
                </html:select>
            </td>
            <td class="title" style="color:#000066;"><label id="lblAccreditationRegistrationIssueDate"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
            <td class="title"><html:text property="txtAccreditationRegistrationIssueDate" styleClass="form-control input-sm datefield"/></td>                                                                                     
        </tr>
        <tr>
            <td class="title" style="color:#000066;"><label id="lblAccreditationRegistrationStartDate"><fmt:message key="page.party.registrations.label.startdate" /></label></td>
            <td class="title"><html:text property="txtAccreditationRegistrationStartDate" styleClass="form-control input-sm datefield"/></td>
            <td class="title" style="color:#000066;"><label id="lblAccreditationRegistrationEndDate"><fmt:message key="page.party.registrations.label.enddate" /></label></td>
            <td class="title"><html:text property="txtAccreditationRegistrationEndDate" styleClass="form-control input-sm datefield"/></td>
        </tr>
    </table>
</div>

<!-- Birth -->
<div style="display: none;" id="birthReg">
    <table class = "party-table">
        <tr>
            <td class="title" style="color:#000066;"><label id="lblBDRegisteredBirthDate"><fmt:message key="page.party.registrations.label.birthdate" /></label></td>
            <td class="input"><html:text property="txtBirthDate" styleClass="form-control input-sm datefield" /></td>
            <td class="title" style="color:#000066;"><label id="lblBDRegisteringAuthority"><fmt:message key="page.party.registrations.label.registeringauthority" /></label></td>
            <td class="input"><html:text property="txtBDRegisteringAuthority" styleClass="form-control input-sm"/></td>
        </tr>
        <tr>
            <td class="title" style="color:#000066;"><label id="lblBDIssueDate"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
            <td class="input"><html:text property="txtBDIssueDate" styleClass="form-control input-sm datefield" /></td>
        </tr>
    </table>
</div>

<!-- Death -->
<div style="display: none;" id="deathReg">
	<table class = "party-table">                                         
	    <tr>
	        <td class="title" style="color:#000066;"><label id="lblDateOfDeath"><fmt:message key="page.party.registrations.label.dateofdeath" /></label></td>
	        <td class="input"><html:text property="txtDateOfDeath" styleClass="form-control input-sm datefield" /></td>
	        <td class="title" style="color:#000066;"><label id="lnlDateOfDeathRegisteringAuthority"><fmt:message key="page.party.registrations.label.registeringauthority" /></label></td>
	        <td class="input"><html:text property="txtDateOfDeathRegisteringAuthority" styleClass="form-control input-sm"/></td>
	    </tr>
	    <tr>    
	        <td class="title" style="color:#000066;"><label id="lblDateOfDeathRegistrationNumber"><fmt:message key="page.party.registrations.label.registrationnumber" /></label></td>
	        <td class="input"><html:text property="txtDateOfDeathRegistrationNumber" styleClass="form-control input-sm"/></td>     
	        <td colspan="2" class="title"></td>     
	    </tr>                   
	</table>
</div>  

<!-- DriversLicence -->
<div style="display: none;" id="driversLicenceReg">
    <table class = "party-table">                                             
        <tr>
            <td class="title" style="color:#000066;"><label id="lblDriversLicenceExtRef"><fmt:message key="page.party.registrations.label.externalreference" /></label></td>
            <td class="title"><html:text property="txtDriversLicenceExternalReference" styleClass="form-control input-sm"/></td>
            <td class="title" style="color:#000066;"><label id="lblDriversLicenceIssueDate"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
            <td class="title"><html:text property="txtDriversLicenceIssueDate" styleClass="form-control input-sm datefield" /></td>
        </tr>               
        <tr>        
            <td class="title" style="color:#000066;"><label id="lblDriversLicenceRenewalDate"><fmt:message key="page.party.registrations.label.renewaldate" /></label></td>
            <td class="title"><html:text property="txtDriversLicenceRenewalDate" styleClass="form-control input-sm datefield" /></td>  
            <td class="title" style="color:#000066;"><label id="lblDriversEndDate"><fmt:message key="page.party.registrations.label.enddate" /></label></td>
            <td class="title"><html:text property="txtDriversLicenceEndDate" styleClass="form-control input-sm datefield" /></td>
        </tr>
    </table>
</div>

<!-- Education -->  
<div style="display: none;" id="educationReg">
<table class = "party-table">                                             
    <tr>
        <td class="title" style="color:#000066;"><label id="lblEducationIssueDate"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
        <td class="title"><html:text property="txtEducationIssueDate" styleClass="form-control input-sm datefield" /></td>
        <td class="title" style="color:#000066;"><label id="lblEducationRegisteringAuthority"><fmt:message key="page.party.registrations.label.registeringauthority" /></label></td>
        <td class="title"><html:text property="txtEducationRegisteringAuthority" styleClass="form-control input-sm"/></td>
    </tr>
    <tr>
        <td class="title" style="color:#000066;"><label id="lblEducationRegistrationNumber"><fmt:message key="page.party.registrations.label.registrationnumber" /></label></td>
        <td colspan="3" class="title"><html:text property="txtEducationRegistrationNumber" styleClass="form-control input-sm"/></td>           
    </tr>
</table>
</div>

<!-- Marriage -->
<div style="display: none;" id="marriageReg">
    <table class = "party-table">                                             
        <tr>
            <td class="title" style="color:#000066;"><label id="lblMaritalStatusRegistrationNumber"><fmt:message key="page.party.registrations.label.registrationnumber" /></label></td>
            <td class="input"><html:text property="txtMRRegistrationNumber" styleClass="form-control input-sm"/></td>
            <td class="title" style="color:#000066;"><label id="lblMaritalStatusRegisteringAuthority"><fmt:message key="page.party.registrations.label.registeringauthority" /></label></td>
            <td class="input"><html:text property="txtMRRegisteringAuthority" styleClass="form-control input-sm"/></td>
        </tr>
        <tr>
            <td class="title" style="color:#000066;"><label id="lblMaritalStartDate"><fmt:message key="page.party.registrations.label.startdate" /></label></td>
            <td class="input"><html:text property="txtMaritalStartDate" styleClass="form-control input-sm datefield" /></td>
            <td class="title" style="color:#000066;"><label id="lblMaritalEndDate"><fmt:message key="page.party.registrations.label.maritalenddate" /></label></td>            
            <td class="input"><html:text property="txtMaritalEndDate" styleClass="form-control input-sm datefield" /></td>               
        </tr>
    </table>
</div>

<!-- National Person -->
<div style="display: none;" id="nationalRegPerson">
    <table class = "party-table">                                             
        <tr>
            <td class="mandatory"><label id="lblNRIdentityNumber"><fmt:message key="page.party.registrations.label.number" /></label></td>
            <td class="input"><html:text property="txtIdentityNumber" styleClass="form-control input-sm" /></td>
            <td class="title" style="color:#000066;"><label id="lblNRIssueDate"><fmt:message key="page.party.registrations.label.issuedate" /></label></td>
            <td class="input"><html:text property="txtNRIssueDate" styleClass="form-control input-sm datefield" /></td>
                      
        </tr>
        <tr>
          <td class="title" style="color:#000066;"><label id="lblNRRegisteringAuthority"><fmt:message key="page.party.registrations.label.registeringauthority" /></label></td>
          <td class="input"><html:text property="txtNRRegisteringAuthority" styleClass="form-control input-sm"/></td>
          <td class="mandatory"><label id="lblCountryName"><fmt:message key="page.party.registrations.label.countryname" /></label></td>
          <td class="input">
              <html:select property="selNationalRegCountryName" styleClass="form-control input-sm" style="width:75%">
                  <smg:enumOptions enumTypeId="<%=EnumerationType.COUNTRY_CODE.getValue()%>" showTerminated = "true"/>
              </html:select>
          </td>
        </tr>
    </table>
</div>

<!-- Add Registration button -->
<div style="display: none;" id="addRegButton">
    <table class = "party-table">
        <tr>            
            <td id="addReg" colspan="4" align="center">
                <html:submit property="method" style="width:13%" styleClass="btn btn-primary btn-sm"
                    onfocus="mandatories()">
                    <fmt:message key="page.party.registrations.action.addregistration"/> 
                </html:submit>                      
            </td>
        </tr>
    </table>
</div>

<!-- Edit Registration button -->
<div style="display: none;" id="updateRegButton">
    <table class = "party-table">
        <tr>            
            <td id="editRegButton" colspan="4" align="center">
                <html:submit property="method" style="width:13%" styleClass="btn btn-primary btn-sm"
                    onfocus="mandatories()">
                    <fmt:message key="page.party.registrations.action.updateregistration"/> 
                </html:submit> 
                <html:button property="method" styleClass="btn btn-primary btn-sm"  onclick="cancelRegistrationEdit()">
                  <fmt:message key="page.party.registrations.action.cancelupdate"/>
                </html:button>                     
            </td>
        </tr>
    </table>
</div>
<div class="sectionDivide"></div>
