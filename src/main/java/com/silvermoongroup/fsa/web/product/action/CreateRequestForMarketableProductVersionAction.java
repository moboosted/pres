/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.product.action;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.product.form.CreateRequestForMarketableProductVersionForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a request for a product version.
 * 
 * @author Justin Walsh
 */
public class CreateRequestForMarketableProductVersionAction extends AbstractLookupDispatchAction {
    
    public CreateRequestForMarketableProductVersionAction() {
    }
    
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        
        CreateRequestForMarketableProductVersionForm form = (CreateRequestForMarketableProductVersionForm)actionForm;
        ObjectReference requestObjectReference = 
                getPolicyAdminService().createRequestForProductVersion(
                        getApplicationContext(), 
                        form.getMpvObjectReference(), 
                        form.getRequestKindId());
        
        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("success"));
        redirect.addParameter("contextObjectReference", requestObjectReference.toString());
        return redirect;
    }
}
