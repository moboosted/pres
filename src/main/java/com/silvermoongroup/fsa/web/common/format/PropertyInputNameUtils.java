package com.silvermoongroup.fsa.web.common.format;

import com.silvermoongroup.businessservice.policymanagement.dto.PropertyDTO;
import org.apache.commons.lang3.StringUtils;

public class PropertyInputNameUtils {

    public static PropertyInputName extractPropertyInputNameFromProperty(PropertyDTO propertyDTO) {
        PropertyInputName propertyInputName = new PropertyInputName();

        propertyInputName.setKindId(extractKindIdFromProperty(propertyDTO));
        propertyInputName.setType(extractTypeFromProperty(propertyDTO));

        return propertyInputName;
    }

    public static PropertyInputName parse(String inputValue) {
        PropertyInputName propertyInputName = new PropertyInputName();
        propertyInputName.setKindId(getId(inputValue));
        propertyInputName.setType(getType(inputValue));

        return propertyInputName;
    }

    private static String extractKindIdFromProperty(PropertyDTO propertyDTO) {
        return propertyDTO.getKind().getId().toString();
    }

    private static String extractTypeFromProperty(PropertyDTO propertyDTO) {
        return propertyDTO.getConformanceType().getName();
    }

    private static String getId(String inputValue) {
        String[] values = StringUtils.split(inputValue, PropertyInputName.SEPARATOR);
        return values[0];
    }

    private static String getType(String inputValue) {
        String[] values = StringUtils.split(inputValue, PropertyInputName.SEPARATOR);
        return values[1];
    }
}
