/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.silvermoongroup.account.criteria.AccountSearchCriteria;
import com.silvermoongroup.account.domain.AccountMapping;
import com.silvermoongroup.account.domain.intf.IAccount;
import com.silvermoongroup.account.domain.intf.IAccountMapping;
import com.silvermoongroup.businessservice.policymanagement.dto.*;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.beans.BeanFactory;
import com.silvermoongroup.common.constants.Components;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.Period;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.InternalCompanyCodeEnumeration;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.common.enumeration.MeansOfPayment;
import com.silvermoongroup.common.ext.enumeration.MeansOfPaymentExtEnum;
import com.silvermoongroup.common.ext.enumeration.TypeReference;
import com.silvermoongroup.fsa.web.common.callback.CallBack;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.common.constant.CallbackConstants;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.vo.BankDetailVO;
import com.silvermoongroup.fsa.web.party.vo.PartyRolesDetailVO;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.FinancialAccountDetailSearchCriteria;
import com.silvermoongroup.party.criteria.PartySearchCriteria;
import com.silvermoongroup.party.criteria.PersonSearchCriteria;
import com.silvermoongroup.party.domain.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.*;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.PropertyMessageResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * The PartyAction - essentially the entire party GUI.
 */
public class PartyAction extends AbstractPartyAction {

    private static final GeneralActionHelper generalActionHelper = new GeneralActionHelper();
    private static final PartyRolesActionHelper partyRolesActionHelper = new PartyRolesActionHelper();
    private static final PartyNamesActionHelper partyNamesActionHelper = new PartyNamesActionHelper();
    private static final PartyRelationshipActionHelper partyRelationshipActionHelper = new PartyRelationshipActionHelper();

    private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Type organisationType;

    public PartyAction() {
    }

    private static GeneralActionHelper getGeneralActionHelper() {
        return generalActionHelper;
    }

    private static PartyRolesActionHelper getPartyRolesActionHelper() {
        return partyRolesActionHelper;
    }

    private static PartyRelationshipActionHelper getPartyRelationshipActionHelper() {
        return partyRelationshipActionHelper;
    }

    private static PartyNamesActionHelper getPartyNamesActionHelper() {
        return partyNamesActionHelper;
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        // map.put ("key-in-message-resources", "name-of-method-to-invoke")
        map.put("button.back", "back");
        map.put("button.cancel", "cancel");
        map.put("button.save", "save");
        map.put("page.party.action.link", "link");

        map.put("page.party.generalparty.action.updategeneral", "updateGeneralParty");

        map.put("page.party.generalparty.action.addpersonname", "addPersonName");
        map.put("page.party.generalparty.action.updatepersonname", "updatePersonName");
        map.put("page.party.generalparty.action.updateorganisation", "updateOrganisation");

        map.put("page.party.contactpoints.action.addPreference", "addPreference");
        map.put("page.party.contactpoints.action.addContact", "addContact");
        map.put("page.party.contactpoints.action.updateContact", "updateContact");
        map.put("page.party.contactpoints.action.updatePreference", "updatePreference");
        map.put("page.party.registrations.action.addregistration", "addRegistration");
        map.put("page.party.relationships.action.addrelationship", "addRelationship");
        map.put("page.party.registrations.action.updateregistration", "updateRegistration");
        map.put("page.party.relationships.action.updaterelationship", "updateRelationship");
        map.put("page.party.partyroles.label.selectparty", "partyRelationships");

        map.put("page.party.capturebankdetails.action.updateFinancialAccountDetails",
                "updateFinancialAccountDetails");
        map.put("page.party.capturebankdetails.action.addFinancialAccountDetails",
                "addFinancialAccountDetails");

        return map;
    }

    // ------------------------------------ START: Entry points from the GUI ----------------------------------------

