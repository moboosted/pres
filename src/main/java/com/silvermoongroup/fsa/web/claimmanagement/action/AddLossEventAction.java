/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.claim.domain.LossEvent;
import com.silvermoongroup.claim.domain.intf.ILossEvent;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.fsa.web.claimmanagement.form.LossEventForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Laurens Weyn
 */
public class AddLossEventAction extends AbstractLookupDispatchAction {

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }


    /**
     * POST: create LossEvent
     */
    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        LossEventForm form = (LossEventForm) actionForm;
        populateStaticPageElements(form, request);

        ILossEvent lossEvent = new LossEvent();

        lossEvent.setDescription(form.getDescription());
        if(form.getLossEventTime() == null || form.getLossEventTime().equals("")) {
            throw new IllegalStateException("Loss Event must have an event time");
        }
        lossEvent.setEventTime(new DateTime(parseDateTime(form.getLossEventTime())));
        lossEvent.setName(form.getLossEventName());
        if(form.getTypeId() == null) {
            throw new IllegalStateException("Loss Event must have a type");
        }
        lossEvent.setTypeId(form.getTypeId());
        /*
         * Create an EXT claim management maven module (extensibility)
         * The EXT claim management module must extend the claim management service
         * and expose an operation called establishLossEvent(ApplicationContext, LossEvent, Types[])
         * this operation will create a LossEvent with a number of roles of the given types.
         *
         * default 1 policynumber, can add multiple later
         * users can create claim folder trees based on loss events
         *
         */
        Long[] typeIDs = {
            CoreTypeReference.VICTIMINLOSSEVENT.getValue(),
            CoreTypeReference.CLAIMANTINLOSSEVENT.getValue()
        };

        lossEvent = getClaimManagementService().establishLossEvent(getApplicationContext(), lossEvent, typeIDs);

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("edit"));
        redirect.addParameter("externalReference", lossEvent.getExternalReference());
        return redirect;
    }


    private void populateStaticPageElements(LossEventForm form, HttpServletRequest request) {
        form.setEditing(false);
    }
}