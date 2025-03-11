/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.input;

import javax.servlet.jsp.PageContext;
import java.util.Collection;

public class SelectInputRenderer extends AbstractInputRenderer {

    private String id;
    private String name;
    private Object value;
    private Collection<?> allowedValues;
    private Object defaultValue;

    @SuppressWarnings("unused")
    private SelectInputRenderer() {
    }

    /**
     * Create a select input renderer for a property specification.
     *
     * @param id            The id of the options element
     * @param name          The name of the select to be rendered.
     * @param value         The underlying value bound in the actual, which may be null.
     * @param allowedValues The set of values allowed by the specification.
     * @param defaultValue The default value (may be null).
     */
    public SelectInputRenderer(String id, String name, Object value, Collection<?> allowedValues, Object defaultValue) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.allowedValues = allowedValues;
        this.defaultValue = defaultValue;
    }

    /**
     * @see com.silvermoongroup.fsa.web.common.property.renderer.input.IInputRenderer#getRenderedInputField(PageContext)
     */
    @Override
    public String getRenderedInputField(PageContext pageContext) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<select id=\"").append(id).append("\" name=\"").append(PROPERTIES_MAP).append("(").append(name).append(")\"");
        if (getStyleClass() != null) {
            stringBuffer.append(" class=\"").append(getStyleClass()).append("\" ");
        }
        stringBuffer.append(">");

        addEmptyOption(stringBuffer);

        for (Object allowedValue : allowedValues) {
            appendOption(stringBuffer, allowedValue, value);
        }

        // the model allows a default value to be specified without including this in the allowed values.
        if (defaultValue != null && !allowedValues.contains(defaultValue)) {
            appendOption(stringBuffer, defaultValue, value);
        }

        stringBuffer.append("</select>");

        return stringBuffer.toString();
    }

    private void appendOption(StringBuffer stringBuffer, Object optionValue, Object selectedValue) {
        if (isEqual(optionValue, selectedValue)) {
            stringBuffer.append("\n<option selected=\"selected\" value=\"").append(optionValue).append("\">");
        } else {
            stringBuffer.append("\n<option value=\"").append(optionValue).append("\">");
        }
        stringBuffer.append(optionValue).append("</option>");
    }

    /**
     * Compare the allowed value (a string) to the object that is bound in the actual.  In cases where the property in
     * the actual is a String, a simple comparison based on {@link Object#equals(Object)} will suffice.  This type of
     * comparison is provided by the default implementation of this method.
     *
     * <p>There are cases however when an object (for example a currency amount) needs to be compared to an allowed
     * value.  In these cases, a simple comparison will fail if the formatting of the allowed value is different
     * to the default string represenation of the object
     *
     * @param allowedValue The string representin the allowed value.
     * @param object       The object being compared.
     * @return true if the objects are equal, otherwise false.
     */
    protected boolean isEqual(Object allowedValue, Object object) {
        return allowedValue.toString().equals(String.valueOf(object));
    }

    protected void addEmptyOption(StringBuffer stringBuffer) {
        stringBuffer.append("\n<option value=\"\" ");
        if (value == null) {
            stringBuffer.append("selected=\"selected\"");
        }
        stringBuffer.append("></value>");
    }

    protected String getName() {
        return name;
    }

    protected Object getValue() {
        return value;
    }

    protected Collection<?> getAllowedValues() {
        return allowedValues;
    }

    protected String getId() {
        return id;
    }
}
