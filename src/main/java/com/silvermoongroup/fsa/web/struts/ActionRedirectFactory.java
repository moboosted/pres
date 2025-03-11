/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionRedirect;
import org.apache.struts.config.ForwardConfig;

import java.lang.reflect.Field;

/**
 * Creates {@link ActionRedirect} using the fields on the form with the {@link RedirectParameter} annotation.
 * 
 * @author Justin Walsh
 */
public class ActionRedirectFactory {
    
    public static ActionRedirect createRedirect(ForwardConfig forwardConfig, ActionForm form) {
        
        ActionRedirect redirect = new ActionRedirect(forwardConfig);
        
        Field[] fields = form.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RedirectParameter.class)) {
                Object value;
                try {
                    value = PropertyUtils.getProperty(form, field.getName());
                } catch (Exception e) {
                    throw new ApplicationRuntimeException(
                            "Error when getting property with name [" + field.getName() + "] - " + e.getMessage(), e);
                }
                
                if (value == null) {
                    continue;
                }

                if (value.getClass().isArray()) {
                    Object [] objectArray = (Object[])value;
                    for (Object obj: objectArray) {
                        redirect.addParameter(field.getName(), String.valueOf(obj));
                    }
                }
                else {
                    redirect.addParameter(field.getName(), String.valueOf(value));
                }
            }
        }
        return redirect;
    }

}
