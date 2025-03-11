package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.EnumerationType;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import com.silvermoongroup.fsa.web.struts.ActionRedirectFactory;
import com.silvermoongroup.fsa.web.struts.RedirectException;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewEnumerationsAction extends AbstractLookupDispatchAction {

    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        
        ViewEnumerationsForm form = (ViewEnumerationsForm) actionForm;
        if (form.getEnumerationTypeId() == null) {
            log.error("Enumeration Id required");
            throw new RedirectException(new ActionRedirect(actionMapping.findForwardConfig("enumerationTypes")));
        }
        
        String enumerationTypeName = getConfigurationService().getEnumerationType(getApplicationContext(), form.getEnumerationTypeId()).getTypeName();
        
        form.setEnumerationTypeName(enumerationTypeName);
        form.setEnumerations(getProductDevelopmentService().findEnumerationsByTypeAndPeriod(getApplicationContext(),
                form.getEnumerationTypeId(), null));
        return actionMapping.getInputForward();
    }

    /**
     * The user wishes to add an enumeration
     */
    public ActionForward add(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        
        ViewEnumerationsForm form = (ViewEnumerationsForm) actionForm;
        ActionRedirect rd = new ActionRedirect(mapping.findForwardConfig("add"));
        rd.addParameter("enumerationTypeId", form.getEnumerationTypeId());
        return rd;
    }
    
    
    /**
     * The user wishes to modify an enumeration
     */
    public ActionForward update(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        
        ViewEnumerationsForm form = (ViewEnumerationsForm) actionForm;
        ActionRedirect rd = new ActionRedirect(mapping.findForwardConfig("update"));
        rd.addParameter("enumerationReference", form.getEnumerationReference());
        return rd;
    }
    
    /**
     * The user wishes to terminate an enumeration
     */
    public ActionForward terminate(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        ViewEnumerationsForm form = (ViewEnumerationsForm) actionForm;
        IEnumeration enumeration = getProductDevelopmentService().getEnumeration(getApplicationContext(),
                form.getEnumerationReference());

        if (Date.today().isAfterOrEqual(enumeration.getEffectivePeriod().getEnd())) {
            ActionMessages messages = new ActionMessages();
            messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
                    "page.viewenumeration.message.alreadyterminated", enumeration.getName()));
            saveInformationMessages(request, messages);
        } else {
            getProductDevelopmentService()
                    .terminateEnumeration(getApplicationContext(), form.getEnumerationReference());
        }

        return ActionRedirectFactory.createRedirect(mapping.findForwardConfig("redirect"), form);
    }

    public ActionForward cancel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return redirectToTypesPage((ViewEnumerationsForm) actionForm, actionMapping);
    }

    protected ActionForward redirectToTypesPage(ViewEnumerationsForm actionForm, ActionMapping actionMapping) {
        ActionRedirect rd = new ActionRedirect(actionMapping.findForwardConfig("enumerationTypes"));
        return rd;
    }

}
