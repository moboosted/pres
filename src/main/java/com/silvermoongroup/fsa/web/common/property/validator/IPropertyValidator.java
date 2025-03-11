/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.validator;

import javax.servlet.http.HttpServletRequest;

/**
 * Property validators are responsible for validating the value of a property.
 */
public interface IPropertyValidator {

    /**
     * Validate a property, returning the message key describing the property
     * validation failure, or null if there are no errors.
     * @param request The servlet request.
     * @param value The value to validate
     * @return A message key describing the property validation error, or null
     * if there are no errors.
     */
    String validate(HttpServletRequest request, String value);
}
