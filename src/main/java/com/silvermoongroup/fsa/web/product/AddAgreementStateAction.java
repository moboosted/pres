/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.product;

import com.silvermoongroup.businessservice.productmanagement.dto.AgreementState;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for adding an agreement state.
 *
 * @author Justin Walsh
 */
public class AddAgreementStateAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.addagreementstate.action.add", "add");
        map.put("page.addagreementstate.action.back", "back");
        return map;
    }

    /**
     * Display an empty page
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        return actionMapping.getInputForward();
    }

    /**
     * POST: Add an account mapping
     */
    public ActionForward add(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        AddAgreementStateForm form = (AddAgreementStateForm) actionForm;

        // is there an agreement state with the same name?
        AgreementState existing = getProductDevelopmentService().findAgreementStateByName(getApplicationContext(), form.getName());
        if (existing != null) {
            ActionMessages messages = getMessages(request);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.addagreementstate.message.duplicate", form.getName()));
            saveErrorMessages(request, messages);
            return actionMapping.getInputForward();
        }

        AgreementState state = new AgreementState();
        state.setName(form.getName());
        state.setLegallyBinding(form.isLegallyBinding());
        state.setRiskBearing(form.isRiskBearing());

        getProductDevelopmentService().establishAgreementState(getApplicationContext(), state);

        addInformationMessage(request, "page.addagreementstate.message.success", form.getName());

        return new ActionRedirect(actionMapping.findForward("find"));
    }

    /**
     * Back to the find page
     */
    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForward("find"));
    }
}
