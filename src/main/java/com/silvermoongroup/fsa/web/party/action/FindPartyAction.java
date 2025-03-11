/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.party.action;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.util.DateUtils;
import com.silvermoongroup.fsa.web.common.constant.CallbackConstants;
import com.silvermoongroup.fsa.web.common.util.SessionUtil;
import com.silvermoongroup.fsa.web.party.form.AbstractPartyForm;
import com.silvermoongroup.fsa.web.party.form.FindPartyForm;
import com.silvermoongroup.fsa.web.party.util.PersonSearchComparator;
import com.silvermoongroup.fsa.web.party.vo.PartySearchResultVO;
import com.silvermoongroup.party.criteria.OrganisationSearchCriteria;
import com.silvermoongroup.party.criteria.PersonSearchCriteria;
import com.silvermoongroup.party.domain.Person;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.*;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Find party action class
 */
public class FindPartyAction extends AbstractPartyAction {

    public FindPartyAction() {
    }

    @Override
    protected Map<String, String> getKeyMethodMap() {

        map.put("page.party.find.action.searchOrganisation", "searchOrganisation");
        map.put("page.party.find.action.searchperson", "searchPerson");
        map.put("page.party.add.action.addparty", "addParty");

        return map;
    }

    // ------------------------------------ START: Entry points from the GUI ----------------------------------------

    /**
     * The default entry point - if the party action is invoked without any method
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return findParty(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * Entry point: The user clicks the "Find Party" menu link
     */
    public ActionForward findParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindPartyForm partyForm = (FindPartyForm) actionForm;

        SessionUtil.clearConversationalState(httpServletRequest);

        partyForm.resetAll();

