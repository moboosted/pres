/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.common.datatype.Percentage;
import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class PercentagePropertyConverter implements IPropertyConverter {

    @Override
    public Object convertFromString(HttpServletRequest request, String value) {
        if(GenericValidator.isBlankOrNull(value)) {
            return null;
        }
        
        // we represent the values as 10% on the GUI, but 0.1 as the value
        BigDecimal raw = new BigDecimal(value);
        BigDecimal converted = raw.movePointLeft(2);
        
        return new Percentage(converted);
    }

}
