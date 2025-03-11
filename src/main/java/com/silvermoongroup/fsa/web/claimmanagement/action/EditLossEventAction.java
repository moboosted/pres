/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.LossEvent;
import com.silvermoongroup.businessservice.claimmanagement.dto.RoleInLossEventDTO;
import com.silvermoongroup.claim.criteria.LossEventSearchCriteria;
import com.silvermoongroup.claim.domain.ClaimFolder;
import com.silvermoongroup.claim.domain.ElementaryClaim;
import com.silvermoongroup.claim.domain.RoleInClaim;
import com.silvermoongroup.claim.domain.RoleInLossEvent;
import com.silvermoongroup.claim.domain.intf.IClaim;
import com.silvermoongroup.claim.domain.intf.IClaimFolder;
import com.silvermoongroup.claim.domain.intf.IElementaryClaim;
import com.silvermoongroup.claim.enumeration.ClaimFolderState;
import com.silvermoongroup.claim.enumeration.ElementaryClaimState;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.policysearch.dto.ClaimableBenefit;
import com.silvermoongroup.fsa.web.claimmanagement.ClaimableBenefitTableEntry;
import com.silvermoongroup.fsa.web.claimmanagement.form.LossEventForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.common.callback.CallBackUtility;
import com.silvermoongroup.fsa.web.party.util.PartyGuiUtility;
import com.silvermoongroup.party.domain.Party;
import com.silvermoongroup.party.domain.PartyRole;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.config.ForwardConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Laurens Weyn
 */
public class EditLossEventAction extends AbstractLookupDispatchAction {

