/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;

public class TimestampPropertyValidator implements IPropertyValidator {

    @Override
    public String validate(HttpServletRequest request, String value) {
        if (!GenericValidator.isBlankOrNull(value) &&
                !GenericValidator.isDate(value, DateTime.DEFAULT_PATTERN, true)) {
            return "message.datetime.invalid";
        }

        return null;
    }

}
