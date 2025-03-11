/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.chain.commands.ActionCommandBase;
import org.apache.struts.chain.contexts.ActionContext;
import org.apache.struts.chain.contexts.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Invokes the method with the annotation {@link OnFormValidationFailure}
 */
public class ExecuteInvalidFormHookAction extends ActionCommandBase {
    
    private static Logger LOG = LoggerFactory.getLogger(ExecuteInvalidFormHookAction.class);

    @Override
    public boolean execute(ActionContext actionCtx) throws Exception {

        Boolean valid = actionCtx.getFormValid();

        if ((valid == null) || !valid.booleanValue()) {
            Action action = actionCtx.getAction();
            ActionForm actionForm = actionCtx.getActionForm();

            Method[] methods = action.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(OnFormValidationFailure.class)) {
                    ServletActionContext saContext = (ServletActionContext) actionCtx;
                    try {
                        method.invoke(action, actionForm, saContext.getRequest());
                    }
                    catch (Exception ex) {
                        LOG.error("Exception Occurred: ", ex);
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
