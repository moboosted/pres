/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.LossEvent;
import com.silvermoongroup.claim.criteria.LossEventSearchCriteria;
import com.silvermoongroup.fsa.web.claimmanagement.form.FindLossEventForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Laurens Weyn
 */
public class FindLossEventAction extends AbstractLookupDispatchAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return actionMapping.getInputForward();
    }

    /**
     * POST: The user searches for loss event(s)
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

        FindLossEventForm form = (FindLossEventForm) actionForm;

        LossEventSearchCriteria criteria = new LossEventSearchCriteria();

        if(form.getEffectiveDate() != null) criteria.setEffectiveDate(getTypeParser().parseDate(form.getEffectiveDate()));
        if(form.getLossEventDate() != null) criteria.setLossEventOn(getTypeParser().parseDate(form.getLossEventDate()));
        if(form.getExternalReference() != null) criteria.setExternalReference(form.getExternalReference());

        List<LossEvent> lossEvents = getClaimManagementService().findLossEvents(getApplicationContext(), criteria);
        Collections.sort(lossEvents, new Comparator<LossEvent>() {
            @Override
            public int compare(LossEvent o1, LossEvent o2) {
                return o1.getExternalReference().toLowerCase().compareTo(o2.getExternalReference().toLowerCase());
            }
        });
        form.setResults(lossEvents);

        return actionMapping.getInputForward();
    }

}
