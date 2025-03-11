/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.PageContext;

public class StringPropertyRenderer extends AbstractPropertyRenderer {

    public StringPropertyRenderer() {
    }

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }
        return propertyValue.toString();
    }

}