    private static final String CALLBACK_KEY = "RolePlayerCallbackKey";
    private static final String LOSS_EVENT_REFERENCE_KEY = "LossEventExternalReference";

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("claim.button.addclaimclaimant", "addClaimClaimantRolePlayer");
        map.put("claim.button.addvictim", "addVictimRolePlayer");
        map.put("claim.button.addagreement", "addAgreement");
        map.put("claim.button.initiateclaim", "initiateClaim");
        map.put("button.save", "save");
        map.put("claim.button.raiseclaim", "createClaim");
        return map;
    }

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        populatePageElements(form, request);

        final ForwardConfig edit = actionMapping.findForwardConfig("edit");
        return new ActionRedirect(edit);
    }


    /**
     * POST: edit LossEvent
     */
    public ActionForward edit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        populatePageElements(form, request);

        return actionMapping.getInputForward();
    }

    /*
     * This creates the Claim Folders.
     */
    public ActionForward createClaim(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //read form
        LossEventForm form = (LossEventForm) actionForm;
        ApplicationContext context = new ApplicationContext();

        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(form.getExternalReference());
        LossEvent lossEvent = getClaimManagementService().findLossEvents(context, search).get(0);

        //create top level
        IClaimFolder topLevelFolder = new ClaimFolder();
        topLevelFolder.setStartDate(lossEvent.getStartDate());
        topLevelFolder.setEndDate(lossEvent.getEndDate());
        topLevelFolder.setApplicationContextId(getApplicationContext().getId());
        //topLevelFolder.addClaimedLossEvent(lossEvent);
        topLevelFolder.addStatus(DateTime.now(), DateTime.FUTURE, ClaimFolderState.OPEN);
        topLevelFolder.setDescription("Loss Event External Reference: " + lossEvent.getExternalReference());
        topLevelFolder = getClaimManagementService().establishClaimFolder(context, topLevelFolder);

        lossEvent.setClaimObjectReference(topLevelFolder.getObjectReference());
        getClaimManagementService().modifyLossEvent(context,lossEvent);
        form.setClaimObjectReference(topLevelFolder.getObjectReference());

        //create tree
        Collection<ClaimableBenefit> foundBenefits = getClaimableBenefits(form);
        buildClaimsHierarchyForSelectedBenefits(form.get_chk(), foundBenefits, topLevelFolder, form.getExternalReference());

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("findAndDisplay"));
        redirect.addParameter("externalReference", form.getExternalReference());
        return redirect;
    }

    public ActionForward save(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        ApplicationContext context = new ApplicationContext();
        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(form.getExternalReference());
        LossEvent event = getClaimManagementService().findLossEvents(context, search).get(0);

        event.setDescription(form.getDescription());
        if(form.getLossEventTime() == null || form.getLossEventTime().equals("")) {
            throw new IllegalStateException("Loss Event must have an event time");
        }
        event.setEventTime(new DateTime(parseDateTime(form.getLossEventTime())));
        event.setName(form.getLossEventName());
        if(form.getTypeId() == null) {
            throw new IllegalStateException("Loss Event must have a type");
        }
        event.setTypeId(form.getTypeId());

        getClaimManagementService().modifyLossEvent(context, event);

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("findAndDisplay"));
        redirect.addParameter("externalReference", form.getExternalReference());
        return redirect;
    }

    public ActionForward initiateClaim(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        ApplicationContext context = new ApplicationContext();

        //list of claimable benefits
        Collection<ClaimableBenefit> foundBenefits = getClaimableBenefits(form);

        //list found benefits on UI
        ArrayList<ClaimableBenefitTableEntry> entries = new ArrayList<>(foundBenefits.size());
        for(ClaimableBenefit claimableBenefit:foundBenefits) {
            entries.add(new ClaimableBenefitTableEntry(claimableBenefit));
        }

        form.setClaimableBenefits(entries);
        form.setEditing(true);

        return actionMapping.getInputForward();

    }

    private Collection<ClaimableBenefit> getClaimableBenefits(LossEventForm form) {
        ApplicationContext applicationContext = getApplicationContext();
        //first get the loss event we need
        LossEvent lossEvent = getLossEvent(form.getExternalReference());
        List<RoleInLossEventDTO> rolesInLossEvent = lossEvent.getRolesInLossEvent();

        //find the role in loss event we need
        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();

        RoleInLossEventDTO victimRole = claimFunctionalityUtil.
            findRoleOfType(rolesInLossEvent, CoreTypeReference.VICTIMINLOSSEVENT.getValue());
        if (null != victimRole) {
            PartyRole partyRole = getCustomerRelationshipService()
                    .getPartyRole(applicationContext, victimRole.getRolePlayer());
            String insuredExternalReference = getCustomerRelationshipService()
                    .findPartyExternalReferenceByObjectReference(
                            applicationContext, partyRole.getRolePlayer().getObjectReference());

            //find the benefits for this loss event through a store procedure
            return getPolicyAdminService().findClaimableBenefits(applicationContext, insuredExternalReference,
                                                                 CoreTypeReference.INSURED.getValue(), DateTime.now()
            );
        }
        return new ArrayList<>(0);
    }

    private LossEvent getLossEvent(String lossEventExternalReference) {
        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(lossEventExternalReference);
        return getClaimManagementService().findLossEvents(getApplicationContext(), search).get(0);
    }

    private void populatePageElements(LossEventForm form, HttpServletRequest request) {
        form.setEditing(true);
        if(form.getExternalReference() != null) {
            ApplicationContext applicationContext = getApplicationContext();
            LossEventSearchCriteria search = new LossEventSearchCriteria();
            search.setExternalReference(form.getExternalReference());

            LossEvent lossEvent = getClaimManagementService().findLossEvents(applicationContext, search).get(0);

            form.setObjectReference(lossEvent.getObjectReference());
            form.setLossEventName(lossEvent.getName());
            form.setDescription(lossEvent.getDescription());
            form.setTypeId(lossEvent.getTypeId());
            form.setStartDate(lossEvent.getStartDate().format());
            form.setEndDate(lossEvent.getEndDate().format());
            form.setLossEventTime(lossEvent.getEventTime().format());//TODO locale-specific format (and/or remove seconds?)
            form.setTypeId(lossEvent.getTypeId());

            Collection<RoleInLossEvent> roleInLossEvents = getClaimManagementService()
                    .getRolesInLossEventForLossEvent(applicationContext, lossEvent.getObjectReference());

            //populate party name/agreement fields
            ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
            for (RoleInLossEvent roleInLossEvent : roleInLossEvents) {
                if (roleInLossEvent.getTypeId().equals(CoreTypeReference.VICTIMINLOSSEVENT.getValue())) {
                    //victim
                    form.setVictimName(claimFunctionalityUtil.
                        getPartyNameForRoleInLossEvent(getApplicationContext(),
                        getCustomerRelationshipService(), getProductDevelopmentService(), roleInLossEvent));
                } else if (roleInLossEvent.getTypeId().equals(CoreTypeReference.CLAIMANTINLOSSEVENT.getValue())) {
                    //claimant
                    form.setClaimClaimantName(claimFunctionalityUtil.
                        getPartyNameForRoleInLossEvent(getApplicationContext(),
                        getCustomerRelationshipService(), getProductDevelopmentService(), roleInLossEvent));
                } else if(roleInLossEvent.getTypeId().equals(CoreTypeReference.AGREEMENTINLOSSEVENT.getValue())) {
                    //agreement
                    ObjectReference agreementReference = roleInLossEvent.getRolePlayer();
                    String agreementExternalReference = getPolicyAdminService()
                            .getLegallyBindingTopLevelAgreement(getApplicationContext(), agreementReference)
                            .getExternalReference();
                    form.setAgreementNumber(agreementReference.toString() + " " + agreementExternalReference);
                }
            }

            // set claim objectreference on the form.
            form.setClaimObjectReference(lossEvent.getClaimObjectReference());
            form.setClaimExternalReference(lossEvent.getClaimExternalReference());

        }
    }

    public ActionForward addVictimRolePlayer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {

        return addRolePlayer(actionMapping, actionForm, request, response, "Victim");
    }

    public ActionForward addClaimClaimantRolePlayer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {

        return addRolePlayer(actionMapping, actionForm, request, response, "ClaimClaimant");
    }

    private ActionForward addRolePlayer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                              HttpServletResponse response, String roleKindToLink) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;

        save(actionMapping, actionForm, request, response);//save our changes before redirecting

        //set attributes needed to fetch a role player and make it back here
        request.setAttribute(CallBackUtility.PAGE, "/secure/claim/lossevent/edit.do?method=.addRolePlayerResponse");
        request.setAttribute(CallBackUtility.KEY, CALLBACK_KEY);
        request.getSession().setAttribute(LOSS_EVENT_REFERENCE_KEY, form.getExternalReference());
        request.getSession().setAttribute(PartyGuiUtility.PARTY_ROLE_CREATION_OVERRIDE_KEY, "TRUE");
        request.getSession().setAttribute("roleKindToLink", roleKindToLink);
        request.setAttribute(CallBackUtility.INTENDED_MAPPING, "addRolePlayer");

        request.setAttribute(CallBackUtility.SCOPE, "request");
        CallBackUtility.addCallBack(request, response);
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForward("addRolePlayer"));
        redirect.addParameter("roleKindToLink", roleKindToLink);
        return redirect;
    }


    public ActionForward addRolePlayerResponse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        form.setExternalReference((String)request.getSession().getAttribute(LOSS_EVENT_REFERENCE_KEY));
        request.getSession().removeAttribute(LOSS_EVENT_REFERENCE_KEY);
        String roleKindToLink = (String)request.getSession().getAttribute("roleKindToLink");
        request.getSession().removeAttribute("roleKindToLink");

        ObjectReference selectedPerson = (ObjectReference)request.getAttribute(CALLBACK_KEY);

        //store in RoleInLossEventDTO of lossEvent
        ApplicationContext applicationContext = getApplicationContext();
        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(form.getExternalReference());
        LossEvent lossEvent = getClaimManagementService().findLossEvents(applicationContext, search).get(0);

        CoreTypeReference partyRoleType= null;
        CoreTypeReference roleInLossEventType= null;
        //depends on button pressed
        if(roleKindToLink.equals("Victim")) {
            partyRoleType = CoreTypeReference.LOSSEVENTVICTIM;
            roleInLossEventType = CoreTypeReference.VICTIMINLOSSEVENT;
        } else if(roleKindToLink.equals("ClaimClaimant")) {
            partyRoleType = CoreTypeReference.LOSSEVENTCLAIMANT;
            roleInLossEventType = CoreTypeReference.CLAIMANTINLOSSEVENT;
        }

        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
        PartyRole partyRole = claimFunctionalityUtil.
            updatePartyRole(applicationContext, getCustomerRelationshipService(), lossEvent.getObjectReference(), selectedPerson, partyRoleType);
        RoleInLossEventDTO roleInLossEvent = new RoleInLossEventDTO();
        populateRoleInLossEvent(applicationContext, roleInLossEvent, lossEvent, partyRole.getObjectReference(), roleInLossEventType);

        //TODO clear the old one or disable button if already added?
        getClaimManagementService().addRoleInLossEventForLossEvent(applicationContext, lossEvent.getObjectReference(), roleInLossEvent);

        populatePageElements(form, request);


        return actionMapping.getInputForward();
    }

    public ActionForward addAgreement(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;

        save(actionMapping, actionForm, request, response);//save our changes before redirecting

        //set attributes needed to fetch an agreement and make it back here
        request.setAttribute(CallBackUtility.PAGE, "/secure/claim/lossevent/edit.do?method=.addAgreementResponse");
        request.setAttribute(CallBackUtility.KEY, CALLBACK_KEY);
        request.getSession().setAttribute(LOSS_EVENT_REFERENCE_KEY, form.getExternalReference());
        request.setAttribute(CallBackUtility.SCOPE, "request");
        request.setAttribute(CallBackUtility.INTENDED_MAPPING, "findAgreement");

        CallBackUtility.addCallBack(request, response);
        return new ActionRedirect(actionMapping.findForward("findAgreement"));
    }

    public ActionForward addAgreementResponse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        LossEventForm form = (LossEventForm) actionForm;
        form.setExternalReference((String)request.getSession().getAttribute(LOSS_EVENT_REFERENCE_KEY));
        request.getSession().removeAttribute(LOSS_EVENT_REFERENCE_KEY);

        //store on loss event
        ApplicationContext applicationContext = getApplicationContext();
        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(form.getExternalReference());
        LossEvent lossEvent = getClaimManagementService().findLossEvents(applicationContext, search).get(0);

        ObjectReference agreementReference = (ObjectReference)request.getAttribute(CALLBACK_KEY);

        //create new role in loss event
        RoleInLossEventDTO roleInLossEvent = new RoleInLossEventDTO();
        populateRoleInLossEvent(applicationContext, roleInLossEvent, lossEvent, agreementReference, CoreTypeReference.AGREEMENTINLOSSEVENT);

        //TODO clear the old one or disable button if already added?
        getClaimManagementService().addRoleInLossEventForLossEvent(applicationContext, lossEvent.getObjectReference(), roleInLossEvent);

        populatePageElements(form, request);

        return actionMapping.getInputForward();
    }

    //applicationContext, roleInLossEvent, lossEvent, partyRole.getObjectReference()
    private void populateRoleInLossEvent(ApplicationContext applicationContext, RoleInLossEventDTO roleInLossEvent,
            LossEvent lossEvent, ObjectReference rolePlayer, CoreTypeReference roleInLossEventType) {

        roleInLossEvent.setRolePlayer(rolePlayer);
        roleInLossEvent.setTypeId(roleInLossEventType.getValue());
        roleInLossEvent.setEffectivePeriod(new DatePeriod(lossEvent.getStartDate(), lossEvent.getEndDate()));
        roleInLossEvent.setApplicationContextId(applicationContext.getId());
        roleInLossEvent.setContext(lossEvent.getObjectReference());
    }

    private void buildClaimsHierarchyForSelectedBenefits(String[] checkedItems,
            Collection<ClaimableBenefit> benefitList,
            IClaimFolder topLevelFolder, String lossEventExternalReferecne) {
        ApplicationContext applicationContext = getApplicationContext();
        ObjectReference lastTopLevelAgreement = null;
        IClaimFolder secondLevelFolder = null;

        LossEvent lossEvent = getLossEvent(lossEventExternalReferecne);
        List<RoleInLossEventDTO> roleInLossEvents = lossEvent.getRolesInLossEvent();

        for(ClaimableBenefit claimableBenefit: benefitList) {
            if(checkIfNotTicked(checkedItems, claimableBenefit))continue;//skip unselected items
            String agreementExternalRef = getPolicyAdminService().getAgreement(getApplicationContext(), claimableBenefit.getClaimableCoverage()).getExternalReference();

            ObjectReference currentTopLevelAgreement = claimableBenefit.getTopLevelAgreement();
            String currentTLAExternalRef = getPolicyAdminService().getAgreement(applicationContext, currentTopLevelAgreement).getExternalReference();

            if(!currentTopLevelAgreement.equals(lastTopLevelAgreement)) {
                //changed: create new folder on second level
                secondLevelFolder = new ClaimFolder();
                inheritProperties(topLevelFolder, secondLevelFolder);
                secondLevelFolder.addStatus(DateTime.now(), DateTime.FUTURE, ClaimFolderState.OPEN);
                secondLevelFolder.setDescription("Policy Object Reference: " + currentTLAExternalRef);

                secondLevelFolder = getClaimManagementService().establishClaimFolder(applicationContext, secondLevelFolder);
                getClaimManagementService().addClaimFolderComponent(applicationContext, topLevelFolder.getObjectReference(), secondLevelFolder);

                lastTopLevelAgreement = currentTopLevelAgreement;
            }
            //add to folder on second level (creating third level)
            IElementaryClaim thirdLevelClaim = new ElementaryClaim();
            inheritProperties(topLevelFolder, thirdLevelClaim);
            thirdLevelClaim.addStatus(DateTime.now(), DateTime.FUTURE, ElementaryClaimState.DRAFTED);
            thirdLevelClaim.setDescription("Claim Benefit Object Reference: " + agreementExternalRef);

            //convert roles from loss event to roles in elementaryClaim
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.VICTIMINLOSSEVENT, CoreTypeReference.LIFEINSUREDINCLAIM);
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.CLAIMANTINLOSSEVENT, CoreTypeReference.BENEFICIARYINCLAIM);
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.AGREEMENTINLOSSEVENT, CoreTypeReference.AGREEMENTINCLAIM);
            //Claimed benefit
            RoleInClaim claimedBenefitRole = new RoleInClaim();
            claimedBenefitRole.setRolePlayer(claimableBenefit.getClaimableCoverage());
            claimedBenefitRole.setType(CoreTypeReference.COVERAGEAGREEMENTINCLAIM);
            thirdLevelClaim.addRoleInClaim(claimedBenefitRole);

            thirdLevelClaim = getClaimManagementService().establishElementaryClaim(applicationContext, thirdLevelClaim);

            establishRoleInClaimPartyRole(roleInLossEvents, thirdLevelClaim, CoreTypeReference.VICTIMINLOSSEVENT, CoreTypeReference.CLAIMLIFEINSURED);
            establishRoleInClaimPartyRole(roleInLossEvents, thirdLevelClaim, CoreTypeReference.CLAIMANTINLOSSEVENT, CoreTypeReference.CLAIMCLAIMANT);
            establishRoleInClaimPartyRole(roleInLossEvents, thirdLevelClaim, CoreTypeReference.AGREEMENTINLOSSEVENT, null);

            getClaimManagementService().addClaimFolderComponent(applicationContext, secondLevelFolder.getObjectReference(), thirdLevelClaim);
        }
    }

    private void establishRoleInClaimPartyRole(List<RoleInLossEventDTO> roleInLossEvents,
                                               IElementaryClaim elementaryClaim,
                                               CoreTypeReference fromRoleType,
                                               CoreTypeReference claimPartyRole) {
        if (claimPartyRole == null) {
            return;
        }

        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
        RoleInLossEventDTO foundRole = claimFunctionalityUtil.findRoleOfType(roleInLossEvents, fromRoleType.getValue());

        Party party = getCustomerRelationshipService().getPartyForPartyOrPartyRole(getApplicationContext(), foundRole.getRolePlayer(), null);
        claimFunctionalityUtil.updatePartyRole(getApplicationContext(),
                getCustomerRelationshipService(), elementaryClaim.getObjectReference(), party.getObjectReference(), claimPartyRole);
    }

    private void createRoleInClaimFromRoleInLossEvent(List<RoleInLossEventDTO> roleInLossEvents,
                                                      IElementaryClaim elementaryClaim,
                                                      CoreTypeReference fromRoleType, CoreTypeReference toRoleType) {
        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
        RoleInLossEventDTO foundRole = claimFunctionalityUtil.findRoleOfType(roleInLossEvents, fromRoleType.getValue());
        if (null != foundRole) {
            RoleInClaim convertedRole = new RoleInClaim();
            convertedRole.setRolePlayer(foundRole.getRolePlayer());
            convertedRole.setType(toRoleType);
            elementaryClaim.addRoleInClaim(convertedRole);
        }
    }

    private boolean checkIfNotTicked(String[] checkedItems, ClaimableBenefit claimableBenefit) {
        boolean isTicked = false;
        for(String selectedItem:checkedItems) {
            if(claimableBenefit.getClaimableCoverage().toString().equals(selectedItem)) {
                isTicked = true;
                break;
            }
        }
        if(!isTicked) return true;
        return false;
    }

    private void inheritProperties(IClaimFolder parent, IClaim child) {
        child.setStartDate(parent.getStartDate());
        child.setEndDate(parent.getEndDate());
        child.setApplicationContextId(getApplicationContext().getId());
        child.setExternalReference(getClaimManagementService().getNextClaimNumber(getApplicationContext()) + "");
    }
}