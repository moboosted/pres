/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import com.silvermoongroup.fsa.web.locale.ITypeParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * Base class for converters
 * 
 * @author Justin Walsh
 */
public abstract class AbstractConverter implements IPropertyConverter {
    
    /**
     * Parse a String value into a {@link BigDecimal} using the user's number formatting preferences.
     * @param request The HttpRequest
     * @param value The value to parse
     * @return null if the value is null or an empty String (after trimming), otherwise the BigDecimal representation 
     * of the value based on the user's preferences.
     * @throws ApplicationRuntimeException If the amount cannot be parsed.
     */
    protected BigDecimal parseBigDecimalValue(HttpServletRequest request, String value) {
        
        value = StringUtils.trimToNull(value);
        if (value == null) {
            return null;
        }
        
        WebApplicationContext wac = 
                WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        
        ITypeParser typeParser= wac.getBean(ITypeParser.class);
        return typeParser.parseBigDecimal(value);
    }


}
