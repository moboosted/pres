/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.locale;

import com.silvermoongroup.base.exception.ApplicationRuntimeException;
import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;
import com.silvermoongroup.common.datatype.Time;
import com.silvermoongroup.fsa.web.user.profile.IUserProfileManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * An implementation of a {@link ITypeParser} which uses user profile information to parse an object.
 * 
 * @author Justin Walsh
 */
@Component
public class TypeParser implements ITypeParser {

    @Autowired
    private IUserProfileManager userProfileManager;

    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeParser#parseDate(java.lang.String)
     */
    @Override
    public Date parseDate(String dateString) {

        dateString = StringUtils.trimToNull(dateString);
        if (dateString == null) {
            return null;
        }

        String pattern = getUserProfileManager().getDateFormat();

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = new Date(df.parse(dateString));
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "The date: " + dateString + " does not conform to the pattern: " + pattern
                    );
        }
        return date;
    }

    /**
     * @see com.silvermoongroup.fsa.web.locale.ITypeParser#parseDateTime(java.lang.String)
     */
    @Override
    public DateTime parseDateTime(String dateString) {

        dateString = StringUtils.trimToNull(dateString);
        if (dateString == null) {
            return null;
        }

        String pattern = getUserProfileManager().getDateTimeFormat();

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        DateTime dateTime;
        try {
            java.util.Date parsedDate = df.parse(dateString);
            dateTime = new DateTime(new Date(parsedDate), new Time(parsedDate));
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException(
                    "The dateTime: " + dateString + " does not conform to the pattern: " + pattern
            );
        }
        return dateTime;
    }
    
    @Override
    public Amount parseAmount(String amountString) {
        BigDecimal value = parseBigDecimal(amountString);
        return value == null ? null : new Amount(value);
    }
    
    @Override
    public BigDecimal parseBigDecimal(String value) {
        
        value = StringUtils.trimToNull(value);
        if (value == null) {
            return null;
        }
        
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(userProfileManager.getDecimalSeparator());
        symbols.setGroupingSeparator(userProfileManager.getGroupingSeparator());
        
        DecimalFormat df = new DecimalFormat(userProfileManager.getNumberFormat(), symbols);
        df.setParseBigDecimal(true);
        
        BigDecimal bd;
        try {
            bd = (BigDecimal)df.parse(value);
        } catch (ParseException e) {
            throw new ApplicationRuntimeException("Error when parsing [" + value + "] into a BigDecimal: " + e.getMessage(), e);
        }
        
        return bd;
    }

    public IUserProfileManager getUserProfileManager() {
        return userProfileManager;
    }

    public void setUserProfileManager(IUserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

}
