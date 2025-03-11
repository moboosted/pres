package com.silvermoongroup.fsa.web.enumeration;

import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

public class AddEnumerationForm extends AbstractEnumerationForm {

    private static final long serialVersionUID = 1L;
    
    @Override
    protected boolean needsValidation(ActionMapping mapping, HttpServletRequest request, String actionName) {
        return "add".equalsIgnoreCase(actionName);
    }
}
