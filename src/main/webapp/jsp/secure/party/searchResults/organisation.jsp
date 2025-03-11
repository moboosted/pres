<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div id="organisationSearch">
	<c:set var="organisationList" value="${findPartyForm.organisationList}" scope="page"/>
	<c:if test="${!empty findPartyForm.organisationList}">
        <table class="structure">
            <tr>
                <td style="width:15%;vertical-align: bottom">
                    <label class="groupHeading" style="align:bottom"><fmt:message key="page.party.searchresults.label.organisationsearchresults" /></label>
                </td>
            </tr>
        </table>
        <display:table
            uid="organisationTableId"
            name="pageScope.organisationList"
            defaultsort="2"
            defaultorder="ascending"
            pagesize="20"
            excludedParams="contactId txtLandLineAreaCode branchName professionalMemberRole contactForRole txtEmail branchCode txtEndDateContactPref bankContactPreference txtPhysicalAddressCity txtEducationIssueDate txtKnownAs txtLandLineLocalNumber txtBDRegisteredName txtStartDatePhysicalAddress txtNRIssueDate txtMaritalStatusDate effectiveToDate contactId txtInitials txtPhysicalAddressSubRegion bank addContactPressed selPostalAddressCountryName txtFullName2 txtLandLineCountryCode txtDateOfDeathRegisteringAuthority txtPostalAddressRegion nameEndDate selEmploymentStatus txtStartDateEmail txtPostalAddressSubRegion txtFloorNumber txtIdentityNumber txtPostnetSuite txtEducationRegistrationNumber differentRoleSelected storePref selTitle txtStartDateContactPref selLanguage name branchReference txtEndDatePostalAddress selGender storeStoreHome txtEndDateEmail mode selAccreditationType txtUnitNumber txtBoxNumber txtStartDatePostalAddress storeStoreBus selEducationLevel txtMRRegistrationNumber partyRolesSize txtAccreditationRegistrationStartDate selPreferredLanguage txtDisposableIncome txtDateOfDeathRegistrationNumber txtEndDatePhysicalAddress selEthnicity txtPostalAddressCity accountNumber txtAccreditationRegistrationEndDate accountHolder txtDateOfDeathIssueDate txtFullName2Disabled selContactPreferenceTypeId txtEducationRegisteringAuthority txtFullName4 txtFirstName hiddenMethod txtGrossIncome txtNRRegisteringAuthority txtPostalAddressPostalCode txtMiddleName txtBDRegisteringAuthority selNameType txtPhysicalAddressRegion txtBDIssueDate editCPType txtStreet readOnly txtBirthDate txtEndDateNumber editMode defContPrefName accountType editContactPressed tabToChangeTo effectiveFromDate selPhysicalAddressCountryName changeBackToPartOf txtMRIssueDate selMaritalStatus txtSurname txtMRRegisteringAuthority txtDateOfDeath selRoleType selPartyType nameStartDate txtOrgFullName key txtStartDateNumber txtBuildingName changeBranch txtPhysicalAddressPostalCode txtHouseNumber method"
            >
            <c:url value="/secure/partyAction.do?method=.selectPartySearchResult" var="viewSearchResultURL">
                <c:param name="embeddedMode" value="${findPartyForm.embeddedMode}"/>
                <c:param name="roleKindToLink" value="${findPartyForm.roleKindToLink}"/>
                <c:if test="${findPartyForm.isDelegating}">
                    <c:param name="selectedPartyObjectRef" value="${organisationTableId.objectReference}"/>
                </c:if>
            </c:url>
            <c:if test="${! findPartyForm.isDelegating}">
                <display:column property="fullName" titleKey="page.party.label.organisationname" class="fontsmalllight" headerClass="order1" href="${viewSearchResultURL}" paramId="selectedPartyObjectRef" paramProperty="objectReference"/>
            </c:if>
            <c:if test="${findPartyForm.isDelegating}">
                <display:column property="fullName" titleKey="page.party.label.organisationname" class="fontsmalllight" headerClass="order1" href="${viewSearchResultURL}" paramId="externalReference" paramProperty="externalReference"/>
            </c:if>
            <display:column property="partyType" titleKey="page.party.searchresults.label.organistiontype" class="fontsmalllight" headerClass="order1"/>
        </display:table>
	</c:if>
</div>