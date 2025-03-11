/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.businessservice.claimmanagement.dto.ClaimTreeNode;
import com.silvermoongroup.businessservice.claimmanagement.dto.ClaimTreeNodeClassification;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller which populates the form bean with content relevant to the selection of the node in the navigator.
 *
 * @author Justin Walsh
 */
public class ClaimNavigatorContentAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                        HttpServletResponse response) {

        DynaActionForm form = (DynaActionForm) actionForm;
        ObjectReference nodeObjectReference = (ObjectReference) form.get("nodeObjectReference");

        ClaimTreeNode node = getClaimManagementService().getClaimTreeNode(getApplicationContext(), nodeObjectReference);
        if (node.getClassification().equals(ClaimTreeNodeClassification.CF)) {
            return getClaimFolderContent(actionMapping, form, nodeObjectReference);
        } else if (node.getClassification().equals(ClaimTreeNodeClassification.EC)) {
            return getElementaryClaimContent(actionMapping, form, nodeObjectReference);
        } else if (node.getClassification().equals(ClaimTreeNodeClassification.CO)) {
            return getClaimOfferContent(actionMapping, form, nodeObjectReference);
        }

        return null;
    }

    private ActionForward getClaimFolderContent(ActionMapping actionMapping, DynaActionForm form, ObjectReference nodeObjectReference) {
        form.set("claimFolder", getClaimManagementService().getClaimFolder(getApplicationContext(), nodeObjectReference));
        return actionMapping.findForward("claimfolder");
    }

    private ActionForward getElementaryClaimContent(ActionMapping actionMapping, DynaActionForm form, ObjectReference nodeObjectReference) {
        form.set("elementaryClaim", getClaimManagementService().getElementaryClaim(getApplicationContext(), nodeObjectReference));
        return actionMapping.findForward("elementaryclaim");
    }

    private ActionForward getClaimOfferContent(ActionMapping actionMapping, DynaActionForm form, ObjectReference nodeObjectReference) {
        form.set("claimOffer", getClaimManagementService().getClaimOffer(getApplicationContext(), nodeObjectReference));
        return actionMapping.findForward("claimoffer");
    }
}
