/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator;

import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.property.IPropertyTypeRegistry;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class InputPropertiesValidator {

    @Autowired
    private IPropertyTypeRegistry registry;
    
    public Map<PropertyInputName, String> getInputPropertiesValidationMessages(HttpServletRequest request,
            Map<PropertyInputName, Object> inputProperties, ActionMessages actionMessages) {

        Map<PropertyInputName, String> inputPropertiesValidationMessages = new HashMap<>();

        for (PropertyInputName propertyInputName : inputProperties.keySet()) {
            String value = (String) inputProperties.get(propertyInputName);

            IPropertyValidator propertyValidator = registry.getValidatorForType(propertyInputName.getType());

            String errorMessage = propertyValidator.validate(request, value);

            if (errorMessage != null) {
                inputPropertiesValidationMessages.put(propertyInputName, errorMessage);
            }
        }

        if (inputPropertiesValidationMessages.size() > 0) {
            actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("message.propertyvalidationfailed"));
        }

        return inputPropertiesValidationMessages;
    }
}