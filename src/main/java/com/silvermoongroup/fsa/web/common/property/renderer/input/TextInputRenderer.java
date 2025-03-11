/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.input;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.PageContext;

public class TextInputRenderer extends AbstractInputRenderer {

    private String value;
    private int sizeOfField;

    @SuppressWarnings("unused")
    private TextInputRenderer() {}

    public TextInputRenderer(String id, String name, String value) {
        this(id, name,value,-1);
    }

    public TextInputRenderer(String id, String name, String value, int sizeOfField) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.sizeOfField = sizeOfField;
    }

    @Override
    public String getRenderedInputField(PageContext pageContext) {

        if (value == null) {
            value = StringUtils.EMPTY;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<input id=\"").append(id).append("\" type=\"text\" value=\"").append(value).append("\"");
        sb.append(" name=\"" + PROPERTIES_MAP + "(").append(name).append(")\" ");

        if (sizeOfField >= 0) {
            sb.append(" size=\"").append(sizeOfField).append("\" ");
        }

        if (getStyleClass() != null) {
            sb.append(" class=\"").append(getStyleClass()).append("\" ");
        }

        sb.append("/>");
        return sb.toString();
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
