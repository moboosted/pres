/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.locale;

import com.silvermoongroup.businessservice.policymanagement.dto.AgreementStateDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.KindDTO;
import com.silvermoongroup.businessservice.policymanagement.dto.RequestStateDTO;
import com.silvermoongroup.businessservice.productmanagement.dto.Type;
import com.silvermoongroup.common.datatype.*;
import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.common.enumeration.CoreTypeReference;
import com.silvermoongroup.common.enumeration.intf.IEnum;
import com.silvermoongroup.common.enumeration.intf.ITypeReference;

import java.math.BigDecimal;
import java.util.List;

/**
 * A class capable of formatting objects into a String representation for display.
 * 
 * @author Justin Walsh
 */
public interface ITypeFormatter {
    
    String formatAmount(Amount amount);
    
    /**
     * Format a currency acmount
     * @param value The value to format
     * @return The formatted value.
     */
    String formatCurrencyAmount(CurrencyAmount value);
    
    /**
     * Format a measure amount
     * @param toFormat
     * @return The formatted value
     */
    String formatMeasureAmount(MeasureAmount toFormat);

    /**
     * Format a measure amount
     *
     * @param toFormat
     * @param includeUnitOfMeasure Whether to prepend the unit of measure
     * @return The formatted value
     */
    String formatMeasureAmount(MeasureAmount toFormat, boolean includeUnitOfMeasure);
    
    /**
     * Format a {@link BigDecimal} value.
     * @param bd The value to format.
     * @return The formatted value.
     */
    String formatBigDecimal(BigDecimal bd);
    
    /**
     * Format a percentage.  A percentate is always stored in decimal format, but formatted as a percent
     * e.g. 0.1 = 10 
     * 
     * @param percentage The percentage to format.
     * @return The formatted string.
     */
    String formatPercentage(Percentage percentage);

    /**
     * Format a date into a string representation using the user's date pattern setting.
     * @param toFormat The date to format
     * @return The formatted date, an empty string if the date is null.
     */
    String formatDate(Date toFormat);

    /**
     * Format a {@link DateTime} into a string representation using the user's date/time pattern setting
     * @param toFormat The {@link DateTime} to format.
     * @return The formated representation.  Empty if the {@link DateTime} to format is null.
     */
    String formatDateTime(DateTime toFormat);

    String formatEnum(IEnum immutableEnum);
    
    String formatEnum(EnumerationReference enumerationRef);
    
    String formatEnum(IEnumeration enumeration);

    String formatKindHint(KindDTO kind);

    String formatSpecificationObjectHint(KindDTO kind, String defaultDisplayHint);

    String formatKind(String kindCategory, String kindName);

    String formatSpecificationObjectName(KindDTO kind, String defaultDisplayName);

    String formatKind(KindDTO kind);

    String formatRequestState(RequestStateDTO state);

    String formatRequestState(String stateName);

    String formatAgreementState(AgreementStateDTO value);

    /**
     * Get a localised message for a given key
     * @param key The key to lookup the message
     * @param parameters The parameters
     * @return The formatted message, a (???? + key + ????) value if the key is not found.
     */
    String formatMessage(String key, String... parameters);
    
    /**
     * Get a localised message for a given key and parameters, defaulting to the 'fallback' parameter.
     * if the key is not found.
     * @param key The key to lookup the message
     * @param parameters The parameters
     * @param fallback The message to use if the key is not present.
     * @return The formatted message, or the the 'fallback' parameter if the message is not found.
     */
    String formatMessageWithFallback(String key, String fallback, String... parameters);
    
    /**
     * Format the type
     * @param toFormat The identifier of the type to format
     * @return The formatted representation of the type
     */
    String formatType(Long toFormat);
    
    /**
     * Format the type
     * @param toFormat
     * @return The formatted representation of the type
     */
    String formatType(Type toFormat);    

    /**
     * Format a {@link CoreTypeReference}
     * @param type The type to format.
     * @return The formatted representation of the type.
     */
    String formatTypeReference(ITypeReference type);
    
    /**
     * Useful if you don't know what the object is that you are formatting.
     * 
     * <p>Currently supports the following objects:
     * <ul>
     * <li>{@link String}</li>
     * <li>{@link Date}</li>
     * <li>{@link KindDTO}</li>
     * <li>{@code LifeCycleStatus}</li>
     * <li>The null value.  Formatted as an empty string</li>
     * </ul>
     *
     * 
     * If the formatting of an object type is not supported, an {@link IllegalArgumentException} is thrown.
     *  
     * @param toFormat The object to format. 
     * @return The formatted object
     * @throws IllegalArgumentException If the object is not a supported object type.
     */
    String formatObject(Object toFormat) throws IllegalArgumentException;
    
    /**
     * Formats the list of objects, returning an array of string representations.
     * @param objectsToFormat The objects to format.
     * @return An array of formatted string representations of the objects passed in.
     * @throws IllegalArgumentException If on of the objects is not a supported object type.
     * @see ITypeFormatter#formatObject(Object)
     */
    String [] formatObjects(List<Object> objectsToFormat) throws IllegalArgumentException;

    /**
     * Generate a div with the object's id displayed and a tool tip for the object reference
     * @param objectReference The object reference
     * @return The generated div
     */
    String generateIdentityDivWithObjectReferenceTooltip(ObjectReference objectReference);

    /**
     * Generate a div with the object's type displayed and a tool tip for the object reference
     * @param objectReference The object reference
     * @return The generated div
     */
    String generateTypeDivWithObjectReferenceTooltip(ObjectReference objectReference);

    /**
     * Generate a div with the name displayed, falling back to the type name if the name is null.  A tool tip containing
     * the  object reference is also generated
     * @param objectReference The object reference
     * @param name The name to display
     * @return The generated div
     */
    String generateDivWithObjectReferenceTooltip(ObjectReference objectReference, String name);

    /**
     * Format category for categoryId
     * @param toFormat
     * @return The formatted representation of the category
     */
    String formatCategory(long toFormat);
}
