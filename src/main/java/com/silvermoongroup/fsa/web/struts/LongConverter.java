/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * A modification of the <code>LongConverter</code> provided by BeanUtils that trims the value (if it is a string)
 * 
 * @author Justin Walsh
 */
public class LongConverter implements Converter {

    /**
     * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
     */
    @Override
    public Object convert(@SuppressWarnings("rawtypes") Class type, Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Long) {
            return (value);
        } else if(value instanceof Number) {
            return Long.valueOf(((Number)value).longValue());
        }

        String valueAsString = StringUtils.trimToNull(value.toString());
        if (valueAsString == null) {
            return null;
        }

        try {
            return Long.valueOf(valueAsString);
        } catch (Exception e) {
            return null;
        }
    }
}
