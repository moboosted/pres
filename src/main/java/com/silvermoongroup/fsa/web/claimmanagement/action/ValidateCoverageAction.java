/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.claimmanagement.action;

import com.silvermoongroup.fsa.web.claimmanagement.form.ValidateCoverageForm;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Laurens Weyn
 */
public class ValidateCoverageAction extends AbstractLookupDispatchAction {


    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("button.cancel", "cancel");
        map.put("claim.button.validate", "validate");
        return map;
    }

    /**
     * GET: The page is rendered for the first time
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ValidateCoverageForm form = (ValidateCoverageForm) actionForm;
        populateStaticPageElements(form, request);
        return actionMapping.getInputForward();
    }



    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        ValidateCoverageForm form = (ValidateCoverageForm) actionForm;
        populateStaticPageElements(form, request);

        //System.out.println("Pressed cancel");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("return"));
        return redirect;
    }

    public ActionForward validate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        ValidateCoverageForm form = (ValidateCoverageForm) actionForm;
        populateStaticPageElements(form, request);

        //System.out.println("Pressed validate");

        ActionRedirect redirect = new ActionRedirect(actionMapping.findForwardConfig("return"));
        return redirect;
    }

    private void populateStaticPageElements(ValidateCoverageForm form, HttpServletRequest request) {

    }
}