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
import com.silvermoongroup.claim.domain.intf.IClaim;
import com.silvermoongroup.claim.domain.intf.IClaimFolder;
import com.silvermoongroup.claim.domain.intf.IElementaryClaim;
import com.silvermoongroup.claim.enumeration.ClaimFolderState;
import com.silvermoongroup.claim.enumeration.ElementaryClaimState;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.policysearch.dto.ClaimableBenefit;
import com.silvermoongroup.fsa.web.claimmanagement.ClaimableBenefitTableEntry;
import com.silvermoongroup.fsa.web.claimmanagement.form.AddClaimFolderHierarchyForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.party.domain.PartyRole;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author Laurens Weyn
 */
public class AddClaimFolderHierarchyAction extends AbstractLookupDispatchAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AddClaimFolderHierarchyForm form = (AddClaimFolderHierarchyForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }

    public ActionForward submit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                                  HttpServletResponse response) {

        //find claimable benefits:
        AddClaimFolderHierarchyForm form = (AddClaimFolderHierarchyForm) actionForm;
        Collection<ClaimableBenefit> foundBenefits = getClaimableBenefits(form);

        //sort by top level agreement, needed for iteration when building claims:
        ArrayList<ClaimableBenefit> foundBenefitList = new ArrayList<>(foundBenefits);
        Collections.sort(foundBenefitList, new Comparator<ClaimableBenefit>() {
            @Override
            public int compare(ClaimableBenefit o1, ClaimableBenefit o2) {
                return o1.getTopLevelAgreement().getObjectId().compareTo(o2.getTopLevelAgreement().getObjectId());
            }
        });
        //build the claims:
        //IClaimFolder topLevelFolder = getTopClaimFolder(form);

        IClaimFolder topLevelFolder = new ClaimFolder();
        //      topLevelFolder.setStartDate(lossEvent.getStartDate());
        //      topLevelFolder.setEndDate(lossEvent.getEndDate());
        topLevelFolder.setApplicationContextId(getApplicationContext().getId());
        //topLevelFolder.addClaimedLossEvent(lossEvent);
        topLevelFolder.addStatus(DateTime.now(), DateTime.FUTURE, ClaimFolderState.OPEN);
        //      topLevelFolder.setDescription("Loss Event External Reference: " + lossEventExternalReference);
        getClaimManagementService().establishClaimFolder(getApplicationContext(), topLevelFolder);

        buildClaimsHierarchyForSelectedBenefits(form.get_chk(), foundBenefitList, topLevelFolder, form.getLossEventExternalReference());

        //success message:
        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.addclaimfolderhierarchy.message.saveclaimfolderhierarchysuccessful"));
        saveInformationMessages(request, messages);

        //TODO where should we redirect to when we're done with the claim folder?
        return actionMapping.findForward("return");
    }

    private void buildClaimsHierarchyForSelectedBenefits(String[] checkedItems,
                                                         ArrayList<ClaimableBenefit> sortedBenefitList,
                                                         IClaimFolder topLevelFolder, String lossEventExternalReferecne) {
        ApplicationContext applicationContext = getApplicationContext();
        ObjectReference lastTopLevelAgreement = null;
        IClaimFolder secondLevelFolder = null;

        LossEvent lossEvent = getLossEvent(lossEventExternalReferecne);
        List<RoleInLossEventDTO> roleInLossEvents = lossEvent.getRolesInLossEvent();

        for(ClaimableBenefit claimableBenefit: sortedBenefitList) {
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

            //Claimed benefit
            RoleInClaim claimedBenefitRole = new RoleInClaim();
            claimedBenefitRole.setRolePlayer(claimableBenefit.getClaimableCoverage());
            claimedBenefitRole.setType(CoreTypeReference.COVERAGEAGREEMENTINCLAIM);
            thirdLevelClaim.addRoleInClaim(claimedBenefitRole);

            thirdLevelClaim = getClaimManagementService().establishElementaryClaim(applicationContext, thirdLevelClaim);
            getClaimManagementService().addClaimFolderComponent(applicationContext, secondLevelFolder.getObjectReference(), thirdLevelClaim);

            //convert roles from loss event to roles in elementaryClaim
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.VICTIMINLOSSEVENT, CoreTypeReference.LIFEINSUREDINCLAIM, CoreTypeReference.CLAIMLIFEINSURED);
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.CLAIMANTINLOSSEVENT, CoreTypeReference.BENEFICIARYINCLAIM, CoreTypeReference.CLAIMCLAIMANT);
            createRoleInClaimFromRoleInLossEvent(roleInLossEvents, thirdLevelClaim, CoreTypeReference.AGREEMENTINLOSSEVENT, CoreTypeReference.AGREEMENTINCLAIM, null);
        }
    }

    private void createRoleInClaimFromRoleInLossEvent(List<RoleInLossEventDTO> roleInLossEvents,
            IElementaryClaim elementaryClaim,
            CoreTypeReference fromRoleType, CoreTypeReference toRoleType,
            CoreTypeReference claimPartyRole) {
        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
        RoleInLossEventDTO foundRole = claimFunctionalityUtil.findRoleOfType(roleInLossEvents, fromRoleType.getValue());
        if (null != foundRole) {
            RoleInClaim convertedRole = new RoleInClaim();
            convertedRole.setRolePlayer(foundRole.getRolePlayer());
            convertedRole.setType(toRoleType);
            elementaryClaim.addRoleInClaim(convertedRole);

            if(claimPartyRole != null) {
                ClaimFunctionalityUtil claimFunctionalityUtil1 = new ClaimFunctionalityUtil();
                claimFunctionalityUtil1.updatePartyRole(getApplicationContext(),
                    getCustomerRelationshipService(), elementaryClaim.getObjectReference(), foundRole.getRolePlayer(), claimPartyRole);
            }

        }
    }

    private void inheritProperties(IClaimFolder parent, IClaim child) {
        child.setStartDate(parent.getStartDate());
        child.setEndDate(parent.getEndDate());
        child.setApplicationContextId(getApplicationContext().getId());
        child.setExternalReference(getClaimManagementService().getNextClaimNumber(getApplicationContext()) + "");
    }

