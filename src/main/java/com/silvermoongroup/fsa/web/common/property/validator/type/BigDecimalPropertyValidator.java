/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BigDecimalPropertyValidator extends AbstractValidator {

    @Override
    public String validate(HttpServletRequest request, String value) {
        
        value = StringUtils.trimToNull(value);
        if (value == null) {
            return null;
        }
        
        if (isValidBigDecimal(request, value) == false) {
            return "message.number.invalid";
        }
        return null;
    }

}
