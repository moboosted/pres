/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.form;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.util.PartyGuiUtility;
import com.silvermoongroup.fsa.web.party.vo.*;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * The struts form bean backing the PartyAction
 */
public class PartyForm extends AbstractPartyForm {

    private static final long serialVersionUID = 1L;

    /**
     * Indicates whether the party that we are liare working with was created during this interaction with the GUI.
     */
    private boolean newParty;

    private boolean partyIsReadOnly = false;
    // tabs
    private int tabToChangeTo;
    private boolean generalTabLoaded = false;
    private boolean relationshipsTabLoaded = false;
    private boolean rolesTabLoaded = false;
    private boolean contactTabLoaded = false;
    private boolean bankTabLoaded = false;
    private boolean regTabLoaded = false;
    private boolean genTabFirstLoad = false;
    private boolean rolesTabFirstLoad = false;
    private boolean contactTabFirstLoad = false;
    private boolean bankTabFirstLoad = false;
    private boolean regTabFirstLoad = false;
    private boolean searchTabFirstLoad = false;

    // search general -------------------------------------------

    /**
     * The object reference of the party selected from the search results.
     */
    private String selectedPartyObjectRef;

    private String selSearch;

    // duplicate search
    private boolean duplicatesFound = false;
    private boolean overRideDups = false;

    // party type
    private String selPartyType = CoreTypeReference.PERSON.getName();
    // party name
    private String fullName;

   // person name
    private String selNameType;
    private String selSuffix;
    private String selTitle;
    private String txtInitials;
    private String txtFirstName;
    private String txtKnownAs;
    private String txtMiddleName = "";
    private String txtSurname;
    private String nameStartDate;
    private String nameStartDate2;
    private String nameEndDate;
    private Long personNameSelected;
    private Long defaultPartyNameId;
    private boolean showNameButton = false;
    private boolean defaultName = false;
    private boolean defaultPartyName = false;

    // organisation name
    private String txtFullName;

    // person general
    private Long selGender;
    private String selEthnicity;
    private String txtIdentityNumber;
    private String txtBirthDate;
    private String txtBirthDateStore;
    private String txtDateOfDeath = "";
    private String txtDeathDateDisplay = "";
    private String selMaritalStatus;
    private String txtMaritalStartDate;
    private String txtMaritalEndDate;
    private String selLanguage;
    private String selEducationLevel;
    private String selEmploymentStatus;
    private String txtGrossIncome;
    private String txtDisposableIncome;

    private String externalReference;

    private String txtPhysicalPostalCode;

    // organisation general
    private String txtCompanyNumber;
    // party role
    private String selRoleType = PartyGuiHttpConstants.SELECT;
    private String selRelationshipRoleType = PartyGuiHttpConstants.SELECT;

    private List<LabelValueBean> relationshipTypes = new ArrayList<>();

    private String selRoleTypeForCpointsLoad;
    private String selRoleTypeForCpointsLoadID;
    public String differentRoleSelected = "false";
    private String txtPartyRoleEffectiveFromDate = null;
    private String txtPartyRoleEffectiveToDate = null;
    private PartyRole matchedPartyRole;
    private Long relationshipRoleSelected;
    private int partyRolesSize;
    private boolean relationshipRole = false;
    private boolean PayerPayeeRole;
    // contact preference
    private Long selContactPreferenceTypeId;
    private String selPreferredLanguage;
    private String txtStartDateContactPref;
    private String txtEndDateContactPref;
    private String defContPrefName;
    private Long preferenceSelected;
    private boolean prefToAddContactTo;
    private boolean defContPref = false;
    // contact point
    private Long selContactPointTypeId;
    private String contactId = null;
    private String editCPType;
    private Long contactSelected;
    private long currentContactSelectedId;
    private boolean defContPoint = false;
    private boolean defContPointPhys = false;
    private boolean physAddrRegionChanged = false;
    private boolean postAddrRegionChanged = false;
    private boolean unstructAddrRegionChanged = false;
    private boolean addContactPressed = false;
    private boolean editContactPressed = false;
    // email
    private String selEmailType;
    private String txtEmail;
    private String txtStartDateEmail;
    private String txtEndDateEmail;
    // phone
    private String txtLandLineCountryCode;
    private String txtLandLineAreaCode;
    private String txtLandLineLocalNumber;
    private String txtLandLineExt;
    private String txtCountryCode;
    private String txtAreaCode;
    private String txtLocalNumber;
    private String txtStartDateNumber;
    private String txtEndDateNumber;
    private String txtTelephoneElectronicType;
    // physical
    private String selPhysicalAddressCountryName;
    private String txtUnitNumber;
    private String txtFloorNumber;
    private String txtBuildingName;
    private String txtBuildingNumber;
    private String txtHouseNumber;
    private String txtStreet;
    private String txtPhysicalAddressCity;
    private String txtPhysicalAddressSubRegion;
    private String txtPhysicalAddressRegion;
    private String txtPhysicalAddressPostalCode;
    private String txtStartDatePhysical;
    private String txtEndDatePhysical;
    private String txtStartDatePhysicalAddress;
    private String txtEndDatePhysicalAddress;
    private Collection<String> phyPCdsCollection = new ArrayList<>();
    private boolean physicalContactEdit = false;
    // postal
    private String selPostalAddressCountryName;
    private String txtBoxNumber;
    private String txtPostnetSuite;
    private String txtPrivateBag;
    private String txtPostalAddressCity;
    private String txtPostalAddressSubRegion;
    private String txtSubRegion;
    private String txtPostalAddressRegion;
    private String txtPostalAddressPostalCode;
    private String postalCodeStore;
    private String txtStartDatePostalAddress;
    private String txtEndDatePostalAddress;
    private Collection<String> postPCdsCollection = new ArrayList<>();
    private boolean postalContactEdit = false;
    // unstructured
    private String selUnstructuredAddressCountryName;
    private String txtAddressLine1;
    private String txtAddressLine2;
    private String txtAddressLine3;
    private String txtAddressLine4;
    private String txtUnstructuredAddressCity;
    private String txtUnstructuredAddressSubRegion;
    private String txtUnstructuredSubRegion;
    private String txtUnstructuredAddressRegion;
    private String txtUnstructuredAddressPostalCode;
    private String unstructuredCodeStore;
    private String txtStartDateUnstructuredAddress;
    private String txtEndDateUnstructuredAddress;
    private Collection<String> unstructPCdsCollection = new ArrayList<>();
    private boolean unstructuredContactEdit = false;
    // bank
    private String selAccountPreference;
    private String selBankCountryCode;
    private String selAccountType;
    private static final int ACCOUNT_NUMBER_LENGTH = 13;
    private String accountHolder;
    private String accountNumber;
    private String accountType;
    private String bank;
    private String bankBranchReference;
    private String bankBranchCode;
    private String bankBranchName;
    private String crCardExpiryDate;
    private String key;
    private String effectiveFromDate;
    private String effectiveToDate;
    private Long bankRoleType;
    private Long fadSelected;
    private Long selBankRoleType;
    private Integer bankContactPreferenceType;
    private boolean def;
    private String bankTabMode;
    private boolean showAll = true;
    private boolean sharedContact;

    // registration
    private Long selRegistrationType;
    private Long selRegistrationTypeForEdit;
    private Collection<LabelValueBean> accreditationOptions = new ArrayList<>();
    private Long regSelected;

    // relationships
    private Long selRelationshipTypeFrom;
    private Long selRelationshipTypeForEditFrom;
    private Long selRelationshipTypeTo;
    private Long selRelationshipTypeForEditTo;
    private Long relFromSelected;

    // birth
    private String txtBDRegisteredName;
    private String txtBDRegisteringAuthority;
    private String txtBDRegisteredBirthDate;
    private String txtBDIssueDate;
    // death
    private String txtDateOfDeathRegistrationNumber;
    private String txtDateOfDeathRegisteringAuthority;
    private String txtDateOfDeathIssueDate;
    // drivers licence
    private String selDriversLicenseRestrictions;
    private String selDriversLicenseVehicleClass;
    private String txtDriversLicenceExternalReference;
    private String txtDriversLicenceVehicleClass;
    private String txtDriversLicenseRestrictions;
    private String txtDriversLicenceIssueDate;
    private String txtDriversLicenceRenewalDate;
    private String txtDriversLicenceEndDate;
    // education
    private String txtEducationDateOfRegistration;
    private String txtEducationRegistrationNumber;
    private String txtEducationRegisteringAuthority;
    private String txtEducationIssueDate;
    private Long educationRegSelected;
    // marriage
    private String txtMRMaritalStatusDate;
    private String txtMRRegistrationNumber;
    private String txtMRRegisteringAuthority;
    private String txtMRIssueDate;

    // national
    private String selNationalRegCountryName;
    private String txtNRRegisteringAuthority;
    private String txtNRIssueDate;

    // accreditation
    private Long selAccreditationTypeId;
    private String txtAccreditationRegistrationIssueDate;
    private String txtAccreditationRegistrationStartDate;
    private String txtAccreditationRegistrationEndDate;

    // general party registration
    private String txtPartyRegistrationDescription;
    private String txtPartyRegistrationIssueDate;
    private String txtPartyRegistrationStartDate;
    private String txtPartyRegistrationEndDate;

    // party relationship
    private String txtPartyRelationshipFromDescription;
    private String txtPartyRelationshipFromStartDate;
    private String txtPartyRelationshipFromEndDate;
    private String txtPartyRelationshipToDescription;
    private String txtPartyRelationshipToStartDate;
    private String txtPartyRelationshipToEndDate;
    private String txtPartyRelationshipToObjRef;
    private String txtPartyRelationshipFromObjRef;
    private String txtPartyRelationshipRelatedFrom;

    // name
    private PartyName defaultPrtyname;
    private Set<PartyName> partyNames;

    private Set<RolePlayerRelationship> relationshipsForParty = null;

    // role
    private Set<PartyRole> partyRolesForParty = null;

    private RolePlayer rolePlayerForCP = null;
    private Collection<LabelValueBean> rolesForCpsList = new ArrayList<>();
    private Set<ContactPreference> contactPrefsForParty = null;
    // registration
    private Set<PartyRegistration> partyRegistrations = null;
    // party ui displays
    // name
    private SortedMap<Long, PartyName> partyNamesMap = new TreeMap<Long, PartyName>();

    private SortedMap<Long, PartyRelationshipVO> partyRelationshipsFromMap = new TreeMap<Long, PartyRelationshipVO>();
    private SortedMap<Long, PartyRelationshipVO> partyRelationshipsToMap = new TreeMap<Long, PartyRelationshipVO>();

    private SortedMap<PartyRolesDetailVO, String> partyRoleAgmtsMap = new TreeMap<PartyRolesDetailVO, String>();
    private SortedMap<PartyRolesDetailVO, String> partyRoleLossEventsMap = new TreeMap<PartyRolesDetailVO, String>();
    private SortedMap<PartyRolesDetailVO, String> partyRoleClaimsMap = new TreeMap<PartyRolesDetailVO, String>();
    private SortedMap<PartyRolePartyDetailVO, String> partyRolePartiesMap = new TreeMap<PartyRolePartyDetailVO, String>();
    private SortedMap<ExternalPartyRoleDetailVO, String> externalPartyRolesMap = new TreeMap<ExternalPartyRoleDetailVO, String>();
    // role
    private SortedMap<PartyRolesDetailVO, String> partyRoleRelationshipsMap = new TreeMap<PartyRolesDetailVO, String>();
    private SortedMap<PartyRolesDetailVO, String> partyRoleNonLinkingMap = new TreeMap<PartyRolesDetailVO, String>();
    // contact
    private Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> prefsPointsMap = new TreeMap<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>>();
    private int contactPointSize = 0;
    // bank
    private Collection<BankDetailVO> bankDetailList = new ArrayList<>();

