/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.claim.criteria.ElementaryClaimSearchCriteria;
import com.silvermoongroup.claim.domain.RoleInClaim;
import com.silvermoongroup.claim.domain.intf.IElementaryClaim;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DatePeriod;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.claimmanagement.form.FindElementaryClaimForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Justin Walsh
 */
public class FindElementaryClaimAction extends AbstractLookupDispatchAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindElementaryClaimForm form = (FindElementaryClaimForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }

    /**
     * POST: The user searches for elementary claim(s)
     */
    public ActionForward find(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return ActionRedirectFactory.createRedirect(actionMapping.findForward("findAndDisplay"), actionForm);
    }

    /**
     * GET: Redirect after post.  Find and display the results
     */
    public ActionForward findAndDisplay(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        FindElementaryClaimForm form = (FindElementaryClaimForm) actionForm;
        populateStaticPageElements(form, request);

        ElementaryClaimSearchCriteria criteria = new ElementaryClaimSearchCriteria();

        criteria.setElementaryClaimId(form.getElementaryClaimId());
        criteria.setExternalReference(form.getExternalReference());
        for (String elementaryClaimState : form.getElementaryClaimStates()) {
            criteria.addElementaryClaimState(EnumerationReference.convertFromString(elementaryClaimState));
        }

        String dateOption = form.getDateOption();
        if (dateOption != null) {
            if (dateOption.equals("on")) {
                criteria.setEffectiveOn(parseDate(form.getEffectiveOn()));
            } else if (dateOption.equals("between")) {
                Date dateFrom = getTypeParser().parseDate(form.getEffectiveFrom());
                Date dateTo = getTypeParser().parseDate(form.getEffectiveTo());
                criteria.setEffectivePeriod(new DatePeriod(dateFrom, dateTo));
            }
        }

        List<IElementaryClaim> elementaryClaims = new ArrayList(getClaimManagementService().findElementaryClaims(getApplicationContext(), criteria));
        Collections.sort(elementaryClaims, new Comparator<IElementaryClaim>() {
            @Override
            public int compare(IElementaryClaim o1, IElementaryClaim o2) {
                return o1.getExternalReference().toLowerCase().compareTo(o2.getExternalReference().toLowerCase());
            }
        });
        form.setResults(elementaryClaims);

        return actionMapping.getInputForward();
    }

    public ActionForward validateCoverage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        FindElementaryClaimForm form = (FindElementaryClaimForm) actionForm;
        String selectedExternalReference = form.getClaimExternalReference();

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("validateCoverage"));
        redirect.addParameter("externalReference", selectedExternalReference);

        ClaimFunctionalityUtil claimFunctionalityUtil = new ClaimFunctionalityUtil();
        IElementaryClaim elementaryClaim = (IElementaryClaim) getElementaryClaim(form);

        for (RoleInClaim roleInClaim : getClaimManagementService().
            getRolesInClaimForClaim(getApplicationContext(), elementaryClaim.getObjectReference())) {

            if (roleInClaim.getTypeId().equals(CoreTypeReference.LIFEINSUREDINCLAIM.getValue()))
                redirect.addParameter("insuredName", claimFunctionalityUtil.getPartyNameForRoleInClaim(
                        getApplicationContext(), getCustomerRelationshipService(), getProductDevelopmentService(), roleInClaim));

            if (roleInClaim.getTypeId().equals(CoreTypeReference.BENEFICIARYINCLAIM.getValue()))
                redirect.addParameter("beneficiaryName", claimFunctionalityUtil.getPartyNameForRoleInClaim(
                        getApplicationContext(), getCustomerRelationshipService(), getProductDevelopmentService(), roleInClaim));

        }

        return redirect;
    }

    private IElementaryClaim getElementaryClaim(FindElementaryClaimForm form) {
        return getElementaryClaim(form.getClaimExternalReference());
    }

    private IElementaryClaim getElementaryClaim(String claimExternalReference) {
        ElementaryClaimSearchCriteria search = new ElementaryClaimSearchCriteria();
        search.setExternalReference(claimExternalReference);
        return getClaimManagementService().findElementaryClaims(getApplicationContext(), search).iterator().next();
    }

    private void populateStaticPageElements(FindElementaryClaimForm form, HttpServletRequest request) {
        if (form.getDateOption() == null) {
            form.setDateOption("any");
        }
    }
}
