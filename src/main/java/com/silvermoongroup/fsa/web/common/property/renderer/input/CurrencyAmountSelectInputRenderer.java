/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.property.renderer.input;

import com.silvermoongroup.common.domain.EnumerationReference;
import com.silvermoongroup.common.domain.intf.IEnumeration;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;
import java.util.Collection;

/**
 * @author Justin Walsh
 */
public class CurrencyAmountSelectInputRenderer extends SelectInputRenderer {

    /**
     * Create a renderer for a currency amount selection.
     *
     * @param name          The name of the select option.
     * @param value         The underlying value on the actual.
     * @param allowedValues The set of allowed values.
     */
//    public CurrencyAmountSelectInputRenderer(String id, String name, ICurrencyCode value,
//            Collection<?> allowedValues) {
//        
//        super(id, name, value, allowedValues);
//    }
    
    public CurrencyAmountSelectInputRenderer(String id, String name, EnumerationReference value,
            Collection<?> allowedValues) {
        super(id, name, value, allowedValues, null);
    }

    @Override
    public String getRenderedInputField(PageContext pageContext) {
        ITypeFormatter typeFormatter = WebApplicationContextUtils.getWebApplicationContext(
                pageContext.getServletContext()).getBean(ITypeFormatter.class);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<select id=\"").append(getId()).append("\" name=\"").append(PROPERTIES_MAP).append("(").append(getName()).append(")\"");
        if (getStyleClass() != null) {
            stringBuffer.append(" class=\"").append(getStyleClass()).append("\" ");
        }
        stringBuffer.append(">");

        addEmptyOption(stringBuffer);

        for (Object allowedValue : getAllowedValues()) {
            IEnumeration currencyCode = (IEnumeration) allowedValue;
            if (isEqual(currencyCode, getValue())) {
                stringBuffer.append("\n<option selected=\"selected\" value=\"").append(currencyCode.getName()).append("\">");
            } else {
                stringBuffer.append("\n<option value=\"").append(currencyCode.getName()).append("\">");
            }

            stringBuffer.append(typeFormatter.formatEnum(currencyCode.getEnumerationReference())).append("</option>");
        }
        stringBuffer.append("</select>");

        return stringBuffer.toString();
    }

    @Override
    protected boolean isEqual(Object allowedValue, Object object) {

        if (object == null) {
            return false;
        }
        EnumerationReference ca = (EnumerationReference) object;
        IEnumeration allowed = (IEnumeration) allowedValue;
        
        return allowed.getEnumerationReference().equals(ca);
    }

}
