/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import com.silvermoongroup.common.domain.ObjectReference;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * LongConverter for an {@link ObjectReference}
 * 
 * @author Justin Walsh
 */
public class ObjectReferenceConverter implements Converter {

    /**
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof ObjectReference) {
            return (value);
        }
        else if(value instanceof String) {
            String valueAsString = (String)value;
            value = StringUtils.trimToNull(valueAsString);
            if (value == null) {
                return null;
            }
            else {
                return ObjectReference.convertFromString(valueAsString);
            }
        }

        throw new ConversionException("Unable to convert " + value.getClass() + " to an ObjectReference");
    }

}
