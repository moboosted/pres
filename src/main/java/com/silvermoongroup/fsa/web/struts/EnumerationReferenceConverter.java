package com.silvermoongroup.fsa.web.struts;

import com.silvermoongroup.common.domain.EnumerationReference;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;

public class EnumerationReferenceConverter implements Converter {

    /**
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof EnumerationReference) {
            return (value);
        }
        else if(value instanceof String) {
            String valueAsString = (String)value;
            value = StringUtils.trimToNull(valueAsString);
            if (GenericValidator.isBlankOrNull(valueAsString)) {
                return null;
            }
            else {
                return EnumerationReference.convertFromString(valueAsString);
            }
        }

        throw new ConversionException("Unable to convert " + value.getClass() + " to an EnumerationReference");
    }

}