    /**
     * Variable to store shared bank details when someone attempts to edit the existing bank details on a role and that
     * bank detail is being shared by other roles.
     */
    private Collection<BankDetailVO> searchedBankDetailList = new ArrayList<>();

    private Map<String, Map<String, String>> bankPartyRolesAgmtsDisplayMap = new TreeMap<String, Map<String, String>>();
    // registration
    private SortedMap<Long, BirthCertificate> birthRegDisplayMap = new TreeMap<Long, BirthCertificate>();
    private SortedMap<Long, DeathCertificate> deathRegDisplayMap = new TreeMap<Long, DeathCertificate>();
    private SortedMap<Long, DriversLicence> licenceRegsDisplayMap = new TreeMap<Long, DriversLicence>();
    private SortedMap<Long, PartyRegistration> educationRegsDisplayMap = new TreeMap<Long, PartyRegistration>();
    private SortedMap<Long, PartyRegistration> marriageRegsDisplayMap = new TreeMap<Long, PartyRegistration>();
    private SortedMap<Long, NationalRegistration> nationalRegPersonDisplayMap = new TreeMap<Long, NationalRegistration>();
    private SortedMap<Long, Accreditation> accreditationRegsDisplayMap = new TreeMap<Long, Accreditation>();
    private SortedMap<Long, PartyRegistration> partyRegsDisplayMap = new TreeMap<Long, PartyRegistration>();

    private List<PartySearchResultVO> emailListSearchContacts = new ArrayList<>();
    private List<PartySearchResultVO> physicalListSearchContacts = new ArrayList<>();
    private List<PartySearchResultVO> postalListSearchContacts = new ArrayList<>();
    private List<PartySearchResultVO> numberListSearchContacts = new ArrayList<>();
    private List<PartySearchResultVO> financialAccountDetailsListSearchContacts = new ArrayList<>();
    public Long accountId;
    public Long accountTypeId;

    public void resetPartyInfo() {
        // current party set on form
        setParty(null);
        setPartyObjectRef(null);
        defaultPrtyname = null;
        partyNames = null;
        partyRolesForParty = null;
        contactPrefsForParty = null;
        partyRegistrations = null;
        defaultPartyNameId = (long) 0;
        defaultPartyName = false;
        // party name
        showNameButton = false;
        // tabs
        generalTabLoaded = false;
        genTabFirstLoad = false;
        relationshipsTabLoaded = false;
        rolesTabLoaded = false;
        contactTabLoaded = false;
        bankTabLoaded = false;
        regTabLoaded = false;
        // cPoint partyRole
        setRolePlayerForCP(null);
        setRoleInContext(null);

    }

    public void resetPartySearchInfo() {
        selectedPartyObjectRef = null;
    }

    public void resetFields() {

        matchedPartyRole = null;
        contactId = null;
        fullName = null;
        nameStartDate = null;
        nameStartDate2 = null;
        nameEndDate = null;
        txtCompanyNumber = null;
        selTitle = null;
        selSuffix = null;
        selNameType = null;
        txtFirstName = null;
        txtMiddleName = null;
        txtSurname = null;
        txtInitials = null;
        txtKnownAs = null;
        defaultName = false;
        selGender = null;
        selEthnicity = null;
        txtIdentityNumber = null;
        txtBirthDate = null;
        selMaritalStatus = null;
        txtMaritalStartDate = "";
        txtMaritalEndDate = "";
        selLanguage = null;
        selEducationLevel = null;
        selEmploymentStatus = null;
        txtGrossIncome = null;
        txtDisposableIncome = null;
        txtPhysicalPostalCode = null;
        selContactPointTypeId = null;
        selEmailType = null;
        txtEmail = null;
        txtAreaCode = null;
        txtLocalNumber = null;
        txtUnitNumber = null;
        txtFloorNumber = null;
        txtBuildingName = null;
        txtBuildingNumber = null;
        txtHouseNumber = null;
        txtStreet = null;
        txtSubRegion = null;
        txtBoxNumber = null;
        txtPostalAddressSubRegion = null;
        txtPostalAddressCity = null;
        selPreferredLanguage = null;
        txtBDRegisteredBirthDate = null;
        txtBDRegisteredName = null;
        txtBDRegisteringAuthority = null;
        txtBDIssueDate = "";
        txtNRRegisteringAuthority = null;
        txtNRIssueDate = "";
        txtMRMaritalStatusDate = null;
        txtMRRegistrationNumber = "";
        txtMRRegisteringAuthority = "";
        txtMRIssueDate = null;
        txtDateOfDeathRegistrationNumber = "";
        txtDateOfDeathRegisteringAuthority = "";
        txtDateOfDeathIssueDate = "";
        txtEducationDateOfRegistration = "";
        txtEducationRegistrationNumber = "";
        txtEducationRegisteringAuthority = "";
        txtEducationIssueDate = "";
        selAccountType = null;
        selAccountPreference = null;
        txtCountryCode = null;
        defaultName = false;
        txtFullName = null;
        txtAccreditationRegistrationStartDate = "";
        txtAccreditationRegistrationEndDate = "";
        txtAccreditationRegistrationIssueDate = "";
        selAccreditationTypeId = null;
        // general party registration
        txtPartyRegistrationDescription = "";
        txtPartyRegistrationIssueDate = "";
        txtPartyRegistrationStartDate = "";
        txtPartyRegistrationEndDate = "";

        //party relationship
        txtPartyRelationshipFromDescription = "";
        txtPartyRelationshipFromStartDate = "";
        txtPartyRelationshipFromEndDate = "";
        txtPartyRelationshipToDescription = "";
        txtPartyRelationshipToStartDate = "";
        txtPartyRelationshipToEndDate = "";
        txtPartyRelationshipFromObjRef = "";
        txtPartyRelationshipToObjRef = "";
        txtPartyRelationshipRelatedFrom = "";

        defaultPartyName = false;
        defContPoint = false;
        defContPointPhys = false;
        defContPref = false;
        selContactPreferenceTypeId = null;
        defaultPartyNameId = null;
        editCPType = null;
        selRoleTypeForCpointsLoad = "Party (default)";
        txtStartDateContactPref = "";
        txtEndDateContactPref = "";
        tabToChangeTo = getTabToChangeTo();
        txtDriversLicenceIssueDate = "";
        txtDriversLicenceEndDate = "";
        txtDriversLicenceRenewalDate = "";
        txtDriversLicenceExternalReference = "";
        PayerPayeeRole = false;
        duplicatesFound = false;
        overRideDups = false;
        selSearch = PartyGuiHttpConstants.FIND_PARTY_PERSON;
        // BANK resets
        def = false;
        showAll = true;
        accountNumber = null;
        accountHolder = null;
        crCardExpiryDate = null;
        effectiveFromDate = null;
        effectiveToDate = null;
        key = null;
        bank = null;
        bankBranchReference = null;
        bankBranchCode = null;
        bankBranchName = null;
        partyRolesSize = 0;
        accountId=null;
        accountTypeId=null;
    }

    /**
     * The reset after a party to party relationship linking has occurred
     */
    public void resetPartyToPartyRelationshipFields() {
        setSelRelationshipRoleType(null);
        setRelationshipRole(false);
    }

    /**
     * Reset <em>EVERYTHING</em>. Typically called when exiting the GUI
     */
    public void resetAll() {

        resetFields();
        resetPartyInfo();
        resetPartySearchInfo();
        resetPartyToPartyRelationshipFields();

        setPartyObjectRef(null);
        setRoleInContext(null);
        setRoleKindToLink(null);
        setEmbeddedMode(false);
        newParty = false;

        //  list is copied from PartyGuiUtility.reset(ActionMapping, ActionForm, HttpServletRequest)
        setAddContactPressed(false);
        setEditContactPressed(false);
        setPartyIsReadOnly(false);
        setReadOnly(false);
        setPhysicalContactEdit(false);
        setPostalContactEdit(false);
        setUnstructuredContactEdit(false);
        setDuplicatesFound(false);
        setOverRideDups(false);
    }

    /**
     *
     */
    public void resetForDisplayFromExternal() {

        resetFields();
        resetPartyInfo();
        resetPartySearchInfo();

        // don't reset the role player object reference
        newParty = false;
    }

    public void reset() {

        resetFields();

        setAddContactPressed(false);
        setEditContactPressed(false);
        setPartyIsReadOnly(false);
        setReadOnly(false);
        setPhysicalContactEdit(false);
        setPostalContactEdit(false);
        setUnstructuredContactEdit(false);
        setDuplicatesFound(false);
        setOverRideDups(false);
    }

    public void clearAllSearchDisplays() {
        setPersonPartialList(new ArrayList<>());
        setPersonList(new ArrayList<>());
        setOrganisationList(new ArrayList<>());
        setEmailListSearchContacts(new ArrayList<>());
        setPhysicalListSearchContacts(new ArrayList<>());
        setPostalListSearchContacts(new ArrayList<>());
    }

    // getters and setters start

    public boolean isPartyIsReadOnly() {
        return partyIsReadOnly;
    }

    public void setPartyIsReadOnly(boolean partyIsReadOnly) {
        this.partyIsReadOnly = partyIsReadOnly;
    }

    public int getTabToChangeTo() {
        return tabToChangeTo;
    }

    public void setTabToChangeTo(int tabToChangeTo) {
        this.tabToChangeTo = tabToChangeTo;
    }

    public boolean isGeneralTabLoaded() {
        return generalTabLoaded;
    }

    public void setGeneralTabLoaded(boolean generalTabLoaded) {
        this.generalTabLoaded = generalTabLoaded;
    }

    public boolean isRelationshipsTabLoaded() {
        return relationshipsTabLoaded;
    }

    public void setRelationshipsTabLoaded(boolean relationshipsTabLoaded) {
        this.relationshipsTabLoaded = relationshipsTabLoaded;
    }

    public boolean isRolesTabLoaded() {
        return rolesTabLoaded;
    }

    public void setRolesTabLoaded(boolean rolesTabLoaded) {
        this.rolesTabLoaded = rolesTabLoaded;
    }

    public boolean isContactTabLoaded() {
        return contactTabLoaded;
    }

    public void setContactTabLoaded(boolean contactTabLoaded) {
        this.contactTabLoaded = contactTabLoaded;
    }

    public boolean isBankTabLoaded() {
        return bankTabLoaded;
    }

    public void setBankTabLoaded(boolean bankTabLoaded) {
        this.bankTabLoaded = bankTabLoaded;
    }

    public boolean isRegTabLoaded() {
        return regTabLoaded;
    }

    public void setRegTabLoaded(boolean regTabLoaded) {
        this.regTabLoaded = regTabLoaded;
    }

