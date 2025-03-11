/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.struts;

import com.silvermoongroup.businessservice.productmanagement.service.intf.IProductDevelopmentService;
import com.silvermoongroup.common.domain.ApplicationContext;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.validator.*;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Date;

/**
 * Custom validators to plug into the struts framework
 *
 * @author Justin Walsh
 */
public class CustomValidators {

    /**
     * Validate an amount (bigdecimal) value
     */
    public static Object validateAmount(Object bean, ValidatorAction va,
                                        Field field, ActionMessages errors, Validator validator,
                                        HttpServletRequest request, ServletContext servletContext) {
        String value = evaluateBean(bean, field);
        if (GenericValidator.isBlankOrNull(value)) {
            return Boolean.TRUE;
        }

        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());

        IUserProfileManager upm = wac.getBean(IUserProfileManager.class);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(upm.getDecimalSeparator());
        symbols.setGroupingSeparator(upm.getGroupingSeparator());

        DecimalFormat df = new DecimalFormat(upm.getNumberFormat(), symbols);
        df.setParseBigDecimal(true);
        try {
            df.parse(value);
        } catch (ParseException e) {
            errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * Validate a date according to the user's locale
     */
    public static Object validateDate(Object bean, ValidatorAction va,
                                      Field field, ActionMessages errors, Validator validator,
                                      HttpServletRequest request, ServletContext servletContext) {
        String value = evaluateBean(bean, field);
        if (GenericValidator.isBlankOrNull(value)) {
            return Boolean.TRUE;
        }

        Date result = parseDate(servletContext, value);
        if (result == null) {
            errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
        }

        return (result == null) ? Boolean.FALSE : result;
    }


    public static Object validateDateBefore(Object bean, ValidatorAction va,
                                            Field field, ActionMessages errors, Validator validator,
                                            HttpServletRequest request, ServletContext servletContext) {
        String value = evaluateBean(bean, field);
        if (GenericValidator.isBlankOrNull(value)) {
            return Boolean.TRUE;
        }

        String secondDatePropertyName = field.getVarValue("secondProperty");
        String secondDateValue = ValidatorUtils.getValueAsString(bean, secondDatePropertyName);
        if (GenericValidator.isBlankOrNull(secondDateValue)) {
            return Boolean.TRUE;
        }

        // we assume date validation has passed
        Date date1 = parseDate(servletContext, value);
        Date date2 = parseDate(servletContext, secondDateValue);
        if (date1 == null || date2 == null) {
            return Boolean.TRUE;
        }

        if (date1.after(date2)) {
            errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
            return Boolean.FALSE;
        }


        return Boolean.TRUE;
    }

    /**
     * Validate a currency code
     */
    public static Object validateCurrencyCode(Object bean, ValidatorAction va,
                                              Field field, ActionMessages errors, Validator validator,
                                              HttpServletRequest request, ServletContext servletContext) {
        String value = evaluateBean(bean, field);
        if (GenericValidator.isBlankOrNull(value)) {
            return Boolean.TRUE;
        }

        IProductDevelopmentService productDevelopmentService =
                WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean
                        (IProductDevelopmentService.class);

        IEnumeration cc = productDevelopmentService.getEnumeration(new ApplicationContext(),
                EnumerationReference.convertFromString(value));

        if (cc == null) {
            errors.add(field.getKey(),
                    Resources.getActionMessage(validator, request, va, field));
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private static String evaluateBean(Object bean, Field field) {
        String value;

        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }

        return value;
    }

    /**
     * Return <code>true</code> if the specified object is a String or a
     * <code>null</code> value.
     *
     * @param o Object to be tested
     * @return The string value
     */
    protected static boolean isString(Object o) {
        return (o == null) || String.class.isInstance(o);
    }

    private static Date parseDate(ServletContext servletContext, String value) {
        IUserProfileManager upm =
                WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(IUserProfileManager.class);
        String datePattern = upm.getDateFormat();

        try {
            return GenericTypeValidator.formatDate(value, datePattern, true);
        } catch (Exception e) {
            return null;
        }
    }

}
