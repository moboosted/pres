<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<div id="personSearch">
<c:set var="personPartialList" value="${findPartyForm.personPartialList}" scope="page"/>
<c:set var="personListSize" value="${findPartyForm.personSearchResultSize}" scope="page"/>
    <c:if test="${!empty findPartyForm.personPartialList}">
        <table class="structure">
            <tr>
                <td style="width:15%;vertical-align:bottom">
                    <label class="groupHeading" style="align:bottom"><fmt:message key="page.party.searchresults.label.personsearchresults" /></label>
                </td>
            </tr>
        </table>
        <display:table
            id="personTableId"
            name="pageScope.personPartialList"
            defaultsort="1"
            defaultorder="ascending"
            pagesize="20"
            sort="external"
            partialList="true"
            size="pageScope.personListSize"
            excludedParams="contactId txtLandLineAreaCode branchName professionalMemberRole contactForRole txtEmail branchCode txtEndDateContactPref bankContactPreference txtPhysicalAddressCity txtEducationIssueDate txtKnownAs txtLandLineLocalNumber txtBDRegisteredName txtStartDatePhysicalAddress txtNRIssueDate txtMaritalStatusDate effectiveToDate contactId txtInitials txtPhysicalAddressSubRegion bank addContactPressed selPostalAddressCountryName txtFullName2 txtLandLineCountryCode txtDateOfDeathRegisteringAuthority txtPostalAddressRegion nameEndDate selEmploymentStatus txtStartDateEmail txtPostalAddressSubRegion txtFloorNumber txtIdentityNumber txtPostnetSuite txtEducationRegistrationNumber differentRoleSelected storePref selTitle txtStartDateContactPref selLanguage name branchReference txtEndDatePostalAddress selGender storeStoreHome txtEndDateEmail mode selAccreditationType txtUnitNumber txtBoxNumber txtStartDatePostalAddress storeStoreBus selEducationLevel txtMRRegistrationNumber partyRolesSize txtAccreditationRegistrationStartDate selPreferredLanguage txtDisposableIncome txtDateOfDeathRegistrationNumber txtEndDatePhysicalAddress selEthnicity txtPostalAddressCity accountNumber txtAccreditationRegistrationEndDate accountHolder belongsToRolePassed txtDateOfDeathIssueDate txtFullName2Disabled selContactPreferenceTypeId txtEducationRegisteringAuthority txtFullName4 txtFirstName hiddenMethod txtGrossIncome txtNRRegisteringAuthority txtPostalAddressPostalCode txtMiddleName txtBDRegisteringAuthority selNameType txtPhysicalAddressRegion txtBDIssueDate editCPType txtStreet readOnly txtBirthDate txtEndDateNumber editMode defContPrefName accountType editContactPressed tabToChangeTo effectiveFromDate selPhysicalAddressCountryName changeBackToPartOf txtMRIssueDate selMaritalStatus txtSurname txtMRRegisteringAuthority txtDateOfDeath selRoleType selPartyType nameStartDate txtOrgFullName key txtStartDateNumber txtBuildingName changeBranch txtPhysicalAddressPostalCode txtHouseNumber selUnstructuredAddressCountryName txtAddressLine1 txtAddressLine2 txtAddressLine3 txtAddressLine4 txtUnstructuredAddressCity txtUnstructuredAddressSubRegion txtUnstructuredSubRegion txtUnstructuredAddressRegion txtUnstructuredAddressPostalCode unstructuredCodeStore txtStartDateUnstructuredAddress txtEndDateUnstructuredAddress method"
            >

           <c:choose>
               <c:when test="${findPartyForm.partyIsReadOnly eq 'true'}">
                   <display:column property="surname" titleKey="page.party.label.surname"/>
               </c:when>
               <c:otherwise>
	               <c:url value="/secure/partyAction.do?method=.selectPartySearchResult" var="viewSearchResultURL" >
	                   <c:param name="embeddedMode" value="${findPartyForm.embeddedMode}" />               
                       <c:param name="roleKindToLink" value="${findPartyForm.roleKindToLink}" />
                       <c:if test="${findPartyForm.isDelegating}">
                           <c:param name="selectedPartyObjectRef" value="${personTableId.objectReference}"/>
                       </c:if>
	               </c:url>
                   <c:if test="${! findPartyForm.isDelegating}">
                       <display:column property="surname" titleKey="page.party.label.surname" headerClass="order1" href="${viewSearchResultURL}" paramId="selectedPartyObjectRef" paramProperty="objectReference"/>
                   </c:if>
                   <c:if test="${findPartyForm.isDelegating}">
                       <display:column property="surname" titleKey="page.party.label.surname" headerClass="order1" href="${viewSearchResultURL}" paramId="externalReference" paramProperty="externalReference"/>
                   </c:if>
               </c:otherwise>
           </c:choose>
           <c:choose>
               <c:when test="${findPartyForm.partyIsReadOnly eq 'true'}">
                    <display:column property="firstname" titleKey="page.party.label.firstname"/>
                </c:when>
                <c:otherwise>
                    <c:url value="/secure/partyAction.do?method=.selectPartySearchResult" var="viewSearchResultURL">
                        <c:param name="embeddedMode" value="${findPartyForm.embeddedMode}"/>
                        <c:param name="roleKindToLink" value="${findPartyForm.roleKindToLink}"/>
                        <c:if test="${findPartyForm.isDelegating}">
                            <c:param name="selectedPartyObjectRef" value="${personTableId.objectReference}"/>
                        </c:if>
                    </c:url>
                    <c:if test="${! findPartyForm.isDelegating}">
                        <display:column property="firstname" titleKey="page.party.label.firstname" headerClass="order1" href="${viewSearchResultURL}" paramId="selectedPartyObjectRef" paramProperty="objectReference"/>
                    </c:if>
                    <c:if test="${findPartyForm.isDelegating}">
                        <display:column property="firstname" titleKey="page.party.label.firstname" headerClass="order1" href="${viewSearchResultURL}" paramId="externalReference" paramProperty="externalReference"/>
                    </c:if>
                </c:otherwise>
            </c:choose>
           <c:choose>
               <c:when test="${findPartyForm.partyIsReadOnly eq 'true'}">
                    <display:column property="middlename" titleKey="page.party.label.middlename"/>
                </c:when>
                <c:otherwise>
                    <display:column property="middlename" titleKey="page.party.label.middlename" headerClass="order1"/>
                </c:otherwise>
            </c:choose>
            <display:column property="initials" titleKey="page.party.label.initials"/>
            <display:column property="title" titleKey="page.party.label.title"/>
            <display:column property="birthDate" titleKey="page.party.label.birthdate" headerClass="order1"/>
            <display:column property="street" titleKey="page.party.label.street" headerClass="order1"/>
            <display:column property="streetNumber" titleKey="page.party.label.housenumber"/>
            <display:column property="physicalAddressCity" titleKey="page.party.label.city" headerClass="order1"/>
            <display:column property="physicalAddressPostalCode" titleKey="page.party.label.postalcode"/>
        </display:table>
    </c:if>
</div>