    public boolean isGenTabFirstLoad() {
        return genTabFirstLoad;
    }

    public void setGenTabFirstLoad(boolean genTabFirstLoad) {
        this.genTabFirstLoad = genTabFirstLoad;
    }

    public boolean isRolesTabFirstLoad() {
        return rolesTabFirstLoad;
    }

    public void setRolesTabFirstLoad(boolean rolesTabFirstLoad) {
        this.rolesTabFirstLoad = rolesTabFirstLoad;
    }

    public boolean isContactTabFirstLoad() {
        return contactTabFirstLoad;
    }

    public void setContactTabFirstLoad(boolean contactTabFirstLoad) {
        this.contactTabFirstLoad = contactTabFirstLoad;
    }

    public boolean isBankTabFirstLoad() {
        return bankTabFirstLoad;
    }

    public void setBankTabFirstLoad(boolean bankTabFirstLoad) {
        this.bankTabFirstLoad = bankTabFirstLoad;
    }

    public boolean isRegTabFirstLoad() {
        return regTabFirstLoad;
    }

    public void setRegTabFirstLoad(boolean regTabFirstLoad) {
        this.regTabFirstLoad = regTabFirstLoad;
    }

    public boolean isSearchTabFirstLoad() {
        return searchTabFirstLoad;
    }

    public void setSearchTabFirstLoad(boolean searchTabFirstLoad) {
        this.searchTabFirstLoad = searchTabFirstLoad;
    }

    public String getSelectedPartyObjectRef() {
        return selectedPartyObjectRef;
    }

    public void setSelectedPartyObjectRef(String selectedPartyObjectRef) {
        this.selectedPartyObjectRef = selectedPartyObjectRef;
    }

    public String getSelSearch() {
        return selSearch;
    }

    public void setSelSearch(String selSearch) {
        this.selSearch = selSearch;
    }

    public boolean isDuplicatesFound() {
        return duplicatesFound;
    }

    public void setDuplicatesFound(boolean duplicatesFound) {
        this.duplicatesFound = duplicatesFound;
    }

    public boolean isOverRideDups() {
        return overRideDups;
    }

    public void setOverRideDups(boolean overRideDups) {
        this.overRideDups = overRideDups;
    }

    public String getSelPartyType() {
        return selPartyType;
    }

