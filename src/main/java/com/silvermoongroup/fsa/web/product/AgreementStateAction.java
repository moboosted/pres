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
 * @author Justin Walsh
 */
public class AgreementStateAction extends AbstractLookupDispatchAction {

    @Override
    protected Map<String, String> getKeyMethodMap() {
        Map<String, String> map = new HashMap<>();
        map.put("page.agreementstate.action.back", "back");
        map.put("page.agreementstate.action.update", "update");
        return map;
    }

    /**
     * Display an empty page
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AgreementStateForm form = (AgreementStateForm)actionForm;
        if (form.getId() == null) {
            return new ActionRedirect("find");
        }

        AgreementState state = getProductDevelopmentService().getAgreementState(getApplicationContext(), form.getId());
        form.setName(state.getName());
        form.setLegallyBinding(state.isLegallyBinding());
        form.setRiskBearing(state.isRiskBearing());

        return actionMapping.getInputForward();
    }

    /**
     * POST: update
     */
    public ActionForward update(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        AgreementStateForm form = (AgreementStateForm)actionForm;
        AgreementState possibleDuplicate = getProductDevelopmentService().findAgreementStateByName(getApplicationContext(), form.getName());
        if (!possibleDuplicate.getId().equals(form.getId())) {
            ActionMessages messages = getMessages(request);
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("page.agreementstate.message.duplicate", form.getName()));
            saveErrorMessages(request, messages);
            return actionMapping.getInputForward();
        }

        AgreementState state = getProductDevelopmentService().getAgreementState(getApplicationContext(), form.getId());
        state.setName(form.getName());
        state.setLegallyBinding(form.isLegallyBinding());
        state.setRiskBearing(form.isRiskBearing());
        getProductDevelopmentService().modifyAgreementState(getApplicationContext(), state);
        addInformationMessage(request, "page.agreementstate.message.success", form.getName());

        return new ActionRedirect(actionMapping.findForward("find"));
    }

    public ActionForward back(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return new ActionRedirect(actionMapping.findForward("find"));
    }
}
