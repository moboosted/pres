/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.party.domain.Party;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * The struts form bean backing the PartyAction
 */
public class FindPartyForm extends AbstractPartyForm {

    private static final long serialVersionUID = 1L;

    private boolean partyIsReadOnly = false;

    /**
     * The object reference of the party selected from the search results.
     */
    private String selectedPartyObjectRef;

    private String selType;
    // search person
    private String txtSearchSurname = null;
    private String txtSearchFirstName = null;
    private String txtSearchMiddleName = null;
    private String txtSearchBirthDate = null;
    private String txtSearchCity = null;
    private String txtSearchPostalCode = null;
    private String txtSearchStreet = null;
    private String txtSearchHouseNumber = null;
    // search organisation
    private String txtSearchOrgFullName = null;
    private String txtSearchOrgType = null;

    // party type
    private String selPartyType = CoreTypeReference.PERSON.getName();

    // organisation name
    private String txtOrgFullName;

    private Party party;

    // party store values end
    // =======================================================================

    public void resetPartyInfo() {
        // current party set on form
        party = null;
        setPartyObjectRef(null);
        setRoleInContext(null);

    }

    public void resetPartySearchInfo() {
        txtSearchBirthDate = null;
        txtSearchFirstName = null;
        txtSearchMiddleName = null;
        txtSearchOrgFullName = null;
        txtSearchSurname = null;
        txtSearchCity = null;
        txtSearchPostalCode = null;
        txtSearchStreet = null;
        txtSearchHouseNumber = null;
        selectedPartyObjectRef = null;
    }

    public void resetFields() {
        txtOrgFullName = null;
        selType = PartyGuiHttpConstants.FIND_PARTY_PERSON;
    }

    /**
     * Reset <em>EVERYTHING</em>. Typically called when exiting the GUI
     */
    public void resetAll() {

        resetFields();
        resetPartyInfo();
        resetPartySearchInfo();

        setPartyObjectRef(null);
        setRoleInContext(null);
        setRoleKindToLink(null);
        setEmbeddedMode(false);

        // this list is copied from PartyGuiUtility.reset(ActionMapping, ActionForm, HttpServletRequest)
        setPartyIsReadOnly(false);
        setReadOnly(false);
    }

    /**
     *
     */
    public void resetForDisplayFromExternal() {

        resetFields();
        resetPartyInfo();
        resetPartySearchInfo();
    }
    
    // getters and setters start

    public boolean isPartyIsReadOnly() {
        return partyIsReadOnly;
    }

    public void setPartyIsReadOnly(boolean partyIsReadOnly) {
        this.partyIsReadOnly = partyIsReadOnly;
    }

    public String getSelectedPartyObjectRef() {
        return selectedPartyObjectRef;
    }

    public void setSelectedPartyObjectRef(String selectedPartyObjectRef) {
        this.selectedPartyObjectRef = selectedPartyObjectRef;
    }

    public String getSelType() {
        return selType;
    }

    public void setSelType(String selType) {
        this.selType = selType;
    }

    public String getTxtSearchSurname() {
        return txtSearchSurname;
    }

    public void setTxtSearchSurname(String txtSearchSurname) {
        this.txtSearchSurname = txtSearchSurname;
    }

    public String getTxtSearchFirstName() {
        return txtSearchFirstName;
    }

    public void setTxtSearchFirstName(String txtSearchFirstName) {
        this.txtSearchFirstName = txtSearchFirstName;
    }

    public String getTxtSearchMiddleName() {
        return txtSearchMiddleName;
    }

    public void setTxtSearchMiddleName(String txtSearchMiddleName) {
        this.txtSearchMiddleName = txtSearchMiddleName;
    }

    public String getTxtSearchBirthDate() {
        return txtSearchBirthDate;
    }

    public void setTxtSearchBirthDate(String txtSearchBirthDate) {
        this.txtSearchBirthDate = txtSearchBirthDate;
    }

    public String getTxtSearchOrgFullName() {
        return txtSearchOrgFullName;
    }

    public void setTxtSearchOrgFullName(String txtSearchOrgFullName) {
        this.txtSearchOrgFullName = txtSearchOrgFullName;
    }

    public String getTxtSearchOrgType() {
        return txtSearchOrgType;
    }

    public void setTxtSearchOrgType(String txtSearchOrgType) {
        this.txtSearchOrgType = txtSearchOrgType;
    }

    public String getSelPartyType() {
        return selPartyType;
    }

    public void setSelPartyType(String selPartyType) {
        this.selPartyType = selPartyType;
    }

    public String getTxtOrgFullName() {
        return txtOrgFullName;
    }

    public void setTxtOrgFullName(String txtOrgFullName) {
        this.txtOrgFullName = txtOrgFullName;
    }

    public String getTxtSearchCity() {
        return txtSearchCity;
    }

    public void setTxtSearchCity(String txtSearchCity) {
        this.txtSearchCity = txtSearchCity;
    }

    public String getTxtSearchPostalCode() {
        return txtSearchPostalCode;
    }

    public void setTxtSearchPostalCode(String txtSearchPostalCode) {
        this.txtSearchPostalCode = txtSearchPostalCode;
    }

    public String getTxtSearchStreet() {
        return txtSearchStreet;
    }

    public void setTxtSearchStreet(String txtSearchStreet) {
        this.txtSearchStreet = txtSearchStreet;
    }

    public String getTxtSearchHouseNumber() {
        return txtSearchHouseNumber;
    }

    public void setTxtSearchHouseNumber(String txtSearchHouseNumber) {
        this.txtSearchHouseNumber = txtSearchHouseNumber;
    }

    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        if (needsValidation(actionMapping, httpServletRequest)) {

            String validateType = StringUtils.trimToNull(httpServletRequest.getParameter("validate"));

            if (validateType.equals("validateSearchOrganisation")) {
                return validateSearchOrganisation();
            } else if (validateType.equals("validateSearchPerson")) {
                return validateSearchPerson();
            }
        }
        return super.validate(actionMapping, httpServletRequest);
    }

    private ActionErrors validateSearchPerson() {

        if (GenericValidator.isBlankOrNull(getTxtSearchSurname())) {
            ActionErrors actionErrors = new ActionErrors();
            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.find.message.personsearchcriteria"));
            return actionErrors;
        }
        return null;
    }

    /**
     * Validate the Search for an organisation
     */
    private ActionErrors validateSearchOrganisation() {

        if (GenericValidator.isBlankOrNull(getTxtSearchOrgFullName())
                && GenericValidator.isBlankOrNull(getTxtSearchOrgType())) {
            ActionErrors actionErrors = new ActionErrors();
            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.find.message.personsearchcriteriarequired"));
            return actionErrors;
        }
        return null;
    }

    // ======================================================================
    // party store values getters and setters start
    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public Integer getPersonSearchResultSize() {
        if (getPersonList() == null) {
            return 0;
        }
        return getPersonList().size();
    }

    public void resetForOrganisationResultsDisplay() {

        // other search results
        resetSearchResults();
        setSelType(PartyGuiHttpConstants.FIND_PARTY_ORG);
        setPageNumber(PartyGuiHttpConstants.FIRST_PAGE);
    }

    public void resetForPersonResultsDisplay() {
        // other search results
        resetSearchResults();
        setSelType(PartyGuiHttpConstants.FIND_PARTY_PERSON);
        setPageNumber(PartyGuiHttpConstants.FIRST_PAGE);
    }

    /**
     * Reset ALL the search results
     */
    private void resetSearchResults() {
        getOrganisationList().clear();
        getPersonList().clear();
        getPersonPartialList().clear();

    }
}