    /**
     * The default entry point - The form should be loaded with party. If there is no party redirect to find party
     * screen
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return createRedirect((PartyForm) actionForm, actionMapping.findForward(FORWARD_FIND_PARTY));
    }

    /**
     * Internal Entry point: We wish to display a party and have provided an object reference.
     */
    public ActionForward displayParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));
        // if delegating setup, establish party.
        if (partyForm.getIsDelegating() && partyForm.getPartyObjectRef().contains(":null")){
           Party party = findOrEstablishDelegatingParty(partyForm);
           partyForm.setPartyObjectRef(party.getObjectReference().toString());
        }
        loadGeneralTab(httpServletRequest, partyForm);

        // we use a different (non session scoped) parameter here, as there is ALWAYS going to be a session scoped
        // parameter
        // which may not reflect our intent
        String tabRequested = httpServletRequest.getParameter("t");
        if (tabRequested != null) {
            try {
                partyForm.setTabToChangeTo(Integer.valueOf(tabRequested));
            } catch (NumberFormatException ex) {
                partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
            }
        }

        return tabChange(actionMapping, partyForm, httpServletRequest, httpServletResponse);
    }

    /**
     * Internal Entry point: A party is selected from the search results
     */
    public ActionForward selectPartySearchResult(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));
        // if delegating setup, establish party.
        if (partyForm.getIsDelegating()){
            Party party = findOrEstablishDelegatingParty(partyForm);
            partyForm.setSelectedPartyObjectRef(party.getObjectReference().toString());
        }

        // handle the selection of a relationship role selection
        if (partyForm.isRelationshipRole()) {
            return linkRelationshipRoles(partyForm, actionMapping, httpServletRequest);
        }

        ObjectReference selectedPartyObjectRef = ObjectReference.convertFromString(partyForm
                .getSelectedPartyObjectRef());

        partyForm.resetPartyInfo();
        partyForm.resetPartySearchInfo();
        partyForm.reset();

        if (partyForm.isEmbeddedMode()) {
            String roleKind = partyForm.getRoleKindToLink();

            if (getPartyGuiUtility().shouldPartyRoleBeCreated(getApplicationContext(), getProductDevelopmentService()
                    , httpServletRequest, roleKind)) {
                PartyRole partyRole = getPartyGuiUtility().createPartyRoleForParty(getApplicationContext(),
                        getCustomerRelationshipService(), selectedPartyObjectRef, getTypeForName(roleKind), null);
                partyForm.setRoleInContext(partyRole);
            }

            if (shouldPartyBeImmediatelyLinked(partyForm, selectedPartyObjectRef)) {

                ObjectReference toSendBack = (partyForm.getRoleInContext() != null ? partyForm.getRoleInContext()
                        .getObjectReference() : selectedPartyObjectRef);

                CallBack callBack = CallBackUtility.getCallBack(httpServletRequest, httpServletResponse);
                CallBackUtility.setAttribute(httpServletRequest, toSendBack, callBack);
                partyForm.setOrganisationList(new ArrayList<>());

                return CallBackUtility.getForwardAction(callBack);
            }
        }

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                selectedPartyObjectRef);
    }

    /**
     * External Entry Point: An party role is selected from an external GUI. We need to display the 'root' party and the
     * party role information
     */
    public ActionForward displayPartyFromExternal(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.resetForDisplayFromExternal();
        partyForm.setEmbeddedMode(true);

        String rolePlayerObjectReference = httpServletRequest.getParameter("rolePlayerObjectReference");
        Assert.notNull(rolePlayerObjectReference, "Parameter rolePlayerObjectReference is required");

        partyForm.setRolePlayerObjectRef(rolePlayerObjectReference);

        ObjectReference rolePlayerObjectRef = partyForm.getRolePlayerObjectRefAsObjectReference();
        loadGeneralTab(httpServletRequest, partyForm, rolePlayerObjectRef);

        // we need to work out whether we have selected a party role or a party
        if (rolePlayerObjectRef.equals(partyForm.getPartyObjectRefAsObjectReference())) {
            // the role player that we clicked on in the agreement screen is a person/organisation
            partyForm.setRoleInContext(null);
        } else {
            // we selected a party role from the the agreement/request/etc screen
            PartyRole partyRole = getCustomerRelationshipService().getPartyRole(getApplicationContext(),
                    rolePlayerObjectRef);
            partyForm.setRoleInContext(partyRole);
        }

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getParty().getObjectReference());
    }

    /**
     * Entry Point: When the "Update General Party" button is clicked
     */
    public ActionForward updateGeneralParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        PartyForm partyForm = (PartyForm) actionForm;
        Party party = partyForm.getParty();

        Person person = (Person) partyForm.getParty();
        getGeneralActionHelper().populateGeneralPersonValuesFromForm(person, partyForm,
                getCustomerRelationshipService(), getProductDevelopmentService(), getTypeParser());
        getCustomerRelationshipService().modifyPerson(getApplicationContext(), (Person) party);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "page.party.message.updateGeneralPersonSuccessful"));

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getParty().getObjectReference());
    }

    /**
     * Entry Point: The "Update Name" button is clicked when viewing a person
     */
    public ActionForward updatePersonName(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;

        Party party = partyForm.getParty();
        PersonName defaultPersonName = (PersonName) party.getDefaultName();

        if (partyForm.getPersonNameSelected() == null) {
            partyForm.setPersonNameSelected(defaultPersonName.getId());
        }

        Type selectedPersonNameType = getProductDevelopmentService().getType(getApplicationContext(), partyForm.getSelNameType());
        ObjectReference personNameToBeUpdateObjectRef = new ObjectReference(Components.PARTY,
                                                                            selectedPersonNameType.getId(),
                                                                            partyForm.getPersonNameSelected());

        PersonName personName = (PersonName) getCustomerRelationshipService().getPartyName(getApplicationContext(),
                personNameToBeUpdateObjectRef);
        personName = getPartyNamesActionHelper().setPersonNameValues(partyForm, personName, getTypeParser(),
                                                                     getProductDevelopmentService());

        boolean isDefaultPartyName = partyForm.isDefaultPartyName();
        partyForm.setDefaultPartyName(false);
        getCustomerRelationshipService().modifyPartyName(getApplicationContext(), personName, isDefaultPartyName);

        personName = (PersonName) getCustomerRelationshipService().getPartyName(getApplicationContext(),
                personNameToBeUpdateObjectRef);

        if (personName.getTypeId().equals(CoreTypeReference.BIRTHNAME.getValue())) {
            new RegistrationActionHelper().editBirthRegBirthName(partyForm, personName,
                    getCustomerRelationshipService());
        }

        ActionMessages actionMessages = new ActionMessages();
        actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "page.party.message.partyNameEditSuccessful"));

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getParty().getObjectReference());
    }

    /**
     * Entry point - The "Add Name" button is clicked when editing a person
     */
    public ActionForward addPersonName(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();

        int activeMarriedNameCount = 0;
        Set<PartyName> partyNames = partyForm.getPartyNames();
        for (PartyName name : partyNames) {
            if (partyForm.getSelNameType().equalsIgnoreCase(CoreTypeReference.BIRTHNAME.getName())) {
                if (name.getTypeId().equals(CoreTypeReference.BIRTHNAME.getValue())) {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.birthName"));
                    saveInformationMessages(httpServletRequest, messages);
                    partyForm.setSelRoleType(PartyGuiHttpConstants.SELECT);
                    return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
                }
            }
            if (partyForm.getSelNameType().equalsIgnoreCase(CoreTypeReference.PREFERREDNAME.getName())) {
                if (name.getTypeId().equals(CoreTypeReference.PREFERREDNAME.getValue())) {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.preferredName"));
                    saveInformationMessages(httpServletRequest, messages);
                    partyForm.setSelRoleType(PartyGuiHttpConstants.SELECT);
                    return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
                }
            }
            if (partyForm.getSelNameType().equalsIgnoreCase(CoreTypeReference.MAIDENNAME.getName())) {
                if (name.getTypeId().equals(CoreTypeReference.MAIDENNAME.getValue())) {
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.maidenName"));
                    saveInformationMessages(httpServletRequest, messages);
                    partyForm.setSelRoleType(PartyGuiHttpConstants.SELECT);
                    return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
                }
            }
            if (partyForm.getSelNameType().equalsIgnoreCase(CoreTypeReference.MARRIEDNAME.getName())) {
                if (name.getTypeId().equals(CoreTypeReference.MARRIEDNAME.getValue())) {
                    // cannot have more than 1 active married name
                    if (name.getEffectiveTo() == null) {
                        activeMarriedNameCount++;
                    }
                }
            }
        }
        if (activeMarriedNameCount >= 1) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.marriageNameActive"));
            saveInformationMessages(httpServletRequest, messages);
            partyForm.setSelRoleType(PartyGuiHttpConstants.SELECT);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }
        PersonName personName = getPartyNamesActionHelper().setPersonNameValues(partyForm, new PersonName(),
                getTypeParser(), getProductDevelopmentService());

        // TODO this is not being set correctly
        boolean useAsDefaultName = partyForm.isDefaultName();
        partyForm.setDefaultName(false); // reset field
        PartyName newPartyName = getCustomerRelationshipService().establishName(getApplicationContext(), personName,
                partyForm.getPartyObjectRefAsObjectReference(), useAsDefaultName);

        if (personName.getTypeId().equals(CoreTypeReference.BIRTHNAME.getValue())) {
            RegistrationActionHelper iRegistrationActionHelper = new RegistrationActionHelper();
            iRegistrationActionHelper.editBirthRegBirthName(partyForm, newPartyName, getCustomerRelationshipService());
        }

        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.partyNameAddSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getPartyObjectRef());
    }

    /**
     * Entry point - The 'Update Organisation' button is clicked
     */
    public ActionForward updateOrganisation(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        PartyForm partyForm = (PartyForm) actionForm;

        // the only thing we are updating are names
        getPartyNamesActionHelper().updateOrganisationName(partyForm, getCustomerRelationshipService());

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "page.party.message.updateGeneralOrganisationSuccessful"));

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getParty().getObjectReference());
    }

    /**
     * Entry Point: The user has needs to add a party from another GUI (e.g. claims, user admin)
     */
    public ActionForward addRolePlayerFromExternal(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setEmbeddedMode(true);
        Assert.notNull(partyForm.getRoleKindToLink(), "The role kind parameter is required");

        partyForm.resetPartyInfo();
        partyForm.reset();

        ActionMessages actionMessages = new ActionMessages();
        setupPartySelectionForRoleKind(partyForm, actionMessages);
        saveInformationMessages(httpServletRequest, actionMessages);

        return actionMapping.findForward(actionMapping.getInput());
    }

    /**
     * Entry Point: The user has clicked on the 'Add' button on the agreement screen and has supplied a role kind (which
     * we need to translate into a role type)
     */
    public ActionForward addRolePlayerFromAgreement(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setEmbeddedMode(true);
        String roleKindId = httpServletRequest.getParameter(CallbackConstants.ROLE_KIND);
        Assert.notNull(roleKindId, "The roleKind parameter must be specified");

        KindDTO kind = getKindById(Long.valueOf(roleKindId));
        partyForm.setRoleKindToLink(kind.getName());
        return addRolePlayerFromExternal(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * Entry Point: The selection of the type of the party role to link in party to party role relationship (1/2)
     */
    public ActionForward partyRelationships(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();

        Type relationshipType = getTypeForName(partyForm.getSelRelationshipRoleType());
        initialiseSearchFormForRelationshipType(partyForm, relationshipType);
        partyForm.setRelationshipRole(true);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);

        Locale locale = (Locale) httpServletRequest.getSession().getAttribute(
                "javax.servlet.jsp.jstl.fmt.locale.session");
        PropertyMessageResources p = (PropertyMessageResources) httpServletRequest.getAttribute(Globals.MESSAGES_KEY);

        String langRelationshipRoleType = null;
        if (p != null) {
            // Value for key errors.notempty
            langRelationshipRoleType = p.getMessage(locale, "common.type." + partyForm.getSelRelationshipRoleType());

        }

        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchRelatedParty",
                partyForm.getFullName(), langRelationshipRoleType));
        saveInformationMessages(httpServletRequest, messages);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    /**
     * Internal Entry point: Perform the linking to another (party to party relationship) (2/2)
     */
    private ActionForward linkRelationshipRoles(PartyForm partyForm, ActionMapping actionMapping,
            HttpServletRequest httpServletRequest) {

        // the party we are creating the party role on
        ObjectReference primaryPartyObjectRef = partyForm.getPartyObjectRefAsObjectReference();
        ObjectReference secondaryPartyObjectRef = ObjectReference.convertFromString(partyForm
                .getSelectedPartyObjectRef());
        Type relationshipType = getTypeForName(partyForm.getSelRelationshipRoleType());

        Assert.notNull(primaryPartyObjectRef);
        Assert.notNull(secondaryPartyObjectRef);
        Assert.notNull(relationshipType);

        if (primaryPartyObjectRef.equals(secondaryPartyObjectRef)) {

            ActionErrors actionErrors = new ActionErrors();
            actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.message.partyToPartyRelationshipTheSameparty", partyForm.getFullName()));

            partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
            saveErrorMessages(httpServletRequest, actionErrors);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_INPUT);
        }

        ApplicationContext applicationContext = getApplicationContext();
        Party secondaryParty = getCustomerRelationshipService().getPartyForPartyOrPartyRole(applicationContext,
                secondaryPartyObjectRef, new String[] { "partyRoles" });

        // check for cyclical links
        for (PartyRole secondaryPartyRole : secondaryParty.getPartyRoles()) {
            if (relationshipType.getId().equals(secondaryPartyRole.getTypeId())
                    && primaryPartyObjectRef.equals(secondaryPartyRole.getContext())) {

                ActionErrors actionErrors = new ActionErrors();
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                        "message.party.partyToPartyRelationshipCyclicalPartOf", partyForm.getFullName(), ""));

                partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
                saveErrorMessages(httpServletRequest, actionErrors);
                return actionMapping.findForward(PartyGuiHttpConstants.URI_INPUT);
            }
        }

        // check for duplicate roles (same type and context)
        Party primaryParty = getCustomerRelationshipService().getPartyForPartyOrPartyRole(applicationContext,
                primaryPartyObjectRef, new String[] { "partyRoles" });
        for (PartyRole primaryPartyRole : primaryParty.getPartyRoles()) {
            if (relationshipType.getId().equals(primaryPartyRole.getTypeId())
                    && secondaryPartyObjectRef.equals(primaryPartyRole.getContext())) {
                ActionErrors actionErrors = new ActionErrors();
                actionErrors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage(
                        "page.party.partyroles.message.duplicatepartyrole", relationshipType.getName()));

                partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
                saveErrorMessages(httpServletRequest, actionErrors);
                return actionMapping.findForward(PartyGuiHttpConstants.URI_INPUT);
            }
        }

        getPartyGuiUtility().createPartyRoleForParty(applicationContext, getCustomerRelationshipService(),
                primaryPartyObjectRef, relationshipType, secondaryPartyObjectRef);
        partyForm.setRolesTabLoaded(false);
        partyForm.resetPartyToPartyRelationshipFields();

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.relationshipRole.message.linked"));
        ActionRedirect redirect = createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY),
                PARAM_PARTY_OBJECT_REF, partyForm.getPartyObjectRef());
        redirect.addParameter("t", PartyGuiHttpConstants.ROLES_TAB); // change to the roles tabo
        return redirect;
    }

    // ------------------------------------ END: Entry points from the GUI ----------------------------------------

    /**
     * Set up the party GUI for the selection of a role player based on the kind
     */
    private void setupPartySelectionForRoleKind(PartyForm partyForm, ActionMessages actionMessages) {

        String roleKind = partyForm.getRoleKindToLink();
        if (roleKind.equals(CoreTypeReference.INSURER.getName())) {

            List<PartySearchResultVO> results = null;
            if (roleKind.equals(CoreTypeReference.INSURER.getName())) {
                results = getPartyGuiUtility().retrieveOrganisations(getApplicationContext(), getCustomerRelationshipService());
            }

            partyForm.setOrganisationList(results);
            partyForm.setPartyIsReadOnly(true);

            if (results.isEmpty()) {
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                        "page.party.message.searchnomatchfound"));
            } else {
                actionMessages.add(ActionMessages.GLOBAL_MESSAGE,
                        new ActionMessage("page.party.message.searchcomplete"));
            }

            partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
            partyForm.setSelPartyType(CoreTypeReference.ORGANISATION.getName());

        } else {
            getPartyRolesActionHelper().setGuiDefaultsForRolesLinking(roleKind, partyForm);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
            partyForm.setSelPartyType(CoreTypeReference.PERSON.getName());
        }

    }

    private void contactPointResultMessages(PartyForm form, ActionMessages messages, boolean found) {
        if (!found) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.contactpoints.message.contactPointMatchesNotFound"));
            form.setTabToChangeTo(PartyGuiHttpConstants.CONTACT_DETAILS_TAB);
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE,
                    new ActionMessage("page.party.message.contactPointMatchesFound"));
            form.setTabToChangeTo(PartyGuiHttpConstants.ROLES_TAB);
        }
    }

    private ActionForward contactPointValidationMessages(ActionMapping actionMapping, HttpServletRequest request,
            PartyForm form, ActionMessages messages) {
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchCriteria"));
        saveErrorMessages(request, messages);
        form.setTabToChangeTo(PartyGuiHttpConstants.CONTACT_DETAILS_TAB);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    private boolean retrieveFinancialAccountDetails(HttpServletRequest request, HttpServletResponse response,
            PartyForm partyForm) {
        boolean found;

        FinancialAccountDetailSearchCriteria searchCriteria = new FinancialAccountDetailSearchCriteria();

        if (!GenericValidator.isBlankOrNull(partyForm.getAccountNumber().trim())) {
            searchCriteria.setAccountNumber(partyForm.getAccountNumber().trim());
        }
        searchCriteria.setSystemDate(Date.today());

        List<ContactPoint> financialAccountDetails = getCustomerRelationshipService().retrieveContactPoints(
                getApplicationContext(), searchCriteria);
        found = financialAccountDetails.size() > 0;

        List<PartySearchResultVO> financialAccounDetailsListSearchContacts = new ArrayList<>();
        for (ContactPoint contactPoint : financialAccountDetails) {
            FinancialAccountDetail detail = (FinancialAccountDetail) contactPoint;
            PartySearchResultVO matchSelect = getPartyGuiUtility().searchFinancialAccountDetailsMatchInfo(detail);
            financialAccounDetailsListSearchContacts.add(matchSelect);
        }
        partyForm.setFinancialAccountDetailsListSearchContacts(financialAccounDetailsListSearchContacts);
        return found;
    }

    public ActionForward loadDataAfterSave(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setReadOnly(false);
        loadGeneralTab(httpServletRequest, partyForm);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    public ActionForward loadDataAfterSaveContact(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setReadOnly(false);
        partyForm.setPostAddrRegionChanged(false);
        partyForm.setPhysAddrRegionChanged(false);
        partyForm.setUnstructAddrRegionChanged(false);
        partyForm.setPostalContactEdit(false);
        partyForm.setPhysicalContactEdit(false);
        partyForm.setUnstructuredContactEdit(false);
        loadContactsTab(partyForm.getParty(), partyForm, httpServletRequest, httpServletResponse);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    public ActionForward loadDataAfterSaveBank(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return loadBankTab(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward loadDataAfterSaveRegistration(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setReadOnly(false);
        loadRegistrationTab(partyForm, httpServletRequest);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    public ActionForward loadDataAfterSaveRelationship(ActionMapping actionMapping, ActionForm actionForm,
                                                       HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setReadOnly(false);
        loadRelationshipsTab(httpServletRequest, partyForm);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    // ContactPreference and ContactPoint methods
    public ActionForward addPreference(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ContactActionHelper iContactActionHelper = new ContactActionHelper(getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        RolePlayer rPlayer = partyForm.getRolePlayerForCP();
        if (rPlayer != null) {
            Set<PartyRole> pRoles = partyForm.getPartyRolesForParty();
            for (PartyRole cptProle : pRoles) {
                if (cptProle.getId().longValue() == rPlayer.getId().longValue()) {
                    iContactActionHelper.addPrefToRolePlayer(getApplicationContext(), partyForm, rPlayer);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "page.party.contactpoints.message.contactPrefAddSuccessful"));
                    saveInformationMessages(httpServletRequest, messages);
                    return load(actionMapping, partyForm, httpServletRequest, httpServletResponse);
                }
            }
        }
        iContactActionHelper.addPrefToRolePlayer(getApplicationContext(), partyForm, partyForm.getParty());
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                "page.party.contactpoints.message.contactPrefAddSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveContact(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward updatePreference(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        RolePlayer rPlayer = partyForm.getRolePlayerForCP();
        ContactActionHelper iContactActionHelper;
        iContactActionHelper = new ContactActionHelper(getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());
        if (rPlayer != null) {
            Set<PartyRole> pRoles = partyForm.getPartyRolesForParty();
            for (PartyRole cptProle : pRoles) {
                if (cptProle.getId().longValue() == rPlayer.getId().longValue()) {
                    iContactActionHelper.editPrefOfRolePlayer(getApplicationContext(), partyForm, rPlayer);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "page.party.message.contactPrefEditSuccessful"));
                    saveInformationMessages(httpServletRequest, messages);
                    return load(actionMapping, partyForm, httpServletRequest, httpServletResponse);
                }
            }
        }
        iContactActionHelper.editPrefOfRolePlayer(getApplicationContext(), partyForm, partyForm.getParty());
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.contactPrefEditSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveContact(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward addContact(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ContactActionHelper iContactActionHelper = new ContactActionHelper(getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        RolePlayer rPlayer = partyForm.getRolePlayerForCP();
        if (rPlayer != null) {
            Set<PartyRole> pRoles = partyForm.getPartyRolesForParty();
            for (PartyRole cptProle : pRoles) {
                if (cptProle.getId().longValue() == rPlayer.getId().longValue()) {
                    iContactActionHelper.addContactToContactPref(getApplicationContext(), partyForm, rPlayer);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "page.party.message.contactPointAddSuccessful"));
                    saveInformationMessages(httpServletRequest, messages);
                    return load(actionMapping, partyForm, httpServletRequest, httpServletResponse);
                }
            }
        }
        iContactActionHelper.addContactToContactPref(getApplicationContext(), partyForm, partyForm.getParty());
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.contactPointAddSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveContact(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward updateContact(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ContactActionHelper iContactActionHelper = new ContactActionHelper(getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        RolePlayer rPlayer = partyForm.getRolePlayerForCP();
        if (rPlayer != null) {
            Set<PartyRole> pRoles = partyForm.getPartyRolesForParty();
            for (PartyRole cptProle : pRoles) {
                if (cptProle.getId().longValue() == rPlayer.getId().longValue()) {
                    iContactActionHelper.editContactOfContactPref(getApplicationContext(), partyForm, rPlayer);
                    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                            "page.party.message.contactPointEditSuccessful"));
                    saveInformationMessages(httpServletRequest, messages);
                    return load(actionMapping, partyForm, httpServletRequest, httpServletResponse);
                }
            }
        }
        iContactActionHelper.editContactOfContactPref(getApplicationContext(), partyForm, partyForm.getParty());
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.contactPointEditSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveContact(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    // Party Registration methods
    public ActionForward addRegistration(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        RegistrationActionHelper iRegistrationActionHelper;
        iRegistrationActionHelper = new RegistrationActionHelper();
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        iRegistrationActionHelper.addRegistration(getApplicationContext(), partyForm, messages,
                getCustomerRelationshipService(), getProductDevelopmentService(), getTypeParser());
        if (!messages.isEmpty()) {
            saveErrorMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.REGISTRATION_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.registrationAddSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveRegistration(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    //Party relationship methods
    public ActionForward addRelationship(ActionMapping actionMapping, ActionForm actionForm,
                                         HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyRelationshipActionHelper iPartyRelationshipActionHelper = new PartyRelationshipActionHelper();
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();

        iPartyRelationshipActionHelper.addRelationship(getApplicationContext(), partyForm, messages,
                getCustomerRelationshipService(), getProductDevelopmentService());

        if (!messages.isEmpty()) {
            saveErrorMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.RELATIONSHIPS_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }

        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.relationshipAddSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveRelationship(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * The user clicks 'Update Relationship' on the party screen
     */
    public ActionForward updateRelationship(ActionMapping actionMapping, ActionForm actionForm,
                                            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyRelationshipActionHelper partyRelationshipActionHelper = new PartyRelationshipActionHelper();
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();

        partyRelationshipActionHelper.editRelationship(partyForm, getCustomerRelationshipService(), messages);

        if (!messages.isEmpty()) {
            saveErrorMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.RELATIONSHIPS_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }

        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.relationshipEditSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveRelationship(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * The user clicks 'Update Registration' on the party screen
     */
    public ActionForward updateRegistration(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        RegistrationActionHelper iRegistrationActionHelper = new RegistrationActionHelper();
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        iRegistrationActionHelper.editRegistration(partyForm, messages, getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());
        if (!messages.isEmpty()) {
            saveInformationMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.REGISTRATION_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.registrationEditSuccessful"));
        saveInformationMessages(httpServletRequest, messages);
        return loadDataAfterSaveRegistration(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    public ActionForward endRole(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PartyForm partyForm = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();
        getPartyRolesActionHelper().endPartyRole(partyForm, messages, getCustomerRelationshipService(),
                httpServletRequest, httpServletResponse);
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.partyRoleEnded"));
        saveInformationMessages(httpServletRequest, messages);

        partyForm.setRolesTabLoaded(false); // mark as dirty

        ActionRedirect redirect = createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY),
                PARAM_PARTY_OBJECT_REF, partyForm.getPartyObjectRef());
        redirect.addParameter("t", PartyGuiHttpConstants.ROLES_TAB); // change to the roles tab
        return redirect;
    }

    // load of Roles for saving contactPoints on Roles
    public ActionForward load(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.setDefContPref(false);
        partyForm.setDefContPrefName(PartyGuiHttpConstants.NONE);
        partyForm.setDefContPoint(false);
        partyForm.setSelContactPreferenceTypeId(null);

        Collection<LabelValueBean> rPlayers = partyForm.getRolesForCpsList();
        RolePlayer rolePlayer;
        partyForm.setRolePlayerForCP(null);
        for (LabelValueBean lvBean : rPlayers) {
            if (lvBean.getValue().equalsIgnoreCase(partyForm.getSelRoleTypeForCpointsLoad())) {
                ObjectReference rPlayerObjRef = ObjectReference.convertFromString(lvBean.getValue());
                boolean isSubTypeOfParty;
                isSubTypeOfParty = isSubTypeOfParty(rPlayerObjRef.getTypeId());
                if (!isSubTypeOfParty) {
                    rolePlayer = getCustomerRelationshipService().getPartyRole(getApplicationContext(), rPlayerObjRef);
                    loadContactsTab(rolePlayer, partyForm, httpServletRequest, httpServletResponse);
                } else {
                    rolePlayer = partyForm.getParty();
                    loadContactsTab(rolePlayer, partyForm, httpServletRequest, httpServletResponse);
                }
                partyForm.setRolePlayerForCP(rolePlayer);
                break;
            }
        }
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    private boolean isSubTypeOfParty(Long toBeCheckedTypeReference) {
        return getProductDevelopmentService().isSubType(new ApplicationContext(), CoreTypeReference.PARTY.getValue(),
                toBeCheckedTypeReference);
    }

    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        String storeAccNo = partyForm.getAccountNumber();
        // clear any existing bank details and previous data
        partyForm.reset();
        partyForm.setAccountNumber(storeAccNo);
        Map<String, BankDetailVO> partyRolesResultsMap = new TreeMap<String, BankDetailVO>();
        Map<String, String> agmtsFromSearchMap = new TreeMap<String, String>();
        Map<String, Map<String, String>> idMapPartyRolesBankAgmts = new TreeMap<String, Map<String, String>>();

        // need only use PersonSearchCrit, no need to also use
        // OrganisationSearchCrit
        // as searching only on Acc number which is on parent partySearchCrit
        PartySearchCriteria personCriteria = new PersonSearchCriteria();
        personCriteria.setAccountNumber(partyForm.getAccountNumber());
        // account number of the search
        List<RolePlayer> listPartyRoles = getCustomerRelationshipService().getPartiesByFinancialAccountDetailsAccountNumberOnly(
                getApplicationContext(), personCriteria);
        if (listPartyRoles != null && listPartyRoles.size() > 0) {
            partyForm.setPartyRolesSize(listPartyRoles.size());
            for (RolePlayer listPartyRole : listPartyRoles) {
                if (!(listPartyRole instanceof PartyRole)) {
                    throw new Exception(
                            "Bank Account details exist on a roleplayer which is not a partyRole of Payee or Payer.");
                    // throw exception if 'PARTYROLE' is a PARTY. If this
                    // happens the data is corrupt
                }
                PartyRole existingPartyRole = (PartyRole) listPartyRole;
                PartyName partyName = getCustomerRelationshipService().getDefaultPartyNameForRolePlayer(
                        getApplicationContext(), existingPartyRole.getObjectReference());
                Set<ContactPreference> contactPreferences = existingPartyRole.getContactPreferences();
                for (ContactPreference contactPreference : contactPreferences) {
                    {
                        for (ContactPoint contactPoint : contactPreference.getContactPoints()) {
                            if (contactPoint instanceof FinancialAccountDetail) {
                                FinancialAccountDetail existingFad = (FinancialAccountDetail) contactPoint;
                                if (existingFad.getAccountNumber().trim().equals(partyForm.getAccountNumber().trim())) {
                                    BankDetailVO searchFadDetail = new BankDetailVO();
                                    searchFadDetail.setName(getPartyNamesActionHelper().getPartyFullName(partyName));
                                    searchFadDetail.setPreference(formatType(contactPreference.getObjectReference()));
                                    searchFadDetail.setPartyRole(formatType(existingPartyRole.getObjectReference()));
                                    searchFadDetail.setAccountNumber(existingFad.getAccountNumber());
                                    searchFadDetail.setAccountHolder(existingFad.getAccountHolderName());
                                    IEnumeration accountTypeEnum = getProductDevelopmentService().getEnumeration(
                                            getApplicationContext(), existingFad.getAccountType());
                                    searchFadDetail.setAccountType(accountTypeEnum.getName());
                                    searchFadDetail.setStartDate(existingFad.getEffectivePeriod().getStart());
                                    searchFadDetail.setEndDate(existingFad.getEffectivePeriod().getEnd());

                                    // check should there be 2 identical AccNos
                                    // with different BrchCds on the same Role,
                                    // the map will not allow duplicates

                                    partyRolesResultsMap.put((existingFad.getAccountNumber()), searchFadDetail);
                                }
                            }
                        }
                    }
                }

                idMapPartyRolesBankAgmts.put(getPartyNamesActionHelper().getPartyFullName(partyName),
                        agmtsFromSearchMap);
            }
            Collection<BankDetailVO> bankResults = partyRolesResultsMap.values();
            partyForm.setBankDetailList(bankResults);
            partyForm.setBankPartyRolesAgmtsDisplayMap(idMapPartyRolesBankAgmts);

            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.bank.searchAccountFound"));
            saveInformationMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        } else {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.message.bank.searchAccountNotFound"));
            saveInformationMessages(httpServletRequest, messages);
            partyForm.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }
    }

    /**
     * Invoked when the user edits banking details
     */
    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return loadDataAfterSaveBank(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    // Other & init methods
    // this method is entered when switching between Person and Organisation
    public ActionForward refresh(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;

        String partyType = partyForm.getSelPartyType();
        partyForm.resetPartySearchInfo();
        partyForm.resetPartyInfo(); //
        partyForm.reset();
        partyForm.setSelPartyType(partyType);
        partyForm.setRelationshipRole(false);
        partyForm.setGeneralTabLoaded(true);
        partyForm.setGenTabFirstLoad(true);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);
        return actionMapping.findForward(actionMapping.getInput());
    }

    public ActionForward tabChange(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        PartyForm partyForm = (PartyForm) actionForm;
        int tabStr = partyForm.getTabToChangeTo();
        partyForm.setGenTabFirstLoad(true);

        if (partyForm.getPartyObjectRefAsObjectReference() != null) {
            if (tabStr == PartyGuiHttpConstants.ROLES_TAB && !partyForm.isRolesTabLoaded()) {
                loadRolesTab(httpServletRequest, partyForm);
            } else if (tabStr == PartyGuiHttpConstants.CONTACT_DETAILS_TAB && !partyForm.isContactTabLoaded()) {
                loadContactsTab(partyForm.getParty(), partyForm, httpServletRequest, httpServletResponse);
            } else if (tabStr == PartyGuiHttpConstants.BANK_DETAILS_TAB && !partyForm.isBankTabLoaded()) {
                return loadBankTab(actionMapping, actionForm, httpServletRequest, httpServletResponse);
            } else if (tabStr == PartyGuiHttpConstants.REGISTRATION_TAB && !partyForm.isRegTabLoaded()) {
                loadRegistrationTab(partyForm, httpServletRequest);
            } else if (tabStr == PartyGuiHttpConstants.RELATIONSHIPS_TAB && !partyForm.isRelationshipsTabLoaded()) {
                loadRelationshipsTab(httpServletRequest, partyForm);
            }
        }
        partyForm.setTabToChangeTo(tabStr);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    private void loadGeneralTab(HttpServletRequest request, PartyForm partyForm) throws Exception {

        ObjectReference rolePlayerObjectRef;
        if (StringUtils.trimToNull(partyForm.getPartyObjectRef()) != null) {
            rolePlayerObjectRef = ObjectReference.convertFromString(partyForm.getPartyObjectRef());
        } else {
            throw new IllegalStateException("The role player object reference was not found");
        }

        loadGeneralTab(request, partyForm, rolePlayerObjectRef);
    }

    /**
     * Load the general tab for the given role player - which may be a party or a party role
     */
    private void loadGeneralTab(HttpServletRequest request, PartyForm partyForm, ObjectReference rolePlayerObjectRef)
            throws Exception {

        Party party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                rolePlayerObjectRef, new String[] { "partyNames" });

        partyForm.setParty(party);
        partyForm.setPartyObjectRef(String.valueOf(party.getObjectReference()));
        partyForm.setDefaultPrtyname(party.getDefaultName());
        partyForm.setDefaultPartyNameId(party.getDefaultName() != null ? party.getDefaultName().getObjectReference()
                .getObjectId() : null);
        partyForm.setPartyNames(party.getAllNames());
        if(partyForm.getIsDelegating()){
            party.getAllNames().forEach(partyName -> getGeneralActionHelper().populatePartyName(request, partyForm, partyName, getProductDevelopmentService()));
        }

        if (party.getObjectReference().getTypeId().equals(CoreTypeReference.PERSON.getValue())) {
            getPartyNamesActionHelper().namesDisplay(party, partyForm);
            partyForm.setShowNameButton(!partyForm.getIsDelegating());
        }

        getGeneralActionHelper().loadGeneralPartyData(request, partyForm, getCustomerRelationshipService(),
                getProductDevelopmentService());
        callbackLinkingDefaults(partyForm);
        partyForm.setGeneralTabLoaded(true);
        partyForm.setGenTabFirstLoad(true);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.GENERAL_TAB);

        // populate the party type drop down field.
        if (CoreTypeReference.PERSON.getValue().equals(party.getObjectReference().getTypeId())){
            partyForm.setSelPartyType("Person");
        } else {
            partyForm.setSelPartyType("Organisation");
        }

    }

    private void loadRelationshipsTab(HttpServletRequest request, PartyForm partyForm) throws Exception {

        Party party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                                                                                   partyForm.getPartyObjectRefAsObjectReference(), null);
        getPartyRelationshipActionHelper().relationshipDisplay(request, partyForm, getApplicationContext(), getCustomerRelationshipService(),
                getProductDevelopmentService(), party);

        callbackLinkingDefaults(partyForm);
        partyForm.setRelationshipsTabLoaded(true);
        partyForm.setSelRelationshipTypeFrom(null);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.RELATIONSHIPS_TAB);
    }

    private void loadRolesTab(HttpServletRequest request, PartyForm partyForm) throws Exception {

        Party party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                partyForm.getPartyObjectRefAsObjectReference(), new String[] { "partyRoles" });
        partyForm.setPartyRolesForParty(party.getPartyRoles());
        partyForm.setPartyRolesSize(party.getPartyRoles().size());
        getPartyRolesActionHelper().partyRolesDisplay(request, getProductDevelopmentService(), getApplicationContext(),
                party, partyForm, getCustomerRelationshipService(), getPolicyAdminService(), getClaimManagementService());
        callbackLinkingDefaults(partyForm);
        partyForm.setRolesTabLoaded(true);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.ROLES_TAB);
    }

    private void loadContactsTab(RolePlayer rolePlayer, PartyForm partyForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        ContactActionHelper iContactActionHelper = new ContactActionHelper(getCustomerRelationshipService(),
                getProductDevelopmentService(), getTypeParser());

        if (rolePlayer instanceof Party) {
            Set<PartyRole> partyRoles =
                getCustomerRelationshipService()
                    .getPartyRolesForRolePlayer(getApplicationContext(), rolePlayer.getObjectReference());
            partyForm.getRolesForCpsList().clear();
            Type rType = getProductDevelopmentService().getType(getApplicationContext(), rolePlayer.getTypeId());
            partyForm.getRolesForCpsList()
                .add(new LabelValueBean(formatType(rType), String.valueOf(rolePlayer.getObjectReference().toString())));

            for (PartyRole pr : partyRoles) {
                Type prType = getProductDevelopmentService().getType(getApplicationContext(), pr.getTypeId());

                if (pr.getContext() != null && pr.getContext().getObjectId() != null) {
                    AgreementDTO agreement = getPolicyAdminService()
                            .findLatestLegallyBindingAgreementForAgreementAnchor(
                                    getApplicationContext(), pr.getContext()
                            );
                    if (agreement == null) {
                        agreement = getPolicyAdminService()
                                .findLatestNonLegallyBindingAgreementForAgreementAnchor(
                                        getApplicationContext(), pr.getContext()
                                );
                    }
                    if (agreement != null) {
                        AgreementDTO topLevelAgreement = getPolicyAdminService().getTopLevelAgreementForAgreementPart(
                                getApplicationContext(), agreement.getObjectReference()
                        );
                        String agreementExtRef = topLevelAgreement.getExternalReference();

                        partyForm.getRolesForCpsList().add(
                                new LabelValueBean(
                                        formatType(prType) + ":" + agreementExtRef,
                                        String.valueOf(pr.getObjectReference().toString())
                                )
                        );
                    }
                }
            }
        }

        Party party;
        if (partyForm.getPartyRolesForParty() == null) {
            party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                    partyForm.getPartyObjectRefAsObjectReference(),
                    new String[] { "defaultContactPreference", "contactPreferences", "partyRoles" });
            partyForm.setPartyRolesForParty(party.getPartyRoles());
        } else {
            party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                    partyForm.getPartyObjectRefAsObjectReference(),
                    new String[] { "defaultContactPreference", "contactPreferences" });
        }
        partyForm.setContactPrefsForParty(party.getContactPreferences());
        iContactActionHelper.contactsDisplay(httpServletRequest, getApplicationContext(), rolePlayer, partyForm);
        callbackLinkingDefaults(partyForm);
        partyForm.setContactTabLoaded(true);
        partyForm.setSelContactPointTypeId(null);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.CONTACT_DETAILS_TAB);

    }


    public ActionForward loadBankDetailForEdit(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        BankActionHelper bankActionHelper = new BankActionHelper(getPolicyAdminService(),
                getProductDevelopmentService(), getApplicationContext());
        PartyForm partyForm = (PartyForm) actionForm;

        partyForm.getSearchedBankDetailList().clear();

        if (partyForm.getBankTabMode().equals("EDIT_MODE")) {
            partyForm = bankActionHelper.populateBankDetailFieldsForEdit(httpServletRequest, partyForm,
                    partyForm.getBankDetailList());
        }
        partyForm.setBankTabLoaded(true);

        partyForm.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    private ActionForward financialAccountDetailErrorMessage(ActionMapping actionMapping, HttpServletRequest request,
            PartyForm form, String errorMessageKey) {
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorMessageKey));
        saveErrorMessages(request, messages);
        form.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);
        return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
    }

    public ActionForward updateFinancialAccountDetails(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response) {

        PartyForm form = (PartyForm) actionForm;

        if (GenericValidator.isBlankOrNull(form.getAccountNumber())) {
            return financialAccountDetailErrorMessage(actionMapping, request, form,
                                                      "page.party.capturebankdetails.action.bankAccountNumberRequired");
        }
        ApplicationContext applicationContext = getApplicationContext();
        // construct the ObjectReference for the bank account information that has been updated.

        ObjectReference fadObjRef = new ObjectReference(Components.PARTY, form.getAccountTypeId(), form.getAccountId());
        ContactPoint cpFromDB = getCustomerRelationshipService().getContactPoint(applicationContext, fadObjRef);
        FinancialAccountDetail fadFromDB = (FinancialAccountDetail) cpFromDB;

        // update.
        fadFromDB.setAccountHolderName(form.getAccountHolder());
        fadFromDB.setExpiryDate(form.getCrCardExpiryDate());
        fadFromDB.setAccountNumber(form.getAccountNumber());

        EnumerationReference accountTypeEnumRef = EnumerationReference.convertFromString(form.getAccountType());
        fadFromDB.setAccountType(accountTypeEnumRef);

        if (!form.getBank().isEmpty()) {
            EnumerationReference bankNameRef = EnumerationReference.convertFromString(form.getBank());
            fadFromDB.setBankName(bankNameRef);
        }

        fadFromDB.setBankBranchReference(form.getBankBranchReference());
        fadFromDB.setBankBranchCode(form.getBankBranchCode());
        fadFromDB.setBankBranchName(form.getBankBranchName());

        getCustomerRelationshipService().modifyContactPoint(applicationContext, fadFromDB);

        return loadBankTab(actionMapping, actionForm, request, response);
    }

    /**
     * This operation uses the details added on the screen to create a new {@link FinancialAccountDetail}.
     * The new {@link FinancialAccountDetail} is added to the {@link ContactPreference} of {@link com.silvermoongroup.common.domain.Type}
     * &quote Banking &quote. If not {@link ContactPreference} of {@link Type} &quote Banking &quote exists for the
     * selected party, one is created.
     *
     * @param actionMapping the action mapping
     * @param actionForm the action form
     * @param request the http request
     * @param response the http response
     * @return the ActionForward produced by {@link #loadBankTab(ActionMapping, ActionForm, HttpServletRequest, HttpServletResponse)}
     */
    public ActionForward addFinancialAccountDetails(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response) {

        PartyForm form = (PartyForm) actionForm;
        ActionMessages messages = new ActionMessages();

        if (GenericValidator.isBlankOrNull(form.getAccountNumber())) {
            return financialAccountDetailErrorMessage(actionMapping, request, form,
                                                      "page.party.capturebankdetails.action.bankAccountNumberRequired");
        }
        ApplicationContext applicationContext = getApplicationContext();

        Party party = form.getParty();

        ContactPreference bankingContactPreference = getBankingContactPreferenceForParty(applicationContext, party);

        //populate new bank account details.
        FinancialAccountDetail financialAccountDetail = new FinancialAccountDetail();

        financialAccountDetail.setAccountNumber(form.getAccountNumber());
        financialAccountDetail.setExpiryDate(form.getCrCardExpiryDate());
        financialAccountDetail.setAccountHolderName(form.getAccountHolder());

        String accountTypeName = form.getAccountType();
        EnumerationReference accountType = EnumerationReference.convertFromString(accountTypeName);
        financialAccountDetail.setAccountType(accountType);

        String bankName = form.getBank();
        EnumerationReference bankNameRef = EnumerationReference.convertFromString(bankName);
        financialAccountDetail.setBankName(bankNameRef);

        financialAccountDetail.setBankBranchReference(form.getBankBranchReference());
        financialAccountDetail.setBankBranchCode(form.getBankBranchCode());
        financialAccountDetail.setBankBranchName(form.getBankBranchName());

//        bankingContactPreference.addContactPoint(financialAccountDetail);
//        financialAccountDetail.addContactPreference(bankingContactPreference);
        getCustomerRelationshipService().attachContactPoint(applicationContext,party.getObjectReference(),bankingContactPreference,financialAccountDetail);

        return loadBankTab(actionMapping, actionForm, request, response);
    }

    /**
     * This operation attempts to find the {@link ContactPreference} of type 'Banking' for the given {@link Party}.
     * If one cannot be found this operation will create a new one.
     *
     * @param applicationContext The application context
     * @param party the given party
     * @return the {@link ContactPreference} of {@Type} &quote Banking &quote
     */
    private ContactPreference getBankingContactPreferenceForParty(ApplicationContext applicationContext, Party party) {
        Collection<ContactPreference> contactPreferences = getCustomerRelationshipService()
                .findContactPreferencesAndPoints(applicationContext, party.getObjectReference());

        Type bankingType = getProductDevelopmentService().getType(applicationContext, "Banking");
        ContactPreference bankingContactPreference = null;
        for(ContactPreference contactPreference : contactPreferences){
            if (contactPreference.getTypeId().equals(bankingType.getId())){
                bankingContactPreference = contactPreference;
                break;
            }
        }
        if (bankingContactPreference==null){
            bankingContactPreference = new ContactPreference();
            bankingContactPreference.setTypeId(bankingType.getId());
            DatePeriod effectivePeriod = new DatePeriod(Date.PAST, Date.FUTURE);
            bankingContactPreference.setEffectivePeriod(effectivePeriod);
            bankingContactPreference = getCustomerRelationshipService()
                    .establishContactPreference(
                            applicationContext, party.getObjectReference(), bankingContactPreference, false);
        }
        return bankingContactPreference;
    }

    /**
     * Internal Entry Point: Loading the Bank Tab.
     */
    public ActionForward loadBankTab(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        PartyForm partyForm = (PartyForm) actionForm;
        partyForm.resetForBankingDetailsDisplay();
        if (logger.isDebugEnabled()) {
            logger.debug("Loading the bank tab..");
        }
        ApplicationContext applicationContext = getApplicationContext();
        BankActionHelper bankActionHelper = new BankActionHelper(getPolicyAdminService(),
                getProductDevelopmentService(), applicationContext);
        callbackLinkingDefaults(partyForm);


        Collection<BankDetailVO> bankDetail = new ArrayList<>();
        if (partyForm.getRoleInContext() != null) { // we have a context (e.g.: an agreement)
                bankDetail.addAll(bankActionHelper.retrieveBankDetailsForRolePlayer(applicationContext,
                        getCustomerRelationshipService(), httpServletRequest, partyForm.getRoleInContext()));
        } else { // We came in from the left nav instead.

            // for the party
            bankDetail.addAll(bankActionHelper.retrieveBankDetailsForRolePlayer(applicationContext,
                    getCustomerRelationshipService(), httpServletRequest, partyForm.getParty()));

            // for every party role
            Set<PartyRole> partyRoles = getCustomerRelationshipService().getPartyRolesForRolePlayer(applicationContext,
                    partyForm.getParty().getObjectReference());
            for (PartyRole partyRole : partyRoles) {
                bankDetail.addAll(bankActionHelper.retrieveBankDetailsForRolePlayer(applicationContext,
                        getCustomerRelationshipService(), httpServletRequest, partyRole));
            }
        }

        partyForm.setBankTabMode("EDIT_MODE");
        partyForm.setReadOnly(false);
        partyForm.setBankDetailList(bankDetail);
        partyForm.setBankTabLoaded(true);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.BANK_DETAILS_TAB);
        if (logger.isDebugEnabled()) {
            logger.debug("Done loading the bank tab..");
        }

        return createRedirect(partyForm, actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS));

    }

    public ActionForward viewAgreementPartForPartyRole(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        PartyForm partyForm = (PartyForm) actionForm;

        ActionRedirect redirect = new ActionRedirect();
        redirect.setPath("/secure/agreementversion/tla.do?method=.viewAnchorAgreementPart");
        redirect.addParameter("relativeAgreementPartObjectReference", partyForm.getAnchorObjectReference());
        redirect.addParameter("contextObjectReference", partyForm.getTlaContextObjectReference());

        return redirect;
    }
    
    /**
     * Find all the agreement versions that have a role player attached with a context of the anchor agreement.
     */
    public void displayPartyRoleHistoryForAgreement(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if(!org.springframework.util.StringUtils.hasText(httpServletRequest.getParameter("anchorObjectReference"))) {
            return;
        }
        ObjectReference anchorObjectReference = ObjectReference.convertFromString(httpServletRequest
                .getParameter("anchorObjectReference"));
        
        Collection<AgreementDTO> agreementPart = getPolicyAdminService().findAgreementsForAgreementAnchor(getApplicationContext(), anchorObjectReference);
        Set<PartyRolesDetailVO> partyRoles = ((PartyForm) actionForm).getPartyRoleAgmtsMap().keySet();
        Set<ObjectReference> partyRolesObjectReferenceSet = new HashSet<>();
        if (partyRoles.isEmpty()) {
            // should return error, you should never be in this situation
        }
        
        for (PartyRolesDetailVO partyRole : partyRoles) {
            partyRolesObjectReferenceSet.add(ObjectReference.convertFromString(partyRole.getPartyRoleObjectReference()));
        }
        
        JsonGenerator gen;
        httpServletResponse.setContentType("application/json");
        
        TypeDTO partyTypeDTO = new TypeDTO();
        partyTypeDTO.setId(CoreTypeReference.PARTY.getValue());
        partyTypeDTO.setName(CoreTypeReference.PARTY.getName());
        try {
            gen = new JsonFactory().createJsonGenerator(httpServletResponse.getWriter());
            try {
                gen.writeStartObject();
                gen.writeArrayFieldStart("agreementversions");
                for (AgreementDTO agreement : agreementPart) {
                    Collection<RoleListDTO> roleListCollection = agreement.getRoleLists();
                    if (roleListCollection != null && !roleListCollection.isEmpty()) {
                        Set<TypeDTO> conformanceType = null;
                        
                        for (RoleListDTO roleList : roleListCollection) {
                            conformanceType = roleList.getConformanceTypes();
                            if (!conformanceType.contains(partyTypeDTO)) {
                                continue;
                            }
                            
                            Collection<ObjectReference> rolePlayers = new ArrayList<>();
                            for (RoleDTO roleDTO : roleList.getRoles()) {
                                rolePlayers.add(roleDTO.getRolePlayer());
                            }
                            if (!rolePlayers.isEmpty()) {
                                if (CollectionUtils.containsAny(partyRolesObjectReferenceSet, rolePlayers)) {
                                    // Agreement Part and its Agreement Version needs to be jsonified
                                    AgreementVersionDTO agreementVersion = getPolicyAdminService()
                                            .findAgreementVersionForAgreementObjectReference(getApplicationContext(),
                                                    agreement.getObjectReference());

                                    if (agreementVersion == null) {
                                        continue;
                                    }

                                    gen.writeStartObject();
                                    gen.writeStringField("version", agreementVersion.getVersionNumber().getMajor() + "." + agreementVersion.getVersionNumber().getMinor());
                                    gen.writeStringField("effectiveDate", getTypeFormatter().formatDate(agreementVersion.getStartDate()));
                                    gen.writeStringField("creationDate", getTypeFormatter().formatDate(agreementVersion.getCreationDate()));
                                    gen.writeStringField("status", agreementVersion.getCurrentState().getName());
                                    gen.writeBooleanField("legallyBinding", agreementVersion.isLegallyBinding());
                                    gen.writeStringField("tlaObjectReference", agreementVersion.getTopLevelAgreementObjectReference().toString());
                                    gen.writeStringField("agreementPart", agreement.getKind().getName());
                                    gen.writeStringField("agreementPartObjectReference", agreement.getObjectReference().toString());
                                    gen.writeEndObject();
                                    
                                }
                            }
                        }
                    }
                }
                gen.writeEndArray();
                gen.writeEndObject();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            } finally {
                gen.close();
            }
        } catch (Exception ex) {
            log.error("Error when writing JSON: " + ex.getMessage(), ex);
            try {
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException ignored) {
            }
        }
    }

    private void loadRegistrationTab(PartyForm partyForm, HttpServletRequest httpServletRequest) throws Exception {
        RegistrationActionHelper iRegistrationActionHelper = new RegistrationActionHelper();

        Party party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(),
                partyForm.getPartyObjectRefAsObjectReference(), new String[] { "partyRegistrations" });

        partyForm.setPartyRegistrations(party.getPartyRegistrations());
        iRegistrationActionHelper.registrationDisplay(httpServletRequest, partyForm, party);
        callbackLinkingDefaults(partyForm);
        partyForm.setRegTabLoaded(true);
        partyForm.setSelRegistrationType(null);
        partyForm.setSelAccreditationTypeId(null);
        partyForm.setTabToChangeTo(PartyGuiHttpConstants.REGISTRATION_TAB);
    }

    private void callbackLinkingDefaults(PartyForm partyForm) {

        if (partyForm.getRoleKindToLink() != null) {
            String roleKind = partyForm.getRoleKindToLink();
            if (roleKind != null) {
                getPartyRolesActionHelper().setGuiDefaultsForRolesLinking(roleKind, partyForm);
            } else {
                partyForm.setSelRoleType(PartyGuiHttpConstants.SELECT);
            }
        }
    }

    @Override
    protected void onInit() {
        super.onInit();
        organisationType = getProductDevelopmentService().getType(getApplicationContext(), CoreTypeReference.ORGANISATION.getValue());
    }

    /**
     * When a party search result is selected, and the party GUI is in linking mode (i.e. the roleKindToLink is not
     * null), should be return immediately to the callback, (possibly after creation of the party role), or should we
     * display the details of the party first?
     * <p>
     * The implementation of this method returns true if the party is an Organisation, or subtype thereof. Subclasses
     * can override this method as required to customise the behaviour
     *
     * 
     * @param partyForm
     *            the party form, which contains additional information about the state of the party GUI
     * @param partyObjectReference
     *            The object reference of the party being linked
     * @return true If the party should be immediately linked, otherwise false.
     */
    protected boolean shouldPartyBeImmediatelyLinked(PartyForm partyForm, ObjectReference partyObjectReference) {

        Long partyTypeId = partyObjectReference.getTypeId();
        if (partyTypeId == null) {
            return false;
        }

        if (partyTypeId.intValue() == CoreTypeReference.ORGANISATION.getValue()) {
            return true;
        }

        return getProductDevelopmentService().isSubType(getApplicationContext(), organisationType.getId(), partyTypeId);
    }

    protected Party findOrEstablishDelegatingParty(PartyForm partyForm){
        if (partyForm.getSelectedPartyObjectRef().contains(CoreTypeReference.PERSON.getValue().toString())){
            Person person = new Person();
            person.setExternalReference(partyForm.getExternalReference());
            // the establishParty methods checks if the person already exists, and if so, returns the existing person
            person = (Person) getCustomerRelationshipService().establishParty(getApplicationContext(), person);
            return person;
        } else {
            Organisation organisation = new Organisation();
            ObjectReference objectReference = new ObjectReference(partyForm.getSelectedPartyObjectRef());
            organisation.setTypeId(objectReference.getTypeId());
            organisation.setExternalReference(partyForm.getExternalReference());
            // the establishParty methods checks if the organisation already exists, and if so, returns the existing organisation
            organisation = (Organisation) getCustomerRelationshipService().establishParty(getApplicationContext(), organisation);
            // If the party comes from an agreement where it is selected to be the role player of the Insurer role,
            // add the internal company code and the customised account mappings for the premium, tax and debit accounts
            if (partyForm.getRoleKindToLink() != null && partyForm.getRoleKindToLink().equals("Insurer")) {
                if(!organisation.getExternalReference().contains("SMG_LUNOS_INSCO")) {
                    IEnumeration internalCompanyCode = establishInternalCompanyCode(getApplicationContext(), organisation);
                    createAccountMappings(getApplicationContext(), internalCompanyCode.getEnumerationReference());
                }
            }
            return organisation;
        }
    }

    private IEnumeration establishInternalCompanyCode(ApplicationContext applicationContext, Organisation organisation) {
        IEnumeration enumeration = new InternalCompanyCodeEnumeration();
        ((InternalCompanyCodeEnumeration) enumeration).setObjectReference(organisation.getObjectReference());
        enumeration.setEnumerationTypeId(EnumerationType.INTERNAL_COMPANY_CODE);
        Long id = new Random().nextLong(8);
        enumeration.setId(id);
        enumeration.setName(organisation.getExternalReference());
        if (organisation.getDescription() != null) {
            enumeration.setDescription(organisation.getDescription());
        } else {
            enumeration.setDescription(organisation.getExternalReference());
        }
        Period<Date> effectivePeriod = new DatePeriod(Date.today(), Date.FUTURE);
        enumeration.setEffectivePeriod(effectivePeriod);

        IEnumeration result = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                new EnumerationReference(id, EnumerationType.INTERNAL_COMPANY_CODE));
        if (result != null) {
            return result;
        }

        return getProductDevelopmentService().establishEnumeration(applicationContext, enumeration);

    }

    private void createAccountMappings(ApplicationContext applicationContext, EnumerationReference internalCompanyCode) {
        // Find Computer Loss Premium Basic Premium Income Account
        AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
        accountSearchCriteria.setName("Computer Loss Premium Basic Premium Income Account");
        accountSearchCriteria.setCurrencyCode(EnumerationReference.convertFromString("165:4"));
        List<IAccount> accounts =  getFinancialManagementService().findAccounts(applicationContext, accountSearchCriteria);
        if(accounts.isEmpty()) {
            throw new RuntimeException("Could not find Computer Loss Premium Basic Premium Income Account");
        } else {
            IAccount premiumAccount = accounts.get(0);
            // Save an account mapping for Computer Loss Premium Basic Premium Income Account with new Internal Company Code
            IAccountMapping accountMapping = new AccountMapping();
            accountMapping.setAccount(premiumAccount);
            accountMapping.setCurrencyCode(EnumerationReference.convertFromString("165:4"));
            accountMapping.setTransactionTypeId(100004L);
            accountMapping.setInternalCompanyCode(internalCompanyCode);
            accountMapping.setMeansOfPayment(MeansOfPayment.JOURNAL);
            getFinancialManagementService().establishAccountMapping(applicationContext, accountMapping);
        }
        // Find Computer Loss Tax Payable Account
        accountSearchCriteria.setName("Computer Loss Tax Payable Account");
        accounts =  getFinancialManagementService().findAccounts(applicationContext, accountSearchCriteria);
        if(accounts.isEmpty()) {
            throw new RuntimeException("Could not find Computer Loss Tax Payable Account");
        } else {
            IAccount taxAccount = accounts.get(0);
            // Save an account mapping for Computer Loss Tax Payable Account with new Internal Company Code
            IAccountMapping accountMapping = new AccountMapping();
            accountMapping.setAccount(taxAccount);
            accountMapping.setCurrencyCode(EnumerationReference.convertFromString("165:4"));
            accountMapping.setTransactionTypeId(100005L);
            accountMapping.setInternalCompanyCode(internalCompanyCode);
            accountMapping.setMeansOfPayment(MeansOfPayment.JOURNAL);
            getFinancialManagementService().establishAccountMapping(applicationContext, accountMapping);
        }
        // Find Silvermoongroup LUNOS Insco Direct Debit Payments Account
        accountSearchCriteria.setName("Silvermoongroup LUNOS Insco Direct Debit Payments Account");
        accounts =  getFinancialManagementService().findAccounts(applicationContext, accountSearchCriteria);
        if(accounts.isEmpty()) {
            throw new RuntimeException("Could not find Silvermoongroup LUNOS Insco Direct Debit Payments Account");
        } else {
            IAccount directDebitAccount = accounts.get(0);
            // Save an account mapping for Silvermoongroup LUNOS Insco Direct Debit Payments Account with new Internal Company Code
            IAccountMapping accountMapping = new AccountMapping();
            accountMapping.setAccount(directDebitAccount);
            accountMapping.setCurrencyCode(EnumerationReference.convertFromString("165:4"));
            accountMapping.setTransactionTypeId(1140L);
            accountMapping.setInternalCompanyCode(internalCompanyCode);
            accountMapping.setMeansOfPayment(MeansOfPaymentExtEnum.DIRECT_DEBIT);
            getFinancialManagementService().establishAccountMapping(applicationContext, accountMapping);
        }
    }

    /**
     * The user has selected the given relationship type and the search tab will be displayed. This hook allows
     * subclasses to customise the search form based on the type.
     * 
     * @param partyForm
     *            The form.
     * @param relationshipType
     *            The relationship type.
     */
    protected void initialiseSearchFormForRelationshipType(PartyForm partyForm, Type relationshipType) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
