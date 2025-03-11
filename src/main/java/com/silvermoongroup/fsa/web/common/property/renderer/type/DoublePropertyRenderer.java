/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.type;

import com.silvermoongroup.fsa.web.common.property.renderer.AbstractPropertyRenderer;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;

public class DoublePropertyRenderer extends AbstractPropertyRenderer {

    public DoublePropertyRenderer() {
    }

    @Override
    protected String formatPropertyValue(PageContext pageContext, Object propertyValue) {
        if (propertyValue == null) {
            return StringUtils.EMPTY;
        }

        ITypeFormatter formatter = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext())
                .getBean(ITypeFormatter.class);
        return formatter.formatBigDecimal(new BigDecimal((Double)propertyValue));
    }

}