    public void setSelPartyType(String selPartyType) {
        this.selPartyType = selPartyType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSelNameType() {
        return selNameType;
    }

    public void setSelNameType(String selNameType) {
        this.selNameType = selNameType;
    }

    public String getSelSuffix() {
        return selSuffix;
    }

    public void setSelSuffix(String selSuffix) {
        this.selSuffix = selSuffix;
    }

    public String getSelTitle() {
        return selTitle;
    }

    public void setSelTitle(String selTitle) {
        this.selTitle = selTitle;
    }

    public String getTxtInitials() {
        return txtInitials;
    }

    public void setTxtInitials(String txtInitials) {
        this.txtInitials = txtInitials;
    }

    public String getTxtFirstName() {
        return txtFirstName;
    }

    public void setTxtFirstName(String txtFirstName) {
        this.txtFirstName = txtFirstName;
    }

    public String getTxtKnownAs() {
        return txtKnownAs;
    }

    public void setTxtKnownAs(String txtKnownAs) {
        this.txtKnownAs = txtKnownAs;
    }

    public String getTxtMiddleName() {
        return txtMiddleName;
    }

    public void setTxtMiddleName(String txtMiddleName) {
        this.txtMiddleName = txtMiddleName;
    }

    public String getTxtSurname() {
        return txtSurname;
    }

    public void setTxtSurname(String txtSurname) {
        this.txtSurname = txtSurname;
    }

    public String getNameStartDate() {
        return nameStartDate;
    }

    public void setNameStartDate(String nameStartDate) {
        this.nameStartDate = nameStartDate;
    }

    public String getNameStartDate2() {
        return nameStartDate2;
    }

    public void setNameStartDate2(String nameStartDate2) {
        this.nameStartDate2 = nameStartDate2;
    }

    public String getNameEndDate() {
        return nameEndDate;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public void setNameEndDate(String nameEndDate) {
        this.nameEndDate = nameEndDate;
    }

    public Long getPersonNameSelected() {
        return personNameSelected;
    }

    public void setPersonNameSelected(Long personNameSelected) {
        this.personNameSelected = personNameSelected;
    }

    public Long getDefaultPartyNameId() {
        return defaultPartyNameId;
    }

    public void setDefaultPartyNameId(Long defaultPartyNameId) {
        this.defaultPartyNameId = defaultPartyNameId;
    }

    public boolean isShowNameButton() {
        return showNameButton;
    }

    public void setShowNameButton(boolean showNameButton) {
        this.showNameButton = showNameButton;
    }

    public boolean isDefaultName() {
        return defaultName;
    }

    public void setDefaultName(boolean defaultName) {
        this.defaultName = defaultName;
    }

    public boolean isDefaultPartyName() {
        return defaultPartyName;
    }

    public void setDefaultPartyName(boolean defaultPartyName) {
        this.defaultPartyName = defaultPartyName;
    }

    public String getTxtFullName() {
        return txtFullName;
    }

    public void setTxtFullName(String txtFullName) {
        this.txtFullName = txtFullName;
    }

    public Long getSelGender() {
        return selGender;
    }

    public void setSelGender(Long selGender) {
        this.selGender = selGender;
    }

    public String getSelEthnicity() {
        return selEthnicity;
    }

    public void setSelEthnicity(String selEthnicity) {
        this.selEthnicity = selEthnicity;
    }

    public String getTxtIdentityNumber() {
        return txtIdentityNumber;
    }

    public void setTxtIdentityNumber(String txtIdentityNumber) {
        this.txtIdentityNumber = txtIdentityNumber;
    }

    public String getTxtBirthDate() {
        return txtBirthDate;
    }

    public void setTxtBirthDate(String txtBirthDate) {
        this.txtBirthDate = txtBirthDate;
    }

    public String getTxtBirthDateStore() {
        return txtBirthDateStore;
    }

    public void setTxtBirthDateStore(String txtBirthDateStore) {
        this.txtBirthDateStore = txtBirthDateStore;
    }

    public String getTxtDateOfDeath() {
        return txtDateOfDeath;
    }

    public void setTxtDateOfDeath(String txtDateOfDeath) {
        this.txtDateOfDeath = txtDateOfDeath;
    }

    public String getTxtDeathDateDisplay() {
        return txtDeathDateDisplay;
    }

    public void setTxtDeathDateDisplay(String txtDeathDateDisplay) {
        this.txtDeathDateDisplay = txtDeathDateDisplay;
    }

    public String getSelMaritalStatus() {
        return selMaritalStatus;
    }

    public void setSelMaritalStatus(String selMaritalStatus) {
        this.selMaritalStatus = selMaritalStatus;
    }

    public String getTxtMaritalStartDate() {
        return txtMaritalStartDate;
    }

    public void setTxtMaritalStartDate(String txtMaritalStartDate) {
        this.txtMaritalStartDate = txtMaritalStartDate;
    }

    public String getTxtMaritalEndDate() {
        return txtMaritalEndDate;
    }

    public void setTxtMaritalEndDate(String txtMaritalEndDate) {
        this.txtMaritalEndDate = txtMaritalEndDate;
    }

    public String getSelLanguage() {
        return selLanguage;
    }

    public void setSelLanguage(String selLanguage) {
        this.selLanguage = selLanguage;
    }

    public String getSelEducationLevel() {
        return selEducationLevel;
    }

    public void setSelEducationLevel(String selEducationLevel) {
        this.selEducationLevel = selEducationLevel;
    }

    public String getSelEmploymentStatus() {
        return selEmploymentStatus;
    }

    public void setSelEmploymentStatus(String selEmploymentStatus) {
        this.selEmploymentStatus = selEmploymentStatus;
    }

    public String getTxtGrossIncome() {
        return txtGrossIncome;
    }

    public void setTxtGrossIncome(String txtGrossIncome) {
        this.txtGrossIncome = txtGrossIncome;
    }

    public String getTxtDisposableIncome() {
        return txtDisposableIncome;
    }

    public void setTxtDisposableIncome(String txtDisposableIncome) {
        this.txtDisposableIncome = txtDisposableIncome;
    }

    public String getTxtCompanyNumber() {
        return txtCompanyNumber;
    }

    public void setTxtCompanyNumber(String txtCompanyNumber) {
        this.txtCompanyNumber = txtCompanyNumber;
    }

    public String getSelRoleType() {
        return selRoleType;
    }

    public void setSelRoleType(String selRoleType) {
        this.selRoleType = selRoleType;
    }

    public String getSelRelationshipRoleType() {
        return selRelationshipRoleType;
    }

    public void setSelRelationshipRoleType(String selRelationshipRoleType) {
        this.selRelationshipRoleType = selRelationshipRoleType;
    }

    public String getSelRoleTypeForCpointsLoad() {
        return selRoleTypeForCpointsLoad;
    }

    public void setSelRoleTypeForCpointsLoad(String selRoleTypeForCpointsLoad) {
        this.selRoleTypeForCpointsLoad = selRoleTypeForCpointsLoad;
    }

    public String getSelRoleTypeForCpointsLoadID() {
        return selRoleTypeForCpointsLoadID;
    }

    public void setSelRoleTypeForCpointsLoadID(String selRoleTypeForCpointsLoadID) {
        this.selRoleTypeForCpointsLoadID = selRoleTypeForCpointsLoadID;
    }

    public String getDifferentRoleSelected() {
        return differentRoleSelected;
    }

    public void setDifferentRoleSelected(String differentRoleSelected) {
        this.differentRoleSelected = differentRoleSelected;
    }

    public String getTxtPartyRoleEffectiveFromDate() {
        return txtPartyRoleEffectiveFromDate;
    }

    public void setTxtPartyRoleEffectiveFromDate(String txtPartyRoleEffectiveFromDate) {
        this.txtPartyRoleEffectiveFromDate = txtPartyRoleEffectiveFromDate;
    }

    public String getTxtPartyRoleEffectiveToDate() {
        return txtPartyRoleEffectiveToDate;
    }

    public void setTxtPartyRoleEffectiveToDate(String txtPartyRoleEffectiveToDate) {
        this.txtPartyRoleEffectiveToDate = txtPartyRoleEffectiveToDate;
    }

    public PartyRole getMatchedPartyRole() {
        return matchedPartyRole;
    }

    public void setMatchedPartyRole(PartyRole matchedPartyRole) {
        this.matchedPartyRole = matchedPartyRole;
    }

    public Long getRelationshipRoleSelected() {
        return relationshipRoleSelected;
    }

    public void setRelationshipRoleSelected(Long relationshipRoleSelected) {
        this.relationshipRoleSelected = relationshipRoleSelected;
    }

    public int getPartyRolesSize() {
        return partyRolesSize;
    }

    public void setPartyRolesSize(int partyRolesSize) {
        this.partyRolesSize = partyRolesSize;
    }

    public boolean isRelationshipRole() {
        return relationshipRole;
    }

    public void setRelationshipRole(boolean relationshipRole) {
        this.relationshipRole = relationshipRole;
    }

    public boolean isPayerPayeeRole() {
        return PayerPayeeRole;
    }

    public void setPayerPayeeRole(boolean payerPayeeRole) {
        PayerPayeeRole = payerPayeeRole;
    }

    public Long getSelContactPreferenceTypeId() {
        return selContactPreferenceTypeId;
    }

    public void setSelContactPreferenceTypeId(Long selContactPreferenceTypeId) {
        this.selContactPreferenceTypeId = selContactPreferenceTypeId;
    }

    public String getSelPreferredLanguage() {
        return selPreferredLanguage;
    }

    public void setSelPreferredLanguage(String selPreferredLanguage) {
        this.selPreferredLanguage = selPreferredLanguage;
    }

    public String getTxtStartDateContactPref() {
        return txtStartDateContactPref;
    }

    public void setTxtStartDateContactPref(String txtStartDateContactPref) {
        this.txtStartDateContactPref = txtStartDateContactPref;
    }

    public String getTxtEndDateContactPref() {
        return txtEndDateContactPref;
    }

    public void setTxtEndDateContactPref(String txtEndDateContactPref) {
        this.txtEndDateContactPref = txtEndDateContactPref;
    }

    public String getDefContPrefName() {
        return defContPrefName;
    }

    public void setDefContPrefName(String defContPrefName) {
        this.defContPrefName = defContPrefName;
    }

    public Long getPreferenceSelected() {
        return preferenceSelected;
    }

    public void setPreferenceSelected(Long preferenceSelected) {
        this.preferenceSelected = preferenceSelected;
    }

    public boolean isPrefToAddContactTo() {
        return prefToAddContactTo;
    }

    public void setPrefToAddContactTo(boolean prefToAddContactTo) {
        this.prefToAddContactTo = prefToAddContactTo;
    }

    public boolean isDefContPref() {
        return defContPref;
    }

    public void setDefContPref(boolean defContPref) {
        this.defContPref = defContPref;
    }

    public Long getSelContactPointTypeId() {
        return selContactPointTypeId;
    }

    public void setSelContactPointTypeId(Long selContactPointTypeId) {
        this.selContactPointTypeId = selContactPointTypeId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getEditCPType() {
        return editCPType;
    }

    public void setEditCPType(String editCPType) {
        this.editCPType = editCPType;
    }

    public Long getContactSelected() {
        return contactSelected;
    }

    public void setContactSelected(Long contactSelected) {
        this.contactSelected = contactSelected;
    }

    public long getCurrentContactSelectedId() {
        return currentContactSelectedId;
    }

    public void setCurrentContactSelectedId(long currentContactSelectedId) {
        this.currentContactSelectedId = currentContactSelectedId;
    }

    public boolean isDefContPoint() {
        return defContPoint;
    }

    public void setDefContPoint(boolean defContPoint) {
        this.defContPoint = defContPoint;
    }

    public boolean isDefContPointPhys() {
        return defContPointPhys;
    }

    public void setDefContPointPhys(boolean defContPointPhys) {
        this.defContPointPhys = defContPointPhys;
    }

    public boolean isPhysAddrRegionChanged() {
        return physAddrRegionChanged;
    }

    public void setPhysAddrRegionChanged(boolean physAddrRegionChanged) {
        this.physAddrRegionChanged = physAddrRegionChanged;
    }

    public boolean isPostAddrRegionChanged() {
        return postAddrRegionChanged;
    }

    public void setPostAddrRegionChanged(boolean postAddrRegionChanged) {
        this.postAddrRegionChanged = postAddrRegionChanged;
    }

    public boolean isUnstructAddrRegionChanged() {
        return unstructAddrRegionChanged;
    }

    public void setUnstructAddrRegionChanged(boolean unstructAddrRegionChanged) {
        this.unstructAddrRegionChanged = unstructAddrRegionChanged;
    }

    public boolean isAddContactPressed() {
        return addContactPressed;
    }

    public void setAddContactPressed(boolean addContactPressed) {
        this.addContactPressed = addContactPressed;
    }

    public boolean isEditContactPressed() {
        return editContactPressed;
    }

    public void setEditContactPressed(boolean editContactPressed) {
        this.editContactPressed = editContactPressed;
    }

    public String getSelEmailType() {
        return selEmailType;
    }

    public void setSelEmailType(String selEmailType) {
        this.selEmailType = selEmailType;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtStartDateEmail() {
        return txtStartDateEmail;
    }

    public void setTxtStartDateEmail(String txtStartDateEmail) {
        this.txtStartDateEmail = txtStartDateEmail;
    }

    public String getTxtEndDateEmail() {
        return txtEndDateEmail;
    }

    public void setTxtEndDateEmail(String txtEndDateEmail) {
        this.txtEndDateEmail = txtEndDateEmail;
    }

    public String getTxtLandLineCountryCode() {
        return txtLandLineCountryCode;
    }

    public void setTxtLandLineCountryCode(String txtLandLineCountryCode) {
        this.txtLandLineCountryCode = txtLandLineCountryCode;
    }

    public String getTxtLandLineAreaCode() {
        return txtLandLineAreaCode;
    }

    public void setTxtLandLineAreaCode(String txtLandLineAreaCode) {
        this.txtLandLineAreaCode = txtLandLineAreaCode;
    }

    public String getTxtLandLineLocalNumber() {
        return txtLandLineLocalNumber;
    }

    public void setTxtLandLineLocalNumber(String txtLandLineLocalNumber) {
        this.txtLandLineLocalNumber = txtLandLineLocalNumber;
    }

    public String getTxtLandLineExt() {
        return txtLandLineExt;
    }

    public void setTxtLandLineExt(String txtLandLineExt) {
        this.txtLandLineExt = txtLandLineExt;
    }

    public String getTxtCountryCode() {
        return txtCountryCode;
    }

    public void setTxtCountryCode(String txtCountryCode) {
        this.txtCountryCode = txtCountryCode;
    }

    public String getTxtAreaCode() {
        return txtAreaCode;
    }

    public void setTxtAreaCode(String txtAreaCode) {
        this.txtAreaCode = txtAreaCode;
    }

    public String getTxtLocalNumber() {
        return txtLocalNumber;
    }

    public void setTxtLocalNumber(String txtLocalNumber) {
        this.txtLocalNumber = txtLocalNumber;
    }

    public String getTxtStartDateNumber() {
        return txtStartDateNumber;
    }

    public void setTxtStartDateNumber(String txtStartDateNumber) {
        this.txtStartDateNumber = txtStartDateNumber;
    }

    public String getTxtEndDateNumber() {
        return txtEndDateNumber;
    }

    public void setTxtEndDateNumber(String txtEndDateNumber) {
        this.txtEndDateNumber = txtEndDateNumber;
    }

    public String getSelPhysicalAddressCountryName() {
        return selPhysicalAddressCountryName;
    }

    public void setSelPhysicalAddressCountryName(String selPhysicalAddressCountryName) {
        this.selPhysicalAddressCountryName = selPhysicalAddressCountryName;
    }

    public String getTxtUnitNumber() {
        return txtUnitNumber;
    }

    public void setTxtUnitNumber(String txtUnitNumber) {
        this.txtUnitNumber = txtUnitNumber;
    }

    public String getTxtFloorNumber() {
        return txtFloorNumber;
    }

    public void setTxtFloorNumber(String txtFloorNumber) {
        this.txtFloorNumber = txtFloorNumber;
    }

    public String getTxtBuildingName() {
        return txtBuildingName;
    }

    public void setTxtBuildingName(String txtBuildingName) {
        this.txtBuildingName = txtBuildingName;
    }

    public String getTxtBuildingNumber() {
        return txtBuildingNumber;
    }

    public void setTxtBuildingNumber(String txtBuildingNumber) {
        this.txtBuildingNumber = txtBuildingNumber;
    }

    public String getTxtHouseNumber() {
        return txtHouseNumber;
    }

    public void setTxtHouseNumber(String txtHouseNumber) {
        this.txtHouseNumber = txtHouseNumber;
    }

    public String getTxtStreet() {
        return txtStreet;
    }

    public void setTxtStreet(String txtStreet) {
        this.txtStreet = txtStreet;
    }

    public String getTxtPhysicalAddressCity() {
        return txtPhysicalAddressCity;
    }

    public void setTxtPhysicalAddressCity(String txtPhysicalAddressCity) {
        this.txtPhysicalAddressCity = txtPhysicalAddressCity;
    }

    public String getTxtPhysicalAddressSubRegion() {
        return txtPhysicalAddressSubRegion;
    }

    public void setTxtPhysicalAddressSubRegion(String txtPhysicalAddressSubRegion) {
        this.txtPhysicalAddressSubRegion = txtPhysicalAddressSubRegion;
    }

    public String getTxtPhysicalAddressRegion() {
        return txtPhysicalAddressRegion;
    }

    public void setTxtPhysicalAddressRegion(String txtPhysicalAddressRegion) {
        this.txtPhysicalAddressRegion = txtPhysicalAddressRegion;
    }

    public String getTxtPhysicalPostalCode() {
        return txtPhysicalPostalCode;
    }

    public void setTxtPhysicalPostalCode(String txtPhysicalPostalCode) {
        this.txtPhysicalPostalCode = txtPhysicalPostalCode;
    }

    public String getTxtPhysicalAddressPostalCode() {
        return txtPhysicalAddressPostalCode;
    }

    public void setTxtPhysicalAddressPostalCode(String txtPhysicalAddressPostalCode) {
        this.txtPhysicalAddressPostalCode = txtPhysicalAddressPostalCode;
    }

    public String getTxtStartDatePhysical() {
        return txtStartDatePhysical;
    }

    public void setTxtStartDatePhysical(String txtStartDatePhysical) {
        this.txtStartDatePhysical = txtStartDatePhysical;
    }

    public String getTxtEndDatePhysical() {
        return txtEndDatePhysical;
    }

    public void setTxtEndDatePhysical(String txtEndDatePhysical) {
        this.txtEndDatePhysical = txtEndDatePhysical;
    }

    public String getTxtStartDatePhysicalAddress() {
        return txtStartDatePhysicalAddress;
    }

    public void setTxtStartDatePhysicalAddress(String txtStartDatePhysicalAddress) {
        this.txtStartDatePhysicalAddress = txtStartDatePhysicalAddress;
    }

    public String getTxtEndDatePhysicalAddress() {
        return txtEndDatePhysicalAddress;
    }

    public void setTxtEndDatePhysicalAddress(String txtEndDatePhysicalAddress) {
        this.txtEndDatePhysicalAddress = txtEndDatePhysicalAddress;
    }

    public Collection<String> getPhyPCdsCollection() {
        return phyPCdsCollection;
    }

    public void setPhyPCdsCollection(Collection<String> phyPCdsCollection) {
        this.phyPCdsCollection = phyPCdsCollection;
    }

    public boolean isPhysicalContactEdit() {
        return physicalContactEdit;
    }

    public void setPhysicalContactEdit(boolean physicalContactEdit) {
        this.physicalContactEdit = physicalContactEdit;
    }

    public String getSelPostalAddressCountryName() {
        return selPostalAddressCountryName;
    }

    public void setSelPostalAddressCountryName(String selPostalAddressCountryName) {
        this.selPostalAddressCountryName = selPostalAddressCountryName;
    }

    public String getTxtBoxNumber() {
        return txtBoxNumber;
    }

    public void setTxtBoxNumber(String txtBoxNumber) {
        this.txtBoxNumber = txtBoxNumber;
    }

    public String getTxtPostnetSuite() {
        return txtPostnetSuite;
    }

    public void setTxtPostnetSuite(String txtPostnetSuite) {
        this.txtPostnetSuite = txtPostnetSuite;
    }

    public String getTxtPrivateBag() {
        return txtPrivateBag;
    }

    public void setTxtPrivateBag(String txtPrivateBag) {
        this.txtPrivateBag = txtPrivateBag;
    }

    public String getTxtPostalAddressCity() {
        return txtPostalAddressCity;
    }

    public void setTxtPostalAddressCity(String txtPostalAddressCity) {
        this.txtPostalAddressCity = txtPostalAddressCity;
    }

    public String getTxtPostalAddressSubRegion() {
        return txtPostalAddressSubRegion;
    }

    public void setTxtPostalAddressSubRegion(String txtPostalAddressSubRegion) {
        this.txtPostalAddressSubRegion = txtPostalAddressSubRegion;
    }

    public String getTxtSubRegion() {
        return txtSubRegion;
    }

    public void setTxtSubRegion(String txtSubRegion) {
        this.txtSubRegion = txtSubRegion;
    }

    public String getTxtPostalAddressRegion() {
        return txtPostalAddressRegion;
    }

    public void setTxtPostalAddressRegion(String txtPostalAddressRegion) {
        this.txtPostalAddressRegion = txtPostalAddressRegion;
    }

    public String getTxtPostalAddressPostalCode() {
        return txtPostalAddressPostalCode;
    }

    public void setTxtPostalAddressPostalCode(String txtPostalAddressPostalCode) {
        this.txtPostalAddressPostalCode = txtPostalAddressPostalCode;
    }

    public String getPostalCodeStore() {
        return postalCodeStore;
    }

    public void setPostalCodeStore(String postalCodeStore) {
        this.postalCodeStore = postalCodeStore;
    }

    public String getTxtStartDatePostalAddress() {
        return txtStartDatePostalAddress;
    }

    public void setTxtStartDatePostalAddress(String txtStartDatePostalAddress) {
        this.txtStartDatePostalAddress = txtStartDatePostalAddress;
    }

    public String getTxtEndDatePostalAddress() {
        return txtEndDatePostalAddress;
    }

    public void setTxtEndDatePostalAddress(String txtEndDatePostalAddress) {
        this.txtEndDatePostalAddress = txtEndDatePostalAddress;
    }

    public Collection<String> getPostPCdsCollection() {
        return postPCdsCollection;
    }

    public void setPostPCdsCollection(Collection<String> postPCdsCollection) {
        this.postPCdsCollection = postPCdsCollection;
    }

    public boolean isPostalContactEdit() {
        return postalContactEdit;
    }

    public void setPostalContactEdit(boolean postalContactEdit) {
        this.postalContactEdit = postalContactEdit;
    }

    public String getSelUnstructuredAddressCountryName() {
        return selUnstructuredAddressCountryName;
    }

    public void setSelUnstructuredAddressCountryName(String selUnstructuredAddressCountryName) {
        this.selUnstructuredAddressCountryName = selUnstructuredAddressCountryName;
    }

    public String getTxtAddressLine1() {
        return txtAddressLine1;
    }

    public void setTxtAddressLine1(String txtAddressLine1) {
        this.txtAddressLine1 = txtAddressLine1;
    }

    public String getTxtAddressLine2() {
        return txtAddressLine2;
    }

    public void setTxtAddressLine2(String txtAddressLine2) {
        this.txtAddressLine2 = txtAddressLine2;
    }

    public String getTxtAddressLine3() {
        return txtAddressLine3;
    }

    public void setTxtAddressLine3(String txtAddressLine3) {
        this.txtAddressLine3 = txtAddressLine3;
    }

    public String getTxtAddressLine4() {
        return txtAddressLine4;
    }

    public void setTxtAddressLine4(String txtAddressLine4) {
        this.txtAddressLine4 = txtAddressLine4;
    }

    public String getTxtUnstructuredAddressCity() {
        return txtUnstructuredAddressCity;
    }

    public void setTxtUnstructuredAddressCity(String txtUnstructuredAddressCity) {
        this.txtUnstructuredAddressCity = txtUnstructuredAddressCity;
    }

    public String getTxtUnstructuredAddressSubRegion() {
        return txtUnstructuredAddressSubRegion;
    }

    public void setTxtUnstructuredAddressSubRegion(String txtUnstructuredAddressSubRegion) {
        this.txtUnstructuredAddressSubRegion = txtUnstructuredAddressSubRegion;
    }

    public String getTxtUnstructuredSubRegion() {
        return txtUnstructuredSubRegion;
    }

    public void setTxtUnstructuredSubRegion(String txtUnstructuredSubRegion) {
        this.txtUnstructuredSubRegion = txtUnstructuredSubRegion;
    }

    public String getTxtUnstructuredAddressRegion() {
        return txtUnstructuredAddressRegion;
    }

    public void setTxtUnstructuredAddressRegion(String txtUnstructuredAddressRegion) {
        this.txtUnstructuredAddressRegion = txtUnstructuredAddressRegion;
    }

    public String getTxtUnstructuredAddressPostalCode() {
        return txtUnstructuredAddressPostalCode;
    }

    public void setTxtUnstructuredAddressPostalCode(String txtUnstructuredAddressPostalCode) {
        this.txtUnstructuredAddressPostalCode = txtUnstructuredAddressPostalCode;
    }

    public String getUnstructuredCodeStore() {
        return unstructuredCodeStore;
    }

    public void setUnstructuredCodeStore(String unstructuredCodeStore) {
        this.unstructuredCodeStore = unstructuredCodeStore;
    }

    public String getTxtStartDateUnstructuredAddress() {
        return txtStartDateUnstructuredAddress;
    }

    public void setTxtStartDateUnstructuredAddress(String txtStartDateUnstructuredAddress) {
        this.txtStartDateUnstructuredAddress = txtStartDateUnstructuredAddress;
    }

    public String getTxtEndDateUnstructuredAddress() {
        return txtEndDateUnstructuredAddress;
    }

    public void setTxtEndDateUnstructuredAddress(String txtEndDateUnstructuredAddress) {
        this.txtEndDateUnstructuredAddress = txtEndDateUnstructuredAddress;
    }

    public Collection<String> getUnstructPCdsCollection() {
        return unstructPCdsCollection;
    }

    public void setUnstructPCdsCollection(Collection<String> unstructPCdsCollection) {
        this.unstructPCdsCollection = unstructPCdsCollection;
    }

    public boolean isUnstructuredContactEdit() {
        return unstructuredContactEdit;
    }

    public void setUnstructuredContactEdit(boolean unstructuredContactEdit) {
        this.unstructuredContactEdit = unstructuredContactEdit;
    }

    public String getSelAccountPreference() {
        return selAccountPreference;
    }

    public void setSelAccountPreference(String selAccountPreference) {
        this.selAccountPreference = selAccountPreference;
    }

    public String getSelBankCountryCode() {
        return selBankCountryCode;
    }

    public void setSelBankCountryCode(String selBankCountryCode) {
        this.selBankCountryCode = selBankCountryCode;
    }

    public String getSelAccountType() {
        return selAccountType;
    }

    public void setSelAccountType(String selAccountType) {
        this.selAccountType = selAccountType;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankBranchReference() {
        return bankBranchReference;
    }

    public void setBankBranchReference(String bankBranchReference) {
        this.bankBranchReference = bankBranchReference;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }

    public String getCrCardExpiryDate() {
        return crCardExpiryDate;
    }

    public void setCrCardExpiryDate(String crCardExpiryDate) {
        this.crCardExpiryDate = crCardExpiryDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEffectiveFromDate() {
        return effectiveFromDate;
    }

    public void setEffectiveFromDate(String effectiveFromDate) {
        this.effectiveFromDate = effectiveFromDate;
    }

    public String getEffectiveToDate() {
        return effectiveToDate;
    }

    public void setEffectiveToDate(String effectiveToDate) {
        this.effectiveToDate = effectiveToDate;
    }

    public Long getBankRoleType() {
        return bankRoleType;
    }

    public void setBankRoleType(Long bankRoleType) {
        this.bankRoleType = bankRoleType;
    }

    public Long getFadSelected() {
        return fadSelected;
    }

    public void setFadSelected(Long fadSelected) {
        this.fadSelected = fadSelected;
    }

    public Long getSelBankRoleType() {
        return selBankRoleType;
    }

    public void setSelBankRoleType(Long selBankRoleType) {
        this.selBankRoleType = selBankRoleType;
    }

    public Integer getBankContactPreferenceType() {
        return bankContactPreferenceType;
    }

    public void setBankContactPreferenceType(Integer bankContactPreferenceType) {
        this.bankContactPreferenceType = bankContactPreferenceType;
    }

    public boolean isDef() {
        return def;
    }

    public void setDef(boolean def) {
        this.def = def;
    }

    /**
     * @return the bankTabMode
     */
    public String getBankTabMode() {
        return bankTabMode;
    }

    /**
     * @param bankTabMode
     *            the bankTabMode to set
     */
    public void setBankTabMode(String bankTabMode) {
        this.bankTabMode = bankTabMode;
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public Long getSelRegistrationType() {
        return selRegistrationType;
    }

    public void setSelRegistrationType(Long selRegistrationType) {
        this.selRegistrationType = selRegistrationType;
    }

    public Long getSelRegistrationTypeForEdit() {
        return selRegistrationTypeForEdit;
    }

    public void setSelRegistrationTypeForEdit(Long selRegistrationTypeForEdit) {
        this.selRegistrationTypeForEdit = selRegistrationTypeForEdit;
    }

    public Long getSelRelationshipTypeFrom() {
        return selRelationshipTypeFrom;
    }

    public void setSelRelationshipTypeFrom(Long selRelationshipTypeFrom) {
        this.selRelationshipTypeFrom = selRelationshipTypeFrom;
    }

    public Long getSelRelationshipTypeForEditFrom() {
        return selRelationshipTypeForEditFrom;
    }

    public void setSelRelationshipTypeForEditFrom(Long selRelationshipTypeForEditFrom) {
        this.selRelationshipTypeForEditFrom = selRelationshipTypeForEditFrom;
    }

    public Collection<LabelValueBean> getAccreditationOptions() {
        return accreditationOptions;
    }

    public void setAccreditationOptions(Collection<LabelValueBean> accreditationOptions) {
        this.accreditationOptions = accreditationOptions;
    }

    public String getTxtBDRegisteredName() {
        return txtBDRegisteredName;
    }

    public void setTxtBDRegisteredName(String txtBDRegisteredName) {
        this.txtBDRegisteredName = txtBDRegisteredName;
    }

    public String getTxtBDRegisteringAuthority() {
        return txtBDRegisteringAuthority;
    }

    public void setTxtBDRegisteringAuthority(String txtBDRegisteringAuthority) {
        this.txtBDRegisteringAuthority = txtBDRegisteringAuthority;
    }

    public String getTxtBDRegisteredBirthDate() {
        return txtBDRegisteredBirthDate;
    }

    public void setTxtBDRegisteredBirthDate(String txtBDRegisteredBirthDate) {
        this.txtBDRegisteredBirthDate = txtBDRegisteredBirthDate;
    }

    public String getTxtBDIssueDate() {
        return txtBDIssueDate;
    }

    public void setTxtBDIssueDate(String txtBDIssueDate) {
        this.txtBDIssueDate = txtBDIssueDate;
    }

    public Long getRegSelected() {
        return regSelected;
    }

    public void setRegSelected(Long regSelected) {
        this.regSelected = regSelected;
    }

    public String getTxtDateOfDeathRegistrationNumber() {
        return txtDateOfDeathRegistrationNumber;
    }

    public void setTxtDateOfDeathRegistrationNumber(String txtDateOfDeathRegistrationNumber) {
        this.txtDateOfDeathRegistrationNumber = txtDateOfDeathRegistrationNumber;
    }

    public String getTxtDateOfDeathRegisteringAuthority() {
        return txtDateOfDeathRegisteringAuthority;
    }

    public void setTxtDateOfDeathRegisteringAuthority(String txtDateOfDeathRegisteringAuthority) {
        this.txtDateOfDeathRegisteringAuthority = txtDateOfDeathRegisteringAuthority;
    }

    public String getTxtDateOfDeathIssueDate() {
        return txtDateOfDeathIssueDate;
    }

    public void setTxtDateOfDeathIssueDate(String txtDateOfDeathIssueDate) {
        this.txtDateOfDeathIssueDate = txtDateOfDeathIssueDate;
    }

    public String getSelDriversLicenseRestrictions() {
        return selDriversLicenseRestrictions;
    }

    public void setSelDriversLicenseRestrictions(String selDriversLicenseRestrictions) {
        this.selDriversLicenseRestrictions = selDriversLicenseRestrictions;
    }

    public String getSelDriversLicenseVehicleClass() {
        return selDriversLicenseVehicleClass;
    }

    public void setSelDriversLicenseVehicleClass(String selDriversLicenseVehicleClass) {
        this.selDriversLicenseVehicleClass = selDriversLicenseVehicleClass;
    }

    public String getTxtDriversLicenceExternalReference() {
        return txtDriversLicenceExternalReference;
    }

    public void setTxtDriversLicenceExternalReference(String txtDriversLicenceExternalReference) {
        this.txtDriversLicenceExternalReference = txtDriversLicenceExternalReference;
    }

    public String getTxtDriversLicenceVehicleClass() {
        return txtDriversLicenceVehicleClass;
    }

    public void setTxtDriversLicenceVehicleClass(String txtDriversLicenceVehicleClass) {
        this.txtDriversLicenceVehicleClass = txtDriversLicenceVehicleClass;
    }

    public String getTxtDriversLicenseRestrictions() {
        return txtDriversLicenseRestrictions;
    }

    public void setTxtDriversLicenseRestrictions(String txtDriversLicenseRestrictions) {
        this.txtDriversLicenseRestrictions = txtDriversLicenseRestrictions;
    }

    public String getTxtDriversLicenceIssueDate() {
        return txtDriversLicenceIssueDate;
    }

    public void setTxtDriversLicenceIssueDate(String txtDriversLicenceIssueDate) {
        this.txtDriversLicenceIssueDate = txtDriversLicenceIssueDate;
    }

    public String getTxtDriversLicenceRenewalDate() {
        return txtDriversLicenceRenewalDate;
    }

    public void setTxtDriversLicenceRenewalDate(String txtDriversLicenceRenewalDate) {
        this.txtDriversLicenceRenewalDate = txtDriversLicenceRenewalDate;
    }

    public String getTxtDriversLicenceEndDate() {
        return txtDriversLicenceEndDate;
    }

    public void setTxtDriversLicenceEndDate(String txtDriversLicenceEndDate) {
        this.txtDriversLicenceEndDate = txtDriversLicenceEndDate;
    }

    public String getTxtEducationDateOfRegistration() {
        return txtEducationDateOfRegistration;
    }

    public void setTxtEducationDateOfRegistration(String txtEducationDateOfRegistration) {
        this.txtEducationDateOfRegistration = txtEducationDateOfRegistration;
    }

    public String getTxtEducationRegistrationNumber() {
        return txtEducationRegistrationNumber;
    }

    public void setTxtEducationRegistrationNumber(String txtEducationRegistrationNumber) {
        this.txtEducationRegistrationNumber = txtEducationRegistrationNumber;
    }

    public String getTxtEducationRegisteringAuthority() {
        return txtEducationRegisteringAuthority;
    }

    public void setTxtEducationRegisteringAuthority(String txtEducationRegisteringAuthority) {
        this.txtEducationRegisteringAuthority = txtEducationRegisteringAuthority;
    }

    public String getTxtEducationIssueDate() {
        return txtEducationIssueDate;
    }

    public void setTxtEducationIssueDate(String txtEducationIssueDate) {
        this.txtEducationIssueDate = txtEducationIssueDate;
    }

    @Override
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
        if (needsValidation(actionMapping, httpServletRequest)) {

            ActionErrors actionMessages = super.validate(actionMapping, httpServletRequest);

            String validateType = StringUtils.trimToNull(httpServletRequest.getParameter("validate"));

            if ("validatePerson".equals(validateType)) {
                if (selPartyType.equalsIgnoreCase(CoreTypeReference.PERSON.getName())) {
                    if (GenericValidator.isBlankOrNull(selTitle)) {
                        actionMessages.add("selTitle", new ActionMessage("errors.required",
                                getFieldName("page.party.label.title")));
                    }
                    if (GenericValidator.isBlankOrNull(txtSurname)) {
                        actionMessages.add("txtSurname", new ActionMessage("errors.required",
                                getFieldName("page.party.label.surname")));
                    }
                    if (GenericValidator.isBlankOrNull(txtFirstName)) {
                        actionMessages.add("txtFirstName", new ActionMessage("errors.required",
                                getFieldName("page.party.label.firstname")));
                    }
                    if (!GenericValidator.isBlankOrNull(nameStartDate)) { // name
                        Date tmpStartDate = new Date(nameStartDate);
                        if (tmpStartDate.isAfter(Date.today())) {
                            actionMessages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                                    "message.party.nameStartdateAfterCurrentDate"));
                        }
                        if (!GenericValidator.isBlankOrNull(nameEndDate)) {
                            Date tmpEndDate = new Date(nameEndDate);
                            if (tmpStartDate.isAfter(tmpEndDate)) {
                                actionMessages.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                                        "message.party.nameStartdateAfterNameEndDate"));
                            }
                        }
                    }
                    if (!GenericValidator.isBlankOrNull(txtGrossIncome)) {
                        if (!PartyGuiUtility.isAmountValid(txtGrossIncome)) {
                            actionMessages.add("txtGrossIncome", new ActionMessage("message.party.invalidGross"));
                        }
                    }
                    if (!GenericValidator.isBlankOrNull(txtDisposableIncome)) {
                        if (!PartyGuiUtility.isAmountValid(txtDisposableIncome)) {
                            actionMessages.add("txtDisposableIncome", new ActionMessage(
                                    "message.party.invalidDisposable"));
                        }
                    }
                    if (GenericValidator.isBlankOrNull(nameStartDate2)) {
                        actionMessages.add("nameStartDate2", new ActionMessage("errors.required",
                                getFieldName("page.party.label.birthdate")));
                    }
                    if (selGender == null) {
                        actionMessages.add("selGender", new ActionMessage("errors.required",
                                getFieldName("page.party.generalparty.label.gender")));
                    }
                } else {

                }
                setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
                return actionMessages;
            } else if (validateType.equals("validateBanking")) {
                validateBanking(actionMessages);
            }  else if (validateType.equals("validateOrganisation")) {
                return validateUpdateOrganisation();
            } else if (validateType.equals("validatePersonName")) {
                ActionErrors actionErrors = new ActionErrors();
                if (GenericValidator.isBlankOrNull(getSelTitle())) {
                    actionErrors.add("selTitle", new ActionMessage("errors.required",
                            getFieldName("page.party.label.title")));
                }
                if (GenericValidator.isBlankOrNull(getTxtSurname())) {
                    actionErrors.add("txtSurname", new ActionMessage("errors.required",
                            getFieldName("page.party.label.surname")));
                }
                if (GenericValidator.isBlankOrNull(getTxtFirstName())) {
                    actionErrors.add("txtFirstName", new ActionMessage("errors.required",
                            getFieldName("page.party.label.firstname")));
                }
                return actionErrors;
            }
        }
        return super.validate(actionMapping, httpServletRequest);
    }

    private ActionErrors validateBanking(ActionMessages actionMessages) {

        ActionErrors actionErrors = new ActionErrors();
        Collection<BankDetailVO> bankList = this.getBankDetailList();
        if (bankList == null || bankList.size() == 0) {
            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.bank.payerPayeeRoleNonExist"));
        } else {
            if (GenericValidator.isBlankOrNull(getKey())) {
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("message.bank.payerPayeeRoleNonExist"));
            } else {
                if (GenericValidator.isBlankOrNull(getAccountHolder())) {
                    actionErrors.add("accountHolder", new ActionMessage("errors.required",
                            getFieldName("page.party.bank.label.cardholdername")));
                }
                if (GenericValidator.isBlankOrNull(getCrCardExpiryDate())) {
                    actionErrors.add("crExpirtyDate", new ActionMessage("errors.required",
                            getFieldName("page.party.bank.label.cardexpirydate")));
                }

                if (GenericValidator.isBlankOrNull(getAccountNumber())) {
                    actionErrors.add("accountNumber", new ActionMessage("errors.required",
                            getFieldName("page.party.bank.label.cardnumber")));
                } else if (!GenericValidator.isLong(getAccountNumber().trim())) {
                    actionErrors.add("accountNumber", new ActionMessage("message.invalid.number.value",
                            getFieldName("page.party.bank.label.cardnumber")));
                } else if (!GenericValidator.maxLength(getAccountNumber().trim(), ACCOUNT_NUMBER_LENGTH)) {
                    actionErrors.add("accountNumber", new ActionMessage("errors.maxlength",
                            getFieldName("page.party.bank.label.cardnumber"), Integer.toString(ACCOUNT_NUMBER_LENGTH)));
                }
            }
        }
        this.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);

        return actionErrors;
    }

