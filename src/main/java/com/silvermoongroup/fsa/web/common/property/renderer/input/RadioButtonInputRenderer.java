/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.property.renderer.input;

import javax.servlet.jsp.PageContext;

public class RadioButtonInputRenderer extends AbstractInputRenderer {

    private String value;


    @SuppressWarnings("unused")
    private RadioButtonInputRenderer() {}

    public RadioButtonInputRenderer(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    @Override
    public String getRenderedInputField(PageContext pageContext) {
        String trueLabel = getMessage(pageContext, "label.true");
        String falseLabel = getMessage(pageContext, "label.false");

        return getRenderedRadioOption(trueLabel, true) + " " + getRenderedRadioOption(falseLabel, false);
    }

    private String getRenderedRadioOption(String optionLabel, boolean optionValue) {
        StringBuilder sb = new StringBuilder();
        sb.append("<input class=\"radio-inline\" id=\"").append(id).append("-").append(optionValue).append("\" type=\"radio\" value=\"").append(optionValue).append("\" ");
        sb.append(" name=\"" + PROPERTIES_MAP + "(").append(name).append(")\" ");

        if(optionValue == Boolean.valueOf(value)) {
            sb.append(" checked=\"checked\" ");
        }
        sb.append("> ");

        sb.append(optionLabel);
        sb.append("</input>");

        return sb.toString();
    }

}
