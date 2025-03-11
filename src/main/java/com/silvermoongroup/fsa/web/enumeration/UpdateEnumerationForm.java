package com.silvermoongroup.fsa.web.enumeration;

import com.silvermoongroup.common.domain.EnumerationReference;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class UpdateEnumerationForm extends AbstractEnumerationForm {

    private static final long serialVersionUID = 1L;
    
    private EnumerationReference enumerationReference;
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "update".equalsIgnoreCase(actionName);
    }
    
    public EnumerationReference getEnumerationReference() {
        return enumerationReference;
    }

    public void setEnumerationReference(EnumerationReference enumerationReference) {
        this.enumerationReference = enumerationReference;
    }
}