    /**
     * Validate the update of an organisation
     */
    private ActionErrors validateUpdateOrganisation() {
        if (GenericValidator.isBlankOrNull(txtFullName)) {
            ActionErrors actionErrors = new ActionErrors();
            actionErrors.add("txtFullName", new ActionMessage("errors.required",
                    getFieldName("page.party.label.organisationname")));
            return actionErrors;
        }
        return null;
    }

    public Long getEducationRegSelected() {
        return educationRegSelected;
    }

    public void setEducationRegSelected(Long educationRegSelected) {
        this.educationRegSelected = educationRegSelected;
    }

    public String getTxtMRMaritalStatusDate() {
        return txtMRMaritalStatusDate;
    }

    public void setTxtMRMaritalStatusDate(String txtMRMaritalStatusDate) {
        this.txtMRMaritalStatusDate = txtMRMaritalStatusDate;
    }

    public String getTxtMRRegistrationNumber() {
        return txtMRRegistrationNumber;
    }

    public void setTxtMRRegistrationNumber(String txtMRRegistrationNumber) {
        this.txtMRRegistrationNumber = txtMRRegistrationNumber;
    }

    public String getTxtMRRegisteringAuthority() {
        return txtMRRegisteringAuthority;
    }

    public void setTxtMRRegisteringAuthority(String txtMRRegisteringAuthority) {
        this.txtMRRegisteringAuthority = txtMRRegisteringAuthority;
    }