/*    private IClaimFolder getTopClaimFolder(AddClaimFolderHierarchyForm form) {
        ClaimFolderSearchCriteria claimFolderSearch = new ClaimFolderSearchCriteria();
        claimFolderSearch.setExternalReference(form.getClaimFolderExternalReference());
        return getClaimManagementService()
                .findClaimFolders(getApplicationContext(), claimFolderSearch).get(0);
    }*/

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

    private void populateStaticPageElements(AddClaimFolderHierarchyForm form, HttpServletRequest request) {

        Collection<ClaimableBenefit> foundBenefits = getClaimableBenefits(form);

        //list found benefits on UI
        //TODO this wrapper class likely isn't needed
        ArrayList<ClaimableBenefitTableEntry> entries = new ArrayList<>(foundBenefits.size());
        for(ClaimableBenefit claimableBenefit:foundBenefits) {
            entries.add(new ClaimableBenefitTableEntry(claimableBenefit));
        }

        form.setClaimableBenefits(entries);
    }

    private Collection<ClaimableBenefit> getClaimableBenefits(AddClaimFolderHierarchyForm form) {
        ApplicationContext applicationContext = getApplicationContext();
        //first get the loss event we need
        LossEvent lossEvent = getLossEvent(form);
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

    private LossEvent getLossEvent(AddClaimFolderHierarchyForm form) {
        return getLossEvent(form.getLossEventExternalReference());
    }

    private LossEvent getLossEvent(String lossEventExternalReference) {
        LossEventSearchCriteria search = new LossEventSearchCriteria();
        search.setExternalReference(lossEventExternalReference);
        return getClaimManagementService().findLossEvents(getApplicationContext(), search).get(0);
    }
}