/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.party.form.AddPartyForm;
import com.silvermoongroup.fsa.web.party.form.PartyForm;
import com.silvermoongroup.fsa.web.party.util.PartyGuiHttpConstants;
import com.silvermoongroup.fsa.web.party.util.PersonSearchComparator;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.OrganisationSearchCriteria;
import com.silvermoongroup.party.criteria.PersonSearchCriteria;
import com.silvermoongroup.party.domain.Organisation;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.PartyRole;
import com.silvermoongroup.party.domain.Person;
import org.apache.struts.action.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The PartyAction - essentially the entire party GUI.
 */
public class AddPartyAction extends AbstractPartyAction {

    private static final String REDIRECT_DISPLAY_PARTY = "displayGeneralDetails";
    private static final AddPartyActionHelper addPartyActionHelper = new AddPartyActionHelper();
    private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AddPartyAction() {
    }

    private static AddPartyActionHelper getPartyActionHelper() {
        return addPartyActionHelper;
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {

        map.put("page.party.add.action.addparty", "addParty");
        map.put("page.party.add.action.addorganisation", "addOrganisation");
        return map;
    }

    // ------------------------------------ START: Entry points from the GUI ----------------------------------------

    /**
     * The default entry point - if the party action is invoked without any method
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return actionMapping.getInputForward();
    }

    /**
     * Entry point: When the 'Add Party' link is selected menu item. NB: There may be a context (e.g. agreement) for the
     * creation of the party - the party is created in 'standalone' mode or via a context.
     * @throws Exception 
     */
    public ActionForward addParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        
        AddPartyForm partyForm = (AddPartyForm) actionForm;

        String roleKind = partyForm.getRoleKindToLink();
        if (roleKind.equals(CoreTypeReference.INSURER.getName())) {
            partyForm.setSelPartyType(CoreTypeReference.ORGANISATION.getName());
        }

        ActionForward actionForward = doSaveParty(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        if (actionForward != null) {
            return actionForward;
        }

        if (!CallBackUtility.isCallBackEmpty(httpServletRequest, httpServletResponse)) {
            if (getPartyGuiUtility().shouldPartyRoleBeCreated(getApplicationContext(), getProductDevelopmentService()
                    , httpServletRequest, roleKind)) {
                PartyRole partyRole = getPartyGuiUtility().createPartyRoleForParty(getApplicationContext(),
                        getCustomerRelationshipService(), partyForm.getPartyObjectRefAsObjectReference(),
                        getTypeForName(roleKind), null);
                partyForm.setRoleInContext(partyRole);
            }
            return link(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        }
        return createRedirect(partyForm, actionMapping.findForward(REDIRECT_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getPartyObjectRef());
    }

    /**
     * Entry point: When the 'Add Party' button is clicked on the GUI.
     */
    public ActionForward saveParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        PartyForm partyForm = (PartyForm) actionForm;

        ActionForward actionForward = doSaveParty(actionMapping, actionForm, httpServletRequest, httpServletResponse);
        if (actionForward != null) {
            return actionForward;
        }

        return createRedirect(partyForm, actionMapping.findForward(FORWARD_DISPLAY_PARTY), PARAM_PARTY_OBJECT_REF,
                partyForm.getParty().getObjectReference());
    }

    // ------------------------------------ END: Entry points from the GUI ----------------------------------------

    /**
     * Handle the logic for saving the party (and duplicates). Return an action forward if any errors need to be
     * handled, otherwise null to indicate that the save was successful and the calling code needs to select an
     * actionforward.
     */
    private ActionForward doSaveParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
            AddPartyForm partyForm = (AddPartyForm) actionForm;
        if (partyForm.getParty() != null) {
        }
        // duplicate checking
        if (partyForm.isOverRideDups()) {
            // we've chosen to override duplicates - reset for next time
            partyForm.setDuplicatesFound(false);
            partyForm.setOverRideDups(false);
            partyForm.getPersonList().clear();
            partyForm.getOrganisationList().clear();
        } else {
            boolean possDupParties = possibleDupPartiesCheck(partyForm, httpServletRequest);
            if (possDupParties) {
                partyForm.setDuplicatesFound(true);
                partyForm.setOverRideDups(false);
                return actionMapping.findForward(PartyGuiHttpConstants.URI_INPUT);
            }
        }

        Party party = populatePartyFromForm(partyForm, httpServletRequest);

        ApplicationContext applicationContext = getApplicationContext();
        party = getCustomerRelationshipService().establishParty(applicationContext, party);

        if (hasErrorMessages(httpServletRequest)) {
            return actionMapping.findForward(PartyGuiHttpConstants.URI_SUCCESS);
        }

        ActionMessages messages = new ActionMessages();
        if (party instanceof Person) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.message.saveGeneralPersonSuccessful"));
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.party.generalparty.message.savedorganisation"));
        }
        saveInformationMessages(httpServletRequest, messages);

        partyForm.setParty(party);
        partyForm.setPartyObjectRef(String.valueOf(party.getObjectReference()));

        // indicates that the calling code should return normally
        return null;
    }

