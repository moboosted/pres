/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.RoleInClaim;
import com.silvermoongroup.fsa.web.claimmanagement.form.RolesInClaimForm;
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
 * Form controller for the display of claim roles.
 *
 * @author Justin Walsh
 */
public class RolesInClaimAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) {

        RolesInClaimForm form = (RolesInClaimForm) actionForm;
        List<RoleInClaim> rolesInClaim = getClaimManagementService().getRolesInClaim(getApplicationContext(), form.getObjectReference());
        Collections.sort(rolesInClaim, new Comparator<RoleInClaim>() {
            @Override
            public int compare(RoleInClaim o1, RoleInClaim o2) {
                return o1.getObjectReference().getObjectId().compareTo(o2.getObjectReference().getObjectId());
            }
        });
        form.setRolesInClaim(rolesInClaim);
        return actionMapping.getInputForward();
    }
}
