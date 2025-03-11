/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;

public class LongPropertyConverter implements IPropertyConverter {

    @Override
    public Object convertFromString(HttpServletRequest request, String value) {
        if(GenericValidator.isBlankOrNull(value)) {
            return null;
        }
        return Long.valueOf(value);
    }

}
