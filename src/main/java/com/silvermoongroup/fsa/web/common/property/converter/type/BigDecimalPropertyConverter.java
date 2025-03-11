/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.converter.type;

import javax.servlet.http.HttpServletRequest;

public class BigDecimalPropertyConverter extends AbstractConverter {

    @Override
    public Object convertFromString(HttpServletRequest request, String value) {        
        return parseBigDecimalValue(request, value);
    }

}
