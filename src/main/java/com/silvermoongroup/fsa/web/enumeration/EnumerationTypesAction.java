package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.domain.EnumerationType;
import com.silvermoongroup.fsa.web.common.action.AbstractLookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.util.LabelValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class EnumerationTypesAction extends AbstractLookupDispatchAction {

    /**
     * The default action - display the form
     */
    public ActionForward defaultExecute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        EnumerationTypesForm form = (EnumerationTypesForm) actionForm;
        populateLookupFormData(form);

        return actionMapping.getInputForward();
    }
    
    /**
     * The user wishes to view enumerations for an enumeration type
     */
    public ActionForward view(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        
        EnumerationTypesForm form = (EnumerationTypesForm) actionForm;
        ActionRedirect rd = new ActionRedirect(mapping.findForwardConfig("view"));
        rd.addParameter("enumerationTypeId", form.getEnumerationTypeId());
        
        return rd;
        
    }

    
    protected void populateLookupFormData(EnumerationTypesForm form) {
        Collection<EnumerationType> enumerationTypes = getConfigurationService().getAllEnumerationTypes(getApplicationContext());
        for (EnumerationType enumerationType : enumerationTypes) {
            form.getEnumerationTypes().add(
                    new LabelValueBean(enumerationType.getTypeName(), String.valueOf(enumerationType.getId())));
        }
    }
}