        // Set Delegating setup if necessary
        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));

        // default to the find person type on the find person tab
        partyForm.setSelPartyType(CoreTypeReference.PERSON.getName());

        return actionMapping.findForward(actionMapping.getInput());
    }

    /**
     * Entry Point: The user needs wants to add a party on the fly (e.g. agreement, claims, user admin)
     */
    public ActionForward addRolePlayerFromExternal(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindPartyForm partyForm = (FindPartyForm) actionForm;
        partyForm.setEmbeddedMode(true);
        Assert.notNull(partyForm.getRoleKindToLink(), "The role kind parameter is required");

        partyForm.resetPartyInfo();
        partyForm.resetFields();    

        partyForm.setPartyIsReadOnly(false);
        partyForm.setReadOnly(false);

        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));

        ActionMessages actionMessages = new ActionMessages();
        setupPartySelectionForRoleKind(partyForm, actionMessages);
        saveInformationMessages(httpServletRequest, actionMessages);

        return actionMapping.findForward(actionMapping.getInput());
    }

    /**
     * Set up the party GUI for the selection of a role player based on the kind
     */
    private void setupPartySelectionForRoleKind(FindPartyForm partyForm, ActionMessages actionMessages) {

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

            partyForm.setSelPartyType(CoreTypeReference.ORGANISATION.getName());

        } else {
            //getPartyRolesActionHelper().setGuiDefaultsForRolesLinking(roleKind, partyForm);
            partyForm.setSelPartyType(CoreTypeReference.PERSON.getName());
        }

    }

    /**
     * Entry Point: The user has clicked on the 'Add' button on the agreement screen and has supplied a role kind (which
     * we need to translate into a role type)
     */
    public ActionForward addRolePlayerFromAgreement(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        FindPartyForm partyForm = (FindPartyForm) actionForm;
        partyForm.setEmbeddedMode(true);
        String roleKindId = httpServletRequest.getParameter(CallbackConstants.ROLE_KIND);
        Assert.notNull(roleKindId, "The roleKind parameter must be specified");

        KindDTO kind = getKindById(Long.valueOf(roleKindId));
        partyForm.setRoleKindToLink(kind.getName());
        return addRolePlayerFromExternal(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     * Entry point: When the "Search Person" button is clicked
     */
    public ActionForward searchPerson(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        FindPartyForm partyForm = (FindPartyForm) actionForm;

        partyForm.resetForPersonResultsDisplay();

        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));
        List<PartySearchResultVO> allPersons = retrievePersons(httpServletRequest, partyForm);
        partyForm.setPersonList(allPersons);

        ArrayList<PartySearchResultVO> personPartialList = new ArrayList<>();
        personPartialList.addAll(allPersons.subList(0, Math.min(allPersons.size(), partyForm.getPageSize())));
        partyForm.setPersonPartialList(personPartialList);

        ActionMessages messages = new ActionMessages();
        if (partyForm.getPersonList().isEmpty()) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchnomatchfound"));
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchcomplete"));
        }

        saveInformationMessages(httpServletRequest, messages);
        
        return actionMapping.getInputForward();
    }

    /**
     * Entry Point: When the "Search Organisation" button is clicked
     */
    public ActionForward searchOrganisation(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        FindPartyForm partyForm = (FindPartyForm) actionForm;
        partyForm.resetForOrganisationResultsDisplay();
        partyForm.setIsDelegating(getCustomerRelationshipService().isDelegating(getApplicationContext()));
        partyForm.setOrganisationList(retrieveOrganisations(partyForm));

        ActionMessages messages = new ActionMessages();
        if (partyForm.getOrganisationList().isEmpty()) {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchnomatchfound"));
        } else {
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.party.message.searchcomplete"));
        }
        saveInformationMessages(httpServletRequest, messages);

        return actionMapping.getInputForward();
    }

    public ActionForward addParty(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("addParty"));
        AbstractPartyForm findPartyForm = (AbstractPartyForm)actionForm;
        
        redirect.addParameter("embeddedMode", findPartyForm.isEmbeddedMode());
        redirect.addParameter("roleKindToLink", findPartyForm.getRoleKindToLink());
        return redirect;
    }    

    // ------------------------------------ END: Entry points from the GUI ----------------------------------------


    /**
     * Build the search criteria from the form and search for an organisation.
     * 
     * @return true if there are results, otherwise false
     */
    protected List<PartySearchResultVO> retrieveOrganisations(FindPartyForm partyForm) {
        
        OrganisationSearchCriteria criteria = new OrganisationSearchCriteria();
        criteria.setFullName(partyForm.getTxtSearchOrgFullName());
        if(!partyForm.getIsDelegating()){
            criteria.setBasic(true);
        }

        String organisationType = StringUtils.trimToNull(partyForm.getTxtSearchOrgType());
        if (organisationType != null) {
            Type orgType = getProductDevelopmentService().getType(getApplicationContext(), organisationType);
            criteria.setType(orgType.getId());
        }

        return getPartyGuiUtility().buildOrganisationSearchResults(getApplicationContext(),
                getCustomerRelationshipService(), criteria);
    }

    private List<PartySearchResultVO> retrievePersons(HttpServletRequest request, FindPartyForm partyForm) {

        PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
        personSearchCriteria.setFirstName(StringUtils.trimToNull(partyForm.getTxtSearchFirstName()));
        personSearchCriteria.setMiddleNames(StringUtils.trimToNull(partyForm.getTxtSearchMiddleName()));
        personSearchCriteria.setLastName(StringUtils.trimToNull(partyForm.getTxtSearchSurname()));

        if(!partyForm.getIsDelegating()){
            personSearchCriteria.setBasic(true);
        }

        if (!GenericValidator.isBlankOrNull(partyForm.getTxtSearchBirthDate())) {
            int yearRangeHigh = 2;
            int yearRangeLow = -2;
            personSearchCriteria.setBirthDate(getTypeParser().parseDate(partyForm.getTxtSearchBirthDate()));
            java.util.Date birthDate = personSearchCriteria.getBirthDate().toJavaUtilDate();
            Date highDate = new Date(DateUtils.addYears(birthDate, yearRangeHigh));
            Date lowDate = new Date(DateUtils.addYears(birthDate, yearRangeLow));
            personSearchCriteria.setHighDateRange(highDate);
            personSearchCriteria.setLowDateRange(lowDate);
        }

        // Set Physical Address criteria.
        personSearchCriteria.setStreet(StringUtils.trimToNull(partyForm.getTxtSearchStreet()));
        personSearchCriteria.setHouseNumber(StringUtils.trimToNull(partyForm.getTxtSearchHouseNumber()));
        personSearchCriteria.setCity(StringUtils.trimToNull(partyForm.getTxtSearchCity()));
        personSearchCriteria.setPostalCode(StringUtils.trimToNull(partyForm.getTxtSearchPostalCode()));

        List<PartySearchResultVO> personList = new ArrayList<>();

        List<Person> searchResults = getCustomerRelationshipService().retrieveParties(getApplicationContext(),
                personSearchCriteria);
        for (Person person : searchResults) {

            PartySearchResultVO matchSelect = getPartyGuiUtility().createMatchSelectBeanForPerson(request, person, getProductDevelopmentService());
            personList.add(matchSelect);
        }
        Collections.sort(personList, new PersonSearchComparator(getTypeParser()));
        return personList;
    }

}
