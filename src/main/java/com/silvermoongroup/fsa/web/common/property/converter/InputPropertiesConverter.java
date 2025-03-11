/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter;

import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.service.intf.IPolicyAdminService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.fsa.web.common.format.PropertyInputName;
import com.silvermoongroup.fsa.web.common.property.IPropertyTypeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
public class InputPropertiesConverter {

    @Autowired
    private IPropertyTypeRegistry registry;

    @Autowired
    private IPolicyAdminService fsaBusinessService;

    private static final String CURRENCY_VALUE = "CurrencyValue";

    public Map<KindDTO, Object> convertValuesToCorrectType(HttpServletRequest request,
                                                           Map<PropertyInputName, Object> inputValues) {

        Map<KindDTO, Object> convertedValues = new HashMap<>();
        ApplicationContext applicationContext = new ApplicationContext();
        Object convertedValue = null;

        for (PropertyInputName propertyInputName : inputValues.keySet()) {

            if (CURRENCY_VALUE.equals(propertyInputName.getType())) {
                continue;
            }

            String value = (String) inputValues.get(propertyInputName);

            IPropertyConverter converter = registry.getConverterForType(propertyInputName.getType());
            if (converter == null) {
                throw new IllegalStateException("No property converter found for type '" + propertyInputName.getType()
                        + "'");
            }

            convertedValue = converter.convertFromString(request, value);
            KindDTO kindDTO = fsaBusinessService.getKind(applicationContext, Long.valueOf(propertyInputName.getKindId()));

            convertedValues.put(kindDTO, convertedValue);
        }

        return convertedValues;
    }
}