    public String getTxtMRIssueDate() {
        return txtMRIssueDate;
    }

    public void setTxtMRIssueDate(String txtMRIssueDate) {
        this.txtMRIssueDate = txtMRIssueDate;
    }

    public String getSelNationalRegCountryName() {
        return selNationalRegCountryName;
    }

    public void setSelNationalRegCountryName(String selNationalRegCountryName) {
        this.selNationalRegCountryName = selNationalRegCountryName;
    }

    public String getTxtNRRegisteringAuthority() {
        return txtNRRegisteringAuthority;
    }

    public void setTxtNRRegisteringAuthority(String txtNRRegisteringAuthority) {
        this.txtNRRegisteringAuthority = txtNRRegisteringAuthority;
    }

    public String getTxtNRIssueDate() {
        return txtNRIssueDate;
    }

    public void setTxtNRIssueDate(String txtNRIssueDate) {
        this.txtNRIssueDate = txtNRIssueDate;
    }

    public Long getSelAccreditationTypeId() {
        return selAccreditationTypeId;
    }

    public void setSelAccreditationTypeId(Long selAccreditationType) {
        this.selAccreditationTypeId = selAccreditationType;
    }

    public String getTxtAccreditationRegistrationIssueDate() {
        return txtAccreditationRegistrationIssueDate;
    }

