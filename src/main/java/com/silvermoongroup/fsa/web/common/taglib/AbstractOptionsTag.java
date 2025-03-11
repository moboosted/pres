/**  Licensed Materials
 **  (C) Copyright Silvermoon Business Systems BVBA, Belgium  2006, 2007
 **  (C) Copyright Silvermoon Trading 5 Pty Ltd, South Africa 2006, 2007
 **  All Rights Reserved.
 **/
package com.silvermoongroup.fsa.web.common.taglib;

import com.silvermoongroup.fsa.web.locale.ITypeFormatter;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.OptionsCollectionTag;
import org.apache.struts.taglib.html.SelectTag;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

/**
 * Base class for custom options tags.
 * 
 * @author Justin Walsh
 */
public abstract class AbstractOptionsTag extends OptionsCollectionTag {

    private static final long serialVersionUID = 1L;

    @Override
    public final int doStartTag() throws JspException {
        // Acquire the select tag we are associated with
        SelectTag selectTag = (SelectTag) pageContext.getAttribute(Constants.SELECT_KEY);

        if (selectTag == null) {
            JspException e = new JspException(OptionsCollectionTag.messages.getMessage("optionsCollectionTag.select"));
            TagUtils.getInstance().saveException(pageContext, e);
            throw e;
        }

        StringBuffer sb = new StringBuffer(1024);
        buildOptions(selectTag, sb);

        TagUtils.getInstance().write(pageContext, sb.toString());
        return Tag.SKIP_BODY;
    }

    /**
     * Build the options for this tag
     * @param selectTag The outer select tag
     * @param sb The StringBuffer that should be used to populate the options
     */
    abstract void buildOptions(SelectTag selectTag, StringBuffer sb) throws JspException;

    protected Object getBean(String name) {
        return getWebApplicationContext().getBean(name);
    }

    protected WebApplicationContext getWebApplicationContext() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    }

    protected ITypeFormatter getTypeFormatter() {
        return getWebApplicationContext().getBean(ITypeFormatter.class);
    }
}
