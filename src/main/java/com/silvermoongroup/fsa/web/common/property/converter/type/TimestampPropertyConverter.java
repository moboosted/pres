/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimestampPropertyConverter implements IPropertyConverter {

    @Override
    public Object convertFromString(HttpServletRequest request, String value) {
        if(GenericValidator.isBlankOrNull(value)) {
            return null;
        }

        SimpleDateFormat df = new SimpleDateFormat(DateTime.DEFAULT_PATTERN);
        try {
            java.util.Date date = df.parse(value);
            return new Timestamp(date.getTime());
        }
        catch (ParseException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }

}
