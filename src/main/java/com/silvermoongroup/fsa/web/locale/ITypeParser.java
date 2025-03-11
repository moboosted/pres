/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.locale;

import com.silvermoongroup.common.datatype.Amount;
import com.silvermoongroup.common.datatype.Date;
import com.silvermoongroup.common.datatype.DateTime;

import java.math.BigDecimal;

/**
 * A class capable or parsing a string representation of an object into the object itself.
 * 
 * @author Justin Walsh
 */
public interface ITypeParser {

    /**
     * Parse a string representation of a date into a {@link com.silvermoongroup.common.datatype.Date} object.
     * 
     * @param value The date string
     * @return The parsed date, or null if the string representation is empty.
     */
    Date parseDate(String value);

    /**
     * Parse a string representation of a dateTime into a {@link com.silvermoongroup.common.datatype.DateTime} object.
     *
     * @param value The date string
     * @return The parsed dateTime, or null if the string representation is empty.
     */
    DateTime parseDateTime(String value);

    /**
     * Parse a string representation into an {@link Amount}.
     * @param value The string representation to parse.
     * @return The parsed value.
     */
    Amount parseAmount(String value);
    
    /**
     * Parse a string representation into an {@link BigDecimal}.
     * @param value The value to parse.
     * @return A big decimial representing the value.
     */
    BigDecimal parseBigDecimal(String value);

}