    /**
     * Create the party object as per details on screen
     */
    private Party populatePartyFromForm(AddPartyForm partyForm, HttpServletRequest httpServletRequest) {

        Party party;
        if (partyForm.getSelPartyType().equalsIgnoreCase(CoreTypeReference.PERSON.getName())) {
            party = getPartyActionHelper().addPersonFromForm(partyForm, getCustomerRelationshipService(),
                    getTypeParser(), getProductDevelopmentService());
        } else {
            party = getPartyActionHelper().addOrganisationFromForm(getApplicationContext(), partyForm,
                    getProductDevelopmentService());
        }
        return party;
    }

    /**
     * Check for duplicates based on the search criteria, populating the search results with the duplicate values
     */
    private boolean possibleDupPartiesCheck(AddPartyForm partyForm, HttpServletRequest httpServletRequest) {

        if (partyForm.getSelPartyType().equalsIgnoreCase(CoreTypeReference.PERSON.getName())) {

            PersonSearchCriteria personSearchcriteria = new PersonSearchCriteria();
            personSearchcriteria.setFirstName(partyForm.getTxtFirstName().trim());
            personSearchcriteria.setLastName(partyForm.getTxtSurname().trim());
            personSearchcriteria.setMiddleNames(partyForm.getTxtMiddleName().trim());
            personSearchcriteria.setBirthDate(getTypeParser().parseDate(partyForm.getTxtBirthDate()));
            personSearchcriteria.setBasic(true);

            List<PartySearchResultVO> personList = new ArrayList<>();

            List<Person> searchResults = getCustomerRelationshipService().retrieveParties(getApplicationContext(),
                    personSearchcriteria);

            for (Person duplicate : searchResults) {

                PartySearchResultVO matchSelect = getPartyGuiUtility().createMatchSelectBeanForPerson(
                        httpServletRequest, duplicate, getProductDevelopmentService());
                personList.add(matchSelect);
                Collections.sort(personList, new PersonSearchComparator(getTypeParser()));
                ArrayList<PartySearchResultVO> personPartialList = new ArrayList<>();

                if (personList.size() < partyForm.getPageSize()) {
                    personPartialList.addAll(personList.subList(0, personList.size()));
                } else {
                    personPartialList.addAll(personList.subList(0, partyForm.getPageSize()));
                }
                partyForm.setPersonPartialList(personPartialList);
                partyForm.setPersonList(personList);
            }

            if (searchResults.size() > 0) {
                return true;
            }
        } else {
            OrganisationSearchCriteria organisationSearchCriteria = new OrganisationSearchCriteria();
            organisationSearchCriteria.setFullName(partyForm.getTxtOrgFullName().trim());
            organisationSearchCriteria.setBasic(true);

            List<Organisation> searchResults = getCustomerRelationshipService().retrieveParties(
                    getApplicationContext(), organisationSearchCriteria);

            for (Organisation duplicate : searchResults) {
                PartySearchResultVO matchSelect = getPartyGuiUtility().createMatchSelectBeanForOrganisation(duplicate);
                List<PartySearchResultVO> organisationList = new ArrayList<>();
                organisationList.add(matchSelect);
                partyForm.setOrganisationList(organisationList);
            }

            if (searchResults.size() > 0) {
                return true;
            }
        }
        return false;
    }
}
