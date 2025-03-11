/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.RoleInClaimOffer;
import com.silvermoongroup.fsa.web.claimmanagement.form.RolesInClaimOfferForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Form controller for the display of claim offer roles.
 *
 * @author Justin Walsh
 */
public class RolesInClaimOfferAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) {

        RolesInClaimOfferForm form = (RolesInClaimOfferForm) actionForm;
        List<RoleInClaimOffer> rolesInClaimOffer = getClaimManagementService().getRolesInClaimOffer(getApplicationContext(), form.getObjectReference());
        Collections.sort(rolesInClaimOffer, new Comparator<RoleInClaimOffer>() {
            @Override
            public int compare(RoleInClaimOffer o1, RoleInClaimOffer o2) {
                return o1.getObjectReference().getObjectId().compareTo(o2.getObjectReference().getObjectId());
            }
        });
        form.setRolesInClaimOffer(rolesInClaimOffer);
        return actionMapping.getInputForward();
    }
}
