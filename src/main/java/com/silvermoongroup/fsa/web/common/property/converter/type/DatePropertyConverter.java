/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.fsa.web.common.property.converter.IPropertyConverter;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatePropertyConverter implements IPropertyConverter {

    @Override
    public Object convertFromString(HttpServletRequest request, String value) {
        if(GenericValidator.isBlankOrNull(value)) {
            return null;
        }

        ApplicationContext applicationContext =
                WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        IUserProfileManager upm = applicationContext.getBean(IUserProfileManager.class);

        SimpleDateFormat sdf = new SimpleDateFormat(upm.getDateFormat());
        try {
            return new Date(sdf.parse(value));
        }
        catch (ParseException e) {
            throw new ApplicationRuntimeException(e);
        }
    }
}