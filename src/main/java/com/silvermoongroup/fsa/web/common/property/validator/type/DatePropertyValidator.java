/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.validator.GenericValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;

public class DatePropertyValidator implements IPropertyValidator {

    @Override
    public String validate(HttpServletRequest request, String value) {
        if (value == null) {
            return null;
        }

        ApplicationContext ac =
                WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        String pattern = ac.getBean(IUserProfileManager.class).getDateFormat();

        if (!GenericValidator.isBlankOrNull(value) && !GenericValidator.isDate(value, pattern, true)) {
            return "message.date.invalid";
        }

        return null;
    }

}
