/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter;

import javax.servlet.http.HttpServletRequest;

/**
 * A property converter converts between string and object representations
 * of a property.
 */
public interface IPropertyConverter {

    /**
     * Convert a string representation of a property to the correct object
     * type.  An empty string is interpreted as a null value.
     * @param request The servlet request.
     * @param value The string to covert.
     * @return The coverted type.
     */
    Object convertFromString(HttpServletRequest request, String value);
}
