/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium 
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.property.validator.type;

import com.silvermoongroup.fsa.web.common.property.validator.IPropertyValidator;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

/**
 * @author Justin Walsh
 */
public abstract class AbstractValidator implements IPropertyValidator {

    protected WebApplicationContext wac;

    protected boolean isValidBigDecimal(HttpServletRequest request, String value) {
        
        value = StringUtils.trim(value);
        if (value == null) {
            return false;
        }

        wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        IUserProfileManager upm = wac.getBean(IUserProfileManager.class);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(upm.getDecimalSeparator());
        symbols.setGroupingSeparator(upm.getGroupingSeparator());

        DecimalFormat df = new DecimalFormat(upm.getNumberFormat(), symbols);
        df.setParseBigDecimal(true);
        try {
            df.parse(value);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