    public void setTxtAccreditationRegistrationIssueDate(String txtAccreditationRegistrationIssueDate) {
        this.txtAccreditationRegistrationIssueDate = txtAccreditationRegistrationIssueDate;
    }

    public String getTxtAccreditationRegistrationStartDate() {
        return txtAccreditationRegistrationStartDate;
    }

    public void setTxtAccreditationRegistrationStartDate(String txtAccreditationRegistrationStartDate) {
        this.txtAccreditationRegistrationStartDate = txtAccreditationRegistrationStartDate;
    }

    public String getTxtAccreditationRegistrationEndDate() {
        return txtAccreditationRegistrationEndDate;
    }

    public void setTxtAccreditationRegistrationEndDate(String txtAccreditationRegistrationEndDate) {
        this.txtAccreditationRegistrationEndDate = txtAccreditationRegistrationEndDate;
    }

    public String getTxtPartyRegistrationDescription() {
        return txtPartyRegistrationDescription;
    }

    public void setTxtPartyRegistrationDescription(String txtPartyRegistrationDescription) {
        this.txtPartyRegistrationDescription = txtPartyRegistrationDescription;
    }

    public String getTxtPartyRegistrationIssueDate() {
        return txtPartyRegistrationIssueDate;
    }

    public void setTxtPartyRegistrationIssueDate(String txtPartyRegistrationIssueDate) {
        this.txtPartyRegistrationIssueDate = txtPartyRegistrationIssueDate;
    }

    public String getTxtPartyRegistrationStartDate() {
        return txtPartyRegistrationStartDate;
    }

    public void setTxtPartyRegistrationStartDate(String txtPartyRegistrationStartDate) {
        this.txtPartyRegistrationStartDate = txtPartyRegistrationStartDate;
    }

    public String getTxtPartyRegistrationEndDate() {
        return txtPartyRegistrationEndDate;
    }

    public void setTxtPartyRegistrationEndDate(String txtPartyRegistrationEndDate) {
        this.txtPartyRegistrationEndDate = txtPartyRegistrationEndDate;
    }

    public String getTxtPartyRelationshipFromDescription() {
        return txtPartyRelationshipFromDescription;
    }

    public void setTxtPartyRelationshipFromDescription(String txtPartyRelationshipFromDescription) {
        this.txtPartyRelationshipFromDescription = txtPartyRelationshipFromDescription;
    }

    public String getTxtPartyRelationshipFromStartDate() {
        return txtPartyRelationshipFromStartDate;
    }

    public void setTxtPartyRelationshipFromStartDate(String txtPartyRelationshipFromStartDate) {
        this.txtPartyRelationshipFromStartDate = txtPartyRelationshipFromStartDate;
    }

    public String getTxtPartyRelationshipFromEndDate() {
        return txtPartyRelationshipFromEndDate;
    }

    public void setTxtPartyRelationshipFromEndDate(String txtPartyRelationshipFromEndDate) {
        this.txtPartyRelationshipFromEndDate = txtPartyRelationshipFromEndDate;
    }

    public Long getSelRelationshipTypeTo() {
        return selRelationshipTypeTo;
    }

    public void setSelRelationshipTypeTo(Long selRelationshipTypeTo) {
        this.selRelationshipTypeTo = selRelationshipTypeTo;
    }

    public Long getSelRelationshipTypeForEditTo() {
        return selRelationshipTypeForEditTo;
    }

    public void setSelRelationshipTypeForEditTo(Long selRelationshipTypeForEditTo) {
        this.selRelationshipTypeForEditTo = selRelationshipTypeForEditTo;
    }

    public Long getRelFromSelected() {
        return relFromSelected;
    }

    public void setRelFromSelected(Long relFromSelected) {
        this.relFromSelected = relFromSelected;
    }

    public String getTxtPartyRelationshipToDescription() {
        return txtPartyRelationshipToDescription;
    }

    public void setTxtPartyRelationshipToDescription(String txtPartyRelationshipToDescription) {
        this.txtPartyRelationshipToDescription = txtPartyRelationshipToDescription;
    }

    public String getTxtPartyRelationshipToStartDate() {
        return txtPartyRelationshipToStartDate;
    }

    public void setTxtPartyRelationshipToStartDate(String txtPartyRelationshipToStartDate) {
        this.txtPartyRelationshipToStartDate = txtPartyRelationshipToStartDate;
    }

    public String getTxtPartyRelationshipToEndDate() {
        return txtPartyRelationshipToEndDate;
    }

    public void setTxtPartyRelationshipToEndDate(String txtPartyRelationshipToEndDate) {
        this.txtPartyRelationshipToEndDate = txtPartyRelationshipToEndDate;
    }

    public String getTxtPartyRelationshipToObjRef() {
        return txtPartyRelationshipToObjRef;
    }

    public void setTxtPartyRelationshipToObjRef(String txtPartyRelationshipToObjRef) {
        this.txtPartyRelationshipToObjRef = txtPartyRelationshipToObjRef;
    }

    public String getTxtPartyRelationshipFromObjRef() {
        return txtPartyRelationshipFromObjRef;
    }

    public void setTxtPartyRelationshipFromObjRef(String txtPartyRelationshipFromObjRef) {
        this.txtPartyRelationshipFromObjRef = txtPartyRelationshipFromObjRef;
    }

    public String getTxtPartyRelationshipRelatedFrom() {
        return txtPartyRelationshipRelatedFrom;
    }

    public void setTxtPartyRelationshipRelatedFrom(String txtPartyRelationshipRelatedFrom) {
        this.txtPartyRelationshipRelatedFrom = txtPartyRelationshipRelatedFrom;
    }

    public PartyName getDefaultPrtyname() {
        return defaultPrtyname;
    }

    public void setDefaultPrtyname(PartyName defaultPrtyname) {
        this.defaultPrtyname = defaultPrtyname;
    }

    public Set<PartyName> getPartyNames() {
        return partyNames;
    }

    public void setPartyNames(Set<PartyName> prtyNames) {
        this.partyNames = prtyNames;
    }

    public Set<PartyRole> getPartyRolesForParty() {
        return partyRolesForParty;
    }

    public void setPartyRolesForParty(Set<PartyRole> partyRolesForParty) {
        this.partyRolesForParty = partyRolesForParty;
    }

    public RolePlayer getRolePlayerForCP() {
        return rolePlayerForCP;
    }

    public void setRolePlayerForCP(RolePlayer rolePlayerForCP) {
        this.rolePlayerForCP = rolePlayerForCP;
    }

    public Collection<LabelValueBean> getRolesForCpsList() {
        return rolesForCpsList;
    }

    public void setRolesForCpsList(Collection<LabelValueBean> rolesForCpsList) {
        this.rolesForCpsList = rolesForCpsList;
    }

    public Set<ContactPreference> getContactPrefsForParty() {
        return contactPrefsForParty;
    }

    public void setContactPrefsForParty(Set<ContactPreference> contactPrefsForParty) {
        this.contactPrefsForParty = contactPrefsForParty;
    }

    public Set<PartyRegistration> getPartyRegistrations() {
        return partyRegistrations;
    }

    public void setPartyRegistrations(Set<PartyRegistration> partyRegistrations) {
        this.partyRegistrations = partyRegistrations;
    }

    public SortedMap<Long, PartyName> getPartyNamesMap() {
        return partyNamesMap;
    }

    public void setPartyNamesMap(SortedMap<Long, PartyName> partyNamesMap) {
        this.partyNamesMap = partyNamesMap;
    }

    public SortedMap<Long, PartyRelationshipVO> getPartyRelationshipsFromMap() {
        return partyRelationshipsFromMap;
    }

    public void setPartyRelationshipsFromMap(
            SortedMap<Long, PartyRelationshipVO> partyRelationshipsFromMap) {
        this.partyRelationshipsFromMap = partyRelationshipsFromMap;
    }

    public SortedMap<Long, PartyRelationshipVO> getPartyRelationshipsToMap() {
        return partyRelationshipsToMap;
    }

    public void setPartyRelationshipsToMap(SortedMap<Long, PartyRelationshipVO> partyRelationshipsToMap) {
        this.partyRelationshipsToMap = partyRelationshipsToMap;
    }

    public SortedMap<PartyRolesDetailVO, String> getPartyRoleAgmtsMap() {
        return partyRoleAgmtsMap;
    }

    public void setPartyRoleAgmtsMap(SortedMap<PartyRolesDetailVO, String> partyRoleAgmtsMap) {
        this.partyRoleAgmtsMap = partyRoleAgmtsMap;
    }

    public SortedMap<PartyRolesDetailVO, String> getPartyRoleLossEventsMap() {
        return partyRoleLossEventsMap;
    }

    public void setPartyRoleLossEventsMap(SortedMap<PartyRolesDetailVO, String> partyRoleLossEventsMap) {
        this.partyRoleLossEventsMap = partyRoleLossEventsMap;
    }

    public SortedMap<PartyRolesDetailVO, String> getPartyRoleClaimsMap() {
        return partyRoleClaimsMap;
    }

    public void setPartyRoleClaimsMap(SortedMap<PartyRolesDetailVO, String> partyRoleClaimsMap) {
        this.partyRoleClaimsMap = partyRoleClaimsMap;
    }

    public SortedMap<PartyRolesDetailVO, String> getPartyRoleRelationshipsMap() {
        return partyRoleRelationshipsMap;
    }

    public void setPartyRoleRelationshipsMap(SortedMap<PartyRolesDetailVO, String> partyRoleRelationshipsMap) {
        this.partyRoleRelationshipsMap = partyRoleRelationshipsMap;
    }

