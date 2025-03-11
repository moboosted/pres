/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa
 **  All Rights Reserved.
 **/

package com.silvermoongroup.fsa.web.common.property.renderer.input;

import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.PageContext;

/**
 * @author Justin Walsh
 */
public abstract class AbstractInputRenderer implements IInputRenderer {

    protected String name;
    protected String id;
    private String styleClass;

    public AbstractInputRenderer() {
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String style) {
        this.styleClass = style;
    }

    protected String getMessage(PageContext pageContext, String key, String... params) {
        WebApplicationContext wac =
                WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
        return wac.getBean(ITypeFormatter.class).formatMessage(key, params);

    }
}
