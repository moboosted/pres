/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.common.domain.ObjectReference;
import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * @author Justin Walsh
 */
public class ObjectReferenceWithToolTipTag extends TagSupport {

    private ObjectReference value;

    // either type or id
    private String display;

    public ObjectReferenceWithToolTipTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            if (getDisplay() == null || getDisplay().equalsIgnoreCase("id")) {
                out.print(getTypeFormatter().generateIdentityDivWithObjectReferenceTooltip(getValue()));
            }
            else if (getDisplay().equalsIgnoreCase("type")) {
                out.print(getTypeFormatter().generateTypeDivWithObjectReferenceTooltip(getValue()));
            }
            else {
                throw new IllegalStateException("display attribute must be one of 'type' or 'id'");
            }

        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    public ObjectReference getValue() {
        return value;
    }

    public void setValue(ObjectReference value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    private ITypeFormatter getTypeFormatter() {
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
        return ac.getBean(ITypeFormatter.class);
    }
}