    public SortedMap<PartyRolesDetailVO, String> getPartyRoleNonLinkingMap() {
        return partyRoleNonLinkingMap;
    }

    public void setPartyRoleNonLinkingMap(SortedMap<PartyRolesDetailVO, String> partyRoleNonLinkingMap) {
        this.partyRoleNonLinkingMap = partyRoleNonLinkingMap;
    }

    public SortedMap<PartyRolePartyDetailVO, String> getPartyRolePartiesMap() { return partyRolePartiesMap; }

    public void setPartyRolePartiesMap(SortedMap<PartyRolePartyDetailVO, String> partyRolePartiesMap) {
        this.partyRolePartiesMap = partyRolePartiesMap;
    }

    public Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> getPrefsPointsMap() {
        return prefsPointsMap;
    }

    public void setPrefsPointsMap(
            Map<ContactPreference, SortedMap<String, SortedMap<Long, ContactPoint>>> prefsPointsMap) {
        this.prefsPointsMap = prefsPointsMap;
    }

    public int getContactPointSize() {
        return contactPointSize;
    }

    public void setContactPointSize(int contactPointSize) {
        this.contactPointSize = contactPointSize;
    }

    public Collection<BankDetailVO> getBankDetailList() {
        return bankDetailList;
    }

    public void setBankDetailList(Collection<BankDetailVO> bankDetailList) {
        this.bankDetailList = bankDetailList;
    }

    public Map<String, Map<String, String>> getBankPartyRolesAgmtsDisplayMap() {
        return bankPartyRolesAgmtsDisplayMap;
    }

    public void setBankPartyRolesAgmtsDisplayMap(Map<String, Map<String, String>> bankPartyRolesAgmtsDisplayMap) {
        this.bankPartyRolesAgmtsDisplayMap = bankPartyRolesAgmtsDisplayMap;
    }

    public SortedMap<Long, BirthCertificate> getBirthRegDisplayMap() {
        return birthRegDisplayMap;
    }

    public void setBirthRegDisplayMap(SortedMap<Long, BirthCertificate> birthRegDisplayMap) {
        this.birthRegDisplayMap = birthRegDisplayMap;
    }

    public SortedMap<Long, DeathCertificate> getDeathRegDisplayMap() {
        return deathRegDisplayMap;
    }

    public void setDeathRegDisplayMap(SortedMap<Long, DeathCertificate> deathRegDisplayMap) {
        this.deathRegDisplayMap = deathRegDisplayMap;
    }

    public SortedMap<Long, DriversLicence> getLicenceRegsDisplayMap() {
        return licenceRegsDisplayMap;
    }

    public void setLicenceRegsDisplayMap(SortedMap<Long, DriversLicence> licenceRegsDisplayMap) {
        this.licenceRegsDisplayMap = licenceRegsDisplayMap;
    }

    public SortedMap<Long, PartyRegistration> getEducationRegsDisplayMap() {
        return educationRegsDisplayMap;
    }

    public void setEducationRegsDisplayMap(SortedMap<Long, PartyRegistration> educationRegsDisplayMap) {
        this.educationRegsDisplayMap = educationRegsDisplayMap;
    }

    public SortedMap<Long, PartyRegistration> getMarriageRegsDisplayMap() {
        return marriageRegsDisplayMap;
    }

    public void setMarriageRegsDisplayMap(SortedMap<Long, PartyRegistration> marriageRegsDisplayMap) {
        this.marriageRegsDisplayMap = marriageRegsDisplayMap;
    }

    public SortedMap<Long, NationalRegistration> getNationalRegPersonDisplayMap() {
        return nationalRegPersonDisplayMap;
    }

    public void setNationalRegPersonDisplayMap(SortedMap<Long, NationalRegistration> nationalRegPersonDisplayMap) {
        this.nationalRegPersonDisplayMap = nationalRegPersonDisplayMap;
    }

    public SortedMap<Long, Accreditation> getAccreditationRegsDisplayMap() {
        return accreditationRegsDisplayMap;
    }

    public void setAccreditationRegsDisplayMap(SortedMap<Long, Accreditation> accreditationRegsDisplayMap) {
        this.accreditationRegsDisplayMap = accreditationRegsDisplayMap;
    }

    public SortedMap<Long, PartyRegistration> getPartyRegsDisplayMap() {
        return partyRegsDisplayMap;
    }

    public void setPartyRegsDisplayMap(SortedMap<Long, PartyRegistration> partyRegsDisplayMap) {
        this.partyRegsDisplayMap = partyRegsDisplayMap;
    }

    public Integer getPersonSearchResultSize() {
        if (getPersonList() == null) {
            return 0;
        }
        return getPersonList().size();
    }

    public List<PartySearchResultVO> getEmailListSearchContacts() {
        return emailListSearchContacts;
    }

    public void setEmailListSearchContacts(List<PartySearchResultVO> emailListSearchContacts) {
        this.emailListSearchContacts = emailListSearchContacts;
    }

    public List<PartySearchResultVO> getPhysicalListSearchContacts() {
        return physicalListSearchContacts;
    }

    public void setPhysicalListSearchContacts(List<PartySearchResultVO> physicalListSearchContacts) {
        this.physicalListSearchContacts = physicalListSearchContacts;
    }

    public List<PartySearchResultVO> getPostalListSearchContacts() {
        return postalListSearchContacts;
    }

    public void setPostalListSearchContacts(List<PartySearchResultVO> postalListSearchContacts) {
        this.postalListSearchContacts = postalListSearchContacts;
    }

    public void setNumberListSearchContacts(List<PartySearchResultVO> numberListSearchContacts) {
        this.numberListSearchContacts = numberListSearchContacts;
    }

    public List<PartySearchResultVO> getFinancialAccountDetailsListSearchContacts() {
        return financialAccountDetailsListSearchContacts;
    }

    public void setFinancialAccountDetailsListSearchContacts(
            List<PartySearchResultVO> financialAccounDetailsListSearchContacts) {
        this.financialAccountDetailsListSearchContacts = financialAccounDetailsListSearchContacts;
    }

    public Collection<BankDetailVO> getSearchedBankDetailList() {
        return searchedBankDetailList;
    }

    public void setSearchedBankDetailList(Collection<BankDetailVO> searchedbankDetailList) {
        this.searchedBankDetailList = searchedbankDetailList;
    }

    public boolean isNewParty() {
        return newParty;
    }

    public void setNewParty(boolean newParty) {
        this.newParty = newParty;
    }

    public String getTxtTelephoneElectronicType() {
        return txtTelephoneElectronicType;
    }

    public void setTxtTelephoneElectronicType(String txtTelephoneElectronicType) {
        this.txtTelephoneElectronicType = txtTelephoneElectronicType;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Long accountTypeId) {
        this.accountTypeId = accountTypeId;
    }
// ------------------------------------------- Reset of search displays -------------------------------------------

    /**
     * @return the sharedContact
     */
    public boolean isSharedContact() {
        return sharedContact;
    }

    /**
     * @param sharedContact
     *            the sharedContact to set
     */
    public void setSharedContact(boolean sharedContact) {
        this.sharedContact = sharedContact;
    }

    public void resetForBankingDetailsDisplay() {
        resetBanking();
        resetContactPointSearchResults();
    }

    public void resetBankingForAdd() {
        bankContactPreferenceType = null;
        sharedContact = false;
        def = false;
        bankRoleType = null;
        bankContactPreferenceType = null;
        accountNumber = null;
        accountHolder = null;
        accountType = null;
        crCardExpiryDate = null;
        effectiveFromDate = null;
        effectiveToDate = null;
        accountId=null;
        accountTypeId = null;
        bank = null;
        bankBranchReference = null;
        bankBranchCode = null;
        bankBranchName = null;
        getSearchedBankDetailList().clear();
    }

    public void resetBanking() {
        key = null;
        fadSelected = null;
        bankContactPreferenceType = null;
        sharedContact = false;
        def = false;
        bankRoleType = null;
        bankContactPreferenceType = null;
        accountNumber = null;
        accountHolder = null;
        accountType = null;
        crCardExpiryDate = null;
        effectiveFromDate = null;
        effectiveToDate = null;
        bank = null;
        bankBranchReference = null;
        bankBranchCode = null;
        bankBranchName = null;
        getSearchedBankDetailList().clear();
    }

    public void resetAfterLinkingContactPoints() {
        // TODO: Need to check what else to clear here.
        resetContactPointSearchResults();
    }

    /**
     * Reset any searches made for Contact Points
     */
    private void resetContactPointSearchResults() {
        getEmailListSearchContacts().clear();
        getPhysicalListSearchContacts().clear();
        getPostalListSearchContacts().clear();
        getFinancialAccountDetailsListSearchContacts().clear();

    }

    public List<LabelValueBean> getRelationshipTypes() {
        return relationshipTypes;
    }

    public void setRelationshipTypes(List<LabelValueBean> relationshipTypes) {
        this.relationshipTypes = relationshipTypes;
    }

    private String getFieldName(String key) {
        return getTypeFormatter().formatMessage(key);
    }

    public void resetContactPointFields() {
        // TODO why not null?
        setTxtLandLineAreaCode("");
        setTxtLandLineCountryCode("");
        setTxtLandLineLocalNumber("");
        setTxtLandLineExt("");
        setTxtStartDateNumber("");
        setTxtEndDateNumber("");
        setTxtTelephoneElectronicType(null);

        setTxtEmail("");
        setTxtStartDateEmail("");
        setTxtEndDateEmail("");

        setTxtUnitNumber("");
        setTxtFloorNumber("");
        setTxtBuildingName("");
        setTxtHouseNumber("");
        setTxtStreet("");
        setTxtPhysicalAddressCity("");
        setTxtPhysicalAddressSubRegion("");
        setTxtPhysicalAddressRegion("");
        setSelPhysicalAddressCountryName("");
        setTxtPhysicalAddressPostalCode("");
        setTxtStartDatePhysicalAddress("");
        setTxtEndDatePhysicalAddress("");

        setTxtBoxNumber("");
        setTxtPostnetSuite("");
        setTxtPostalAddressSubRegion("");
        setTxtPostalAddressCity("");
        setTxtPostalAddressRegion("");
        setTxtPostalAddressPostalCode("");

        setSelUnstructuredAddressCountryName("");
        setTxtAddressLine1("");
        setTxtAddressLine2("");
        setTxtAddressLine3("");
        setTxtAddressLine4("");
        setTxtUnstructuredAddressCity("");
        setTxtUnstructuredAddressSubRegion("");
        setTxtUnstructuredSubRegion("");
        setTxtUnstructuredAddressRegion("");
        setTxtUnstructuredAddressPostalCode("");
        setUnstructuredCodeStore("");
        setTxtStartDateUnstructuredAddress("");
        setTxtEndDateUnstructuredAddress("");
    }

    public SortedMap<ExternalPartyRoleDetailVO, String> getExternalPartyRolesMap() { return externalPartyRolesMap; }

    public void setExternalPartyRolesMap(SortedMap<ExternalPartyRoleDetailVO, String> externalPartyRolesMap) {
        this.externalPartyRolesMap = externalPartyRolesMap;
    }
}
